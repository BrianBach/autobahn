package net.geant.autobahn.intradomain.pathfinder;


import org.junit.After;
import org.junit.Before;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.SdhIntradomainPathfinder;
import net.geant.autobahn.intradomain.topologies.SdhTopology1;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.geant.autobahn.intradomain.topologies.SdhTopology1._10Gb;
import static net.geant.autobahn.intradomain.topologies.SdhTopology1._1Gb;


public class SdhPathfinderTest_Topology1 {

    private Map<String, Node> nodes = null;
    private Map<String, GenericLink> glinks = null;
    private IntradomainPathfinder pf = null;
    
    /**
     * @throws java.lang.Exception
     */
    
    @Before
    public void setUp() throws Exception {
        SdhTopology1 topoSrc = new SdhTopology1();
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
IntradomainTopology topo = builder.getTopology();
        
        nodes = new HashMap<String, Node>();
        topo.getSdhDevices();
        for(Node n : topo.getNodes()) {
            
            nodes.put(n.getName(), n);
        }    
        
        glinks = new HashMap<String, GenericLink>();
        for(GenericLink glink : topo.getGenericLinks()) {
            glinks.put(glink.toString(), glink);
        }
        
        // Instantiate the pathfinder
        pf = new SdhIntradomainPathfinder(topo);
        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testFindingAllPathsBetweenEdgeNodes() {
        
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.2"), null, Integer.MAX_VALUE);
        
        TestCase.assertEquals(3, paths.size());
    }
    
    @Test
    public void testFindingLimitedNumberOfPathsBetweenEdgeNodes() {
        
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.2"), null, 1);
        
        TestCase.assertEquals(1, paths.size());
    }
    
    @Test
    public void testFindingAllPathsBetweenEdgeNodesExcludingALink() {
        
        Set<GenericLink> excluded = new HashSet<GenericLink>();
        GenericLink link = glinks.get("p1.7-p1.10");
        excluded.add(link);
        
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.2"), excluded, Integer.MAX_VALUE);
        
        TestCase.assertEquals(1, paths.size());
        
        for(IntradomainPath path : paths) {
            TestCase.assertFalse(path.getLinks().contains(link));   
        }
    }
    
    public void testFindingAllPathsBetweenEdgeNodesExcludingLinks() {
        
        Set<GenericLink> excluded = new HashSet<GenericLink>();
        excluded.add(glinks.get("p1.7-p1.10"));
        excluded.add(glinks.get("p1.4-p1.7"));
        
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.2"), excluded, Integer.MAX_VALUE);
        
        TestCase.assertEquals(0, paths.size());
        
        for(IntradomainPath path : paths) {
            TestCase.assertFalse(path.getLinks().contains(excluded));   
        }
    }

    @Test
    public void testFindingPathsBetweenInternalNodes() {
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.3"), 
                nodes.get("Node1.4"), null, Integer.MAX_VALUE);

        TestCase.assertEquals(3, paths.size());
    }
    
    @Test
    public void testFindingPathsToUnattachedNode() {
        List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
                nodes.get("Node1.5"), null, Integer.MAX_VALUE);

        TestCase.assertEquals(0, paths.size());
    }
    
    @Test
    public void testFindingPathsBetweenGivenLinks() {
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p1.1-cli-port1"), 
                glinks.get("p1.2-cli-port2"), _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(3, paths.size());
    }
    
    @Test
    public void testFindingPathsBetweenGivenLinksOverGivenCapacity() {
        List<IntradomainPath> paths = pf.findPaths(glinks.get("p1.1-cli-port1"), 
                glinks.get("p1.2-cli-port2"), _10Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(0, paths.size());
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksWithConstraints() {
        PathConstraints pcon = new PathConstraints();
        MinValueConstraint rcon = new MinValueConstraint(170.0);
        pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, rcon);
        
        IntradomainPath path = pf.findPath(glinks.get("p1.1-cli-port1"), 
                glinks.get("p1.2-cli-port2"), _1Gb, pcon, null, 0);

        TestCase.assertNotNull(path);
        
        TestCase.assertNotNull(rcon.intersect(path.getMergedConstraints()
                .getMinValueConstraint(ConstraintsNames.TIMESLOTS)));
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksWithWrongConstraints() {
        PathConstraints pcon = new PathConstraints();
        pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, new MinValueConstraint(1024.0));
        
        IntradomainPath path = pf.findPath(glinks.get("p1.1-cli-port1"), 
                glinks.get("p1.2-cli-port2"), _1Gb, pcon, null, 0);
        
        if (path != null)
            path = null;

        TestCase.assertNull(path);
    }

}