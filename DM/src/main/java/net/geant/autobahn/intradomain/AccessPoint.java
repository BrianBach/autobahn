package net.geant.autobahn.intradomain;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.dm2idm.Dm2IdmClient;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.administration.DmAdministration;
import net.geant.autobahn.intradomain.administration.KeyValue;
import net.geant.autobahn.intradomain.ethernet.EthMonitoring;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.intradomain.sdh.SdhMonitoring;
import net.geant.autobahn.intradomain.topology.TopologyFileReader;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;
import net.geant.autobahn.topologyabstraction.TopologyAbstractionClient;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Access point implementations of all web services. Singleton design pattern.
 * 
 * @author Michal
 */
public final class AccessPoint implements Idm2Dm, DmAdministration {

	private static AccessPoint instance;
	private enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
    private State state;
	private static final Logger log = Logger.getLogger(AccessPoint.class);
	private Properties properties;
	
	private ResourcesReservation intradomainManager;
    private String taAddress;
    private String topologyType;
	
    private IntradomainPathfinder pathfinder;
    
    /**
     * Entry point for application:<br/>
     * reads properties from app.properties (some properties can be changed later
     * via monitoring interface), sets ssl properties, calls <code>init</code> method
     * @throws Exception when one of the submodules could not be initialized
     */
	private AccessPoint() throws Exception {
		
		properties = new Properties();
        try {
        	InputStream is =
        		getClass().getClassLoader().getResourceAsStream("etc/dm.properties");
            properties.load(is);
            is.close();
            log.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            log.info("Could not load app.properties: " + e.getMessage());
            throw new Exception("Could not load app.properties: " + e.getMessage());
        }
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
				log.error("Error while creating AccessPoint", e);
			}
		}
		return instance;
	}
	
	/**
	 * Returns a system property with given name.
	 * @param name String name of the property
	 * @return String property
	 */
    public String getProperty(String name) {
    	
    	return properties.getProperty(name);
    }
    
    /**
     * Returns the Domain Manager module.
     * 
     * @return ResourcesReservation instance
     */
    public ResourcesReservation getDomainManager() {
    	
    	return intradomainManager;
    }
    
    /**
     * Initializes sub-modules:<br/>
     * - topology (cNIS or database)<br/>
     * - pathfinder<br/>
     * - TopologyConverter<br/>
     * - Reseources Reservation<br/>
     * - Monitoring<br/>
     * Most sub modules can be configured through app.properties
     */
    public void init() throws Exception {
        state = State.RESTARTING;

        runBeforeInitChecks();
        
        log.info("===== Initialization =====");
        long stime = System.currentTimeMillis();
        
        try {
            // Get Topology Abstraction address
            taAddress = properties.getProperty("topologyabstraction.address");
            TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
    
            // Init database
            DmHibernateUtil.configure(properties.getProperty("db.host"), 
                properties.getProperty("db.port"), properties.getProperty("db.name"), 
                properties.getProperty("db.user"), properties.getProperty("db.pass"));
    
            // Init persisent reservations manager
    		PersistentReservationsManager prman = new PersistentReservationsManager(
    				DmHibernateUtil.getInstance());
            
            // Init topology
            topologyType = properties.getProperty("db.type");
            //topologyType = ta.getTopologyType();
            IntradomainTopology topology = new IntradomainTopology(properties.getProperty("cnis.address"),
            		properties.getProperty("domainName"), topologyType);
            
            // Send topology to TA via WS
            ta.setIntradomainTopology(topology, topologyType);
            
            // Reading topology from xml format from Topology Builder 
            TopologyFileReader topologySource = new TopologyFileReader(properties
    				.getProperty("topology.file"));
            topologySource.saveTopology();
            
            // Init intradomain pathfinder
            pathfinder = IntradomainPathfinderFactory.getIntradomainPathfinder(topology);
            
            // Run abstraction of internal part of topology process
            ta.abstractInternalPartOfTopology();
            
            DmHibernateUtil.getInstance().closeSession();
            
            // Create Domain Manager
            intradomainManager = new ResourcesReservation(pathfinder, prman, properties);
            
            if("true".equals(properties.getProperty("monitoring"))) {
            	if("ethernet".equals(topologyType)) {
            		EthMonitoring ethm = new EthMonitoring(this, topology);
            		ethm.startMonitoring();
            	}
            	if("sdh".equals(topologyType)) {
            		SdhMonitoring sthm = new SdhMonitoring(this);
            		sthm.startMonitoring();
            	}
            	log.info("Start Monitoring Deamon");
            }

            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }

        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        log.info("===== End of initialization - " + total + " secs =====");
        
        runAfterInitChecks();
    }
        
    /**
     * Resets the modules. Properties are not cleared so init can reuse them.
     */
    public void dispose() {
        
        log.info("===== Disposing =====");
        intradomainManager.dispose();
        intradomainManager = null;
        log.info("===== Disposed =====");
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkResources(net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public DomainConstraints checkResources(Link[] arg0, ReservationParams arg1)
			throws OversubscribedException, AAIException {

		return intradomainManager.checkResources(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resId, Link[] links, ReservationParams params)
			throws ConstraintsAlreadyUsedException {

		try {
			intradomainManager.addReservation(resId, links, params);
		} catch (OversubscribedException e) {
			log.warn("Oversubscribed when adding Reservation: " + resId + ", "
					+ e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String arg0) {
		
		intradomainManager.removeReservation(arg0);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkModification(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public boolean checkModification(String resId, TimeRange time) {
		
		return intradomainManager.checkModification(resId, time.getStartTime(),
				time.getEndTime());
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#modifyReservation(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public void modifyReservation(String resId, TimeRange time) {
		
		intradomainManager.modifyReservation(resId, time.getStartTime(), 
				time.getEndTime());
	}

	/**
	 * Executed by the IDM when the communication between IDM is possible.
	 * Domain Manager continues the abstraction process and inject all abstract
	 * links to the IDM. After that, the restoration of broken reservation is
	 * being performed (if any).
	 * 
	 * @param idmAddress
	 *            URL address of the corresponding IDM
	 */
	public void prepareTopology(final String idmAddress) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				// Abstract external part of the topology
                TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
				List<Link> links = ta.abstractExternalPartOfTopology(idmAddress);
				
				// Report links back to idm
				if(idmAddress != null && !"".equals(idmAddress)) {
					Dm2Idm idm = new Dm2IdmClient(idmAddress);
					idm.injectAbstractLinks(links);
					
					intradomainManager.restoreReservations();

					idm.restorationCompleted();
				}
			}
		});
		
		t.start();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
        return ta.getIdentifiers(portName, linkBodId);
	}

	/**
	 * Restarts the Domain Manager. Executed by the IDM when it's requested to
	 * be restarted.
	 */
	public void restart() {
        state = State.RESTARTING;
        dispose();
        try {
            init();
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		List<KeyValue> props = new ArrayList<KeyValue>();
		
		for(Object key : this.properties.keySet()) {
			String skey = (String)key;
			String value = (String)properties.get(key);
			props.add(new KeyValue(skey, value));
		}
		
		return props;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.administration.Administration#setProperties(java.util.List)
	 */
	public void setProperties(List<KeyValue> properties) {
		state = State.RESTARTING;
		dispose();

		// Update properties
		if(properties != null) {
			for (KeyValue kv : properties)
				this.properties.put(kv.getKey(), kv.getValue());
		}
        
        try {
            init();
            prepareTopology((String) this.properties.get("idm.address"));
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.administration.Administration#setTopology(net.geant.autobahn.intradomain.IntradomainTopology)
	 */
	public void setTopology(IntradomainTopology topology) {
		state = State.RESTARTING;
		dispose();

	try {
		Transaction t = DmHibernateUtil.getInstance().beginTransaction();
		
		IntradomainTopology.clearIntradomainTopologyDatabase();
		topology.saveTopology();
		
		t.commit();
    } catch (Exception e) {
        log.error("Error while saving topology, ", e);
    }

        try {
            init();
            prepareTopology((String) this.properties.get("idm.address"));
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }
	}
    
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        log.info("===== Pre-initialization check for DM module. Watch out for any messages below... =====");
        
        // Check properties
        
        String domainName = properties.getProperty("domainName");
        if (domainName == null || domainName.equals("none") || domainName.equals("")) {
            log.info("domainName field is empty, please check dm.properties file.");
        }
        
        String lookuphost = properties.getProperty("lookuphost");
        if (lookuphost == null || lookuphost.equals("none") || lookuphost.equals("")) {
            log.info("lookuphost is empty. The DM may need the LS in order to communicate with the IDM.");
        }
        
        String idm_address = properties.getProperty("idm.address");
        try {
            new URL(idm_address);
        } catch (MalformedURLException e) {
            log.info("idm.address field is not a proper URL:");
            log.info(e.getMessage());
            log.info("Please check dm.properties file.");
        }
        
        String topologyabstraction_address = properties.getProperty("topologyabstraction.address");
        try {
            new URL(topologyabstraction_address);
        } catch (MalformedURLException e) {
            log.info("topologyabstraction.address field is not a proper URL:");
            log.info(e.getMessage());
            log.info("Please check dm.properties file.");
        }
        
        String resourcesreservationcalendar_address = properties.getProperty("resourcesreservationcalendar.address");
        try {
            new URL(resourcesreservationcalendar_address);
        } catch (MalformedURLException e) {
            log.info("resourcesreservationcalendar.address field is not a proper URL:");
            log.info(e.getMessage());
            log.info("Please check dm.properties file.");
        }
        
        log.info("===== Pre-initialization check for DM module is complete. =====");
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        log.info("===== Post-initialization check for DM module. Watch out for any messages below... =====");

        // Add any checks here
        
        log.info("===== Post-initialization check for DM module is complete. =====");
    }
}
