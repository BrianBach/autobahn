package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.administration.Administration;

public class AdministrationClient {
    private static final QName SERVICE = new QName("http://administration.autobahn.geant.net/", "AdministrationService");
    private final static QName AdministrationPort = new QName("http://administration.autobahn.geant.net/", "AdministrationPort");
	
	private Service service;
	
	public AdministrationClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(AdministrationPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
    public Administration getAdministrationPort() {
    	return service.getPort(Administration.class);
    }

}
