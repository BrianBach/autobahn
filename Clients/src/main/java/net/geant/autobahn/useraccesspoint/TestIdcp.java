/**
 * 
 */
package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Sends uap requests that go to idcp domains (works with /etc/sql/sample_topo_idcp.sql file)
 * Issue idcp clientports at the command line interface to learn more endpoints
 * @author PCSS
 */
public class TestIdcp {
	
	private static final String local = "http://localhost:8080/autobahn/uap";
	private static UserAccessPoint uap = new UserAccessPointService(local).getUserAccessPointPort();
	
    static String reserve() throws Exception {
    	
    	final String ab = "10.10.32.4";
    	final String i2 = "ion.internet2.edu:rtr.salt:xe-0/1/3:xe-0/1/3.0";
    	final String caltech = "caltech.edu:vlsr4:0-4-1:11.1.1.10";
        
        ReservationRequest r1 = new ReservationRequest();
        
        r1.setStartPort(new PortType(ab));
        r1.setEndPort(new PortType(caltech));
        
        r1.setCapacity(1000000000);
        r1.setDescription("test reservation");
        
        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 3);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 7);
        
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);
        
        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");
        sreq.getReservations().add(r1);
        String serviceId = uap.submitService(sreq);
        System.out.println(serviceId + " has been submitted");
        return serviceId;
    }
    
    static void cancel(String serviceId) throws Exception {
    	
        uap.cancelService(serviceId);
        System.out.println(serviceId + " has been cancelled");
    }
    
    static void queryPorts() throws Exception {

        List<PortType> cports = uap.getDomainClientPorts();
        System.out.println("Domain client ports found: " + cports.size());
        for(PortType cport : cports) 
        	System.out.println(cport.getAddress());
        
        cports = uap.getAllClientPorts();
        System.out.println("All client ports found: " + cports.size());
        for(PortType cport : cports) 
    	   System.out.println(cport.getAddress());
    }
    
    public static void main(String args[]) throws Exception {
    	
    	boolean cancel = false;
    	String serviceId = reserve();
    	if (cancel == true) {
    		Thread.sleep(10 * 1000);
    		cancel(serviceId);
    	}
    	System.out.println("program exit...");
    }
}
