/**
 * 
 */
package net.geant.autobahn.framework.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.idcp.OscarsClient;
import net.geant.autobahn.network.Link;


/**
 * Calls idcp client and pulls topology
 * @author Michal
 *
 */
public class IdcpTopologyCommand implements AutobahnCommand {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant.autobahn.framework.Framework, java.lang.String[])
	 */
	@Override
	public String execute(Framework autobahn, String[] args) {
	
		OscarsClient oscars = new OscarsClient(args[1]);
		try {
			List<Link> links = oscars.getTopology();
			
			StringBuffer sb = new StringBuffer();
			for (Link link : links)
				sb.append(link + "\r\n");
			
			// also dump content to file
			Writer output = new BufferedWriter(new FileWriter("idcp.txt"));
			try {
				output.write(sb.toString());
			} finally {
				output.close();
			}
			return sb.toString();
			
		} catch (Exception e) {
			return "An exception occured: " + e.getMessage();
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
	 */
	@Override
	public String commandInfo() {
		
		return "Displays topology pulled from an idcp domain";
	}
}
