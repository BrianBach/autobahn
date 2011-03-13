/**
 * TopologyConverter.java
 * 2007-04-02
 */
package net.geant.autobahn.converter;

import java.util.List;
import java.util.Map;

import net.geant.autobahn.converter.GenericTopologyConverter.NeighborStatus;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.topologyabstraction.ExternalIdentifiersSource;


/**
 * Performs abstraction of the domain's network topology. Network devices are
 * assigned abstract identifiers to be used by AutoBAHN system upper layers.
 * Some network details become hidden.
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */
public interface TopologyConverter {

	/**
	 * Abstracts internal part of the domain topology. That means creates
	 * abstracted topology and generates identifiers for all network devices
	 * inside the domain: nodes, ports, links that are not connected with other
	 * domains.
	 * 
	 * @return stats Statistics of the network
	 */
    public Stats abstractInternalPartOfTopology();

	/**
	 * Generates abstract identifiers for edge links and network devices (ports,
	 * nodes) that belong to the other domain.
	 * 
	 * @param source External source of abstracted identifiers 
	 * @return list of abstract links
	 */
    public List<Link> abstractExternalPartOfTopology(ExternalIdentifiersSource source); 
    
    /**
     * Returns list of abstract links 
     * 
     * @return list of abstract links
     */
    public List<Link> getAbstractLinks();

	/**
	 * Returns GenericLink associated with specified interdomain Link. Method
	 * can be used only for edge links, that connects with other domain.
	 * 
	 * @param l
	 *            abstract Link
	 * @return GenericLink that is associated with specified abstract link
	 */
    public GenericLink getEdgeLink(Link l);

	/**
	 * Return abstracted identifiers for the specified port, node that it
	 * belongs to and link that is connected to the port.
	 * 
	 * @param portName
	 *            Intradomain name of the port
	 * @param linkBodId
	 *            Abstracted identifier of the corresponding link assigned to it
	 *            in the second domain.
	 * @return Abstracted identifiers for network devices
	 */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId);
    
    /**
     * 
     * @return
     */
    public Map<String, NeighborStatus> getNeighborsStatus();
}
