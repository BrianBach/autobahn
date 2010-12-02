/**
 * 
 */
package net.geant.autobahn.itest.env1;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.testplatform.configuration.Domain;
import net.geant.autobahn.testplatform.configuration.IntraTopologyBuilder;

/**
 * @author jacek
 *
 */
public class AutobahnTestTopology {

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    private final static Domain domain1 = new Domain("domain1", "localhost", 28080, 28090);
    private final static Domain domain2 = new Domain("domain2", "localhost", 28081, 28091);
    private final static Domain domain3 = new Domain("domain3", "localhost", 28082, 28092);

    static {
    	 domain1.setLongitude(-1.17); domain1.setLatitude(34.00);
    	 domain2.setLongitude(5.17); domain2.setLatitude(14.00);
    	 domain3.setLongitude(20.17); domain3.setLatitude(22.00);
    }
    
    private final static String hostDomain1 = "http://client-domain.domain1.com";
    private final static String hostDomain2 = "http://client-domain.domain2.com";
    private final static String hostDomain3 = "http://client-domain.domain3.com";

    
    public void domain1(IntraTopologyBuilder t) {

    	t.setDomain(domain1);
    	
        GenericInterface p1 = t.createRouterIf("Node1.1", "p1.1", "dom1-port1", _1Gb);
        GenericInterface p2 = t.createRouterIf("Node1.1", "p1.2", "dom1-port2", _1Gb);
        GenericInterface p4 = t.createNodeIf("Node1.1", "p1.4", _10Gb);
        
        GenericInterface p3 = t.createRouterIf("Node1.2", "p1.3", "to-cli1", _1Gb);
        GenericInterface p5 = t.createNodeIf("Node1.2", "p1.5", _10Gb);
        
        // Remote interfaces
        GenericInterface dom2 = t.createExternalRouterIf("dom2-node1", "dom2-port1", domain2.getDomainId(), _1Gb);
        GenericInterface dom3 = t.createExternalRouterIf("dom3-node", "dom3-port1", domain3.getDomainId(), _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node", "cli-port", hostDomain1, _10Gb, "Enpoint in Poznan");
        
        // Links
        t.addSpanningTree(p1, dom2, 100, 200);
        t.addSpanningTree(p2, dom3, 100, 200);
        t.addSpanningTree(p3, cli1, 100, 200);
        t.addSpanningTree(p4, p5, 150, 200);
    }

    public void domain2(IntraTopologyBuilder t) {
        
    	t.setDomain(domain2);
    	
        GenericInterface p1 = t.createRouterIf("Node2.1", "p2.1", "dom2-port1", _1Gb);
        GenericInterface p2 = t.createRouterIf("Node2.2", "p2.2", "dom2-port2", _1Gb);
        GenericInterface p3 = t.createRouterIf("Node2.3", "p2.3", "to-cli1", _1Gb);
        GenericInterface p4 = t.createRouterIf("Node2.3", "p2.4", "to-cli2", _1Gb);
        
        GenericInterface p5 = t.createNodeIf("Node2.1", "p1.5", _1Gb);
        GenericInterface p6 = t.createNodeIf("Node2.1", "p1.6", _1Gb);
        GenericInterface p7 = t.createNodeIf("Node2.2", "p1.7", _1Gb);
        GenericInterface p8 = t.createNodeIf("Node2.2", "p1.8", _1Gb);
        GenericInterface p9 = t.createNodeIf("Node2.3", "p1.9", _1Gb);
        GenericInterface p10 = t.createNodeIf("Node2.3", "p1.10", _1Gb);
        
        // Remote interfaces
        GenericInterface dom1 = t.createExternalRouterIf("dom1-node", "dom1-port1", domain1.getDomainId(), _1Gb);
        GenericInterface dom3 = t.createExternalRouterIf("dom3-node", "dom3-port2", domain3.getDomainId(), _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node1", "cli-port1", hostDomain2, _1Gb, "Enpoint in Dublin - sigma");
        GenericInterface cli2 = t.createClientIf("cli-node2", "cli-port2", hostDomain2, _1Gb, "Enpoint in Dublin - pi");

        // Links
        t.addSpanningTree(p1, dom1, 100, 200);
        t.addSpanningTree(p2, dom3, 100, 200);
        t.addSpanningTree(p3, cli1, 100, 200);
        t.addSpanningTree(p4, cli2, 100, 200);
        t.addSpanningTree(p5, p7, 100, 200);
        t.addSpanningTree(p6, p9, 100, 200);
        t.addSpanningTree(p8, p10, 100, 200);
    }

    public void domain3(IntraTopologyBuilder t) {
        
    	t.setDomain(domain3);
    	
        GenericInterface p1 = t.createRouterIf("Node3.1", "p3.1", "dom3-port1", _1Gb);
        GenericInterface p2 = t.createRouterIf("Node3.1", "p3.2", "dom3-port2", _1Gb);
        GenericInterface p3 = t.createRouterIf("Node3.1", "p3.3", "to-cli1", _1Gb);
        
        // Remote interfaces
        GenericInterface dom1 = t.createExternalRouterIf("dom1-node", "dom1-port2", domain1.getDomainId(), _1Gb);
        GenericInterface dom2 = t.createExternalRouterIf("dom2-node2", "dom2-port2", domain2.getDomainId(), _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node", "cli-port", hostDomain3, _1Gb, "Calculation cluster - CERN");
        
        // Links
        t.addSpanningTree(p1, dom1, 100, 800);
        t.addSpanningTree(p2, dom2, 100, 800);
        t.addSpanningTree(p3, cli1, 100, 800);
    }

}
