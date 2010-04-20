package net.geant.autobahn.administration;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

public class AdministrationSampleClient {

    private static final QName SERVICE_NAME = new QName("http://administration.autobahn.geant.net/", "AdministrationService");
    private final static QName AdministrationPort = new QName("http://administration.autobahn.geant.net/", "AdministrationPort");
    
    private Administration adm = null;
    
    public AdministrationSampleClient(String target) {
        Service service = Service.create(SERVICE_NAME);
        service.addPort(AdministrationPort, SOAPBinding.SOAP11HTTP_BINDING, target);

        adm = service.getPort(AdministrationPort, Administration.class);
    }

    private void sampleReservation() {

        System.out.println("ServiceID:\n" + adm.getServices());
    }
    
    public static void main(String args[]) throws Exception {

        AdministrationSampleClient instance = new AdministrationSampleClient("http://localhost:1234/autobahn/administration");

        instance.sampleReservation();
        //System.out.println(instance.getPorts());
        
        System.exit(0);
    }
    
    
}

