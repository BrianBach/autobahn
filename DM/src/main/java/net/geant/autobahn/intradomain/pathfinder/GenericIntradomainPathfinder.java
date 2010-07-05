package net.geant.autobahn.intradomain.pathfinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;

/**
 * Base implementation of the intradomain pathfinder.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class GenericIntradomainPathfinder implements
		IntradomainPathfinder {

    private static final Logger log = Logger.getLogger(GenericIntradomainPathfinder.class);
    
	protected Map<GenericLink, GraphEdge> gredges = new HashMap<GenericLink, GraphEdge>();
	protected Map<Node, GraphNode> grnodes = new HashMap<Node, GraphNode>();
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder#findPaths(net.geant.autobahn.intradomain.common.GenericLink, net.geant.autobahn.intradomain.common.GenericLink, long, java.util.Collection, int)
	 */
	public List<IntradomainPath> findPaths(GenericLink src, GenericLink dest,
			long capacity, Collection<GenericLink> excluded, int limit,
			int userVlanId) {
		
		return findPaths(src, dest, capacity, null, excluded, limit, userVlanId);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder#findPaths(net.geant.autobahn.intradomain.common.Node, net.geant.autobahn.intradomain.common.Node, java.util.Collection, int)
	 */
	public List<IntradomainPath> findPaths(Node start, Node dest,
			Collection<GenericLink> excluded, int limit) {
		
		GraphSearch graph = initGraph(excluded, 0);
		
		GraphNode gr_start = grnodes.get(start);
		GraphNode gr_dest = grnodes.get(dest);
		
		List<GraphEdge[]> paths = graph.findPaths(gr_start, gr_dest, limit);
		
		List<IntradomainPath> res = new ArrayList<IntradomainPath>();
		
		for(GraphEdge[] path : paths) {
			IntradomainPath ipath = createIntradomainPath(path);

			if(ipath != null) {
				res.add(ipath);
			}
		}

		return res;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder#findPath(net.geant.autobahn.intradomain.common.GenericLink, net.geant.autobahn.intradomain.common.GenericLink, long, net.geant.autobahn.constraints.PathConstraints, java.util.Collection)
	 */
	public IntradomainPath findPath(GenericLink src, GenericLink dest,
			long capacity, PathConstraints pcon, Collection<GenericLink> excluded,
			int userVlanId) {
		
		List<IntradomainPath> res = findPaths(src, dest, capacity, pcon, excluded, 3, userVlanId);
		
		if(res == null || res.size() < 1)
			return null;
		
		return res.get(0);
	}

	private List<IntradomainPath> findPaths(GenericLink src, GenericLink dest,
			long capacity, PathConstraints pcon,
			Collection<GenericLink> excluded, int limit,
			int userVlanId) {
		GraphSearch graph = initGraph(excluded, userVlanId);

		GraphEdge start = gredges.get(src);
		GraphEdge end = gredges.get(dest);

		if(start == null || end == null) {
			// No edges found
			return null;
		}
		
		List<GraphEdge[]> paths = graph.findPaths(start, end, capacity, pcon, limit);
		
		// Sort results by number of links
		List<IntradomainPath> res = new ArrayList<IntradomainPath>();
		
		for(GraphEdge[] path : paths) {
			IntradomainPath ipath = createIntradomainPath(start, path, end);

			if(ipath != null) {
				res.add(ipath);
			}
		}
		
		Collections.sort(res);
		
		return res;
	}
	
	private IntradomainPath createIntradomainPath(GraphEdge start, GraphEdge[] path, GraphEdge end) {
		GraphEdge[] completePath = new GraphEdge[path.length + 2];

		completePath[0] = start;
		for(int i = 0; i < path.length; i++) {
			completePath[i + 1] = path[i];
		}
		completePath[completePath.length - 1] = end;
		
		return createIntradomainPath(completePath);
	}
	
	private IntradomainPath createIntradomainPath(GraphEdge[] path) {
		if(path.length < 1) {
			log.info("Wrong path!");
		}
		
		long capacity = Long.MAX_VALUE;
		
		PathConstraints pcon = new PathConstraints();

		IntradomainPath ipath = new IntradomainPath();
		
		for(GraphEdge edge : path) {
			pcon = pcon.intersect(edge.getConstraints());
			
			if(pcon == null) {
				// Constraints not agreed
				return null;
			}

			ipath.addGenericLink(edge.getLink(), pcon);
			
			capacity = Math.min(capacity, edge.getCapacity());
		}
		
		ipath.setCapacity(capacity);
		
		return ipath;
	}

	/**
	 * Initialize a graph to search in. Graph represents the whole network
	 * topology of the domain, while some links can be excluded from the graph.
	 * 
	 * @param excluded List of links not to include in the graph
	 * @param userVlanId User-required VLAN for the reservation (0 if not supplied)
	 * @return Graph to be searched
	 */
	public abstract GraphSearch initGraph(Collection<GenericLink> excluded, int userVlanId);
}
