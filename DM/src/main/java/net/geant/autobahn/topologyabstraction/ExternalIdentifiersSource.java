package net.geant.autobahn.topologyabstraction;

import net.geant.autobahn.network.LinkIdentifiers;

/**
 * Provides abstract identifiers from external source for specified port and
 * node and link associated with the port.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public interface ExternalIdentifiersSource {

	/**
	 * Return abstract identifiers.
	 * 
	 * @param domain
	 *            External domain that identifiers are retrieved from
	 * @param portName
	 *            Name of the port
	 * @param linkBodId
	 *            Abstract identifier of a link assigned by the domain.
	 * @return Abstract identifiers.
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName, String linkBodId);
}
