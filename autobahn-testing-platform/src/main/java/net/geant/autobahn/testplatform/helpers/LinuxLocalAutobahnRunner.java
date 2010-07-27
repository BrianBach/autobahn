package net.geant.autobahn.testplatform.helpers;

public class LinuxLocalAutobahnRunner extends LocalAutobahnRunner {

	@Override
	public String getClasspath(String prefix) {
		String[] libs = getLibraries(prefix);
		
		String cp = ".:autobahn-framework.jar";
		for(String lib : libs) {
			cp += ":lib/" + lib; 
		}
		return cp;
	}

	@Override
	public String getJavaExecutableCmd() {
		return "java";
	}

}
