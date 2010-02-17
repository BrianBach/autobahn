package net.geant.autobahn.utils;

import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.common.GenericInterface;

import org.hibernate.Transaction;

public class SC08DemoTopology {

    private final static String pionier = "http://IP-addr:8080/autobahn/interdomain";
	
    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;
	
    public IntraTopologyBuilder pionier(IntraTopologyBuilder t) {
        // PIONIER interfaces
        GenericInterface p1 = t.createRouterIf("Node1", "p1", pionier, _1Gb);
        GenericInterface p2 = t.createNodeIf("Node1", "p2", _10Gb);

        GenericInterface p3 = t.createNodeIf("Node2", "p3", _10Gb);
        GenericInterface p4 = t.createRouterIf("Node2", "p4", pionier, _1Gb);
        GenericInterface p5 = t.createRouterIf("Node2", "p5", pionier, _1Gb);
        GenericInterface p6 = t.createRouterIf("Node2", "p6", pionier, _1Gb);
        GenericInterface p7 = t.createRouterIf("Node2", "p7", pionier, _1Gb);
        GenericInterface p8 = t.createRouterIf("Node2", "p8", pionier, _1Gb);
        GenericInterface p9 = t.createRouterIf("Node2", "p9", pionier, _1Gb);
        GenericInterface p10 = t.createRouterIf("Node2", "p10", pionier, _1Gb);
        GenericInterface p11 = t.createRouterIf("Node2", "p11", pionier, _1Gb);
        GenericInterface p12 = t.createRouterIf("Node2", "p12", pionier, _1Gb);
        GenericInterface p13 = t.createRouterIf("Node2", "p13", pionier, _1Gb);
        GenericInterface p14 = t.createRouterIf("Node2", "p14", pionier, _1Gb);
        GenericInterface p15 = t.createRouterIf("Node2", "p15", pionier, _1Gb);

        
        // Remote interfaces
        GenericInterface host = t.createClientIf("PSNC-host-node", "PSNC-host-port", "http://client-domain.psnc.pl/", _1Gb);
        
        GenericInterface learn = t.createClientIf("LEARN-DCN-node", "LEARN-DCN", "http://oscars-domain1.edu/", _1Gb);
        GenericInterface esnet = t.createClientIf("ESNet-SDN-node", "ESNet-SDN", "http://oscars-domain2.edu/", _1Gb);
        GenericInterface scinet_1 = t.createClientIf("SCInet-1-node", "SCInet-1", "http://oscars-domain3.edu/", _1Gb);
        GenericInterface scinet_2 = t.createClientIf("SCInet-2-node", "SCInet-2", "http://oscars-domain4.edu/", _1Gb);
        GenericInterface dutch = t.createClientIf("UvA-node", "UvA-Booth", "http://oscars-domain5.edu/", _1Gb);
        GenericInterface nict = t.createClientIf("NICT-node", "NICT", "http://oscars-domain6.edu/", _1Gb);
        GenericInterface northrop = t.createClientIf("Northrop-Grumman-node", "Northrop-Grumman", "http://oscars-domain7.edu/", _1Gb);
        GenericInterface inet2_bth = t.createClientIf("Internet2-node1", "Internet2-booth", "http://oscars-domain8.edu/", _1Gb);
        GenericInterface inet2_aa = t.createClientIf("Internet2-node2", "Internet2-AA", "http://oscars-domain8.edu/", _1Gb);
        GenericInterface lhc1 = t.createClientIf("Caltech-LHC-1-node", "Caltech-LHC-1", "http://oscars-domain9.edu/", _1Gb);
        GenericInterface lhc2 = t.createClientIf("Caltech-LHC-2-node", "Caltech-LHC-2", "http://oscars-domain10.edu/", _1Gb);
        GenericInterface lsu = t.createClientIf("LSU-node", "LSU", "http://oscars-domain11.edu/", _1Gb);
        
        
        // Links
        t.addSpanningTree(p1, host, 3000, 3100);
        t.addSpanningTree(p2, p3, 3000, 3100);
        t.addSpanningTree(p4, learn, 3000, 3100);
        t.addSpanningTree(p5, esnet, 3000, 3100);
        t.addSpanningTree(p6, scinet_1, 3000, 3100);
        t.addSpanningTree(p7, scinet_2, 3000, 3100);
        t.addSpanningTree(p8, dutch, 3000, 3100);
        t.addSpanningTree(p9, nict, 3000, 3100);
        t.addSpanningTree(p10, northrop, 3000, 3100);
        t.addSpanningTree(p11, inet2_bth, 3000, 3100);
        t.addSpanningTree(p12, inet2_aa, 3000, 3100);
        t.addSpanningTree(p13, lhc1, 3000, 3100);
        t.addSpanningTree(p14, lhc2, 3000, 3100);
        t.addSpanningTree(p15, lsu, 3000, 3100);
        
        return t;
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SC08DemoTopology topo = new SC08DemoTopology();
		
		DmHibernateUtil.configure("150.254.149.74", "5432", "jra3_1",
				"jra3", "geant");
		HibernateUtil hbm = DmHibernateUtil.getInstance();
		
		IntraTopologyBuilder builder = new IntraTopologyBuilder(true);
        builder.setSession(hbm.currentSession());
        
		Transaction t = hbm.beginTransaction();
        
		topo.pionier(builder);

		t.commit();
		hbm.closeSession();
	}

}
