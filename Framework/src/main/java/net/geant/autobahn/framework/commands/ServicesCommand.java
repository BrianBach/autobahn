/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Service;

/**
 * @author jacek
 *
 */
public class ServicesCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		List<Service> services = autobahn.getIdm().getServices();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("Services found: " + services.size());
		
		for(Service srv : services) {
			sb.append("Service: " + srv.getBodID() + ", user: " + srv.getUser().getName() + "\n");
			List<AutobahnReservation> reservations = srv.getReservations();
			sb.append("Reservations (" + reservations.size() + "):\n");
			for(AutobahnReservation r : reservations) {
				sb.append("\t" + r.getBodID() + " [" + r.getStartPort() + " - " + r.getEndPort() + "] " +
						"(" + r.getStartTime().getTime() + " - " + r.getEndTime().getTime() + ") " +
						r.getCapacity() + " bps | " + r.getStateObject());
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Lists services.";
	}
}
