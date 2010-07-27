package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.useraccesspoint.UserAccessPoint;

public class UserAccessPointClient {
	private Service service;
	
    private static final QName SERVICE = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointService");
    private final static QName UserAccessPointPort = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointPort");

	
	public UserAccessPointClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(UserAccessPointPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);

	}
	
	public UserAccessPoint getUserAccessPointPort() {
		return service.getPort(UserAccessPoint.class);
	}
	
	public static void main(String[] args) {
		UserAccessPoint uap = new UserAccessPointClient(
				"http://localhost:8080/autobahn/uap").getUserAccessPointPort();
		
		for(String port : uap.getAllClientPorts()) {
			System.out.println(port);
		}
	}
}
