
package net.geant.autobahn.useraccesspoint;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 29 14:54:08 CET 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

public final class UserAccessPoint_UserAccessPointPort_Client {

    private UserAccessPoint uap = null;
    
    private static final String heanet = "http://kanga.heanet.ie:8080/autobahn/uap";
    private static final String localhost = "http://150.254.160.216:8080/autobahn/uap";
    private static final String pionier = "http://poznan.autobahn.psnc.pl:8080/autobahn/uap";
    private static final String geant2 = "http://srv2.lon.uk.geant2.net:8080/autobahn/uap";
    private static final String grnet = "http://gn2jra3.grnet.gr:8080/autobahn/uap";
    private final static String garr = "http://abcpl.dir.garr.it:8080/autobahn/uap";

    private static final String heanet_2 = "http://poznan.autobahn.psnc.pl:8083/autobahn/uap";
    private static final String pionier_2 = "http://poznan.autobahn.psnc.pl:8081/autobahn/uap";
    private static final String geant2_2 = "http://poznan.autobahn.psnc.pl:8082/autobahn/uap";
    
    private static final String mine = "http://localhost:8080/autobahn/uap";

    private static final String callback_url = "http://poznan.autobahn.psnc.pl:8443/callback";
    
    private static final QName SERVICE_NAME = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointService");

    public UserAccessPoint_UserAccessPointPort_Client(String target) {
        UserAccessPointService ss = new UserAccessPointService(target);
        uap = ss.getUserAccessPointPort();  
    }

