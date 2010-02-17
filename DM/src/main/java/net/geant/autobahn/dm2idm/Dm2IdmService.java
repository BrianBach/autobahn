package net.geant.autobahn.dm2idm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.edugain.WSSecurity;


/**
 * Factory for creating web services client of Dm2Idm service. 
 * 
 * @author Michal
 */
@WebServiceClient(name = "Dm2IdmService", 
		targetNamespace = "http://dm2idm.autobahn.geant.net/", 
		wsdlLocation = "file:wsdl/dm2idm.wsdl")
class Dm2IdmService {
	
	private final static QName Dm2IdmPort = new QName("http://dm2idm.autobahn.geant.net/", "Dm2IdmPort");
	public final static QName SERVICE = new QName("http://dm2idm.autobahn.geant.net/", "Dm2IdmService");
	private Service service;
	
	public Dm2IdmService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(Dm2IdmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
	@WebEndpoint(name = "Dm2IdmPort")
	public Dm2Idm getDm2IdmPort() {
		Dm2Idm res = service.getPort(Dm2Idm.class);
		
		WSSecurity.setClientTimeout(res);
		
		try {
			WSSecurity.configureEndpoint(res);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return res;
	}
}
