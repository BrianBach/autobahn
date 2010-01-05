package net.geant2.jra3.topologyabstraction;

import java.util.List;
import java.util.Set;

import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.converter.Stats;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;

/**
 * Communicates with a Topology Abstraction instance through web services.
 * 
 * @author Kostas
 */
public class TopologyAbstractionClient implements TopologyAbstraction {
    
    private TopologyAbstraction ta = null;
    
    /**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of an IDM
     */
    public TopologyAbstractionClient(String endPoint) {
        if("none".equals(endPoint))
            return;
        
        TopologyAbstractionService service = new TopologyAbstractionService(endPoint);
        ta = service.getTopologyAbstractionPort();
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType){
        if(ta != null)
             ta.setIntradomainTopology(topology, topologyType);
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#abstractInternalPartOfTopology()
     */
    public Stats abstractInternalPartOfTopology() {
        if(ta != null)
            return ta.abstractInternalPartOfTopology();
        
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology(String idmAddress) {
        if(ta != null)
            return ta.abstractExternalPartOfTopology(idmAddress);
        
        return null;
    }

    /* (non-Javadoc)
	 * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
		if(ta != null)
			return ta.getAbstractLinks();
		
		return null;
	}

	/* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getIdentifiers(java.lang.String, java.lang.String)
     */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        if(ta != null)
            return ta.getIdentifiers(portName, linkBodId);
        
        return null;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getEdgeLink(net.geant2.jra3.network.Link)
     */
    public GenericLink getEdgeLink(Link l) {
        if(ta != null)
            return ta.getEdgeLink(l);
        
        return null;
    }
    

    /* (non-Javadoc)
     * @see net.geant2.jra3.topologyabstraction.TopologyAbstraction#getAllEdgeLinks()
     */
    public Set<Link> getAllEdgeLinks() {
        if(ta != null)
            return ta.getAllEdgeLinks();
        
        return null;
    }

}
