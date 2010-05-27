package net.geant.autobahn.converter.sdh;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.sdh.StmType;
import net.geant.autobahn.utils.IntraTopologyBuilder;

public class TestTopology1 {
    

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    private final static String domain1 = "http://localhost:8080/autobahn/interdomain";

    private final static String hostDomain1 = "http://client-domain.domain1.com";

    //  cli-node2-----Node 1.2-----Node1.1-----cli-node1
    //                  |        /     |
    //                  |       /      |
    //                  |      /       |
    //                  |     /        |
    //                  |    /         |
    //                Node 1.3-----Node1.4
    public void domain1(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Node1.1", "p1.1", domain1, _1Gb);
        GenericInterface p2 = t.createRouterIf("Node1.2", "p1.2", domain1, _1Gb);
        
        GenericInterface p3 = t.createNodeIf("Node1.1", "p1.3", _10Gb);
        GenericInterface p4 = t.createNodeIf("Node1.1", "p1.4", _10Gb);
        GenericInterface p5 = t.createNodeIf("Node1.1", "p1.5", _10Gb);

        GenericInterface p6 = t.createNodeIf("Node1.2", "p1.6", _10Gb);
        GenericInterface p7 = t.createNodeIf("Node1.2", "p1.7", _10Gb);

        GenericInterface p8 = t.createNodeIf("Node1.3", "p1.8", _10Gb);
        GenericInterface p9 = t.createNodeIf("Node1.3", "p1.9", _10Gb);
        GenericInterface p10 = t.createNodeIf("Node1.3", "p1.10", _10Gb);
        
        GenericInterface p11 = t.createNodeIf("Node1.4", "p1.11", _10Gb);
        GenericInterface p12 = t.createNodeIf("Node1.4", "p1.12", _10Gb);
        
        GenericInterface p13 = t.createNodeIf("Node1.5", "p1.13", _10Gb);
        GenericInterface p14 = t.createNodeIf("Node1.5", "p1.14", _10Gb);
        
        // Remote interfaces
        GenericInterface cli1 = t.createClientIf("cli-node1", "cli-port1", hostDomain1, _10Gb);
        GenericInterface cli2 = t.createClientIf("cli-node2", "cli-port2", hostDomain1, _10Gb);
        
        // Stm links
        StmType type0 = new StmType();
        type0.setName("stm type 0");
        type0.setBandwidth(10);
        
        // Links
        t.addStmLink(p1, cli1, type0);
        t.addStmLink(p2, cli2, type0);
        t.addStmLink(p3, p8, type0);
        t.addStmLink(p4, p7, type0);
        t.addStmLink(p5, p11, type0);
        t.addStmLink(p7, p10, type0);
        t.addStmLink(p9, p12, type0);
    }

}
