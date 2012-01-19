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
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.textclient.RequestParser;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutobahnTest {

	private final static String callback = "http://localhost:9090/callback";
	
	static private AutobahnManagementFactory factory = new AutobahnManagementFactory(
		"env1");

	static private AutobahnManagement[] domains = factory.createInstances();
	
	static private AutobahnManagement domain1 = domains[0];
	static private AutobahnManagement domain2 = domains[1];
	static private AutobahnManagement domain3 = domains[2];

	private MockToolServer mockServer = null;

	private RequestParser parser = new RequestParser();
	private static StatusObserver observer = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtils.setLoggerClass(Log4jLogger.class);
		observer = new StatusObserver(callback);
		
		System.out.println(" - -- Starting instances...");
		observer.start();

        AutobahnManagement.cleanOldReservations(domains);
		AutobahnManagement.start(observer, domains);
		
		observer.waitForDomains(domain1, domain2, domain3);
		
		System.out.println(" - -- Started");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("--- --- --- --- --- ---");
		mockServer = null;
	}

	@After
	public void tearDown() throws Exception {
		if(mockServer != null)
			mockServer.stop();
	}

	@Test
	public void testSuccessfullReservation() throws Exception {
		
		System.out.println("1");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();
        
        for(PortType p : uap.getAllClientPorts())
        	System.out.println(p.getAddress());
        
		ServiceRequest req = parser.readServiceRequest("/requests/request1.txt");
		req.setJustification("ATest1");
		
        String sid = uap.submitServiceAndRegister(req, observer.getUrl());
        
        observer.registerService(sid);
        
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));
        
        // Cancel and wait till cancelled
        uap.cancelService(sid);
        assertEquals(ReservationState.CANCELLED, observer.waitAndGetEvent(sid));
	}
	
	@Test
	public void testReservationActivation() throws Exception {
		
		System.out.println("2");
		
		ServiceRequest req = parser.readServiceRequest("/requests/request2.txt");
		req.setJustification("ATest2");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();

        String sid = uap.submitServiceAndRegister(req, observer.getUrl());
        
        observer.registerService(sid);
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));
        
        assertEquals(ReservationState.ACTIVE, observer.waitAndGetEvent(sid));

        assertEquals(ReservationState.FINISHED, observer.waitAndGetEvent(sid));
	}
	
	@Test
	public void testFailedActivation() throws Exception {
		System.out.println("3");
		
		mockServer = new MockToolServer(9080);
		
		MockTool tool1 = new MockTool("tool", "programmer");
		tool1.getProgrammer().setAddBehaviour(new ToolBehaviour(Result.SYS_EX, 5));
		mockServer.addMockTool(tool1);
		
		// starts in new Thread
		mockServer.start(true);
		
		// plug the mock to the DM
		domain2.setDmProperty("tool.address", tool1.getToolAddress());
		observer.waitForDomains(domain2);
		
		ServiceRequest req = parser.readServiceRequest("/requests/request2.txt");
		req.setJustification("ATest3");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();
        
        String sid = uap.submitServiceAndRegister(req, observer.getUrl());
        
        observer.registerService(sid);
        
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));
        
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid));

		// unplug the mock from the DM
		domain2.setDmProperty("tool.address", "none");
		observer.waitForDomains(domain2);
	}
	
	@Test
	public void testReservingTwiceTheSame() throws Exception {
		System.out.println("4");
		
		ServiceRequest req = parser.readServiceRequest("/requests/request1.txt");
		req.setJustification("ATest4");
		
        UserAccessPoint uap = domain1.getUserAccessPoint();  

        String sid = uap.submitServiceAndRegister(req, observer.getUrl());
        
        observer.registerService(sid);
        
        assertEquals(ReservationState.SCHEDULED, observer.waitAndGetEvent(sid));
        
        // Another reservation
        String sid2 = uap.submitServiceAndRegister(req, observer.getUrl());
        
        observer.registerService(sid2);
        
        assertEquals(ReservationState.FAILED, observer.waitAndGetEvent(sid2));

        // cancel the first service
        uap.cancelService(sid);
        observer.waitForEvent(sid);
	}
	
	@AfterClass
	public static void cleanup() {
        try {
        	Thread.sleep(5 * 1000);
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
