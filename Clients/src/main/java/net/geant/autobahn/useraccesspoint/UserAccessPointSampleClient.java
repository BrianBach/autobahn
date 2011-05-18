package net.geant.autobahn.useraccesspoint;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;

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
        //r1.setStartPort("10.10.32.6");
        r1.setStartPort(new PortType("10.10.32.5"));
        //r1.setEndPort("testIdcpPort2");
        r1.setEndPort(new PortType("10.20.32.5"));
        r1.setMaxDelay(2);
        
        PathInfo exl = new PathInfo();
        exl.addDomain("http://some_domain_8.18:8080/autobahn/interdomain");
        r1.setUserExclude(exl);

        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.HOUR, 10);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.DATE, 10);

        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);

        sreq.getReservations().add(r1);

//        System.out.println("ServiceID:\n" + uap.submitService(sreq));
        System.out.println("ServiceID:\n" + uap.checkReservationPossibility(r1));
//        System.out.println("ServiceID:\n" + uap.queryService("150.140.8.13:8080@1270051708928"));
        
    }
    
    private String[] getPorts() throws UserAccessPointException {
        return uap.getAllClientPorts();
    }

    public static void main(String args[]) throws Exception {
        UserAccessPointSampleClient instance = new UserAccessPointSampleClient("http://150.140.8.13:8080/autobahn/uap");
        String[] idcpPorts = instance.uap.getIdcpPorts();
        if (idcpPorts!=null) {
            for (int i=0; i<idcpPorts.length; i++) {
                System.out.println("Idcp Port:"+idcpPorts[i]);
            }
        }
        System.out.println("---");
        
        String[] clPorts = instance.uap.getAllClientPorts();
        if (clPorts!=null) {
            for (int i=0; i<clPorts.length; i++) {
                System.out.println("Client Port:"+clPorts[i]);
            }
        }
        System.out.println("---");

        String[] doms = instance.uap.getAllDomains();
        if (doms!=null) {
            for (int i=0; i<doms.length; i++) {
                System.out.println("Domain:"+doms[i]);
            }
        }
        System.out.println("---");

        String[] domsNonClient = instance.uap.getAllDomains_NonClient();
        if (domsNonClient!=null) {
            for (int i=0; i<domsNonClient.length; i++) {
                System.out.println("Domain Non client:"+domsNonClient[i]);
            }
        }
        System.out.println("---");

        String[] lnks = instance.uap.getAllLinks();
        if (lnks!=null) {
            for (int i=0; i<lnks.length; i++) {
                System.out.println("Link:"+lnks[i]);
            }
        }
        System.out.println("---");

        String[] lnksNonCl = instance.uap.getAllLinks_NonClient();
        if (lnksNonCl!=null) {
            for (int i=0; i<lnksNonCl.length; i++) {
                System.out.println("Link non client:"+lnksNonCl[i]);
            }
        }
        System.out.println("---");

        String[] domClPorts = instance.uap.getDomainClientPorts();
        if (domClPorts!=null) {
            for (int i=0; i<domClPorts.length; i++) {
                System.out.println("Dom Client port:"+domClPorts[i]);
            }
        }
        System.out.println("---");

        instance.sampleReservation();
        //System.out.println(instance.getPorts());
        
        System.exit(0);
    }
}