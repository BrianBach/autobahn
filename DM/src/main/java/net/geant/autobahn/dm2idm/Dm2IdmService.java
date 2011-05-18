package net.geant.autobahn.dm2idm;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPathException;

import org.apache.log4j.Logger;

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
	private final static Logger log = Logger.getLogger(Dm2IdmService.class);
	
	public Dm2IdmService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(Dm2IdmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}
	
	@WebEndpoint(name = "Dm2IdmPort")
	public Dm2Idm getDm2IdmPort() {
		Dm2Idm res = service.getPort(Dm2Idm.class);
		
		WSSecurity dm2idm = null;
        try {
            dm2idm = new WSSecurity("etc/security");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
		dm2idm.setClientTimeout(res);
		
		try {
			dm2idm.configureEndpoint(res);
		} catch (FileNotFoundException e) {
			log.error("File not found: " + e.getMessage());
		} catch (IOException e) {
			log.error("I/O exception : " + e.getMessage());
		} catch (Exception e) {
			log.error("General exception: " + e.getMessage());
		} catch (Throwable e) {
			log.error("Exception: " + e.getMessage());
		}		
		
		return res;
	}
}
