package net.geant.autobahn.tool.mock;

import net.geant.autobahn.tool.Tool;
import net.geant.autobahn.tool.ToolImpl;

public class MockTool {

	private ProgrammableTool ptool = null;
	private ToolProgrammer toolProgrammer;
	private int port = 0;
	
	private String tServiceName;
	private String pServiceName;
	
	public MockTool(String toolServiceName, String progServiceName) {
		this.tServiceName = toolServiceName;
		this.pServiceName = progServiceName;
		
		ptool = new ProgrammableTool(new ToolImpl(), toolServiceName);
		toolProgrammer = new ToolProgrammerImpl(ptool);
	}
	
	public ToolProgrammer getProgrammer() {
		return toolProgrammer;
	}
	
	public Tool getTool() {
		return ptool;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getToolAddress() {
		if(port == 0)
			throw new IllegalStateException("Mock not connected to any server...");
		
		return "http://localhost:" + port + "/autobahn/" + tServiceName;
	}
	
	public String getProgrammerAddress() {
		if(port == 0)
			throw new IllegalStateException("Mock not connected to any server...");
		
		return "http://localhost:" + port + "/autobahn/" + pServiceName;
	}
}
