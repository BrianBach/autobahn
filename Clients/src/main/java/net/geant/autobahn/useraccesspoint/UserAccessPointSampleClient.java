package net.geant.autobahn.useraccesspoint;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException_Exception;

public class UserAccessPointSampleClient {

    private static final QName SERVICE_NAME = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointService");
    private final static QName UserAccessPointPort = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointPort");
    
    private UserAccessPoint uap = null;
    
    public UserAccessPointSampleClient(String target) {
        Service service = Service.create(SERVICE_NAME);
        service.addPort(UserAccessPointPort, SOAPBinding.SOAP11HTTP_BINDING, target);

        uap = service.getPort(UserAccessPointPort, UserAccessPoint.class);
    }

    private void sampleReservation() throws DatatypeConfigurationException,
            UserAccessPointException, ParseException {

        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");

        ReservationRequest r1 = new ReservationRequest();
        r1.setCapacity(1000000000); // in bps = 1Gbs
        r1.setDescription("res1");
        r1.setStartPort("10.10.32.5");
        r1.setEndPort("10.20.32.5");
        r1.setMaxDelay(2);
        r1.setUserVlanId(0);

        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.HOUR, 10);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.DATE, 10);

        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);

        sreq.getReservations().add(r1);

        System.out.println("ServiceID:\n" + uap.submitService(sreq));
//        System.out.println("ServiceID:\n" + uap.checkReservationPossibility(r1));
//        System.out.println("ServiceID:\n" + uap.queryService("150.140.8.13:8080@1270051708928"));
        
    }
    
    private String[] getPorts() {
        return uap.getAllClientPorts();
    }

    public static void main(String args[]) throws Exception {

        UserAccessPointSampleClient instance = new UserAccessPointSampleClient("http://localhost:1234/autobahn/uap");

        instance.sampleReservation();
        //System.out.println(instance.getPorts());
        
        System.exit(0);
    }
}