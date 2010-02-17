/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class HelpCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		StringBuffer sb = new StringBuffer();
		
		if(args.length > 1) {
			AutobahnCommand cmd = Framework.commands.get(args[1]);
			
			if(cmd != null) {
				return "Command <" + args[1] + "> info:\n" + cmd.commandInfo(); 
			} else {
				return "Command <" + args[1] + "> not found";
			}
		}
		
		sb.append("Autobahn commands:\n");
		
		for(String key : Framework.commands.keySet()) {
			AutobahnCommand cmd = Framework.commands.get(key);
			
			sb.append(key + " - " + cmd.commandInfo());
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public String commandInfo() {
		return "Prints this info";
	}
}
