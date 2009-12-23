/*
 * InterdomainPathfinder.java
 * 
 * 2006-02-22
 */
package net.geant2.jra3.interdomain.pathfinder;

import net.geant2.jra3.reservation.Reservation;
import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Path;

import java.util.Iterator;
import java.util.List;

/**
 * <code>InterdomainPathfinder</code> interface provides a list of paths
 *  
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 */
public interface InterdomainPathfinder {
    /**
     * Returns neighbours of a domain specified by identifier. 
     * @param domainId
     * @return
     */
    public List<AdminDomain> getNeighbours(AdminDomain domainId);
    
    /**
     * Returns path from port a to port b
     * @param reservation
     * @param excludedLinks links that should be not used for paths
     * construction. May involve both interdomain links and intradomain
     * abstract links.
     * @return
     */
    public Iterator<Path> findInterdomainPaths(Reservation reservation, List<Link> excludedLinks);
}
