package net.geant.autobahn.testplatform.clients;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.intradomain.administration.DmAdministration;

public class DmAdministrationClient {
	private Service service;
	
    public final static QName SERVICE = new QName("http://administration.intradomain.autobahn.geant.net/", "DmAdministrationService");
    public final static QName DmAdministrationPort = new QName("http://administration.intradomain.autobahn.geant.net/", "DmAdministrationPort");

    public DmAdministrationClient(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(DmAdministrationPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns DmAdministration
     */
    @WebEndpoint(name = "DmAdministrationPort")
    public DmAdministration getDmAdministrationPort() {
        return service.getPort(DmAdministration.class);
    }
}
