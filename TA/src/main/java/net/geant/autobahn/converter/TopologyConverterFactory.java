/**
 * TopologyConverterFactory.java
 * 2007-04-03
 */
package net.geant.autobahn.converter;

import java.io.IOException;
import java.util.Properties;

import net.geant.autobahn.converter.ethernet.EthernetTopologyConverter;
import net.geant.autobahn.converter.mpls.MplsTopologyConverter;
import net.geant.autobahn.converter.sdh.SdhTopologyConverter;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;

/**
 * Factory class for returning proper implementation of <code>
 * TopologyConverter</code< interface
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class TopologyConverterFactory {
    
    private TopologyConverterFactory() { }
    
    public static TopologyConverter getTopologyConverter(IntradomainTopology topology, 
    		IntradomainPathfinder pathfinder, Properties properties) throws IOException {
        
        String domain = properties.getProperty("domainName");
        String lookuphost = properties.getProperty("lookuphost");
        
        InternalIdentifiersSource internal = new InternalIdentifiersSourceURN(domain);
        PublicIdentifiersMapping mapping = new PublicIdentifiersMapping(
        		properties.getProperty("public.ids.file"));
        
        if (topology.isEthernet())
            return new EthernetTopologyConverter(topology, pathfinder, internal, mapping, lookuphost);
        else if (topology.isSDH())
            return new SdhTopologyConverter(topology, pathfinder, internal, mapping, lookuphost);
        else if (topology.isMpls())
        	return new MplsTopologyConverter(topology, pathfinder, internal, mapping, lookuphost);
        else
        	throw new IllegalArgumentException(
				"Topology converter for topology type cannot be found!");
    }
}
