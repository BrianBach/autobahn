/**
 * 
 */
package net.geant2.jra3.intradomain.pathfinder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.geant2.jra3.constraints.ConstraintsNames;
import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.constraints.RangeConstraint;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.intradomain.IntradomainTopology;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.ethernet.EthernetIntradomainPathfinder;
import net.geant2.jra3.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.geant2.jra3.intradomain.pathfinder.EthTopology1._10Gb;
import static net.geant2.jra3.intradomain.pathfinder.EthTopology1._1Gb;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetPathfinderTest_Topology2 {

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
	public void testFindingAllPaths() {
		List<IntradomainPath> paths = pf.findPaths(nodes.get("Node1.1"), 
				nodes.get("Node1.3"), null, Integer.MAX_VALUE);
		
		TestCase.assertEquals(4, paths.size());
		for(IntradomainPath path : paths) {
			System.out.println(path);
		}
	}
	
	@Test
	public void testFindingAllPathsBetweenGivenLinks() {
		List<IntradomainPath> paths = pf.findPaths(glinks.get("p1.1-cli-port1"), 
				glinks.get("p1.4-cli-port4"), _1Gb, null, Integer.MAX_VALUE);
		
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
		
		IntradomainPath path = pf.findPath(glinks.get("p1.1-cli-port1"), 
				glinks.get("p1.4-cli-port4"), _1Gb, pcon, null);

		TestCase.assertNotNull(path);
		TestCase.assertNotNull(rcon.intersect(path.getMergedConstraints()
				.getRangeConstraint(ConstraintsNames.VLANS)));
		TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.6-p1.10")));
		TestCase.assertFalse(path.getLinks().contains(glinks.get("p1.8-p1.13")));
	}
}
