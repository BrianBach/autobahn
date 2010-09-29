/**
 * 
 */
package net.geant.autobahn.interdomain.pathfinder;

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

import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinderImplDFS;
import net.geant.autobahn.interdomain.pathfinder.Topology;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.reservation.Reservation;

/**
 * @author kostas
 *
 */
public class InterdomainPathfinderImplDFS_TwoDomsTest {

    // Topology simulates a testbed with two domains
    Topology topology_straight,topology_multihomed;
    AdminDomain adclient1,adclient2,ad817,ad818;
    ProvisioningDomain pdclient1,pdclient2,pd817,pd818;
    Node n10_0_2, n10_0_1, n10_0_0, n20_0_1, n20_0_0, n20_0_2;
    Port p10_32_4,p10_32_3,p10_32_1,p10_32_2,p10_32_0,p20_32_3,p20_32_1,p20_32_2,p20_32_0,p20_32_4;
    Link l10_64_1,l10_64_0,l20_64_2,l20_64_0,l20_64_1;
    List<AdminDomain> ads;
    List<Node> ns;
    List<Link> ls;
    
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
        adclient1 = new AdminDomain("1", true);   //client
        pdclient1 = new ProvisioningDomain("1", "PIP", adclient1);
        adclient2 = new AdminDomain("2", true);   //client
        pdclient2 = new ProvisioningDomain("2", "PIP", adclient2);
        ad817 = new AdminDomain("3", false);
        pd817 = new ProvisioningDomain("3", "PIP", ad817);
        ad818 = new AdminDomain("4", false);
        pd818 = new ProvisioningDomain("4", "PIP", ad818);

        n10_0_2 = new Node("PSC", "n10_0_2", "n10_0_2", pdclient1);
        n20_0_2 = new Node("PSC", "n20_0_2", "n20_0_2", pdclient2);
        n20_0_1 = new Node("PSC", "n20_0_1", "n20_0_1", pd817);
        n20_0_0 = new Node("PSC", "n20_0_0", "n20_0_0", pd817);
        n10_0_1 = new Node("PSC", "n10_0_1", "n10_0_1", pd818);
        n10_0_0 = new Node("PSC", "n10_0_0", "n10_0_0", pd818);
        
        p10_32_4 = new Port("p10_32_4", "p10_32_4", "IP", false, n10_0_2);  // client port
        p20_32_4 = new Port("p20_32_4", "p20_32_4", "IP", false, n20_0_2);  // client port
        p10_32_3 = new Port("p10_32_3", "p10_32_3", "IP", false, n10_0_1);
        p10_32_1 = new Port("p10_32_1", "p10_32_1", "IP", false, n10_0_1);
        p10_32_0 = new Port("p10_32_0", "p10_32_0", "IP", false, n10_0_0);
        p10_32_2 = new Port("p10_32_2", "p10_32_2", "IP", false, n10_0_0);
        p20_32_0 = new Port("p20_32_0", "p20_32_0", "IP", false, n20_0_0);
        p20_32_2 = new Port("p20_32_2", "p20_32_2", "IP", false, n20_0_0);
        p20_32_3 = new Port("p20_32_3", "p20_32_3", "IP", false, n20_0_1);
        p20_32_1 = new Port("p20_32_1", "p20_32_1", "IP", false, n20_0_1);
        
        l10_64_1 = Link.createInterDomainLink(p10_32_3, p10_32_4, 1000000000);
        l10_64_1.setBodID("l10_64_1");
        l20_64_1 = Link.createInterDomainLink(p20_32_3, p20_32_4, 1000000000);
        l20_64_1.setBodID("l20_64_1");
        l20_64_2 = Link.createInterDomainLink(p10_32_2, p20_32_2, 1000000000);
        l20_64_2.setBodID("l20_64_2");
        l10_64_0 = Link.createVirtualLink(p10_32_0, p10_32_1, 1000000000);
        l10_64_0.setBodID("l10_64_0");
        l20_64_0 = Link.createVirtualLink(p20_32_0, p20_32_1, 1000000000);
        l20_64_0.setBodID("l20_64_0");
        
        ads = new ArrayList<AdminDomain>();
        ns = new ArrayList<Node>();
        ads.add(adclient1); ads.add(adclient2); ads.add(ad817); ads.add(ad818);
        ns.add(n10_0_2); ns.add(n20_0_2); ns.add(n20_0_1); ns.add(n20_0_0); ns.add(n10_0_1); ns.add(n10_0_0);

        ls = new ArrayList<Link>();
        ls.add(l10_64_1); ls.add(l20_64_1); ls.add(l20_64_2); ls.add(l10_64_0); ls.add(l20_64_0);
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
     * Tests IPF on a straight topology
     */
    @Test
    public void testFindInterdomainPaths_straight() {
        System.out.println("---------Straight");
        topology_straight = new TopologyTest(ads, ls, ns);

        List<Link> pathls = new ArrayList<Link>();
        pathls.add(l20_64_1); pathls.add(l20_64_0); pathls.add(l20_64_2); pathls.add(l10_64_0); pathls.add(l10_64_1);
        Path path1 = new Path();
        path1.setLinks(pathls);

        InterdomainPathfinderImplDFS pf = new InterdomainPathfinderImplDFS(topology_straight);
        Reservation rsv = new Reservation();
        rsv.setStartPort(p20_32_4);
        rsv.setEndPort(p10_32_4);

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
