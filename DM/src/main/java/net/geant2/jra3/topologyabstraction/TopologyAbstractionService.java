package net.geant2.jra3.topologyabstraction;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Factory for creating web services client of TopologyAbstraction service. 
 * 
 * @author Kostas
 */
@WebServiceClient(name = "TopologyAbstractionService", 
        targetNamespace = "http://topologyabstraction.jra3.geant2.net/", 
        wsdlLocation = "file:wsdl/topologyabstraction.wsdl")
class TopologyAbstractionService {

    private final static QName TopologyAbstractionPort = new QName("http://topologyabstraction.jra3.geant2.net/", "TopologyAbstractionPort");
    public final static QName SERVICE = new QName("http://topologyabstraction.jra3.geant2.net/", "TopologyAbstractionService");
    private Service service;
    
    public TopologyAbstractionService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(TopologyAbstractionPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    @WebEndpoint(name = "TopologyAbstractionPort")
    public TopologyAbstraction getTopologyAbstractionPort() {
        TopologyAbstraction res = service.getPort(TopologyAbstraction.class);
        
        WSSecurity.setClientTimeout(res);
        
        return res;
    }
}
