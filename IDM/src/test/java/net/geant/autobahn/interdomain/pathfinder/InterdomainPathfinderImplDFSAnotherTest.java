/**
 * 
 */
package net.geant.autobahn.interdomain.pathfinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.useraccesspoint.PathInfo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author kostas
 *
 */
public class InterdomainPathfinderImplDFSAnotherTest {

    Topology topology_straight,topology_multihomed;
    AdminDomain ad1,ad2,ad3,ad4,ad5,ad6;
    ProvisioningDomain pd1,pd2,pd3,pd4,pd5,pd6;
    Node n1_1,n2_1,n3_1,n3_2,n3_3,n4_1,n5_1,n5_2,n6_1;
    Port p1_1_1;
    Port p2_1_1;
    Port p3_1_1,p3_1_2,p3_1_3,p3_1_4,p3_2_1,p3_2_2,p3_2_3,p3_3_1,p3_3_2,p3_3_3;
    Port p4_1_1;
    Port p5_1_1,p5_1_2,p5_1_3,p5_2_1,p5_2_2,p5_2_3;
    Port p6_1_1;
    Link l1_3,l2_3,l3_4,l3_5,l5_6;
    Link l3_3_1,l3_3_2,l3_3_3;
    Link l5_5_1,l5_5_2;
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
        ad4 = new AdminDomain("4", false);
        pd4 = new ProvisioningDomain("4", "PIP", ad4);
        ad5 = new AdminDomain("5", false);
        pd5 = new ProvisioningDomain("5", "PIP", ad5);
        ad6 = new AdminDomain("6", true);   //client
        pd6 = new ProvisioningDomain("6", "PIP", ad6);

        n1_1 = new Node("PSC", "n1_1", "n1_1", pd1);
        n2_1 = new Node("PSC", "n2_1", "n2_1", pd2);
        n3_1 = new Node("PSC", "n3_1", "n3_1", pd3);
        n3_2 = new Node("PSC", "n3_2", "n3_2", pd3);
        n3_3 = new Node("PSC", "n3_3", "n3_3", pd3);
        n4_1 = new Node("PSC", "n4_1", "n4_1", pd4);
        n5_1 = new Node("PSC", "n5_1", "n5_1", pd5);
        n5_2 = new Node("PSC", "n5_2", "n5_2", pd5);
        n6_1 = new Node("PSC", "n6_1", "n6_1", pd6);
        
        p1_1_1 = new Port("p1_1_1", "IP", false, n1_1);
        p2_1_1 = new Port("p2_1_1", "IP", false, n2_1);
        p3_1_1 = new Port("p3_1_1", "IP", false, n3_1);
        p3_1_2 = new Port("p3_1_2", "IP", false, n3_1);
        p3_1_3 = new Port("p3_1_3", "IP", false, n3_1);
        p3_1_4 = new Port("p3_1_4", "IP", false, n3_1);
        p3_2_1 = new Port("p3_2_1", "IP", false, n3_2);
        p3_2_2 = new Port("p3_2_2", "IP", false, n3_2);
        p3_2_3 = new Port("p3_2_3", "IP", false, n3_2);
        p3_3_1 = new Port("p3_3_1", "IP", false, n3_3);
        p3_3_2 = new Port("p3_3_2", "IP", false, n3_3);
        p3_3_3 = new Port("p3_3_3", "IP", false, n3_3);
        p4_1_1 = new Port("p4_1_1", "IP", false, n4_1);
        p5_1_1 = new Port("p5_1_1", "IP", false, n5_1);
        p5_1_2 = new Port("p5_1_2", "IP", false, n5_1);
        p5_1_3 = new Port("p5_1_3", "IP", false, n5_1);
        p5_2_1 = new Port("p5_2_1", "IP", false, n5_2);
        p5_2_2 = new Port("p5_2_2", "IP", false, n5_2);
        p5_2_3 = new Port("p5_2_3", "IP", false, n5_2);
        p6_1_1 = new Port("p6_1_1", "IP", false, n6_1);
        
