/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.useraccesspoint.PortType;

/**
 * @author jacek
 *
 */
public class ClientPortsCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		
		List<PortType> ports = null;
		
		if(args.length > 1) {
			if("all".equalsIgnoreCase(args[1]))
				ports = autobahn.getIdm().getAllClientPorts();
			else if("domain".equalsIgnoreCase(args[1]))
				ports = autobahn.getIdm().getDomainClientPorts();
			else {
			    return "Error in command";
			}
		} else {
			// Default behaviour
			ports = autobahn.getIdm().getDomainClientPorts();
		}

		if (ports == null) {
		    return "No ports found";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Client ports, found: " + ports.size() + "\n");
		for(PortType port : ports) {
			sb.append("- " + port.getFriendlyName() + "\n");
		}
		
		return sb.toString();
	}

	public String commandInfo() {
		return "Displays client ports.\n" +
				"\t'clientports all' to display all client ports in the system\n" +
				"\t'clientports domain' to show only client ports connected to this domain.";
	}
}
