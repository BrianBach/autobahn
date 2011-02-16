/**
 * 
 */
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
import net.geant.autobahn.utils.EthTopology4;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jacek
 *
 */
public class EthernetPathfinderVlanTranslationTest {

    private Map<String, Node> nodes = null;
    private Map<String, GenericLink> glinks = null;
    private IntradomainPathfinder pf = null;
	
    private IntraTopologyBuilder builder = null;
    
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        EthTopology4 topoSrc = new EthTopology4();
        builder = new IntraTopologyBuilder(false);
        topoSrc.domain1(builder);
        
        IntradomainTopology topo = builder.getIntradomainTopology();
        
        nodes = new HashMap<String, Node>();
        for(Node n : topo.getNodes()) {
        	n.setVlanTranslationSupport(false);
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
	}

	@Test
	public void findingPathsTestTranslationOff() {
		System.out.println(" -- 1 --");
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(1, paths.size());
	}

	@Test
	public void findingPathsTestTranslationOffRestrictionsOn() {
		System.out.println(" -- 1 --");
    	
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(50, 50));

    	IntradomainPath pSkel = new IntradomainPath();

    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(0, paths.size());
	}
	
	@Test
	public void simplePathVlanTranslationAllOn() {
		System.out.println(" -- 2 --");
		builder.switchVlanTranslationSupport(true, "all");
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(3, paths.size());
	}

	@Test
	public void simplePathVlanTranslationOff1Node() {
		System.out.println(" -- 3 --");
		builder.switchVlanTranslationSupport(true, "all");
		builder.switchVlanTranslationSupport(false, "Node1.2");
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(2, paths.size());
	}
	
	@Test
	public void simplePathVlanTranslationEdgesOn() {
		builder.switchVlanTranslationSupport(true, "Node1.1", "Node1.3");
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, Integer.MAX_VALUE, 0);

        TestCase.assertEquals(1, paths.size());
	}
	
	@Test
	public void simplePathVlanTranslationEdgesOnUserVlan() {
		builder.switchVlanTranslationSupport(true, "Node1.1", "Node1.3");
		
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(15, 15);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertTrue(path.getLinks().contains(glinks.get("p1.6-p1.8")));
        TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.4-p1.11")));
        TestCase.assertNotNull(path);
	}
	
	@Test
	public void simplePathVlanTranslationEdgesOnUserWrongVlan() {
		builder.switchVlanTranslationSupport(true, "Node1.1", "Node1.3");
		
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(51, 51);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), null);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNull(path);
	}
	
	@Test
	public void simplePathVlanTranslationEdgesOnUserVlan2() {
		builder.switchVlanTranslationSupport(true, "all");
		builder.switchVlanTranslationSupport(false, "Node1.1");
		
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(50, 50);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNotNull(path);
	}
	
	@Test
	public void simplePathVlanTranslationEdgesOnUserVlanOppositeDirection() {
		System.out.println("-- XX -");
		
		builder.switchVlanTranslationSupport(false, "all");
		builder.switchVlanTranslationSupport(true, "Node1.3");
		
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(50, 50);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
		
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), null);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNotNull(path);
        
        System.out.println(path);
	}
	
	@Test
	public void simplePathVlanTranslationOffAgreedConstraints() {
		System.out.println("-- XX - 15 -");
		
		builder.switchVlanTranslationSupport(false, "all");
		
        PathConstraints pcon = new PathConstraints();
        RangeConstraint rcon = new RangeConstraint(15, 15);
        pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
        
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);
        
        TestCase.assertNotNull(path);
        TestCase.assertEquals("15-15", getVlan(path.getConstraints(glinks.get("p1.3-p1.5"))));
	}
	
	@Test
	public void simplePathVlanTranslationOnAgreedConstraints1() {
		System.out.println("-- XX -");
		
		builder.switchVlanTranslationSupport(false, "all");
		builder.switchVlanTranslationSupport(true, "Node1.2", "Node1.3");
		
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(50, 50));

        PathConstraints pcon2 = new PathConstraints();
        pcon2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(15, 15));

        
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon2);
    	
        IntradomainPath path = pf.findPath(pSkel, _1Gb, null, 0);

        TestCase.assertNotNull(path);
        
        TestCase.assertEquals("50-50", getVlan(path.getConstraints(glinks.get("p1.2-cli-port2"))));
        TestCase.assertEquals("15-15", getVlan(path.getConstraints(glinks.get("p1.6-p1.8"))));
        TestCase.assertEquals("15-15", getVlan(path.getConstraints(glinks.get("p1.3-p1.5"))));
        TestCase.assertEquals("15-15", getVlan(path.getConstraints(glinks.get("p1.1-cli-port1"))));
	}
	
	@Test
	public void simpleFindPathsVlanTranslation2() {
		System.out.println("-- XXX -");
		
		builder.switchVlanTranslationSupport(false, "all");
		builder.switchVlanTranslationSupport(true, "Node1.2", "Node1.3");
		
        PathConstraints pcon = new PathConstraints();
        pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(50, 50));

        PathConstraints pcon2 = new PathConstraints();
        pcon2.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(15, 15));

        
    	IntradomainPath pSkel = new IntradomainPath();
    	pSkel.addGenericLink(glinks.get("p1.1-cli-port1"), pcon2);
    	pSkel.addGenericLink(glinks.get("p1.2-cli-port2"), pcon);
    	
        List<IntradomainPath> paths = pf.findPaths(pSkel, _1Gb, null, 3, 0);

        TestCase.assertEquals(2, paths.size());
        
        IntradomainPath fpath = paths.get(0);
        
        TestCase.assertEquals("50-50", getVlan(fpath.getConstraints(glinks.get("p1.2-cli-port2"))));
        TestCase.assertEquals("15-30", getVlan(fpath.getConstraints(glinks.get("p1.6-p1.8"))));
        TestCase.assertEquals("15-15", getVlan(fpath.getConstraints(glinks.get("p1.3-p1.5"))));
        TestCase.assertEquals("15-15", getVlan(fpath.getConstraints(glinks.get("p1.1-cli-port1"))));
	}
	
	private String getVlan(PathConstraints pcon) {
		return pcon.getRangeConstraint(ConstraintsNames.VLANS).toString();
	}
	
}