        l1_3 = Link.createInterDomainLink(p1_1_1, p3_1_1, 1000000000);
        l1_3.setBodID("l1_3");
        l2_3 = Link.createInterDomainLink(p2_1_1, p3_2_1, 1000000000);
        l2_3.setBodID("l2_3");
        l3_4 = Link.createInterDomainLink(p3_1_3, p4_1_1, 1000000000);
        l3_4.setBodID("l3_4");
        l3_5 = Link.createInterDomainLink(p3_3_1, p5_1_1, 1000000000);
        l3_5.setBodID("l3_5");
        l5_6 = Link.createInterDomainLink(p5_2_1, p6_1_1, 1000000000);
        l5_6.setBodID("l5_6");
        l3_3_1 = Link.createVirtualLink(p3_1_2, p3_2_2, 1000000000);
        l3_3_1.setBodID("l3_3_1");
        l3_3_2 = Link.createVirtualLink(p3_2_3, p3_3_2, 1000000000);
        l3_3_2.setBodID("l3_3_2");
        l3_3_3 = Link.createVirtualLink(p3_1_4, p3_3_3, 1000000000);
        l3_3_3.setBodID("l3_3_3");
        l5_5_1 = Link.createVirtualLink(p5_1_2, p5_2_2, 1000000000);
        l5_5_1.setBodID("l5_5_1");
        l5_5_2 = Link.createVirtualLink(p5_1_3, p5_2_3, 1000000000);
        l5_5_2.setBodID("l5_5_2");
        
