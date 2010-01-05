/**
 * 
 */
package net.geant2.jra3.framework.commands;

import net.geant2.jra3.framework.Framework;

/**
 * @author jacek
 *
 */
public class ShutdownCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#execute(net.geant2.jra3.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {

		autobahn.getIdm().dispose();
		autobahn.stop(true);
		
		return "Autobahn is being halted... Bye!";
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Shutdowns Autobahn.";
	}
}
