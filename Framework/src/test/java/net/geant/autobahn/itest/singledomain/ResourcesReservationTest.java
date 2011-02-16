package net.geant.autobahn.itest.singledomain;

import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper._1Gb;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper._1Mb;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.buildTopology;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.cal;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.check;
import static net.geant.autobahn.itest.singledomain.SingleDomainTestHelper.reserve;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import junit.framework.TestCase;
import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.converter.AccessPoint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.PersistentReservationsManager;
import net.geant.autobahn.intradomain.ResourcesReservation;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.utils.GN2_Jan08_DemoTopology;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.cxf.common.logging.LogUtils;

/**
 * @author jacek
 *
 */
public class ResourcesReservationTest extends TestCase {

    private ResourcesReservation dm = null;
    private IntradomainPathfinder pathfinder = null;
    private PersistentReservationsManager prman = null;
    private Properties props, taProps, calProps = null;
    
    private Map<String, Link> all_links = new HashMap<String, Link>();
    
    private Endpoint TApoint, Calpoint;

    private IntraTopologyBuilder builder;
    
    public ResourcesReservationTest(String name) throws IOException {
        super(name);

        LogUtils.setLoggerClass(Log4jLogger.class);
    }

    private void ethInit(boolean useDb) throws IOException {
        // Topology
        //June08IntraTopology topo = new June08IntraTopology();
        GN2_Jan08_DemoTopology topo = new GN2_Jan08_DemoTopology();
        builder = new IntraTopologyBuilder(false);
        topo.pionier(builder);
            
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
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ethInit(false);
        
        IntradomainTopology topology = buildTopology(builder);
        
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

        // Publish Calendar endpoint
        //Calpoint = Endpoint.publish("http://localhost:8080/autobahn/resourcesreservationcalendar", 
        Calpoint = Endpoint.publish(props.getProperty("resourcesreservationcalendar.address"), 
                new net.geant.autobahn.calendar.ResourcesReservationCalendarImpl(calProps));

        // init DomainManager
        dm = new ResourcesReservation(pathfinder, prman, props);
        System.out.println("-----");
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        
        dm.dispose();
        TApoint.stop();
        Calpoint.stop();
    }

