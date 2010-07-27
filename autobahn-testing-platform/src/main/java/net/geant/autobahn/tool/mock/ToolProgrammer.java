package net.geant.autobahn.tool.mock;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://mock.tool.autobahn.geant.net/", name = "ToolProgrammer")
public interface ToolProgrammer {

	/**
	 * 
	 * @param behaviour
	 */
	void setAddBehaviour(@WebParam(name="behaviour")ToolBehaviour behaviour);
	
	/**
	 * 
	 * @param behaviour
	 */
	void setRemoveBehaviour(@WebParam(name="behaviour")ToolBehaviour behaviour);
}
