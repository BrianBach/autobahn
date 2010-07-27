package net.geant.autobahn.tool;

import java.util.List;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.reservation.ReservationParams;

public class ToolImpl implements Tool {

	public void addReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException,
			SystemException, RequestException,
			ResourceNotFoundException {
		
		System.out.println("addReservation.begin");
		
		System.out.println("ID: " + resID);
		
		int vlanIndex = params.getPathConstraints().getRangeNames().indexOf(ConstraintsNames.VLANS);
		if(vlanIndex > -1) {
			RangeConstraint rcon = params.getPathConstraints().getRangeConstraints().get(vlanIndex);
		
			if(rcon != null) {
				System.out.println("VLAN to reserve: " + rcon.getRanges().get(0).getMin());
			} else
				System.out.println("RCON NULL !");
		}		
		
		for (GenericLink gl : links) {
			System.out.println("gl: " + gl.getStartInterface().getName() + "-" 
					+ gl.getEndInterface().getName());
		}
		
		System.out.println("addReservation.end");
	}

	public void removeReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException, SystemException, 
			ReservationNotFoundException, RequestException {
		
		System.out.println("Tool: Removing res: " + resID);
	}

}
