/*
 * InterdomainPathfinderImpl.java
 * 
 * 2006-02-22
 */
package net.geant.autobahn.interdomain.pathfinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.HomeDomainReservation;

import org.apache.log4j.Logger;

/**
 * <code>InterdomainPathfinderImplDFS</code> class implements the
 * InterdomainPathfinder interface by creating an ordered list of 
 * possible interdomain paths, according to the attributes of the reservation
 *  
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 */
public class InterdomainPathfinderImplDFS extends InterdomainPathfinderAbstractImpl implements InterdomainPathfinder {

    private final Logger log = Logger.getLogger(InterdomainPathfinderImplDFS.class);
    
    public final int MAX_ITERATIONS = 10;
    
    // Global variables used by search
    int limit = MAX_ITERATIONS;
    List<Path> foundPaths;
    List<Link> edges;
    int recursion_limit;
    
    /**
     * Initalizes Interdomain Pathfinder
     * @param topology
     */
    public InterdomainPathfinderImplDFS(Topology topology) {
        super(topology);
    }

    /**
     * Initalizes Interdomain Pathfinder
     * @param topology
     * @param limit - set limit of paths found to a different value than MAX_ITERATIONS
     */
    public InterdomainPathfinderImplDFS(Topology topology, int limit) {
        super(topology);
        this.limit = limit;
    }

    public Iterator<Path> findInterdomainPaths(HomeDomainReservation reservation, List<Link> excludedLinks) {

        limit = MAX_ITERATIONS; // Initialize iteration limit
        
        foundPaths = new ArrayList<Path>();
        edges = topology.getLinks();
        List<Link> allEdges = new ArrayList<Link>();
        allEdges.addAll(edges);
        
        if (excludedLinks != null) {
            // Remove excluded links
            for (Link e : allEdges) {
                if (excludedLinks.contains(e)) {
                    edges.remove(e);
                }
            }
            allEdges.clear();
            allEdges.addAll(edges);            
        }

        if (reservation.getUserExclude() != null) {
            // Remove user-excluded links
            List<String> userExcludeLinks = reservation.getUserExclude().getLinks();
            if (userExcludeLinks != null && userExcludeLinks.size() > 0) {
                log.debug("User has requested to exclude some links...");
                for (Link e : allEdges) {
                    if (userExcludeLinks.contains(e.getBodID())) {
                        log.debug("Excluding link " + e);
                        edges.remove(e);
                    }
                }
                allEdges.clear();
                allEdges.addAll(edges);
            }

            // Remove all links belonging or attached to user-excluded domains
            List<String> userExcludeDomains = reservation.getUserExclude().getDomains();
            if (userExcludeDomains != null && userExcludeDomains.size() > 0) {
                log.debug("User has requested to exclude some domains...");
                for (Link e : allEdges) {
                    for (String str : userExcludeDomains) {
                        if (str.equalsIgnoreCase(e.getStartDomainID()) || 
                                str.equalsIgnoreCase(e.getEndDomainID())) {
                            log.debug("Excluding link " + e);
                            edges.remove(e);
                        }
                    }
                }
                allEdges.clear();
                allEdges.addAll(edges);
            }
        }
        
        // Since the path has to start from a specific port and end at
        // another specific port, we have to make sure that the algorithm
        // does not choose another link
        // at the start or the end of the path.
        // The simplest solution is to remove from the graph all other
        // links from the start and end node, except the designated ones.
        //
        // However, this assumption does not apply for IDCP nodes, where the 
        // IDCP node simply models an IDCP cloud that will take care of the reservation 
        // (see DICE IDCP integration spec at 
        // https://intranet.geant.net/sites/Services/SA2/T5/AutoBAHN/Pages/AutoBAHNDiceIDCPIntegration.aspx)
        if (reservation.isAb2IdcpReservation()) {
            edges = trimNode(reservation.getStartPort(), allEdges);
        }
        else if (reservation.isIdcp2AbReservation()) {
            edges = trimNode(reservation.getEndPort(), allEdges);            
        }
        else {
            edges = trimStartEndNodes(reservation.getStartPort(), reservation.getEndPort(), allEdges);
        }

        log.debug("DFS searching for up to " + limit + " paths, from node " +
                reservation.getStartPort().getNode() + " to node " +
                reservation.getEndPort().getNode());
        Stack<String> pathDomains = new Stack<String>();
        pathDomains.add(reservation.getStartPort().getNode().getAdminDomainID());
        recursion_limit = topology.getLinks().size() * topology.getNodes().size();
        log.debug("Recursion limit set at " + recursion_limit);
        search(reservation.getStartPort().getNode(), reservation.getEndPort().getNode(), new Stack<Link>(), pathDomains);
        
        if (reservation.getUserInclude() != null) {
            
            // Remove paths that do not traverse the user-included links
            List<String> userIncludeLinks = reservation.getUserInclude().getLinks();
            if (userIncludeLinks != null && userIncludeLinks.size() > 0) {
                HashSet<Path> invalidPaths = new HashSet<Path>();
                log.debug("User has requested to include some links...");
                for (String e : userIncludeLinks) {
                    for (Iterator<Path> iter = foundPaths.iterator(); iter.hasNext();) {
                        Path p = iter.next();
                        if (!pathContainsLink(p, e)) {
                            log.debug("Removing path " + p + " that does not contain link " + e);
                            invalidPaths.add(p);
                            break;
                        }
                    }
                }
                foundPaths.removeAll(invalidPaths);
            }
            
            // Remove paths that do not traverse the user-included domains
            List<String> userIncludeDomains = reservation.getUserInclude().getDomains();
            if (userIncludeDomains != null && userIncludeDomains.size() > 0) {
                HashSet<Path> invalidPaths = new HashSet<Path>();
                log.debug("User has requested to include some domains...");
                for (String d : userIncludeDomains) {
                    for (Iterator<Path> iter = foundPaths.iterator(); iter.hasNext();) {
                        Path p = iter.next();
                        if (!pathTraversesDomain(p, d)) {
                            log.debug("Removing path " + p + " that does not traverse domain " + d);
                            invalidPaths.add(p);
                            break;
                        }
                    }
                }
                foundPaths.removeAll(invalidPaths);
            }
        }

        List<Path> resPaths = new ArrayList<Path>();
        resPaths.addAll(foundPaths);
        foundPaths.clear();
        log.debug("Interdomain PF found " + resPaths.size() + " paths using DFS.");
        // Before handing over the Paths, we remove all IDs that we temporarily used
        // in order to differentiate between paths.
        return removePathIds(resPaths).iterator();
    }
    
