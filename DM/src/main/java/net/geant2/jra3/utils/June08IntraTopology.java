/**
 * 
 */
package net.geant2.jra3.utils;

import net.geant2.jra3.dao.hibernate.DmHibernateUtil;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.sdh.StmType;

import org.hibernate.Transaction;

/**
 * @author jacek
 *
 */
public class June08IntraTopology {

    public static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
    public static long _10Gb = (long) 10 * 1000 * 1000 * 1000;

    private final static String pionier = "http://poznan.autobahn.psnc.pl:8080/autobahn/interdomain";
    private final static String grnet = "http://gn2jra3.grnet.gr:8080/autobahn/interdomain";
    private final static String heanet = "http://kanga.heanet.ie:8080/autobahn/interdomain";
    private final static String geant2 = "http://srv2.lon.uk.geant2.net:8080/autobahn/interdomain";
    private final static String garr = "http://abcpl.dir.garr.it:8080/autobahn/interdomain";
    private final static String cesnet = "http://idm1.cesnet.cz:8080/autobahn/interdomain";
    private final static String carnet = "http://lambda.carnet.hr:8080/autobahn/interdomain";
    
    private final static String hostDomain1 = "http://client-domain.domain1.com";
    private final static String hostDomain2 = "http://client-domain.domain2.com";
    private final static String hostDomain3 = "http://client-domain.domain3.com";
    private final static String internet2 = "http://client-domain.internet2.edu";
    private final static String vlbi = "http://client-domain.vlbi.nl";
    private final static String fccn = "http://client-domain.fccn.pt";
    //private final static String cesnet = "http://client-domain.cesnet.cz";
    
	public IntraTopologyBuilder pionier(IntraTopologyBuilder t) {
        // PIONIER interfaces
        GenericInterface p1 = t.createRouterIf("XMR1", "p1", pionier, _1Gb);
        GenericInterface p5 = t.createRouterIf("XMR1", "p5", pionier, _1Gb);
        GenericInterface p2 = t.createNodeIf("XMR1", "p2", _10Gb);
        
        GenericInterface p3 = t.createRouterIf("XMR2", "p3", pionier, _1Gb);
        GenericInterface p4 = t.createNodeIf("XMR2", "p4", _10Gb);

        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("GN2-node-1", "GN2-port-pionier", geant2, _1Gb);
        GenericInterface cesnet_port = t.createRouterIf("CESNET-node", "CESNET-port-pionier", cesnet, _1Gb);
        GenericInterface host = t.createClientIf("PSNC-host-node", "PSNC-host-port", "http://client-domain.psnc.pl/", _1Gb);

        // Links
        t.addSpanningTree(p1, host, 100, 200);
        t.addSpanningTree(p3, gn2, 100, 200);
        t.addSpanningTree(p5, cesnet_port, 100, 200);
        t.addSpanningTree(p2, p4, 150, 200);
        
        return t;
	}
	
	public void heanet(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Cwt-sw1", "Cwt-sw1 Gig2/0/7", heanet, _1Gb);
        GenericInterface p2 = t.createNodeIf("Cwt-sw1", "Cwt-sw1 TE1/0/1", _10Gb);
        
        GenericInterface p3 = t.createNodeIf("tcd-sw1", "tcd-sw1TE1/0/1", _10Gb);
        GenericInterface p4 = t.createNodeIf("tcd-sw1", "tcd-sw1 Gig2/0/5", _1Gb);

        GenericInterface p5 = t.createNodeIf("CWT-PE1", "CWT-PE1 TE1/4", _10Gb);
        GenericInterface p6 = t.createNodeIf("CWT-PE1", "CWT-PE1 TE1/3", _10Gb);

        GenericInterface p7 = t.createNodeIf("TCD-PE1", "TCD-PE1 TE1/2", _10Gb);
        GenericInterface p8 = t.createNodeIf("TCD-PE1", "TCD-PE1 TE1/4", _10Gb);
        
        GenericInterface p9 = t.createNodeIf("GD5-sw1", "GD5-sw1 Gig1/0/1", _1Gb);
        GenericInterface p10 = t.createRouterIf("GD5-sw1", "GD5-sw1 Gig1/0/7", heanet, _1Gb);

        
        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("GN2-node-1", "GN2-port-heanet", geant2, _1Gb);
        GenericInterface host = t.createClientIf("HEANET-host-node", "HEANET-host-port", "http://client-domain.heanet.ie/", _1Gb);

        // Links
        t.addSpanningTree(p1, gn2, 100, 200);
        t.addSpanningTree(p2, p3, 100, 200);
        t.addSpanningTree(p4, p5, 100, 200);
        t.addSpanningTree(p6, p7, 100, 200);
        t.addSpanningTree(p8, p9, 100, 200);
        t.addSpanningTree(p10, host, 100, 200);
	}
    
