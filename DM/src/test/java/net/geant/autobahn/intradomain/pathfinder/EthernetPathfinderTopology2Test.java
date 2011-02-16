package net.geant.autobahn.intradomain.pathfinder;

import static net.geant.autobahn.intradomain.topologies.EthTopology1._1Gb;

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
import net.geant.autobahn.intradomain.topologies.EthTopology2;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetPathfinderTopology2Test {

    private Map<String, Node> nodes = null;
    private Map<String, GenericLink> glinks = null;
    private IntradomainPathfinder pf = null;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        EthTopology2 topoSrc = new EthTopology2();
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
        IntradomainTopology topo = builder.getIntradomainTopology();
        
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
        
        TestCase.assertEquals(4, paths.size());
        for(IntradomainPath path : paths) {
            System.out.println(path);
        }
    }

    @Test
    public void testFindingAllPathsBetweenNodes4_3() {
        System.out.println("---testFindingAllPathsBetweenNodes4_3");
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.4"), 
                nodes.get("Node1.3"), null, Integer.MAX_VALUE);
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
        }
        TestCase.assertEquals(3, paths.size());
    }
    
    @Test
    public void testFindingAllPathsBetweenGivenLinks() {
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink( glinks.get("p1.4-cli-port4"), null);

    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);
        
        TestCase.assertEquals(3, paths.size());
        
        for(IntradomainPath path : paths) {
            System.out.println(path);
            // this link has vlan range that is not overlapping with others
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.8-p1.13")));
            
            // check multihoming
            TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.2-cli-port2")));
        }
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksWithConstraints() {
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(160, 160);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.4-cli-port4"), pcon);

        
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNotNull(path);
        TestCase.assertNotNull(rcon.intersect(path.getMergedConstraints()
                .getRangeConstraint(ConstraintsNames.VLANS)));
        TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.6-p1.10")));
        TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.8-p1.13")));
    }
}
