package net.geant.autobahn.autoBahnGUI.manager;
import java.util.List;

import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
/**
 * Interface for WEB GUI Manager
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface Manager {

	/**
	 * Gets array of all string representations of  Reservation states members
	 * @return array with string Reservation types members 
	 */
	public String[] getReservationStates();
	/**
	 * Gets array of all string representations of  ReservationService states members
	 * @return array with string ReservationService types members 
	 */
	public String[] getServiceStates();
	/**
	 * Gets list of all of IDMs names registered in WEB GUI
	 * @return array with string ReservationService types members 
	 */
	public List<String> getAllInterdomainManagers ();
	/**
	 * Gets list of all services from all IDMs registered in WEB GUI
	 * @return list of Service objects  
	 */
	public List<ServiceType> getServicesForAllInterDomainManagers();
	/**
	 * Gets list of all IDMs registered in WEB GUI
	 * @return list of InterDomain  types members 
	 */
	public List<InterDomainManager> getInterDomainManagers();
	/**
	 * Check if UserAccessPoint web service interface for IDM is accessible
	 * @return true if interface is working 
	 */
	public boolean checkUserAccessPointConnection (String idm);
	/**
	 * Check if Administration web service interface for IDM is accessible
	 * @return true if interface is accessible 
	 */
	public boolean checkAdminstrationConnection (String idm);
	/**
	 * Submits the ServiceRequest in IDM by the UserAccessPoint interface
	 * @throws UserAccessPointException_Exception if some connection problem appears or user cannot request service
	 * @throws ManagerException 
	 */
	public String submitServiceAtInterDomainManager (String idm, ServiceRequest request)throws UserAccessPointException, ManagerException;
	/**
	 * Gets list of configuration properties for specified IDM 
	 * 
	 * @param idm identifier name of the IDM
	 * @return	list of KeyValue
	 */
	public List<KeyValue> getPropertiesForInterDomainManager (String idm);
	/**
	 * Sets list of configuration properties for specified IDM registerd in WEB GUI 
	 * 
	 * @param idm identifier of the IDM
	 * @param list of KeyValue
	 */
	public void setPropertiesForInterDomainManager (String idm, List<KeyValue> properties);
	/**
	 * Gets list of all services for specified IDM registered in WEB GUI
	 * @param idm identifier of the IDM
	 * @return list of Service objects  
	 */
	public List<ServiceType> getServicesFromInterDomainManager (String idm);
	/**
	 * Gets specified service from specified IDM registered in WEB GUI
	 * 
	 * @param idm identifier of the IDM 
	 * @param serviceId identifier of the search service
	 * @return Service if exist, null if not
	 */
	public ServiceType getServiceFromInterDomainManager (String idm, String serviceId);
	/**
	 * Cancel specified service in specified IDM ristered in WEB GUI
	 *  
	 * @param idm identifier of the IDM 
	 * @param serviceId identifier of the search service
	 * @throws UserAccessPointException_Exception if some connection problem or cancelling error appear
	 */
	public void cancelServiceInInterDomainManager(String idm,String serviceId) throws UserAccessPointException;
	/**
	 * Gets specified IDM registered in WEB GUI
	 * @param idm identifier of the IDM
	 * @return InterDomainManager object if exist, if not null
	 */
	public InterDomainManager getInterDomainManager (String idm);
	/**
	 * Gets logged information from specified IDM registered in WEB GUI
	 * @param idm identifier of the IDM
	 * @return String log information
	 */
	public String getLogsInterDomainManager(String idm, boolean b, boolean c);
    /**
     * Gets all IDCP port names in all IDM registered in WEB GUI
     * @return list of  ports names
     */
    public List<String> getAllIdcpPorts ();
	/**
	 * Gets all port names in all IDM registered in WEB GUI
	 * @return list of  ports names
	 */
	public List<String> getAllPorts ();
	
    /**
     * Gets all port names (with associated friendly ones from LS) in all IDM
     * registered in WEB GUI
     * @return list of ports names with associated friendly ones
     */
    public List<PortMap> getAllFriendlyPorts ();
    
    /**
     * Gets all port names (with associated friendly ones from LS) in all IDM
     * registered in WEB GUI
     * Also gets IDCP ports
     * @return list of ports names with associated friendly ones
     */
    public List<PortMap> getAllFriendlyAndIdcpPorts ();

    /**
     * Gets all domain names
     * @return list of domains names
     */
    public List<String> getAllDomains();
    
    /**
     * Gets all non-client domain names
     * @return list of domains names
     */
    public List<String> getAllDomains_NonClient();
    
    /**
     * Gets all link names
     * @return list of links names
     */
    public List<String> getAllLinks();
    
    /**
     * Gets all link names that do not attach to a client domain
     * @return list of links names
     */
    public List<String> getAllLinks_NonClient();
    
	/**
	 * Gets names of ports managed by specified IDM
	 * @param idm identifier of the IDM
	 * @return list of  ports names
	 */
	public List<String> getInterDomainManagerPorts (String idm);

	/**
     * Gets names of ports (with associated friendly ones from LS) 
     * managed by specified IDM
	 * @param idm identifier of the IDM
	 * @return list of ports names with associated friendly ones from LS 
	 */
    public List<PortMap> getFriendlyInterDomainManagerPorts(String idmIdentifier);

	/**
	 * Gets time period after with the  registered earlier IDM is mark as not accessible
	 * @return
	 */
	public long getTearDownTime();
	/**
	 * Converts the real name of the port to virtual one
	 * 
	 * @param port real name	
	 * @return  virtual name
	 */
	public String mapPort (String port);
	/**
	 * Converts the virtual name of the port to real one
	 * @param mapping virtual name
	 * @return real name
	 */
	public String demapPort (String mapping);
	
	
	/**
	 * Checks if request reservation is possible to schedule
	 * @param idm identifier of the IDM
	 * @param request reservation request
	 * @return true if reservation is possible to schedule
	 */
	public ReservationTest  checkReservationPossibility(String idm,ReservationRequest request)throws UserAccessPointException;
	
	/**
	 * Gets ServiceRequest template for service request form
	 * @return ServiceRequest template
	 */
	public ServiceRequest getServiceRequestTemplate();
	
	/**
	 * Gets ReservationRequest template for reservation request form 
	 * @return ReservationRequest template
	 */
	public ReservatiomDepandentOnTimezone getReservationRequestTemplate();
	
	/**
	 * Demaps port in ReservationRequest
	 * @param reservation ReservationRequest
	 */
	public void demapPortsForReservationRequest (ReservationRequest reservation);
	
	/**
	 * Maps port in ReservationRequest
	 * @param reservation ReservationRequest
	 */
	public void mapPortsForReservationRequest (ReservationRequest reservation);
	
	/**
	 * Demaps port in ReservationRequest
	 * @param reservation ReservationRequest
	 */
	public void demapPortsForsServiceRequest (ServiceRequest service);
	
	/**
	 * Maps port in ServiceRequest
	 * @param reservation ServiceRequest
	 */
	public void mapPortsForServiceRequest (ServiceRequest service);
	
	/**
	 * Get mapped ports names manages by IDM
	 * @param idm identifier of IDM
	 * @return list of mapped ports names
	 */
	public List<String> getMappedInterDomainManagerPorts(String idm);
	
	/**
	 * Get mapped ports names manages by all IDMs
	 * @return list of mapped ports names
	 */
	public List<String> getMappedAllPorts();
	
	public List<String> getReservationPriorities();
	
	public List<String> getReservationResiliencies();
	/**
	 * Gets list of time zones
	 * @return	list of time zones
	 */
	public List<String> getTimezones ();
	/**
	 * Gets default time zone for Web GUI
	 * @return time zone name
	 */
	public String getTimezone ();
	
	/**
	 * Convert reservation time credentials from timezone to application` timezone
	 * @param request
	 */
	public void convertTimeToApplicationTimezone(String timezone, ReservationRequest request);
	
	/**
	 * Convert reservation time credentials to timezone
	 * 
	 * @timezone timezone
	 * @param request
	 */
	public void convertTimeToTimezone(String timezone, ReservationRequest request);
	
	/**
	 * Gets submitted services in IDM
	 * @param idm identifier of the IDM if null provides list of all IDM services
	 * @return ServicesFormModel
	 */
	public ServicesFormModel getSubmitedServicesInInterDomainManager(String idm);
	/**
	 * Gets SettingFormModel used in IDM setting view
	 * @param idm identifier of IDM
	 * @return	SettingsFormModel
	 */
	public SettingsFormModel getSettingsForInterDomainManager  (String idm);
	/**
	 * Gets LogsFormModel used in IDM logs view
	 * @param idm identifier of IDM
	 * @return LogsFormModel
	 */
	public LogsFormModel getLogsForInterDomainManager (String idm);
	
	public LookupService getLookupServiceObject();
	
	public String getFriendlyNamefromLS(String identifier);
	
	public boolean checkIDMavailability();
	

}

