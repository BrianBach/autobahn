package net.geant.autobahn.idm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Neighbor;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.StatisticsType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.administration.Translator;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.gui.GuiNotifier;
import net.geant.autobahn.idcp.Autobahn2OscarsConverter;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.Idm2DmClient;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinder;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImplDFS;
import net.geant.autobahn.interdomain.pathfinder.TopologyImpl;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.network.StatisticsEntry;
import net.geant.autobahn.network.dao.StatisticsEntryDAO;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.reservation.User;
import net.geant.autobahn.useraccesspoint.CheckListener;
import net.geant.autobahn.useraccesspoint.ModifyRequest;
import net.geant.autobahn.useraccesspoint.RequestConverter;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ReservationResponse;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.ServiceResponse;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
import net.geant.autobahn.useraccesspoint.callback.UapCallback;
import net.geant.autobahn.useraccesspoint.callback.UapCallbackClient;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;

/**
 * This class is responsible for creating and managing the BoD system.
 * Its main tasks are:
 * 1) initializing and destroying the BoD submodules
 * 2) handling requests from external users. These requests are parsed,
 *   then users are authenticated and reservations are put on scheduler
 *   (via <code>UserAccessPoint</code>) 
 * 3) handling requests from remote IDMs 
 *   (via <code>Interdomain</code>)
 * 4) provide external tools with debug information 
 *   (via <code>Administration</code>)
 * 5) interact with DM (<code>Dm2Idm</code>)   
 * 
 * @author <a href="mailto:radek.krzywania@man.poznan.pl">Radek Krzywania</a>
 */

