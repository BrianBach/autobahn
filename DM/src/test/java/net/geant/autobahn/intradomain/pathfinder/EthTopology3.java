package net.geant.autobahn.intradomain.pathfinder;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.utils.IntraTopologyBuilder;

/**
 * @author Kostas Stamos
 *
 */
public class EthTopology3 {

    public static long _100Mb = (long) 100 * 1000 * 1000;
    public static long _500Mb = (long) 500 * 1000 * 1000;
    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    private final static String domain1 = "http://localhost:8080/autobahn/interdomain";

    private final static String hostDomain1 = "http://client-domain.domain1.com";

    //                       Node1.4
    //                       /     \
    //                      /       \
    //  cli-node2-----Node 1.2=====Node1.1=====cli-node1
    //                  |        /     
    //                  |       /      
    //                  |      /       
    //                  |     /        
    //                  |    /         
    //  cli-node3-----Node 1.3-----Node1.5=====Node1.6-------Node1.7
    //                                              \         //
    //                                               \       //
    //                                                \     //
    //                                                Node1.8------cli-node4
    public void domain1(IntraTopologyBuilder t) {
        
        GenericInterface p1_c1 = t.createRouterIf("Node1.1", "p1_c1", domain1, _100Mb);
        GenericInterface p1_c1_1 = t.createRouterIf("Node1.1", "p1_c1_1", domain1, _1Gb);
        GenericInterface p2_c2 = t.createRouterIf("Node1.2", "p2_c2", domain1, _1Gb);
        GenericInterface p3_c3 = t.createRouterIf("Node1.3", "p3_c3", domain1, _500Mb);
        GenericInterface p8_c4 = t.createRouterIf("Node1.8", "p8_c4", domain1, _1Gb);
        
        GenericInterface p1_2 = t.createNodeIf("Node1.1", "p1_2", _10Gb);
        GenericInterface p1_2_1 = t.createNodeIf("Node1.1", "p1_2_1", _10Gb);
        GenericInterface p1_4 = t.createNodeIf("Node1.1", "p1_4", _10Gb);
        GenericInterface p1_3 = t.createNodeIf("Node1.1", "p1_3", _10Gb);
        
        GenericInterface p2_1 = t.createNodeIf("Node1.2", "p2_1", _10Gb);
        GenericInterface p2_1_1 = t.createNodeIf("Node1.2", "p2_1_1", _10Gb);
        GenericInterface p2_4 = t.createNodeIf("Node1.2", "p2_4", _10Gb);
        GenericInterface p2_3 = t.createNodeIf("Node1.2", "p2_3", _10Gb);
        
        GenericInterface p3_1 = t.createNodeIf("Node1.3", "p3_1", _10Gb);
        GenericInterface p3_2 = t.createNodeIf("Node1.3", "p3_2", _10Gb);
        GenericInterface p3_5 = t.createNodeIf("Node1.3", "p3_5", _10Gb);
        
        GenericInterface p4_1 = t.createNodeIf("Node1.4", "p4_1", _10Gb);
        GenericInterface p4_2 = t.createNodeIf("Node1.4", "p4_2", _10Gb);
        
        GenericInterface p5_3 = t.createNodeIf("Node1.5", "p5_3", _10Gb);
        GenericInterface p5_6 = t.createNodeIf("Node1.5", "p5_6", _10Gb);
        GenericInterface p5_6_1 = t.createNodeIf("Node1.5", "p5_6_1", _100Mb);
        
        GenericInterface p6_5 = t.createNodeIf("Node1.6", "p6_5", _10Gb);
        GenericInterface p6_5_1 = t.createNodeIf("Node1.6", "p6_5_1", _10Gb);
        GenericInterface p6_7 = t.createNodeIf("Node1.6", "p6_7", _10Gb);
        GenericInterface p6_8 = t.createNodeIf("Node1.6", "p6_8", _10Gb);
        
        GenericInterface p7_8 = t.createNodeIf("Node1.7", "p7_8", _10Gb);
        GenericInterface p7_8_1 = t.createNodeIf("Node1.7", "p7_8_1", _10Gb);
        GenericInterface p7_6 = t.createNodeIf("Node1.7", "p7_6", _10Gb);
        
        GenericInterface p8_7 = t.createNodeIf("Node1.8", "p8_7", _10Gb);
        GenericInterface p8_7_1 = t.createNodeIf("Node1.8", "p8_7_1", _10Gb);
        GenericInterface p8_6 = t.createNodeIf("Node1.8", "p8_6", _10Gb);
        
        // Remote interfaces
        GenericInterface cli1 = t.createClientIf("cli-node1", "cli-port1", hostDomain1, _10Gb);
        GenericInterface cli1_1 = t.createClientIf("cli-node1", "cli-port1_1", hostDomain1, _10Gb);
        GenericInterface cli2 = t.createClientIf("cli-node2", "cli-port2", hostDomain1, _10Gb);
        GenericInterface cli3 = t.createClientIf("cli-node3", "cli-port3", hostDomain1, _10Gb);
        GenericInterface cli4 = t.createClientIf("cli-node4", "cli-port4", hostDomain1, _10Gb);
        
        // Links
        t.addSpanningTree(p1_c1, cli1, 100, 200);
        t.addSpanningTree(p1_c1_1, cli1_1, 100, 200);
        t.addSpanningTree(p2_c2, cli2, 100, 200);
        t.addSpanningTree(p3_c3, cli3, 100, 200);
        t.addSpanningTree(p8_c4, cli4, 100, 200);
        
        t.addSpanningTree(p1_2, p2_1, 150, 200);
        t.addSpanningTree(p1_2_1, p2_1_1, 100, 149);
        t.addSpanningTree(p1_4, p4_1, 100, 200);
        t.addSpanningTree(p1_3, p3_1, 201, 201);
        t.addSpanningTree(p2_4, p4_2, 100, 200);
        t.addSpanningTree(p2_3, p3_2, 100, 200);
        t.addSpanningTree(p3_5, p5_3, 100, 200);
        t.addSpanningTree(p5_6, p6_5, 100, 200);
        t.addSpanningTree(p5_6_1, p6_5_1, 100, 200);
        t.addSpanningTree(p6_7, p7_6, 100, 200);
        t.addSpanningTree(p6_8, p8_6, 100, 200);
        t.addSpanningTree(p7_8, p8_7, 100, 200);
        t.addSpanningTree(p7_8_1, p8_7_1, 100, 200);
    }

}
