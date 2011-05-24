package net.geant.autobahn.intradomain.pathfinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;

import org.apache.log4j.Logger;

/**
 * Base implementation of the intradomain pathfinder.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class GenericIntradomainPathfinder implements
		IntradomainPathfinder {

	public static final int PATHS_LIMIT = 100;
	
    private static final Logger log = Logger.getLogger(GenericIntradomainPathfinder.class);
    
	protected Map<GenericLink, GraphEdge> gredges = new HashMap<GenericLink, GraphEdge>();
	protected Map<Node, GraphNode> grnodes = new HashMap<Node, GraphNode>();
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder#findPaths(net.geant.autobahn.intradomain.common.GenericLink, net.geant.autobahn.intradomain.common.GenericLink, long, java.util.Collection, int)
	 */
	public List<IntradomainPath> findPaths(IntradomainPath pathSkel,
			long capacity, Collection<GenericLink> excluded, int limit, int mtu) {
		
		GraphSearch graph = initGraph(excluded, mtu);

		GraphEdge start = gredges.get(pathSkel.getFirstLink());
		GraphEdge end = gredges.get(pathSkel.getLastLink());

		if(start == null || end == null) {
			// No edges found
			return null;
		}
		
		//filter the user ingress and egress constraints
		if(pathSkel.getIngressConstraints() != null) {
			start.intersect(pathSkel.getIngressConstraints());
			if(start.getConstraints() == null) {
				log.debug("Ingress link:" + start.getLink() + " does not match the requirements: " 
						+ pathSkel.getIngressConstraints());
				return null;
			}
		}
		
		if(pathSkel.getEgressConstraints() != null) {
			end.intersect(pathSkel.getEgressConstraints());
			if(end.getConstraints() == null) {
				log.debug("Egress link:" + end.getLink() + " does not match the requirements: " 
						+ pathSkel.getEgressConstraints());
				return null;
			}
		}
		
		List<GraphEdge[]> paths = graph.findPaths(start, end, capacity, limit);
		
		List<IntradomainPath> res = new ArrayList<IntradomainPath>();
		
		for(GraphEdge[] p : paths) {
			IntradomainPath ipath = createIntradomainPath(start, p, end);

			if(ipath != null) {
				res.add(ipath);
			}
		}

		// Sort results by number of links
		Collections.sort(res);
		
		return res;
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
		
		if(paths != null){
    		for(GraphEdge[] path : paths) {
    			IntradomainPath ipath = createIntradomainPath(path);
    
    			if(ipath != null) {
    				res.add(ipath);
    			}
    		}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder#findPath(net.geant.autobahn.intradomain.common.GenericLink, net.geant.autobahn.intradomain.common.GenericLink, long, net.geant.autobahn.constraints.PathConstraints, java.util.Collection)
	 */
	public IntradomainPath findPath(IntradomainPath path, long capacity, Collection<GenericLink> excluded, int mtu) {
		
		List<IntradomainPath> res = findPaths(path, capacity, excluded, PATHS_LIMIT, mtu);
		
		if(res == null || res.size() < 1)
			return null;
		
		IntradomainPath ipath = res.get(0);
		settleConstraintsValuesForPath(ipath);
		
		return ipath;
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

	/**
	 * 
	 * @param path
	 * @return
	 */
	public IntradomainPath createIntradomainPath(GraphEdge[] path) {
		if (path.length < 1) {
			log.info("Wrong path!");
		}

		long capacity = Long.MAX_VALUE;

		PathConstraints pcon = new PathConstraints();
		IntradomainPath ipath = new IntradomainPath();

		for (GraphEdge edge : path) {
			pcon = pcon.intersect(edge.getConstraints());

			if (pcon == null) {
				// Constraints not agreed
				return null;
			}

			ipath.addGenericLink(edge.getLink(), pcon);

			capacity = Math.min(capacity, edge.getCapacity());
		}

		ipath.setCapacity(capacity);

		return ipath;
	}

	public void settleConstraintsValuesForPath(IntradomainPath path) {
		
		for(GenericLink gl : path.getLinks()) {
			PathConstraints pcon = path.getConstraints(gl);
			
			// vlans
			RangeConstraint vlans = pcon.getRangeConstraint(ConstraintsNames.VLANS);
			if(vlans != null) {
				int singleValue = vlans.getFirstValue();
				
				// replaces it with a single value (first one)
				RangeConstraint sVlan = new RangeConstraint(singleValue, singleValue);
				pcon.addRangeConstraint(ConstraintsNames.VLANS, sVlan);
			}
			
			path.setPathConstraints(gl, pcon);
		}
	}
	
	/**
	 * Initialize a graph to search in. Graph represents the whole network
	 * topology of the domain, while some links can be excluded from the graph.
	 * 
	 * @param excluded List of links not to include in the graph
	 * @param inCon
	 * @param egCon
	 * @param mtu 
     * @return Graph to be searched
     */
    public abstract GraphSearch initGraph(Collection<GenericLink> excluded, int mtu);

}
