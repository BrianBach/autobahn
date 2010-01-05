package net.geant2.jra3.idm2dm;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

import net.geant2.jra3.idm.WSSecurity;

/**
 * @author Michal
 */

@WebServiceClient(name = "Idm2DmService", targetNamespace = "http://idm2dm.jra3.geant2.net/", wsdlLocation = "file:wsdl/idm2dm.wsdl")
public class Idm2DmService {

	private final static int TIMEOUT = 1000 * 60 * 5;
    private final static QName SERVICE = new QName("http://idm2dm.jra3.geant2.net/", "Idm2DmService");
    private final static QName Idm2DmPort = new QName("http://idm2dm.jra3.geant2.net/", "Idm2DmPort");
    private Service service;
    
    public Idm2DmService(String endPoint) {
        service = Service.create(SERVICE);
        service.addPort(Idm2DmPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns Idm2DmPort
     */
    @WebEndpoint(name = "Idm2DmPort")
    public Idm2Dm getIdm2DmPort() {
    	Idm2Dm port = service.getPort(Idm2Dm.class); 
    	WSSecurity.setClientTimeout(port, TIMEOUT);
    	
        return port;
    }

}
