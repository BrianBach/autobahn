/**
 * 
 */
package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;

/**
 * @author jacek
 *
 */
public class ClientPortsCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		String[] ports = null;
		
		if(args.length > 1) {
			if("all".equals(args[1]))
				ports = autobahn.getIdm().getAllClientPorts();
			else if("domain".equals(args[1]))
				ports = autobahn.getIdm().getDomainClientPorts();
		} else {
			// Default behaviour
			ports = autobahn.getIdm().getDomainClientPorts();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("Client ports, found: " + ports.length + "\n");
		for(String port : ports) {
			sb.append(port + "\n");
		}
		
		return sb.toString();
	}

	public String commandInfo() {
		return "Displays client ports.\n" +
				"\t'clientports all' to display all client ports in the system\n" +
				"\t'clientports domain' to show only client ports connected to this domain.";
	}
}
