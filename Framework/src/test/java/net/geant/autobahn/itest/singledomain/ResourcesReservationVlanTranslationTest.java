/**
 * 
 */
package net.geant.autobahn.itest.singledomain;


import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.*;

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
import net.geant.autobahn.intradomain.IntradomainPath;
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

/**
 * @author Jacek
 *
 */
public class ResourcesReservationVlanTranslationTest {

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
        new EthTopology4().domain1(tbuilder);
            
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
        taProps.setProperty("domainName", "pionier");
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
	public void testSimpleReservationNoTranslation() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 1 --");
		
        Link[] links = new Link[] {
        		all_links.get("pionier.Link.11"),
                all_links.get("pionier.Link.7"), 
        		all_links.get("pionier.Link.13")};
        
        DomainConstraints[] dcons = check(dm, links, _1Mb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        TestCase.assertEquals(1, dcons[0].getPathConstraints().size());
        
        TestCase.assertEquals("15-30", getVlan(dcons[0]));
        TestCase.assertEquals("15-30", getVlan(dcons[1]));
        
        try {
			reserve(dm, "res1", links, _1Mb, 31, "01-07-2020 13:30:00",
			        "03-07-2020 13:30:00");
			TestCase.fail("OverSub ex should be thrown!");
		} catch (OversubscribedException e) {
		}
		
		reserve(dm, "res1", links, _1Mb, 30, "01-07-2020 13:30:00",
				"03-07-2020 13:30:00");

		try {
			reserve(dm, "res2", links, _1Mb, 30, "01-07-2020 13:30:00",
					"03-07-2020 13:30:00");
			TestCase.fail("Con Already in Use ex should be thrown!");
		} catch (ConstraintsAlreadyUsedException cauex) {
		}
		
		dm.removeReservation("res1");
		
		System.out.println(net.geant.autobahn.calendar.AccessPoint.getInstance().getConstraintsCalendar());
	}
	
	@Test
	public void testSimpleReservation() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 2 --");
		
		tbuilder.switchVlanTranslationSupport(true, "all");
		
        Link[] links = new Link[] {
        		all_links.get("pionier.Link.11"),
                all_links.get("pionier.Link.7"), 
        		all_links.get("pionier.Link.13")};
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        TestCase.assertEquals(3, dcons[0].getPathConstraints().size());
        
		reserve(dm, "res1", links, _1Mb, 15, 50, "01-07-2020 13:30:00",
        	"03-07-2020 13:30:00");
		
		IntradomainPath path = buildIntradomainPath(tbuilder, "p1.3-p1.5", "p1.6-p1.8", "p1.7-p1.13", "p1.4-p1.11", "p1.9-p1.12", "p1.10-p1.14");
		
		IntradomainPath res = cal.getConstraints(path, cal("01-07-2020 13:30:00"), cal("03-07-2020 13:30:00"));
		
		System.out.println(res.getInfo());
		
		System.out.println(net.geant.autobahn.calendar.AccessPoint.getInstance().getConstraintsCalendar());
		
		dm.removeReservation("res1");
	}
	
	@Test
	public void testSimpleReservation3() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 3 --");
		
		tbuilder.switchVlanTranslationSupport(true, "all");
		
        Link[] links = new Link[] {
        		all_links.get("pionier.Link.11"),
                all_links.get("pionier.Link.7"), 
        		all_links.get("pionier.Link.13")};
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        System.out.println(dcons[0]);
        System.out.println(dcons[1]);
	}
	
	@Test
	public void testSimpleReservation4() throws OversubscribedException, AAIException, ConstraintsAlreadyUsedException {
		System.out.println(" -- 4 --");
		
		tbuilder.switchVlanTranslationSupport(false, "all");
		//tbuilder.switchVlanTranslationSupport(false, "Node1.1", "Node1.3");
		
        Link[] links = new Link[] {
        		all_links.get("pionier.Link.11"),
                all_links.get("pionier.Link.7"), 
        		all_links.get("pionier.Link.13")};
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        System.out.println(dcons[0]);
        System.out.println(dcons[1]);
	}
}
