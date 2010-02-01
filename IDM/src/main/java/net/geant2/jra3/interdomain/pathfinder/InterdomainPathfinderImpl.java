/*
 * InterdomainPathfinderImpl.java
 * 
 * 2006-02-22
 */
package net.geant2.jra3.interdomain.pathfinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Node;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.network.Port;
import net.geant2.jra3.reservation.Reservation;

import org.apache.log4j.Logger;

/**
 * <code>InterdomainPathfinderImpl</code> class implements the
 * InterdomainPathfinder interface by creating an ordered list of 
 * possible interdomain paths, according to the attributes of the reservation
 *  
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 */
public class InterdomainPathfinderImpl extends InterdomainPathfinderAbstractImpl implements InterdomainPathfinder {

    private final Logger log = Logger.getLogger(InterdomainPathfinderImpl.class);
    
    // the INFINITE, the maximum weight should be < to this value
    private final int INFINITE = Integer.MAX_VALUE;

    private List<Link> _links = null;
    
    /**
     * Initalizes Interdomain Pathfinder
     * @param topology
     */
    public InterdomainPathfinderImpl(Topology topology) {
        super(topology);
    }

    /**
     * @param reservation reservation attributes.
     * 
     * @return Returns a list of possible interdomain paths, or null if some error occured.
     */
    public Iterator<Path> findInterdomainPaths(Reservation reservation, List<Link> excludedLinks) {

        List<Path> allPaths = new ArrayList<Path>();
        
        try {
            // Retrieve the links and nodes from the topology object
            _links = topology.getLinks();
            
            // NY -> GN2 and GN2 -> Greece multihoming hardcode
            List<String> exclude = new ArrayList<String>();

            String sportId = reservation.getStartPort().getBodID();
            String dportId = reservation.getEndPort().getBodID();
            
            // GN2 <-> I2 Hard code
           	if("10.10.32.30".equals(sportId) || "10.10.32.30".equals(dportId)) {
           		exclude.add("10.10.64.8");
           	} else if("10.10.32.29".equals(sportId) || "10.10.32.29".equals(dportId)) {
           		exclude.add("10.10.64.9");
           	}
            
           	// GN2 <-> Athens hard code
           	if("10.11.32.6".equals(dportId) || "10.11.32.6".equals(sportId)) {
           		exclude.add("10.11.64.4");
           	} else if("10.11.32.7".equals(dportId) || "10.11.32.7".equals(sportId)) {
           		exclude.add("10.11.64.3");
           	}
           	
           	
            List<Link> selectedLinks = new ArrayList<Link>();
           	
            // Since the path has to start from a specific port and end at
            // another specific port, we have to make sure that the algorithm
            // (which only sees nodes and links) does not choose another link
            // at the start or the end of the path.
            // The simplest solution is to remove from the graph all other
            // links from the start and end node, except the designated ones.
            String startnodeid = reservation.getStartPort().getNode().getBodID();
            String endnodeid = reservation.getEndPort().getNode().getBodID();
            
            for(Link l : _links) {
            	if(exclude.contains(l.getBodID()))
            		continue;
            	
                // Do not add other links at the start or end node
                String nid1 = l.getStartPort().getNode().getBodID();
                String nid2 = l.getEndPort().getNode().getBodID();
                String pid1 = l.getStartPort().getBodID();
                String pid2 = l.getEndPort().getBodID();
                if (nid1.equals(startnodeid) || nid2.equals(startnodeid) 
                        || nid1.equals(endnodeid) || nid2.equals(endnodeid)) {
                    // But do not remove the designated start and end links
                    if (!pid1.equals(sportId) && !pid2.equals(sportId)
                            && !pid1.equals(dportId) && !pid2.equals(dportId)) {
                        continue;
                    }
                }

                selectedLinks.add(l);
            }
            
            if(excludedLinks != null && excludedLinks.size() > 0) {
            	selectedLinks.removeAll(excludedLinks);
            }

            List<Link> allLinks = new ArrayList<Link>(); 
            allLinks.addAll(selectedLinks);
            
            // add inverted links
            for(Link l : selectedLinks) {
            	allLinks.add(l.inverse());
            }

            List<Node> nodes = topology.getNodes();
            
            // Build graph from links and nodes
            int graph[][];
            graph = buildGraph(allLinks, nodes);
            
            // Find (single) path using the simple Dijkstra algorithm
            int predecessors[];
            
            int source = findNodeInList(nodes, reservation.getStartPort().getNode());
            //int source = nodes.indexOf(reservation.getStartPort().getNode());
            
            int target = findNodeInList(nodes, reservation.getEndPort().getNode());
            //int target = nodes.indexOf(reservation.getEndPort().getNode());

            predecessors = runDijkstra(graph, source);
            
            // Dikstra algorithm finds all paths from source, so just retrieve
            // the one to the target we want, as a Path object
            Path shortestPath = buildPath(predecessors, source, target, nodes, allLinks);
            if (shortestPath != null) {
                allPaths.add(shortestPath);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        return allPaths.iterator();
    }
    
    /**
     * Returns a graph, which is a NxN adjacency matrix, according to the 
     * position of the Node objects in the nodes List.
     * Weights are assigned according to the link delay.
     * -1 denotes that the link does not exist
     *  
     * @param links
     * @param nodes
     * @return
     */
    private int[][] buildGraph(List<Link> links, List<Node> nodes) throws Exception {
        
        int graph[][] = new int[nodes.size()][nodes.size()];
        // Initialize
        for (int i=0; i<graph.length; i++) {
            for (int j=0; j<graph.length; j++) {
                graph[i][j] = -1;
            }
            graph[i][i] = 0;    // A node has distance 0 to itself
        }
        
        for (int k=0; k<links.size(); k++) {
            Link link = links.get(k);

            Node startNode = link.getStartPort().getNode();
            int startNodeIndex = findNodeInList(nodes, startNode);
            //int startNodeIndex = nodes.indexOf(startNode);

            Node endNode = link.getEndPort().getNode();
            int endNodeIndex = findNodeInList(nodes, endNode);
            //int endNodeIndex = nodes.indexOf(endNode);
            
            // Update the value in the adjacency matrix if it is not
            // initialized (-1), OR if it has been initialized, but the
            // new weight is smaller. This means that if there are multiple
            // links between the same start and end, only the link with the
            // smallest weight is taken into account for the route computation.
            if ( (graph[startNodeIndex][endNodeIndex]==-1) ||
                    graph[startNodeIndex][endNodeIndex] > link.getDelay() ) {
                graph[startNodeIndex][endNodeIndex] = link.getDelay();
            }
        }
        
        return graph;
    }

    /**
     * Returns the index position in the Node List argument where the Node
     * argument is found (according to NodeId).
     * Works similarly to List.indexOf, but is safer because it searches
     * looking only for the id of the object.
     *  
     * @param nodelist
     * @param n
     * @return
     * @throws Exception
     */
    private int findNodeInList(List<Node> nodelist, Node n) throws Exception {
        for (int i=0; i<nodelist.size(); i++) {
            if (nodelist.get(i).equals(n)) {
                return i;
            }
        }
        
        log.info("Node not found: " + n.getBodID());
        
        // Node could not be found, throw exception
        throw new Exception("PF exception: The node attached to a link could not be found in the nodes list");
    }

    /**
     * Runs Dijkstra algorithm and returns predecessors array
     * 
     * @param graph
     * @param source
     * @return
     */
    private int[] runDijkstra(int graph[][], int source) {

        // Stores the best estimate of the shortest distance from
        // the source to each node 
        int d[] = new int[graph.length];
        
        // Initialized with infinite value.
        // Value of -1 means the node has been settled 
        int dC[] = new int[graph.length];
        
        // Stores the predecessor of each node on the shortest path 
        // from the source
        int p[] = new int[graph.length];

        // Initialize
        for (int i = 0; i < graph.length; i++ ) {
            d[i] = this.INFINITE;
            dC[i] = this.INFINITE;
            p[i] = -1;
        }
        
        // We start knowning the distance of the source from itself (zero)
        d[source] = 0;
        dC[source] = 0;

        int i = 0;
        int min = this.INFINITE;
        int pos = 0;

        while (i < graph.length) {
            //extract minimum distance
            for (int j = 0; j < dC.length; j++ ){
                if( min > d[j] && dC[j] != -1 ){
                    min = d[j];
                    pos = j;
                }
            }
            // This node is settled
            dC[pos] = -1;

            // relax its neighbours
            for (int j = 0; j < graph.length; j++ ) {
                if ( (graph[pos][j] != -1) && (d[j] > graph[pos][j] + d[pos]) ) {
                    d[j] = graph[pos][j] + d[pos];
                    p[j] = pos;
                }
            }
            i++;
            min = this.INFINITE;
        }

        return p;
    }

    /**
     * Builds the Path object from the predecessors array
     * 
     * @param p
     * @param source
     * @param target
     * @param nodes
     * @param links
     * @return null if no path exists
     */
    private Path buildPath(int p[], int source, int target, List<Node> nodes, List<Link> links) throws Exception{
        
        // A list of nodes forming the path
        List<Node> graphpath = new ArrayList<Node>();

        if (p[target] == -1) {  // No path exists
            return null;
        }

        // Start by adding the target node
        graphpath.add(nodes.get(target));
        // Go from the target backwards following the predecessors array
        int currentnode = p[target];
        // Stop when predecessor value is -1
        // (i.e. there is no predecessor, i.e. we reached the source)
        while (currentnode!=-1) {
            // Insert next node at the beginning and shift all previous nodes
            graphpath.add(0, nodes.get(currentnode));
            // Go to the predecessor of the current node
            currentnode = p[currentnode];
        }
        
        // Build a Path object from the graphpath nodes list
        Path path = new Path();
        List<Link> linkpath = new ArrayList<Link>(graphpath.size()-1);
        for (int i=0; i<graphpath.size()-1; i++) {
            Link lnk = findLink(graphpath.get(i), graphpath.get(i+1), links);
            linkpath.add(i, lnk);
        }
        path.setLinks(linkpath);
        
        return path;
    }
    
    /**
     * Finds the smaller weight link that connects the two nodes
     * 
     * @param start
     * @param end
     * @param links
     * @return
     */
    private Link findLink(Node start, Node end, List<Link> links) throws Exception {
        Link link = new Link();
        boolean found = false;
        for (int i=0; i<links.size(); i++) {
            Link templink = links.get(i); 
            if ( templink.getStartPort().getNode().equals(start) &&
                    templink.getEndPort().getNode().equals(end)) {
                if (found) {
                    if (link.getDelay() > templink.getDelay()) {
                        // A link with smaller weight, connecting the same nodes,
                        // has already been found
                        continue;
                    }
                }
                found = true;
                
                link = findLink(templink.getStartPort(), templink.getEndPort(), links);
            }
        }
        
        if (!found) {
            throw new Exception("PF exception: No connecting link found");
        }
        return link;
    }

    private Link findLink(Port p1, Port p2, List<Link> links) {
        for(Link link : links) {
            Port start = link.getStartPort();
            Port end = link.getEndPort();
            
            if(p1.equals(start) && p2.equals(end)
                    || p1.equals(end) && p2.equals(start))
                return link;
        }
        
        return null;
    }
   
}