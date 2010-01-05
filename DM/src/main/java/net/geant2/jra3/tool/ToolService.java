package net.geant2.jra3.tool;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import net.geant2.jra3.intradomain.WSSecurity;


/**
 * Creates endpoint for Tool web service.
 * 
 * @author Michal
 */

@WebServiceClient(name = "ToolService", targetNamespace = "http://tool.jra3.geant2.net/", wsdlLocation = "file:wsdl/tool.wsdl")
public class ToolService {

    private final static QName SERVICE = new QName("http://tool.jra3.geant2.net/", "ToolService");
    private final static QName ToolPort = new QName("http://tool.jra3.geant2.net/", "ToolPort");
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
    	
        return  port;
    }
}
