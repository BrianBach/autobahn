package net.geant.autobahn.intradomain.pathfinder;


import static net.geant.autobahn.intradomain.topologies.SdhTopology1._10Gb;
import static net.geant.autobahn.intradomain.topologies.SdhTopology1._1Gb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.sdh.SdhIntradomainPathfinder;
import net.geant.autobahn.intradomain.topologies.SdhTopology2;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SdhPathfinderTopologyVlanTranslationTest {

    private Map<String, Node> nodes = null;
    private Map<String, GenericLink> glinks = null;
    private IntradomainPathfinder pf = null;
    private IntraTopologyBuilder builder = null;
    
    /**
     * @throws java.lang.Exception
     */
    
    @Before
    public void setUp() throws Exception {
        SdhTopology2 topoSrc = new SdhTopology2();
        builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
        IntradomainTopology topo = builder.getIntradomainTopology();
        
        nodes = new HashMap<String, Node>();
        topo.getSdhDevices();
        for(Node n : topo.getNodes()) {
        	n.setVlanTranslationSupport(true);
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
    public void testFindingPathsBetweenGivenLinks() {
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(3, paths.size());
        
        RangeConstraint rcon1 = paths.get(0).getIngressConstraints().getRangeConstraint(ConstraintsNames.VLANS);
        RangeConstraint rcon2 = paths.get(0).getEgressConstraints().getRangeConstraint(ConstraintsNames.VLANS);
        
        TestCase.assertNull(rcon1.intersect(rcon2));
    }

    @Test
    public void testFindingPathsBetweenGivenLinksTranslationOff() {
    	builder.switchVlanTranslationSupport(false, "all");
    	
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(0, paths.size());
    }    
    
    @Test
    public void testFindingPathBetweenGivenLinksWithConstraints() {
        PathConstraints pcon = new PathConstraints();
        MinValueConstraint rcon = new MinValueConstraint(170.0);
        pcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, rcon);
        
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);

        
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNotNull(path);
        
        TestCase.assertNotNull(rcon.intersect(path.getIngressConstraints()
                .getMinValueConstraint(ConstraintsNames.TIMESLOTS)));
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksWithWrongConstraints() {
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("700"));
        
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
        
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);
        
        TestCase.assertNull(path);
    }
    
    @Test
    public void testFindingPathBetweenGivenLinksWithGivenVlans() {
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("99"));

        PathConstraints pcon2 = new PathConstraints();
        pcon2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint("209"));
        
        IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon2);
        
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);
        
        TestCase.assertNotNull(path);
        
        TestCase.assertEquals(99, path.getIngressConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
        TestCase.assertEquals(209, path.getEgressConstraints().getRangeConstraint(ConstraintsNames.VLANS).getFirstValue());
    }    

}