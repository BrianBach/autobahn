/*
 * TC1_v2.java
 *
 * 2007-04-02
 */
package net.geant.autobahn.utils;

import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.intradomain.common.GenericInterface;

import org.hibernate.Transaction;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class TC1IntraTopology {

    private void domain1(IntraTopologyBuilder t) {
        
        String domainID = "domain1.eu";

        // router ports
        GenericInterface p1_1 = t.createRouterIf("test_router1.domain1.eu", "p1.1", domainID, 150336000);
        GenericInterface p1_3 = t.createNodeIf("test_router1.domain1.eu", "p1.3", 1000000000);
        GenericInterface p1_2 = t.createRouterIf("test_router2.domain1.eu", "p1.2", domainID, 1000000000);
        GenericInterface p1_4 = t.createNodeIf("test_router2.domain1.eu", "p1.4", 1000000000);
        
        // node ports
        GenericInterface p1_5 = t.createNodeIf("test_node1.domain1.eu", "p1.5", 1000000000);
        GenericInterface p1_7 = t.createNodeIf("test_node1.domain1.eu", "p1.7", 1000000000);
        GenericInterface p1_9 = t.createNodeIf("test_node1.domain1.eu", "p1.9", 1000000000);

        GenericInterface p1_6 = t.createNodeIf("test_node1.domain1.eu", "p1.6", 1000000000);
        GenericInterface p1_8 = t.createNodeIf("test_node1.domain1.eu", "p1.8", 1000000000);
        GenericInterface p1_10 = t.createNodeIf("test_node1.domain1.eu", "p1.10", 1000000000);
        
        // links
        t.addSpanningTree(p1_3,   p1_5, 100, 400);
        t.addSpanningTree(p1_4,   p1_6, 100, 400);
        t.addSpanningTree(p1_7,   p1_8, 200, 300);
        t.addSpanningTree(p1_9,   p1_10, 300, 400);
        
        // inter-domain interfaces
        GenericInterface irA_1 = t.createRouterIf("test_router1.domainA.eu",  "pA.1", "domainA.eu", 1000000000);
        GenericInterface ir2_1 = t.createRouterIf("test_router1.domain2.eu",  "p2.1", "domain2.eu", 150336000);

        // inter-domain links
        t.addSpanningTree(p1_1, irA_1, 100, 800);
        t.addSpanningTree(p1_2, ir2_1, 100, 800);
    }

    private void domain2(IntraTopologyBuilder t) {
        
        String domainID = "domain2.eu";

        // router ports
        GenericInterface p2_1 = t.createRouterIf("test_router1.domain2.eu",  "p2.1", domainID, 150336000);
        GenericInterface p2_3 = t.createNodeIf("test_router1.domain2.eu",  "p2.3", 1000000000);
        GenericInterface p2_2 = t.createRouterIf("test_router2.domain2.eu",  "p2.2", domainID, 150336000);
        GenericInterface p2_4 = t.createNodeIf("test_router2.domain2.eu",  "p2.4", 1000000000);
        GenericInterface p2_10 = t.createRouterIf("test_router2.domain2.eu",  "p2.10", domainID, 150336000);
        
        // node ports
        GenericInterface p2_5 = t.createNodeIf("test_node1.domain2.eu",  "p2.5", 1000000000);
        GenericInterface p2_6 = t.createNodeIf("test_node1.domain2.eu",  "p2.6", 1000000000);
        
        //links
        t.addSpanningTree(p2_3,   p2_5, 100, 400);
        t.addSpanningTree(p2_6,   p2_4, 100, 400);
        
        //inter-domain interfaces
        GenericInterface ir1_2 = t.createRouterIf("test_router2.domain1.eu", "p1.2", "domain1.eu", 150336000);
        GenericInterface irB_1 = t.createRouterIf("test_router1.domainB.eu", "pB.1", "domainB.eu", 1000000000);
        GenericInterface irDRAGON_1 = t.createRouterIf("test_router1.domainDRA.eu",  "pDR.1", "domainDRA.eu", 1000000000);

        // inter-domain links
        t.addSpanningTree(p2_1, ir1_2, 100, 800);
        t.addSpanningTree(p2_2, irB_1, 100, 800);
        t.addSpanningTree(p2_10, irDRAGON_1, 100, 800);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        TC1IntraTopology topo = new TC1IntraTopology();
        
        DmHibernateUtil.configure("150.254.160.216", "5432", "jra3_2", "jra3", "geant");
        Transaction t = DmHibernateUtil.getInstance().beginTransaction();

        //topo.domain1();
        topo.domain2(null);
        //topo.domain3();
        
        t.commit();
        DmHibernateUtil.getInstance().closeSession();
    }
    
    public static void cleanAllDbs() {
        
        for(int i = 1; i <= 3; i++) {
            String db_name = "jra3_" + i;
            
            DmHibernateUtil.configure("150.254.160.216", "5432", "jra3_2", "jra3", "geant");
            Transaction t = DmHibernateUtil.getInstance().beginTransaction();
            t.commit();
            DmHibernateUtil.getInstance().closeSession();
        }
    }
}
