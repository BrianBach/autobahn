package net.geant.autobahn.testplatform.helpers;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.amanager.AutobahnManagerService;
import net.geant.autobahn.intradomain.administration.DmAdministration;
import net.geant.autobahn.testplatform.clients.AdministrationClient;
import net.geant.autobahn.testplatform.clients.DmAdministrationClient;
import net.geant.autobahn.testplatform.clients.UserAccessPointClient;
import net.geant.autobahn.testplatform.configuration.DomainConfiguration;
import net.geant.autobahn.testplatform.observer.StatusObserver;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;

public class AutobahnManagement {

	private String path;
	private LocalAutobahnRunner runner;
	private String wsPrefix;
	private DomainConfiguration conf;
	
	protected AutobahnManagement(String path, String wsPrefix, LocalAutobahnRunner runner) {
		this.path = path;
		this.wsPrefix = wsPrefix;
		this.runner = runner;
	}
	
	public String getDomainAddress() {
		return wsPrefix;
	}
	
	public void startInstance() {
		runner.start(path);
	}
	
	public void stopInstance() {
		runner.stop(path);
	}
	
	public void cleanOldReservations() {
		this.conf.cleanOldReservations();
	}
	
	public void startInstance(String statusObserverAddress) {
		runner.start(path, statusObserverAddress);
	}

	public void startInstance(StatusObserver statusObserver) {
		runner.start(path, statusObserver.getUrl());
	}

	public void setConfiguration(DomainConfiguration conf) {
		this.conf = conf;
	}

	/**
	 * 
	 * @param name
	 * @param val
	 */
	public void setDmProperty(String name, String val) {
    	DmAdministration client = new DmAdministrationClient(wsPrefix + "/dmadmin").getDmAdministrationPort();
    	
    	List<net.geant.autobahn.intradomain.administration.KeyValue> props = 
    		new ArrayList<net.geant.autobahn.intradomain.administration.KeyValue>();
    	net.geant.autobahn.intradomain.administration.KeyValue prop = new net.geant.autobahn.intradomain.administration.KeyValue();
    	prop.setKey(name);
    	prop.setValue(val);
    	props.add(prop);
    	
    	client.setProperties(props);
	}
	
	public UserAccessPoint getUserAccessPoint() {
        UserAccessPointClient ss = new UserAccessPointClient(wsPrefix + "/uap");
        
        return ss.getUserAccessPointPort();
	}
	
	public static void halt(String idmAddress) {
		AutobahnManagerService service = new AutobahnManagerService(idmAddress + "/manager");
		service.getAutobahnManagerPort().halt();
	}
	
	public static void restart(String idmAddress) {
    	AdministrationClient service = new AdministrationClient(idmAddress + "/administration");
    	Administration port = service.getAdministrationPort();
    	
    	port.setProperties(new ArrayList<KeyValue>());
	}

	public static void start(StatusObserver observer, AutobahnManagement... instances) {
		for(AutobahnManagement instance : instances) {
			instance.startInstance(observer);
		}
	}
	
	public static void cleanOldReservations(AutobahnManagement... instances) {
		for(AutobahnManagement instance : instances) {
			instance.cleanOldReservations();
		}
	}
	
	public static void cancelAll(String idmAddress) {
    	AdministrationClient service = new AdministrationClient(idmAddress + "/administration");
    	Administration port = service.getAdministrationPort();
    	
    	port.cancelAllServices();
	}

	public static void restartAll(String... addresses) {
		for(String address : addresses)
			restart(address);
	}
	
	public static void cancelAll(String... addresses) {
		for(String address : addresses)
			cancelAll(address);
	}
	
}
