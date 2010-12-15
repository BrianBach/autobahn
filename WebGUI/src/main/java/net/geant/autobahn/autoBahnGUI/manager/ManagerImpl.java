package net.geant.autobahn.autoBahnGUI.manager;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.geant.autobahn.aai.UserAuthParameters;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.MapKeySetComparator;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServicesComparator;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinderNotifier;
import net.geant.autobahn.gui.EventType;
import net.geant.autobahn.gui.ReservationChangedType;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.util.AuthorityUtils;

/**
 * Manager is responsible for communication and managing of IDMs registered in WEB GUI
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ManagerImpl implements Manager, ManagerNotifier {
	
	/**
	 * Default time zone for WEB GUI
	 */
	private  String timezone ="UTC";
	/**
	 * List of all timezones for WEB GUI
	 */
	private List<String> timezones = Arrays.asList(TimeZone.getAvailableIDs());
	/**
	 * Topology notifier 
	 */
	private TopologyFinderNotifier notifier;
	/**
	 * Service states name
	 */
	public static final String[] serviceStates = {"unknown","accepted", "in progress", "scheduled",
	        "active", "finished", "failed", "cancelled" }; 
	
	/**
	 * Reservation states
	 */
	public static int UNKNOWN = 0;
	public static int ACCEPTED = 1;
    public static int PATHFINDING = 2;
    public static int LOCAL_CHECK = 3;
    public static int SCHEDULING =4;
    public static int SCHEDULED = 5;
    public static int CANCELLING = 6;
    public static int DEFERRED_CANCEL = 7;
    public static int WITHDRAWING = 8;
    public static int ACTIVATING = 9;
    public static int ACTIVE = 10;
    public static int FINISHING = 11;
    public static int FINISHED = 21;
    public static int CANCELLED = 22;
    public static int FAILED = 23; 
	
    /**
	 * Reservations states names
	 */
	public static final String[] reservationStates = {"UNKNOWN", "ACCEPTED", "PATHFINDING", "LOCAL_CHECK",
            "SCHEDULING", "SCHEDULED", "CANCELLING", "DEFERRED_CANCEL","WITHDRAWING","ACTIVATING",
            "ACTIVE", "FINISHING", 
            "UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN",
            "FINISHED","CANCELLED", "FAILED"};
	
	public static final String[] priorities ={};
	
	/**
	 * Map with IDM registered in the system
	 */
	private  Map<String, InterDomainManager> idms = Collections.synchronizedMap(new HashMap<String, InterDomainManager>());
	
	/**
	 * Map with IDM registration times
	 */
	private ConcurrentMap<String, Long> idmsTime = new ConcurrentHashMap<String, Long>();
	
	/**
	 * Identifies period of time when IDM register in WEB GUI is mark as down
	 */
	private long tearDownTime = 60000;
	/**
	 * Logs information
	 */
	private static Logger logger = Logger.getLogger("IDMsManager");
	
 	/**
 	 * Maps the virtual port to real one and vice versa
 	 */
	private PortsMapper portsMapper;
	
	private LookupService lookupService;
	
	public ManagerImpl(){
//		Properties properties = new Properties();
//        try {
//            InputStream is = getClass().getClassLoader().getResourceAsStream(
//                        "../etc/webgui.properties");
//            properties.load(is);
//            is.close();
//            logger.debug(properties.size() + " properties loaded");
//        } catch (IOException e) {
//            logger.info("Could not load lookuphost properties: " + e.getMessage());
//        }
//        String host = properties.getProperty("lookuphost");
//        lookupService = new LookupService(host);
		
		
	}
	
	public LookupService getLookupServiceObject(){
		return lookupService;
	}
	/**
	 * Creates the InterDomainManager
	 * @param name name of new InterDomainManagert
	 * @return InterDomainManager
	 */
	public InterDomainManager createIdm (String name){
		InterDomainManager interdomain = new InterDomainManager(name, name);
		return interdomain;
	}

	
	/**
	 * Registers InterDomainManager
	 * 
	 * @param idm InterDomainManager
	 */
	public void addIdm(InterDomainManager idm) {
		if (idm ==null)
			return;
		idms.put(idm.getIdentifier(), idm);
	}
	
	/**
	 * Removes registered InterDomainManager
	 * 
	 * @param identifier	InterDomainManager identifier
	 * @return	 removed InterDomainManager
	 */
	public InterDomainManager remove (String identifier){
		InterDomainManager manager = idms.remove(identifier);
		return manager;
	}
	/**
	 * Removes registered InterDomainManager 
	 * 
	 * @param name	 InterDomain manager
	 * @return	 removed InterDomainManager
	 */
	public InterDomainManager remove (InterDomainManager idm){
		if (idm==null)
			return null;
		return idms.remove(idm);
	}
	/**
	 * Removes all registered InterDomainManager 
	 */
	public void removeAll() {
		Iterator<String> iterator = idms.keySet().iterator();
		InterDomainManager idm = null;
		while (iterator.hasNext()) {
			idm = (InterDomainManager)idms.get(iterator.next());
			remove(idm);
			idm = null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagers()
	 */
	public List<InterDomainManager> getInterDomainManagers() {
		List<InterDomainManager> managers = new ArrayList<InterDomainManager>();
		Iterator<String> keyIterator = idms.keySet().iterator();
		InterDomainManager manager;
		while (keyIterator.hasNext()){
			manager = idms.get(keyIterator.next());
			if (manager!=null)
				managers.add(manager);
			
		}
		return managers;
	}
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllIdcpPorts()
     */
    public List<String> getAllIdcpPorts (){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
            InterDomainManager manager = idms.get(idm);
            String[] temp = manager.getIdcpPorts();
            
            List<String> ports = new ArrayList<String>();
            
            if(temp != null) {
                for(String link : temp) {
                    ports.add(link);
                }
                
                return ports; 
            }
        }
        
        return null;
    }
    
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllPorts()
	 */
	public List<String> getAllPorts (){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
        	InterDomainManager manager = idms.get(idm);
        	String[] temp = manager.getAllClientPorts();
        	
        	List<String> ports = new ArrayList<String>();
        	
        	if(temp != null) {
        		for(String link : temp) {
        			ports.add(link);
        		}
        		
        		return ports; 
        	}
        }
        
		return null;
	}
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllFriendlyPorts()
     */
    public List<PortMap> getAllFriendlyPorts () {
        List<String> ports = getAllPorts();
        List<PortMap> friendlyPorts = new ArrayList<PortMap>();
        
        for (String p_id : ports) {
            String friendlyName = getFriendlyNamefromLS(p_id);
            if (friendlyName == null || friendlyName.trim().equals("") 
                    || friendlyName.trim().equals("null")) {
                friendlyPorts.add(new PortMap(p_id, p_id));                
            } else {
                friendlyPorts.add(new PortMap(p_id, friendlyName.trim() + " (" + p_id + ")"));
            }
        }
        return friendlyPorts;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllFriendlyAndIdcpPorts()
     */
    public List<PortMap> getAllFriendlyAndIdcpPorts () {
        List<PortMap> friendlyPorts = getAllFriendlyPorts();
        List<String> idcpPorts = this.getAllIdcpPorts();
        
        if (friendlyPorts == null) {
            friendlyPorts = new ArrayList<PortMap>();
        }
        
        if (idcpPorts == null) {
            return friendlyPorts;
        }
        
        for (String idcpP : idcpPorts) {
            friendlyPorts.add(new PortMap(idcpP, idcpP));                
        }
        return friendlyPorts;
    }

    /**
     * Checks the Lookup Service for the friendly name of a port identifier.
     * 
     * @param The port identifier to look for.
     * @return The friendly name if operation was successful, null otherwise
     */
    public String getFriendlyNamefromLS(String identifier) {
    	
        String friendlyName = null;
        try {
            friendlyName = lookupService.QueryFriendlyName(identifier);
        } catch (LookupServiceException e) {
            logger.info("End port friendly name could not be acquired from LS");
            logger.info(e.getMessage());
        }
        return friendlyName;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains()
     */
    public List<String> getAllDomains(){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
        	InterDomainManager manager = idms.get(idm);
        	String[] temp = manager.getAllDomains();
        	
        	List<String> domains = new ArrayList<String>();
        	
        	if(temp != null) {
        		for(String link : temp) {
        			domains.add(link);
        		}
        		
        		return domains; 
        	}
        }
        
		return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains_NonClient()
     */
    public List<String> getAllDomains_NonClient(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager = null;
        
        while (iterator.hasNext()) {
            manager = idms.get(iterator.next());
            if (manager != null) {
                List<String> domains = new ArrayList<String>();
            	
            	String[] temp = manager.getAllDomains_NonClient();
        		for(String domain : temp) {
        			domains.add(domain);
        		}

        		return domains;
            }
        }
        
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllLinks()
     */
    public List<String> getAllLinks(){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
        	InterDomainManager manager = idms.get(idm);
        	String[] temp = manager.getAllLinks();
        	
        	if(temp != null) {
            	List<String> links = new ArrayList<String>();
            	
        		for(String link : temp) {
        			links.add(link);
        		}
        		
        		return links; 
        	}
        }
        
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllLinks_NonClient()
     */
    public List<String> getAllLinks_NonClient(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager = null;
        while (iterator.hasNext()) {
            manager = idms.get(iterator.next());
            if (manager != null) {
                String[] temp = manager.getAllLinks_NonClient();
                
                if (temp != null) {
                    List<String> links = new ArrayList<String>();

            		for(String link : temp) {
            			links.add(link);
            		}
            		
            		return links;
                }
            }
        }
        return null;
    }

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagerPorts(java.lang.String)
	 */
	public List<String> getInterDomainManagerPorts (String idmIdentifier){
	    InterDomainManager manager = idms.get(idmIdentifier);
	    if (manager == null)
	    	return null;
	    
    	String[] temp = manager.getDomainClientPorts();
	    List<String> ports = new ArrayList<String>();
	    
	    for(String port : temp) {
	    	ports.add(port);
	    }
	    
	    //logger.info (ports);
	    return ports;
	}
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getFriendlyInterDomainManagerPorts()
     */
    public List<PortMap> getFriendlyInterDomainManagerPorts(String idmIdentifier) {
        List<String> ports = getInterDomainManagerPorts(idmIdentifier);
        List<PortMap> friendlyPorts = new ArrayList<PortMap>();
        if (ports == null){
            return null;
        }
        for (String p_id : ports) {
            String friendlyName = getFriendlyNamefromLS(p_id);
            if (friendlyName == null || friendlyName.trim().equals("")
                    || friendlyName.trim().equals("null")) {
                friendlyPorts.add(new PortMap(p_id, p_id));                
            } else {
                friendlyPorts.add(new PortMap(p_id, friendlyName.trim() + " (" + p_id + ")"));
            }
        }
        return friendlyPorts;
    }
	
	/**
	public Service requestService (String idm, ServiceRequest service) throws UserAccessPointException_Exception{
		InterDomainManager manager = idms.get(idm);
		if (manager==null || service == null) 
			return null;
		String identifier;
		identifier = manager.submitService(service);
		return manager.getService(identifier);		
	}*/
	
	public Map<String,String> getServicesForAllInterDomainManagers(){
		
		List<ServiceType> list = new ArrayList<ServiceType>(); 
		InterDomainManager manager = null;
		List<ServiceType> managerServices =null;
		
		for (String idm:idms.keySet()){
			manager = idms.get(idm);
			if (manager == null)
				continue;
			managerServices = manager.getServices(false);
			if (managerServices == null)
				continue;
			list.addAll(managerServices);
		}	
		Map<String,String> map = new HashMap<String, String>();		
		
		for (int i = 0; i < list.size(); i++) {
						
			List<Reservation> res = list.get(i).getReservations();	
			for (int j = 0; j < res.size(); j++) {

				String bodyID = res.get(j).getBodID();
				String homeID = res.get(j).getPath().getHomeDomainID();
		
				map.put(bodyID,homeID);
			}
		}
		if (map.size() == 0) return null;
		else 
			return sortMapByKey(map);
		
	}
	
	
public LinkedHashMap<String, String> sortMapByKey(final Map<String, String> map){
		
		TreeSet<String> treeset= new TreeSet<String>(new MapKeySetComparator());
		treeset.addAll(map.keySet());
		
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		 
		for(String str : treeset) {
			result.put(str, map.get(str));
		}
			
			return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllInterdomainManagers()
	 */
	public List<String> getAllInterdomainManagers (){
		List<String>names = new ArrayList<String> ();
		for (String name:idms.keySet())
			names.add(name);
		return names;
	}
	
	/**
	 * Make actions needed when  new IDM appear for first time
	 * 
     * @param idmName - Unique Name of the IDM domain, e.g. Geant
	 * @param idmUrl - URL where the IDM is listening
	 * @return
	 */
	public InterDomainManager newInterDomainManagerConnected(String idmName, String idmUrl) {
		int index = idmUrl.indexOf("/interdomain");
		String url;
		if (index > 0) {
			url = idmUrl.substring(0, index);
		}
		else {
			url = idmUrl;
		}
		InterDomainManager manager = new InterDomainManager(idmName, url);
		if (manager != null) {
			idms.put(idmName, manager);
		}
		return manager;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#reservationChanged(java.lang.String, java.lang.String, java.lang.String, net.geant.autobahn.gui.ReservationChangedType, java.lang.String)
	 */
	public void reservationChanged(String idm, String serviceId, String resID,
			ReservationChangedType state, String message) {
		if (serviceId == null)
			serviceId = resID.substring(0, resID.lastIndexOf("_res_"));
		InterDomainManager manager = idms.get(idm);
		if (manager == null){
			return;
		}
		ServiceType service = manager.getService(serviceId);
		if (service == null)
			return;
		List<Reservation> list = new ArrayList<Reservation>();
		list.addAll(service.getReservations());
		int stateOfReservation  = convertReservationTypes(state);
		if (stateOfReservation==SCHEDULED){
			manager.forceUpdateService(serviceId);
		}
		int length = list.size();
		Reservation reservation =null;
		for (int i=0;i<length;i++){
			reservation = list.get(i);
			if (reservation.getBodID().equals(resID)){
				reservation.setState(stateOfReservation);
				break;
			}
		}
	}
	public void checkIDMavailability(){
		if (!idms.isEmpty()) {
			Iterator<String> iterator = idmsTime.keySet().iterator();
			do 	{
					String nextElement = iterator.next();
           	
		            	if (System.currentTimeMillis() - idmsTime.get(nextElement) > tearDownTime) {
			            	logger.info("IDM tear down "+nextElement);
			            	idms.remove(nextElement);
			            	
			            }
					                		            
	        } while (iterator.hasNext());
        }
		else
			logger.info("IDMs not availabale ");

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#statusUpdated(java.lang.String, java.lang.String, net.geant.autobahn.administration.Status)
	 */
	public void statusUpdated(String idm, String idmUrl, Status status) {
		
		InterDomainManager manager = idms.get(idm);
		if (manager == null) {
			manager = newInterDomainManagerConnected(idm, idmUrl);
		}
		
		manager.setStatus(status);
		
		if (!idmsTime.containsKey(idm)) {
			idmsTime.put(idm, manager.getLastStatusUpdateInMillis());
		} else {
			idmsTime.replace(idm, manager.getLastStatusUpdateInMillis());			
		}
				
		if (manager.getLastStatusUpdateInMillis()>tearDownTime) {
			try{
				manager.getServices(true);
				manager.getProperties();
				manager.getLog(true);
			}catch (Exception e){
				logger.info (e.getClass().getName()+":"+e.getMessage());
			}
		}		
		if (notifier!= null)	
				notifier.updateTopology();
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getLogsInterDomainManager(java.lang.String, boolean, boolean)
	 */
	public String getLogsInterDomainManager(String idm, boolean refresh, boolean all) {
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			return null;	
		return manager.getLog(refresh, all);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationStates()
	 */
	public String[] getReservationStates (){
		return reservationStates;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkUserAccessPointConnection(java.lang.String)
	 */
	public boolean checkUserAccessPointConnection(String idm) {
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			return false;
		return manager.isUserAccessPointConnected();
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkAdminstrationConnection(java.lang.String)
	 */
	public boolean checkAdminstrationConnection(String idm) {
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			return false;
		return manager.isAdmnistrationConnected();
	}
	
	private void logServiceRequest (ServiceRequest service ){
		StringBuffer buffer =new StringBuffer ();
		buffer.append ("Service:").append(service.getUserName()).append(",").append(service.getUserHomeDomain());
		if (service.getReservations()==null)
			logger.info( buffer.toString());
		buffer.append("\n");
		int length = service.getReservations().size();
		ReservationRequest request = null;
		for (int i=0;i<length;i++){
			request =  service.getReservations().get(i);
			if (request != null)
			{
				buffer.append("Reservation"+request.getStartTime()+":"+request.getEndPort());
			}
		}
		//logger.info(buffer.toString());
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#submitServiceAtInterDomainManager(java.lang.String, net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public String submitServiceAtInterDomainManager(String idm, ServiceRequest request) throws UserAccessPointException, ManagerException {
		logger.info("Submitting service");
		if (request == null){
			throw new ManagerException(ManagerException.NO_SERVICE, "Sumitting null service");
		}
		if (request.getReservations()== null || request.getReservations().isEmpty()){
			throw new ManagerException(ManagerException.SERVICE_WITHOUT_RESERVATIONS, "Empty service submitted");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Set<String> authorities = AuthorityUtils.authorityArrayToSet(auth.getAuthorities());
    	UserAuthParameters authParameters = new UserAuthParameters(auth.getName(), authorities);
        
        for (int i=0; i<request.getReservations().size(); i++) {
        	request.getReservations().get(i).setAuthParameters(authParameters);
        }
        
		logger.info("Verified");
		logServiceRequest(request);
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			throw new ManagerException (ManagerException.UNKNOWN_MANAGER,"No manager with name:"+idm);
		logger.info("Verified");
		synchronized (manager) {
			String  id = manager.submitService(request);
			logger.info ("submitted");
			return id;	
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkReservationPossibility(java.lang.String, net.geant.autobahn.useraccesspoint.ReservationRequest)
	 */
	public ReservationTest checkReservationPossibility(String idm,
			ReservationRequest request) throws UserAccessPointException {
		InterDomainManager manager = idms.get(idm);
		logger.info ("check reservation possibility"+ manager== null);
		ReservationTest test = new ReservationTest();
		if (manager == null) {
			test.setStatus(false);
		}
		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Set<String> authorities = AuthorityUtils.authorityArrayToSet(auth.getAuthorities());
	    	UserAuthParameters authParameters = new UserAuthParameters(auth.getName(), authorities);
	        request.setAuthParameters(authParameters);

			test.setStatus(manager.checkReservationPossibility(request));
		}
		return test;	
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getPropertiesForInterDomainManager(java.lang.String)
	 */
	public List<KeyValue> getPropertiesForInterDomainManager(String idm) {
		InterDomainManager manager = idms.get(idm);
		if (manager != null)
			return manager.getProperties();
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#setPropertiesForInterDomainManager(java.lang.String, java.util.List)
	 */
	public void setPropertiesForInterDomainManager(String idm, List<KeyValue> properties) {
		InterDomainManager manager = idms.get(idm);
		if (manager != null)
			manager.setProperties(properties);
			
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceStates()
	 */
	public String[] getServiceStates() {
		return serviceStates;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServicesFromInterDomainManager(java.lang.String)
	 */
	public List<ServiceType> getServicesFromInterDomainManager(String idm) {
		InterDomainManager manager= idms.get(idm);
		if (manager != null){
			return manager.getServices(false);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#cancelServiceInInterDomainManager(java.lang.String, java.lang.String)
	 */
	public void cancelServiceInInterDomainManager(String idm,
			String serviceID) throws UserAccessPointException {
				
		if (idm==null || serviceID == null)
			return;
		InterDomainManager manager= idms.get(idm);
		
		if ((manager != null) && ( manager.getService(serviceID) != null )){
			
			logger.info("Canceling service: "+idm+":"+serviceID);
			manager.cancelService(serviceID);
		}
		else
			logger.info("Service can not be canceled: "+idm+":"+serviceID);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#update(java.lang.String, net.geant.autobahn.gui.EventType, java.util.List)
	 */
	public void update(String idm, EventType event,
			List<KeyValue> properties) {
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManager(java.lang.String)
	 */
	public InterDomainManager getInterDomainManager(String idm) {
		return idms.get(idm);
	}
	/**
	 * Sets the topolofy notifier
	 * @return topology notifier
	 */
	public TopologyFinderNotifier getNotifier() {
		return notifier;
	}

	public void setNotifier(TopologyFinderNotifier notifier) {
		this.notifier = notifier;
	}
	/**
	 * Converts the reservation state known by WEB GUI
	 * @param state reservation state
	 * @return return state known by WEB GUI
	 */
	public int  convertReservationTypes (ReservationChangedType state){
		if (state==ReservationChangedType.ACTIVE)
			return ACTIVE;
		else
		if (state==ReservationChangedType.CANCELLED)
			return CANCELLED;
		else
		if (state==ReservationChangedType.FAILED)
			return FAILED;
		else
		if (state==ReservationChangedType.FINISHED)
			return FINISHED;
		else
		if (state==ReservationChangedType.SCHEDULED)
			return SCHEDULED;
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceFromInterDomainManager(java.lang.String, java.lang.String)
	 */
	public ServiceType getServiceFromInterDomainManager(String idm,String serviceId) {
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			return null;
		else
			return manager.getService(serviceId);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#mapPort(java.lang.String)
	 */
	public String mapPort(String port) {		
		if (portsMapper!= null)
			return portsMapper.mapPort(port);
		return port;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#demapPort(java.lang.String)
	 */
	public String demapPort (String mapping){
		if (portsMapper != null)
			portsMapper.demapPort(mapping);
		return mapping;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getTearDownTime()
	 */
	public long getTearDownTime() {
		return tearDownTime;
	}
	/** 
	 * Sets period of time after it the registered IDM is mark as not accessible
	 * @param tearDownTime period in mili seconds
	 */
	public void setTearDownTime(long tearDownTime) {
		this.tearDownTime = tearDownTime;
	}
	
	public PortsMapper getPortsMapper() {
		return portsMapper;
	}

	public void setPortsMapper(PortsMapper portsMapper) {
		this.portsMapper = portsMapper;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#demapPortsForReservationRequest(net.geant.autobahn.useraccesspoint.ReservationRequest)
	 */
	public void demapPortsForReservationRequest(ReservationRequest reservation) {
		reservation.setStartPort(demapPort(reservation.getStartPort()));
		reservation.setEndPort(demapPort(reservation.getEndPort()));
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#demapPortsForsServiceRequest(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public void demapPortsForsServiceRequest(ServiceRequest service) {
		for(ReservationRequest reservation:service.getReservations()){
			reservation.setStartPort(demapPort(reservation.getStartPort()));
			reservation.setEndPort(demapPort(reservation.getEndPort()));
		}
	}
	/**
	 * Get default startTime and endTime strings
	 * 
	 * @return array with startTime and endTime 
	 */
	public String[] getTimes (){
		String[] times = new String[2];
		TimeZone time = TimeZone.getTimeZone(timezone);
		if (time== null)
			time=TimeZone.getTimeZone("UTC");
		GregorianCalendar calender = (GregorianCalendar) GregorianCalendar.getInstance(time);
		Date dat = new Date ();
		calender.setTime(dat);
		calender.add(Calendar.MINUTE, 15);
		dat = calender.getTime();
		//SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
		times[0]=formatter.format(dat);
		calender.add(Calendar.HOUR, 1);
		dat = calender.getTime();
		times[1]= formatter.format(dat);
		return times;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationRequestTemplate()
	 */
	public ReservatiomDepandentOnTimezone getReservationRequestTemplate() {
		ReservatiomDepandentOnTimezone res = new ReservatiomDepandentOnTimezone();
		ReservationRequest reservation = new ReservationRequest();
		String[] times = getTimes();
		reservation.setPriority(Priority.NORMAL);
		reservation.setResiliency(Resiliency.NONE);
		reservation.setCapacity(1000);
		try {
			reservation.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(times[0]).toGregorianCalendar());
			reservation.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(times[1]).toGregorianCalendar());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		res.setRequest(reservation);
		res.setTimezone(timezone);
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceRequestTemplate()
	 */
	public ServiceRequest getServiceRequestTemplate() {
		ServiceRequest service= new ServiceRequest();
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (obj instanceof UserDetails) {
			username = ((UserDetails)obj).getUsername();
		} else 
			username = obj.toString();
		if (username == null)
			username= "test";
		//service.setUserEmail("test.user@test.domain");
		service.setUserName(username);
		List<String> idms = getAllInterdomainManagers();
		if (idms!= null && !idms.isEmpty())
			service.setUserHomeDomain(idms.get(0));
		return service;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#mapPortsForReservationRequest(net.geant.autobahn.useraccesspoint.ReservationRequest)
	 */
	public void mapPortsForReservationRequest(ReservationRequest reservation) {
		if (reservation.getStartPort()!= null)
			reservation.setStartPort(mapPort(reservation.getStartPort()));
		if (reservation.getEndPort()!= null)
		reservation.setEndPort(mapPort(reservation.getEndPort()));
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#mapPortsForServiceRequest(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public void mapPortsForServiceRequest(ServiceRequest service) {
		for(ReservationRequest reservation:service.getReservations()){
			mapPortsForReservationRequest(reservation);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getMappedAllPorts()
	 */
	public List<String> getMappedAllPorts() {
		List<String> allPorts = getAllPorts();
		if (allPorts==null)
			return null;
		List<String> mappedAllPorts = new ArrayList <String>();
		for (String port:allPorts){
			mappedAllPorts.add(mapPort(port));
		}	
		return mappedAllPorts;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getMappedInterDomainManagerPorts(java.lang.String)
	 */
	public List<String> getMappedInterDomainManagerPorts(String idm) {
		List<String> domainPorts = getInterDomainManagerPorts(idm);
		if (domainPorts==null)
			return null;
		List<String> mappedDomainPorts = new ArrayList <String>();
		String mappedPort = null;
		for (String port:domainPorts){
			mappedPort = mapPort(port);
			mappedDomainPorts.add(mappedPort);
		}
		return mappedDomainPorts;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationPriorities()
	 */
	public List<String> getReservationPriorities (){
		List<String> list = new ArrayList<String>();
		for (Priority req: Priority.values())
			list.add(req.toString());
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationResiliencies()
	 */
	public List<String> getReservationResiliencies (){
		List<String> list = new ArrayList<String>();
		for (Resiliency req: Resiliency.values())
			list.add(req.toString());
		return list;
	}

	public List<String> getTimezones() {
		return timezones;
	}


	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	
	
	     // Change a date to GMT from a given timezone  
	     public static Date toGmtFromZone(Date date, String fromZone) {  
	         TimeZone pst = TimeZone.getTimeZone(fromZone);  
	         return new Date(date.getTime() - pst.getRawOffset());  
	     }  
	   

	public void convertTimeToApplicationTimezone(String timezone, ReservationRequest request){
		
		logger.info ("Modifing timezone:"+timezone);
		try {
			if (timezone == null || timezone.equals("UTC")|| timezone.equals("GMT")|| this.timezone.equals(timezone)) {
				return;
			}
			TimeZone applicationTimeZone = TimeZone.getTimeZone(this.timezone);
			if (applicationTimeZone== null)
				applicationTimeZone= TimeZone.getTimeZone("UTC");
			TimeZone pst = TimeZone.getTimeZone(timezone); 
			Calendar startTime= request.getStartTime();
			Calendar endTime= request.getEndTime();
	        GregorianCalendar startDate=(GregorianCalendar)Calendar.getInstance();
	        startDate.setTimeInMillis(startTime.getTimeInMillis()-pst.getRawOffset()+applicationTimeZone.getRawOffset());
	        GregorianCalendar endDate=(GregorianCalendar)Calendar.getInstance();
	        endDate.setTimeInMillis(endTime.getTimeInMillis()-pst.getRawOffset()+applicationTimeZone.getRawOffset());
	        
	        try {
				request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate).toGregorianCalendar());
				request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate).toGregorianCalendar());
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertTimeToTimezone(String timezone, ReservationRequest request){	
		logger.info ("Modifing timezone:"+timezone);
		if (timezone == null || timezone.equals("UTC")|| timezone.equals("GMT")|| this.timezone.equals(timezone))
			return;
		TimeZone applicationTimeZone = TimeZone.getTimeZone(this.timezone);
		if (applicationTimeZone== null)
			applicationTimeZone= TimeZone.getTimeZone("UTC");
		TimeZone pst = TimeZone.getTimeZone(timezone); 
		Calendar startTime= request.getStartTime();
		Calendar endTime= request.getEndTime();
        GregorianCalendar startDate=(GregorianCalendar)Calendar.getInstance();
        startDate.setTimeInMillis(startTime.getTimeInMillis()+pst.getRawOffset()-applicationTimeZone.getRawOffset());
        GregorianCalendar endDate=(GregorianCalendar)Calendar.getInstance();
        endDate.setTimeInMillis(endTime.getTimeInMillis()+pst.getRawOffset()-applicationTimeZone.getRawOffset());
        try {
			request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate).toGregorianCalendar());
			request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate).toGregorianCalendar());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}	
	}


	public ServicesFormModel getSubmitedServicesInIDM(String idm) {
		ServicesFormModel serv = new ServicesFormModel();
		List<String> managers = getAllInterdomainManagers();
		InterDomainManager manager = null;
		logger.info("managers:"+managers);
		if (managers== null || managers.size()==0)
			return serv;
		serv.setIdms(managers);
		if (idm ==null){
			serv.setCurrentIdm(managers.get(0));
			manager = idms.get(managers.get(0));
			serv.setComparator(new ServicesComparator());
			serv.setServices(manager.getServices());
			
		} else { 
			manager = idms.get(idm);
			List<ServiceType> services = manager.getServices();
			serv.setComparator(new ServicesComparator());
			serv.setServices(services);
			serv.setCurrentIdm(idm);
		}
	
		
		//Filtering submitted services		
		boolean isAdmin=AuthorityUtils.userHasAuthority("ROLE_ADMINISTRATOR");
		
		if(!isAdmin && serv.getServices()!=null) {
			//Filtering by username
			List<ServiceType> filteredServices=new ArrayList<ServiceType>();
			
			//TODO: consider using the same way that getServiceRequestTemplate gets the username
			String contextUsername=SecurityContextHolder.getContext().getAuthentication().getName();
			
			for (ServiceType servType: serv.getServices()) {
				
				String serviceID = servType.getBodID();
				if(manager.getService(serviceID) == null)
					continue;
				//Get the username of the first reservation as username
				String servUsername=servType.getUser().getName();
				if(servUsername==null) continue;
			
				if(servUsername.equals(contextUsername)) {
					filteredServices.add(servType);
				}
			}
			
			serv.setServices(filteredServices);
		}
		
		return serv;
	}

	
	public LogsFormModel getLogsForInterDomainManager(String idm) {
		LogsFormModel serv =  new LogsFormModel();
		List<String > managers = 	getAllInterdomainManagers();
		serv.setIdms(managers);
		if (managers== null || managers.isEmpty()){
			serv.setError("There is no log provided");
			serv.setLogs("");
			serv.setError("There is no settings provided");
			return serv;
		}
		if (idm ==null){
			idm= managers.get(0);
		}
		serv.setLogs (getLogsInterDomainManager(idm, true, true));
		serv.setCurrentIdm(idm);
		if (serv.getLogs()== null)
				serv.setLogs("");
		
		return serv;
	}


	public SettingsFormModel getSettingsForInterDomainManager(String idm) {
		SettingsFormModel serv =  new SettingsFormModel();
		List<String > managers = 	getAllInterdomainManagers();
		serv.setIdms(managers);
		if (managers== null || managers.isEmpty()){
			serv.setError("There is no settings provided");
			return serv;
		}
		if (idm ==null){
			idm = managers.get(0);
		}
		InterDomainManager manager = idms.get(idm);
		try {
		    serv.setProperties(manager.getProperties());
		} catch (Exception e){
		    logger.error("No settings");
		    serv.setProperties(null);
		}
		serv.setCurrentIdm(idm);
		return serv;
	}


	public ServicesFormModel getSubmitedServicesInInterDomainManager(String idm) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<ServiceType> sortServicesByBodyID(List<ServiceType> list){
						
		Comparator<ServiceType> comparator = new ServicesComparator();
		Collections.sort(list, comparator);

		return list;
	}
	public String setParameter(String param){	
		return param;
	}
	public int getNumberOfAvailableIDMs(){
		if(idms == null)
			return -1;		
		
		return idms.size();
	}
}
