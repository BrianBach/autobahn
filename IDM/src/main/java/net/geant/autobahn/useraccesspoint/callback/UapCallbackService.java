package net.geant.autobahn.useraccesspoint.callback;

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


@WebServiceClient(name = "UapCallbackService", 
		targetNamespace = "http://callback.useraccesspoint.autobahn.geant.net/", wsdlLocation = "file:wsdl/uap_callback.wsdl")
public class UapCallbackService {
	
	private final static QName SERVICE = new QName("http://callback.useraccesspoint.autobahn.geant.net/", "UapCallbackService");
    private final static QName UapCallbackPort = new QName("http://callback.useraccesspoint.autobahn.geant.net/", "UapCallbackPort");
    private Service service;
	private final static Logger log = Logger.getLogger(UapCallbackService.class);

	
	public UapCallbackService(String endPoint) {
		service = Service.create(SERVICE);
		service.addPort(UapCallbackPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
	}

	/**
	 * 
	 * @return
	 */
    @WebEndpoint(name = "UapCallbackPort")
    public UapCallback getUapCallbackPort() {

    	UapCallback port = service.getPort(UapCallback.class); 
    	
        WSSecurity uapCallback = null;
        try {
        	uapCallback = new WSSecurity("etc/edugain");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        uapCallback.setClientTimeout(port);
        
        try {
        	uapCallback.configureEndpoint(port);
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
