package net.geant.autobahn.autoBahnGUI.manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.geant.autobahn.aai.UserAuthParameters;
import net.geant.autobahn.administration.AdministrationException;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.MapKeySetComparator;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServicesComparator;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.model.StatisticsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinderNotifier;
import net.geant.autobahn.gui.EventType;
import net.geant.autobahn.gui.ReservationChangedType;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.useraccesspoint.Mode;
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
	public static final int UNKNOWN = 0;
	public static final int ACCEPTED = 1;
    public static final int PATHFINDING = 2;
    public static final int LOCAL_CHECK = 3;
    public static final int SCHEDULING =4;
    public static final int SCHEDULED = 5;
    public static final int CANCELLING = 6;
    public static final int DEFERRED_CANCEL = 7;
    public static final int WITHDRAWING = 8;
    public static final int ACTIVATING = 9;
    public static final int ACTIVE = 10;
    public static final int FINISHING = 11;
    public static final int FINISHED = 21;
    public static final int CANCELLED = 22;
    public static final int FAILED = 23; 
	
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
	private static Logger logger = Logger.getLogger("ManagerImpl");
	
	/**
	 * Map with all ports available in each domain
	 */
 	private Map<String,String> ports = new HashMap<String,String>();
	
	
	/**
 	 * Maps the virtual port to real one and vice versa
 	 */
	private PortsMapper portsMapper;
	
	private LookupService lookupService;
	
	private String[] comparedLinks;
	
	private String[] comparedDomains;
	
	public ManagerImpl() {
		Properties properties = new Properties();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(
                        "../etc/webgui.properties");
            properties.load(is);
            is.close();
            logger.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            logger.info("Could not load lookuphost properties: " + e.getMessage());
        }
        String host = properties.getProperty("lookuphost");
        if (LookupService.isLSavailable(host)) {
            lookupService = new LookupService(host);
        } else {
            lookupService = null;
        }
        
        // Build user-friendly list of timezones
        for (int i =0; i<timezones.size(); i++) {
            String tzStr = timezones.get(i);
            TimeZone tz = TimeZone.getTimeZone(tzStr);
            int offset = tz.getRawOffset() / (60*1000);
            int offset_hrs = offset / 60;
            int offset_min = offset % 60;
            if (offset_hrs < 0) {
                timezones.set(i, String.format("(GMT%03d:%02d) %s", offset_hrs, -offset_min, tzStr));
            } else {
                timezones.set(i, String.format("(GMT+%02d:%02d) %s", offset_hrs, offset_min, tzStr));
            }
        }
        Collections.sort(timezones);
	}
	
    @Override
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
		refreshPorts();
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
    @Override
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
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllIdcpPorts(java.lang.String)
     */
    @Override
    public List<String> getAllIdcpPorts (String idmIdentifier) {
        if (idmIdentifier != null) {
            InterDomainManager manager = idms.get(idmIdentifier);
            if (manager == null) {
                logger.info("getAllIdcpPorts() could not find idm manager for " 
                        + idmIdentifier + ", will try with any other registered IDM");
                return getAllIdcpPorts();
            }

            String[] temp = manager.getIdcpPorts();

            List<String> ports = new ArrayList<String>();

            if (temp != null) {
                logger.info("--Got the following IDCP ports:");
                for (String link : temp) {
                    ports.add(link);
                    logger.info(link);
                }

                return ports;
            } else {
                return getAllIdcpPorts();
            }
        }

        // idmIndentifier is null
        return getAllIdcpPorts();
    }
    
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllIdcpPorts()
     */
    @Override
    public List<String> getAllIdcpPorts (){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
            logger.info("Getting IDCP ports from " + idm);
            InterDomainManager manager = idms.get(idm);
            String[] temp = manager.getIdcpPorts();
            
            List<String> ports = new ArrayList<String>();
            
            if (temp != null) {
                logger.info("--Got the following IDCP ports:");
                for(String link : temp) {
                    ports.add(link);
                    logger.info(link);
                }
                
                return ports; 
            }
        }
        
        return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllPorts(java.lang.String)
     */
    @Override
    public List<String> getAllPorts(String idmIdentifier) throws UserAccessPointException {
        if (idmIdentifier != null) {
            InterDomainManager manager = idms.get(idmIdentifier);
            if (manager == null) {
                logger.info("getAllPorts() could not find idm manager for " + idmIdentifier
                        + ", will try with any other registered IDM");
                return getAllPorts();
            }

            String[] temp = manager.getAllClientPorts();

            List<String> ports = new ArrayList<String>();

            if (temp != null) {
                logger.info("--Got the following ports:");
                for (String link : temp) {
                    ports.add(link);
                    logger.info(link);
                }

                return ports;
            } else {
                return getAllPorts();
            }
        }

        // idmIndentifier is null
        return getAllPorts();
    }
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllPorts()
     */
    @Override
    public List<String> getAllPorts () throws UserAccessPointException {
        // Parse through IDMs and get the first non-null result
        for (String idm : idms.keySet()) {
            logger.info("Getting client ports from " + idm);
            InterDomainManager manager = idms.get(idm);
            String[] temp = manager.getAllClientPorts();
            
            List<String> ports = new ArrayList<String>();
            
            if(temp != null) {
                logger.info("--Got the following ports:");
                for (String link : temp) {
                    ports.add(link);
                    logger.info(link);
                }
                
                return ports; 
            }
        }
        
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllFriendlyPorts(java.lang.String)
     */
    @Override
    public List<PortMap> getAllFriendlyPorts (String idm) throws UserAccessPointException {
        List<String> ports = getAllPorts(idm);
        List<PortMap> friendlyPorts = new ArrayList<PortMap>();
        
        for (String p_id : ports) {
            String friendlyName = getFriendlyNamefromLS(p_id);
            if (friendlyName == null || friendlyName.trim().equals("") 
                    || friendlyName.trim().equalsIgnoreCase("null")) {
                friendlyPorts.add(new PortMap(p_id, p_id));                
            } else {
                friendlyPorts.add(new PortMap(p_id, friendlyName.trim() + " (" + p_id + ")"));
            }
        }
        return friendlyPorts;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllFriendlyAndIdcpPorts(java.lang.String)
     */
    @Override
    public List<PortMap> getAllFriendlyAndIdcpPorts (String idm) throws UserAccessPointException {
        List<PortMap> friendlyPorts = getAllFriendlyPorts(idm);
        List<String> idcpPorts = this.getAllIdcpPorts(idm);
        
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
    @Override
    public String getFriendlyNamefromLS(String identifier) {
        if (lookupService == null) {
            return null;
        }

        String friendlyName = null;
        try {
            friendlyName = lookupService.queryFriendlyName(identifier);
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
    @Override
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
    @Override
    public List<String> getAllDomains_NonClient(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager = null;
        
        while (iterator.hasNext()) {
            manager = idms.get(iterator.next());
            if (manager != null) {
                List<String> domains = new ArrayList<String>();
            	
            	String[] temp = manager.getAllDomains_NonClient();
            	if (temp!=null) {
            		for(String domain : temp) {
            			domains.add(domain);
            		}
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
    @Override
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
    @Override
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
    @Override
	public List<String> getInterDomainManagerPorts (String idmIdentifier){
	    InterDomainManager manager = idms.get(idmIdentifier);
	    if (manager == null) {
	        System.out.println("idms.get manager is NULL");
	    	return null;
	    }
	    
    	String[] temp = manager.getDomainClientPorts();
    	if (temp == null) {
    	    System.out.println("idms manager is" + manager.getIdentifier());
    	    System.out.println("idms.get client ports is NULL");
    	    return null;
    	}
    	
	    List<String> ports = new ArrayList<String>();
	    for(String port : temp) {
	    	ports.add(port);
	    }
	    return ports;
	}
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getFriendlyInterDomainManagerPorts()
     */
    @Override
    public List<PortMap> getFriendlyInterDomainManagerPorts(String idmIdentifier) {
        List<String> ports = getInterDomainManagerPorts(idmIdentifier);
        List<PortMap> friendlyPorts = new ArrayList<PortMap>();
        if (ports == null){
            return null;
        }
        for (String p_id : ports) {
            String friendlyName = getFriendlyNamefromLS(p_id);
            if (friendlyName == null || friendlyName.trim().equals("")
                    || friendlyName.trim().equalsIgnoreCase("null")) {
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
	
    @Override
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
						
			List<ReservationType> res = list.get(i).getReservations();	
			for (int j = 0; j < res.size(); j++) {
				
				if(res.get(j).getPath() == null)
					continue;
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
    @Override
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
		refreshPorts();
		if (manager != null) {
			idms.put(idmName, manager);
		}
		return manager;
	}
	
	private void refreshPorts() {
        ports.clear();
        try {
            getAllPorts();
        } catch (UserAccessPointException e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#reservationChanged(java.lang.String, java.lang.String, java.lang.String, net.geant.autobahn.gui.ReservationChangedType, java.lang.String)
	 */
    @Override
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
		List<ReservationType> list = new ArrayList<ReservationType>();
		list.addAll(service.getReservations());
		int stateOfReservation  = convertReservationTypes(state);
		if (stateOfReservation==SCHEDULED){
			manager.forceUpdateService(serviceId);
		}
		int length = list.size();
		ReservationType reservation = null;
		for (int i=0;i<length;i++){
			reservation = list.get(i);
			if (reservation.getBodID().equals(resID)){
				reservation.setState(stateOfReservation);
				break;
			}
		}
	}

	@Override
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
			logger.info("IDMs not available ");

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#statusUpdated(java.lang.String, java.lang.String, net.geant.autobahn.administration.Status)
	 */
    @Override
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
				
		if (manager.getLastStatusUpdateInMillis() > tearDownTime) {
			try{
				manager.getServices(true);
				manager.getProperties();
				manager.getLog(true);
			}catch (Exception e){
				logger.info (e.getClass().getName()+":"+e.getMessage());
			}
		}
		if (notifier != null)	
				notifier.updateTopology(idm);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getLogsInterDomainManager(java.lang.String, boolean, boolean)
	 */
    @Override
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
    @Override
	public String[] getReservationStates (){
		return reservationStates;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkUserAccessPointConnection(java.lang.String)
	 */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
	public void setPropertiesForInterDomainManager(String idm, List<KeyValue> properties) {
		InterDomainManager manager = idms.get(idm);
		if (manager != null)
			manager.setProperties(properties);
			
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceStates()
	 */
    @Override
	public String[] getServiceStates() {
		return serviceStates;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServicesFromInterDomainManager(java.lang.String)
	 */
    @Override
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
    @Override
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
    @Override
	public void update(String idm, EventType event,
			List<KeyValue> properties) {
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManager(java.lang.String)
	 */
    @Override
	public InterDomainManager getInterDomainManager(String idm) {
		return idms.get(idm);
	}

	/**
	 * Sets the topology notifier
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
	public int convertReservationTypes (ReservationChangedType state){
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
    @Override
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
    @Override
	public String mapPort(String port) {		
		if (portsMapper!= null)
			return portsMapper.mapPort(port);
		return port;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#demapPort(java.lang.String)
	 */
    @Override
	public String demapPort (String mapping){
		if (portsMapper != null)
			portsMapper.demapPort(mapping);
		return mapping;
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getTearDownTime()
	 */
    @Override
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
    @Override
	public void demapPortsForReservationRequest(ReservationRequest reservation) {
		
		reservation.getStartPort().setAddress(demapPort(reservation.getStartPort().getAddress()));
		reservation.getEndPort().setAddress(demapPort(reservation.getEndPort().getAddress()));
		
		//reservation.setStartPort(demapPort(reservation.getStartPort()));
		//reservation.setEndPort(demapPort(reservation.getEndPort()));
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#demapPortsForsServiceRequest(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
    @Override
	public void demapPortsForsServiceRequest(ServiceRequest service) {
		for(ReservationRequest reservation : service.getReservations()){
			
			reservation.getStartPort().setAddress(demapPort(reservation.getStartPort().getAddress()));			
			
			reservation.getEndPort().setAddress(demapPort(reservation.getEndPort().getAddress()));
			
		//	reservation.setStartPort(demapPort(reservation.getStartPort()));
		//	reservation.setEndPort(demapPort(reservation.getEndPort()));
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
    @Override
	public ReservatiomDepandentOnTimezone getReservationRequestTemplate() {
		ReservatiomDepandentOnTimezone res = new ReservatiomDepandentOnTimezone();
		ReservationRequest reservation = new ReservationRequest();
		String[] times = getTimes();
		reservation.setPriority(Priority.NORMAL);
		reservation.setResiliency(Resiliency.NONE);
		reservation.setCapacity(10);
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
    @Override
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
    @Override
	public void mapPortsForReservationRequest(ReservationRequest reservation) {
		if (reservation.getStartPort()!= null)
			reservation.getStartPort().setAddress(mapPort(reservation.getStartPort().getAddress()));
			//reservation.setStartPort(mapPort(reservation.getStartPort()));
		if (reservation.getEndPort()!= null)
			//reservation.setEndPort(mapPort(reservation.getEndPort()));
		reservation.getEndPort().setAddress(mapPort(reservation.getStartPort().getAddress()));
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#mapPortsForServiceRequest(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
    @Override
	public void mapPortsForServiceRequest(ServiceRequest service) {
		for(ReservationRequest reservation:service.getReservations()){
			mapPortsForReservationRequest(reservation);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getMappedAllPorts()
	 */
    @Override
	public List<String> getMappedAllPorts() throws UserAccessPointException {
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
    @Override
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
    @Override
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
    @Override
	public List<String> getReservationResiliencies (){
		List<String> list = new ArrayList<String>();
		for (Resiliency req: Resiliency.values())
			list.add(req.toString());
		return list;
	}
	
    @Override
	public List<String> getReservationModes() {
		List<String> list = new ArrayList<String>();
		for (Mode req : Mode.values())
			list.add(req.toString());
		return list;
	}
	
	public List<String> getTimezones() {
		return timezones;
	}

    @Override
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

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#convertTimeToApplicationTimezone(java.lang.String, net.geant.autobahn.useraccesspoint.ReservationRequest)
     */
    @Override
	public void convertTimeToApplicationTimezone(String userZoneStr, ReservationRequest request){
		
		logger.info ("Converting times for request " + request + 
		        " according to timezone " + userZoneStr);
		try {
			if (userZoneStr == null) {
				return;
			}
			
	        // Cut user-friendly text from timezone String
	        String[] split_tz = userZoneStr.split("\\) ");
	        userZoneStr = split_tz[split_tz.length-1];
			TimeZone userZone = TimeZone.getTimeZone(userZoneStr);
			
			Calendar startTime = request.getStartTime();
	        GregorianCalendar startTimeShifted = (GregorianCalendar) Calendar.getInstance();
            startTimeShifted.setTimeZone(userZone);
	        startTimeShifted.setTimeInMillis(startTime.getTimeInMillis() - 
	                userZone.getRawOffset() + startTime.getTimeZone().getRawOffset());
            
            Calendar endTime = request.getEndTime();
	        GregorianCalendar endTimeShifted = (GregorianCalendar) Calendar.getInstance();
	        endTimeShifted.setTimeZone(userZone);
	        endTimeShifted.setTimeInMillis(endTime.getTimeInMillis() - 
	                userZone.getRawOffset() + endTime.getTimeZone().getRawOffset());
	        
	        try {
				request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeShifted).toGregorianCalendar());
				request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeShifted).toGregorianCalendar());
				logger.info("Request was shifted to " + request);
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
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

    @Override
	public LogsFormModel getLogsForInterDomainManager(String idm) {
		LogsFormModel serv =  new LogsFormModel();
		List<String > managers = 	getAllInterdomainManagers();
		serv.setIdms(managers);
		if (managers== null || managers.isEmpty()){
			serv.setError("There is no log provided");
			serv.setLogs("");
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

    @Override
    public StatisticsFormModel getStatisticsForInterDomainManager(String idm) {
        StatisticsFormModel serv = new StatisticsFormModel();
        List<String> managers = getAllInterdomainManagers();
        serv.setIdms(managers);
        if (managers == null || managers.isEmpty()){
            serv.setError("No IDM could be found. No statistics provided");
            return serv;
        }
        if (idm == null){
            idm = managers.get(0);
        }
        InterDomainManager manager = idms.get(idm);
        try {
            serv.setStatistics(manager.getStatistics(true));
        } catch (Exception e){
            logger.error("No statistics could be retrieved.");
            serv.setStatistics(null);
        }
        serv.setCurrentIdm(idm);
        return serv;
    }

    @Override
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

    @Override
	public ServicesFormModel getSubmitedServicesInInterDomainManager(String idm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceType> sortServicesByBodyID(List<ServiceType> list){
						
		Comparator<ServiceType> comparator = new ServicesComparator();
		Collections.sort(list, comparator);

		return list;
	}
	
    @Override
	public String setParameter(String param){	
		return param;
	}

    @Override
	public void convertCapacity(ReservationRequest request){
		
		long capacity = request.getCapacity();
		request.setCapacity(capacity * 1000000 );
	}
	
    @Override
	public Map<String,String> getAllAvailablePorts(String idm) throws UserAccessPointException{
		
		if(ports.size() == 0){
			
			List<PortMap> ports_all = getAllFriendlyPorts(idm);
			if(ports_all == null & ports_all.size()==0)
				return null;
			
			for (PortMap portMap : ports_all) {
				ports.put(portMap.getIdentifier(), portMap.getFriendlyName());
			}
			if(ports.size() == 0)
				return null;
		}
		return ports;
	}
	
    @Override
	public String getFriendlyNamePort(String port) throws UserAccessPointException{
		
		if(port == null & port.length() == 0)
			return null;
		
		if(ports.size() == 0)
			getAllAvailablePorts(null);
		
		return ports.get(port);
	}
	
    @Override
	public List<LinkMap> getAllDomainLinks(){
		
		List<String> str = getAllLinks_NonClient();
		//List<String> domainLinks = new ArrayList<String>();
		List<LinkMap> domainLinks = new ArrayList<LinkMap>();
			
		for(String idm : idms.keySet()) {	
	        InterDomainManager manager = idms.get(idm);
	        List<Link> links = manager.getTopology();
	        	for (Link link : links) {
	        		if(str.contains(link.getBodID())){
	        			String path = new String();
        				if(link.getStartDomainID().equalsIgnoreCase(link.getEndDomainID()))
        					path = " ["+link.getBodID() +"] Internal Link "+link.getStartDomainID();
        				else
        					path = " ["+link.getBodID() +"] from "+link.getStartDomainID()+" to "+link.getEndDomainID();
	        			
	        			//domainLinks.add(path);
	        			domainLinks.add(new LinkMap(link.getBodID(), path));
	        		}	
				}
	        	break;
	    }
			
		if(domainLinks.size() == 0)
			return null;
			
		return domainLinks;
	}

    @Override
    public boolean checkTopology(String idm){
    	
    	if(idms != null && idms.size() > 0){
    		
    		if(!idms.containsKey(idm))
    			return false;
    		
    		InterDomainManager manager = idms.get(idm);
    		if(manager == null)
    			return false;
    	
    		String[] links = manager.getAllLinks();
    		String[] domains = manager.getAllDomains();
    	
    		if(comparedLinks != links || comparedDomains != domains){
    			comparedLinks = links;
    			comparedDomains = domains;
    			
    			return true;
    		}
    	}
    	return false;
    }
    
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#handleTopologyChange(java.lang.String, boolean)
     */
    @Override
    public void handleTopologyChange(String idmParam, boolean deleteReservations) {

        InterDomainManager manager = idms.get(idmParam);
        if (manager != null){
            logger.info("Restarting IDM that caused topology change: " + idmParam);
            try {
                manager.handleTopologyChange(deleteReservations);
            } catch (AdministrationException e) {
                // Log the problem and continue to the other IDMs
                logger.info(e.getMessage());
            }
        }
        
        // Parse through IDMs and get the first non-null result
        for (String idm : idms.keySet()) {
            manager = idms.get(idm);
            
            if(idm != null && !idm.equals(idmParam)) {
                logger.info("Restarting IDM " + idm);
                try {
                    manager.handleTopologyChange(deleteReservations);
                } catch (AdministrationException e) {
                    // Log the problem and continue to next IDM
                    logger.info(e.getMessage());
                }
            }
        }
        
    }

}
