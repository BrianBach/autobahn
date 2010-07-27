package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.topologyabstraction.TopologyAbstraction;

public class TopologyAbstractionClient {
	private Service service;
	
    public final static QName SERVICE = new QName("http://topologyabstraction.autobahn.geant.net/", "TopologyAbstractionService");
    public final static QName TopologyAbstractionPort = new QName("http://topologyabstraction.autobahn.geant.net/", "TopologyAbstractionPort");

    public TopologyAbstractionClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(TopologyAbstractionPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns TopologyAbstraction
     */
    @WebEndpoint(name = "TopologyAbstractionPort")
    public TopologyAbstraction getTopologyAbstractionPort() {
        return service.getPort(TopologyAbstraction.class);
    }
}
