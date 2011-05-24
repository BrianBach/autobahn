/**
 * 
 */
package net.geant.autobahn.converter.sdh;

import java.util.ArrayList;

import net.geant.autobahn.converter.GenericTopologyConverter;
import net.geant.autobahn.converter.InternalIdentifiersSource;
import net.geant.autobahn.converter.PublicIdentifiersMapping;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.StmLink;

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
		for(SpanningTree st : topology.getSpanningTrees()) {
			genericLinks.add(st.getEthLink().getGenericLink());
		}
		
		nodes = new ArrayList<Node>();
    	for(SdhDevice device : topology.getSdhDevices()) {
    		nodes.add(device.getNode());
    	}
		for(SpanningTree st : topology.getSpanningTrees()) {
			nodes.add(st.getEthLink().getGenericLink().getEndInterface().getNode());
		}    	
	}
}
