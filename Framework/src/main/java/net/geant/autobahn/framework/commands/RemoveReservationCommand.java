/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class RemoveReservationCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Removes reservation from DM and Tool";
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		if("tool".equals(args[1])) {
			String resID = args[2].replaceAll(":", "_");
			
			try {
				autobahn.getDm().getDomainManager().getTool().removeReservation(resID, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "Reservation " + args[2] + " removed";
		}
		
		autobahn.getDm().removeReservation(args[1]);
		
		return "Reservation " + args[1] + " removed";
	}

}
