package net.geant.autobahn.itest.env1;

import static org.junit.Assert.assertEquals;
import net.geant.autobahn.testplatform.helpers.AutobahnManagement;
import net.geant.autobahn.testplatform.helpers.AutobahnManagementFactory;
import net.geant.autobahn.testplatform.observer.StatusObserver;
import net.geant.autobahn.testplatform.observer.StatusObserver.ReservationState;
import net.geant.autobahn.tool.mock.MockTool;
import net.geant.autobahn.tool.mock.MockToolServer;
import net.geant.autobahn.tool.mock.Result;
import net.geant.autobahn.tool.mock.ToolBehaviour;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
import net.geant.autobahn.useraccesspoint.textclient.RequestParser;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsWithTool {
	
	private final static String callback = "http://localhost:9090/callback";

	static private AutobahnManagementFactory factory = new AutobahnManagementFactory(
		"env1");

	static private AutobahnManagement[] domains = factory.createInstances();
	
	static private AutobahnManagement domain1 = domains[0];
	static private AutobahnManagement domain2 = domains[1];
	static private AutobahnManagement domain3 = domains[2];
	

	private static StatusObserver observer = null;
	
	private MockToolServer mockServer = null;
	private MockTool mock1 = null;
	private MockTool mock2 = null;
	
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
	
	@Before
	public void setUp() {
		// Program the behaviour
		mockServer = new MockToolServer(9080);
		
		mock1 = new MockTool("tool1", "programmer1");
		mock1.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.OK, 5));
		mockServer.addMockTool(mock1);
		
		mock2 = new MockTool("tool2", "programmer2");
		mock2.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.OK, 5));
		mockServer.addMockTool(mock2);
		
		mockServer.start(true);
	}
	
	@Test
	public void testReservation() throws UserAccessPointException {
		System.out.println("Tool1");
		
		UserAccessPoint uap = domain1.getUserAccessPoint();
		
        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "20sec", "40sec", "Test1", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        // clean the state
        uap.cancelService(sid);
        assertEquals(ReservationState.CANCELLED, observer.waitAndGetEvent(sid));
	}

	@Test
	public void testFailedActivation() throws UserAccessPointException {
		System.out.println("Tool2");
		
		mock1.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.SYS_EX, 5));
		
		// plug the mock to the DM
		domain2.setDmProperty("tool.address", mock1.getToolAddress());
		
		observer.waitForDomains(domain2);
		
        UserAccessPoint uap = domain1.getUserAccessPoint();  

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "20sec", "40sec", "Test1", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        // should be failed
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
	}

	@Test
	public void testFailedActivationIn2Domains() throws UserAccessPointException {
		System.out.println("Tool3");
		
		mock1.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.SYS_EX, 3));
		mock2.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.SYS_EX, 5));
		
		// plug the mock to the DM
		domain2.setDmProperty("tool.address", mock1.getToolAddress());
		domain3.setDmProperty("tool.address", mock2.getToolAddress());
		
		observer.waitForDomains(domain2, domain3);
		
        UserAccessPoint uap = domain1.getUserAccessPoint();  

        ServiceRequest req = RequestParser.createServiceRequest(
        		"10.10.32.5", "10.11.32.10", 1000000000, "20sec", "40sec", "Test1", false);
        
        String sid = uap.submitServiceAndRegister(req, callback);
        observer.registerService(sid);

        // should be scheduled
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));

        // should be failed
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));
	}
	
	@After
	public void tearDown() {
		mockServer.stop();
	}
	
	@AfterClass
	public static void cleanup() {
        try {
        	Thread.sleep(10 * 1000);
        } catch(Exception e) {
        }
        observer.stop();
        
		System.out.println(" - -- Stopping instances");
		domain1.stopInstance();
		domain2.stopInstance();
		domain3.stopInstance();
	}
}

