package net.geant2.jra3.utils;

import net.geant2.jra3.dao.hibernate.DmHibernateUtil;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.common.GenericInterface;

import org.hibernate.Transaction;

public class GN2_Jan08_DemoTopology {

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    
	public void pionier(IntraTopologyBuilder t) {
        //t.setSession(HibernateUtil.currentSession());
        
        String domainID = "https://poznan.autobahn.psnc.pl:8443/autobahn-idm/services/Interdomain";

        // PIONIER interfaces
        GenericInterface p1 = t.createRouterIf("XMR1", "p1", domainID, _1Gb);
        GenericInterface p2 = t.createNodeIf("XMR1", "p2", _1Gb);
        
        GenericInterface p3 = t.createRouterIf("XMR2", "p3", domainID, _1Gb);
        GenericInterface p4 = t.createNodeIf("XMR2", "p4", _1Gb);

        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("LONDON-MCC", "GN2-port-pionier",
						"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain", _1Gb);
        GenericInterface host = t.createClientIf("NODE-PSNC-HOST", "PSNC-HOST", "https://c.psnc.pl/", _1Gb);

        // Links
        t.addSpanningTree(p1, host, 100, 200);
        t.addSpanningTree(p3, gn2, 100, 200);
        t.addSpanningTree(p2, p4, 150, 200);
	}
	
	public void heanet(IntraTopologyBuilder t) {
        //t.setSession(HibernateUtil.currentSession());
        
        String domainID = "https://kanga.heanet.ie:8443/autobahn-idm/services/Interdomain";

        // PIONIER interfaces
        GenericInterface p1 = t.createRouterIf("Cwt-sw1", "Cwt-sw1 Gig2/0/7", domainID, _1Gb);
        GenericInterface p2 = t.createNodeIf("Cwt-sw1", "Cwt-sw1 TE1/0/1", _10Gb);
        
        GenericInterface p3 = t.createNodeIf("tcd-sw1", "tcd-sw1TE1/0/1", _10Gb);
        GenericInterface p4 = t.createNodeIf("tcd-sw1", "tcd-sw1 Gig2/0/5", _1Gb);

        GenericInterface p5 = t.createNodeIf("CWT-PE1", "CWT-PE1 TE1/4", _10Gb);
        GenericInterface p6 = t.createNodeIf("CWT-PE1", "CWT-PE1 TE1/3", _10Gb);

        GenericInterface p7 = t.createNodeIf("TCD-PE1", "TCD-PE1 TE1/2", _10Gb);
        GenericInterface p8 = t.createNodeIf("TCD-PE1", "TCD-PE1 TE1/4", _10Gb);
        
        GenericInterface p9 = t.createNodeIf("GD5-sw1", "GD5-sw1 Gig1/0/1", _1Gb);
        GenericInterface p10 = t.createRouterIf("GD5-sw1", "GD5-sw1 Gig1/0/7", domainID, _1Gb);

        
        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("LONDON-MCC", "gn2-port",
						"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain",
						_1Gb);
        GenericInterface host = t.createRouterIf("NODE-HEANET-HOST", "HEANET-HOST", "https://client-domain.heanet.ie/", _1Gb);

        // Links
        t.addSpanningTree(p1, gn2, 100, 200);
        t.addSpanningTree(p2, p3, 100, 200);
        t.addSpanningTree(p4, p5, 100, 200);
        t.addSpanningTree(p6, p7, 100, 200);
        t.addSpanningTree(p8, p9, 100, 200);
        t.addSpanningTree(p10, host, 100, 200);
	}
	
	private void grnet(IntraTopologyBuilder t) {
        //t.setSession(HibernateUtil.currentSession());
        
        String domainID = "https://gn2jra3.grnet.gr:8443/autobahn-idm/services/Interdomain";

        GenericInterface p1 = t.createRouterIf("Athens-MCC", "Gi1/10", domainID, _1Gb);
        GenericInterface p2 = t.createRouterIf("Athens-MCC", "Gi1/11", domainID, _1Gb);

        GenericInterface p3 = t.createRouterIf("Athens-MCC", "Gi5/2", domainID, _1Gb);
        GenericInterface p4 = t.createNodeIf("Athens-MCC", "Gi5/0", _1Gb);

        GenericInterface p5 = t.createNodeIf("Crete-MCC", "Crete_p5", _1Gb);
        GenericInterface p6 = t.createRouterIf("Crete-MCC", "Gi1/0/12", domainID, _1Gb);
        
        GenericInterface gn2_1 = t.createRouterIf("PARIS-MCC", "gn2-port-3_4",
				"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain",
				_1Gb);
        GenericInterface gn2_2 = t.createRouterIf("PARIS-MCC", "gn2-port-5_6",
				"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain",
				_1Gb);
        GenericInterface host_athens = t.createRouterIf("NODE-ATHENS-HOST", "ATHENS-HOST", "https://client-domain1.grnet.gr/", _1Gb);
        GenericInterface host_crete = t.createRouterIf("NODE-CRETE-HOST", "CRETE-HOST", "https://client-domain2.grnet.gr/", _1Gb);
        
        // Links
        t.addSpanningTree(p1, gn2_1, 150, 200);
        t.addSpanningTree(p2, gn2_2, 150, 200);
        t.addSpanningTree(p3, host_athens, 150, 200);
        t.addSpanningTree(p4, p5, 150, 200);
        t.addSpanningTree(p6, host_crete, 150, 200);
	}
	
