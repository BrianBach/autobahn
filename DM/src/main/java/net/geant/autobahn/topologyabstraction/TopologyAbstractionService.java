package net.geant.autobahn.topologyabstraction;

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
    
	    
    public TopologyAbstractionService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(TopologyAbstractionPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "TopologyAbstractionPort")
    public TopologyAbstraction getTopologyAbstractionPort() {
        TopologyAbstraction res = service.getPort(TopologyAbstraction.class);
        
        WSSecurity.setClientTimeout(res);

        try {
			WSSecurity.configureEndpoint(res);
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
		        
        return res;
    }
}
