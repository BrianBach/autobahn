package net.geant.autobahn.intradomain.pathfinder;

import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.ethernet.EthernetIntradomainPathfinder;
import net.geant.autobahn.intradomain.sdh.SdhIntradomainPathfinder;

/**
 * Factory class that builds proper pathfinder based on the given topology.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class IntradomainPathfinderFactory {

    private IntradomainPathfinderFactory() {}
    
    /**
     * Creates proper pathfinder instance.
     * 
     * @param topology intradomain Topology
     * @return intradomain pathfinder
     */
    public static IntradomainPathfinder getIntradomainPathfinder(IntradomainTopology topology) {
        
        if (topology.isEthernet())
            return new EthernetIntradomainPathfinder(topology);
        else if (topology.isSDH())
            return new SdhIntradomainPathfinder(topology);
        else 
            throw new IllegalArgumentException("Pathfinder for topology type cannot be found");
    }
	
}
