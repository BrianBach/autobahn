package net.geant.autobahn.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

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
    	
    	WSSecurity.setClientTimeout(port);

    	try {
			WSSecurity.configureEndpoint(port);
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
    	
        return  port;
    }
}