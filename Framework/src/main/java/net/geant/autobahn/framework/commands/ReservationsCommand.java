/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

/**
 * @author jacek
 *
 */
public class ReservationsCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		StringBuffer sb = new StringBuffer();
		
		List<ReservationType> reservations = new ArrayList<ReservationType>();

		int min = 0;
		int max = 100;
		String filter = "all";
		
		if(args.length > 1) {
			if("running".equals(args[1])) {
				min = 0;
				max = HomeDomainState.FINISHED.getCode();
			} else if("active".equals(args[1])) {
				min = HomeDomainState.ACTIVE.getCode();
				max = HomeDomainState.ACTIVE.getCode();
			}
			
			filter = args[1];
		}
		
		for(ServiceType srv : autobahn.getIdm().getServices()) {
			for(ReservationType r : srv.getReservations()) {
				if(r.getState() >= min && r.getState() < max)
					reservations.add(r);
			}
		}
		
		sb.append("Reservations [showing " + filter + "] (" + reservations.size() + "):\n");
		for(ReservationType r : reservations) {
			sb.append("\t" + r.getInfo() + "\n");
			sb.append("\n");
		}
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Prints reservations submitted to the domain.";
	}
}
