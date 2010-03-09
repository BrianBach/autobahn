package net.geant.autobahn.interdomain;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPathException;

import org.apache.log4j.Logger;

import net.geant.autobahn.edugain.WSSecurity;

/**
 * @author Michal
 */

@WebServiceClient(name = "InterdomainService", 
		targetNamespace = "http://interdomain.autobahn.geant.net/", wsdlLocation = "file:wsdl/interdomain.wsdl")
class InterdomainService {

	private final static int TIMEOUT = 10000;
	private final static QName SERVICE = new QName("http://interdomain.autobahn.geant.net/", "InterdomainService");
    private final static QName InterdomainPort = new QName("http://interdomain.autobahn.geant.net/", "InterdomainPort");
    private Service service;
    private final static Logger log = Logger.getLogger(InterdomainService.class);
    
    public InterdomainService(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(InterdomainPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "InterdomainPort")
    public Interdomain getInterdomainPort() {
        Interdomain port = service.getPort(Interdomain.class);
		
        WSSecurity interdomain = null;
        try {
            interdomain = new WSSecurity("etc/edugain");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        interdomain.setClientTimeout(port);
        
        try {
            interdomain.configureEndpoint(port);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.error("I/O exception : " + e.getMessage());
        } catch (Exception e) {
            log.error("General exception: " + e.getMessage());
        } catch (Throwable e) {
            log.error("Exception: " + e.getMessage());
        }   

		return port;
    }
}
