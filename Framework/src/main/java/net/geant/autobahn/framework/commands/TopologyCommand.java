/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.network.Link;

/**
 * @author jacek
 *
 */
public class TopologyCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	public String execute(Framework autobahn, String[] args) {
		List<Link> links = autobahn.getIdm().getTopology();
		
		StringBuffer sb = new StringBuffer();
		sb.append("Topology. Number of links: " + links.size() + "\n");
		for(Link l : links) {
			sb.append(l + "\n");
		}
		
		return sb.toString();
	}

	public String commandInfo() {
		return "Displays list of available links of global topology";
	}
}