	private void grnet(IntraTopologyBuilder t) {

        GenericInterface p1 = t.createRouterIf("Athens-MCC", "Gi1/10", grnet, _1Gb);
        GenericInterface p2 = t.createRouterIf("Athens-MCC", "Gi1/11", grnet, _1Gb);

        GenericInterface p3 = t.createRouterIf("Athens-MCC", "Gi5/2", grnet, _1Gb);
        GenericInterface p4 = t.createNodeIf("Athens-MCC", "Gi5/0", _1Gb);

        GenericInterface p5 = t.createNodeIf("Crete-MCC", "Crete_p5", _1Gb);
        GenericInterface p6 = t.createRouterIf("Crete-MCC", "Gi1/0/12", grnet, _1Gb);
        
        GenericInterface gn2_1 = t.createRouterIf("GN2-node-4", "GN2-port-athens", geant2, _1Gb);
        GenericInterface gn2_2 = t.createRouterIf("GN2-node-4", "GN2-port-crete", geant2, _1Gb);
        GenericInterface host_athens = t.createClientIf("NODE-ATHENS-HOST", "ATHENS-HOST", "https://client-domain1.grnet.gr/", _1Gb);
        GenericInterface host_crete = t.createClientIf("NODE-CRETE-HOST", "CRETE-HOST", "https://client-domain2.grnet.gr/", _1Gb);
        
        // Links
        t.addSpanningTree(p1, gn2_1, 150, 200);
        t.addSpanningTree(p2, gn2_2, 150, 200);
        t.addSpanningTree(p3, host_athens, 150, 200);
        t.addSpanningTree(p4, p5, 150, 200);
        t.addSpanningTree(p6, host_crete, 150, 200);
	}
	
