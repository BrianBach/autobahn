package net.geant2.jra3.dm2idm;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import net.geant2.jra3.intradomain.WSSecurity;


/**
 * Factory for creating web services client of Dm2Idm service. 
 * 
 * @author Michal
 */
@WebServiceClient(name = "Dm2IdmService", 
		targetNamespace = "http://dm2idm.jra3.geant2.net/", 
		wsdlLocation = "file:wsdl/dm2idm.wsdl")
class Dm2IdmService {
	
	private final static QName Dm2IdmPort = new QName("http://dm2idm.jra3.geant2.net/", "Dm2IdmPort");
	public final static QName SERVICE = new QName("http://dm2idm.jra3.geant2.net/", "Dm2IdmService");
	private Service service;
	
	public Dm2IdmService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(Dm2IdmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
	@WebEndpoint(name = "Dm2IdmPort")
	public Dm2Idm getDm2IdmPort() {
		Dm2Idm res = service.getPort(Dm2Idm.class);
		
		WSSecurity.setClientTimeout(res);
		
		return res;
	}
}
