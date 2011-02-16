package net.geant.autobahn.tool;

import javax.jws.WebService;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.tool.types.GenericLinkType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;

/**
 * @author Michal
 */

@WebService(name = "Tool", serviceName = "ToolService",
        portName = "ToolPort",
        targetNamespace = "http://tool.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.tool.Tool")
public class ToolImpl implements Tool {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException {
		
		System.out.println("addReservation.begin");
		
		System.out.println("ID: " + resID);
/*		System.out.println("Start: " + params.getStartTime().getTime());
		
		RangeConstraint rcon = params.getPathConstraints().getRangeConstraint(ConstraintsNames.VLANS);
		if(rcon != null)
			System.out.println(rcon);
		else
			System.out.println("RCON NULL !");
*/		
		for (GenericLinkType gl : path.getLinks()) {
			
			System.out.println("gl - " + gl.toString());
		}
		
		System.out.println("addReservation.end");
		
		//throw new SystemException("System not ready");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resID, IntradomainPathType ipath,
			ReservationParamsType params) throws AAIException,
			RequestException, SystemException, ReservationNotFoundException {
		
		System.out.println("removeReservation.begin");
		
		System.out.println("ID: " + resID);
		
		System.out.println("removeReservation.end");
	}
}
