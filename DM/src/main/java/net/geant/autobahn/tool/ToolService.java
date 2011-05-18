package net.geant.autobahn.tool;

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
 * Creates endpoint for Tool web service.
 * 
 * @author Michal
 */

@WebServiceClient(name = "ToolService", targetNamespace = "http://tool.autobahn.geant.net/", wsdlLocation = "file:wsdl/tool.wsdl")
public class ToolService {

    private final static QName SERVICE = new QName("http://tool.autobahn.geant.net/", "ToolService");
    private final static QName ToolPort = new QName("http://tool.autobahn.geant.net/", "ToolPort");
    private Service service;
	private final static Logger log = Logger.getLogger(ToolService.class);

    
    public ToolService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(ToolPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns ToolPort
     */
    @WebEndpoint(name = "ToolPort")
    public Tool getToolPort() {
    	Tool port = service.getPort(Tool.class);
    	
        WSSecurity tool = null;
        try {
            tool = new WSSecurity("etc/security");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        tool.setClientTimeout(port);
        
        try {
            tool.configureEndpoint(port);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.error("I/O exception : " + e.getMessage());
        } catch (Exception e) {
            log.error("General exception: " + e.getMessage());
        } catch (Throwable e) {
            log.error("Exception: " + e.getMessage());
        }   
    	
        return  port;
    }
}
