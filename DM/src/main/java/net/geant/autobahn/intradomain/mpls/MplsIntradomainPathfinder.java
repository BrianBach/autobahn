/**
 * 
 */
package net.geant.autobahn.intradomain.mpls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.GraphEdge;
import net.geant.autobahn.intradomain.pathfinder.GraphNode;
import net.geant.autobahn.intradomain.pathfinder.GraphSearch;

import org.apache.log4j.Logger;

/**
 * Calculates paths within mpls domains 
 * @author PCSS
 */
public class MplsIntradomainPathfinder extends GenericIntradomainPathfinder {

	private static final Logger log = Logger.getLogger(MplsIntradomainPathfinder.class);
	
	public static final int MPLS_MTU = 1504; // depends on services deployed within the network 
    private List<MplsLink> all_links = new ArrayList<MplsLink>();
    private List<Node> all_nodes = new ArrayList<Node>();
    	
	public MplsIntradomainPathfinder(IntradomainTopology topology) { 

		this.all_links = topology.getMplsLinks();
		this.all_nodes = topology.getNodes();
	}
	
	public MplsIntradomainPathfinder(List<MplsLink> links, List<Node> nodes) { 

		this.all_links = links;
		this.all_nodes = nodes;
	}
	
	@Override
	public GraphSearch initGraph(Collection<GenericLink> excluded, int mtu) {
		
		if (all_nodes == null || all_nodes.size() == 0) {
			
			log.debug("could not prepare graph due to lack of nodes");
			return null;
		}
			
		// put all nodes into grnodes
		for (Node n : all_nodes)
			grnodes.put(n, new GraphNode(n));
		
		for (MplsLink ml : all_links) {
						
			GenericLink gl = ml.getGenericLink();
			
			// do not take into account the excluded links
			if (excluded == null || excluded.contains(gl))
				continue;
			
			int startMtu = gl.getStartInterface().getMtu();
			int endMtu = gl.getEndInterface().getMtu();
			
			if (mtu > 0) {
				
				log.debug("checking requested mtu " + mtu + " for link " + gl);
				
				if ((startMtu < 0 && mtu > MPLS_MTU) || (mtu > startMtu)) {
					log.debug("Link " + gl + " rejected");
					continue;
				}
				
				if ((endMtu < 0 && mtu > MPLS_MTU) || (mtu > endMtu)) {
					log.debug("Link " + gl + " rejected");
					continue;
				}
			}
			
			PathConstraints constraints = new PathConstraints();
			
			if (gl.getStartInterface().getMtu() != 0 && gl.getEndInterface().getMtu() != 0)				
				constraints.addMinValueConstraint(ConstraintsNames.MTU, new MinValueConstraint((double)(startMtu < endMtu ? startMtu : endMtu)));
			
			// TODO add vlan info
						
			GraphNode start = grnodes.get(gl.getStartInterface().getNode());
            GraphNode end = grnodes.get(gl.getEndInterface().getNode());
            
        	GraphEdge edge = new GraphEdge(start, end, gl, constraints);
            start.addEdge(edge);
            
            GraphEdge edge1 = new GraphEdge(end, start, gl, constraints);
            end.addEdge(edge1);
            
            if (gl.isInterdomain()) 
            	gredges.put(gl, edge);
            
            if (gl.getStartInterface().getDomainId() != null) 
            	start.setEdgeNode(true);

            if (gl.getEndInterface().getDomainId() != null) 
            	end.setEdgeNode(true);
		}
		
        GraphSearch grSearch = new GraphSearch();
        
        for (GraphNode gn : grnodes.values())  
            grSearch.addGraphNode(gn);
        
        return grSearch;
	}

	@Override
	public void settleConstraintsValuesForPath(IntradomainPath path) {
		//TODO impl later
	}
}
