package net.geant.autobahn.interdomain.pathfinder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.useraccesspoint.PathInfo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InterdomainPathfinderImplDFS_Idcp {

    Topology topology_straight,topology_multihomed;
    AdminDomain ad1,ad2,ad3,ad4,ad5;
    ProvisioningDomain pd1,pd2,pd3,pd4,pd5;
    Node n1_1,n2_1,n3_1,n3_2,n3_3,n4_1,n5_1;
    Node n5_dum1,n5_dum2,n5_dum3;
    Port p1_1_1;
    Port p2_1_1;
    Port p3_1_1,p3_1_2,p3_1_3,p3_1_4,p3_2_1,p3_2_2,p3_2_3,p3_3_1,p3_3_2,p3_3_3;
    Port p4_1_1;
    Port p5_1_1,p5_dum1,p5_dum2,p5_dum3;
    Port pIdcp1, pIdcp2, pIdcp3;
    Port p6_1_1;
    Link l1_3,l2_3,l3_4,l3_5;
    Link l3_3_1,l3_3_2,l3_3_3;
    Link lIdcp_dum1,lIdcp_dum2,lIdcp_dum3;
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
        ad4 = new AdminDomain("4", true);
        pd4 = new ProvisioningDomain("4", "PIP", ad4);
        ad5 = new AdminDomain("5", true);   // idcp
        ad5.setIdcpServer("https://some_idcp_server.com");
        pd5 = new ProvisioningDomain("5", "PIP", ad5);

        n1_1 = new Node("PSC", "n1_1", "n1_1", pd1);
        n2_1 = new Node("PSC", "n2_1", "n2_1", pd2);
        n3_1 = new Node("PSC", "n3_1", "n3_1", pd3);
        n3_2 = new Node("PSC", "n3_2", "n3_2", pd3);
        n3_3 = new Node("PSC", "n3_3", "n3_3", pd3);
        n4_1 = new Node("PSC", "n4_1", "n4_1", pd4);
        n5_1 = new Node("PSC", "n5_1", "n5_1", pd5);
        n5_dum1 = new Node("PSC", "n5_dum1", "n5_dum1", pd5);
        n5_dum2 = new Node("PSC", "n5_dum2", "n5_dum2", pd5);
        n5_dum3 = new Node("PSC", "n5_dum3", "n5_dum3", pd5);
        
        p1_1_1 = new Port("p1_1_1", "p1_1_1", "IP", false, n1_1);
        p2_1_1 = new Port("p2_1_1", "p2_1_1", "IP", false, n2_1);
        p3_1_1 = new Port("p3_1_1", "p3_1_1", "IP", false, n3_1);
        p3_1_2 = new Port("p3_1_2", "p3_1_2", "IP", false, n3_1);
        p3_1_3 = new Port("p3_1_3", "p3_1_3", "IP", false, n3_1);
        p3_1_4 = new Port("p3_1_4", "p3_1_4", "IP", false, n3_1);
        p3_2_1 = new Port("p3_2_1", "p3_2_1", "IP", false, n3_2);
        p3_2_2 = new Port("p3_2_2", "p3_2_2", "IP", false, n3_2);
        p3_2_3 = new Port("p3_2_3", "p3_2_3", "IP", false, n3_2);
        p3_3_1 = new Port("p3_3_1", "p3_3_1", "IP", false, n3_3);
        p3_3_2 = new Port("p3_3_2", "p3_3_2", "IP", false, n3_3);
        p3_3_3 = new Port("p3_3_3", "p3_3_3", "IP", false, n3_3);
        p4_1_1 = new Port("p4_1_1", "p4_1_1", "IP", false, n4_1);
        p5_1_1 = new Port("p5_1_1", "p5_1_1", "IP", false, n5_1);
        p5_dum1 = new Port("p5_dum1", "p5_dum1", "IP", false, n5_1);
        p5_dum2 = new Port("p5_dum2", "p5_dum2", "IP", false, n5_1);
        p5_dum3 = new Port("p5_dum3", "p5_dum3", "IP", false, n5_1);
        pIdcp1 = new Port("pIdcp1", "pIdcp1", "IP", false, n5_dum1);
        pIdcp2 = new Port("pIdcp2", "pIdcp2", "IP", false, n5_dum2);
        pIdcp3 = new Port("pIdcp3", "pIdcp3", "IP", false, n5_dum3);
        
        l1_3 = Link.createInterDomainLink(p1_1_1, p3_1_1, 1000000000);
        l1_3.setBodID("l1_3");
        l2_3 = Link.createInterDomainLink(p2_1_1, p3_2_1, 1000000000);
        l2_3.setBodID("l2_3");
        l3_4 = Link.createInterDomainLink(p3_1_3, p4_1_1, 1000000000);
        l3_4.setBodID("l3_4");
        l3_5 = Link.createInterDomainLink(p3_3_1, p5_1_1, 1000000000);
        l3_5.setBodID("l3_5");
        l3_3_1 = Link.createVirtualLink(p3_1_2, p3_2_2, 1000000000);
        l3_3_1.setBodID("l3_3_1");
        l3_3_2 = Link.createVirtualLink(p3_2_3, p3_3_2, 1000000000);
        l3_3_2.setBodID("l3_3_2");
        l3_3_3 = Link.createVirtualLink(p3_1_4, p3_3_3, 1000000000);
        l3_3_3.setBodID("l3_3_3");

        lIdcp_dum1 = Link.createVirtualLink(p5_dum1, pIdcp1, 1000000000);
        lIdcp_dum1.setBodID("lIdcp_dum1");
        lIdcp_dum2 = Link.createVirtualLink(p5_dum2, pIdcp2, 1000000000);
        lIdcp_dum2.setBodID("lIdcp_dum2");
        lIdcp_dum3 = Link.createVirtualLink(p5_dum3, pIdcp3, 1000000000);
        lIdcp_dum3.setBodID("lIdcp_dum3");

        ads = new ArrayList<AdminDomain>();
        ns = new ArrayList<Node>();
        ads.add(ad1); ads.add(ad2); ads.add(ad3); ads.add(ad4); ads.add(ad5);
        ns.add(n1_1); ns.add(n2_1); ns.add(n3_1); ns.add(n3_2); ns.add(n3_3);
        ns.add(n4_1); ns.add(n5_1);
        ns.add(n5_dum1); ns.add(n5_dum2); ns.add(n5_dum3);
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
     * End port is an Idcp port
     */
    @Test
    public void testFindInterdomainPaths_straightIncl_IdcpPort() {
        System.out.println("---------Straight with User Incl, Idcp Port 3");
        List<Link> ls = new ArrayList<Link>();
        ls.add(l1_3); ls.add(l2_3); ls.add(l3_4); ls.add(l3_5);
        ls.add(l3_3_1); ls.add(l3_3_2); ls.add(l3_3_3);
        ls.add(lIdcp_dum1); ls.add(lIdcp_dum2); ls.add(lIdcp_dum3);
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
        //                   [n5_1] (IDCP)
        //                   /  |  \
        //                  /   |   \
        //                 /    |    \
        //                /     |     \
        //        [n5_dum1] [n5_dum2] [n5_dum3]
        //
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l1_3); pathls.add(l3_3_3); pathls.add(l3_5); pathls.add(lIdcp_dum3);
        // p111---[p311-p314]---[p333-p331]---[p511-p5_dum3]---[pIdcp3]
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p1_1_1);
        rsv.setEndPort(pIdcp3);
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

}
