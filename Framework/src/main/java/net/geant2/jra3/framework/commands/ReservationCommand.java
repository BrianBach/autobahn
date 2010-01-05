/**
 * 
 */
package net.geant2.jra3.framework.commands;

import net.geant2.jra3.framework.Framework;
import net.geant2.jra3.reservation.Reservation;

/**
 * @author jacek
 *
 */
public class ReservationCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#execute(net.geant2.jra3.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		if(args.length < 2)
			return "You have to specify reservation id!";
		
		Reservation r = autobahn.getIdm().getReservation(args[1]);
		
		if(r == null)
			return "Reservation <" + args[1] + "> not found";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(r.getInfo() + "\n");
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Prints information about specified reservation.";
	}
}
