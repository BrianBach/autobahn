/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class QuitCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework)
	 */
	public String execute(Framework autobahn, String[] args) {
		
		autobahn.stop(false);
		
		return "Bye!";
	}

	public String commandInfo() {
		return "Quits telnet session.";
	}
}
