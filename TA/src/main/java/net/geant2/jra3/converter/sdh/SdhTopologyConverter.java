/**
 * 
 */
package net.geant2.jra3.converter.sdh;

import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.converter.GenericTopologyConverter;
import net.geant2.jra3.converter.InternalIdentifiersSource;
import net.geant2.jra3.converter.PublicIdentifiersMapping;
import net.geant2.jra3.intradomain.pathfinder.IntradomainPathfinder;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.StmLink;

/**
 * @author jacek
 *
 */
public class SdhTopologyConverter extends GenericTopologyConverter {

	public SdhTopologyConverter(IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
	}
	
	public SdhTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder,
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
		
		genericLinks = new ArrayList<GenericLink>();
		for(StmLink link : topology.getStmLinks()) {
			genericLinks.add(link.getStmLink());
		}

		nodes = new ArrayList<Node>();
    	for(SdhDevice device : topology.getSdhDevices()) {
    		nodes.add(device.getNode());
    	}
	}
	
    public void setTopology(List<StmLink> stmLinks, List<SdhDevice> devices) {
    	genericLinks = new ArrayList<GenericLink>();
    	
    	for(StmLink link: stmLinks) {
    		genericLinks.add(link.getStmLink());
    	}
    	
    	nodes = new ArrayList<Node>();
    	
    	for(SdhDevice device : devices) {
    		nodes.add(device.getNode());
    	}
    }
}