    private void geant2(IntraTopologyBuilder t) {

        //tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p02-gMAU 
        //tbdxc1.lon.uk@192.168.119.2:tbdxc1.lon.uk:tbdxc1.lon.uk
        //tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p01
        //tbdxc1.fra.de@192.168.121.2:tbdxc1.fra.de:tbdxc1.fra.de/r01s3b13p01
        //tbdxc1.pra.cz@192.168.120.2:tbdxc1.pra.cz:tbdxc1.pra.cz/r01s3b13p03
        
//        GenericInterface p15 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p01-gMAU", geant2, _1Gb);
//        GenericInterface p16 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p02-gMAU", geant2, _1Gb);
        GenericInterface p15 = t.createRouterIf("Frankfurt-MCC", "tbdxc1.fra.de@192.168.121.2:tbdxc1.fra.de:tbdxc1.fra.de/r01s3b13p02-gMAU", geant2, _1Gb);
        GenericInterface p16 = t.createRouterIf("Frankfurt-MCC", "tbdxc1.fra.de@192.168.121.2:tbdxc1.fra.de:tbdxc1.fra.de/r01s3b13p03-gMAU", geant2, _1Gb);
        GenericInterface p17 = t.createRouterIf("Frankfurt-MCC", "tbdxc1.fra.de@192.168.121.2:tbdxc1.fra.de:tbdxc1.fra.de/r01s3b13p01-gMAU", geant2, _1Gb);
        GenericInterface p18 = t.createRouterIf("Prague-MCC", "tbdxc1.pra.cz@192.168.120.2:tbdxc1.pra.cz:tbdxc1.pra.cz/r01s3b13p03-gMAU", geant2, _1Gb);
        GenericInterface p19 = t.createRouterIf("Prague-MCC", "tbdxc1.pra.cz@192.168.120.2:tbdxc1.pra.cz:tbdxc1.pra.cz/r01s3b13p02-gMAU", geant2, _1Gb);
        GenericInterface p20 = t.createRouterIf("Prague-MCC", "tbdxc1.pra.cz@192.168.120.2:tbdxc1.pra.cz:tbdxc1.pra.cz/r01s3b13p01-gMAU", geant2, _1Gb);
        GenericInterface p21 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p04-gMAU", geant2, _1Gb);
        GenericInterface p22 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p02-gMAU", geant2, _1Gb);
        GenericInterface p23 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p03-gMAU", geant2, _1Gb);
        GenericInterface p24 = t.createRouterIf("Paris-MCC", "tbdxc1.par.fr@192.168.122.2:tbdxc1.par.fr:tbdxc1.par.fr/r01s3b13p01-gMAU", geant2, _1Gb);

        GenericInterface p25 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p01-gMAU", geant2, _1Gb);
        GenericInterface p26 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p02-gMAU", geant2, _1Gb);
        GenericInterface p27 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p03-gMAU", geant2, _1Gb);
        GenericInterface p28 = t.createRouterIf("Amsterdam-MCC", "tbdxc1.ams.nl@192.168.123.2:tbdxc1.ams.nl:tbdxc1.ams.nl/r01s3b13p04-gMAU", geant2, _1Gb);

        GenericInterface p29 = t.createRouterIf("Frankfurt-MCC", "tbdxc1.fra.de@192.168.121.2:tbdxc1.fra.de:tbdxc1.fra.de/r01s3b13p04-gMAU", geant2, _1Gb);
        
        GenericInterface p1 = t.createNodeIf("London-MCC", "p1", _10Gb);
        GenericInterface p2 = t.createNodeIf("London-MCC", "p2", _10Gb);
        GenericInterface p3 = t.createNodeIf("London-MCC", "p3", _10Gb);

        GenericInterface p4 = t.createNodeIf("Amsterdam-MCC", "p4", _10Gb);
        GenericInterface p5 = t.createNodeIf("Amsterdam-MCC", "p5", _10Gb);
        GenericInterface p6 = t.createNodeIf("Amsterdam-MCC", "p6", _10Gb);

        GenericInterface p7 = t.createNodeIf("Prague-MCC", "p7", _10Gb);
        GenericInterface p8 = t.createNodeIf("Prague-MCC", "p8", _10Gb);
        GenericInterface p9 = t.createNodeIf("Prague-MCC", "p9", _10Gb);
        
        GenericInterface p10 = t.createNodeIf("Paris-MCC", "p10", _10Gb);
        GenericInterface p11 = t.createNodeIf("Paris-MCC", "p11", _10Gb);
        
        GenericInterface p12 = t.createNodeIf("Frankfurt-MCC", "p12", _10Gb);
        GenericInterface p13 = t.createNodeIf("Frankfurt-MCC", "p13", _10Gb);
        GenericInterface p14 = t.createNodeIf("Frankfurt-MCC", "p14", _10Gb);
        
        GenericInterface heanet_port = t.createRouterIf("HEANET-node", "HEANET-port", heanet, _1Gb);
        GenericInterface garr_port = t.createRouterIf("GARR-node", "GARR-port", garr, _1Gb);
        GenericInterface pionier_port = t.createRouterIf("PIONIER-node", "PIONIER-port", pionier, _1Gb);
        GenericInterface carnet_port = t.createRouterIf("CARNET-node", "CARNET-port", carnet, _1Gb);
        GenericInterface grnet_port_1 = t.createRouterIf("GRNET-node-1", "GRNET-port-athens", grnet, _1Gb);
        GenericInterface grnet_port_2 = t.createRouterIf("GRNET-node-1", "GRNET-port-crete", grnet, _1Gb);
        GenericInterface cesnet_port_a = t.createRouterIf("CESNET-node-1", "CESNET-port-a", cesnet, _1Gb);
        GenericInterface cesnet_port_b = t.createRouterIf("CESNET-node-1", "CESNET-port-b", cesnet, _1Gb);

        
        GenericInterface internet2_a = t.createClientIf("Internet2-node", "Internet2-port-a", internet2, _1Gb);
        GenericInterface internet2_b = t.createClientIf("Internet2-node", "Internet2-port-b", internet2, _1Gb);
        GenericInterface vlbi_1 = t.createClientIf("VLBI-1", "vlbi-port-1", vlbi, _1Gb);
        GenericInterface vlbi_2 = t.createClientIf("VLBI-2", "vlbi-port-2", vlbi, _1Gb);
        GenericInterface vlbi_3 = t.createClientIf("VLBI-3", "vlbi-port-3", vlbi, _1Gb);
        GenericInterface vlbi_4 = t.createClientIf("VLBI-4", "vlbi-port-4", vlbi, _1Gb);
        
        GenericInterface fccn_port = t.createClientIf("FCCN-node", "FCCN-port", fccn, _1Gb);
        
		//Stm links
		StmType type0 = new StmType();
		type0.setName("stm type 0");
		type0.setBandwidth(10);
        
        t.addStmLink(p1, p10, type0);
        t.addStmLink(p2, p4, type0);
        t.addStmLink(p3, p14, type0);
        t.addStmLink(p5, p13, type0);
        t.addStmLink(p6, p8, type0);
        t.addStmLink(p9, p12, type0);
        t.addStmLink(p11, p7, type0);
        
        t.addStmLink(p15, heanet_port, type0);
        t.addStmLink(p16, garr_port, type0);
        t.addStmLink(p17, pionier_port, type0);
        t.addStmLink(p18, carnet_port, type0);
        t.addStmLink(p19, cesnet_port_b, type0);
        t.addStmLink(p20, cesnet_port_a, type0);
        t.addStmLink(p21, grnet_port_1, type0);
        t.addStmLink(p22, grnet_port_2, type0);
        t.addStmLink(p23, internet2_b, type0);
        t.addStmLink(p24, internet2_a, type0);
        t.addStmLink(p25, vlbi_1, type0);
        t.addStmLink(p26, vlbi_2, type0);
        t.addStmLink(p27, vlbi_3, type0);
        t.addStmLink(p28, vlbi_4, type0);
        t.addStmLink(p29, fccn_port, type0);
        
        t.saveSdhDevices();
    }
    
