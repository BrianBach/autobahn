/**
 * 
 */
package net.geant2.jra3.framework.commands;

import net.geant2.jra3.framework.Framework;

/**
 * @author jacek
 *
 */
public class RestartCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#execute(net.geant2.jra3.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
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
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Restarts DM and IDM.";
	}
}
