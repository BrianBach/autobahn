package net.geant.autobahn.testplatform.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.geant.autobahn.intradomain.administration.DmAdministration;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.testplatform.clients.DmAdministrationClient;
import net.geant.autobahn.testplatform.helpers.AutobahnManagement;
import net.geant.autobahn.testplatform.helpers.AutobahnManagementFactory;
import net.geant.autobahn.testplatform.helpers.Unzipper;
import net.geant.autobahn.testplatform.observer.StatusObserver;

public class EnvironmentSetup {

	private MyProperties props = null;
	private List<DomainConfiguration> configurations = new ArrayList<DomainConfiguration>();
	private StatusObserver observer; 
	private AutobahnManagement[] domains;
	private File zipFile = new File("target/autobahn.zip");
	
	public EnvironmentSetup(MyProperties props) {
		this.props = props;
	}
	
	@SuppressWarnings("unchecked")
	public void prepareConfiguration() {
		String className = props.getProperty("topology.class");
		
		List<IntraTopologyBuilder> builders = new ArrayList<IntraTopologyBuilder>();
		
		try {
			Class c = Class.forName(className);
			Method methods[] = c.getDeclaredMethods();
			
			Object instance = c.newInstance();
			
			for(Method m : methods) {
				if(m.getName().startsWith("domain")) {
					IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
					builders.add(builder);
					
					m.invoke(instance, builder);
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		IncrementalProperties incremental = new IncrementalProperties(props);
		AutobahnManagementFactory factory = new AutobahnManagementFactory();
		domains = new AutobahnManagement[builders.size()];
		
		int index = 0;
		// Create configuration for each instance
		for(IntraTopologyBuilder builder : builders) {
			IncrementalProperties current = incremental.incrementValues();
			
			DomainConfiguration conf = new DomainConfiguration(props, builder, current);
			configurations.add(conf);

			String os = System.getProperty("os.name").toLowerCase();
			
			AutobahnManagement domain = null;
			if(os.indexOf("windows") >= 0) {
				domain = factory.createWindowsInstance(conf.getHome(), 
						conf.buildAutobahnUrl(""));
			} else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
				domain = factory.createLinuxInstance(conf.getHome(), 
						conf.buildAutobahnUrl(""));
			}
			
			domain.setConfiguration(conf);
			domains[index++] = domain;
		}
	}
	
	public AutobahnManagement[] recreateEnvironment() {
		prepareConfiguration();
		
		if(!isReady()) {
			copyDistributions();
			writeConfigurationFiles();
			dropDatabases();
			createDatabasesSchema();
			
			startInstances();
			
			injectIntradomainTopology();
			
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			shareAbstractTopology();
			
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			stopInstances();
			
			try {
				Thread.sleep(20 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return domains;		
	}
	
	private File createOrGetHomeDir() {
		File home = new File(props.getProperty("default.home") + "/"
				+ props.getProperty("environment.name"));
		
		if(home.exists())
			return home;
		
		home = new File(props.getProperty("default.home"));
		if(!home.exists())
			home.mkdir();
		
		home = new File(home, props.getProperty("environment.name"));
		if(!home.exists())
			home.mkdir();
		
		return home;
	}
	
	public boolean isReady() {
		
		if(!zipFile.exists()) {
			System.out.println("New AutoBAHN distribution not found. Using the existing installations.");
			return true;
		}
		
		for(DomainConfiguration conf : configurations) {
			Long stamp = conf.getDistributionTimeStamp();
			if(stamp == null || stamp < zipFile.lastModified()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void copyDistributions() {
		createOrGetHomeDir();

		File destDir = new File("dist-temp");
		if(destDir.exists()) {
			DomainConfiguration.deleteDirectory(destDir);
		}
		destDir.mkdir();
		
		Unzipper.unzip(zipFile, destDir);
		
		for(DomainConfiguration conf : configurations) {
			try {
				conf.copyDistribution(new File("dist-temp/autobahn"), zipFile.lastModified());
			} catch(IOException e) {
				System.out.println("Problem with copying distribution, " + e.getMessage());
			}
		}
		
		DomainConfiguration.deleteDirectory(destDir);
	}
	
	public void writeConfigurationFiles() {
		createOrGetHomeDir();
		
		for(DomainConfiguration conf : configurations) {
			try {
				conf.writeConfigurationFiles();
			} catch(IOException e) {
				System.out.println("Problem with writing configuration, " + e.getMessage());
			}
		}
	}
	
	public void createDatabasesSchema() {
		for(DomainConfiguration conf : configurations) {
			conf.createDatabaseSchema();
		}
	}
	
	public void dropDatabases() {
		for(DomainConfiguration conf : configurations) {
			conf.dropDatabase();
		}
	}
	
	public void cleanDatabases() {
		for(DomainConfiguration conf : configurations) {
			conf.cleanOldReservations();
		}
	}
	
	public void startInstances() {
		observer = new StatusObserver("http://localhost:9090/status");
		observer.start();
		
		System.out.println(" - -- Starting instances...");
		
		for(AutobahnManagement domain : domains) {
			System.out.println("Starting: " + domain.getDomainAddress());
			domain.startInstance(observer);
		}
		
		observer.waitForDomains(domains);
		System.out.println(" - -- Started");
	}
	
	public void stopInstances() {
		System.out.println(" - -- Stopping instances");
		
		if(domains != null) {
			for(AutobahnManagement domain : domains)
				domain.stopInstance();
		} else {
			AutobahnManagementFactory factory = new AutobahnManagementFactory();
			
			for(DomainConfiguration conf : configurations) {
				AutobahnManagement domain = factory.createWindowsInstance(
						conf.getHome(), conf.buildAutobahnUrl(""));
				domain.stopInstance();
			}
		}
	}
	
	public void injectIntradomainTopology() {
		if(observer == null) {
			observer = new StatusObserver("http://localhost:9090/status");
			observer.start();
		}
		
		for(DomainConfiguration conf : configurations) {
			DmAdministration client = new DmAdministrationClient(
					conf.buildAutobahnUrl("/dmadmin")).getDmAdministrationPort();
			client.setTopology(conf.getTopology());
		}
		
		observer.waitForDomains(domains);
		System.out.println(" - -- Injected");
	}
	
	public void shareAbstractTopology() {
		AbstractTopologyManager absMan = new AbstractTopologyManager();
		
		System.out.println();
		
		List<Link> links = absMan.getAbstractLinks(domains);
		
		absMan.sendLinksToIDM(domains, links);
	}
	
	public void createEnvironment() {
		copyDistributions();
		writeConfigurationFiles();
		createDatabasesSchema();
		
		startInstances();
		
		injectIntradomainTopology();
		shareAbstractTopology();
		
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stopInstances();
		
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		saveEnvironmentFiles();
	}
	
	private void cleanup() {
		if(observer != null)
			observer.stop();
	}
	
	class DirComparator implements Comparator<String> {

		public int compare(String o1, String o2) {
			int num1 = Integer.valueOf(o1.replaceAll("[^\\d]", ""));
			int num2 = Integer.valueOf(o2.replaceAll("[^\\d]", ""));
			
			return num1 - num2;
		}
		
	}
	
	private void saveEnvironmentFiles() {
		File rootdir = new File("src/test/resources/local");

		File dir = new File(rootdir, props.getProperty("environment.name"));
		if (!dir.exists())
			dir.mkdir();

		try {
			InputStream is = this.getClass().getResourceAsStream("/local/local-env.properties");
			OutputStream out = new FileOutputStream(new File(dir, "test-env.properties"));
			
			DomainConfiguration.copy(is, out);
			out.close();
			is.close();
		} catch (IOException e) {
			System.out.println("Problem when copying the properties. "
					+ e.getMessage());
		}
		
		System.out.println("New testing environment has been created. " +
				"The settings files are located in: " + dir);
	}
	
	public static void main(String[] args) throws IOException {
		
		MyProperties props = null;
		for(int i = 0; i < args.length; i++) {
			if("--config-file".equals(args[i])) {
				File file = new File(args[i + 1]);
				props = new MyProperties(file);
				break;
			}
		}
		
		// Load the default configuration from the classpath
		// For creating a new environment from scratch
		if(props == null) {
			InputStream is = EnvironmentSetup.class.getResourceAsStream("/local/local-env.properties");
			props = new MyProperties(is);
		}
		
		EnvironmentSetup env = new EnvironmentSetup(props);
		
		env.prepareConfiguration();
		
		if("--create-environment".equals(args[0])) {
			System.out.println("Creating new environment: ");
			env.createEnvironment();
			env.cleanup();
			System.exit(0);
		} else if("--create-databases".equals(args[0])) {
			env.createDatabasesSchema();
		} else if("--drop-databases".equals(args[0])) {
			env.dropDatabases();
		} else if("--clean-databases".equals(args[0])) {
			env.cleanDatabases();
		} else if("--start-instances".equals(args[0])) {
			env.startInstances();
		} else if("--stop-instances".equals(args[0])) {
			env.stopInstances();
		} else if("--intradomain-topology".equals(args[0])) {
			env.injectIntradomainTopology();
		} else if("--interdomain-topology".equals(args[0])) {
			env.shareAbstractTopology();
		}
	
		env.cleanup();
	}
	
}
