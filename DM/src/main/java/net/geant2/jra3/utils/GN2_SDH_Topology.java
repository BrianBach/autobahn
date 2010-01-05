package net.geant2.jra3.utils;

import java.util.List;

import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.StmLink;
import net.geant2.jra3.intradomain.sdh.StmType;

public class GN2_SDH_Topology {

	public final static long _1Gb = (long) 1 * 1000 * 1000 * 1000;
	
	private List<StmLink> links = null;
	private List<SdhDevice> devices = null;
	
	public GN2_SDH_Topology() {
		initGN2();
	}
	
	public void initGN2() {
		IntraTopologyBuilder t = new IntraTopologyBuilder(false);
		
		final String gn2DomainName = "gn2.net";
		
		// Ports
		GenericInterface p1 = t.createRouterIf("Paris-MCC", "p1", gn2DomainName, _1Gb);
		GenericInterface p2 = t.createRouterIf("Paris-MCC", "p2", gn2DomainName, _1Gb);
		GenericInterface p3 = t.createRouterIf("London-MCC", "p3", gn2DomainName, _1Gb);
		GenericInterface p4 = t.createRouterIf("London-MCC", "p4", gn2DomainName, _1Gb);

		// Internal ports
		GenericInterface p5 = t.createNodeIf("Paris-MCC", "p5", _1Gb);
		GenericInterface p6 = t.createNodeIf("Paris-MCC", "p6", _1Gb);
		GenericInterface p7 = t.createNodeIf("London-MCC", "p7", _1Gb);
		GenericInterface p8 = t.createNodeIf("London-MCC", "p8", _1Gb);
		GenericInterface p9 = t.createNodeIf("London-MCC", "p9", _1Gb);
		GenericInterface p10 = t.createNodeIf("PRAGUE-MCC", "p10", _1Gb);
		GenericInterface p11 = t.createNodeIf("PRAGUE-MCC", "p11", _1Gb);
		GenericInterface p12 = t.createNodeIf("PRAGUE-MCC", "p12", _1Gb);
		GenericInterface p13 = t.createNodeIf("AMSTERDAM-MCC", "p13", _1Gb);
		GenericInterface p14 = t.createNodeIf("AMSTERDAM-MCC", "p14", _1Gb);
		GenericInterface p15 = t.createNodeIf("FRANKFURT-MCC", "p15", _1Gb);
		GenericInterface p16 = t.createNodeIf("FRANKFURT-MCC", "p16", _1Gb);

		//Ports in other domains
		GenericInterface port_poznan = t.createRouterIf("Poznan-MCC", "port_poznan", "psnc.pl", _1Gb);
		GenericInterface port_dublin = t.createRouterIf("Dublin-MCC", "port_dublin", "heanet.ie", _1Gb);
		GenericInterface port_athens1 = t.createRouterIf("Athens-MCC", "port_athens1", "grnet.gr", _1Gb);
		GenericInterface port_athens2 = t.createRouterIf("Athens-MCC", "port_athens2", "grnet.gr", _1Gb);

		//Stm links
		StmType type0 = new StmType();
		type0.setName("stm type 0");
		type0.setBandwidth(10);
		
		t.addStmLink(p1, port_athens1, type0);
		t.addStmLink(p2, port_athens2, type0);
		t.addStmLink(p3, port_poznan, type0);
		t.addStmLink(p4, port_dublin, type0);
		t.addStmLink(p5, p7, type0);
		t.addStmLink(p6, p10, type0);
		t.addStmLink(p8, p13, type0);
		t.addStmLink(p9, p15, type0);
		t.addStmLink(p11, p14, type0);
		t.addStmLink(p12, p16, type0);
		
		this.links = t.getStmLinks();
		this.devices = t.getSdhDevices();
	}
	
	public List<StmLink> getLinks() {
		return links;
	}

	public List<SdhDevice> getDevices() {
		return devices;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