        ads = new ArrayList<AdminDomain>();
        ns = new ArrayList<Node>();
        ads.add(ad1); ads.add(ad2); ads.add(ad3); ads.add(ad4); ads.add(ad5); ads.add(ad6);
        ns.add(n1_1); ns.add(n2_1); ns.add(n3_1); ns.add(n3_2); ns.add(n3_3);
        ns.add(n4_1); ns.add(n5_1); ns.add(n5_2); ns.add(n6_1);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with reservation with user-included link
     */
    @Test
    public void testFindInterdomainPaths_straightIncl() {
        System.out.println("---------Straight with User Incl");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5); ls.add(l5_6);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(l5_5_1);
        //
        //      [n1_1]            [n2_1]
        //          \               /
        //           \             /
        //           [n3_1]---[n3_2]
        //            /   \      |  
        //           /     \     | 
        //          /       \    |
        //         /         [n3_3]
        //        /             |
        //     [n4_1]           |
        //                      |
        //                   [n5_1]
        //                     |
        //                     |
        //                   [n5_2]---n[6_1]
        //
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_3); pathls.add(l3_5); pathls.add(l5_5_1); pathls.add(l5_6);
        // p111---[p311-p314]---[p333-p331]---[p511-p512]---[p522-p521]---p611
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        HomeDomainReservation rsv = new HomeDomainReservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p6_1_1);
        PathInfo userIncl = new PathInfo();
        userIncl.addLink("l3_3_3");
        rsv.setUserInclude(userIncl);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        System.out.println("Expected:"+path1.toString());
        System.out.println("Result:"+respath1.toString());
        //assertEquals(respath1, path1);

        List<Link> expectedLinks = path1.getLinks();
        List<Link> resLinks = respath1.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with reservation with user-included link
     * End port is not the port attached to the last link, but a different node port
     */
    @Test
    public void testFindInterdomainPaths_straightIncl_AltPort() {
        System.out.println("---------Straight with User Incl, Alt Port");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5); ls.add(l5_6);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(l5_5_1);
        //
        //      [n1_1]            [n2_1]
        //          \               /
        //           \             /
        //           [n3_1]---[n3_2]
        //            /   \      |  
        //           /     \     | 
        //          /       \    |
        //         /         [n3_3]
        //        /             |
        //     [n4_1]           |
        //                      |
        //                   [n5_1]
        //                     |
        //                     |
        //                   [n5_2]---n[6_1]
        //
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_3); pathls.add(l3_5); pathls.add(l5_5_1);
        // p111---[p311-p314]---[p333-p331]---[p511-p512]---[p522-p521]
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        HomeDomainReservation rsv = new HomeDomainReservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p5_2_1);
        PathInfo userIncl = new PathInfo();
        userIncl.addLink("l3_3_3");
        rsv.setUserInclude(userIncl);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        assertEquals(false, pathsres.hasNext());
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a straight topology with reservation with user-excluded link
     */
    @Test
    public void testFindInterdomainPaths_straight() {
        System.out.println("---------Straight with User Excl");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5); ls.add(l5_6);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(l5_5_1);
        //
        //      [n1_1]            [n2_1]
        //          \               /
        //           \             /
        //           [n3_1]---[n3_2]
        //            /   \      |  
        //           /     \     | 
        //          /       \    |
        //         /         [n3_3]
        //        /             |
        //     [n4_1]           |
        //                      |
        //                   [n5_1]
        //                     |
        //                     |
        //                   [n5_2]---n[6_1]
        //
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_1); pathls.add(l3_3_2); pathls.add(l3_5); pathls.add(l5_5_1); pathls.add(l5_6);
        // p111---[p311-p312]---[p322-p323]---[p332-p331]---[p511-p512]---[p522-p521]---p611
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        HomeDomainReservation rsv = new HomeDomainReservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p6_1_1);
        PathInfo userExcl = new PathInfo();
        userExcl.addLink("l3_3_3");
        rsv.setUserExclude(userExcl);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        System.out.println("Expected:"+path1.toString());
        System.out.println("Result:"+respath1.toString());
        //assertEquals(respath1, path1);

        List<Link> expectedLinks = path1.getLinks();
        List<Link> resLinks = respath1.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a multihomed topology with reservation with user-included links
     */
    @Test
    public void testFindInterdomainPaths_multihomedIncl() {
        System.out.println("---------Multihomed with User Incl");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5); ls.add(l5_6);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(l5_5_1); ls.add(l5_5_2);
        //
        //      [n1_1]            [n2_1]
        //          \               /
        //           \             /
        //           [n3_1]---[n3_2]
        //            /   \      |  
        //           /     \     | 
        //          /       \    |
        //         /         [n3_3]
        //        /             |
        //     [n4_1]           |
        //                      |
        //                   [n5_1]
        //                     | |
        //                     | |
        //                   [n5_2]---n[6_1]
        //
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_3); pathls.add(l3_5); pathls.add(l5_5_1); pathls.add(l5_6);
        // p111---[p311-p314]---[p333-p331]---[p511-p512]---[p522-p521]---p611
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_multihomed);
        HomeDomainReservation rsv = new HomeDomainReservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p6_1_1);
        PathInfo userIncl = new PathInfo();
        userIncl.addLink("l3_3_3");
        userIncl.addLink("l5_5_1");
        rsv.setUserInclude(userIncl);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        System.out.println("Expected:"+path1.toString());
        System.out.println("Result:"+respath1.toString());
        //assertEquals(respath1, path1);

        List<Link> expectedLinks = path1.getLinks();
        List<Link> resLinks = respath1.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPaths(net.geant.autobahn.reservation.Reservation, java.util.List)}.
     * 
     * Tests IPF on a multihomed topology with reservation with user-excluded link
     */
    @Test
    public void testFindInterdomainPaths_multihomedExcl() {
        System.out.println("---------Multihomed with User Excl");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5); ls.add(l5_6);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(l5_5_1); ls.add(l5_5_2);
        //
        //      [n1_1]            [n2_1]
        //          \               /
        //           \             /
        //           [n3_1]---[n3_2]
        //            /   \      |  
        //           /     \     | 
        //          /       \    |
        //         /         [n3_3]
        //        /             |
        //     [n4_1]           |
        //                      |
        //                   [n5_1]
        //                     | |
        //                     | |
        //                   [n5_2]---n[6_1]
        //
        topology_multihomed = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_1); pathls.add(l3_3_2); pathls.add(l3_5); pathls.add(l5_5_2); pathls.add(l5_6);
        // p111---[p311-p312]---[p322-p323]---[p332-p331]---[p511-p513]---[p523-p521]---p611
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_multihomed);
        HomeDomainReservation rsv = new HomeDomainReservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(p6_1_1);
        PathInfo userExcl = new PathInfo();
        userExcl.addLink("l3_3_3");
        userExcl.addLink("l5_5_1");
        rsv.setUserExclude(userExcl);

        Iterator<Path> pathsres = pf.findInterdomainPaths(rsv, null);
        Path respath1 = pathsres.next();    // Get 1st path of result

        System.out.println("Expected:"+path1.toString());
        System.out.println("Result:"+respath1.toString());
        //assertEquals(respath1, path1);

        List<Link> expectedLinks = path1.getLinks();
        List<Link> resLinks = respath1.getLinks();
        System.out.println("Expected:"+expectedLinks.toString());
        System.out.println("Result:"+resLinks.toString());
        assertEquals(expectedLinks, resLinks);
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#getNeighbours(net.geant.autobahn.network.AdminDomain)}.
     */
    @Ignore
    public void testGetNeighbours() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImpl#findInterdomainPathsTest(java.util.List, java.lang.String, java.lang.String, net.geant.autobahn.interdomain.pathfinder.Topology)}.
     */
    @Ignore
    public void testFindInterdomainPathsTest() {
        fail("Not yet implemented");
    }

}
