package net.geant.autobahn.autoBahnGUI.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.AdministrationService;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.useraccesspoint.ModifyRequest;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.ServiceResponse;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException_Exception;
import net.geant.autobahn.useraccesspoint.UserAccessPointService;

/**
 * Models JRA3 InterDomainManager (JRA3 IDM) 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class InterDomainManager implements UserAccessPoint, Administration{
	/**
	 * Identifier
	 */
	private String identifier;
	/**
	 * Web services address prefix
	 */
	private String url;
	/**
	 * UserAccessPoint web service interface  
	 */
	private UserAccessPoint userAccessPoint;
	/**
	 * Administration web service interface
	 */
	private Administration administration;
	/**
	 * Services submited in JRA3 IDM
	 */
	public Map<String, Service> services = Collections.synchronizedMap(new HashMap <String, Service>());
	
	/**
	 * JRA3 IDM status
	 */
	private Status status;
	
	/**
	 * Time of last update of JRA3 IDM status
	 */
	private long lastStatusUpdateInMillis;
	
	/**
	 * JRA3 IDM managed ports
	 */
	private List<String> ports;

	/**
	 * JRA3 IDM logs 
	 */
	private String logs;
	
	/**
	 * Logs information
	 */
	public static Logger logger = Logger.getLogger("IDMsManager");
	
	/**
	 * Creates InterDomainManager object
	 * 
	 * @param indentifier identifier of the InterDomainManager
	 * @param url web services address prefix
	 */
	public InterDomainManager(String identifier, String url){
		this.identifier = identifier;	
		this.url= url;
		connect (url);
	}
	/**
	 * Establish connections with JRA3 IDM UserAccessPoint and Administration web service interface  
	 * 
	 * @param url web services address prefix
	 */
	public void connect (String url){
		userAccessPoint = connectUserAccessPoint(url);
		administration = connectAdministration(url);
	}
	/**
	 * Establish connection with JRA3 IDM  Administration web service interface
	 * 
	 * @param url web services address prefix
	 * @return connected Administration web service interface
	 */
	public static Administration connectAdministration (String url){
		String address = url+"/administration";
		AdministrationService service = new AdministrationService(address);
    	Administration admin = service.getAdministrationPort();
	    return admin; 
	}
	/**
	 * Establish connection with JRA3 IDM  UserAccessPoint web service interface
	 * 
	 * @param url web services address prefix
	 * @return connected UserAccessPoint web service interface
	 */
	public static UserAccessPoint connectUserAccessPoint (String url){
		String address = url+"/uap";	
	    UserAccessPointService service = new UserAccessPointService(address);
	    UserAccessPoint admin = service.getUserAccessPointPort();
	    return admin; 
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#cancelService(java.lang.String)
	 */
	public void cancelService(String serviceID)
			throws UserAccessPointException_Exception {
		if (isUserAccessPointConnected())
			userAccessPoint.cancelService(serviceID);
		
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#queryService(java.lang.String)
	 */
	public ServiceResponse queryService(String serviceID)
			throws UserAccessPointException_Exception {
		if (isUserAccessPointConnected())
			return userAccessPoint.queryService(serviceID);
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#submitService(net.geant.autobahn.useraccesspoint.ServiceRequest)
	 */
	public String submitService(ServiceRequest request)
			throws UserAccessPointException_Exception {
		if (isUserAccessPointConnected()){
			return userAccessPoint.submitService(request);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getLog(boolean)
	 */
	public String getLog(boolean all) {
		if (isAdmnistrationConnected()){
			try{
				return administration.getLog(all);
			}catch (Exception e){
				logger.error ("Cannot reservation from idm:"+e.getClass().getName()+":"+e.getMessage());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		if (isAdmnistrationConnected()){
			try{
			return administration.getProperties();
			}catch (Exception e){
				logger.error ("Cannot reservation from idm:"+e.getClass().getName()+":"+e.getMessage());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
	 */
	public Reservation getReservation(String resID) {
		if (isAdmnistrationConnected()){
			try{
				return administration.getReservation(resID);
			}catch (Exception e){
				logger.error ("Cannot reservation from idm:"+e.getClass().getName()+":"+e.getMessage());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getTopology()
	 */
	public List<Link> getTopology() {
		
		if (isAdmnistrationConnected()){
			try{
				return administration.getTopology();
			}catch (Exception e){
				logger.error ("Cannot get topology from idm:"+e.getClass().getName()+":"+e.getMessage());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setProperties(java.util.List)
	 */
	public void setProperties(List<KeyValue> properties) {
		if (isAdmnistrationConnected())
			try{
				administration.setProperties(properties);
			}catch (Exception e){
				logger.error ("Cannot set properties for idm:"+e.getClass().getName()+":"+e.getMessage());
			}
	}
	/** 
	 * Gets identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * Sets identifier
	 * @param identifier identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * Gets web service address prefix
	 * 
	 * @return web service address prefix
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Sets web service address prefix
	 * 
	 * @param web service address prefix
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * Sets JRA3 IDM status
	 * 
	 * @return web JRA3 IDM status
	 */
	public void setStatus (Status status){
		if (status!= null){
			this.status= status;
			lastStatusUpdateInMillis= System.currentTimeMillis();
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getService(java.lang.String)
	 */
	public Service getService(String name) {
		Service service = services.get(name);
		if (service != null)
			return service;
		try{
		if (isAdmnistrationConnected()){
			service = administration.getService(name);
			if (service !=null){
				services.put(service.getBodID(), service);
				return service;
			}
		}
		}catch (Exception e){
			logger.error ("Cannot get service from idm:"+e.getClass().getName()+":"+e.getMessage());
		}
		return null;
	}
	/**
	 * Get service submitted in JRA3 IDM 
	 * @param clearOld identifies if cached list should be clear before get new list
	 * @return Service object lists
	 */
	public List<Service>getServices(boolean clearOld){
		List<Service> serv=new ArrayList<Service>();
		if (clearOld){
			if (services == null)
				services= new HashMap<String, Service>();
			else
				services.clear();
			try{
			List<Service> servicesIdm = administration.getServices();
			if (servicesIdm == null )
				return serv;
			for (Service service:servicesIdm){
				services.put(service.getBodID(), service);
			}
			}catch (Exception e) {
				logger.error("Problem with getting services from idm:"+identifier);
			}
		}
		Service service;
		for (String name:services.keySet())
		{
			service = services.get(name);
			if (service == null) services.remove(name);
			serv.add(service);
		}
		return serv;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getServices()
	 */
	public List<Service>  getServices(){
		if (!isAdmnistrationConnected())
				return null;
		List<Service> servicesIdm=null;
		try{
			servicesIdm= administration.getServices ();
		}catch (Exception e){
			logger.error("Cannot get services from:"+identifier+":"+e.getClass().getName()+":"+e.getMessage());
		}
		return servicesIdm;
	}
	/**
	 * Gets logs from JRA3 IDM
	 * 
	 * @param refreash
	 * @param all
	 * @return
	 */
	public String getLog (boolean refreash, boolean all){
		if (refreash){
			if (isAdmnistrationConnected())
				logs = administration.getLog(all);
			else
				return null;
		}
		return logs;
	}
	/**
	 * Check if UserAccessPoint web service interface is connected  JRA3 IDM
	 * 
	 * @return true if yes, if no tries to establish connection 
	 */
	public boolean isUserAccessPointConnected(){
		if (userAccessPoint == null){
			connectUserAccessPoint(url+"/uap");
		}
		return userAccessPoint!=null;
	}
	/**
	 * Check if Administration web service interface is connected  JRA3 IDM
	 * 
	 * @return true if yes, if no tries to establish connection 
	 */
	public boolean isAdmnistrationConnected(){
		if (administration == null){
			connectAdministration(url+"/administration");
		}
		return administration!=null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getStatus()
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * Gets the time in mili second when last update was received
	 * 
	 * @return last update time in mili second
	 */
	public long getLastStatusUpdateInMillis() {
		return lastStatusUpdateInMillis;
	}
	
	/**
	 * Sets the time in mili seconds
	 * 
	 * @param last update time in mili seconds
	 */
	public void setLastStatusUpdateInMillis(long lastStatusUpdateInMillis) {
		this.lastStatusUpdateInMillis = lastStatusUpdateInMillis;
	}
	/**
	 * Gets logs from JRA3 IDM
	 * @return logs  form JRA3 IDM
	 */
	public String getLogs() {
		return logs;
	}
	/**
	 * Sets logs from JRA3 IDM
	 * @param logs  form JRA3 IDM
	 */
	public void setLogs(String logs) {
		this.logs = logs;
	}
	/** 
	 * Sets ports managed by JRA3 IDM
	 * @ports 
	 */
	public void setPorts(List<String> ports) {
		this.ports = ports;
	}
	
    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllDomains()
     */
    public List<String> getAllDomains() {
        try {
            if (isUserAccessPointConnected())
                return userAccessPoint.getAllDomains();
        } catch (Exception e ){
            logger.error ("Problem with getting domains:"+e.getClass().getName()+":"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllLinks()
     */
    public List<String> getAllLinks() {
        try {
            if (isUserAccessPointConnected())
                return userAccessPoint.getAllLinks();
        } catch (Exception e ){
            logger.error ("Problem with getting links:"+e.getClass().getName()+":"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllClientPorts()
	 */
	public List<String> getAllClientPorts() {
		try{
		if (isUserAccessPointConnected())
			return userAccessPoint.getAllClientPorts();
		}catch (Exception e ){
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getDomainClientPorts()
	 */
	public List<String> getDomainClientPorts() {
		logger.info("Getting ports from idm:"+identifier+":"+ports);
		if (ports != null)
			return ports;
		try{
			if (isUserAccessPointConnected())
			{
				ports =userAccessPoint.getDomainClientPorts();
				logger.info(ports);
				return ports;
			}
		}catch (Exception e){
			logger.error ("Problem with getting domain ports:"+e.getClass().getName()+":"+e.getMessage());
		}
		return null;
	}
	/**
	 * In normal way service is cached  in InterDomainManager services collection
	 * This method force to download service state from JRA3 IDM manager directly 
	 */
	public void forceUpdateService (String serviceID){
		if (!isAdmnistrationConnected())
			return;
		Service service = administration.getService(serviceID);
		if (service == null)
			return;
		Service storeService = services.get(serviceID);
		if (storeService!= null){
			storeService.setJustification(service.getJustification());
			storeService.getReservations().clear();
			if (service.getReservations()!= null)
			{
				int length = service.getReservations().size();
				for (int i=0;i<length;i++)
					storeService.getReservations().add(service.getReservations().get(i));
			}
			storeService.setState(service.getState());
		}else{
			services.put(serviceID, service);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#modifyReservation(net.geant.autobahn.useraccesspoint.ModifyRequest)
	 */
	public void modifyReservation(ModifyRequest request) {
		try{
			if (isUserAccessPointConnected())
				userAccessPoint.modifyReservation(request);
		}catch (Exception e ){
				e.printStackTrace();
		}
		
	}
	public boolean checkReservationPossibility(ReservationRequest request)
			throws UserAccessPointException_Exception {
		if (isUserAccessPointConnected())
			return userAccessPoint.checkReservationPossibility(request);
		return false;
	}
	public void registerCallback(String serviceID, String url) {
		// TODO Auto-generated method stub
		
	}
	public String submitServiceAndRegister(ServiceRequest request, String url)
			throws UserAccessPointException_Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void cancelAllServices() {
		// TODO Auto-generated method stub
		
	}
}