public final class AccessPoint implements UserAccessPoint, 
			Interdomain, Dm2Idm, Administration {

	private static AccessPoint instance;
    private static final Logger log = Logger.getLogger(AccessPoint.class);

	public static enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
    private State state;
    private int logPosition;
    private Properties properties;
    private String domainURL;
    private String domainName;
    private List<AdminDomain> neighbors;
    
    private TopologyImpl topology = null;
    private InterdomainPathfinder pathFinder = null;
    private ServiceScheduler serviceScheduler = null;
    private ReservationProcessor reservationProcessor = null;
    private Idm2Dm domainManager = null;
    private GuiNotifier guiNotifier = null;
    private UapCallback startupNotifier = null;
    private RequestConverter reqConverter = null;
    
    private IdmDAOFactory daos = null;
    
    private StringBuffer initChecks;
    
    private List<AdminDomain> compareDomains = new ArrayList<AdminDomain>();
    private List<Link> compareLinks = new ArrayList<Link>(); 
    
	private AccessPoint() throws Exception {
	}

	/**
	 * Returns an instance of AccessPoint
	 * @return
	 */
	public static synchronized AccessPoint getInstance() {

		if (instance == null) {
			try {
				log.debug("Creating instance: IDM AP");
				instance = new AccessPoint();
			} catch (Exception e) {
				log.error("Error while creating IDM AP");
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	/**
     * Initializes IDM module
     */
	public State init() {
		Properties properties = new Properties();
	        
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					"etc/idm.properties");
			properties.load(is);
			is.close();
			log.debug(properties.size() + " properties loaded");
		} catch (IOException e) {
			log.info("Could not load app.properties: " + e.getMessage());
		}
	    
	    return init(properties);
	}
	
	/**
	 * Initializes the instance using given properties. 
	 * 
	 * @param props Properties object containing settings
	 */
	public State init(Properties props) {
		this.properties = props;
		
        state = State.RESTARTING;
		
        runBeforeInitChecks();
        
        log.info("===== IDM module Initialization =====");
        
        long stime = System.currentTimeMillis();
        
		domainURL = properties.getProperty("domain");
		domainName = properties.getProperty("domainName");
		if (domainName == null || domainName.equalsIgnoreCase("none") || domainName.equals("")) {
		    log.info("domainName property does not exist. Will try to use domain property" +
		    		"(" + domainURL + ") both as the IDM URL and as the domain name.");
		    domainName = domainURL;
		}

		try {
	        IdmHibernateUtil.configure(properties.getProperty("db.host"), 
	    			properties.getProperty("db.port"), properties.getProperty("db.name"), 
	    			properties.getProperty("db.user"), properties.getProperty("db.pass"));
	        daos = HibernateIdmDAOFactory.getInstance();
	
	        // init resources manager
	        domainManager = new Idm2DmClient(properties.getProperty("dm.address"));
	        
	        // init topology
	        topology = new TopologyImpl();
	        
	        // init pathfinder
	        pathFinder = new InterdomainPathfinderImplDFS(topology);
	        
	        // init neighbors
	        AdminDomain admin = daos.getAdminDomainDAO().getByBodID(domainName);
	        
	        //Register to Lookup
	        String host = properties.getProperty("lookuphost");
	        if (isLSavailable(host)) {
    	        LookupService lookup = new LookupService(host);
    	        try {
                    lookup.RegisterIdm(domainName, domainURL);
                } catch (LookupServiceException lse) {
                    log.info("IDM could not register itself to the LS");
                    lse.printStackTrace();
                }
	        }
	        
	        neighbors = pathFinder.getNeighbours(admin); 
	        
	        
	        
	        IdmHibernateUtil.getInstance().closeSession();
	        
	        compareDomains = topology.getDomains();
	        compareLinks = topology.getLinks();
	        
	        
	        // init reservation modules
	        serviceScheduler = new ServiceScheduler();
	        reservationProcessor = new ReservationProcessor(domainName, domainManager);
	        
	        // init startup notifier
	        String startupNotifyAddress = properties.getProperty("startup.notify");
	        if(startupNotifyAddress != null) {
	        	startupNotifier = new UapCallbackClient(startupNotifyAddress);
	        }
	        
	        recoverReservations();

	        // call DM to abstract topology
	        try {
	        	domainManager.prepareTopology(domainURL.replace("interdomain", "dm2idm"));
	        } catch (Exception e) {
	        	log.error("Cannot connect to dm... IDM will not be able to process requests properly.");
	        	log.debug("Exception info: ", e);
	        }
	        
	        // create request converter
	        reqConverter = new RequestConverter(daos);
	        
            state = State.READY;
	        
        } catch (org.hibernate.exception.GenericJDBCException e) {
            state = State.ERROR;
            log.error("Database error while IDM init: " + 
                    ExceptionUtils.getRootCause(e).getMessage() + 
                    "\nPlease check the #DB PPOPERTIES section in etc/idm.properties " +
                    "file and verify the values there.");
            log.debug("Error info: ", e);
        } catch (Exception e) {
            state = State.ERROR;
            Throwable thr = ExceptionUtils.getRootCause(e);
            if (thr instanceof java.net.BindException) {
                log.error("Error while IDM init: " + thr.getMessage() +
                        "\nPlease check whether another server is running using" +
                        " the same ports as Autobahn. You can check and edit the" +
                        " ports used by Autobahn in etc/services.properties.");                
            }
            else if (thr instanceof java.net.ConnectException) {
                log.error("Error while IDM init: " + thr.getMessage() +
                        "\nPlease check whether the dm.address and the lookuphost" +
                        " have been properly defined in etc/idm.properties.");                
            }
            else {
                log.error("Error while IDM init: " + ((thr == null)?"":thr.getMessage()));
            }
            log.debug("Error info: ", e);
        }
        
        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        log.info("===== End of initialization - " + total + " secs =====");
        
        runAfterInitChecks();
        
        return state;
	}

	private void retrieveIdcpTopology() {
	    List<String> idcpServerList = getPropertiesSubset(properties, "idcp.");
	    for (String idcpServer : idcpServerList) {
	    	
            Autobahn2OscarsConverter client = new Autobahn2OscarsConverter(idcpServer);
            
            // The IDCP cloud should be already connected with at least one link
            // to the AutoBAHN topology, and an admin_domain with the server name 
            // should be present as a client domain
            ProvisioningDomain idcpProvDomain = this.getProvisioningDomainByAdminId(idcpServer);
            if (idcpProvDomain == null) {
                log.info("IDCP server " + idcpServer + " was defined in idm.properties, but it " +
                		"does not exist in the database. It will therefore be ignored");
                continue;
            }
            
            // If the corresponding admin domain has already been set as an IDCP cloud
            // (and therefore has an idcpServer value), it means that this IDCP cloud
            // has already been contacted and its topology fetched, so skip it
            if (idcpProvDomain.getAdminDomain().isIdcpCloud()) {
                log.info("IDCP server " + idcpServer + " has already been contacted and topology " +
                		"has been fetched, so it is now skipped");
                continue;
            }
            
            // First time we handle this idcpServer, so set it as an IDCP cloud
            AdminDomain idcpAdminDom = idcpProvDomain.getAdminDomain();
            this.saveIdcpServer(idcpAdminDom, idcpServer);
            
            List<Port> idcpPorts = new ArrayList<Port>();
            
            try {
                List<Link> links = client.getEndpoints();
                
                for (Link l : links) {
                    log.debug("Retrieving IDCP client ports from link " + l.getBodID());

                    // Set all IDCP ports as part of the IDCP cloud
                    Port ePort = l.getEndPort();
                    Port sPort = l.getStartPort();
                    if (!idcpPorts.contains(ePort)) {
                        ePort.getNode().setProvisioningDomain(idcpProvDomain);
                        idcpPorts.add(ePort);
                        log.debug("Retrieved IDCP end port " + ePort.getBodID());
                    }
                    if (!idcpPorts.contains(sPort)) {
                        sPort.getNode().setProvisioningDomain(idcpProvDomain);
                        idcpPorts.add(sPort);
                        log.debug("Retrieved IDCP start port " + sPort.getBodID());
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
            // Construct dummy IDCP internal links and nodes and insert them in topology
            if (!idcpPorts.isEmpty()) {
                
                // Find the dummy node that is attached to all connections between the
                // IDCP cloud and AutoBAHN, and which will also be connected to all IDCP ports.
                Node idcpNode = this.getNodeByAdminId(idcpServer);
                log.debug("IDCP ports will be linked to node " + idcpNode +
                        " in provisioning domain " + idcpProvDomain.getBodID());
                idcpNode.getProvisioningDomain().getAdminDomain().setIdcpServer(idcpServer);

                // Add IDCP ports to AutoBAHN interdomain topology
                for (Port p : idcpPorts) {
                    // Create dummy port on idcpNode
                    Port dummyIdcpPort = new Port();
                    dummyIdcpPort.setBodID(p.getBodID() + "_dummyPort");
                    dummyIdcpPort.setNode(idcpNode);
                    
                    // Create dummy node to contain the actual idcpPort
                    Node dummyIdcpNode = new Node();
                    dummyIdcpNode.setBodID(p.getBodID() + "_dummyNode");
                    dummyIdcpNode.setProvisioningDomain(idcpProvDomain);
                    p.setNode(dummyIdcpNode);
                    
                    // Create dummy link connecting dummy port to IDCP port and insert it
                    // into topology
                    Link dummyLink = Link.createVirtualLink(dummyIdcpPort, p, 0);
                    dummyLink.setBodID(p.getBodID() + "_dummyLink");
                    dummyLink.setOperationalState(StateOper.UP);
                    dummyLink.setCapacity(Long.MAX_VALUE);
                    
                    // Check if it already exists
                    log.debug("Checking if this IDCP link already exists in topology " + dummyLink);
                    List<Link> topLinks = topology.getLinks();
                    boolean existing = false;
                    for (Link existingLink : topLinks) {
                        if (existingLink.equals(dummyLink)) {
                            existing = true;
                        }
                    }
                    if (!existing) {
                        log.debug("Inserting IDCP link in topology " + dummyLink);
                        
                        // Make sure DB session is closed so that we have no merging problems
                        IdmHibernateUtil.getInstance().closeSession();
                        
                        topology.insertLink(dummyLink);
                    }
                }
                
            }
            
            // At this point idcpServer information may have been overwritten in the DB
            // so make sure it is saved
            IdmHibernateUtil.getInstance().closeSession();
            this.saveIdcpServer(idcpAdminDom, idcpServer);
	    }
	}
	
	/**
	 * Sets the idcpServer for the specified domain and saves the information in the database
	 * @param idcpAdminDom
	 * @param idcpServer
	 */
	private void saveIdcpServer(AdminDomain idcpAdminDom, String idcpServer) {
        log.debug("Setting idcpServer " + idcpServer + " for admin domain " + idcpAdminDom.getBodID());
        idcpAdminDom.setIdcpServer(idcpServer);
        HibernateUtil hbm = IdmHibernateUtil.getInstance();
        Transaction t = hbm.beginTransaction();
        daos.getAdminDomainDAO().update(idcpAdminDom);
        t.commit();
        IdmHibernateUtil.getInstance().closeSession();
	}
	
	/**
	 * 
	 * Utility function to remove links associated with an idcp cloud identified by idcpServer
	 * @param idcpServer
	 */
	private void removeIdcpTopology(String idcpServer) {
		
		HibernateUtil hbt = IdmHibernateUtil.getInstance();
		Transaction t = hbt.beginTransaction();
		
		daos.getAdminDomainDAO().delete(daos.getAdminDomainDAO().get(idcpServer));
		t.commit();
		hbt.closeSession();
		
		log.info("topology obtained from " + idcpServer + " has been removed");
	}
	
	/**
	 * Removes all non-autobahn topologies
	 */
	private void removeIdcpTopology() {
		
		for (String idcp : getPropertiesSubset(properties, "idcp."))
			removeIdcpTopology(idcp);
	}
	
	/**
	 * Read all properties containing a specified String and store in a string list.
	 * 
     * @param props - Properties to read from
     * @param token - Specified String to search for in property names
	 * @return List of property values whose keys contain the specified String.
	 */
	private List<String> getPropertiesSubset(Properties props, String token) {
	    List<String> propNames = new ArrayList<String>();
	    for (Enumeration e = props.propertyNames(); e.hasMoreElements() ;) {
	        String name = (String) e.nextElement();
	        if (name.contains(token)) {
	            propNames.add(props.getProperty(name));
	        }
	    }
	    return propNames;
	}
	
	 /**
     * Clears all submodules
     * Properties are not cleared so init can reuse them
     */
	public void dispose() {
		String domainName = properties.getProperty("domainName");
		
		// Remove idm from LS
		String host = properties.getProperty("lookuphost");
		if (isLSavailable(host)) {
            LookupService lookup = new LookupService(host);
            try {
    			lookup.RemoveIdm(domainName);
    		} catch (LookupServiceException e) {
    			e.printStackTrace();
    			log.info("IDM could not remove itself from LS");
    		}
		}
		
        log.info("===== Disposing =====");
        topology.close();
        
        if(serviceScheduler != null)
        	serviceScheduler.stop();
        
        if (guiNotifier != null) {
        	guiNotifier.stop();
        	guiNotifier = null;
        }
	    log.info("===== Disposed =====");
	}
		

	private void recoverReservations() {
		// Services
		List<Service> services = daos.getServiceDAO().getActiveServices();
		log.info("IDM recovery: " + services.size() + " active services found");
		for(Service srv : services) {
			srv.recover(reservationProcessor, serviceScheduler);
			//TODO: hack for lazy loading - change it
			Translator.convert(srv);
		}
		
		// Reservations
		List<Reservation> reservations = daos.getReservationDAO().getRunningReservations();
		IdmHibernateUtil.getInstance().closeSession();
		
		log.info("IDM recovery: " + reservations.size() + " running reservation found");
		for(Reservation r : reservations) {
			reservationProcessor.recoverReservation((AutobahnReservation) r);
		}
	}
	
	public String getLocalDomain() {
		return domainName;
	}
	
    public String getLocalDomainURL() {
        return domainURL;
    }
    
	public String getProperty(String name) {
		if (properties == null) {
		    return null;
		}
		return properties.getProperty(name);
	}
	
	// ----------------- USERACCESSPOINT -----------------
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#cancelService(java.lang.String)
	 */
	public void cancelService(String serviceID) throws UserAccessPointException {
		
        log.info("============= CANCEL REQUEST ============");
        log.info("Cancel service: " + serviceID);
		
		if (!serviceScheduler.cancelService(serviceID))
			throw new UserAccessPointException("service with id " + serviceID + " does not exist");
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllDomains()
     */
    public String[] getAllDomains() {
        
        List<AdminDomain> domains = daos.getAdminDomainDAO().getAll();
        String[] res = new String[domains.size()];
        for (int i=0; i < res.length; i++) {
            AdminDomain ad = domains.get(i);
            if (ad == null) {
                log.error("A domain returned from DB is null");
                continue;
            }
            
            if (ad.isIdcpCloud()) {
                continue;
            }
            
            res[i] = ad.getBodID();
        }
        
        return res;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllDomains_NonClient()
     */
    public String[] getAllDomains_NonClient() {
        
        List<AdminDomain> domains = daos.getAdminDomainDAO().getAll();
        String[] res = new String[domains.size()];
        for (int i=0; i < res.length; i++) {
            AdminDomain ad = domains.get(i);
            if (ad == null) {
                log.error("A domain returned from DB is null");
                continue;
            }
            
            if (ad.isIdcpCloud()) {
                continue;
            }
            
            if (!ad.isClientDomain()) {
                res[i] = ad.getBodID();
            }
        }
        
        return res;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllLinks()
     */
    public String[] getAllLinks() {
        
        List<Link> links = daos.getLinkDAO().getAll();
        String[] res = new String[links.size()];
        for (int i=0; i < res.length; i++) {
            Link lnk = links.get(i);
            if (lnk == null) {
                log.error("A link returned from DB is null");
                continue;
            }
            
            if (lnk.isIdcpLink()) {
                continue;
            }
            
            res[i] = lnk.getBodID();
        }
        
        return res;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllLinks_NonClient()
     */
    public String[] getAllLinks_NonClient() {
        
        List<Link> links = daos.getLinkDAO().getAll();
        String[] res = new String[links.size()];
        for (int i=0; i < res.length; i++) {
            Link l = links.get(i);
            if (l == null) {
                log.error("A link returned from DB is null");
                continue;
            }
            
            if (l.isIdcpLink()) {
                continue;
            }
            
            AdminDomain startDom = l.getStartPort().getNode().getProvisioningDomain().getAdminDomain();
            AdminDomain endDom = l.getEndPort().getNode().getProvisioningDomain().getAdminDomain();
            if (!startDom.isClientDomain() && !endDom.isClientDomain()) {
                res[i] = l.getBodID();
            }
        }
        
        return res;
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllClientPorts()
	 */
	public String[] getAllClientPorts() {
		
		List<Port> cports = daos.getPortDAO().getClientPorts();
        List<Port> clientPorts = new ArrayList<Port>();
        
		for (int i=0; i < cports.size(); i++) {
		    Port p = cports.get(i);
		    
            // Ignore IDCP ports
            if (p.isIdcpPort()) {
                continue;
            }
            
            clientPorts.add(p);
		}
		
        String[] cp = new String[clientPorts.size()];
        for (int i=0; i < cp.length; i++) {
            cp[i] = clientPorts.get(i).getBodID();
        }

		return cp;
	}

	/**
	 * 
	 * @return An array containing all client ports with friendly names (if available)
	 */
    public String[] getAllClientPorts_Friendly() {
        return getFriendlyNamesfromLS(getAllClientPorts());
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllIdcpPorts()
     */
    public String[] getIdcpPorts() {
        List<Port> cports = daos.getPortDAO().getAll();
        List<Port> idcpPorts = new ArrayList<Port>();

        for (int i=0; i < cports.size(); i++) {
            Port p = cports.get(i);
            
            // Ignore non-IDCP ports
            if (!p.isIdcpPort()) {
                continue;
            }

            // Ignore non-actual IDCP ports, (dummy ones)
            // TODO: Change _dummyPort naming convention? (not very safe)
            if (p.getBodID().contains("_dummyPort")) {
                continue;
            }
            
            idcpPorts.add(p);
        }
        
        String[] cp = new String[idcpPorts.size()];
        for (int i=0; i < cp.length; i++) {
            cp[i] = idcpPorts.get(i).getBodID();
        }
        return cp;
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getDomainClientPorts()
	 */
	public String[] getDomainClientPorts() {
		List<Port> cports = daos.getPortDAO().getDomainClientPorts(domainName);
		String[] cp = new String[cports.size()];
		for (int i=0; i < cp.length; i++) 
			cp[i] = cports.get(i).getBodID();
		
		return cp;
	}

   /**
     * 
     * @return An array containing this domain's client ports with friendly names (if available)
     */
    public String[] getDomainClientPorts_Friendly() {
        return getFriendlyNamesfromLS(getDomainClientPorts());
    }

	/**
	 * Return the first provisioning domain that is contained in the admin domain
	 * with this bodID
	 * @param adminBodId
	 * @return The first provisioning domain that matches the bodID, null if not found
	 */
	public ProvisioningDomain getProvisioningDomainByAdminId(String adminBodId) {
	    List<ProvisioningDomain> domains = topology.getProvDomains();
	    for (ProvisioningDomain pd : domains) {
	        if (pd.getAdminDomainID().equalsIgnoreCase(adminBodId)) {
	            return pd;
	        }
	    }
	    return null;
	}

    /**
     * Return the first Node that is contained in the admin domain
     * with this bodID
     * @param adminBodId
     * @return The first Node that matches the bodID, null if not found
     */
    public Node getNodeByAdminId(String adminBodId) {
        List<Node> nodes = topology.getNodes();
        for (Node n : nodes) {
            if (n.getAdminDomainID().equalsIgnoreCase(adminBodId)) {
                return n;
            }
        }
        return null;
    }
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#queryService(java.lang.String)
	 */
	public ServiceResponse queryService(String serviceID)
			throws UserAccessPointException {
		
		Service service = serviceScheduler.queryService(serviceID);
		if (service == null)
			throw new UserAccessPointException("service with id " + serviceID + " does not exist");
		
		ServiceResponse response = new ServiceResponse();
		response.setUserEmail(service.getUser().getEmail());
		response.setUserHomeDomain(service.getUser().getHomeDomain().getName());
		response.setUserName(service.getUser().getName());

		List<ReservationResponse> reservations = new ArrayList<ReservationResponse>();
		
		for (AutobahnReservation r : service.getReservations()) {
			
			ReservationResponse resv = new ReservationResponse();
			resv.setBidirectional(r.isBidirectional());
			resv.setCapacity(r.getCapacity());
			resv.setDescription(r.getDescription());
			resv.setEndPort(r.getEndPort().getBodID());
			resv.setMaxDelay(r.getMaxDelay());
			resv.setMessage("message");
			resv.setStartPort(r.getStartPort().getBodID());
			resv.setStartTime(r.getStartTime());
			resv.setEndTime(r.getEndTime());
			
			//mtu info has been added
            resv.setMtu(r.getMtu());
            
			// set enums
			String slabel = r.getStateObject().toString();
			resv.setState(net.geant.autobahn.useraccesspoint.State.valueOf(slabel));
			
			reservations.add(resv);
		}
		response.setReservations(reservations);
		return response;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#submitService(net.geant.autobahn.useraccesspoint.ServiceRequest, String)
	 */
	public String submitServiceAndRegister(ServiceRequest request, String url)
			throws UserAccessPointException {
		
		Service service = null;
		
        // Reservations
        List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>(); 

		for (ReservationRequest r : request.getReservations()) {
            // check for attempts to make reservation in the past
			HomeDomainReservation resv = reqConverter.createReservation(r);
            
            // set gui notifier
            if (guiNotifier != null)
            	resv.addStatusListener(guiNotifier);
            
            // Create UapCallback client
            UapCallbackClient client = null;
            if(url != null && !url.equals("")) {
            	client = new UapCallbackClient(url);
            	resv.addStatusListener(client);
            }
            
            reservations.add(resv);
		}
		
        // User
        User user = new User();
        user.setName(request.getUserName());
        user.setEmail(request.getUserEmail());
        AdminDomain homeDomain = daos.getAdminDomainDAO().getByBodID(getLocalDomain());
        user.setHomeDomain(homeDomain);

        log.info("========== NEW SERVICE REQUEST ==========");
        log.info(user);
        log.info(request);
        
        // Create and save service
        service = createService(request.getJustification(), user,
				reservations);
        
        serviceScheduler.submitService(service);
		
		return service.getBodID();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#submitService(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public String submitService(ServiceRequest request)
			throws UserAccessPointException {
		
		return submitServiceAndRegister(request, null);
	}

	/**
	 * 
	 */
	public boolean checkReservationPossibility(ReservationRequest req) throws UserAccessPointException {
		
		HomeDomainReservation res = reqConverter.createReservation(req);
		
		res.setBodID("fake_res_" + System.currentTimeMillis());
		
        res.setPathFinder(pathFinder);
        res.setResourcesReservation(domainManager);
        res.setLocalDomainID(getLocalDomain());
		
        res.setFake(true);
        
        CheckListener listener = new CheckListener();
        res.addStatusListener(listener);

        log.info("========== CHECK RESERVATION POSSIBILITY REQUEST ==========\n" + req);
        
        synchronized(listener) {
            // Reservation processing should be in the synchronized block.
            // Otherwise, the reservation might be processed before we reach
            // the wait() and therefore the notification will have been made
            // before we can receive it.
            reservationProcessor.runReservation(res);
            
        	try {
				listener.wait();
			} catch (InterruptedException e) { }
        }
        
		return listener.getResult();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#registerCallback(String, String)
	 */
	public void registerCallback(String serviceId, String url) {
		UapCallbackClient client = new UapCallbackClient(url);
		
		Service srv = serviceScheduler.getService(serviceId);
		
		for(AutobahnReservation res : srv.getReservations()) {
			res.addStatusListener(client);
		}
	}
	
    /**
     * 
     * @param u
     * @param description
     * @param reservations
     * @return
     */
    public Service createService(String description, User user,
			List<HomeDomainReservation> reservations) {
    	
    	
        // Service
        Service service = new Service(daos.getServiceDAO().generateNextId(),
				reservationProcessor);
        service.setJustification(description);
        service.setUser(user);
        
        for(HomeDomainReservation res : reservations) {
            
        	if(res.getBodID() != null) {
        		service.setBodID(res.getBodID());
        	}
        	
            res.setPathFinder(pathFinder);
            res.setResourcesReservation(domainManager);
            res.setLocalDomainID(getLocalDomain());
            
            service.addReservation(res);
        }

        HibernateUtil hbm = IdmHibernateUtil.getInstance();
		Transaction t = hbm.beginTransaction();
		
		log.debug("Reservation port (starting):"+service.getReservations().get(0).getStartPort());
		log.debug("Reservation port (ending):"+service.getReservations().get(0).getEndPort());
        daos.getServiceDAO().create(service);
        
        t.commit();
        hbm.closeSession();
        
        return service;
    }

    /**
     * 
     * @param service
     */
    public void executeService(Service service) {
    	serviceScheduler.submitService(service);
    }
    
    /**
     * 
     */
	public void modifyReservation(ModifyRequest request) {
		reservationProcessor.modifyReservation(request.getResId(), 
				request.getStartTime(), request.getEndTime());
	}

	// ----------------- INTERDOMAIN -----------------
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#hello()
	 */
	public boolean hello() {
		return true;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
		Idm2Dm dm = new Idm2DmClient(properties.getProperty("dm.address"));
		return dm.getIdentifiers(portName, bodId);
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#scheduleReservation(net.geant.autobahn.reservation.Reservation)
	 */
	public void scheduleReservation(Reservation reservation) {
		reservationProcessor.scheduleReservation(reservation);
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportSchedule(java.lang.String, int, java.lang.String, boolean, net.geant.autobahn.constraints.GlobalConstraints)
	 */
	public void reportSchedule(String resID, int code, String message,
			boolean success, GlobalConstraints global) {
		reservationProcessor.reportSchedule(resID, code, message, success, global);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws NoSuchReservationException {
		reservationProcessor.cancelReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportCancellation(java.lang.String, java.lang.String, boolean)
	 */
	public void reportCancellation(String resID, String message, boolean success) {
		reservationProcessor.reportCancellation(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportActive(java.lang.String, java.lang.String, boolean)
	 */
	public void reportActive(String resID, String message, boolean success) throws NoSuchReservationException {
		reservationProcessor.reportActive(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportFinished(java.lang.String, java.lang.String, boolean)
	 */
	public void reportFinished(String resID, String message, boolean success) {
		reservationProcessor.reportFinished(resID, message, success);
	}

	public void withdrawReservation(String resID) throws NoSuchReservationException {
		reservationProcessor.withdrawReservation(resID);
	}
	
	public void reportWithdraw(String resID, String message, boolean success) {
		reservationProcessor.reportWithdraw(resID, message, success);
	}

	public void modifyReservation(String resID, TimeRange time) {
		reservationProcessor.modifyReservation(resID, time.getStartTime(), 
				time.getEndTime());
	}

	public void reportModify(String resID, TimeRange time, String message,
			boolean success) {
		reservationProcessor.reportModify(resID, time.getStartTime(), 
				time.getEndTime(), message, success);
	}
	
	// ----------------- DM2IDM ----------------
	public void activate(String resID, boolean success) {
		reservationProcessor.activate(resID, success);
	}

	public void finish(String resID, boolean success) {
		reservationProcessor.finish(resID, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.dm2idm.Dm2Idm#getIdentifiers(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName, String linkBodId) {
		
		final int maxTries = 3;
		
		// Try at most 10 times
		for (int i = 0; i < maxTries; i++) {
			
			try {
				Interdomain idm = new InterdomainClient(domain);
				return idm.getIdentifiers(portName, linkBodId);
	        } catch (MalformedURLException e1) {
	        	log.debug("getIdentifiers failure, try#" + (i + 1) + ": "  + e1.getMessage());
			} catch (Exception ex) {
				log.debug("getIdentifiers failure, try#" + (i + 1) + ": "  + ex.getMessage());
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public void injectAbstractLinks(List<Link> links) {
		
		if(links != null && links.size() > 0) {
			log.info(links.size() + " links retrieved from DM");
			
			// Init quagga
	        if(Boolean.valueOf(properties.getProperty("ospf.use"))) {
	            String ospfAddress = properties.getProperty("ospf.address");
	            int ospfPort = Integer.parseInt(properties.getProperty("ospf.port"));
	            int lsaType = Integer.parseInt(properties.getProperty("ospf.lsaType"));
	            String ifAddr = properties.getProperty("ospf.ifAddr");
	            String areaId = properties.getProperty("ospf.areaId");
	            int opaqueType = Integer.parseInt(properties.getProperty("ospf.opaqueType"));
	            int opaqueId = Integer.parseInt(properties.getProperty("ospf.opaqueId"));
	            log.info("Trying to connect to ospf api...");
	            for (int i=0; i<20; i++) { 
	                try {
	                    topology.init(ospfAddress, ospfPort, lsaType, ifAddr,
	                            areaId, opaqueType, opaqueId); 
	                    log.info("Connected to ospf api: " + ospfAddress  + ":" + ospfPort);
	                    break;
	                } catch (Exception e) {
	                    if (i == 19) {
	                        log.error("Connecting to ospf api failed: " + e.getMessage());
	                        log.debug("", e);
	                    }
	                }
	                
	                try {
						Thread.sleep(1000 * 5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	        }
	        
	        try {
		        // Insert link into the topology
		        for (Link l : links) {
		        	log.info("Link: " + l + " acquired");
		        	topology.insertLink(l);
		        }
		        // Also insert IDCP topology information
		        retrieveIdcpTopology();
	        
	        } catch(Exception e) {
	        	log.error("Error when saving link.", e);
	        }
		}
		
        reservationProcessor.setRestorationMode(true);
	}
    
    @Override
	public void restorationCompleted() {
    	reservationProcessor.setRestorationMode(false);
        if(startupNotifier != null)
        	startupNotifier.domainUp(this.domainURL);
        
        // init gui notifier
        String guiAddress = properties.getProperty("gui.address");
        if (!guiAddress.equals("none")) {
        	int update = Integer.parseInt(properties.getProperty("gui.update"));
        	try {
				guiNotifier = new GuiNotifier(guiAddress, update);
			} catch (MalformedURLException e) {
				log.error("Error when setting up gui notifier", e);
			}
			
			// attach it to the axisting reservations
			if (guiNotifier != null) {
				reservationProcessor.addStatusListenerToAllReservations(guiNotifier);
			}
        }
        
        log.info("AutoBAHN Initialization completed.");
        log.info("Waiting for the requests...");
	}

	public boolean saveReservationStatusDB(String res, int st) {
        Session session = IdmHibernateUtil.getInstance().currentSession();
        Transaction t = session.beginTransaction();
        IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
        List<Reservation> tmpres = daos.getReservationDAO().getAll();
        for(Reservation rs:tmpres)
        {
            // Reservation found in the db
            if(rs.getBodID().equals(res)) {
                rs.setOperationalStatus(new StateOper(st));
                session.update(rs);
                t.commit();
                return true;
            }   
        }
        // Reservation not found in db
        return false;
    }
    
	
	// ------------- ADMINISTRATION ------------
	
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getLog(boolean)
	 */
	public String getLog(boolean all) {
		
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("logs/autobahn.log"));
			if (!all)
				bis.skip(logPosition);
			StringBuffer sb = new StringBuffer();
			int c;
			while ((c = bis.read()) != -1) {
				sb.append((char)c);
				if (!all)
					logPosition++;
			}
			bis.close();
			return sb.toString();
		} catch (IOException e) {
			log.debug(e.getMessage());
			return "erroc ocurred while getting log info";
		}
	}

    /* (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getStatistics(boolean)
     */
    public StatisticsType getStatistics(boolean all) {
        StatisticsType st = new StatisticsType();
        st.setInter(daos.getStatisticsEntryDAO().getInterdomainEntries());
        st.setIntra(daos.getStatisticsEntryDAO().getIntradomainEntries());
        
        return st;
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		List<KeyValue> props = new ArrayList<KeyValue>();
		
		Enumeration<Object> en = this.properties.keys();
		while (en.hasMoreElements()) {
			
			String key = (String)en.nextElement();
			String value = this.properties.getProperty(key);
			props.add(new KeyValue(key, value));
		}
		return props;
	}

	public Reservation getAutobahnReservation(String resID) {
		
		List<Service> services = serviceScheduler.getServices();
		for (Service serv : services) {
			for (Reservation resv : serv.getReservations()) {
				if (resv.getBodID().equals(resID))
					return resv;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
	 */
	public ReservationType getReservation(String resID) {
		
		List<Service> services = serviceScheduler.getServices();
		for (Service serv : services) {
			for (AutobahnReservation resv : serv.getReservations()) {
				if (resv.getBodID().equals(resID))
					return Translator.convert(resv);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setTopology(java.util.List)
	 */
	public void setTopology(List<Link> links) {
        HibernateUtil hbm = IdmHibernateUtil.getInstance();
		Transaction t = hbm.beginTransaction();
		
		try {
		
			daos.getServiceDAO().deleteAll();
			daos.getReservationDAO().deleteAll();
			daos.getLinkDAO().deleteAll();
			daos.getPortDAO().deleteAll();
			daos.getIDMNodeDAO().deleteAll();
			daos.getProvisioningDomainDAO().deleteAll();
			daos.getAdminDomainDAO().deleteAll();
			t.commit();
			hbm.closeSession();
			
			if(links != null && links.size() > 0) {
				for(Link l : links) {
					log.info("Link: " + l + " acquired from external source");
					topology.insertLink(l);
				}
                // Also insert IDCP topology information
                retrieveIdcpTopology();
			}
		} catch(Exception e) {
			log.error("Problem z set topo, ", e);
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getTopology()
	 */
	public List<Link> getTopology() {
		return topology.getLinks();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {
		
		dispose();

		// Update properties
		if(properties != null) {
			//properties.clear();
		
			for (KeyValue kv : properties)
				this.properties.put(kv.getKey(), kv.getValue());
		}

		domainManager.restart();
    	init(this.properties);
	}

	/**
	 * 
	 */
	public void cancelAllServices() {
		List<Service> services = serviceScheduler.getServices();
		
		for(Service srv : services)
			serviceScheduler.cancelService(srv.getBodID());
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getService(java.lang.String)
	 */
	public ServiceType getService(String serviceId) {
		return Translator.convert(serviceScheduler.getService(serviceId));
	}

	/**
	 * 
	 * @param resId
	 * @return
	 */
	public Service getServiceForReservation(String resId) {
		return serviceScheduler.getServiceForReservation(resId);
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getServices()
	 */
	public List<ServiceType> getServices() {
		List<Service> services = serviceScheduler.getServices();
		
		List<ServiceType> result = new ArrayList<ServiceType>();
		for(Service serv : services) {
			ServiceType stype = Translator.convert(serv);
			result.add(stype);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getStatus()
	 */
	public Status getStatus() {
		
		float lon = Float.valueOf(properties.getProperty("longitude"));
		float lat = Float.valueOf(properties.getProperty("latitude"));
		
		List<Link> links = topology.getLinks();
		
	
        // init neighbors
        AdminDomain admin = daos.getAdminDomainDAO().getByBodID(domainName);

        if(compareDomains.size() != topology.getDomains().size() || compareLinks.size() != topology.getLinks().size()){
        	neighbors = pathFinder.getNeighbours(admin);
        	compareDomains.clear();
        	compareDomains = topology.getDomains();
        	compareLinks.clear();
        	compareLinks = topology.getLinks();
        }
        
		IdmHibernateUtil.getInstance().closeSession();
		
		List<Neighbor> nbors = new ArrayList<Neighbor>();
		for (int i=0; i < neighbors.size(); i++) {
			
			Neighbor n = new Neighbor();
			n.setDomain(neighbors.get(i).getBodID());

			for (Link l : links) {
				if (l.getStartDomainID().equalsIgnoreCase(domainName) &&
					l.getEndDomainID().equalsIgnoreCase(n.getDomain()) ||
					l.getStartDomainID().equalsIgnoreCase(n.getDomain()) &&
					l.getEndDomainID().equalsIgnoreCase(domainName)) {
					n.setLink(l);
				}
			}
			nbors.add(n);
		}
		
		Status st = new Status();
		st.setDomain(domainName);
		st.setLongitude(lon);
		st.setLatitude(lat);
		st.setNeighbors(nbors);
		return st;
	}
	
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        initChecks = new StringBuffer("");
        
        // Check properties
        
        if (properties == null) {
            initChecks.append("No properties were loaded, this is a fatal problem.\n");
        }
        
        String domain = properties.getProperty("domain");
        if (domain == null || domain.equalsIgnoreCase("none") || domain.equals("")) {
            initChecks.append("domain field is empty, please check idm.properties file.\n");
        }
        // Check if it is a proper URL
        try {
            new URL(domain);
        } catch (MalformedURLException e) {
            initChecks.append("domain field is not a proper URL:\n");
            initChecks.append(e.getMessage()+"\n");
        }
        
        String domainName = properties.getProperty("domainName");
        if (domainName == null || domainName.equalsIgnoreCase("none") || domainName.equals("")) {
            initChecks.append("domainName field is empty, please check idm.properties file." +
            		" The system will assume the IDM URL (" + domainURL + ") is also the domain name\n");
        }
        
        String lookuphost = properties.getProperty("lookuphost");
        if (lookuphost == null || lookuphost.equalsIgnoreCase("none") || lookuphost.equals("")) {
            initChecks.append("lookuphost is empty. IDM will not be able to register itself at the LS." +
            		" This will only work if other IDMs already have the URL of this IDM as the " +
            		"domain name in their DBs.\n");
        } else {
            // Check if it is a proper URL
            try {
                new URL(lookuphost);
            } catch (MalformedURLException e) {
                initChecks.append(lookuphost + " is not a proper URL for LS." +
                		" IDM will not be able to register itself at the LS." +
                        " This will only work if other IDMs already have the URL of this IDM as the " +
                        "domain name in their DBs.");
                initChecks.append(e.getMessage()+"\n");
            }
        }
        
        if (getTimeoutProperty("timeout.activating") == 0) {
            initChecks.append("timeout.activating property is zero or " +
            		"a non-proper integer value, so a default value will be used instead.");
        }

        if (getTimeoutProperty("timeout.scheduling") == 0) {
            initChecks.append("timeout.scheduling property is zero or " +
                    "a non-proper integer value, so a default value will be used instead.");
        }
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        if (state == State.ERROR || initChecks==null) {
            log.error("IDM module was not initialized successfully. Please check debug.log for" +
                    " more information.");
            return;
        }
        
        // Check if the domainName exists in the database
        if (daos.getAdminDomainDAO().getByBodID(domainName) == null) {
            initChecks.append("The domain " + domainName + " does not exist in the DB. " +
                    "If this is the very first time you are starting the software " +
                    "this is normal. Otherwise, this is almost certainly a problem " +
                    "and you need to check the idm.properties " +
                    "and the admin domains in the DB.\n");
        }
        
        if (initChecks.toString().equals("")) {
            log.info("IDM module was initialized successfully.");
        } else {
            initChecks.append("");
            log.info("\nIDM module initialization reported " +
            		"the following potential problems:\n"+initChecks.toString());
        }
    }

    /**
     * Saves a statistics entry in the database.
     * 
     * @param se
     */
    public static synchronized void saveStatisticsEntry(StatisticsEntry se) {
        HibernateUtil hbm = IdmHibernateUtil.getInstance();
        if(hbm == null) {
            return;
        }
        
        StatisticsEntryDAO dao = HibernateIdmDAOFactory.getInstance().getStatisticsEntryDAO();
        
        Transaction t = hbm.beginTransaction();
        dao.update(se);
        if (!t.wasCommitted()) {
            t.commit();
        }
        
        hbm.closeSession();
    }

    private boolean isLSavailable(String ls) {
        if ((ls == null) || ls.equalsIgnoreCase("none") || ls.equals("")) {
            return false;
        }
        // Check if it is a proper URL
        try {
            new URL(ls);
        } catch (MalformedURLException e) {
            log.debug(ls + " is not a proper URL for LS");
            return false;
        }
        return true;
    }

    /**
     * Checks the Lookup Service for the friendly names of a list of ports.
     * 
     * @param The list of port ids.
     * @return The list with port ids that was provided as an argument with
     * the ids substituted with friendly names (where available from LS)
     */
    public String[] getFriendlyNamesfromLS(String[] cp) {
        String host = properties.getProperty("lookuphost");
        if (!isLSavailable(host)) {
            // Just return the initially provided ids
            return cp;
        }

        LookupService lookup = new LookupService(host);
        String[] friendlyPorts = new String[cp.length];

        for (int i=0; i < cp.length; i++) {
            String friendlyName = null;
            try {
                friendlyName = lookup.QueryFriendlyName(cp[i]);
            } catch (LookupServiceException e) {
                log.info("Friendly name for end port " + cp[i] + " could not be acquired from LS");
                log.debug(e.getMessage());
            }
            if (friendlyName == null || friendlyName.trim().equals("") 
                    || friendlyName.trim().equalsIgnoreCase("null")) {
                friendlyPorts[i] = cp[i];
            } else {
                friendlyPorts[i] = friendlyName.trim() + " (" + cp[i] + ")";
            }
        }
        return friendlyPorts;
    }
    
    /**
     * Reads the supplied timeout property from properties
     * 
     * @param prop - the property key
     * @return Timeout in msec, 0 if not found
     */
    public int getTimeoutProperty(String prop) {
        int timeout = 0;
        String timeoutStr = getProperty(prop);
        
        if (timeoutStr != null) {
            try {
                timeout = Integer.parseInt(timeoutStr);
            } catch (NumberFormatException ne) {
                // No need to do anything
            }
        }
        // Value in properties is in seconds, make it msec
        return timeout * 1000;        
    }

}
