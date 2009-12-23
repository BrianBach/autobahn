package net.geant2.jra3.converter;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.converter.Stats;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.topologyabstraction.TopologyAbstraction;

/**
 * Implementation of the TopologyAbstraction web service. Delegates the messages the an
 * AccessPoint instance.
 * 
 * @author Kostas
 */
@WebService(name="TopologyAbstraction", serviceName="TopologyAbstractionService",
        portName="TopologyAbstractionPort", 
        targetNamespace="http://topologyabstraction.jra3.geant2.net/",
        endpointInterface="net.geant2.jra3.topologyabstraction.TopologyAbstraction")
public final class TopologyAbstractionImpl implements TopologyAbstraction {

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType){
        AccessPoint.getInstance().setIntradomainTopology(topology, topologyType);
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#abstractInternalPartOfTopology()
     */
    public Stats abstractInternalPartOfTopology() {
        return AccessPoint.getInstance().abstractInternalPartOfTopology();
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology(String idmAddress) {
        return AccessPoint.getInstance().abstractExternalPartOfTopology(idmAddress);
    }
    
    /* (non-Javadoc)
	 * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
		return AccessPoint.getInstance().getAbstractLinks();
	}

	/* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getIdentifiers(java.lang.String, java.lang.String)
     */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        return AccessPoint.getInstance().getIdentifiers(portName, linkBodId);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getEdgeLink(net.geant2.jra3.network.Link)
     */
    public GenericLink getEdgeLink(Link l) {
        return AccessPoint.getInstance().getEdgeLink(l);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getAllEdgeLinks()
     */
    public Set<Link> getAllEdgeLinks() {
        return AccessPoint.getInstance().getAllEdgeLinks();
    }

}
