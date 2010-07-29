package net.geant.autobahn.converter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import net.geant.autobahn.converter.ethernet.EthernetTopologyConverter;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.topologyabstraction.ExternalIdentifiersSource;
import net.geant.autobahn.topologyabstraction.FileIdentifiersSource;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;

import org.apache.log4j.Logger;

/**
 * Implementation of web services. Singleton design pattern.
 * 
 * @author Kostas
 */
public final class AccessPoint implements TopologyAbstraction {

    private static AccessPoint instance;
    private enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
    private State state;
    private final Logger log = Logger.getLogger(AccessPoint.class);
    private Properties properties;
    
    private TopologyConverter converter;
    private IntradomainTopology topology;
    private String topologyType;
    
    /**
     * Entry point for application:<br/>
     * reads properties from app.properties 
     * @throws Exception when one of the submodules could not be initialized
     */
    public AccessPoint() throws Exception {

        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("etc/ta.properties");
            properties.load(fis);
            fis.close();
            log.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            log.info("Could not load app.properties: " + e.getMessage());
            throw new Exception("Could not load app.properties: " + e.getMessage());
        }

        runBeforeInitChecks();
        // Initialization of the module has not been completed, as we
        // yet don't have topology information. Initialization will be
        // performed as soon as DM send IntradomainTopology information
        // (in the setIntradomainTopology function)
    }

    /**
     * Entry point for application:<br/>
     * reads properties from parameter
     * @throws Exception when properties parameter is null
     */
    public AccessPoint(Properties props) throws Exception {

        if (props != null) {
            properties = props;
        }
        else {
            throw new Exception("No properties provided.");
        }
        
        runBeforeInitChecks();
        // Initialization of the module has not been completed, as we
        // yet don't have topology information. Initialization will be
        // performed as soon as DM send IntradomainTopology information
        // (in the setIntradomainTopology function)
    }

    /**
     * Returns an instance of AccessPoint. Singleton.
     * @return
     */
    public synchronized static AccessPoint getInstance() {
        
        if (instance == null) {
            try {
                instance = new AccessPoint();
            } catch (Exception e) {
                instance.log.error("Error while creating Topology Abstraction module AccessPoint", e);
            }
        }
        return instance;
    }

    /**
     * Returns an instance of AccessPoint. Singleton.
     * The AP instance will be initialized with the provided properties.
     * @param props
     * @return
     */
    public synchronized static AccessPoint getInstance(Properties props) {
        
        if (instance == null) {
            try {
                instance = new AccessPoint(props);
            } catch (Exception e) {
                instance.log.error("Error while creating Topology Abstraction module AccessPoint", e);
            }
        }
        return instance;
    }
    
    /**
     * Initializes the TA module:<br/>
     * Uses the topology received from DM to
     * - initialize pathfinder<br/>
     * - initialize topology converter<br/>
     */
    public void init() throws Exception {
        
        log.info("===== Topology Abstraction module Initialization =====");
        long stime = System.currentTimeMillis();

        if(!topology.isEmpty()) {
	        // Init intradomain pathfinder
	        IntradomainPathfinder pathfinder = IntradomainPathfinderFactory
	                .getIntradomainPathfinder(topology);
	        
	        // Init topologyConverter
	        converter = TopologyConverterFactory.getTopologyConverter(topology, pathfinder, properties);
        }

        float total = (System.currentTimeMillis() - stime) / 1000.0f;

        // Run some checks now that we are done
        runAfterInitChecks();
        
        log.info("===== End of initialization - " + total + " secs =====");
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType) {
        this.topology = topology;
        log.debug("Received topology, type: "+topologyType);
        this.topologyType = topologyType;
        //log.debug("Printing topology retrieved from WS..."+topology.TopologyString());
        
        // Since we now have IntradomainTopology information, it is time
        // to complete initialization of the module
        state = State.RESTARTING;
        try {
            init();
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractInternalPartOfTopology()
     */
    public Stats abstractInternalPartOfTopology() {
        if(converter != null) {
            Stats stats = converter.abstractInternalPartOfTopology();
	            
            log.debug(topologyType + ": " + stats.numNodes + " nodes, " + stats.numEdgeNodes + " edgeNodes, " +
	                       stats.numLinks + " links");
            log.debug("found: " + stats.numPaths + " paths in " + stats.calcTime + " secs");
            log.debug(converter);
	
            return stats;
        }
	        
        return null;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology(String idmAddress) {
        if(converter != null)
            return converter.abstractExternalPartOfTopology(new ExternalIdentifiersSourceImpl(idmAddress));
        
        return new ArrayList<Link>();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#abstractExternalPartOfTopology_File(java.lang.String)
     */
    public List<Link> abstractExternalPartOfTopology_File(String path) {
        if(converter != null) {
            try {
                ExternalIdentifiersSource source = new FileIdentifiersSource(path);
                return converter.abstractExternalPartOfTopology(source);
            } catch (IOException e) {
                return new ArrayList<Link>();
            }
        }
        
        return new ArrayList<Link>();
    }

    /* (non-Javadoc)
	 * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAbstractLinks()
	 */
	public List<Link> getAbstractLinks() {
		if(converter != null)
			return converter.getAbstractLinks();
		
		return null;
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getIdentifiers(java.lang.String, java.lang.String)
     */
    public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        if(converter != null)
            return converter.getIdentifiers(portName, linkBodId);
        
        return null;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getEdgeLink(net.geant.autobahn.network.Link)
     */
    public GenericLink getEdgeLink(Link l) {
        if(converter != null)
            return converter.getEdgeLink(l);
        
        return null;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#getAllEdgeLinks()
     */
    public Set<Link> getAllEdgeLinks() {
        if(converter != null && topology.isEthernet())
            return ((EthernetTopologyConverter) converter).getAllEdgeLinks();
        
        return null;
    }
    
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        log.info("===== Pre-initialization check for Topology Abstraction module. Watch out for any messages below... =====");
        
        // Check properties
        
        String id_nodes = properties.getProperty("id.nodes");
        if (id_nodes == null || id_nodes.equals("none") || id_nodes.equals("")) {
            log.info("id.nodes is empty, please check ta.properties file.");
        }
        String id_ports = properties.getProperty("id.ports");
        if (id_ports == null || id_ports.equals("none") || id_ports.equals("")) {
            log.info("id.ports is empty, please check ta.properties file.");
        }
        String id_links = properties.getProperty("id.links");
        if (id_links == null || id_links.equals("none") || id_links.equals("")) {
            log.info("id.links is empty, please check ta.properties file.");
        }
        
        String lookuphost = properties.getProperty("lookuphost");
        if (lookuphost == null || lookuphost.equals("none") || lookuphost.equals("")) {
            log.info("lookuphost is empty. Database entries for interdomain links will have to contain" +
            		"both local and remote port names as the remote port name will not be recovered through the LS.");
        }
        
        log.info("===== Pre-initialization check for Topology Abstraction module is complete. =====");
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        log.info("===== Post-initialization check for Topology Abstraction module. Watch out for any messages below... =====");
        
        // Check public.ids
        
        FileInputStream fis = null;
        Properties public_ids = new Properties();
        try {
            fis = new FileInputStream(properties.getProperty("public.ids.file"));
            public_ids.load(fis);
            fis.close();
        } catch (IOException e) {
            log.info("public.ids.file property points to a non-existing or non-accessible file:");
            log.info(e.getMessage());
            log.info("It will not be possible to map public to private names of local ports of interdomain links.");
        }
        
        // Check whether the public.ids in the file correspond to ports in the topology
        List<GenericLink> glinks = topology.getGenericLinks();
        boolean found = false;
        for (Enumeration e = public_ids.propertyNames() ; e.hasMoreElements() ;) {
            String portName_key = (String) e.nextElement();
            String portName_value = public_ids.getProperty(portName_key);
            found = false;
            for (GenericLink gl : glinks) {
                if (portName_key.equals(gl.getStartInterface().getName()) ||
                        portName_key.equals(gl.getEndInterface().getName()) ||
                        portName_value.equals(gl.getStartInterface().getName()) ||
                        portName_value.equals(gl.getEndInterface().getName()) ) {
                    log.debug("Check OK: Entry " + portName_key + "=" + portName_value + " from public.ids file exists in DB.");
                    found = true;
                    break;  // No need to go through all GenericLinks
                }
            }
            if (!found) {
                log.info("Entry " + portName_key + "=" + portName_value + " from public.ids file does not exist in DB." +
                		"This will almost certainly create problems! Please check for mis-typed port names.");
            }
        }
        log.info("===== Post-initialization check for Topology Abstraction module is complete. =====");
    }
}
