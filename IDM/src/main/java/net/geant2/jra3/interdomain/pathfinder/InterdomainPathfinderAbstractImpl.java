package net.geant2.jra3.interdomain.pathfinder;

import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;

/**
 * Abstract <code>InterdomainPathfinderAbstractImpl</code> class implements
 * functions of the InterdomainPathfinder interface 
 * that are common to all implementations.
 *   
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 */
public abstract class InterdomainPathfinderAbstractImpl implements InterdomainPathfinder {

    protected Topology topology;
    
    /**
     * Initalizes Interdomain Pathfinder
     * @param topology
     */
    public InterdomainPathfinderAbstractImpl(Topology topology) {
        
        this.topology = topology;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.pathfinder.interdomain.InterdomainPathfinder#getNeighbours(net.geant2.jra3.pnetwork.AdminDomain)
     */
    public List<AdminDomain> getNeighbours(AdminDomain domain) {
        
        List<AdminDomain> neighbors = new ArrayList<AdminDomain>();
        
        try {
            // Retrieve the links and nodes from the topology object
            List<Link> links = topology.getLinks();
            List<AdminDomain> domains = topology.getDomains();
            
            for (int i=0; i<links.size(); i++) {
                Link lnk = links.get(i);
                
                if (!lnk.getStartDomainID().equals(lnk.getEndDomainID())) {
                    if (lnk.getStartDomainID().equals(domain.getBodID())) {
                        // This is a neigboring domain
                        AdminDomain tmpdom = findAdminDomainInList(domains, lnk.getEndDomainID());
                        if (!neighbors.contains(tmpdom) && !tmpdom.isClientDomain()) {
                            neighbors.add(tmpdom);
                        }
                        continue;
                    }
                    if (lnk.getEndDomainID().equals(domain.getBodID())) {
                        // This is a neigboring domain
                        AdminDomain tmpdom = findAdminDomainInList(domains, lnk.getStartDomainID());
                        if (!neighbors.contains(tmpdom) && !tmpdom.isClientDomain()) {
                            neighbors.add(tmpdom);
                        }
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return neighbors;
        }

        return neighbors;
    }
    
    private AdminDomain findAdminDomainInList(List<AdminDomain> domainlist, String id) throws Exception {
        for (int i=0; i<domainlist.size(); i++) {
            if (domainlist.get(i).getBodID() == id) {
                return domainlist.get(i);
            }
        }
        
        // Node could not be found, throw exception
        throw new Exception("PF exception: The domain could not be found in the domains list");
    }

}
