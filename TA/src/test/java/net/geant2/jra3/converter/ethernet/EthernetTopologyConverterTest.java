package net.geant2.jra3.converter.ethernet;


import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import net.geant2.jra3.converter.InternalIdentifiersSource;
import net.geant2.jra3.converter.PublicIdentifiersMapping;
import net.geant2.jra3.converter.TopologyConverter;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.converter.Stats;
import net.geant2.jra3.intradomain.pathfinder.IntradomainPathfinder;
import net.geant2.jra3.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.topologyabstraction.ExternalIdentifiersSource;
import net.geant2.jra3.topologyabstraction.FileIdentifiersSource;
import net.geant2.jra3.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EthernetTopologyConverterTest {

	@Before
	public void setUp() throws Exception {
		System.out.println(" - --");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleDomainWith2Clients() throws IOException {
		IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
		TestTopology1 topo = new TestTopology1();
		topo.domain1(builder);
		
		IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
				builder.getTopology());
		
        String nrange = "10.11.0.0/19";
        String prange = "10.11.32.0/19";
        String lrange = "10.11.64.0/19";
        
        InternalIdentifiersSource internal = new InternalIdentifiersSource(
        		nrange, prange, lrange);
        
        // It's OK as this topology has no neighbors
        PublicIdentifiersMapping mapping = null;
		
		TopologyConverter conv = new EthernetTopologyConverter(builder
				.getTopology(), pf, internal, mapping);

		Stats stats = conv.abstractInternalPartOfTopology();
		
		TestCase.assertEquals(7, stats.numNodes);
		TestCase.assertEquals(4, stats.numEdgeNodes);
		TestCase.assertEquals(3, stats.numPaths);

		// It's OK as this topology has no neighbors
		conv.abstractExternalPartOfTopology(null);
		
		List<Link> links = conv.getAbstractLinks();
		
		TestCase.assertEquals(3, links.size());
	}
	
	@Test
	public void testTopologyWithoutExternalConnections() {
		//TODO write the test body
	}
	
	@Test
	public void testAbstractingInternalPart() throws IOException {

		TopologyConverter conv = createTopology2Converter();
		
		Stats stats = conv.abstractInternalPartOfTopology();

		TestCase.assertEquals(7, stats.numNodes);
		TestCase.assertEquals(7, stats.numEdgeNodes);
		TestCase.assertEquals(7, stats.numLinks);
		TestCase.assertEquals(6, stats.numPaths);
		
		System.out.println(conv);
	}
	
	@Test(expected=NullPointerException.class)
	public void testPassingNullAsExternalSource() throws IOException {

		TopologyConverter conv = createTopology2Converter();
		
		//TODO write the test body
		
		Stats stats = conv.abstractInternalPartOfTopology();
		conv.abstractExternalPartOfTopology(null);
	}
	
	@Test
	public void testAbstractingSampleTopology() throws IOException {

		TopologyConverter conv = createTopology2Converter();
		
		Stats stats = conv.abstractInternalPartOfTopology();

		TestCase.assertEquals(7, stats.numNodes);
		TestCase.assertEquals(7, stats.numEdgeNodes);
		TestCase.assertEquals(7, stats.numLinks);
		TestCase.assertEquals(6, stats.numPaths);

        ExternalIdentifiersSource source = new FileIdentifiersSource(
				"./src/test/resources/test_etc/topology2-external-ids.properties");
        List<Link> links = conv.abstractExternalPartOfTopology(source);
        
        TestCase.assertEquals(7, links.size());
        
        for(Link l : links)
        	System.out.println(l);

        // Check the client link
        GenericLink glink = conv.getEdgeLink(links.get(1));
        TestCase.assertEquals("p2.4-cli-port2", glink.toString());

        // Check the external link
        glink = conv.getEdgeLink(links.get(2));
        TestCase.assertEquals("p2.1-dom1-port1", glink.toString());
        
        // Check the virtual link
        glink = conv.getEdgeLink(links.get(5));
        TestCase.assertNull(glink);
	}
	
	private TopologyConverter createTopology2Converter() throws IOException {
		IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
		TestTopology2 topo = new TestTopology2();
		topo.domain2(builder);
		
		IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
				builder.getTopology());
		
        String nrange = "10.11.0.0/19";
        String prange = "10.11.32.0/19";
        String lrange = "10.11.64.0/19";
        
        InternalIdentifiersSource internal = new InternalIdentifiersSource(
        		nrange, prange, lrange);
        PublicIdentifiersMapping mapping = new PublicIdentifiersMapping(
        		"./src/test/resources/test_etc/topology2-public-ids.properties");
		
		TopologyConverter conv = new EthernetTopologyConverter(builder
				.getTopology(), pf, internal, mapping);
		
		return conv;
	}
	

}