	private void garr(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("garr-mc", "mc-port-1", garr, _1Gb);
        GenericInterface p2 = t.createRouterIf("garr-mc", "mc-port-2", garr, _1Gb);

        
        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("GN2-node-1", "GN2-port-garr", geant2, _1Gb);
        GenericInterface host = t.createClientIf("GARR-host-node", "GARR-host-port", "http://client-domain.garr.it/", _1Gb);

        // Links
        t.addSpanningTree(p1, gn2, 950, 960);
        t.addSpanningTree(p2, host, 950, 960);
	}
    
    private void carnet(IntraTopologyBuilder t) {
        
        GenericInterface p1 = t.createRouterIf("Kraftwerk-ES", "Gigabit 1/0/11", carnet, _1Gb);
        GenericInterface p2 = t.createRouterIf("Kraftwerk-ES", "Gigabit 1/0/8", carnet, _1Gb);
        
        // Remote interfaces
        GenericInterface gn2 = t.createRouterIf("GN2-node-3", "GN2-port-carnet", geant2, _1Gb);
        GenericInterface host = t.createClientIf("CARNET-host-node", "CARNET-host-port", "http://client-domain.carnet.cr/", _1Gb);
        
        // Links
        t.addSpanningTree(p1, gn2, 950, 960);
        t.addSpanningTree(p2, host, 950, 960);
    }
	
