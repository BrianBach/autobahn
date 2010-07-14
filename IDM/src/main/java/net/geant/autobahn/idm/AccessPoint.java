package net.geant.autobahn.idm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Neighbor;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.administration.Translator;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.gui.GuiNotifier;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.Idm2DmClient;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinder;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImplDFS;
import net.geant.autobahn.interdomain.pathfinder.TopologyImpl;
import net.geant.autobahn.lookup.*;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.proxy.ProxyImpl;
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

	private enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
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
    
    /**
     * Entry point for application:
     * reads properties from app.properties (some properties can be changed later
     * via monitoring interface), sets ssl properties, calls <code>init</code> method
     * @throws Exception when one of the submodules could not be initialized
     */
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
     * Initializes sub modules:
     * Most sub modules can be configured through app.properties
     */
	public void init() {
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
	        
	    init(properties);
	 }
	
	/**
	 * Initializes the instance using given properties. 
	 * 
	 * @param props Properties object containing settings
	 */
	public void init(Properties props) {
		this.properties = props;
		
        state = State.RESTARTING;
		
        log.info("===== Initialization =====");
        
        long stime = System.currentTimeMillis();
        
		domainURL = properties.getProperty("domain");
		domainName = properties.getProperty("domainName");

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
	        LookupService lookup = new LookupService(host);
	        try {
                lookup.RegisterIdm(domainName, domainURL);
            } catch (LookupServiceException lse) {
                log.info("IDM could not register itself to the LS");
                lse.printStackTrace();
            }
	        
	        neighbors = pathFinder.getNeighbours(admin); 
	        
	        IdmHibernateUtil.getInstance().closeSession();
	        
	        // init reservation modules
	        serviceScheduler = new ServiceScheduler();
	        reservationProcessor = new ReservationProcessor(domainName, domainManager);
	        
	        // init startup notifier
	        String startupNotifyAddress = properties.getProperty("startup.notify");
	        if(startupNotifyAddress != null) {
	        	startupNotifier = new UapCallbackClient(startupNotifyAddress);
	        }
	        
	        // init gui notifier
	        String guiAddress = properties.getProperty("gui.address");
	        if (!guiAddress.equals("none")) {
	        	int update = Integer.parseInt(properties.getProperty("gui.update"));
	        	guiNotifier = new GuiNotifier(guiAddress, update);
	        }
	
	        recoverReservations();

	        // call DM to abstract topology
	        try {
	        	domainManager.prepareTopology(domainURL.replace("interdomain", "dm2idm"));
	        } catch(Exception e) {
	        	log.error("Cannot connect to dm... IDM will not be able to process requests properly.");
	        }
	        
	        // create request converter
	        reqConverter = new RequestConverter(daos);
	        
            state = State.READY;
	        
		} catch(Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
		}
        
        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        log.info("===== End of initialization - " + total + " secs =====");
	}
	
	 /**
     * Clears all submodules
     * Properties are not cleared so init can reuse them
     */
	public void dispose() {
		String domainName = properties.getProperty("domainName");
		
		// Remove idm from LS
		String host = properties.getProperty("lookuphost");
        LookupService lookup = new LookupService(host);
        try {
			lookup.RemoveIdm(domainName);
		} catch (LookupServiceException e) {
			e.printStackTrace();
			log.info("IDM could not remove itself from LS");
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
		}
		
		// Reservations
		List<Reservation> reservations = daos.getReservationDAO().getRunningReservations();
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
			throw new UserAccessPointException("service with id " + serviceID + " does not exists");
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
		String[] cp = new String[cports.size()];
		for (int i=0; i < cp.length; i++) 
			cp[i] = cports.get(i).getBodID();
		
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
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#queryService(java.lang.String)
	 */
	public ServiceResponse queryService(String serviceID)
			throws UserAccessPointException {
		
		Service service = serviceScheduler.queryService(serviceID);
		if (service == null)
			throw new UserAccessPointException("service with id " + serviceID + " does not exists");
		
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
			
			resv.setUserInclude(r.getUserInclude());
			resv.setUserExclude(r.getUserExclude());
			resv.setUserVlanId(r.getUserVlanId());

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
		
		Interdomain idm = new InterdomainClient(domain);
		
		int maxTries = 3;
		
		// Try at most 10 times
		for (int i = 0; i < maxTries; i++) {
			try {
				return idm.getIdentifiers(portName, linkBodId);
			} catch (Exception ex) {
				//log.debug("getLinkId, #" + (i + 1) + ": "  + ex.getMessage());
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
		
		if(links == null || links.size() < 1) {
	        if(startupNotifier != null)
	        	startupNotifier.domainUp(this.domainURL);

			return;
		}
		
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
                        log.error("Connecting to ospf api failed: " + e.getMessage(), e);
                    }
                }
                
                try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
        
        // Insert link into the topology
        for (Link l : links) {
        	log.info("Link: " + l + " acquired");
        	topology.insertLink(l);
        }
        
        if(startupNotifier != null)
        	startupNotifier.domainUp(this.domainURL);
        // Now when DM is ready we can recover reservations
        //recoverReservations();
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

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
	 */
	public Reservation getReservation(String resID) {
		
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
		
		IdmHibernateUtil.getInstance().closeSession();
		
		List<Neighbor> nbors = new ArrayList<Neighbor>();
		for (int i=0; i < neighbors.size(); i++) {
			
			Neighbor n = new Neighbor();
			n.setDomain(neighbors.get(i).getBodID());

			for (Link l : links) {
				if (l.getStartDomainID().equals(domainName) &&
					l.getEndDomainID().equals(n.getDomain()) ||
					l.getStartDomainID().equals(n.getDomain()) &&
					l.getEndDomainID().equals(domainName)) {
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
}
