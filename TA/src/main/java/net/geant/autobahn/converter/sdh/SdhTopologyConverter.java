/**
 * 
 */
package net.geant.autobahn.converter.sdh;

import java.util.ArrayList;

import org.apache.log4j.Logger;

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

    private final Logger log = Logger.getLogger(SdhTopologyConverter.class);

    public SdhTopologyConverter(IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
	}
	
	public SdhTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder,
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
		
        if (topology == null) {
            log.error("SDH Topology is null, can not initialize abstraction");
            return;
        }
        
		genericLinks = new ArrayList<GenericLink>();

		if (topology.getStmLinks() != null) {
    		for(StmLink link : topology.getStmLinks()) {
    			genericLinks.add(link.getStmLink());
    		}
		}
        if (topology.getSpanningTrees() != null) {
    		for(SpanningTree st : topology.getSpanningTrees()) {
                GenericLink gl = st.getEthLink().getGenericLink();
                if (!genericLinks.contains(gl)) {
                    genericLinks.add(gl);
                }
    		}
        }
		
		nodes = new ArrayList<Node>();
		if (topology.getSdhDevices() != null) {
        	for(SdhDevice device : topology.getSdhDevices()) {
        		nodes.add(device.getNode());
        	}
		}
        if (topology.getSpanningTrees() != null) {
    		for(SpanningTree st : topology.getSpanningTrees()) {
                Node n = st.getEthLink().getGenericLink().getEndInterface().getNode();
                if (!nodes.contains(n)) {
                    nodes.add(n);
                }
    		}
        }
	}
}