    private void cesnet(IntraTopologyBuilder t) {
        GenericInterface p1 = t.createRouterIf("Praha-Cisco3550", "p1", cesnet, _1Gb);
        GenericInterface p2 = t.createRouterIf("Praha-Cisco3550", "p2", cesnet, _1Gb);
        GenericInterface p5 = t.createRouterIf("Praha-Cisco3550", "p5", cesnet, _1Gb);
        GenericInterface p6 = t.createRouterIf("Praha-Cisco3550", "p6", cesnet, _1Gb);
        GenericInterface p11 = t.createRouterIf("Praha-Cisco3550", "p11", cesnet, _1Gb);
        GenericInterface p12 = t.createRouterIf("Praha-Cisco3550", "p12", cesnet, _1Gb);

        GenericInterface p9 = t.createNodeIf("Praha-Cisco3550", "p9", _1Gb);
        GenericInterface p10 = t.createNodeIf("Praha-Force10E300", "p10", _1Gb);
        
        GenericInterface p3 = t.createRouterIf("Praha-Force10E300", "p3", cesnet, _1Gb);

        GenericInterface host1 = t.createClientIf("HOST-node-1", "10.1.1.2", "http://client-domain.cesnet.cz/", _1Gb);
        GenericInterface host2 = t.createClientIf("TRAFFIC-GEN", "10.1.1.3", "http://client-domain.cesnet.cz/", _1Gb);
        GenericInterface host3 = t.createClientIf("Video-server", "10.1.1.10", "http://client-domain.cesnet.cz/", _1Gb);
        GenericInterface host4 = t.createClientIf("Video-server", "10.1.1.11", "http://client-domain.cesnet.cz/", _1Gb);
        
        GenericInterface gn2_1 = t.createRouterIf("GN2-node-2", "GN2-port-cesnet-a", geant2, _1Gb);
        GenericInterface gn2_2 = t.createRouterIf("GN2-node-2", "GN2-port-cesnet-b", geant2, _1Gb);
        GenericInterface pio = t.createRouterIf("PIONIER-node", "PIONIER-port-cesnet", pionier, _1Gb);

        // Links
        t.addSpanningTree(p1, gn2_1, 100, 200);
        t.addSpanningTree(p2, gn2_2, 100, 200);
        t.addSpanningTree(p3, pio, 151, 151);
        
        t.addSpanningTree(p9, p10, 100, 200);
        t.addSpanningTree(p5, host3, 100, 200);
        t.addSpanningTree(p6, host4, 100, 200);
        t.addSpanningTree(p11, host1, 100, 200);
        t.addSpanningTree(p12, host2, 100, 200);
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		June08IntraTopology topo = new June08IntraTopology();
		
		DmHibernateUtil.configure("localhost", "5432", "jra3_4", "jra3", "geant2");
		DmHibernateUtil hibernate = DmHibernateUtil.getInstance();
		
		IntraTopologyBuilder builder = new IntraTopologyBuilder(true);
        builder.setSession(hibernate.currentSession());

        
		Transaction t = hibernate.beginTransaction();
        
		topo.pionier(builder);
		//topo.heanet();
		//topo.grnet();
		//topo.geant2(builder);
		//topo.garr();
		//topo.carnet();
		//topo.cesnet(builder);
		
		t.commit();
		hibernate.closeSession();
	}

}
