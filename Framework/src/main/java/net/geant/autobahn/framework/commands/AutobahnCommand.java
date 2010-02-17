/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public interface AutobahnCommand {

	/**
	 * 
	 * @param autobahn
	 * @param args
	 * @return
	 */
	public String execute(Framework autobahn, String[] args);
	
	/**
	 * 
	 * @return
	 */
	public String commandInfo();
}
