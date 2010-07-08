/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.List;

import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.reservation.Reservation;

/**
 * @author jacek
 *
 */
public class ServicesCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		List<ServiceType> services = autobahn.getIdm().getServices();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("Services found: " + services.size());
		
		for(ServiceType srv : services) {
			sb.append("Service: " + srv.getBodID() + ", user: " + srv.getUser().getName() + "\n");
			List<Reservation> reservations = srv.getReservations();
			sb.append("Reservations (" + reservations.size() + "):\n");
			for(Reservation r : reservations) {
				sb.append("\t" + r.getBodID() + " [" + r.getStartPort() + " - " + r.getEndPort() + "] " +
						"(" + r.getStartTime().getTime() + " - " + r.getEndTime().getTime() + ") " +
						r.getCapacity() + " bps | " + r.getState());
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
