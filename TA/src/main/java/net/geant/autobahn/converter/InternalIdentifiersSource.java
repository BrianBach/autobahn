package net.geant.autobahn.converter;

/**
 * Provides abstract identifiers for internal network devices.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public interface InternalIdentifiersSource {

    /**
     * 
     * @return Abstract identifier for the network's node.
     */
    public String generateNodeID();

    /**
     * 
     * @return Abstract identifier for the network's port.
     */
    public String generatePortID();

    /**
     * 
     * @return Abstract identifier for the network's link.
     */
    public String generateLinkID();
}
