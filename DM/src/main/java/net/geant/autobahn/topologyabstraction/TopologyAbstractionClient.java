package net.geant.autobahn.topologyabstraction;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;

/**
 * Communicates with a Topology Abstraction instance through web services.
 * 
 * @author Kostas
 */
public class TopologyAbstractionClient implements TopologyAbstraction {
    
    private final static Logger log = Logger.getLogger(TopologyAbstractionClient.class);
    
    private TopologyAbstraction ta = null;
    
    /**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of a TA
     */
    public TopologyAbstractionClient(String endPoint) {
        if("none".equalsIgnoreCase(endPoint)) {
            log.info("TA location was specified as none, DM->TA communication impossible");
            return;
        }
        
        try {
            new URL(endPoint);
            log.debug("TA location seems a valid URL, trying to connect to it");
        } catch (MalformedURLException e) {
            log.error("No valid TA location ("+ endPoint +") could be found, communication impossible");
            return;
        }
        
        TopologyAbstractionService service = new TopologyAbstractionService(endPoint);
        ta = service.getTopologyAbstractionPort();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType){
        if(ta != null)
             ta.setIntradomainTopology(topology, topologyType);
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractInternalPartOfTopology()
     */
    public Stats abstractInternalPartOfTopology() {
        if(ta != null)
            return ta.abstractInternalPartOfTopology();
        
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology(String idmAddress) {
        if(ta != null)
            return ta.abstractExternalPartOfTopology(idmAddress);
        
        return null;
    }

    /* (non-Javadoc)
	 * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
		if(ta != null)
			return ta.getAbstractLinks();
		
		return null;
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getIdentifiers(java.lang.String, java.lang.String)
     */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        if(ta != null)
            return ta.getIdentifiers(portName, linkBodId);
        
        return null;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getEdgeLink(net.geant.autobahn.network.Link)
     */
    public GenericLink getEdgeLink(Link l) {
        if(ta != null)
            return ta.getEdgeLink(l);
        
        return null;
    }
    

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAllEdgeLinks()
     */
    public Set<Link> getAllEdgeLinks() {
        if(ta != null)
            return ta.getAllEdgeLinks();
        
        return null;
    }

}
