/**
 * 
 */
package net.geant.autobahn.intradomain.sdh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.constraints.BooleanConstraint;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.EthLink;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.GraphEdge;
import net.geant.autobahn.intradomain.pathfinder.GraphNode;
import net.geant.autobahn.intradomain.pathfinder.GraphSearch;

import org.apache.log4j.Logger;

/**
 * @author jacek
 *
 */
public class SdhIntradomainPathfinder extends GenericIntradomainPathfinder {

	private List<StmLink> all_links = null;
	private List<SdhDevice> all_devices = null;
	
	// A single link may have multiple VLAN ranges (SpanningTree)
    private Map<GenericLink, List<SpanningTree>> strees = null;
	
	private static final Logger log = Logger.getLogger(SdhIntradomainPathfinder.class);
	private final int defaultSdhMtu = 4474;
	
    public SdhIntradomainPathfinder(IntradomainTopology topology) {
        this(topology.getStmLinks(), topology.getSdhDevices(), topology.getSpanningTrees());
    }

	public SdhIntradomainPathfinder(List<StmLink> all_links,
			List<SdhDevice> all_devices, List<SpanningTree> sptrees) {
		super();
		this.all_links = all_links;
		this.all_devices = all_devices;
		
		// Build a map of links with all their associated VLAN ranges (SpanningTree)
        this.strees = new HashMap<GenericLink, List<SpanningTree>>();
        for (SpanningTree st : sptrees) {
            List<SpanningTree> stList = strees.get(st.getEthLink().getGenericLink());
            if (stList != null) {
                stList.add(st);
            } else {
                stList = new ArrayList<SpanningTree>();
                stList.add(st);
                strees.put(st.getEthLink().getGenericLink(), stList);
            }
        }
	}


	@Override
	public GraphSearch initGraph(Collection<GenericLink> excluded, int mtu) {
		
    	grnodes = new HashMap<Node, GraphNode>();
    	
        for (SdhDevice device : all_devices) {
        	Node n = device.getNode();
        	grnodes.put(n, new GraphNode(n));
        }
    	for(GenericLink gl : strees.keySet()) {
    		Node n = gl.getEndInterface().getNode();
        	grnodes.put(n, new GraphNode(n));
    	}
    	
    	gredges.clear();
        
    	List<GenericLink> allLinks = new ArrayList<GenericLink>();
    	for(StmLink stmLink : all_links) {
    		allLinks.add(stmLink.getStmLink());
    	}
    	for(GenericLink gl : strees.keySet()) {
    		allLinks.add(gl);
    	}

        // Determine neighbors of each node
        for (GenericLink link : allLinks) {
            
            // Skip excluded generic links
            if(excluded != null && excluded.contains(link))
            	continue;
            
            Node s = link.getStartInterface().getNode();
            Node e = link.getEndInterface().getNode();
            
            if (mtu > 0){
                log.debug("User has requested Mtu size " + mtu + ", checking if" +
                        " sdh link supports it...");
                if (link.getStartInterface().getMtu() != 0){
                    if(mtu > link.getStartInterface().getMtu()){
                        log.debug("Link " + " rejected.");
                        continue;
                    }
                } else {
                    if(mtu > this.defaultSdhMtu){
                        log.debug("Link " + " rejected.");
                        continue;
                    }
                }
                if (link.getEndInterface().getMtu() != 0){
                    if(mtu > link.getEndInterface().getMtu()){
                        log.debug("Link " + " rejected.");
                        continue;
                    }
                } else {
                    if(mtu > this.defaultSdhMtu){
                        log.debug("Link " + " rejected.");
                        continue;
                    }
                }
            } 
            
            double mtuMin;
            if (link.getStartInterface().getMtu() < link.getEndInterface().getMtu()){
                mtuMin = link.getStartInterface().getMtu();
            } else {
                mtuMin = link.getEndInterface().getMtu();
            }
            
            GraphNode sgr = grnodes.get(s);
            GraphNode egr = grnodes.get(e);

            // For sdh empty constraints
			PathConstraints pcon = new PathConstraints();
			
			if(sgr.getInternalNode().isVlanTranslationSupport() || egr.getInternalNode().isVlanTranslationSupport()) {
				pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, new BooleanConstraint(true, "OR"));
        	} else {
				pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, new BooleanConstraint(false, "OR"));
        	}
			
			double ts_num = Math.ceil((double)link.getCapacity() / GlobalConstraints.ONE_TIMESLOT_CAPACITY);
			pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(ts_num));
			pcon.addMinValueConstraint(ConstraintsNames.MTU, new MinValueConstraint(mtuMin));

			GraphEdge edge = new GraphEdge(sgr, egr, link, pcon);
            sgr.addEdge(edge);
            
            GraphEdge invEdge = new GraphEdge(egr, sgr, link, pcon);
            egr.addEdge(invEdge);
            
            if(link.isInterdomain()) {
            	gredges.put(link, edge);
            	List<SpanningTree> stList = strees.get(link);
            	if (stList == null) {
            	    log.error("SDH intra graph error, no VLAN range for link " + link);
            	} else {
                    RangeConstraint rcon = new RangeConstraint();

                    // Add a VLAN range for each associated SpanningTree
                    for (SpanningTree st : stList) {
                        if(st != null) {
                            rcon.addRange((int) st.getVlan().getLowNumber(), 
                                    (int) st.getVlan().getHighNumber());
                        } else {
                            log.error("VLAN range for link " + link + " was null");
                        }
                    }
                    pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
            	}
            } else {
            	pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("0-4096"));
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
