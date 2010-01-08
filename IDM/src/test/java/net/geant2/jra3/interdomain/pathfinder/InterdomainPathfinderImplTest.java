/**
 * 
 */
package net.geant2.jra3.interdomain.pathfinder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Node;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.network.Port;
import net.geant2.jra3.network.ProvisioningDomain;
import net.geant2.jra3.reservation.Reservation;

/**
 * @author kostas
 *
 */
public class InterdomainPathfinderImplTest {

    Topology topology_straight,topology_multihomed;
    AdminDomain ad1,ad2,ad3,ad4;
    ProvisioningDomain pd1,pd2,pd3,pd4;
    Node n1_1,n2_1,n2_2,n3_1,n3_2,n4_1;
    Port p1_1_1,p2_1_1,p2_1_2,p2_2_1,p2_2_2,p3_1_1,p3_1_2,p3_2_1,p3_2_2,p4_1_1;
    Port p1_1_2,p2_1_3;
    Port p3_2_3,p4_1_2;
    Port p2_2_3,p3_1_3;
    Link l1_2,l2_2,l2_3,l3_3,l3_4;
    Link l1_2_2;
    Link l3_4_2;
    Link l2_3_2;
    List<AdminDomain> ads;
    List<Node> ns;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        ad1 = new AdminDomain("1", true);   //client
        pd1 = new ProvisioningDomain("1", "PIP", ad1);
        ad2 = new AdminDomain("2", false);
        pd2 = new ProvisioningDomain("2", "PIP", ad2);
        ad3 = new AdminDomain("3", false);
        pd3 = new ProvisioningDomain("3", "PIP", ad3);
        ad4 = new AdminDomain("4", true);   //client
        pd4 = new ProvisioningDomain("4", "PIP", ad4);

        n1_1 = new Node("PSC", "n1_1", "n1_1", pd1);
        n2_1 = new Node("PSC", "n2_1", "n2_1", pd2);
        n2_2 = new Node("PSC", "n2_2", "n2_2", pd2);
        n3_1 = new Node("PSC", "n3_1", "n3_1", pd3);
        n3_2 = new Node("PSC", "n3_2", "n3_2", pd3);
        n4_1 = new Node("PSC", "n4_1", "n4_1", pd4);
        
        p1_1_1 = new Port("p1_1_1", "p1_1_1", "IP", false, n1_1);
        p2_1_1 = new Port("p2_1_1", "p2_1_1", "IP", false, n2_1);
        p2_1_2 = new Port("p2_1_2", "p2_1_2", "IP", false, n2_1);
        p2_2_1 = new Port("p2_2_1", "p2_2_1", "IP", false, n2_2);
        p2_2_2 = new Port("p2_2_2", "p2_2_2", "IP", false, n2_2);
        p3_1_1 = new Port("p3_1_1", "p3_1_1", "IP", false, n3_1);
        p3_1_2 = new Port("p3_1_2", "p3_1_2", "IP", false, n3_1);
        p3_2_1 = new Port("p3_2_1", "p3_2_1", "IP", false, n3_2);
        p3_2_2 = new Port("p3_2_2", "p3_2_2", "IP", false, n3_2);
        p4_1_1 = new Port("p4_1_1", "p4_1_1", "IP", false, n4_1);
        
        l1_2 = Link.createInterDomainLink(p1_1_1, p2_1_1, 1000000000);
        l1_2.setBodID("l1_2");
        l2_2 = Link.createVirtualLink(p2_1_2, p2_2_1, 1000000000);
        l2_2.setBodID("l2_2");
        l2_3 = Link.createInterDomainLink(p2_2_2, p3_1_1, 1000000000);
        l2_3.setBodID("l2_3");
        l3_3 = Link.createVirtualLink(p3_1_2, p3_2_1, 1000000000);
        l3_3.setBodID("l3_3");
        l3_4 = Link.createInterDomainLink(p3_2_2, p4_1_1, 1000000000);
        l3_4.setBodID("l3_4");
        
        ads = new ArrayList<AdminDomain>();
        ns = new ArrayList<Node>();
        ads.add(ad1); ads.add(ad2); ads.add(ad3); ads.add(ad4);
        ns.add(n1_1); ns.add(n2_1); ns.add(n2_2); ns.add(n3_1); ns.add(n3_2); ns.add(n4_1);

        // Additional multi-homed link at the beginning
        p1_1_2 = new Port("p1_1_2", "p1_1_2", "IP", false, n1_1);
        p2_1_3 = new Port("p2_1_3", "p2_1_3", "IP", false, n2_1);
        l1_2_2 = Link.createInterDomainLink(p1_1_2, p2_1_3, 1000000000);
        l1_2_2.setBodID("l1_2_2");
        
        // Additional multi-homed link at the end
        p3_2_3 = new Port("p3_2_3", "p3_2_3", "IP", false, n3_2);
        p4_1_2 = new Port("p4_1_2", "p4_1_2", "IP", false, n4_1);
        l3_4_2 = Link.createInterDomainLink(p3_2_3, p4_1_2, 1000000000);
        l3_4_2.setBodID("l3_4_2");
        
