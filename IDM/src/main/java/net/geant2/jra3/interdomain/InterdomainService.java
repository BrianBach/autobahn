package net.geant2.jra3.interdomain;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import net.geant2.jra3.idm.WSSecurity;

/**
 * @author Michal
 */

@WebServiceClient(name = "InterdomainService", 
		targetNamespace = "http://interdomain.jra3.geant2.net/", wsdlLocation = "file:wsdl/interdomain.wsdl")
class InterdomainService {

	private final static int TIMEOUT = 10000;
	private final static QName SERVICE = new QName("http://interdomain.jra3.geant2.net/", "InterdomainService");
    private final static QName InterdomainPort = new QName("http://interdomain.jra3.geant2.net/", "InterdomainPort");
    private Service service;
    
    public InterdomainService(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(InterdomainPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }
    
    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "InterdomainPort")
    public Interdomain getInterdomainPort() {
        Interdomain port = service.getPort(Interdomain.class);
		
		WSSecurity.setClientTimeout(port, TIMEOUT);

		return port;
    }
}
