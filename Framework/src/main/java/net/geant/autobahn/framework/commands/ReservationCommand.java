/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class ReservationCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		if(args.length < 2)
			return "You have to specify reservation id!";
		
		ReservationType r = autobahn.getIdm().getReservation(args[1]);
		
		if(r == null)
			return "Reservation <" + args[1] + "> not found";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(r.getInfo() + "\n");
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Prints information about specified reservation.";
	}
}
