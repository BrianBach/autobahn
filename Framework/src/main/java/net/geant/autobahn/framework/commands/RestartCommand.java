/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class RestartCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		autobahn.getCalendar().dispose();
		autobahn.getTopologyAbstraction().dispose();
	    autobahn.getDm().dispose();
		
		try {
			autobahn.getDm().init();
		} catch(Exception e) {
			return "Error while DM init: " + e.getMessage();
		}

		autobahn.getIdm().dispose();
		autobahn.startIdm();
		
		return "Restart completed.";
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Restarts DM and IDM.";
	}
}
