package net.geant.autobahn.proxy;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.WebServiceClient;
import javax.xml.xpath.XPathException;

import org.apache.log4j.Logger;

import net.geant.autobahn.proxy.Proxy;
import net.geant.autobahn.edugain.WSSecurity;

/**
 * Factory for creating web services client of Proxy service. 
 * 
 * @author Johnies Zaoudis
 */

@WebServiceClient(name = "proxy", 
        targetNamespace = "http://proxy.autobahn.geant.net/", 
        wsdlLocation = "file:wsdl/proxy.wsdl")
public class ProxyService {
    private final static QName ProxyPort = new QName("http://proxy.autobahn.geant.net/", "ProxyPort");
    public final static QName SERVICE = new QName("http://proxy.autobahn.geant.net/", "ProxyService");
    private Service service;
	private final static Logger log = Logger.getLogger(ProxyService.class);

    
    public ProxyService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(ProxyPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "ProxyPort")
    public Proxy getProxyPort() {
        Proxy res = service.getPort(Proxy.class);
        
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
    