	private void geant2(IntraTopologyBuilder t) {
        //t.setSession(HibernateUtil.currentSession());
        
        String domainID = "https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain";

        // GEANT2 interfaces
        GenericInterface p1 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p02-gMAU", domainID, _1Gb);
        GenericInterface p2 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p01-gMAU", domainID, _1Gb);

        GenericInterface p3 = t.createRouterIf("London-MCC", "tbdxc1.lon.uk@192.168.119.2:tbdxc1.lon.uk:tbdxc1.lon.uk/r01s3b13p03-gMAU", domainID, _1Gb);
        GenericInterface p4 = t.createRouterIf("London-MCC", "tbdxc1.lon.uk@192.168.119.2:tbdxc1.lon.uk:tbdxc1.lon.uk/r01s3b13p01-gMAU", domainID, _1Gb);
        
        GenericInterface p5 = t.createNodeIf("Paris-MCC", "p5_Paris", _1Gb);
        GenericInterface p6 = t.createNodeIf("Paris-MCC", "p6_Paris", _1Gb);
        
        GenericInterface p7 = t.createNodeIf("London-MCC", "p7_Lon", _1Gb);
        GenericInterface p8 = t.createNodeIf("London-MCC", "p8_Lon", _1Gb);
        GenericInterface p9 = t.createNodeIf("London-MCC", "p9_Lon", _1Gb);
        
        GenericInterface p10 = t.createNodeIf("Prague-MCC", "p10_Pra", _1Gb);
        GenericInterface p11 = t.createNodeIf("Prague-MCC", "p11_Pra", _1Gb);
        GenericInterface p12 = t.createNodeIf("Prague-MCC", "p12_Pra", _1Gb);
        
        GenericInterface p13 = t.createNodeIf("Amsterdam-MCC", "p13_Ams", _1Gb);
        GenericInterface p14 = t.createNodeIf("Amsterdam-MCC", "p14_Ams", _1Gb);
        
        GenericInterface p15 = t.createNodeIf("Frankfurt-MCC", "p15_Fra", _1Gb);
        GenericInterface p16 = t.createNodeIf("Frankfurt-MCC", "p16_Fra", _1Gb);
        
        // Remote interfaces
        GenericInterface grnet1 = t.createRouterIf("Athens-MCC", "p21_Athens",
						"https://grnet.gr:8443/autobahn-idm/services/Interdomain", _1Gb);
        GenericInterface grnet2 = t.createRouterIf("Athens-MCC", "p22_Athens",
				"https://grnet.gr:8443/autobahn-idm/services/Interdomain", _1Gb);
        GenericInterface psnc = t.createRouterIf("Poznan-MCC", "p31_Poznan",
				"https://poznan.autobahn.psnc.pl:8443/autobahn-idm/services/Interdomain", _1Gb);
        GenericInterface heanet = t.createRouterIf("Dublin-MCC", "p41_Dublin",
				"https://kanga.heanet.ie:8443/autobahn-idm/services/Interdomain", _1Gb);

        // Links
        t.addSpanningTree(p5, p7, 150, 200);
        t.addSpanningTree(p6, p10, 150, 200);
        t.addSpanningTree(p8, p13, 150, 200);
        t.addSpanningTree(p9, p15, 150, 200);
        t.addSpanningTree(p11, p14, 150, 200);
        t.addSpanningTree(p12, p16, 150, 200);
        
        t.addSpanningTree(p1, grnet1, 150, 200);
        t.addSpanningTree(p2, grnet2, 150, 200);
        t.addSpanningTree(p3, psnc, 150, 200);
        t.addSpanningTree(p4, heanet, 150, 200);
	}
	
    public static void main(String[] args) {
    	GN2_Jan08_DemoTopology instance = new GN2_Jan08_DemoTopology();
        
        DmHibernateUtil.configure("150.254.160.216", "5432", "jra3_1", "jra3", "geant");
        //Transaction t = HibernateUtil.beginTransaction();

        IntraTopologyBuilder topo = new IntraTopologyBuilder(true);
        
        instance.pionier(topo);
        //topo.heanet();
        //topo.grnet();
        
        //topo.geant2();
        
        //t.commit();
        //HibernateUtil.closeSession();
    }
}
