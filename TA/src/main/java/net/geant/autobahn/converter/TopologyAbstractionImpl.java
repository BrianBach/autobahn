package net.geant.autobahn.converter;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;

/**
 * Implementation of the TopologyAbstraction web service. Delegates the messages the an
 * AccessPoint instance.
 * 
 * @author Kostas
 */
@WebService(name="TopologyAbstraction", serviceName="TopologyAbstractionService",
        portName="TopologyAbstractionPort", 
        targetNamespace="http://topologyabstraction.autobahn.geant.net/",
        endpointInterface="net.geant.autobahn.topologyabstraction.TopologyAbstraction")
public final class TopologyAbstractionImpl implements TopologyAbstraction {

    private final Logger log = Logger.getLogger(TopologyAbstractionImpl.class);
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType){
        try {
            AccessPoint.getInstance().setIntradomainTopology(topology, topologyType);
        } catch (Exception e) {
            log.error("TA setIntradomainTopology failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractInternalPartOfTopology()
     */
    public Stats abstractInternalPartOfTopology() {
        try {
            return AccessPoint.getInstance().abstractInternalPartOfTopology();
        } catch (Exception e) {
            log.error("TA abstractInternalPartOfTopology failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology(String idmAddress) {
        try {
            return AccessPoint.getInstance().abstractExternalPartOfTopology(idmAddress);
        } catch (Exception e) {
            log.error("TA abstractExternalPartOfTopology failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
    }
    
    /* (non-Javadoc)
	 * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
	    try {
	        return AccessPoint.getInstance().getAbstractLinks();
        } catch (Exception e) {
            log.error("TA getAbstractLinks failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getIdentifiers(java.lang.String, java.lang.String)
     */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        try {
            return AccessPoint.getInstance().getIdentifiers(portName, linkBodId);
        } catch (Exception e) {
            log.error("TA getIdentifiers failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getEdgeLink(net.geant.autobahn.network.Link)
     */
    public GenericLink getEdgeLink(Link l) {
        try {
            return AccessPoint.getInstance().getEdgeLink(l);
        } catch (Exception e) {
            log.error("TA getEdgeLink failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAllEdgeLinks()
     */
    public Set<Link> getAllEdgeLinks() {
        try {
            return AccessPoint.getInstance().getAllEdgeLinks();
        } catch (Exception e) {
            log.error("TA getAllEdgeLinks failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
    }

    @Override
    public void dispose() {
        AccessPoint.getInstance().dispose();
    }

}
