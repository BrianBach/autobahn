package net.geant.autobahn.intradomain.ethernet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.GraphEdge;
import net.geant.autobahn.intradomain.pathfinder.GraphNode;
import net.geant.autobahn.intradomain.pathfinder.GraphSearch;

/**
 * Implementation of the intradomain pathfinder for ethernet domains.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetIntradomainPathfinder extends GenericIntradomainPathfinder {

    private List<SpanningTree> all_sptrees = new ArrayList<SpanningTree>();
    private List<Node> all_nodes = new ArrayList<Node>();

    private static final Logger log = Logger.getLogger(EthernetIntradomainPathfinder.class);
    private final int defaultEthernetMtu = 1514; 
    
    /**
     * Initialize object with given topology.
     * 
     * @param topology
     */
    public EthernetIntradomainPathfinder(IntradomainTopology topology) {
        this.all_nodes = topology.getNodes();
        this.all_sptrees = topology.getSpanningTrees();
    }
	
	/**
	 * Creates instance with the given network devices.
	 * 
	 * @param all_sptrees List of all spanning tree objects
	 * @param all_nodes of all nodes in the domain
	 */
    public EthernetIntradomainPathfinder(List<SpanningTree> all_sptrees,
			List<Node> all_nodes) {
		this.all_sptrees = all_sptrees;
		this.all_nodes = all_nodes;
    }
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder#initGraph(java.util.Collection)
	 */
	@Override
	public GraphSearch initGraph(Collection<GenericLink> excluded, int userVlanId, int mtu) {
		
		List<Node> nodes = all_nodes;
		List<SpanningTree> sptrees = all_sptrees;
		
    	grnodes = new HashMap<Node, GraphNode>();
        for (Node n : nodes) {
        	grnodes.put(n, new GraphNode(n));
        }

    	gredges.clear();
        
        // Determine neighbors of each node
        for (SpanningTree st : sptrees) {
            
            // VLANs range from 1-4095, so a zero or negative value means that
            // the user did not use the VLAN option when creating the reservation
            //TODO: Verify (and document) that VLAN 0 would never be selected by the user
            if (userVlanId > 0) {
                log.debug("User has requested VLAN " + userVlanId 
                        + ", checking if ethernet link supports it...");
                // Skip link that does not adhere to user-required VLAN.
                if (userVlanId < st.getVlan().getLowNumber() || 
                        userVlanId > st.getVlan().getHighNumber()) {
                    log.debug("Link " + st.getEthLink() + " rejected.");
                    continue;
                }
            }
            
            if (mtu > 0){
                log.debug("User has requested Mtu size " + mtu + ", checking if" +
                        " ethernet link supports it...");
                
                int linkMtu = st.getEthLink().getGenericLink().getStartInterface().getMtu();
                
                if (linkMtu<=0) {
                    linkMtu = this.defaultEthernetMtu;
                }
                
                if (mtu > linkMtu){  
                    log.debug("Link " + st.getEthLink() + " rejected.");
                    continue;
                }
                
                linkMtu = st.getEthLink().getGenericLink().getEndInterface().getMtu();
                
                if (linkMtu<=0) {
                    linkMtu = this.defaultEthernetMtu;
                }
                
                if (mtu > linkMtu){
                    log.debug("Link " + st.getEthLink() + " rejected.");
                    continue;
                }
            }
            GenericLink link = st.getEthLink().getGenericLink();
            
            // Skip excluded generic links
            if(excluded != null && excluded.contains(link))
            	continue;
            
            Node s = link.getStartInterface().getNode();
            Node e = link.getEndInterface().getNode();
            
            GraphNode sgr = grnodes.get(s);
            GraphNode egr = grnodes.get(e);

			RangeConstraint rcon = new RangeConstraint((int)st.getVlan()
					.getLowNumber(), (int)st.getVlan().getHighNumber());
			PathConstraints pcon = new PathConstraints();
			pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
			
			//mtu info added
            MinValueConstraint mcon = null;
            if ((st.getEthLink().getGenericLink().getStartInterface().getMtu() != 0) && (st.getEthLink().getGenericLink().getEndInterface().getMtu()!= 0)){
                if (st.getEthLink().getGenericLink().getStartInterface().getMtu() < st.getEthLink().getGenericLink().getEndInterface().getMtu()){
                    mcon = new MinValueConstraint((double)st.getEthLink().getGenericLink().getStartInterface().getMtu());
                } else {
                    mcon = new MinValueConstraint((double)st.getEthLink().getGenericLink().getEndInterface().getMtu());
                }
                pcon.addMinValueConstraint(ConstraintsNames.MTU, mcon);
            }
			
			GraphEdge edge = new GraphEdge(sgr, egr, link, pcon);
            sgr.addEdge(edge);
            
            GraphEdge invEdge = new GraphEdge(egr, sgr, link, pcon);
            egr.addEdge(invEdge);
            
            if(link.isInterdomain()) {
            	gredges.put(link, edge);
            }
            
            if(link.getStartInterface().getDomainId() != null) {
            	sgr.setEdgeNode(true);
            }

            if(link.getEndInterface().getDomainId() != null) {
            	egr.setEdgeNode(true);
            }
        }

        // Add nodes to graph
        GraphSearch grSearch = new GraphSearch();
        
        for (GraphNode gn : grnodes.values()) { 
            grSearch.addGraphNode(gn);
        }
        
        return grSearch;
	}
}