    private void reservation() throws Exception {
        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");
      //  sreq.setUserHomeDomain("http://localhost:8080/autobahn/interdomain");
      //  sreq.setJustification("Test");
        ReservationRequest r1 = new ReservationRequest();
        r1.setCapacity(1000);
      //  r1.setDescription("res1");
    
        
/*        r1.setStartPort("10.13.32.16");
        r1.setEndPort("10.12.32.3");
*/
        
/*        r1.setStartPort("10.10.32.6");
        r1.setEndPort("10.13.32.16");
*/        

        // Poznan
        //r1.setStartPort("10.13.32.4");
        // Dublin
        //r1.setStartPort("10.12.32.4");
        // Crete
        //r1.setStartPort("10.11.32.7");
        // Rome
        //r1.setStartPort("10.14.32.2");
        // Zagreb
        //r1.setStartPort("10.16.32.2");
        // SCARIE
        r1.setStartPort(new PortType("10.10.32.17"));
        
        // Poznan
        //r1.setEndPort("10.13.32.4");
        // Dublin
        //r1.setEndPort("10.12.32.4");
        // Crete
        //r1.setEndPort("10.11.32.7");
        // Rome
        //r1.setEndPort("10.14.32.2");
        // Zagreb
        //r1.setEndPort("10.16.32.2");
        //I2 port A
        r1.setEndPort(new PortType("10.10.32.16"));
        //I2 port B
        //r1.setEndPort("10.10.32.22");
        // Czech
        //r1.setEndPort("10.10.32.27");
        
        
        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 1);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 6);
        
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);
        //r1.setProcessNow(true);

        //createCalendar("2008-05-19T17:12:00+02:00")
        sreq.getReservations().add(r1);
        
        System.out.println("ServiceID:\n" + uap.submitService(sreq));
    }
    
    private void service4() throws DatatypeConfigurationException, UserAccessPointException {
        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");
        
        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 1);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 3);

        GregorianCalendar end_later = (GregorianCalendar) Calendar.getInstance();
        end_later.add(Calendar.MINUTE, 15);
        
        //10.10.32.30   10.16.32.2
        //10.10.32.31   10.11.32.6
        //10.10.32.32   10.13.32.4
        //10.10.32.29   10.12.32.5
        
        // Res 1
        ReservationRequest r1 = new ReservationRequest();
        r1.setCapacity(1000000000);
        r1.setDescription("res1");

        r1.setStartPort(new PortType("10.10.32.31"));
        r1.setEndPort(new PortType("10.12.32.5"));
        
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);
        r1.setProcessNow(true);
        
        // Res 2
        ReservationRequest r2 = new ReservationRequest();
        r2.setCapacity(1000000000);
        r2.setDescription("res2");

        r2.setStartPort(new PortType("10.10.32.32"));
        r2.setEndPort(new PortType("10.16.32.2"));
        
        r2.setStartTime(start);
        r2.setEndTime(end);
        r2.setPriority(Priority.NORMAL);
        r2.setResiliency(Resiliency.NONE);
        r2.setProcessNow(true);

        // Res 3
        ReservationRequest r3 = new ReservationRequest();
        r3.setCapacity(1000000000);
        r3.setDescription("res3");

        r3.setStartPort(new PortType("10.10.32.33"));
        r3.setEndPort(new PortType("10.11.32.6"));
        
        r3.setStartTime(start);
        r3.setEndTime(end);
        r3.setPriority(Priority.NORMAL);
        r3.setResiliency(Resiliency.NONE);
        r3.setProcessNow(true);

        // Res 4
        ReservationRequest r4 = new ReservationRequest();
        r4.setCapacity(1000000000);
        r4.setDescription("res4");

        r4.setStartPort(new PortType("10.10.32.34"));
        r4.setEndPort(new PortType("10.13.32.4"));
        
        r4.setStartTime(start);
        r4.setEndTime(end);
        r4.setPriority(Priority.NORMAL);
        r4.setResiliency(Resiliency.NONE);
        r4.setProcessNow(true);

        sreq.getReservations().add(r2);
        sreq.getReservations().add(r3);
        sreq.getReservations().add(r4);
        sreq.getReservations().add(r1);
        
        //System.out.println("ServiceID:\n" + uap.submitServiceAndRegister(sreq, callback_url));
        System.out.println("ServiceID:\n" + uap.submitService(sreq));
    }
    
    private void service2() throws DatatypeConfigurationException, UserAccessPointException {
        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");
        
        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 15);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 25);

        //10.10.32.29   10.12.32.5
        //10.10.32.30   10.16.32.2
        //10.10.32.31   10.11.32.6
        //10.10.32.32   10.13.32.4
        
        // Res 1
        ReservationRequest r1 = new ReservationRequest();
        r1.setCapacity(1000000000);
        r1.setDescription("res1, AMS - Dublin");

        r1.setStartPort(new PortType("10.10.32.31"));
        r1.setEndPort(new PortType("10.12.32.5"));
        
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);
        //r1.setProcessNow(true);

        // Res 3
        ReservationRequest r3 = new ReservationRequest();
        r3.setCapacity(1000000000);
        r3.setDescription("res2, AMS - Czech");

        r3.setStartPort(new PortType("10.10.32.34"));
        r3.setEndPort(new PortType("10.10.32.27"));
        
        r3.setStartTime(start);
        r3.setEndTime(end);
        r3.setPriority(Priority.NORMAL);
        r3.setResiliency(Resiliency.NONE);
        //r3.setProcessNow(true);

        //sreq.getReservations().add(r1);
        sreq.getReservations().add(r3);
        
        //System.out.println("ServiceID:\n" + uap.submitServiceAndRegister(sreq, callback_url));
        System.out.println("ServiceID:\n" + uap.submitService(sreq));
    }
    
    private void modify(String resId) throws DatatypeConfigurationException {

        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 1);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 12);

        ModifyRequest par = new ModifyRequest();
        par.setResId(resId);
        par.setStartTime(start);
        par.setEndTime(end);
        
        System.out.println("Extend till: " + end.getTime());
        
        uap.modifyReservation(par);
    }
    
    private void cancel(String srvID) throws UserAccessPointException {
        
        uap.cancelService(srvID);
    }
    
    private void queryPorts(String domain) {

        String[] cports = uap.getDomainClientPorts();
        System.out.println("Domain client ports found: " + cports.length);
        for(String cport : cports) {
            System.out.println(cport);
        }
        
        try {
            cports = uap.getAllClientPorts();
            System.out.println("All client ports found: " + cports.length);
            for(String cport : cports) {
                System.out.println(cport);
            }
        } catch (UserAccessPointException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void queryService(String srvID) throws UserAccessPointException {
        ServiceResponse resp = uap.queryService(srvID);
        
        System.out.println("User: " + resp.getUserName());
        
        for(ReservationResponse resv : resp.getReservations()) {
            System.out.println("Reservation:");
            System.out.println(resv.getStartPort() + " " + resv.getEndPort());
            System.out.println(resv.getState());
        }
    }
    
    public static void main(String args[]) throws Exception {

        UserAccessPoint_UserAccessPointPort_Client instance = new UserAccessPoint_UserAccessPointPort_Client(
                mine);
        instance.queryPorts(mine);
    //  instance.queryService(mine);
        instance.reservation();
        //instance.queryPorts(mine);
        //instance.service4();
        //instance.service2();
        
        //instance.modify("poznan.autobahn.psnc.pl:8080@1214833280501_res_1");
        //instance.cancel("localhost:8080@1235142592637_res_1");
        //instance.queryPorts(pionier_2);
        //instance.queryService("poznan.autobahn.psnc.pl:8080@1218104551787");
        
        System.exit(0);
    }
}
