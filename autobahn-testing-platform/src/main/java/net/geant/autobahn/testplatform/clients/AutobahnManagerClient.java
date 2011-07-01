package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.amanager.AutobahnManager;

public class AutobahnManagerClient {
    private static final QName SERVICE = new QName("http://amanager.autobahn.geant.net/", "AutobahnManagerService");
    private final static QName AutobahnManagerPort = new QName("http://amanager.autobahn.geant.net/", "AutobahnManagerPort");
	
	private Service service;
	
	public AutobahnManagerClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(AutobahnManagerPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
    public AutobahnManager getAutobahnManagerPort() {
    	return service.getPort(AutobahnManager.class);
    }
}
