package net.geant.autobahn.tool;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.tool.types.GenericLinkType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;

public class ToolImpl implements Tool {

	public void addReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException,
			SystemException, RequestException,
			ResourceNotFoundException {
		
		System.out.println("addReservation.begin");
		
		System.out.println("ID: " + resID);
		
/*		int vlanIndex = params.getPathConstraintsIngress().getRangeNames().indexOf(ConstraintsNames.VLANS);
		if(vlanIndex > -1) {
			RangeConstraint rcon = params.getPathConstraintsEgress().getRangeConstraints().get(vlanIndex);
		
			if(rcon != null) {
				System.out.println("VLAN to reserve: " + rcon.getRanges().get(0).getMin());
			} else
				System.out.println("RCON NULL !");
		}	*/	
		
		for (GenericLinkType gl : path.getLinks()) {
			System.out.println("gl: " + gl.getStartInterface().getName() + "-" 
					+ gl.getEndInterface().getName());
		}
		
		System.out.println("addReservation.end");
	}

	public void removeReservation(String resID, IntradomainPathType path,
			ReservationParamsType params) throws AAIException, SystemException, 
			ReservationNotFoundException, RequestException {
		
		System.out.println("Tool: Removing res: " + resID);
	}

}
