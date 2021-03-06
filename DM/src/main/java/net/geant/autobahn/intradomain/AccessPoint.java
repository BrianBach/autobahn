package net.geant.autobahn.intradomain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.dao.DmDAOFactory;
import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dao.hibernate.HibernateDmDAOFactory;
import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.dm2idm.Dm2IdmClient;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.administration.DmAdministration;
import net.geant.autobahn.intradomain.administration.KeyValue;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.network.StatisticsEntry;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;
import net.geant.autobahn.topologyabstraction.TopologyAbstractionClient;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;

/**
 * Access point implementations of all web services. Singleton design pattern.
 * 
 * @author Michal
 */
public final class AccessPoint implements Idm2Dm, DmAdministration {

	private static AccessPoint instance;
	public static enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
    private State state;
	private static final Logger log = Logger.getLogger(AccessPoint.class);
	private Properties properties;
	
	private ResourcesReservation intradomainManager;
    private String taAddress;
    private String topologyType;
	
    private IntradomainPathfinder pathfinder;
    
    private StringBuffer initChecks;
    
    /**
     * Entry point for application:<br/>
     * reads properties from app.properties (some properties can be changed later
     * via monitoring interface), sets ssl properties, calls <code>init</code> method
     * @throws Exception when one of the submodules could not be initialized
     */
	private AccessPoint() throws Exception {
		
		properties = new Properties();
        String prop_file = "etc/autobahn.properties";
        if (!new File(prop_file).exists()) {
            prop_file = "etc/dm.properties";
        }
        try {
        	InputStream is =
        		getClass().getClassLoader().getResourceAsStream(prop_file);
            properties.load(is);
            is.close();
            log.debug(properties.size() + " properties loaded from " + prop_file);
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
     * - Resources Reservation<br/>
     * - Monitoring<br/>
     * Most sub modules can be configured through app.properties
     */
    public State init() throws Exception {
        state = State.RESTARTING;

        runBeforeInitChecks();
        
        log.info("===== DM module Initialization =====");
        long stime = System.currentTimeMillis();
        
        try {
            // Get Topology Abstraction address
            taAddress = properties.getProperty("topologyabstraction.address");
            TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
    
            // Init database
            DmHibernateUtil.configure(properties.getProperty("db.host"), 
                properties.getProperty("db.port"), properties.getProperty("db.name"), 
                properties.getProperty("db.user"), properties.getProperty("db.pass"));
    
            // Init persistent reservations manager
    		PersistentReservationsManager prman = new PersistentReservationsManager(
    				DmHibernateUtil.getInstance());
            
            // Init topology
            topologyType = properties.getProperty("db.type");
            //topologyType = ta.getTopologyType();
            IntradomainTopology topology = new IntradomainTopology(properties.getProperty("cnis.address"),
            		properties.getProperty("domainName"), topologyType);
            
            // Send topology to TA via WS
            log.debug("Send to TA" + topology);
            ta.setIntradomainTopology(topology, topologyType);
            
            // Init intradomain pathfinder
            pathfinder = IntradomainPathfinderFactory.getIntradomainPathfinder(topology);
            
            // Run abstraction of internal part of topology process
            ta.abstractInternalPartOfTopology();
            
            DmHibernateUtil.getInstance().closeSession();
            
            // Create Domain Manager
            intradomainManager = new ResourcesReservation(pathfinder, prman, properties);
            
            state = State.READY;
        } catch (org.hibernate.exception.GenericJDBCException e) {
            state = State.ERROR;
            log.error("Database error while DM init: " + 
                    ExceptionUtils.getRootCause(e).getMessage() + 
                    "\nPlease check the #DB PPOPERTIES section in properties " +
                    "file and verify the values there.");
            log.debug("Error info: ", e);
        } catch (Exception e) {
            state = State.ERROR;
            Throwable thr = ExceptionUtils.getRootCause(e);
            if (thr instanceof java.net.BindException) {
                log.error("Error while DM init: " + thr.getMessage() +
                		"\nPlease check whether another server is running using" +
                		" the same ports as Autobahn. You can check and edit the" +
                		" ports used by Autobahn in etc/services.properties.");                
            }
            else if (thr instanceof java.net.ConnectException) {
                log.error("Error while DM init: " + thr.getMessage() +
                        "\nPlease check whether the URL of the rest of the services" +
                        " (IDM, TA, Calendar) have been properly defined in" +
                        " properties file.");                
            } else {
                log.error("Error while DM init: " + ((thr == null)?"":thr.getMessage()), thr);
            }
            log.debug("Error info: ", e);
        }

        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        log.info("===== End of initialization - " + total + " secs =====");
        
        runAfterInitChecks();
        
        return state;
    }
        
    /**
     * Resets the modules. Properties are not cleared so init can reuse them.
     */
    public void dispose() {
        
        log.info("===== Disposing =====");
        DmHibernateUtil.getInstance().closeSession();
        intradomainManager.dispose();
        intradomainManager = null;
        log.info("===== Disposed =====");
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkResources(net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public DomainConstraints[] checkResources(Link[] arg0, ReservationParams arg1)
			throws OversubscribedException, AAIException {

		DomainConstraints[] res = null;
		try {
			res = intradomainManager.checkResources(arg0, arg1);
		} catch(OversubscribedException ove) {
			throw ove;
		} catch(Exception e) {
			log.error("Failed CHECK R:", e);
		}
		
		return res;
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
		} catch (Exception e) {
			log.error("ERROR when adding reservation: " + resId + ", ", e);
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
        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
        ta.dispose();
	    
        intradomainManager.dispose();
        
	    state = State.RESTARTING;
        dispose();
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
            log.error("Error while init: " + e.getMessage());
            log.debug("Error info: ", e);
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
            log.error("Error while init: " + e.getMessage());
            log.debug("Error info: ", e);
        }
	}
    
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        initChecks = new StringBuffer("");
        
        // Check properties
        
        String domainName = properties.getProperty("domainName");
        if (domainName == null || domainName.equalsIgnoreCase("none") || domainName.equals("")) {
            initChecks.append("domainName field is empty, please check properties file.\n");
        }
        
        String lookuphost = properties.getProperty("lookuphost");
        if (lookuphost == null || lookuphost.equalsIgnoreCase("none") || lookuphost.equals("")) {
            initChecks.append("lookuphost is empty. The DM may need the LS in order to " +
            		"communicate with the IDM.\n");
        } else {
            // Check if it is a proper URL
            try {
                new URL(lookuphost);
            } catch (MalformedURLException e) {
                initChecks.append(lookuphost + " is not a proper URL for LS. " +
                		"The DM may need the LS in order to communicate with the IDM.\n");
            }
        }
        
        String idm_address = properties.getProperty("idm.address");
        try {
            new URL(idm_address);
        } catch (MalformedURLException e) {
            initChecks.append("idm.address field is not a proper URL:\n");
            initChecks.append(e.getMessage()+"\n");
            initChecks.append("Please check properties file.\n");
        }
        
        String topologyabstraction_address = properties.getProperty("topologyabstraction.address");
        try {
            new URL(topologyabstraction_address);
        } catch (MalformedURLException e) {
            initChecks.append("topologyabstraction.address field is not a proper URL:\n");
            initChecks.append(e.getMessage()+"\n");
            initChecks.append("Please check properties file.\n");
        }
        
        String resourcesreservationcalendar_address = properties.getProperty("resourcesreservationcalendar.address");
        try {
            new URL(resourcesreservationcalendar_address);
        } catch (MalformedURLException e) {
            initChecks.append("resourcesreservationcalendar.address field is not a proper URL:\n");
            initChecks.append(e.getMessage()+"\n");
            initChecks.append("Please check properties file.\n");
        }
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        if (state == State.ERROR || initChecks==null) {
            log.error("DM module was not initialized successfully. Please check debug.log for" +
            		" more information.");
            return;
        }
        
        // Check if the client ports have descriptions
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        List<GenericInterface> giList = daos.getGenericInterfaceDAO().getAll();
        for (GenericInterface gi : giList) {
            if (gi.isClientPort()) {
                if (gi.getDescription() == null || 
                        gi.getDescription().trim().equals("") || 
                        gi.getDescription().trim().equalsIgnoreCase("null")) {
                    initChecks.append("The client port \"" + gi.getName() + 
                            "\" does not have a description " +
                    		"that can be used as a user-friendly name. " +
                    		"Please make sure that the relevant description field " +
                    		"is filled in cNIS or other topology source. " +
                    		"Otherwise the port will be displayed to the " +
                    		"end user with only its internal id.\n");
                }
            }
        }
        DmHibernateUtil.getInstance().closeSession();

        if (initChecks.toString().equals("")) {
            log.info("DM module was initialized successfully.");
        } else {
            log.info("\nDM module initialization reported " +
            		"the following potential problems:\n"+initChecks.toString());
        }
    }
    
    public List<StatisticsEntry> getStatistics() {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        return daos.getStatisticsEntryDAO().getAll();
    }
    
    public StatisticsEntry getStatistics(String resId) {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        return daos.getStatisticsEntryDAO().getByResId(resId);
    }
    
    public List<StatisticsEntry> getStatisticsIntra() {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        return daos.getStatisticsEntryDAO().getIntradomainEntries();
    }

    public List<StatisticsEntry> getStatisticsInter() {
        DmDAOFactory daos = HibernateDmDAOFactory.getInstance();
        return daos.getStatisticsEntryDAO().getInterdomainEntries();
    }

}
