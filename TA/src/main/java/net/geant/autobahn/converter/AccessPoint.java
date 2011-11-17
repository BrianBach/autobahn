package net.geant.autobahn.converter;

import java.io.File;
import java.io.FileInputStream;
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
import org.hibernate.exception.ExceptionUtils;

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

    private StringBuffer initChecks;
    
    /**
     * Entry point for application:<br/>
     * reads properties from app.properties 
     * @throws Exception when one of the submodules could not be initialized
     */
    public AccessPoint() throws Exception {

        properties = new Properties();
        String prop_file = "etc/autobahn.properties";
        if (!new File(prop_file).exists()) {
            prop_file = "etc/ta.properties";
        }
        try {
            FileInputStream fis = new FileInputStream(prop_file);
            properties.load(fis);
            fis.close();
            log.debug(properties.size() + " properties loaded from " + prop_file);
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
        
        state = State.RESTARTING;
        
        log.info("===== Topology Abstraction module Initialization =====");
        long stime = System.currentTimeMillis();

        try {
            if(!topology.isEmpty()) {
    	        // Init intradomain pathfinder
    	        IntradomainPathfinder pathfinder = IntradomainPathfinderFactory
    	                .getIntradomainPathfinder(topology);
    	        
    	        // Init topologyConverter
    	        converter = TopologyConverterFactory.getTopologyConverter(topology, pathfinder, properties);
            }
    
            state = State.READY;
            
        } catch (Exception e) {
            state = State.ERROR;
            Throwable thr = ExceptionUtils.getRootCause(e);
            if (thr instanceof java.net.BindException) {
                log.error("Error while TA init: " + thr.getMessage() +
                        "\nPlease check whether another server is running using" +
                        " the same ports as Autobahn. You can check and edit the" +
                        " ports used by Autobahn in etc/services.properties.");                
            }
            else {
                log.error("Error while TA init: " + ((thr == null)?"":thr.getMessage()));
            }
            log.debug("Error info: ", e);
        }
        
        float total = (System.currentTimeMillis() - stime) / 1000.0f;

        log.info("===== End of initialization - " + total + " secs =====");
        
        // Run some checks now that we are done
        runAfterInitChecks();        
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.topologyabstraction.TopologyAbstraction#setIntradomainTopology()
     */
    public void setIntradomainTopology(IntradomainTopology topology, String topologyType) {
        this.topology = topology;
        log.debug("Received topology, type: "+topologyType+", topology:" + topology + ", genericLinks:" + topology.getGenericLinks());
        this.topologyType = topologyType;
        log.debug("Printing topology retrieved from WS..."+topology.TopologyString());
        
        // Since we now have IntradomainTopology information, it is time
        // to complete initialization of the module
        state = State.RESTARTING;
        try {
            init();
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init: " + e.getMessage());
            log.debug("Error info: ", e);
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
        if(converter != null) {
            return converter.getEdgeLink(l);
        }
        
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
    
    public TopologyConverter getConverter() {
    	return converter;
    }
    
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        initChecks = new StringBuffer("");
        
        // Check properties
        
        String domainName = properties.getProperty("domainName");
        if (domainName == null || domainName.equalsIgnoreCase("none") || domainName.equals("")) {
            initChecks.append("domainName is empty, please check properties file.\n");
        }
        
        String lookuphost = properties.getProperty("lookuphost");
        if (lookuphost == null || lookuphost.equalsIgnoreCase("none") || lookuphost.equals("")) {
            initChecks.append("lookuphost is empty. Database entries for interdomain links " +
            		"will have to contain both local and remote port names as the remote " +
            		"port name will not be recovered through the LS.\n");
        }
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        if (state == State.ERROR || initChecks==null) {
            log.error("TA module was not initialized successfully. Please check debug.log for" +
                    " more information.");
            return;
        }
        
        // Check public.ids
        
        FileInputStream fis = null;
        Properties public_ids = new Properties();
        try {
            fis = new FileInputStream(properties.getProperty("public.ids.file"));
            public_ids.load(fis);
            fis.close();
        } catch (IOException e) {
            initChecks.append("public.ids.file property points to a non-existing or " +
            		"non-accessible file:\n");
            initChecks.append(e.getMessage()+"\n");
            initChecks.append("It will not be possible to map public to private names " +
            		"of local ports of interdomain links.\n");
        }
        
        // Check whether the public.ids in the file correspond to ports in the topology
        List<GenericLink> glinks = topology.getGenericLinks();
        boolean found = false;
        
        for (Enumeration e = public_ids.propertyNames() ; e.hasMoreElements() ;) {
            String portName_key = (String) e.nextElement();
            String portName_value = public_ids.getProperty(portName_key);
            found = false;
            if(glinks != null) {
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
            }
            if (!found) {
                initChecks.append("Entry " + portName_key + "=" + portName_value + " " +
                		"from public.ids file does not exist in DB." +
                		"This will almost certainly create problems! " +
                		"Please check for mis-typed port names.\n");
            }
        }
        
        if (initChecks.toString().equals("")) {
            log.info("Topology Abstraction module was initialized successfully.");
        } else {
            log.info("\nTopology Abstraction module initialization reported " +
            		"the following potential problems:\n"+initChecks.toString());
        }
    }

    @Override
    public void dispose() {
        instance = null;
    }
}
