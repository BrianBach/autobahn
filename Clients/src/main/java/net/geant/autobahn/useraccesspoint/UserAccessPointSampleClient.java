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
    
    public static void main(String args[]) throws Exception {
        System.out.println("Give IDM to connect to:");
        byte byteStr[] = new byte[50];
        System.in.read(byteStr);
        String idm = (new String(byteStr).trim());

        System.out.println("Give IDM port:");
        byteStr = new byte[50];
        System.in.read(byteStr);
        String port = (new String(byteStr).trim());
        
        if (idm == null || idm.equals("")) {
            idm = "109.105.111.62";
        }
        if (port == null || port.equals("")) {
            port = "8080";
        }
        
        UserAccessPointSampleClient instance = new UserAccessPointSampleClient("http://"+idm+":"+port+"/autobahn/uap");
        System.out.println("Connecting to "+idm+":"+port);
        
        System.out.println("\n---getIdcpPorts():");
        String[] idcpPorts = instance.uap.getIdcpPorts();
        if (idcpPorts!=null) {
            for (int i=0; i<idcpPorts.length; i++) {
                System.out.println(i+": "+idcpPorts[i]);
            }
        }
        
        System.out.println("\n---getAllClientPorts():");
        String[] clPorts = instance.uap.getAllClientPorts();
        if (clPorts!=null) {
            for (int i=0; i<clPorts.length; i++) {
                System.out.println(i+": "+clPorts[i]);
            }
        }
        
        System.out.println("\n---getAllDomains():");
        String[] doms = instance.uap.getAllDomains();
        if (doms!=null) {
            for (int i=0; i<doms.length; i++) {
                System.out.println(i+": "+doms[i]);
            }
        }
        
        System.out.println("\n---getAllDomains_NonClient():");
        String[] domsNonClient = instance.uap.getAllDomains_NonClient();
        if (domsNonClient!=null) {
            for (int i=0; i<domsNonClient.length; i++) {
                System.out.println(i+": "+domsNonClient[i]);
            }
        }
        
        System.out.println("\n---getAllLinks():");
        String[] lnks = instance.uap.getAllLinks();
        if (lnks!=null) {
            for (int i=0; i<lnks.length; i++) {
                System.out.println(i+": "+lnks[i]);
            }
        }
        
        System.out.println("\n---getAllLinks_NonClient():");
        String[] lnksNonCl = instance.uap.getAllLinks_NonClient();
        if (lnksNonCl!=null) {
            for (int i=0; i<lnksNonCl.length; i++) {
                System.out.println(i+": "+lnksNonCl[i]);
            }
        }
        
        System.out.println("\n---getDomainClientPorts():");
        String[] domClPorts = instance.uap.getDomainClientPorts();
        if (domClPorts!=null) {
            for (int i=0; i<domClPorts.length; i++) {
                System.out.println(i+": "+domClPorts[i]);
            }
        }
        System.out.println("---");

        //instance.sampleReservation();
        
        System.exit(0);
    }
}