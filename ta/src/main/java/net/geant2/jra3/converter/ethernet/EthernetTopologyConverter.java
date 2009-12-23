package net.geant2.jra3.converter.ethernet;

import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.converter.GenericTopologyConverter;
import net.geant2.jra3.converter.InternalIdentifiersSource;
import net.geant2.jra3.converter.PublicIdentifiersMapping;
import net.geant2.jra3.intradomain.pathfinder.IntradomainPathfinder;

/**
 * Topology converter for ethernet domains.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetTopologyConverter extends GenericTopologyConverter {

	private List<SpanningTree> sptrees = null;
	
	/**
	 * Creates an instance
	 * 
	 * @param pathfinder Pathfinder instance
	 * @param internal Internal identifiers source
	 * @param mapping Public ports names mapping
	 */
	public EthernetTopologyConverter(IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal,	PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
	}

	/**
	 * Creates an instance.
	 * 
	 * @param topology Network topology
	 * @param pathfinder Pathfinder instance
	 * @param internal Internal identifiers source
	 * @param mapping Public ports names mapping
	 */
	public EthernetTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
		
		sptrees = topology.getSpanningTrees();

		genericLinks = new ArrayList<GenericLink>();

		if(sptrees != null){
			for(SpanningTree st: sptrees) {
				genericLinks.add(st.getEthLink().getGenericLink());
			}
		}
		
		nodes = topology.getNodes();
	}

    /**
     * Injects the ethernet topology.
     * 
     * @param strees List of spanning tree object
     * @param nodes List od nodes
     */
    public void setTopology(List<SpanningTree> strees, List<Node> nodes) {
    	this.nodes = nodes;
    	genericLinks = new ArrayList<GenericLink>();
    	
    	for(SpanningTree st: strees) {
    		genericLinks.add(st.getEthLink().getGenericLink());
    	}
    }

}