    public void testCheckingEmpty() throws OversubscribedException, AAIException {
        System.out.println("---testCheckingEmpty");
        Link[] links = new Link[] {
                all_links.get("10.13.64.3"), 
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1")};
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        assertEquals(1, dcons[0].getPathConstraints().size());
    }
    
    public void testReservingAll() throws OversubscribedException,
            ConstraintsAlreadyUsedException, AAIException {
        System.out.println("---testReservingAll");
        Link[] links = new Link[] {
                all_links.get("10.13.64.3"), 
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1")};
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        assertEquals(1, dcons[0].getPathConstraints().size());
        
        reserve(dm, "res1", links, _1Gb, 160, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        try {
            check(dm, links, _1Gb, "01-07-2020 13:30:00", "03-07-2020 13:30:00");
            fail("Oversubsribed should be thrown!");
        } catch(OversubscribedException e) {
            // expected
        }
        
        // Clear all reservations
        dm.removeReservation("res1");
    }
    
    public void testReservingPartOfCapacity() throws ConstraintsAlreadyUsedException, OversubscribedException, AAIException {
        System.out.println("---testReservingPartOfCapacity");
        Link[] links = new Link[] {
                all_links.get("10.13.64.3"), 
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1")};

        check(dm, links, 500 * _1Mb, "01-07-2020 13:30:00", "03-07-2020 13:30:00");
        
        reserve(dm, "res1", links, 500 * _1Mb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        reserve(dm, "res2", links, 500 * _1Mb, 151, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");

        try {
            check(dm, links, 500 * _1Mb, "01-07-2020 13:30:00",
                    "03-07-2020 13:30:00");
            fail("Oversubsribed should be thrown!");
        } catch (OversubscribedException e) { }
        
        // Clear all reservations
        dm.removeReservation("res1");
        dm.removeReservation("res2");
    }
    
    public void testNotOverlapping() throws ConstraintsAlreadyUsedException,
            OversubscribedException, AAIException {
        System.out.println("---testNotOverlapping");
        Link[] links = new Link[] {
                all_links.get("10.13.64.3"), 
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1")};
        
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        DomainConstraints[] dcons = check(dm, links, _1Gb, "05-07-2020 13:30:00", "06-07-2020 13:30:00");
        
        assertEquals(1, dcons[0].getPathConstraints().size());
        
        PathConstraints pcon = dcons[0].getPathConstraints().get(0);
        RangeConstraint rcon = pcon.getRangeConstraint(ConstraintsNames.VLANS);
        
        assertEquals(150, rcon.getFirstValue());
        
        reserve(dm, "res2", links, _1Gb, 150, "05-07-2020 13:30:00",
                "06-07-2020 13:30:00");
        
        // Clear all reservations
        dm.removeReservation("res1");
        dm.removeReservation("res2");
    }
    
    public void testReservingSameConstraints() throws OversubscribedException,
            ConstraintsAlreadyUsedException, AAIException {
        System.out.println("---testReservingSameConstraints");
        Link[] links = new Link[] {
                all_links.get("10.13.64.3"), 
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1")};
        
        reserve(dm, "res1", links, 500 * _1Mb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");

        DomainConstraints[] dcons = check(dm, links, _1Gb, "05-07-2020 13:30:00",
                "06-07-2020 13:30:00");

        assertEquals(1, dcons[0].getPathConstraints().size());
        
        try {
            reserve(dm, "res2", links, 500 * _1Mb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
            fail("Constraints in use should be thrown!");
        } catch(ConstraintsAlreadyUsedException e) { }
        
        // Clear all reservations
        dm.removeReservation("res1");
    }

    public void testReservingOverlapping() throws OversubscribedException,
            ConstraintsAlreadyUsedException, AAIException {
        System.out.println("---testReservingOverlapping");
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };

        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");

        try {
            check(dm, links, _1Gb, "02-07-2020 13:30:00", "06-07-2020 13:30:00");
            fail("Oversubscribed should be thrown");
        } catch (OversubscribedException e) { }
        
        // Clear all reservations
        dm.removeReservation("res1");
    }
    
    public void testReservingOverlappingSetupTime() throws OversubscribedException,
            ConstraintsAlreadyUsedException, AAIException {
        System.out.println("---testReservingOverlappingSetupTime");
        
        props.setProperty("tool.time.setup", "60");
        props.setProperty("tool.time.teardown", "30");

        dm = new ResourcesReservation(pathfinder, prman, props);
        
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        try {
            // Reservation additional time is:
            // 30 sec for tear down and 1 min for setup
            check(dm, links, _1Gb, "03-07-2020 13:31:10", "05-07-2020 13:30:00");
            fail("Oversubscribed should be thrown");
        } catch (OversubscribedException e) { }
        
        check(dm, links, _1Gb, "03-07-2020 13:33:00", "05-07-2020 13:30:00");
        
        // Clear all reservations
        dm.removeReservation("res1");
    }

    public void testReservingAndRemoving()
            throws ConstraintsAlreadyUsedException, OversubscribedException, AAIException {
        System.out.println("---testReservingAndRemoving");
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");
        
        try {
            // Reservation additional time is:
            // 1 min for tear down and 1 min for setup
            check(dm, links, _1Gb, "01-07-2020 13:30:00", "03-07-2020 13:30:00");
            fail("Oversubscribed should be thrown");
        } catch (OversubscribedException e) { }
        
        dm.removeReservation("res1");
        
        check(dm, links, _1Gb, "01-07-2020 13:30:00", "03-07-2020 13:30:00");
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
            "03-07-2020 13:30:00");
        
        // Clear all reservations
        dm.removeReservation("res1");
    }
    
    public void testReservingAndFinishing()
            throws ConstraintsAlreadyUsedException, InterruptedException,
            OversubscribedException, AAIException {
        System.out.println("---testReservingAndFinishing");
        
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 5);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, 30);
        
        reserve(dm, "res1", links, _1Gb, 150, 150, now, end);

        // check now
        try {
            now = Calendar.getInstance();
            end = (Calendar) now.clone();
            end.add(Calendar.MINUTE, 30);
            
            check(dm, links, _1Gb, now, end);
            fail("Oversubscribed should be thrown");
        } catch (OversubscribedException e) { }

        Thread.sleep(40 * 1000);

        end = Calendar.getInstance();
        check(dm, links, _1Gb, now, end);
    }
    
    // ---------------- MODIFICATION ----------------------
    public void testSuccessfullModifying()
            throws ConstraintsAlreadyUsedException, OversubscribedException, AAIException {
        System.out.println("---testSuccessfullModifying");
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");

        boolean possible = dm.checkModification("res1", cal("01-07-2020 13:30:00"), 
                cal("05-07-2020 13:30:00"));
        
        assertTrue(possible);
        
        check(dm, links, _1Gb, "04-07-2020 13:30:00", "05-07-2020 13:30:00");
        
        dm.modifyReservation("res1", cal("01-07-2020 13:30:00"), 
                cal("05-07-2020 13:30:00"));
        
        try {
            check(dm, links, _1Gb, "04-07-2020 13:30:00", "05-07-2020 13:30:00");
            fail("Should be oversubscribed !");
        } catch (OversubscribedException e) { }
        
        // Clear all reservations
        dm.removeReservation("res1");
    }
    
    public void testModifyingConfilictingReservation() throws ConstraintsAlreadyUsedException, OversubscribedException, AAIException {
        System.out.println("---testModifyingConfilictingReservation");
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        reserve(dm, "res1", links, _1Gb, 150, "01-07-2020 13:30:00",
                "03-07-2020 13:30:00");

        reserve(dm, "res2", links, _1Gb, 150, "04-07-2020 13:30:00",
            "08-07-2020 13:30:00");
        
        boolean possible = dm.checkModification("res1", cal("01-07-2020 13:30:00"), 
                cal("05-07-2020 13:30:00"));
        
        assertFalse(possible);
        
        try {
            check(dm, links, _1Gb, "01-07-2020 13:30:00", "03-07-2020 13:30:00");
            fail("Should be oversubscribed !");
        } catch (OversubscribedException e) { }
        
        // Clear all reservations
        dm.removeReservation("res1");
        dm.removeReservation("res2");
    }

    public void testModifyingActiveReservation()
            throws ConstraintsAlreadyUsedException, InterruptedException,
            OversubscribedException, AAIException {
        System.out.println("---testModifyingActiveReservation");
        Link[] links = new Link[] { 
                all_links.get("10.13.64.3"),
                all_links.get("10.10.64.0"), 
                all_links.get("10.10.64.1") };
        
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, 5);
        Calendar end = (Calendar) now.clone();
        end.add(Calendar.SECOND, 35);
        
        reserve(dm, "res1", links, _1Gb, 150, 150, now, end);
        
        // Wait for Active
        Thread.sleep(10 * 1000);

        Calendar nend = (Calendar) now.clone();
        nend.add(Calendar.SECOND, 60);
        
        boolean possible = dm.checkModification("res1", now, nend);
        assertTrue(possible);
        
        dm.modifyReservation("res1", now, nend);

        // Wait for old End time - 40 sec should be after 
        // initial reservation duration
        Thread.sleep(30 * 1000);
        
        // check now
        Calendar tmp = Calendar.getInstance();
        tmp.add(Calendar.MINUTE, 30);
        try {
            check(dm, links, _1Gb, Calendar.getInstance(), tmp);
            fail("Should be oversubscribed");
        } catch (OversubscribedException e) { }
        
        // Wait until modified reservation should finish
        Thread.sleep(30 * 1000);
        check(dm, links, _1Gb, Calendar.getInstance(), tmp);
    }
    
}
