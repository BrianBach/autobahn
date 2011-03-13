/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.util.Map;

import net.geant.autobahn.converter.GenericTopologyConverter.NeighborStatus;
import net.geant.autobahn.framework.Framework;

/**
 * @author Jacek
 *
 */
public class NeighborsCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	@Override
	public String execute(Framework autobahn, String[] args) {
		Map<String, NeighborStatus> neighbors = autobahn
				.getTopologyAbstraction().getConverter()
				.getNeighborsStatus();
		
		StringBuffer sb = new StringBuffer();
		for(String k : neighbors.keySet()) {
			sb.append(k + " - " + neighbors.get(k) + "\n");
		}
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	@Override
	public String commandInfo() {
		return "Presents information on the neighboring domains.";
	}

}
