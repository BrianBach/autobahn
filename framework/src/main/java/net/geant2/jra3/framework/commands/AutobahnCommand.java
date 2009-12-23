/**
 * 
 */
package net.geant2.jra3.framework.commands;

import net.geant2.jra3.framework.Framework;

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