    /**
     * Helper functions that checks whether a path contains a 
     * link with the designated id.
     * 
     * @param p - path
     * @param edge - link
     * @return
     */
    private boolean pathContainsLink(Path p, String edge) {
        
        List<Link> pathLinks = p.getLinks();
        for (Link l : pathLinks) {
            if (l.getBodID().equals(edge)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper functions that checks whether a path contains a 
     * link within the domain with the designated id.
     * 
     * @param p - path
     * @param dom - domain
     * @return
     */
    private boolean pathTraversesDomain(Path p, String dom) {
        
        List<Link> pathLinks = p.getLinks();
        for (Link l : pathLinks) {
            if (l.getEndDomainID().equalsIgnoreCase(dom)
                    || l.getStartDomainID().equalsIgnoreCase(dom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function that removes all other edges attached to 
     * the nodes containing sPort and ePort.
     * 
     * @param sPort
     * @param ePort
     * @param initialEdges
     * @return List of edges without the removed edges, or null if initialEdges was null
     */
    private List<Link> trimStartEndNodes(Port sPort, Port ePort, List<Link> initialEdges) {
        
        if (initialEdges == null) {
            return null;
        }
        
        List<Link> trimmedEdges = new ArrayList<Link>();
        String sportId = sPort.getBodID();
        String dportId = ePort.getBodID();
        String startnodeid = sPort.getNode().getBodID();
        String endnodeid = ePort.getNode().getBodID();
        
        for (Link l : initialEdges) {
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
            trimmedEdges.add(l);
        }
        return trimmedEdges;
    }

    /**
     * Helper function that removes all other edges attached to 
     * the node containing chosenPort.
     * 
     * @param chosenPort
     * @param initialEdges
     * @return List of edges without the removed edges, or null if initialEdges was null
     */
    private List<Link> trimNode(Port chosenPort, List<Link> initialEdges) {
        
        if (initialEdges == null) {
            return null;
        }
        
        List<Link> trimmedEdges = new ArrayList<Link>();
        String chportId = chosenPort.getBodID();
        String chnodeid = chosenPort.getNode().getBodID();
        
        for (Link l : initialEdges) {
            // Do not add other links at the start or end node
            String nid1 = l.getStartPort().getNode().getBodID();
            String nid2 = l.getEndPort().getNode().getBodID();
            String pid1 = l.getStartPort().getBodID();
            String pid2 = l.getEndPort().getBodID();
            
            if (nid1.equals(chnodeid) || nid2.equals(chnodeid)) {
                // But do not remove the link attached to the designated port
                if (!pid1.equals(chportId) && !pid2.equals(chportId)) {
                    continue;
                }
            }
            trimmedEdges.add(l);
        }
        return trimmedEdges;
    }

    /**
     * Find paths from start to dest. Upon each one found, append it to currentPath and
     * add to foundPaths.
     * 
     * @param start
     * @param dest
     * @param currentPath
     * @param pathDomains - Ordered list of domains in the current Path
     */
    private void search(Node start, Node dest, Stack<Link> currentPath, Stack<String> pathDomains) {

        if (recursion_limit <= 0 && limit < MAX_ITERATIONS) {
            log.debug("DFS Algorithm recursion limit reached.");
            return;
        }
        recursion_limit--;
        log.debug("recursion limit: "+recursion_limit);
        
        if (limit == 0) {
            return;
        }
        
        List<Link> nodeEdges = getNodeEdges(start, edges);
        log.debug(start + " has " + nodeEdges.size() + " edges.");
        for (Link edge : nodeEdges) {
            log.debug("Trying edge " + edge + " at Node " + start);
            
            Node neighbor = edge.getEndPort().getNode();
            // "edge" is a Link from Node "start" to another node.
            // Thus make sure that we get the other node and not "start" itself.
            if (neighbor.equals(start)) {
                neighbor = edge.getStartPort().getNode();
            }
            
            if (containsNode(currentPath, neighbor)) {
                log.debug("Neighbor " + neighbor + "already in current path, trying next edge");
                continue;
            }

            // Check if going to Node neighbor makes the current Path 
            // visit an earlier visited domain
            //List<String> pathDomains2 = getPathDomains(currentPath);
            log.debug("Checking if neighbor domain (" + neighbor.getAdminDomainID() + 
                    ") is already in the path domains (" + pathDomains + ")");
            int domIndx = pathDomains.lastIndexOf(neighbor.getAdminDomainID());
            if (domIndx >= 0 && domIndx < pathDomains.size()-1) {
                // The path has already visited this domain and then visited at least
                // one more, so skip this neighbor so as not to re-visit an earlier domain
                log.debug("Domain already visited, trying next edge");
                continue;
            }
            
            if (neighbor.equals(dest)) {
                Link[] completePath = new Link[currentPath.size() + 1];
                currentPath.toArray(completePath);
                completePath[currentPath.size()] = edge;

                foundPaths.add(buildPath(completePath));
                limit = limit - 1;
                log.debug("Path found! Trying next edge for more paths...");
                continue;
            }
            
            log.debug("Edge " + edge + " added to current path");
            currentPath.add(edge);
            pathDomains.add(neighbor.getAdminDomainID());
            
            log.debug("DFS searching for path from node " + neighbor + " to node " +
                    dest + ", already fixed path: " + currentPath);
            search(neighbor, dest, currentPath, pathDomains);
            
            currentPath.pop();
            pathDomains.pop();
        }
        
    }
    
    /**
     * Create Path object from Link array
     * 
     * @param linklist
     * @return
     */
    private Path buildPath(Link[] linklist) {
        Path p = new Path();
        for (Link e : linklist) {
            p.add(e);
        }
        
        // Add a temporary unique Path id. This ID is needed in order
        // to differentiate between Paths and use the Java Collections
        // methods that are based on Object equality and comparison.
        // Before however handing over
        // the Paths to the users of our class, these IDs will have
        // to be removed, since Hibernate should take care of
        // assigning IDs in the Database.
        p.setPathID(limit);
        
        return p;
    }
    
    /**
     * Remove Path IDs from all path in the list.
     * @param resPaths
     * @return The same list.
     */
    private List<Path> removePathIds(List<Path> resPaths) {
        for (Path p : resPaths) {
            p.setPathID(0);
        }
        return resPaths;
    }
    
    /**
     * Return an ordered List of domains visited by this path
     * @param path
     * @return
     */
    private List<String> getPathDomains(Stack<Link> path) {
        List<String> domains = new ArrayList<String>();

        for (int i=0; i<=path.size()-2; i++) {
            Link curLink = path.get(i);
            Node curStart = curLink.getStartPort().getNode();
            Node curEnd = curLink.getEndPort().getNode();
            Link nextLink = path.get(i+1);
            Node nextStart = nextLink.getStartPort().getNode();
            Node nextEnd = nextLink.getEndPort().getNode();
            Node commonCurNode; // The curLink node that is the one connecting to nextLink
            Node notCommonCurNode; // The curLink node that is not the one connecting to nextLink
            Node notCommonNextNode; // The nextLink node that is not the one connecting to curLink
            
            if (curStart.equals(nextStart)) {
                notCommonCurNode = curEnd;
                commonCurNode = curStart;
                notCommonNextNode = nextEnd;
            } else if (curStart.equals(nextEnd)) {
                notCommonCurNode = curEnd;
                commonCurNode = curStart;
                notCommonNextNode = nextStart;                
            } else if (curEnd.equals(nextStart)) {
                notCommonCurNode = curStart;
                commonCurNode = curEnd;
                notCommonNextNode = nextEnd;
            } else if (curEnd.equals(nextEnd)) {
                notCommonCurNode = curStart;
                commonCurNode = curEnd;
                notCommonNextNode = nextStart;
            } else {
                throw new IllegalArgumentException("Supplied links " + curLink.getBodID() + 
                        " and " + nextLink.getBodID() + " are not sequential");
            }
            
            domains.add(notCommonCurNode.getAdminDomainID());
            
            // At the last link, add both Domains
            if (i == path.size()-2) {
                domains.add(commonCurNode.getAdminDomainID());
                domains.add(notCommonNextNode.getAdminDomainID());
            }
        }

        // The path is a single link, so just add both ports' domains
        if (path.size() == 1) {
            Link e = path.get(0);
            domains.add(e.getStartDomainID());
            domains.add(e.getEndDomainID());
        }

        // Return domain list without 
        // duplicate consecutive domain entries
        return removeConsecutiveDuplicates(domains);
    }

    /**
     * Remove duplicate consecutive entries from a list of strings
     * @param domains
     * @return list without consecutive duplicates
     */
    private List<String> removeConsecutiveDuplicates(List<String> initial) {
        
        List<String> listNoDupl = new ArrayList<String>();
        for (int i=0; i<=initial.size()-2; i++) {
            if (initial.get(i).equals(initial.get(i+1))) {
                continue;
            }
            listNoDupl.add(initial.get(i));
        }
        if (initial.size() <= 1) {
            listNoDupl = initial;
        } else {
            listNoDupl.add(initial.get(initial.size()-1));
        }

        return listNoDupl;
    }
    
    /**
     * Helper method that returns all edges that attach to a certain node.
     * 
     * @param node
     * @param edges
     * @return
     */
    public static List<Link> getNodeEdges(Node node, List<Link> edges) {
        List<Link> nodeEdges = new ArrayList<Link>();
        for (Link e: edges) {
            if (e.getStartPort().getNode().equals(node) ||
                    e.getEndPort().getNode().equals(node)) {
                nodeEdges.add(e);
            }
        }
        return nodeEdges;
    }
    
    /**
     * Helper method to check if the collections of links contains specified
     * node.
     * 
     * @param path Path in form of graph edges
     * @param node Node of the graph to be found
     * @return true if the path contains the node
     */
    public static boolean containsNode(Collection<Link> path, Node node) {

        for(Link e : path) {
            if(e.getStartPort().getNode().equals(node) ||
                    e.getEndPort().getNode().equals(node)) {
                return true;
            }
        }
        
        return false;
    }

}