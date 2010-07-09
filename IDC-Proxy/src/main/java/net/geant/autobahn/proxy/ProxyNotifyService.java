/**
 * 
 */
package net.geant.autobahn.proxy;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPathException;

import net.geant.autobahn.edugain.WSSecurity;

import org.apache.log4j.Logger;

/**
 * @author Micha³
 *
 */
@WebServiceClient(name = "proxynotify", 
        targetNamespace = "http://proxy.autobahn.geant.net/", 
        wsdlLocation = "file:wsdl/proxynotify.wsdl")
public class ProxyNotifyService {
	
	
    private final static QName ProxyNotifyPort = new QName("http://proxy.autobahn.geant.net/", "ProxyNotifyPort");
    public final static QName SERVICE = new QName("http://proxy.autobahn.geant.net/", "ProxyNotifyService");
    private Service service;
	private final static Logger log = Logger.getLogger(ProxyNotifyService.class);

    
    public ProxyNotifyService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(ProxyNotifyPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "ProxyPort")
    public ProxyNotify getProxyPort() {
        ProxyNotify res = service.getPort(ProxyNotify.class);

        WSSecurity proxy = null;
        try {
        	proxy = new WSSecurity("etc/edugain");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        proxy.setClientTimeout(res);
        
        try {
        	proxy.configureEndpoint(res);
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
