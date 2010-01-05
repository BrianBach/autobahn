/**
 * 
 */
package net.geant2.jra3.framework.commands;

import java.util.Calendar;

import net.geant2.jra3.framework.Framework;

/**
 * @author jacek
 *
 */
public class UptimeCommand implements AutobahnCommand {

	private Calendar start = Calendar.getInstance();
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#execute(net.geant2.jra3.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		Calendar now = Calendar.getInstance();

		long diff = now.getTimeInMillis() - start.getTimeInMillis();
		
		long msec = diff % 1000;
		long sec = (diff / 1000) % 60;
		long mins = (diff / 1000 / 60) % 60;
		long hours = (diff / 1000 / 60 / 60) % 24;
		long days = (diff / 1000 / 60 / 60 / 24);
		
		return String.format("%d days, %02dh %02dm %02ds %02dmsec", days, hours, mins, sec, msec);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.framework.commands.AutobahnCommand#commandInfo()
	 */
	public String commandInfo() {
		return "Informs how long the server is running";
	}
}
