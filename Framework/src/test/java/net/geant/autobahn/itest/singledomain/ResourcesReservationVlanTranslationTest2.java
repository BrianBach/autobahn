package net.geant.autobahn.itest.singledomain;


import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper._1Mb;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.buildTopology;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.check;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import junit.framework.TestCase;
import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.calendar.ResourcesReservationCalendarImpl;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.converter.AccessPoint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.PersistentReservationsManager;
import net.geant.autobahn.intradomain.ResourcesReservation;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar;
import net.geant.autobahn.utils.EthTopology4;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourcesReservationVlanTranslationTest2 {

    private ResourcesReservation dm = null;
    private IntradomainPathfinder pathfinder = null;
    private PersistentReservationsManager prman = null;
    private Properties props, taProps, calProps = null;
    
    private Map<String, Link> all_links = new HashMap<String, Link>();
	
    private Endpoint TApoint, Calpoint;
    private ResourcesReservationCalendar cal = null;

    private IntraTopologyBuilder tbuilder;
    
    private void ethInit(boolean useDb) throws IOException {
        // Topology
        //June08IntraTopology topo = new June08IntraTopology();
        tbuilder = new IntraTopologyBuilder(false);
        new EthTopology4().domain2(tbuilder);
            
        prman = new PersistentReservationsManager();
        
        props = new Properties();
        props.setProperty("db.type", "eth");
        //props.setProperty("tool.address", "http://leontodon.man.poznan.pl:8081/autobahn/tool");
        props.setProperty("topologyabstraction.address", "http://localhost:8090/autobahn/topologyabstraction");
        props.setProperty("tool.address", "none");
        props.setProperty("idm.address", "none");
        props.setProperty("tool.time.setup", "0");
        props.setProperty("tool.time.teardown", "0");
        props.setProperty("resourcesreservationcalendar.address", "http://localhost:8090/autobahn/resourcesreservationcalendar");

        taProps = new Properties();
        taProps.setProperty("id.nodes", "10.10.0.0/24");
        taProps.setProperty("id.ports", "10.10.32.0/24");
        taProps.setProperty("id.links", "10.10.64.0/24");
        taProps.setProperty("public.ids.file", "src/test/resources/etc/public_ids.properties");
        taProps.setProperty("lookuphost","http://localhost:21080/perfsonar-java-xml-ls/services/LookupService");

        calProps = new Properties();
        calProps.setProperty("db.type", "ethernet");
        calProps.setProperty("tool.time.setup", "120");
        calProps.setProperty("tool.time.teardown", "60");
    }
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        ethInit(false);

        IntradomainTopology topology = buildTopology(tbuilder);
        tbuilder.switchVlanTranslationSupport(false, "all");
        
        // Init intradomain pathfinder
        pathfinder = IntradomainPathfinderFactory.getIntradomainPathfinder(topology);

        AccessPoint taAp = AccessPoint.getInstance(taProps);
        
        // Send topology to TA
        taAp.setIntradomainTopology(topology, "ethernet");
        // Run abstraction of internal part of topology process
        taAp.abstractInternalPartOfTopology();
        // Run abstraction of external part of topology process
        taAp.abstractExternalPartOfTopology_File("src/test/resources/etc/external_ids.properties");
    
        System.out.println("Abstract links:");
        for(Link l : taAp.getAbstractLinks()) {
            all_links.put(l.getBodID(), l);
            System.out.println(l);
        }

        // Publish TA endpoint
        //TApoint = Endpoint.publish("http://localhost:8080/autobahn/topologyabstraction", 
        TApoint = Endpoint.publish(props.getProperty("topologyabstraction.address"), 
                new net.geant.autobahn.converter.TopologyAbstractionImpl());

        cal = new ResourcesReservationCalendarImpl(calProps);
        
        // Publish Calendar endpoint
        Calpoint = Endpoint.publish(props.getProperty("resourcesreservationcalendar.address"), cal);

        // init DomainManager
        dm = new ResourcesReservation(pathfinder, prman, props);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
        dm.dispose();
        TApoint.stop();
        Calpoint.stop();
	}

	@Test
	public void testSimpleReservationTranslationOff() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 1 --");
		
        Link[] links = new Link[] {
        		all_links.get("10.10.64.1"),
                all_links.get("10.10.64.0"), 
        		all_links.get("10.10.64.2")};
     
        try {
	        DomainConstraints[] dcons = check(dm, links, _1Mb, "01-07-2020 13:30:00",
	                "03-07-2020 13:30:00");
	        TestCase.fail("OverSub should be thrown");
        } catch(OversubscribedException overEx) {
        	
        }
	}
	
	@Test
	public void testSimpleReservationTranslationOn() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 2 --");

		tbuilder.switchVlanTranslationSupport(true, "all");
		
        Link[] links = new Link[] {
        		all_links.get("10.10.64.1"),
                all_links.get("10.10.64.0"), 
        		all_links.get("10.10.64.2")};
        
        DomainConstraints[] dcons = check(dm, links, _1Mb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        System.out.println(dcons[0]);
        System.out.println(dcons[1]);
	}

	@Test
	public void testSimpleReservationTranslationOnOnlyEdgeNodes() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 3 --");

		tbuilder.switchVlanTranslationSupport(true, "Node1.1", "Node1.3");
		
        Link[] links = new Link[] {
        		all_links.get("10.10.64.1"),
                all_links.get("10.10.64.0"), 
        		all_links.get("10.10.64.2")};
        
        DomainConstraints[] dcons = check(dm, links, _1Mb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        System.out.println(dcons[0]);
        System.out.println(dcons[1]);
        
        SingleDomainTestHelper.reserve(dm, "res1", links, _1Mb, 15, 60, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
	}
	
	@Test
	public void testSimpleReservationTranslationOnOnlyOneEdgeNode() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 4 --");

		tbuilder.switchVlanTranslationSupport(true, "Node1.3");
		
        Link[] links = new Link[] {
        		all_links.get("10.10.64.1"),
                all_links.get("10.10.64.0"), 
        		all_links.get("10.10.64.2")};
        
        DomainConstraints[] dcons = check(dm, links, _1Mb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        TestCase.assertNotNull(dcons[0]);
        TestCase.assertEquals(1, dcons[0].getPathConstraints().size());
	}
}
