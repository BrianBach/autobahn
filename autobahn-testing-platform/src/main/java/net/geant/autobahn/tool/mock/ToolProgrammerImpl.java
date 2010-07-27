package net.geant.autobahn.tool.mock;

import javax.jws.WebService;

@WebService(name = "ToolProgrammer", serviceName = "ToolProgrammerService",
        portName = "ToolProgrammerPort",
        targetNamespace = "http://mock.tool.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.tool.mock.ToolProgrammer")
public class ToolProgrammerImpl implements ToolProgrammer {

	private ProgrammableTool tool;
	
	public ToolProgrammerImpl(ProgrammableTool tool) {
		this.tool = tool;
	}
	
	public void setAddBehaviour(ToolBehaviour behaviour) {
		tool.setAddBehavior(behaviour);
	}

	public void setRemoveBehaviour(ToolBehaviour behaviour) {
		tool.setRemoveBehaviour(behaviour);
	}

}
