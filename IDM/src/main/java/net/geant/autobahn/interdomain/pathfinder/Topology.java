/**
 * Toplogy.java
 * 2006-11-13
 */
package net.geant.autobahn.interdomain.pathfinder;

import java.util.List;

import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;

/**
 * This interface is used by <code>Pathfinder</code> to obtain
 * current view of topology. Allows insertion and removal of links
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public interface Topology {

    /**
     * Returns list of links
     * @return list of links
     */
    List<Link> getLinks();
    
    /**
     * Returns list of nodes
     * @return list of nodes
     */
    List<Node> getNodes();
    
    /**
     * Returns list od domains
     * @return list of domains
     */
    List<AdminDomain> getDomains();
        
    /**
     * Inserts link in the topology
     * @param link link to insert
     * @return if insertion of link was successful returns true, otherwise false 
     */
    boolean insertLink(Link link);
    
    /**
     * Removes link from topology
     * @param link link to delete
     * @return if deletion of link was successful returns true, otherwise false
     */
    boolean removeLink(Link link);
    
}
