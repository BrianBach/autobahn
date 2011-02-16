package net.geant.autobahn.tool.mock;

import javax.jws.WebService;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.tool.RequestException;
import net.geant.autobahn.tool.ReservationNotFoundException;
import net.geant.autobahn.tool.ResourceNotFoundException;
import net.geant.autobahn.tool.SystemException;
import net.geant.autobahn.tool.Tool;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;

@WebService(name = "Tool", serviceName = "ToolService",
        portName = "ToolPort",
        targetNamespace = "http://tool.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.tool.Tool")
public class ProgrammableTool implements Tool {

	private Tool tool;
	private ToolBehaviour addBehaviour;
	private ToolBehaviour remBehaviour;
	private String identifier;
	
	public ProgrammableTool(Tool tool, String id) {
		this.tool = tool;
		this.identifier = id;
		
		addBehaviour = new ToolBehaviour(Result.OK, 10);
		
		remBehaviour = new ToolBehaviour(Result.OK, 3);
	}
	
	public void setAddBehavior(ToolBehaviour beh) {
		System.out.println("Changing add behaviour: " + beh.getDelay());
		this.addBehaviour = beh;
	}
	
	public void setRemoveBehaviour(ToolBehaviour beh) {
		this.remBehaviour = beh;
	}
	
	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, SystemException, RequestException, ResourceNotFoundException {

		System.out.println("Add reservation: " + identifier);
		tool.addReservation(resID, path, params);
		produceResult(addBehaviour);
	}

	public void removeReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, SystemException, ReservationNotFoundException, RequestException {
		
		System.out.println("Add reservation: " + identifier);
		tool.removeReservation(resID, path, params);
		produceResult(remBehaviour);
	}

	private void produceResult(ToolBehaviour behaviour) throws RequestException, SystemException {
		try {
			Thread.sleep(behaviour.getDelay() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		switch(behaviour.getResult()) {
			case OK:
				return;
			case REQ_EX:
				throw new RequestException("Expected request exception");
			case SYS_EX:
				throw new SystemException("Expected system exception");
		}
	}
}
