package net.geant.autobahn.idm2dm;

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
import net.geant.autobahn.idm2dm.Idm2Dm;


/**
 * @author Michal
 */

@WebServiceClient(name = "Idm2DmService", targetNamespace = "http://idm2dm.autobahn.geant.net/", wsdlLocation = "file:wsdl/idm2dm.wsdl")
public class Idm2DmService {

	private final static int TIMEOUT = 1000 * 60 * 5;
    private final static QName SERVICE = new QName("http://idm2dm.autobahn.geant.net/", "Idm2DmService");
    private final static QName Idm2DmPort = new QName("http://idm2dm.autobahn.geant.net/", "Idm2DmPort");
    private Service service;
	private final static Logger log = Logger.getLogger(Idm2DmService.class);

    
    public Idm2DmService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(Idm2DmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "Idm2DmPort")
    public Idm2Dm getIdm2DmPort() {
    	Idm2Dm port = service.getPort(Idm2Dm.class); 
    	
        WSSecurity idm2dm = null;
        try {
            idm2dm = new WSSecurity("etc/edugain");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        idm2dm.setClientTimeout(port);
        
        try {
            idm2dm.configureEndpoint(port);
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
