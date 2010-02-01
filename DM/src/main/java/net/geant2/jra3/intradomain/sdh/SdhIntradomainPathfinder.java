/**
 * 
 */
package net.geant2.jra3.intradomain.sdh;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.geant2.jra3.constraints.ConstraintsNames;
import net.geant2.jra3.constraints.MinValueConstraint;
import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.pathfinder.GenericIntradomainPathfinder;
import net.geant2.jra3.intradomain.pathfinder.GraphEdge;
import net.geant2.jra3.intradomain.pathfinder.GraphNode;
import net.geant2.jra3.intradomain.pathfinder.GraphSearch;

/**
 * @author jacek
 *
 */
public class SdhIntradomainPathfinder extends GenericIntradomainPathfinder {

	private List<StmLink> all_links = null;
	private List<SdhDevice> all_devices = null;
	
	
    public SdhIntradomainPathfinder(IntradomainTopology topology) {
        this.all_links = topology.getStmLinks();
        this.all_devices = topology.getSdhDevices();
    }

	public SdhIntradomainPathfinder(List<StmLink> all_links,
			List<SdhDevice> all_devices) {
		super();
		this.all_links = all_links;
		this.all_devices = all_devices;
	}


	@Override
	public GraphSearch initGraph(Collection<GenericLink> excluded, int userVlanId) {
	    // userVlanId parameter is ingored, since an SDH domain does not support this
		
    	grnodes = new HashMap<Node, GraphNode>();
    	
        for (SdhDevice device : all_devices) {
        	Node n = device.getNode();
        	grnodes.put(n, new GraphNode(n));
        }

    	gredges.clear();
        
        // Determine neighbors of each node
        for (StmLink stmLink : all_links) {
            GenericLink link = stmLink.getStmLink();
            
            // Skip excluded generic links
            if(excluded != null && excluded.contains(link))
            	continue;
            
            Node s = link.getStartInterface().getNode();
            Node e = link.getEndInterface().getNode();
            
            GraphNode sgr = grnodes.get(s);
            GraphNode egr = grnodes.get(e);

            // For sdh empty constraints
			PathConstraints pcon = new PathConstraints();
			
			double ts_num = Math.ceil((double)link.getCapacity() / 150336000.0);
			pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(ts_num));
			
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
