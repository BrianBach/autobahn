package net.geant.autobahn.itest.env1;

import static org.junit.Assert.assertEquals;
import net.geant.autobahn.testplatform.helpers.AutobahnManagement;
import net.geant.autobahn.testplatform.helpers.AutobahnManagementFactory;
import net.geant.autobahn.testplatform.observer.StatusObserver;
import net.geant.autobahn.testplatform.observer.StatusObserver.ReservationState;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
import net.geant.autobahn.useraccesspoint.textclient.RequestParser;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class StartingStoppingTests {

	private final static String callback = "http://localhost:9090/callback";
	
	static private AutobahnManagementFactory factory = new AutobahnManagementFactory(
		"env1");

	static private AutobahnManagement[] domains = factory.createInstances();
	
	static private AutobahnManagement domain1 = domains[0];
	static private AutobahnManagement domain2 = domains[1];
	static private AutobahnManagement domain3 = domains[2];
	
	private static StatusObserver observer = null;
	
	@BeforeClass
	public static void startupTesting() {
		LogUtils.setLoggerClass(Log4jLogger.class);
		observer = new StatusObserver(callback);
		observer.start();
		
		System.out.println(" - -- Starting instances...");
        AutobahnManagement.cleanOldReservations(domains);
		AutobahnManagement.start(observer, domains);
		
		observer.waitForDomains(domain1, domain2, domain3);
		
		System.out.println(" - -- Started");
	}
	
	@Test
	public void testReservation() throws UserAccessPointException {
		System.out.println("SS1");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "20sec", "40sec", "StartStop1", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        // clean the state
        uap.cancelService(sid);
        assertEquals(ReservationState.CANCELLED, observer.waitAndGetEvent(sid));
	}
	
	@Test
	public void testDomainDownWhenActivatingTimeout() throws Exception {
		System.out.println("SS2");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "30sec", "3min", "StartStop2", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        domain2.stopInstance();
        
        // should fail
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
        
        domain2.startInstance(observer);
        observer.waitForDomains(domain2);
	}
	
	@Test
	public void testDomainDownWhenActivating() throws Exception {
		System.out.println("SS3");
		
        UserAccessPoint uap = domain1.getUserAccessPoint(); 

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "30sec", "3min", "StartStop3", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        domain2.stopInstance();
        
        // should fail
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
        
        domain2.startInstance(observer);
        observer.waitForDomains(domain2);
	}
	
	@Test
	public void testDomainDownWhenActivatingSmallFinishTime() throws Exception {
		System.out.println("SS4");
		
        UserAccessPoint uap = domain1.getUserAccessPoint(); 

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "20sec", "40sec", "StartStop4", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        domain2.stopInstance();
        
        // should fail
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
        
        domain2.startInstance(observer);
        observer.waitForDomains(domain2);
	}
	
	@Test
	public void testDomainDownWhenScheduling() throws Exception {
		System.out.println("SS5");
		
		UserAccessPoint uap = domain1.getUserAccessPoint();

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "30sec", "3min", "StartStop5", false);
        
        domain2.stopInstance();
        domain3.stopInstance();
        
        try {
        	Thread.sleep(15 * 1000);
        } catch(Exception e) {
        }
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
        
        AutobahnManagement.start(observer, domain2, domain3);
        
        observer.waitForDomains(domain2, domain3);
	}
	
	@AfterClass
	public static void cleanup() {
        try {
        	Thread.sleep(30 * 1000);
        } catch(Exception e) {
        }
		observer.stop();
        
		System.out.println(" - -- Stopping instances");
		domain1.stopInstance();
		domain2.stopInstance();
		domain3.stopInstance();
		
        try {
        	Thread.sleep(20 * 1000);
        } catch(Exception e) {
        }
	}
}
