/**
 * 
 */
package net.geant2.jra3.framework.commands;

import net.geant2.jra3.framework.Framework;

/**
 * @author jacek
 *
 */
public class QuitCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#execute(net.geant2.jra3.framework.Framework)
	 */
	public String execute(Framework autobahn, String[] args) {
		
		autobahn.stop(false);
		
		return "Bye!";
	}

	public String commandInfo() {
		return "Quits telnet session.";
	}
}
