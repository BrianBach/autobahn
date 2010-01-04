package net.geant2.jra3.tool;

import java.util.List;

import javax.jws.WebService;

import net.geant2.jra3.aai.AAIException;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.reservation.ReservationParams;

/**
 * @author Michal
 */

@WebService(name = "Tool", serviceName = "ToolService",
        portName = "ToolPort",
        targetNamespace = "http://tool.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.tool.Tool")
public class ToolImpl implements Tool {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant2.jra3.reservation.ReservationParams)
	 */
	public void addReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException, RequestException,
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
		for (GenericLink gl : links) {
			
			System.out.println("gl - " + gl.toString());
		}
		
		System.out.println("addReservation.end");
		
		//throw new SystemException("System not ready");
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.tool.Tool#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException,
			RequestException, SystemException, ReservationNotFoundException {
		
		System.out.println("removeReservation.begin");
		
		System.out.println("ID: " + resID);
		
		System.out.println("removeReservation.end");
	}
}
