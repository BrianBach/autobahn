package net.geant.autobahn.intradomain.pathfinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.EthernetIntradomainPathfinder;
import net.geant.autobahn.intradomain.topologies.EthTopology3;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.geant.autobahn.intradomain.topologies.EthTopology3._100Mb;
import static net.geant.autobahn.intradomain.topologies.EthTopology3._10Gb;
import static net.geant.autobahn.intradomain.topologies.EthTopology3._1Gb;
import static net.geant.autobahn.intradomain.topologies.EthTopology3._500Mb;

/**
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class EthernetPathfinderTopology3Test {

    private Map<String, Node> nodes = null;
    private Map<String, GenericLink> glinks = null;
    private IntradomainPathfinder pf = null;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        EthTopology3 topoSrc = new EthTopology3();
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
        IntradomainTopology topo = builder.getTopology();
        
        nodes = new HashMap<String, Node>();
        for(Node n : topo.getNodes()) {
            nodes.put(n.getName(), n);
        }

        glinks = new HashMap<String, GenericLink>();
        for(GenericLink glink : topo.getGenericLinks()) {
            glinks.put(glink.toString(), glink);
        }

        // Instantiate the pathfinder
        pf = new EthernetIntradomainPathfinder(topo);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.println(" - --");
    }

    @Test
    public void testFindingAllPathsBetweenNodes1_3() {
        System.out.println("---testFindingAllPathsBetweenNodes1_3");

        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.3"), null, Integer.MAX_VALUE);
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
        }
        TestCase.assertEquals(4, paths.size());
    }
    
    @Test
    public void testFindingAllPathsBetweenNodes4_8() {
        System.out.println("---testFindingAllPathsBetweenNodes4_8");

        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.4"), 
                nodes.get("Node1.8"), null, Integer.MAX_VALUE);
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
        }
        TestCase.assertEquals(18, paths.size());
    }
    
    @Test
    public void testFindingAllPathsBetweenGivenLinksCli1_Cli3() {
        System.out.println("---testFindingAllPathsBetweenGivenLinksCli1_Cli3");
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p1_c1-cli-port1"), 
                glinks.get("p3_c3-cli-port3"), _100Mb, null, Integer.MAX_VALUE, 0);
        
        TestCase.assertEquals(3, paths.size());
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
            // this link has vlan range that is not overlapping with others
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p1_3-p3_1")));
            
            // check multihoming
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p1_c1_1-cli-port1_1")));
        }
    }
    
    @Test
    public void testFindingAllPathsBetweenGivenLinksCli1_Cli3NotEnoughCapacity() {
        System.out.println("---testFindingAllPathsBetweenGivenLinksCli1_Cli3NotEnoughCapacity");
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p1_c1-cli-port1"), 
                glinks.get("p3_c3-cli-port3"), _1Gb, null, Integer.MAX_VALUE, 0);
        
        TestCase.assertEquals(0, paths.size());
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksCli1_Cli3WithConstraints() {
        System.out.println("---testFindingPathBetweenGivenLinksCli1_Cli3WithConstraints");
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(160, 160);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
        IntradomainPath path = pf.findPath(glinks.get("p1_c1-cli-port1"), 
                glinks.get("p3_c3-cli-port3"), _100Mb, pcon, null, 0);

        TestCase.assertNotNull(path);
        TestCase.assertNotNull(rcon.intersect(path.getMergedConstraints()
                .getRangeConstraint(ConstraintsNames.VLANS)));
        TestCase.assertFalse(path.getLinks().contains(glinks.get("p1_2_1-p2_1_1")));
        TestCase.assertFalse(path.getLinks().contains(glinks.get("p1_3-p3_1")));
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksCli1_Cli3WithConstraintsNotEnoughCapacity() {
        System.out.println("---testFindingPathBetweenGivenLinksCli1_Cli3WithConstraintsNotEnoughCapacity");
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(160, 160);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
        IntradomainPath path = pf.findPath(glinks.get("p1_c1-cli-port1"), 
                glinks.get("p3_c3-cli-port3"), _1Gb, pcon, null, 0);

        TestCase.assertNull(path);
    }

    
    @Test
    public void testFindingAllPathsBetweenGivenLinksCli2_Cli4() {
        System.out.println("---testFindingAllPathsBetweenGivenLinksCli2_Cli4");
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p2_c2-cli-port2"), 
                glinks.get("p8_c4-cli-port4"), _1Gb, null, Integer.MAX_VALUE, 0);
        
        TestCase.assertEquals(3, paths.size());
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
            // this link has vlan range that is not overlapping with others
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p1_3-p3_1")));
            
            // this link has only 100Mb capacity
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p5_6_1-p6_5_1")));
        }
    }
    
    @Test
    public void testFindingAllPathsBetweenGivenLinksCli2_Cli4NotEnoughCapacity() {
        System.out.println("---testFindingAllPathsBetweenGivenLinksCli2_Cli4NotEnoughCapacity");
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p2_c2-cli-port2"), 
                glinks.get("p8_c4-cli-port4"), _10Gb, null, Integer.MAX_VALUE, 0);
        
        TestCase.assertEquals(0, paths.size());
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksCli2_Cli4WithConstraints() {
        System.out.println("---testFindingPathBetweenGivenLinksCli2_Cli4WithConstraints");
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(160, 160);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
        IntradomainPath path = pf.findPath(glinks.get("p2_c2-cli-port2"), 
                glinks.get("p8_c4-cli-port4"), _100Mb, pcon, null, 0);

        TestCase.assertNotNull(path);
        TestCase.assertNotNull(rcon.intersect(path.getMergedConstraints()
                .getRangeConstraint(ConstraintsNames.VLANS)));
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksCli2_Cli4WithConstraintsNotEnoughCapacity() {
        System.out.println("---testFindingPathBetweenGivenLinksCli2_Cli4WithConstraintsNotEnoughCapacity");
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(160, 160);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
        IntradomainPath path = pf.findPath(glinks.get("p2_c2-cli-port2"), 
                glinks.get("p8_c4-cli-port4"), _10Gb, pcon, null, 0);

        TestCase.assertNull(path);
    }
}
