package net.geant.autobahn.utils;

import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.common.GenericInterface;

import org.hibernate.Transaction;

public class SC07DemoTopology {

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;
	
	private void pionier(IntraTopologyBuilder t) {
        
        String domainID = "https://150.254.160.217:8443/autobahn-idm/services/Interdomain";

        // PIONIER interfaces
        GenericInterface p1 = t.createRouterIf("XMR1", "XMR1:8/3", domainID, _1Gb);
        GenericInterface p3 = t.createNodeIf("XMR1", "XMR1:1/4", _10Gb);
        
        GenericInterface p2 = t.createRouterIf("XMR2", "XMR2:8/7", domainID, _1Gb);
        GenericInterface p4 = t.createNodeIf("XMR2", "XMR2:1/4", _10Gb);

        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("tbdxc1.lon.uk", "tbdxc1.lon.uk@192.168.119.2:tbdxc1.lon.uk:tbdxc1.lon.uk/r01s3b013p03-gMAU",
						"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain",
						_1Gb);
        GenericInterface host = t.createRouterIf("hostnode.psnc.pl", "host.psnc.pl", "https://host.psnc.pl/", _1Gb);

        // Links
        t.addSpanningTree(p1, host, 3010, 3010);
        t.addSpanningTree(p2, gn2, 3010, 3010);
        t.addSpanningTree(p3, p4, 3010, 3010);
	}
	
	public void heanet(IntraTopologyBuilder t) {
        
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
        GenericInterface gn2 = t.createRouterIf("tbdxc1.lon.uk", "tbdxc1.lon.uk@192.168.119.2:tbdxc1.lon.uk:tbdxc1.lon.uk/r01s3b013p01-gMAU",
						"https://srv2.lon.uk.geant2.net:8443/autobahn-idm/services/Interdomain",
						_1Gb);
        GenericInterface host = t.createRouterIf("hostnode.psnc.pl", "host.heanet.ie", "https://host.heanet.ie/", _1Gb);

        // Links
        t.addSpanningTree(p1, gn2, 3010, 3010);
        t.addSpanningTree(p2, p3, 3010, 3010);
        t.addSpanningTree(p4, p5, 3010, 3010);
        t.addSpanningTree(p6, p7, 3010, 3010);
        t.addSpanningTree(p8, p9, 3010, 3010);
        t.addSpanningTree(p10, host, 3010, 3010);
	}
	
    public static void main(String[] args) {
    	SC07DemoTopology topo = new SC07DemoTopology();

        DmHibernateUtil.configure("150.254.160.216", "5432", "jra3_3", "jra3", "geant");
        HibernateUtil hbm = DmHibernateUtil.getInstance();

		IntraTopologyBuilder builder = new IntraTopologyBuilder(true);
        builder.setSession(hbm.currentSession());
        
        Transaction t = hbm.beginTransaction();
        
        //topo.pionier();
        topo.heanet(builder);
        
        t.commit();
        hbm.closeSession();
    }

}
