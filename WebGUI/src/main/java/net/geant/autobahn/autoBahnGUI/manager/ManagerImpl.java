package net.geant.autobahn.autoBahnGUI.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;


import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinderNotifier;
import net.geant.autobahn.gui.EventType;
import net.geant.autobahn.gui.ReservationChangedType;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException_Exception;

/**
 * Manager is responsible for communication and managing of JRA3 IDMs registered in WEB GUI
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ManagerImpl implements Manager,ManagerNotifier{
	
	/**
	 * Default time zone for JRA3 GUI
	 */
	private  String timezone ="UTC";
	/**
	 * List of all timezones for JRA3 WEB GUI
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
	 * Map with JRA3 IDM registered in the system
	 */
	private  Map<String, InterDomainManager> idms = Collections.synchronizedMap(new HashMap<String, InterDomainManager>());
	
	/**
	 * Identifies period of time when JRA3 IDM register in WEB GUI is mark as down
	 */
	private long tearDownTime = 10000;
	/**
	 * Logs information
	 */
	private static Logger logger = Logger.getLogger("IDMsManager");
	
 	/**
 	 * Maps the virtual port to real one and vice versa
 	 */
	private PortsMapper portsMapper;
	
	
	
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
	public void removeAll(){
		Iterator<String> iterator = idms.keySet().iterator();
		InterDomainManager idm=null;
		while (iterator.hasNext())
		{
			idm = (InterDomainManager)idms.get(iterator.next());
			remove (idm);
			idm =null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagers()
	 */
	public List<InterDomainManager> getInterDomainManagers(){
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
	public  InterDomainManager getFirstDomain(){
		if (idms.size()==0)
			return null;
		Set<String> keyset = idms.keySet();
		return idms.get(keyset.iterator().next());
	}*/
	
	
	
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllPorts()
	 */
	public List<String> getAllPorts (){
		Iterator<String> iterator = idms.keySet().iterator();
		InterDomainManager manager =null;
		List<String> ports=null;
		while (iterator.hasNext()){
			manager = idms.get(iterator.next());
			if (manager != null){
				ports = manager.getAllClientPorts();
				if (ports != null)
					break;
			}
		}
		return ports;
	}
	
	/*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains()
     */
    public List<String> getAllDomains(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager =null;
        List<String> domains = null;
        while (iterator.hasNext()){
            manager = idms.get(iterator.next());
            if (manager != null){
                domains = manager.getAllDomains();
                if (domains != null)
                    break;
            }
        }
        return domains;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains()
     */
    public List<String> getAllLinks(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager =null;
        List<String> links = null;
        while (iterator.hasNext()){
            manager = idms.get(iterator.next());
            if (manager != null){
                links = manager.getAllLinks();
                if (links != null)
                    break;
            }
        }
        return links;
    }

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagerPorts(java.lang.String)
	 */
	public List<String> getInterDomainManagerPorts (String idmIdentifier){
	    InterDomainManager manager = idms.get(idmIdentifier);
	    if (manager == null)
	    	return null;
	    List<String> ports = manager.getDomainClientPorts();
	    logger.info (ports);
	    return ports;
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
	
	
	public List<Service> getServicesForAllInterDomainManagers(){
		List<Service> list = new ArrayList<Service>(); 
		InterDomainManager manager = null;
		List<Service> managerServices =null;
		
		for (String idm:idms.keySet()){
			manager = idms.get(idm);
			if (manager == null)  continue;
			managerServices = manager.getServices(false);
			if (managerServices == null) continue;
			list.addAll(managerServices);
		}
		return list;
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
	 * Make actions needed when  new JRA3 IDM appear for first time  
	 * @param idm
	 * @return
	 */
	public InterDomainManager newInterDomainManagerConnected (String idm){
		int index = idm.indexOf("/interdomain");
		String url;
		if (index > 0)
			url = idm.substring(0, index);
		else
			url=idm;
		InterDomainManager manager = new InterDomainManager (idm, url);
		if (manager != null)
			idms.put(idm, manager);
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
		Service service =manager.getService(serviceId);
		if (service == null)
			return;
		List<Reservation> list = service.getReservations();
		int stateOfReservation  = convertReservationTypes(state);
		if (stateOfReservation==SCHEDULED){
			manager.forceUpdateService(serviceId);
		}
		int length = list.size();
		Reservation reservation =null;
		for (int i=0;i<length;i++){
			reservation = list.get(i);
			if (reservation.getBodID().equals(resID)){
				reservation.setIntState(stateOfReservation);
				break;
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#statusUpdated(java.lang.String, net.geant.autobahn.administration.Status)
	 */
	public void statusUpdated(String idm, Status status) {
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			manager= newInterDomainManagerConnected(idm);
		manager.setStatus(status);
		if (manager.getLastStatusUpdateInMillis()>tearDownTime)
		try{
			manager.getServices(true);
			manager.getProperties();
			manager.getLog(true);
		}catch (Exception e){
			logger.info (e.getClass().getName()+":"+e.getMessage());
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
		logger.info(buffer.toString());
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#submitServiceAtInterDomainManager(java.lang.String, net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public String submitServiceAtInterDomainManager(String idm, ServiceRequest request) throws UserAccessPointException_Exception, ManagerException {
		logger.info("Submitting service");
		if (request == null){
			throw new ManagerException(ManagerException.NO_SERVICE, "Sumitting null service");
		}
		if (request.getReservations()== null || request.getReservations().isEmpty()){
			throw new ManagerException(ManagerException.SERVICE_WITHOUT_RESERVATIONS, "Empty service submitted");
		}
		logger.info("Verified");
		logServiceRequest(request);
		InterDomainManager manager = idms.get(idm);
		if (manager == null)
			throw new ManagerException (ManagerException.UNKNOWN_MANAGER,"No manager with name:"+idm);
		logger.info("Verified");
		synchronized (manager) {
			String  id = manager.submitService(request);
			logger.info ("sumitted");
			return id;	
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkReservationPossibility(java.lang.String, net.geant.autobahn.useraccesspoint.ReservationRequest)
	 */
	public ReservationTest checkReservationPossibility(String idm,
			ReservationRequest request) throws UserAccessPointException_Exception {
		InterDomainManager manager = idms.get(idm);
		logger.info ("check reservation possibility"+ manager== null);
		ReservationTest test = new ReservationTest();
		if (manager == null)
			test.setStatus(false);
		else
			test.setStatus(manager.checkReservationPossibility(request));
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
	public List<Service> getServicesFromInterDomainManager(String idm) {
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
			String serviceID) throws UserAccessPointException_Exception {
		
		if (idm==null || serviceID == null)
			return;
		InterDomainManager manager= idms.get(idm);
		if (manager != null){
			logger.info("Canceling service:"+idm+":"+serviceID);
			manager.cancelService(serviceID);
			manager.getServices();
		}
		
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
	public Service getServiceFromInterDomainManager(String idm,String serviceId) {
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
	 * Sets period of time after it the registered JRA3 IDM is mark as not accessible
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
			reservation.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(times[0]));
			reservation.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(times[1]));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
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
		service.setUserName("testUser");
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
		if (timezone == null || timezone.equals("UTC")|| timezone.equals("GMT")|| this.timezone.equals(timezone))
			return;
		TimeZone applicationTimeZone = TimeZone.getTimeZone(this.timezone);
		if (applicationTimeZone== null)
			applicationTimeZone= TimeZone.getTimeZone("UTC");
		TimeZone pst = TimeZone.getTimeZone(timezone); 
		Calendar startTime= request.getStartTime().toGregorianCalendar();
		Calendar endTime= request.getEndTime().toGregorianCalendar();
        GregorianCalendar startDate=(GregorianCalendar)Calendar.getInstance();
        startDate.setTimeInMillis(startTime.getTimeInMillis()-pst.getRawOffset()+applicationTimeZone.getRawOffset());
        GregorianCalendar endDate=(GregorianCalendar)Calendar.getInstance();
        endDate.setTimeInMillis(endTime.getTimeInMillis()-pst.getRawOffset()+applicationTimeZone.getRawOffset());
        try {
			request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate));
			request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
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
		Calendar startTime= request.getStartTime().toGregorianCalendar();
		Calendar endTime= request.getEndTime().toGregorianCalendar();
        GregorianCalendar startDate=(GregorianCalendar)Calendar.getInstance();
        startDate.setTimeInMillis(startTime.getTimeInMillis()+pst.getRawOffset()-applicationTimeZone.getRawOffset());
        GregorianCalendar endDate=(GregorianCalendar)Calendar.getInstance();
        endDate.setTimeInMillis(endTime.getTimeInMillis()+pst.getRawOffset()-applicationTimeZone.getRawOffset());
        try {
			request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate));
			request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	public ServicesFormModel getSubmitedServicesInIDM(String idm) {
		ServicesFormModel serv =  new ServicesFormModel();
		List<String > managers = 	getAllInterdomainManagers();
		logger.info("managers:"+managers);
		if (managers== null || managers.size()==0)
			return serv;
		serv.setIdms(managers);
		if (idm ==null){
			serv.setCurrentIdm(managers.get(0));
			InterDomainManager manager = idms.get(managers.get(0));
			serv.setServices(manager.getServices());
		}else{
			InterDomainManager manager = idms.get(idm);	
			List<Service> services = manager.getServices();
			serv.setServices(services);
			serv.setCurrentIdm(idm);
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
		serv.setProperties(manager.getProperties());
		serv.setCurrentIdm(idm);
		return serv;
	}


	public ServicesFormModel getSubmitedServicesInInterDomainManager(String idm) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}