package net.geant.autobahn.converter.ethernet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.geant.autobahn.converter.GenericTopologyConverter;
import net.geant.autobahn.converter.InternalIdentifiersSource;
import net.geant.autobahn.converter.PublicIdentifiersMapping;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;

/**
 * Topology converter for ethernet domains.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetTopologyConverter extends GenericTopologyConverter {

    private final Logger log = Logger.getLogger(EthernetTopologyConverter.class);
    
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
	 * @param lookuphost URL of lookup service
	 */
	public EthernetTopologyConverter(IntradomainTopology topology, IntradomainPathfinder pathfinder, 
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping, String lookuphost) {
		super(pathfinder, internal, mapping, lookuphost);
		
        if (topology == null) {
            log.error("Ethernet Topology is null, can not initialize abstraction");
            return;
        }
        
		sptrees = topology.getSpanningTrees();

		genericLinks = new ArrayList<GenericLink>();

		if(sptrees != null){
			for(SpanningTree st: sptrees) {
                GenericLink gl = st.getEthLink().getGenericLink();
                if (!genericLinks.contains(gl)) {
                    genericLinks.add(st.getEthLink().getGenericLink());
                }
			}
		}
		
		nodes = topology.getNodes();
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
			InternalIdentifiersSource internal, PublicIdentifiersMapping mapping) {
		super(pathfinder, internal, mapping);
		
        if (topology == null) {
            log.error("Ethernet Topology is null, can not initialize abstraction");
            return;
        }
        
		sptrees = topology.getSpanningTrees();

		genericLinks = new ArrayList<GenericLink>();

		if(sptrees != null){
			for(SpanningTree st: sptrees) {
                GenericLink gl = st.getEthLink().getGenericLink();
                if (!genericLinks.contains(gl)) {
                    genericLinks.add(gl);
                }
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
    	
        if (strees != null){
            for (SpanningTree st : strees) {
                GenericLink gl = st.getEthLink().getGenericLink();
                if (!genericLinks.contains(gl)) {
                    genericLinks.add(gl);
                }
            }
        }
    }

}
