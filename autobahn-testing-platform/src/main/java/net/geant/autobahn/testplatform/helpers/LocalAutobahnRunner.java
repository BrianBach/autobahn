package net.geant.autobahn.testplatform.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LocalAutobahnRunner {

	class StreamGobbler extends Thread {
		private InputStream is;
		private String type;
		private String domainId;
		
		StreamGobbler(InputStream is, String type, String domainId) {
			this.is = is;
			this.type = type;
			this.domainId = domainId;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.println(domainId + ">" + line);
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	
	public void start(String prefix) {
		autobahnCmd(prefix, "", "start");
	}

	public void stop(String prefix) {
		autobahnCmd(prefix, "", "stop");
	}
	
	public void start(String prefix, String observerAddress, String domainId) {
		autobahnCmd(prefix, domainId, "start", "--startup-notifier", observerAddress);
	}
	
	private void autobahnCmd(String prefix, String domainId, String... cmds) {
		Runtime rt = Runtime.getRuntime();

		String[] command = {
				getJavaExecutableCmd(),
				"-Dcxf.config.file=etc/cxf/cxf.xml",
				"-Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger",
				"-classpath", getClasspath(prefix), "net.geant.autobahn.framework.Framework", 
			};
		
		 List<String> temp = new ArrayList<String>();
		 temp.addAll(Arrays.asList(command));
		 temp.addAll(Arrays.asList(cmds));
		 command = temp.toArray(new String[temp.size()]);
		
		try {
			Process proc = rt.exec(command, null, new File(prefix));
			
            // any error message?
            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR", domainId);            
            
            // any output?
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT", domainId);
                
            // kick them off
            errorGobbler.start();
            outputGobbler.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class JarFilter implements FilenameFilter {
	    public boolean accept(File dir, String name) {
	        return (name.endsWith(".jar"));
	    }
	}

	protected String[] getLibraries(String prefix) {
		File libDir = new File(prefix + "/lib");
		
		if(libDir.exists() && libDir.isDirectory()) {
			String[] list = libDir.list(new JarFilter());
			if(list != null) 
				Arrays.sort(list);
			return list;
		}
		
		return null;
	}
	
	public abstract String getJavaExecutableCmd();
	
	public abstract String getClasspath(String prefix);

}
