package net.geant.autobahn.topologyabstraction;

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
 * Factory for creating web services client of TopologyAbstraction service. 
 * 
 * @author Kostas
 */
@WebServiceClient(name = "TopologyAbstractionService", 
        targetNamespace = "http://topologyabstraction.autobahn.geant.net/", 
        wsdlLocation = "file:wsdl/topologyabstraction.wsdl")
class TopologyAbstractionService {

    private final static QName TopologyAbstractionPort = new QName("http://topologyabstraction.autobahn.geant.net/", "TopologyAbstractionPort");
    public final static QName SERVICE = new QName("http://topologyabstraction.autobahn.geant.net/", "TopologyAbstractionService");
    private Service service;
	private final static Logger log = Logger.getLogger(TopologyAbstractionService.class);
    
	    
    public TopologyAbstractionService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(TopologyAbstractionPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "TopologyAbstractionPort")
    public TopologyAbstraction getTopologyAbstractionPort() {
        TopologyAbstraction res = service.getPort(TopologyAbstraction.class);
        
        WSSecurity topology = null;
        try {
            topology = new WSSecurity("etc/edugain");
        } catch (XPathException e) {
            log.error("Could not create security object: " + e.getMessage());
        }
        
        topology.setClientTimeout(res);
        
        try {
            topology.configureEndpoint(res);
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