        // Additional multi-homed link at the middle
        p2_2_3 = new Port("p2_2_3", "p2_2_3", "IP", false, n2_2);
        p3_1_3 = new Port("p3_1_3", "p3_1_3", "IP", false, n3_1);
        l2_3_2 = Link.createInterDomainLink(p2_2_3, p3_1_3, 1000000000);
        l2_3_2.setBodID("l2_3_2");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology
     */
    @Test
    public void testFindInterdomainPaths_straight() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4);
        // [n1_1]---[n2_1]---[n2_2]---[n3_1]---[n3_2]---[n4_1]
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);        
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path1 = new Path();
        path1.setLinks(pathls);

        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_straight);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        System.out.println("---------Straight");
        System.out.println("Expected:"+path1.toString());
        System.out.println("Result:"+respath1.toString());
        assertEquals(respath1, path1);

        List<Link> expectedLinks = path1.getLinks();
        List<Link> resLinks = respath1.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed start.
     * The reservation requests the upper port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedstart1() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l1_2_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4);
        // [n1_1]===[n2_1]---[n2_2]---[n3_1]---[n3_2]---[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------Multihomedstart1");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }


    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed start.
     * The reservation requests the lower port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedstart2() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l1_2_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4);
        // [n1_1]===[n2_1]---[n2_2]---[n3_1]---[n3_2]---[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);
        // p112---[p213-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_2);
        rsv.setEndPort(p4_1_1);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------Multihomedstart2");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed end.
     * The reservation requests the upper port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedend1() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4); ls.add(l3_4_2);
        // [n1_1]---[n2_1]---[n2_2]---[n3_1]---[n3_2]===[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------Multihomedend1");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed end.
     * The reservation requests the lower port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedend2() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4); ls.add(l3_4_2);
        // [n1_1]---[n2_1]---[n2_2]---[n3_1]---[n3_2]===[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4_2);
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p323]---p412
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_2);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------Multihomedend2");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed start and end.
     * The reservation requests the upper ports both at start and at the end links.
     */
    @Test
    public void testFindInterdomainPaths_multihomedstart_end1() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l1_2_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4); ls.add(l3_4_2);
        // [n1_1]===[n2_1]---[n2_2]---[n3_1]---[n3_2]===[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------multihomedstart_end1");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed start and end.
     * The reservation requests the lower ports both at start and at the end links.
     */
    @Test
    public void testFindInterdomainPaths_multihomedstart_end2() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l1_2_2); ls.add(l2_2); ls.add(l2_3); ls.add(l3_3); ls.add(l3_4); ls.add(l3_4_2);
        // [n1_1]===[n2_1]---[n2_2]---[n3_1]---[n3_2]===[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4_2);
        // p112---[p213-p212]---[p221-p222]---[p311-p312]---[p321-p323]---p412
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_2);
        rsv.setEndPort(p4_1_2);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------multihomedstart_end2");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed middle link.
     * The reservation requests the upper port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedmiddle_exclude1() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l2_2); ls.add(l2_3); ls.add(l2_3_2); ls.add(l3_3); ls.add(l3_4);
        // [n1_1]---[n2_1]---[n2_2]===[n3_1]---[n3_2]---[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3); pathls.add(l3_3); pathls.add(l3_4);
        // p111---[p211-p212]---[p221-p222]---[p311-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        List<Link> excludedls = new ArrayList<Link>();
        excludedls.add(l2_3_2);
        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, excludedls);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------multihomedmiddle_exclude1");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant2.jra3.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with multi-homed middle link.
     * The reservation requests the lower port.
     */
    @Test
    public void testFindInterdomainPaths_multihomedmiddle_exclude2() {
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_2); ls.add(l2_2); ls.add(l2_3); ls.add(l2_3_2); ls.add(l3_3); ls.add(l3_4);
        // [n1_1]---[n2_1]---[n2_2]===[n3_1]---[n3_2]---[n4_1]
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_2); pathls.add(l2_2); pathls.add(l2_3_2); pathls.add(l3_3); pathls.add(l3_4);
        // p111---[p211-p212]---[p221-p223]---[p313-p312]---[p321-p322]---p411
        Path path = new Path();
        path.setLinks(pathls);
        
        InterdomainPathfinderImpl pf = new InterdomainPathfinderImpl(topology_multihomed);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p4_1_1);

        List<Link> excludedls = new ArrayList<Link>();
        //excludedls.add(l2_3);
        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, excludedls);
        Path respath = pathsres.next();    // Get 1st path of result

        System.out.println("---------multihomedmiddle_exclude2");
        System.out.println("Expected:"+path.toString());
        System.out.println("Result:"+respath.toString());
        assertEquals(respath, path);

        List<Link> expectedLinks = path.getLinks();
        List<Link> resLinks = respath.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#getNeighbours(net.geant2.jra3.network.AdminDomain)}.
     */
    @Ignore
    public void testGetNeighbours() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.geant2.jra3.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPathsTest(java.util.List, java.lang.String, java.lang.String, net.geant2.jra3.interdomain.pathfinder.Topology)}.
     */
    @Ignore
    public void testFindInterdomainPathsTest() {
        fail("Not yet implemented");
    }

}
