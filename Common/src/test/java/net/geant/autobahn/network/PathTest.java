/*
 * PathTest.java
 *
 * 16 July 2010
 */
package net.geant.autobahn.network;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class PathTest {

    // Topology simulates a testbed with two domains
    AdminDomain adclient1,adclient2,ad817,ad818;
    ProvisioningDomain pdclient1,pdclient2,pd817,pd818;
    Node n10_0_2, n10_0_1, n10_0_0, n20_0_1, n20_0_0, n20_0_2;
    Port p10_32_4,p10_32_3,p10_32_1,p10_32_2,p10_32_0,p20_32_3,p20_32_1,p20_32_2,p20_32_0,p20_32_4;
    Link l10_64_1,l10_64_0,l20_64_2,l20_64_0,l20_64_1;
    
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
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.geant.autobahn.network.Path#containedInList_LbL(net.geant.autobahn.network.Path, java.util.List)}.
     */
    @Test
    public void testContainedInList_LbL_DiffObject() {
        List<Link> ls1;
        ls1 = new ArrayList<Link>();
        ls1.add(l20_64_1); ls1.add(l20_64_2); ls1.add(l10_64_0); ls1.add(l20_64_0);

        List<Link> ls2;
        ls2 = new ArrayList<Link>();
        ls2.add(l20_64_1); ls2.add(l20_64_2); ls2.add(l10_64_0); ls2.add(l20_64_0);

        List<Link> ls3;
        ls3 = new ArrayList<Link>();
        ls3.add(l10_64_1); ls3.add(l20_64_1); ls3.add(l10_64_0); ls3.add(l20_64_0);

        List<Link> ls4;
        ls4 = new ArrayList<Link>();
        ls4.add(l20_64_2); ls4.add(l10_64_0); ls4.add(l20_64_0);

        Path p1 = new Path();
        p1.setLinks(ls1);
        Path p2 = new Path();
        p2.setLinks(ls2);
        Path p3 = new Path();
        p3.setLinks(ls3);
        Path p4 = new Path();
        p4.setLinks(ls4);
        
        List<Path> pList = new ArrayList<Path>();
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        
        TestCase.assertEquals(true, Path.containedInList_LbL(p1, pList));
    }

    /**
     * Test method for {@link net.geant.autobahn.network.Path#containedInList_LbL(net.geant.autobahn.network.Path, java.util.List)}.
     */
    @Test
    public void testContainedInList_LbL_False() {
        List<Link> ls1;
        ls1 = new ArrayList<Link>();
        ls1.add(l20_64_1); ls1.add(l20_64_2); ls1.add(l10_64_0);

        List<Link> ls2;
        ls2 = new ArrayList<Link>();
        ls2.add(l20_64_1); ls2.add(l20_64_2); ls2.add(l10_64_0); ls2.add(l20_64_0);

        List<Link> ls3;
        ls3 = new ArrayList<Link>();
        ls3.add(l10_64_1); ls3.add(l20_64_1); ls3.add(l10_64_0); ls3.add(l20_64_0);

        List<Link> ls4;
        ls4 = new ArrayList<Link>();
        ls4.add(l20_64_2); ls4.add(l10_64_0); ls4.add(l20_64_0);

        Path p1 = new Path();
        p1.setLinks(ls1);
        Path p2 = new Path();
        p2.setLinks(ls2);
        Path p3 = new Path();
        p3.setLinks(ls3);
        Path p4 = new Path();
        p4.setLinks(ls4);
        
        List<Path> pList = new ArrayList<Path>();
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        
        TestCase.assertEquals(false, Path.containedInList_LbL(p1, pList));
    }

    /**
     * Test method for {@link net.geant.autobahn.network.Path#containedInList_LbL(net.geant.autobahn.network.Path, java.util.List)}.
     */
    @Test
    public void testContainedInList_LbL_SameObject() {
        List<Link> ls1;
        ls1 = new ArrayList<Link>();
        ls1.add(l20_64_1); ls1.add(l20_64_2); ls1.add(l10_64_0);

        List<Link> ls2;
        ls2 = new ArrayList<Link>();
        ls2.add(l20_64_1); ls2.add(l20_64_2); ls2.add(l10_64_0); ls2.add(l20_64_0);

        List<Link> ls3;
        ls3 = new ArrayList<Link>();
        ls3.add(l10_64_1); ls3.add(l20_64_1); ls3.add(l10_64_0); ls3.add(l20_64_0);

        Path p1 = new Path();
        p1.setLinks(ls1);
        Path p2 = new Path();
        p2.setLinks(ls2);
        Path p3 = new Path();
        p3.setLinks(ls3);
        
        List<Path> pList = new ArrayList<Path>();
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        
        TestCase.assertEquals(true, Path.containedInList_LbL(p1, pList));
    }

    /**
     * Test method for {@link net.geant.autobahn.network.Path#checkIfEqual_LbL(net.geant.autobahn.network.Path, net.geant.autobahn.network.Path)}.
     */
    @Test
    public void testCheckIfEqual_LbL() {
        List<Link> ls1;
        ls1 = new ArrayList<Link>();
        ls1.add(l20_64_1); ls1.add(l20_64_2); ls1.add(l10_64_0); ls1.add(l20_64_0);

        List<Link> ls2;
        ls2 = new ArrayList<Link>();
        ls2.add(l20_64_1); ls2.add(l20_64_2); ls2.add(l10_64_0); ls2.add(l20_64_0);

        Path p1 = new Path();
        p1.setLinks(ls1);
        Path p2 = new Path();
        p2.setLinks(ls2);
        
        TestCase.assertEquals(true, Path.checkIfEqual_LbL(p1, p2));
    }

    /**
     * Test method for {@link net.geant.autobahn.network.Path#checkIfEqual_LbL(net.geant.autobahn.network.Path, net.geant.autobahn.network.Path)}.
     */
    @Test
    public void testCheckIfEqual_LbL_False() {
        List<Link> ls1;
        ls1 = new ArrayList<Link>();
        ls1.add(l20_64_1); ls1.add(l20_64_2); ls1.add(l10_64_0);

        List<Link> ls2;
        ls2 = new ArrayList<Link>();
        ls2.add(l20_64_1); ls2.add(l20_64_2); ls2.add(l10_64_0); ls2.add(l20_64_0);

        Path p1 = new Path();
        p1.setLinks(ls1);
        Path p2 = new Path();
        p2.setLinks(ls2);
        
        TestCase.assertEquals(false, Path.checkIfEqual_LbL(p1, p2));
    }

}
