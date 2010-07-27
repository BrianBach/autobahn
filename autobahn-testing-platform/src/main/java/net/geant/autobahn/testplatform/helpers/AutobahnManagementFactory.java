package net.geant.autobahn.testplatform.helpers;

import java.io.File;
import java.io.InputStream;

import net.geant.autobahn.testplatform.configuration.EnvironmentSetup;
import net.geant.autobahn.testplatform.configuration.MyProperties;

public class AutobahnManagementFactory {

	private EnvironmentSetup env;
	
	public AutobahnManagementFactory() {
	}
	
	public AutobahnManagementFactory(String path) {
		String branch = System.getProperty("launchEnv", "local");

		MyProperties props = null;
		InputStream is = this.getClass().getResourceAsStream("/" + branch + "/" + path + "/test-env.properties");
		
		if(is != null) {
			props = new MyProperties(is);
		} else {
			// Try to load the remote and change the settings to the local environment 
			// then save it as the local env.
			is = this.getClass().getResourceAsStream("/remote/" + path + "/test-env.properties");
			if(is == null) {
				System.out.println("Configuration file not found. Exitting...");
				return;
			}
			
			System.out.println("Converting the remote environment settings to the local ones...");

			props = new MyProperties(is);

			is = EnvironmentSetup.class
					.getResourceAsStream("/local/local-env.properties");
			MyProperties localProps = new MyProperties(is);

			props.setProperty("default.home", localProps.getProperty("default.home"));
			props.setProperty("default.db-name-prefix", localProps.getProperty("default.db-name-prefix"));
			props.setProperty("default.db-host", localProps.getProperty("default.db-host"));
			props.setProperty("default.db-user", localProps.getProperty("default.db-user"));
			props.setProperty("default.db-pass", localProps.getProperty("default.db-pass"));
			props.setProperty("default.db-port", localProps.getProperty("default.db-port"));

			File dir = new File("src/test/resources/local/" + path);
			if (!dir.exists())
				dir.mkdir();
			File f = new File(dir, "test-env.properties");

			props.save(f);
		}
		
		this.env = new EnvironmentSetup(props);
	}
	
	public AutobahnManagement[] createInstances() {
		
		if(env != null) {
			return env.recreateEnvironment();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param prefix
	 * @param wsPrefix
	 * @return
	 */
	public AutobahnManagement createWindowsInstance(String prefix, String wsPrefix) {
		
		return new AutobahnManagement(prefix, wsPrefix, new WindowsLocalAutobahnRunner());
	}

	/**
	 * 
	 * @param prefix
	 * @param wsPrefix
	 * @return
	 */
	public AutobahnManagement createLinuxInstance(String prefix, String wsPrefix) {

		return new AutobahnManagement(prefix, wsPrefix, new LinuxLocalAutobahnRunner());
	}
}
