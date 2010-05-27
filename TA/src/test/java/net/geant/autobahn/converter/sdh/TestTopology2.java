package net.geant.autobahn.converter.sdh;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.sdh.StmType;
import net.geant.autobahn.utils.IntraTopologyBuilder;

public class TestTopology2 {
    
    // cli-node----Node1.2~~~~Node1.1
    //                      (dom1-node)
    //                     /          \
    //                    /            \
    //                   /              \
    //                  /              Node3.1-------cli-node
    //                 /              (dom3-node)
    //                |                 /
    //                |                /
    //                |               /
    //               Node2.1~~~~~~Node2.2
    //            (dom2-node1)  (dom2-node2)
    //                  ~          ~
    //                   ~        ~
    //                    ~      ~
    //                    Node2.3
    //                    /     \
    //                   /       \
    //                  /         \
    //           cli-node1       cli-node2

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    private final static String domain1 = "http://localhost:8080/autobahn/interdomain";
    private final static String domain2 = "http://localhost:8081/autobahn/interdomain";
    private final static String domain3 = "http://localhost:8082/autobahn/interdomain";

    private final static String hostDomain1 = "http://client-domain.domain1.com";
    private final static String hostDomain2 = "http://client-domain.domain2.com";
    private final static String hostDomain3 = "http://client-domain.domain3.com";

    
    // dom2-node1----Node1.1~~~~Node1.2----cli-node
    //             /
    //            /
    //           /
    //   dom3-node
    public void domain1(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Node1.1", "p1.1", domain1, _1Gb);
        GenericInterface p2 = t.createRouterIf("Node1.1", "p1.2", domain1, _1Gb);
        GenericInterface p4 = t.createNodeIf("Node1.1", "p1.4", _10Gb);
        
        GenericInterface p3 = t.createRouterIf("Node1.2", "p1.3", domain1, _1Gb);
        GenericInterface p5 = t.createNodeIf("Node1.2", "p1.5", _10Gb);
        
        // Remote interfaces
        GenericInterface dom2 = t.createRouterIf("dom2-node1", "dom2-port1", domain2, _1Gb);
        GenericInterface dom3 = t.createRouterIf("dom3-node", "dom3-port1", domain3, _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node", "cli-port", hostDomain1, _10Gb);
        
        // Stm links
        StmType type0 = new StmType();
        type0.setName("stm type 0");
        type0.setBandwidth(10);
        
        // Links
        t.addStmLink(p1, dom2, type0);
        t.addStmLink(p2, dom3, type0);
        t.addStmLink(p3, cli1, type0);
        t.addStmLink(p4, p5, type0);
    }

    // dom1-node-----Node2.1~~~~Node2.2----dom3-node
    //                  ~          ~
    //                   ~        ~
    //                    ~      ~
    //                    Node2.3
    //                    /     \
    //                   /       \
    //                  /         \
    //           cli-node1       cli-node2
    public void domain2(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Node2.1", "p2.1", domain2, _1Gb);
        GenericInterface p2 = t.createRouterIf("Node2.2", "p2.2", domain2, _1Gb);
        GenericInterface p3 = t.createRouterIf("Node2.3", "p2.3", domain2, _1Gb);
        GenericInterface p4 = t.createRouterIf("Node2.3", "p2.4", domain2, _1Gb);
        
        GenericInterface p5 = t.createNodeIf("Node2.1", "p1.5", _1Gb);
        GenericInterface p6 = t.createNodeIf("Node2.1", "p1.6", _1Gb);
        GenericInterface p7 = t.createNodeIf("Node2.2", "p1.7", _1Gb);
        GenericInterface p8 = t.createNodeIf("Node2.2", "p1.8", _1Gb);
        GenericInterface p9 = t.createNodeIf("Node2.3", "p1.9", _1Gb);
        GenericInterface p10 = t.createNodeIf("Node2.3", "p1.10", _1Gb);
        
        // Remote interfaces
        GenericInterface dom1 = t.createRouterIf("dom1-node", "dom1-port1", domain1, _1Gb);
        GenericInterface dom3 = t.createRouterIf("dom3-node", "dom3-port2", domain3, _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node1", "cli-port1", hostDomain2, _1Gb);
        GenericInterface cli2 = t.createClientIf("cli-node2", "cli-port2", hostDomain2, _1Gb);

        // Stm links
        StmType type0 = new StmType();
        type0.setName("stm type 0");
        type0.setBandwidth(10);
        
        // Links
        t.addStmLink(p1, dom1, type0);
        t.addStmLink(p2, dom3, type0);
        t.addStmLink(p3, cli1, type0);
        t.addStmLink(p4, cli2, type0);
        t.addStmLink(p5, p7, type0);
        t.addStmLink(p6, p9, type0);
        t.addStmLink(p8, p10, type0);
    }

    // dom1-node----Node3.1----dom2-node2
    //               |
    //               |
    //            cli-node
    public void domain3(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Node3.1", "p3.1", domain3, _1Gb);
        GenericInterface p2 = t.createRouterIf("Node3.1", "p3.2", domain3, _1Gb);
        GenericInterface p3 = t.createRouterIf("Node3.1", "p3.3", domain3, _1Gb);
        
        // Remote interfaces
        GenericInterface dom1 = t.createRouterIf("dom1-node", "dom1-port2", domain1, _1Gb);
        GenericInterface dom2 = t.createRouterIf("dom2-node2", "dom2-port2", domain2, _1Gb);
        GenericInterface cli1 = t.createClientIf("cli-node", "cli-port", hostDomain3, _1Gb);
        
        // Stm links
        StmType type0 = new StmType();
        type0.setName("stm type 0");
        type0.setBandwidth(10);
        
        // Links
        t.addStmLink(p1, dom1, type0);
        t.addStmLink(p2, dom2, type0);
        t.addStmLink(p3, cli1, type0);
    }

}
