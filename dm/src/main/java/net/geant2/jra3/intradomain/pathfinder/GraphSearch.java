/**
 * GraphSearch.java
 * 2007-04-02
 */
package net.geant2.jra3.intradomain.pathfinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import net.geant2.jra3.constraints.PathConstraints;

/**
 * Performs graph path finding between edge nodes (routers)
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class GraphSearch {

    private List<GraphNode> nodes = new ArrayList<GraphNode>();
    private List<GraphNode> edgeNodes = new ArrayList<GraphNode>();
    private List<GraphEdge[]> paths = new ArrayList<GraphEdge[]>();
    
    private int limit = -1;
    private long minCapacity = -1;
    
    public void addGraphNode(GraphNode node) {
        nodes.add(node);
        if (node.isEdgeNode())
            edgeNodes.add(node);
    }
    
    /**
     * Returns number of nodes
     * @return number of nodes
     */
    public int getNumNodes() {
        return nodes.size() - edgeNodes.size();
    }
    
    /**
     * Returns number of edge nodes
     * @return number of edge nodes
     */
    public int getNumEdgeNodes() {
        return edgeNodes.size();
    }

    /**
     * Performs searching of the graph from the given start edge to the dest edge.
     * 
     * @param src Start edge of the graph search
     * @param dest Destination edge of the graph search 
     * @param minCapacity Minimal capacity the the path should quarantee
     * @param pcons Network constraints that the path should agree with
     * @param limit Maximum number of paths found
     * @return List of graph edge arrays
     */
    public List<GraphEdge[]> findPaths(GraphEdge src, GraphEdge dest,
			long minCapacity, PathConstraints pcons, int limit) {

    	paths.clear();
    	this.minCapacity = minCapacity;
    	this.limit = limit;
    	
    	GraphNode src_node = src.getStartNode();
    	GraphNode dst_node = dest.getStartNode();

    	long pathCapacity = Math.min(src.getCapacity(), dest.getCapacity());
    	
    	if(pcons == null) {
    		pcons = new PathConstraints();
    	}
    	pcons = pcons.intersect(src.getConstraints());
    	pcons = pcons.intersect(dest.getConstraints());

    	
    	if(src_node.equals(dst_node) && pathCapacity >= minCapacity) {
    		paths.add(new GraphEdge[] {});
    	} else {
    		search(src_node, dst_node, new Stack<GraphEdge>(), pathCapacity,
					pcons);
    	}
    	
    	return paths;
    }

    /**
     * Performs graph searching of the path from given start node to the dest node. 
     * 
     * @param start Start node of the path
     * @param dest Dest node of the path
     * @param limit Maximum number of found paths
     * @return List of graph edge arrays
     */
    public List<GraphEdge[]> findPaths(GraphNode start, GraphNode dest,
			int limit) {

    	paths.clear();
    	this.limit = limit;
    	
    	search(start, dest, new Stack<GraphEdge>(), 0, new PathConstraints());
    	
    	return paths;
    }
    
    private void search(GraphNode start, GraphNode dest,
			Stack<GraphEdge> currentPath, long pathCapacity,
			PathConstraints pcons) {

    	if(limit == 0)
    		return;
    	
    	for(GraphEdge edge : start.getEdges()) {
    		
    		if(edge.isInterdomain())
    			continue;
    		
    		GraphNode neighbor = edge.getEndNode();
    		
    		if(containsNode(currentPath, neighbor))
    			continue;

    		// Update capacity
    		long tmp = Math.min(pathCapacity, edge.getCapacity());

    		// Cut off - capacity
    		if(tmp < minCapacity)
    			continue;
    		
    		// Update constraints
    		PathConstraints merged = pcons.intersect(edge.getConstraints());

    		// Cut off constarints
    		if(merged == null)
    			continue;

    		if(neighbor.equals(dest)) {
   	    		GraphEdge[] completePath = new GraphEdge[currentPath.size() + 1];
   	    		currentPath.toArray(completePath);
   	    		completePath[currentPath.size()] = edge;

   	    		paths.add(completePath);
   	    		limit = limit - 1;
   	    		continue;
    		}
    		
    		currentPath.add(edge);
    		
    		search(neighbor, dest, currentPath, tmp, merged);
    		
    		currentPath.pop();
    	}
    	
    }
    
	/**
	 * Helper method to check if the collections of nodes contain specified
	 * node.
	 * 
	 * @param path Path in form of graph edges
	 * @param node Node of the graph to be found
	 * @return true if the path contains the node
	 */
    public static boolean containsNode(Collection<GraphEdge> path,
			GraphNode node) {

    	for(GraphEdge e : path) {
    		if(e.getStartNode().equals(node))
    			return true;
    	}
    	
    	return false;
    }
}
