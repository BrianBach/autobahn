package net.geant.autobahn.intradomain.sdh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import net.geant.autobahn.intradomain.common.GenericConnection;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.InterfaceType;
import net.geant.autobahn.intradomain.common.Location;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.common.Path;
import net.geant.autobahn.intradomain.common.VersionInfo;


public class SdhTest {
	
	private List <HoVcLink> vcLinks;
	
	/**
	 * Creates a VC-based SDH topology
	 *
	 */
	private void createTopology() {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		// Domain
		SdhDomain domain = new SdhDomain();
		domain.setName("test domain");
		domain.setEquipmentProvider("Alcatel");
		domain.setProvMethod("SDH");
		domain.setDateModified(new Date());
		session.save(domain);
		
		// Location
		Location l = new Location();
		l.setCountry("GR");
		l.setInstitution("GRNET");
		l.setName("A test location in Grnet");
		session.save(l);
		
		// Version
		VersionInfo v = new VersionInfo();
		v.setCreatedBy("George Alyfantis");
		v.setDateCreated(new Date());
		session.save(v);
		
		
		////// Create Sdh devices //////
		
		// Node 1
		Node n1 = new Node();
		n1.setName("node1");
		n1.setIpAddress("195.134.67.11");
		n1.setLocation(l);
		n1.setStatus("up");
		n1.setVendor("Alcatel");
		n1.setVersion(v);
		session.save(n1);
		
		// Sdh Device 1
		SdhDevice device1 = new SdhDevice();
		device1.setName("Sdhdevice1");
		device1.setNsap(12345);
		device1.setSdhDomain(domain);
		device1.setNode(n1);
		session.save(device1);
		
		
		// Node 2
		Node n2 = new Node();
		n2.setName("node2");
		n2.setIpAddress("195.134.67.12");
		n2.setLocation(l);
		n2.setStatus("up");
		n2.setVendor("Alcatel");
		n2.setVersion(v);
		session.save(n2);
		
		// Sdh Device 2
		SdhDevice device2 = new SdhDevice();
		device2.setName("Sdhdevice2");
		device2.setNsap(23456);
		device2.setSdhDomain(domain);
		device2.setNode(n2);
		System.out.println("!!!");
		session.save(device2);
		System.out.println("???");
		
		// Node 3
		Node n3 = new Node();
		n3.setName("node3");
		n3.setIpAddress("195.134.67.13");
		n3.setLocation(l);
		n3.setStatus("up");
		n3.setVendor("Alcatel");
		n3.setVersion(v);
		session.save(n3);
		
		// Sdh Device 3
		SdhDevice device3 = new SdhDevice();
		device3.setName("Sdhdevice3");
		device3.setNsap(34567);
		device3.setSdhDomain(domain);
		device3.setNode(n3);
		session.save(device3);
		
		// Node 4
		Node n4 = new Node();
		n4.setName("node4");
		n4.setIpAddress("195.134.67.14");
		n4.setLocation(l);
		n4.setStatus("up");
		n4.setVendor("Alcatel");
		n4.setVersion(v);
		session.save(n4);
		
		// Sdh Device 4
		SdhDevice device4 = new SdhDevice();
		device4.setName("Sdhdevice4");
		device4.setNsap(45678);
		device4.setSdhDomain(domain);
		device4.setNode(n4);
		session.save(device4);

		// Node 5
		Node n5 = new Node();
		n5.setName("node5");
		n5.setIpAddress("195.134.67.15");
		n5.setLocation(l);
		n5.setStatus("up");
		n5.setVendor("Alcatel");
		n5.setVersion(v);
		session.save(n5);
		
		// Sdh Device 5
		SdhDevice device5 = new SdhDevice();
		device5.setName("Sdhdevice5");
		device5.setNsap(56789);
		device5.setSdhDomain(domain);
		device5.setNode(n5);
		session.save(device5);
		
		// Node 6
		Node n6 = new Node();
		n6.setName("node6");
		n6.setIpAddress("195.134.67.16");
		n6.setLocation(l);
		n6.setStatus("up");
		n6.setVendor("Alcatel");
		n6.setVersion(v);
		session.save(n6);
		
		// Sdh Device 6
		SdhDevice device6 = new SdhDevice();
		device6.setName("Sdhdevice6");
		device6.setNsap(67890);
		device6.setSdhDomain(domain);
		device6.setNode(n6);
		session.save(device6);
		
		////// Create Sdh devices (END) //////
		
		
		InterfaceType it = new InterfaceType();
		it.setSwitchingType("PSC");
		it.setDataEncodingType("data encoding??");
		session.save(it);
		
		
		
		
		///// Create Sdh ports ////
		
		// TODO define interfaces to other domains too
		
		// Port on node 1. 1->4
		GenericInterface g14 = new GenericInterface();
		g14.setName("Generic Interface on node 1. 1->4");
		g14.setBandwidth(622080000);
		g14.setInterfaceType(it);
		g14.setNode(n1);
		g14.setStatus("up");
		g14.setVersion(v);
		g14.setDomainId(null);
		session.save(g14);
		
		SdhPort p14 = new SdhPort();
		p14.setAddress("1-1-17-2");
		p14.setPhyPortType("I-16");
		p14.setGenericInterface(g14);
		session.save(p14);
		
		// Port on node 2. 2->4
		GenericInterface g24 = new GenericInterface();
		g24.setName("Generic Interface on node 2. 2->4");
		g24.setBandwidth(622080000);
		g24.setInterfaceType(it);
		g24.setNode(n2);
		g24.setStatus("up");
		g24.setVersion(v);
		g24.setDomainId(null);
		session.save(g24);
		
		SdhPort p24 = new SdhPort();
		p24.setAddress("2-1-14-2");
		p24.setPhyPortType("I-16");
		p24.setGenericInterface(g24);
		session.save(p24);

		// Port on node 2. 2->6
		GenericInterface g26 = new GenericInterface();
		g26.setName("Generic Interface on node 2. 2->6");
		g26.setBandwidth(622080000);
		g26.setInterfaceType(it);
		g26.setNode(n2);
		g26.setStatus("up");
		g26.setVersion(v);
		g26.setDomainId(null);
		session.save(g26);
		
		SdhPort p26 = new SdhPort();
		p26.setAddress("2-1-14-3");
		p26.setPhyPortType("I-16");
		p26.setGenericInterface(g26);
		session.save(g26);

		// Port on node 3. 3->5
		GenericInterface g35 = new GenericInterface();
		g35.setName("Generic Interface on node 3. 3->5");
		g35.setBandwidth(622080000);
		g35.setInterfaceType(it);
		g35.setNode(n3);
		g35.setStatus("up");
		g35.setVersion(v);
		g35.setDomainId(null);
		session.save(g35);
		
		
		SdhPort p35 = new SdhPort();
		p35.setAddress("5-1-12-1");
		p35.setPhyPortType("I-16");
		p35.setGenericInterface(g35);
		session.save(p35);
		
		
		// Port on node 4. 4->1
		GenericInterface g41 = new GenericInterface();
		g41.setName("Generic Interface on node 4. 4->1");
		g41.setBandwidth(622080000);
		g41.setInterfaceType(it);
		g41.setNode(n4);
		g41.setStatus("up");
		g41.setVersion(v);
		g41.setDomainId(null);
		session.save(g41);
		
		SdhPort p41 = new SdhPort();
		p41.setAddress("2-1-14-4");
		p41.setPhyPortType("I-16");
		p41.setGenericInterface(g41);
		session.save(p41);
		

		// Port on node 4. 4->2
		GenericInterface g42 = new GenericInterface();
		g42.setName("Generic Interface on node 4. 4->2");
		g42.setBandwidth(622080000);
		g42.setInterfaceType(it);
		g42.setNode(n4);
		g42.setStatus("up");
		g42.setVersion(v);
		g42.setDomainId(null);
		session.save(g42);
		
		SdhPort p42 = new SdhPort();
		p42.setAddress("2-1-14-5");
		p42.setPhyPortType("I-16");
		p42.setGenericInterface(g42);
		session.save(p42);

		// Port on node 4. 4->5
		GenericInterface g45 = new GenericInterface();
		g45.setName("Generic Interface on node 4. 4->5");
		g45.setBandwidth(995328*10000);
		g45.setInterfaceType(it);
		g45.setNode(n4);
		g45.setStatus("up");
		g45.setVersion(v);
		g45.setDomainId(null);
		session.save(g45);
		
		SdhPort p45 = new SdhPort();
		p45.setAddress("2-1-14-6");
		p45.setPhyPortType("I-64");
		p45.setGenericInterface(g45);
		session.save(p45);
		
		// Port on node 4. 4->6
		GenericInterface g46 = new GenericInterface();
		g46.setName("Generic Interface on node 4. 4->5");
		g46.setBandwidth(995328*10000);
		g46.setInterfaceType(it);
		g46.setNode(n4);
		g46.setStatus("up");
		g46.setVersion(v);
		g46.setDomainId(null);
		session.save(g46);
		
		SdhPort p46 = new SdhPort();
		p46.setAddress("2-1-14-7");
		p46.setPhyPortType("I-64");
		p46.setGenericInterface(g46);
		session.save(p46);

		// Port on node 5. 5->3
		GenericInterface g53 = new GenericInterface();
		g53.setName("Generic Interface on node 5. 5->3");
		g53.setBandwidth(622080000);
		g53.setInterfaceType(it);
		g53.setNode(n5);
		g53.setStatus("up");
		g53.setVersion(v);
		g53.setDomainId(null);
		session.save(g53);
		
		SdhPort p53 = new SdhPort();
		p53.setAddress("1-1-11-1");
		p53.setPhyPortType("I-16");
		p53.setGenericInterface(g53);
		session.save(p53);

		
		// Port on node 5. 5->4
		GenericInterface g54 = new GenericInterface();
		g54.setName("Generic Interface on node 5. 5->4");
		g54.setBandwidth(995328*10000);
		g54.setInterfaceType(it);
		g54.setNode(n5);
		g54.setStatus("up");
		g54.setVersion(v);
		g54.setDomainId(null);
		session.save(g54);
		
		SdhPort p54 = new SdhPort();
		p54.setAddress("1-1-11-2");
		p54.setPhyPortType("I-64");
		p54.setGenericInterface(g54);
		session.save(p54);
		
		// Port on node 5. 5->6
		GenericInterface g56 = new GenericInterface();
		g56.setName("Generic Interface on node 5. 5->6");
		g56.setBandwidth(995328*10000);
		g56.setInterfaceType(it);
		g56.setNode(n5);
		g56.setStatus("up");
		g56.setVersion(v);
		g56.setDomainId(null);
		session.save(g56);
		
		SdhPort p56 = new SdhPort();
		p56.setAddress("1-1-11-3");
		p56.setPhyPortType("I-64");
		p56.setGenericInterface(g56);
		session.save(p56);
		
		// Port on node 6. 6->2
		GenericInterface g62 = new GenericInterface();
		g62.setName("Generic Interface on node 6. 6->2");
		g62.setBandwidth(622080000);
		g62.setInterfaceType(it);
		g62.setNode(n6);
		g62.setStatus("up");
		g62.setVersion(v);
		g62.setDomainId(null);
		session.save(g62);
		
		SdhPort p62 = new SdhPort();
		p62.setAddress("1-2-11-1");
		p62.setPhyPortType("I-16");
		p62.setGenericInterface(g62);
		session.save(p62);

		// Port on node 6. 6->4
		GenericInterface g64 = new GenericInterface();
		g64.setName("Generic Interface on node 6. 6->4");
		g64.setBandwidth(995328*10000);
		g64.setInterfaceType(it);
		g64.setNode(n6);
		g64.setStatus("up");
		g64.setVersion(v);
		g64.setDomainId(null);
		session.save(g64);
		
		SdhPort p64 = new SdhPort();
		p64.setAddress("1-2-11-1");
		p64.setPhyPortType("I-64");
		p64.setGenericInterface(g64);
		session.save(p64);
		
		// Port on node 6. 6->5
		GenericInterface g65 = new GenericInterface();
		g65.setName("Generic Interface on node 6. 6->5");
		g65.setBandwidth(995328*10000);
		g65.setInterfaceType(it);
		g65.setNode(n6);
		g65.setStatus("up");
		g65.setVersion(v);
		g65.setDomainId(null);
		session.save(g65);
		
		SdhPort p65 = new SdhPort();
		p65.setAddress("1-2-11-2");
		p65.setPhyPortType("I-64");
		p65.setGenericInterface(g65);
		session.save(p65);
		
		///// Create Sdh ports (END) ////
		
		
		/////  Create STM types //////
		StmType stm1 = new StmType();
		stm1.setName("STM-1");
		stm1.setBandwidth(155520000);
		session.save(stm1);
		
		StmType stm4 = new StmType();
		stm4.setName("STM-4");
		stm4.setBandwidth(622080000);
		session.save(stm4);
		
		StmType stm16 = new StmType();
		stm16.setName("STM-16");
		stm16.setBandwidth(248832*10000);
		session.save(stm16);
		
		StmType stm64 = new StmType();
		stm64.setName("STM-64");
		stm64.setBandwidth(995328*10000);
		session.save(stm64);
		
		StmType stm256 = new StmType();
		stm256.setName("STM-256");
		stm256.setBandwidth(3981312*10000);
		session.save(stm256);
		
		/////  Create STM types (END) //////
		
		///// Create optical channels //////
		
		Och och1 = new Och();
		och1.setPayload("payload format 1??");
		och1.setStatus("up");
		session.save(och1);
		
		Och och2 = new Och();
		och2.setPayload("payload format 2??");
		och2.setStatus("up");
		session.save(och2);
		
		///// Create optical channels (END) //////
		
		
		///// Create STM links /////
		GenericLink link141 = new GenericLink();
		link141.setStartInterface(g14);
		link141.setEndInterface(g41);
		link141.setProtection(true);
		link141.setPropDelay(0.001);
		link141.setDirection("bidirectional");
		link141.setVersion(v);
		session.save(link141);
		
		// STM link from device 1 to device 4 on channel 1
		StmLink stmlink141 = new StmLink();
		stmlink141.setStmLink(link141);
		stmlink141.setOch(och1);
		stmlink141.setStmType(stm4);
		stmlink141.setStatus("up");
		session.save(stmlink141);
		
		GenericLink link142 = new GenericLink();
		link142.setStartInterface(g14);
		link142.setEndInterface(g41);
		link142.setProtection(true);
		link142.setPropDelay(0.001);
		link142.setDirection("bidirectional");
		link142.setVersion(v);
		session.save(link142);
		
		// STM link from device 1 to device 4 on channel 2
		StmLink stmlink142 = new StmLink();
		stmlink142.setStmLink(link142);
		stmlink142.setOch(och2);
		stmlink142.setStmType(stm4);
		stmlink142.setStatus("up");
		session.save(stmlink142);
		
		
		GenericLink link241 = new GenericLink();
		link241.setStartInterface(g24);
		link241.setEndInterface(g42);
		link241.setProtection(true);
		link241.setPropDelay(0.001);
		link241.setDirection("bidirectional");
		link241.setVersion(v);
		session.save(link241);
		
		// STM link from device 2 to device 4 on channel 1
		StmLink stmlink241 = new StmLink();
		stmlink241.setStmLink(link241);
		stmlink241.setOch(och1);
		stmlink241.setStmType(stm4);
		stmlink241.setStatus("up");
		//session.save(stmlink241);
		
		GenericLink link242 = new GenericLink();
		link242.setStartInterface(g24);
		link242.setEndInterface(g42);
		link242.setProtection(true);
		link242.setPropDelay(0.001);
		link242.setDirection("bidirectional");
		link242.setVersion(v);
		session.save(link242);
		
		// STM link from device 2 to device 4 on channel 2
		StmLink stmlink242 = new StmLink();
		stmlink242.setStmLink(link242);
		stmlink242.setOch(och2);
		stmlink242.setStmType(stm4);
		stmlink242.setStatus("up");
		session.save(stmlink242);
		
		
		GenericLink link261 = new GenericLink();
		link261.setStartInterface(g26);
		link261.setEndInterface(g62);
		link261.setProtection(true);
		link261.setPropDelay(0.001);
		link261.setDirection("bidirectional");
		link261.setVersion(v);
		session.save(link261);
		
		
		// STM link from device 2 to device 6 on channel 1
		StmLink stmlink261 = new StmLink();
		stmlink261.setStmLink(link261);
		stmlink261.setOch(och1);
		stmlink261.setStmType(stm4);
		stmlink261.setStatus("up");
		session.save(stmlink261);
		
		GenericLink link262 = new GenericLink();
		link262.setStartInterface(g26);
		link262.setEndInterface(g62);
		link262.setProtection(true);
		link262.setPropDelay(0.001);
		link262.setDirection("bidirectional");
		link262.setVersion(v);
		session.save(link262);
		
		// STM link from device 2 to device 6 on channel 2
		StmLink stmlink262 = new StmLink();
		stmlink262.setStmLink(link262);
		stmlink262.setOch(och2);
		stmlink262.setStmType(stm4);
		stmlink262.setStatus("up");
		session.save(stmlink262);
		
		
		GenericLink link351 = new GenericLink();
		link351.setStartInterface(g35);
		link351.setEndInterface(g53);
		link351.setProtection(true);
		link351.setPropDelay(0.001);
		link351.setDirection("bidirectional");
		link351.setVersion(v);
		session.save(link351);
		
		// STM link from device 3 to device 5 on channel 1
		StmLink stmlink351 = new StmLink();
		stmlink351.setStmLink(link351);
		stmlink351.setOch(och1);
		stmlink351.setStmType(stm4);
		stmlink351.setStatus("up");
		session.save(stmlink351);
		
		GenericLink link352 = new GenericLink();
		link352.setStartInterface(g35);
		link352.setEndInterface(g53);
		link352.setProtection(true);
		link352.setPropDelay(0.001);
		link352.setDirection("bidirectional");
		link352.setVersion(v);
		session.save(link352);
		
		// STM link from device 3 to device 5 on channel 2
		StmLink stmlink352 = new StmLink();
		stmlink352.setStmLink(link352);
		stmlink352.setOch(och2);
		stmlink352.setStmType(stm4);
		stmlink352.setStatus("up");
		session.save(stmlink352);
		
		
		GenericLink link411 = new GenericLink();
		link411.setStartInterface(g41);
		link411.setEndInterface(g14);
		link411.setProtection(true);
		link411.setPropDelay(0.001);
		link411.setDirection("bidirectional");
		link411.setVersion(v);
		session.save(link411);
		
		// STM link from device 4 to device 1 on channel 1
		StmLink stmlink411 = new StmLink();
		stmlink411.setStmLink(link411);
		stmlink411.setOch(och1);
		stmlink411.setStmType(stm4);
		stmlink411.setStatus("up");
		session.save(stmlink411);
		
		GenericLink link412 = new GenericLink();
		link412.setStartInterface(g41);
		link412.setEndInterface(g14);
		link412.setProtection(true);
		link412.setPropDelay(0.001);
		link412.setDirection("bidirectional");
		link412.setVersion(v);
		session.save(link412);
		
		// STM link from device 4 to device 1 on channel 2
		StmLink stmlink412 = new StmLink();
		stmlink412.setStmLink(link412);
		stmlink412.setOch(och2);
		stmlink412.setStmType(stm16);
		stmlink412.setStatus("up");
		session.save(stmlink412);
		
		
		GenericLink link421 = new GenericLink();
		link421.setStartInterface(g42);
		link421.setEndInterface(g24);
		link421.setProtection(true);
		link421.setPropDelay(0.001);
		link421.setDirection("bidirectional");
		link421.setVersion(v);
		session.save(link421);
		
		// STM link from device 4 to device 2 on channel 1
		StmLink stmlink421 = new StmLink();
		stmlink421.setStmLink(link421);
		stmlink421.setOch(och1);
		stmlink421.setStmType(stm4);
		stmlink421.setStatus("up");
		session.save(stmlink421);
		
		GenericLink link422 = new GenericLink();
		link422.setStartInterface(g42);
		link422.setEndInterface(g24);
		link422.setProtection(true);
		link422.setPropDelay(0.001);
		link422.setDirection("bidirectional");
		link422.setVersion(v);
		session.save(link422);
		
		// STM link from device 4 to device 2 on channel 2
		StmLink stmlink422 = new StmLink();
		stmlink422.setStmLink(link422);
		stmlink422.setOch(och2);
		stmlink422.setStmType(stm4);
		stmlink422.setStatus("up");
		session.save(stmlink422);
		
		
		GenericLink link451 = new GenericLink();
		link451.setStartInterface(g45);
		link451.setEndInterface(g54);
		link451.setProtection(true);
		link451.setPropDelay(0.001);
		link451.setDirection("bidirectional");
		link451.setVersion(v);
		session.save(link451);
		
		// STM link from device 4 to device 5 on channel 1
		StmLink stmlink451 = new StmLink();
		stmlink451.setStmLink(link451);
		stmlink451.setOch(och1);
		stmlink451.setStmType(stm16);
		stmlink451.setStatus("up");
		
		GenericLink link452 = new GenericLink();
		link452.setStartInterface(g45);
		link452.setEndInterface(g54);
		link452.setProtection(true);
		link452.setPropDelay(0.001);
		link452.setDirection("bidirectional");
		link452.setVersion(v);
		session.save(link452);
		
		// STM link from device 4 to device 5 on channel 2
		StmLink stmlink452 = new StmLink();
		stmlink452.setStmLink(link452);
		stmlink452.setOch(och2);
		stmlink452.setStmType(stm16);
		stmlink452.setStatus("up");
		session.save(stmlink452);
		
		
		GenericLink link461 = new GenericLink();
		link461.setStartInterface(g46);
		link461.setEndInterface(g64);
		link461.setProtection(true);
		link461.setPropDelay(0.001);
		link461.setDirection("bidirectional");
		link461.setVersion(v);
		session.save(link461);
		
		// STM link from device 4 to device 6 on channel 1
		StmLink stmlink461 = new StmLink();
		stmlink461.setStmLink(link461);
		stmlink461.setOch(och1);
		stmlink461.setStmType(stm64);
		stmlink461.setStatus("up");
		session.save(stmlink461);
		
		GenericLink link462 = new GenericLink();
		link462.setStartInterface(g46);
		link462.setEndInterface(g64);
		link462.setProtection(true);
		link462.setPropDelay(0.001);
		link462.setDirection("bidirectional");
		link462.setVersion(v);
		session.save(link462);
		
		// STM link from device 4 to device 6 on channel 2
		StmLink stmlink462 = new StmLink();
		stmlink462.setStmLink(link462);
		stmlink462.setOch(och2);
		stmlink462.setStmType(stm64);
		stmlink462.setStatus("up");
		session.save(stmlink462);
		
		
		GenericLink link531 = new GenericLink();
		link531.setStartInterface(g53);
		link531.setEndInterface(g35);
		link531.setProtection(true);
		link531.setPropDelay(0.001);
		link531.setDirection("bidirectional");
		link531.setVersion(v);
		session.save(link531);
		
		// STM link from device 5 to device 4 on channel 1
		StmLink stmlink531 = new StmLink();
		stmlink531.setStmLink(link531);
		stmlink531.setOch(och1);
		stmlink531.setStmType(stm64);
		stmlink531.setStatus("up");
		session.save(stmlink531);
		
		GenericLink link532 = new GenericLink();
		link532.setStartInterface(g53);
		link532.setEndInterface(g35);
		link532.setProtection(true);
		link532.setPropDelay(0.001);
		link532.setDirection("bidirectional");
		link532.setVersion(v);
		session.save(link532);
		
		// STM link from device 5 to device 4 on channel 2
		StmLink stmlink532 = new StmLink();
		stmlink532.setStmLink(link532);
		stmlink532.setOch(och2);
		stmlink532.setStmType(stm64);
		stmlink532.setStatus("up");
		session.save(stmlink532);
		
		
		GenericLink link541 = new GenericLink();
		link541.setStartInterface(g54);
		link541.setEndInterface(g45);
		link541.setProtection(true);
		link541.setPropDelay(0.001);
		link541.setDirection("bidirectional");
		link541.setVersion(v);
		session.save(link541);
		
		// STM link from device 5 to device 4 on channel 1
		StmLink stmlink541 = new StmLink();
		stmlink541.setStmLink(link541);
		stmlink541.setOch(och1);
		stmlink541.setStmType(stm64);
		stmlink541.setStatus("up");
		
		GenericLink link542 = new GenericLink();
		link542.setStartInterface(g54);
		link542.setEndInterface(g45);
		link542.setProtection(true);
		link542.setPropDelay(0.001);
		link542.setDirection("bidirectional");
		link542.setVersion(v);
		session.save(link542);
		
		// STM link from device 5 to device 4 on channel 2
		StmLink stmlink542 = new StmLink();
		stmlink542.setStmLink(link542);
		stmlink542.setOch(och2);
		stmlink542.setStmType(stm64);
		stmlink542.setStatus("up");
		session.save(stmlink542);
		
		
		GenericLink link561 = new GenericLink();
		link561.setStartInterface(g56);
		link561.setEndInterface(g65);
		link561.setProtection(true);
		link561.setPropDelay(0.001);
		link561.setDirection("bidirectional");
		link561.setVersion(v);
		session.save(link561);
		
		// STM link from device 5 to device 6 on channel 1
		StmLink stmlink561 = new StmLink();
		stmlink561.setStmLink(link561);
		stmlink561.setOch(och1);
		stmlink561.setStmType(stm64);
		stmlink561.setStatus("up");
		
		GenericLink link562 = new GenericLink();
		link562.setStartInterface(g56);
		link562.setEndInterface(g65);
		link562.setProtection(true);
		link562.setPropDelay(0.001);
		link562.setDirection("bidirectional");
		link562.setVersion(v);
		session.save(link562);
		
		// STM link from device 5 to device 6 on channel 2
		StmLink stmlink562 = new StmLink();
		stmlink562.setStmLink(link562);
		stmlink562.setOch(och2);
		stmlink562.setStmType(stm64);
		stmlink562.setStatus("up");
		session.save(stmlink562);
		
		
		GenericLink link621 = new GenericLink();
		link621.setStartInterface(g62);
		link621.setEndInterface(g26);
		link621.setProtection(true);
		link621.setPropDelay(0.001);
		link621.setDirection("bidirectional");
		link621.setVersion(v);
		session.save(link621);
		
		// STM link from device 6 to device 2 on channel 1
		StmLink stmlink621 = new StmLink();
		stmlink621.setStmLink(link621);
		stmlink621.setOch(och1);
		stmlink621.setStmType(stm4);
		stmlink621.setStatus("up");
		session.save(stmlink621);
		
		GenericLink link622 = new GenericLink();
		link622.setStartInterface(g62);
		link622.setEndInterface(g26);
		link622.setProtection(true);
		link622.setPropDelay(0.001);
		link622.setDirection("bidirectional");
		link622.setVersion(v);
		session.save(link622);
		
		// STM link from device 6 to device 2 on channel 2
		StmLink stmlink622 = new StmLink();
		stmlink622.setStmLink(link622);
		stmlink622.setOch(och2);
		stmlink622.setStmType(stm4);
		stmlink622.setStatus("up");
		session.save(stmlink622);
		
		
		GenericLink link641 = new GenericLink();
		link641.setStartInterface(g64);
		link641.setEndInterface(g46);
		link641.setProtection(true);
		link641.setPropDelay(0.001);
		link641.setDirection("bidirectional");
		link641.setVersion(v);
		session.save(link641);
		
		// STM link from device 6 to device 4 on channel 1
		StmLink stmlink641 = new StmLink();
		stmlink641.setStmLink(link641);
		stmlink641.setOch(och1);
		stmlink641.setStmType(stm64);
		stmlink641.setStatus("up");
		session.save(stmlink641);
		
		GenericLink link642 = new GenericLink();
		link642.setStartInterface(g64);
		link642.setEndInterface(g46);
		link642.setProtection(true);
		link642.setPropDelay(0.001);
		link642.setDirection("bidirectional");
		link642.setVersion(v);
		session.save(link642);
		
		// STM link from device 6 to device 2 on channel 2
		StmLink stmlink642 = new StmLink();
		stmlink642.setStmLink(link642);
		stmlink642.setOch(och2);
		stmlink642.setStmType(stm64);
		stmlink642.setStatus("up");
		session.save(stmlink642);
		
		
		GenericLink link651 = new GenericLink();
		link651.setStartInterface(g65);
		link651.setEndInterface(g56);
		link651.setProtection(true);
		link651.setPropDelay(0.001);
		link651.setDirection("bidirectional");
		link651.setVersion(v);
		session.save(link651);
		
		// STM link from device 6 to device 5 on channel 1
		StmLink stmlink651 = new StmLink();
		stmlink651.setStmLink(link651);
		stmlink651.setOch(och1);
		stmlink651.setStmType(stm64);
		stmlink651.setStatus("up");
		session.save(stmlink651);
		
		GenericLink link652 = new GenericLink();
		link652.setStartInterface(g65);
		link652.setEndInterface(g56);
		link652.setProtection(true);
		link652.setPropDelay(0.001);
		link652.setDirection("bidirectional");
		link652.setVersion(v);
		session.save(link652);
		
		// STM link from device 6 to device 5 on channel 2
		StmLink stmlink652 = new StmLink();
		stmlink652.setStmLink(link652);
		stmlink652.setOch(och2);
		stmlink652.setStmType(stm64);
		stmlink652.setStatus("up");
		session.save(stmlink652);
		
		///// Create STM links (END) /////
		
		//// Create VLAN tags ////
		VlanTag vt10 = new VlanTag();
		vt10.setName("VLAN10");
		vt10.setDateModified(new Date());
		session.save(vt10);
		
		VlanTag vt20 = new VlanTag();
		vt20.setName("VLAN20");
		vt20.setDateModified(new Date());
		session.save(vt20);
		
		VlanTag vt30 = new VlanTag();
		vt30.setName("VLAN30");
		vt30.setDateModified(new Date());
		session.save(vt30);
		
		VlanTag vt40 = new VlanTag();
		vt40.setName("VLAN40");
		vt40.setDateModified(new Date());
		session.save(vt40);
		
		VlanTag vt50 = new VlanTag();
		vt50.setName("VLAN50");
		vt50.setDateModified(new Date());
		session.save(vt50);
		
		//// Create VLAN tags (END) ////
		
		
		
		//// Create VC Groups ////
		
		Path p142= new Path();
		p142.setName("Path 1->4->2");
		p142.setStatus("up");
		p142.setVersion(v);
		session.save(p142);
		
		GenericConnection connection142 = new GenericConnection();
		connection142.setPath(p142);
		connection142.setVersion(v);
		session.save(connection142);
		
		HoVcGroup vcgroup142 = new HoVcGroup();
		vcgroup142.setName("VC group 1->4->2");
		vcgroup142.setVlanTag(vt10);
		vcgroup142.setHoVcGroup(connection142);
		session.save(vcgroup142);
		
		
		Path p1462= new Path();
		p1462.setName("Path 1->4->6->2");
		p1462.setStatus("up");
		p1462.setVersion(v);
		session.save(p1462);
		
		GenericConnection connection1462 = new GenericConnection();
		connection1462.setPath(p1462);
		connection1462.setVersion(v);
		session.save(connection1462);
		
		HoVcGroup vcgroup1462 = new HoVcGroup();
		vcgroup1462.setName("VC group 1->4->6->2");
		vcgroup1462.setVlanTag(vt20);
		vcgroup1462.setHoVcGroup(connection1462);
		session.save(vcgroup1462);
		
		
		Path p241= new Path();
		p241.setName("Path 2->4->1");
		p241.setStatus("up");
		p241.setVersion(v);
		session.save(p241);
		
		GenericConnection connection241 = new GenericConnection();
		connection241.setPath(p241);
		connection241.setVersion(v);
		session.save(connection241);
		
		HoVcGroup vcgroup241 = new HoVcGroup();
		vcgroup241.setName("VC group 2->4->1");
		vcgroup241.setVlanTag(vt10);
		vcgroup241.setHoVcGroup(connection241);
		session.save(vcgroup241);
		
		
		Path p2641= new Path();
		p2641.setName("Path 2->6->4->1");
		p2641.setStatus("up");
		p2641.setVersion(v);
		session.save(p2641);
		
		GenericConnection connection2641 = new GenericConnection();
		connection2641.setPath(p2641);
		connection2641.setVersion(v);
		session.save(connection2641);
		
		HoVcGroup vcgroup2641 = new HoVcGroup();
		vcgroup2641.setName("VC group 2->6->4->1");
		vcgroup2641.setVlanTag(vt20);
		vcgroup2641.setHoVcGroup(connection2641);
		session.save(vcgroup2641);
		
		
		Path p14653= new Path();
		p14653.setName("Path 1->4->6->5->3");
		p14653.setStatus("up");
		p14653.setVersion(v);
		session.save(p14653);
		
		GenericConnection connection14653 = new GenericConnection();
		connection14653.setPath(p14653);
		connection14653.setVersion(v);
		session.save(connection14653);
		
		HoVcGroup vcgroup14653 = new HoVcGroup();
		vcgroup14653.setName("VC group 1->4->6->5->3");
		vcgroup14653.setVlanTag(vt30);
		vcgroup14653.setHoVcGroup(connection14653);
		session.save(vcgroup14653);

		
		Path p142653 = new Path();
		p142653.setName("Path 1->4->2->6->5->3");
		p142653.setStatus("up");
		p142653.setVersion(v);
		session.save(p142653);
		
		GenericConnection connection142653 = new GenericConnection();
		connection142653.setPath(p142653);
		connection142653.setVersion(v);
		session.save(connection142653);
		
		HoVcGroup vcgroup142653 = new HoVcGroup();
		vcgroup142653.setName("VC group 1->4->2->6->5->3");
		vcgroup142653.setVlanTag(vt40);
		vcgroup142653.setHoVcGroup(connection142653);
		session.save(vcgroup142653);
		
		
		Path p35641= new Path();
		p35641.setName("Path 3->5->6->4->1");
		p35641.setStatus("up");
		p35641.setVersion(v);
		session.save(p35641);
		
		GenericConnection connection35641 = new GenericConnection();
		connection35641.setPath(p35641);
		connection35641.setVersion(v);
		session.save(connection35641);
		
		HoVcGroup vcgroup35641 = new HoVcGroup();
		vcgroup35641.setName("VC group 3->5->6->4->1");
		vcgroup35641.setVlanTag(vt30);
		vcgroup35641.setHoVcGroup(connection35641);
		session.save(vcgroup35641);
		
		
		Path p356241= new Path();
		p356241.setName("Path 3->5->6->2->4->1");
		p356241.setStatus("up");
		p356241.setVersion(v);
		session.save(p356241);
		
		GenericConnection connection356241 = new GenericConnection();
		connection356241.setPath(p356241);
		connection356241.setVersion(v);
		session.save(connection356241);
		
		HoVcGroup vcgroup356241 = new HoVcGroup();
		vcgroup356241.setName("VC group 3->5->6->2->4->1");
		vcgroup356241.setVlanTag(vt40);
		vcgroup356241.setHoVcGroup(connection356241);
		session.save(vcgroup356241);
		
		
		Path p2653= new Path();
		p2653.setName("Path 2->6->5->3");
		p2653.setStatus("up");
		p2653.setVersion(v);
		session.save(p2653);
		
		GenericConnection connection2653 = new GenericConnection();
		connection2653.setPath(p2653);
		connection2653.setVersion(v);
		session.save(connection2653);
		
		HoVcGroup vcgroup2653 = new HoVcGroup();
		vcgroup2653.setName("VC group 2->6->5->3");
		vcgroup2653.setVlanTag(vt50);
		vcgroup2653.setHoVcGroup(connection2653);
		session.save(vcgroup2653);
		
		
		Path p3562= new Path();
		p3562.setName("Path 3->5->6->2");
		p3562.setStatus("up");
		p3562.setVersion(v);
		session.save(p3562);
		
		GenericConnection connection3562 = new GenericConnection();
		connection3562.setPath(p3562);
		connection3562.setVersion(v);
		session.save(connection3562);
		
		HoVcGroup vcgroup3562 = new HoVcGroup();
		vcgroup3562.setName("VC group 3->5->6->2");
		vcgroup3562.setVlanTag(vt50);
		vcgroup3562.setHoVcGroup(connection3562);
		session.save(vcgroup3562);

		//// Create VC Groups (END) ////
		
		
		
		//// Create VC types ////
		
		HoVcType vc11 = new HoVcType();
		vc11.setName("VC-11");
		vc11.setBandwidth(1664000);
		vc11.setPayload(1600000);
		session.save(vc11);
		
		HoVcType vc12 = new HoVcType();
		vc12.setName("VC-12");
		vc12.setBandwidth(2240000);
		vc12.setPayload(2176000);
		session.save(vc12);
		
		HoVcType vc2 = new HoVcType();
		vc2.setName("VC-2");
		vc2.setBandwidth(6848000);
		vc2.setPayload(6784000);
		session.save(vc2);
		
		HoVcType vc3 = new HoVcType();
		vc3.setName("VC-3");
		vc3.setBandwidth(48960000);
		vc3.setPayload(48384000);
		session.save(vc3);
		
		HoVcType vc4 = new HoVcType();
		vc4.setName("VC-4");
		vc4.setBandwidth(150336000);
		vc4.setPayload(149760000);
		session.save(vc4);
		
		HoVcType vc4_4c = new HoVcType();
		vc4_4c.setName("VC-4-4c");
		vc4_4c.setBandwidth(601344000);
		vc4_4c.setPayload(599040000);
		session.save(vc4_4c);
		
		HoVcType vc4_16c = new HoVcType();
		vc4_16c.setName("VC-4-16c");
		vc4_16c.setBandwidth(2405376*1000);
		vc4_16c.setPayload(2396160*1000);
		session.save(vc4_16c);
		
		HoVcType vc4_64c = new HoVcType();
		vc4_64c.setName("VC-4-64c");
		vc4_64c.setBandwidth(9621504*1000);
		vc4_64c.setPayload(9584640*1000);
		session.save(vc4_64c);
		
		HoVcType vc4_256c = new HoVcType();
		vc4_256c.setName("VC-4-256c");
		vc4_256c.setBandwidth(38486016*1000);
		vc4_256c.setPayload(38338560*1000);
		session.save(vc4_256c);

		//// Create VC types (END) ////
		
		
		this.vcLinks = new ArrayList<HoVcLink>();
		
		
		//// Create VC links ////
		
		HoVcLink vclink14_142 = new HoVcLink();
		vclink14_142.setHoVcGroup(vcgroup142);
		vclink14_142.setHoVcType(vc4);
		vclink14_142.setStmLink(stmlink141);
		vclink14_142.setTimeSlot(1);
		vclink14_142.setGroupSequence(1);
		vclink14_142.setStatus("up");
		vclink14_142.setDateModified(new Date());
		session.save(vclink14_142);
		this.vcLinks.add(vclink14_142);
		
		HoVcLink vclink42_142 = new HoVcLink();
		vclink42_142.setHoVcGroup(vcgroup142);
		vclink42_142.setHoVcType(vc4);
		vclink42_142.setStmLink(stmlink421);
		vclink42_142.setTimeSlot(1);
		vclink42_142.setGroupSequence(2);
		vclink42_142.setStatus("up");
		vclink42_142.setDateModified(new Date());
		session.save(vclink42_142);
		this.vcLinks.add(vclink42_142);
		
		
		
		HoVcLink vclink14_1462 = new HoVcLink();
		vclink14_1462.setHoVcGroup(vcgroup1462);
		vclink14_1462.setHoVcType(vc3);
		vclink14_1462.setStmLink(stmlink142);
		vclink14_1462.setTimeSlot(1);
		vclink14_1462.setGroupSequence(1);
		vclink14_1462.setStatus("up");
		vclink14_1462.setDateModified(new Date());
		session.save(vclink14_1462);
		this.vcLinks.add(vclink14_1462);
		
		HoVcLink vclink46_1462 = new HoVcLink();
		vclink46_1462.setHoVcGroup(vcgroup1462);
		vclink46_1462.setHoVcType(vc3);
		vclink46_1462.setStmLink(stmlink462);
		vclink46_1462.setTimeSlot(1);
		vclink46_1462.setGroupSequence(2);
		vclink46_1462.setStatus("up");
		vclink46_1462.setDateModified(new Date());
		session.save(vclink46_1462);
		this.vcLinks.add(vclink46_1462);
		
		HoVcLink vclink62_1462 = new HoVcLink();
		vclink62_1462.setHoVcGroup(vcgroup1462);
		vclink62_1462.setHoVcType(vc3);
		vclink62_1462.setStmLink(stmlink622);
		vclink62_1462.setTimeSlot(1);
		vclink62_1462.setGroupSequence(3);
		vclink62_1462.setStatus("up");
		vclink62_1462.setDateModified(new Date());
		session.save(vclink62_1462);
		this.vcLinks.add(vclink62_1462);
		
		
		
		HoVcLink vclink14_14653 = new HoVcLink();
		vclink14_14653.setHoVcGroup(vcgroup14653);
		vclink14_14653.setHoVcType(vc4);
		vclink14_14653.setStmLink(stmlink141);
		vclink14_14653.setTimeSlot(1);
		vclink14_14653.setGroupSequence(1);
		vclink14_14653.setStatus("up");
		vclink14_14653.setDateModified(new Date());
		session.save(vclink14_14653);
		this.vcLinks.add(vclink14_14653);
		
		HoVcLink vclink46_14653 = new HoVcLink();
		vclink46_14653.setHoVcGroup(vcgroup14653);
		vclink46_14653.setHoVcType(vc4);
		vclink46_14653.setStmLink(stmlink461);
		vclink46_14653.setTimeSlot(1);
		vclink46_14653.setGroupSequence(2);
		vclink46_14653.setStatus("up");
		vclink46_14653.setDateModified(new Date());
		session.save(vclink46_14653);
		this.vcLinks.add(vclink46_14653);
		
		HoVcLink vclink65_14653 = new HoVcLink();
		vclink65_14653.setHoVcGroup(vcgroup14653);
		vclink65_14653.setHoVcType(vc4);
		vclink65_14653.setStmLink(stmlink651);
		vclink65_14653.setTimeSlot(1);
		vclink65_14653.setGroupSequence(3);
		vclink65_14653.setStatus("up");
		vclink65_14653.setDateModified(new Date());
		session.save(vclink65_14653);
		this.vcLinks.add(vclink65_14653);
		
		HoVcLink vclink53_14653 = new HoVcLink();
		vclink53_14653.setHoVcGroup(vcgroup14653);
		vclink53_14653.setHoVcType(vc4);
		vclink53_14653.setStmLink(stmlink531);
		vclink53_14653.setTimeSlot(1);
		vclink53_14653.setGroupSequence(4);
		vclink53_14653.setStatus("up");
		vclink53_14653.setDateModified(new Date());
		session.save(vclink53_14653);
		this.vcLinks.add(vclink53_14653);
		
		
		HoVcLink vclink14_142653 = new HoVcLink();
		vclink14_142653.setHoVcGroup(vcgroup142653);
		vclink14_142653.setHoVcType(vc3);
		vclink14_142653.setStmLink(stmlink142);
		vclink14_142653.setTimeSlot(1);
		vclink14_142653.setGroupSequence(1);
		vclink14_142653.setStatus("up");
		vclink14_142653.setDateModified(new Date());
		session.save(vclink14_142653);
		this.vcLinks.add(vclink14_142653);
		
		HoVcLink vclink42_142653 = new HoVcLink();
		vclink42_142653.setHoVcGroup(vcgroup142653);
		vclink42_142653.setHoVcType(vc3);
		vclink42_142653.setStmLink(stmlink422);
		vclink42_142653.setTimeSlot(1);
		vclink42_142653.setGroupSequence(2);
		vclink42_142653.setStatus("up");
		vclink42_142653.setDateModified(new Date());
		session.save(vclink42_142653);
		this.vcLinks.add(vclink42_142653);
		
		HoVcLink vclink26_142653 = new HoVcLink();
		vclink26_142653.setHoVcGroup(vcgroup142653);
		vclink26_142653.setHoVcType(vc3);
		vclink26_142653.setStmLink(stmlink262);
		vclink26_142653.setTimeSlot(1);
		vclink26_142653.setGroupSequence(3);
		vclink26_142653.setStatus("up");
		vclink26_142653.setDateModified(new Date());
		session.save(vclink26_142653);
		this.vcLinks.add(vclink26_142653);
		
		HoVcLink vclink65_142653 = new HoVcLink();
		vclink65_142653.setHoVcGroup(vcgroup142653);
		vclink65_142653.setHoVcType(vc3);
		vclink65_142653.setStmLink(stmlink652);
		vclink65_142653.setTimeSlot(1);
		vclink65_142653.setGroupSequence(4);
		vclink65_142653.setStatus("up");
		vclink65_142653.setDateModified(new Date());
		session.save(vclink65_142653);
		this.vcLinks.add(vclink65_142653);
		
		HoVcLink vclink53_142653 = new HoVcLink();
		vclink53_142653.setHoVcGroup(vcgroup142653);
		vclink53_142653.setHoVcType(vc3);
		vclink53_142653.setStmLink(stmlink532);
		vclink53_142653.setTimeSlot(1);
		vclink53_142653.setGroupSequence(5);
		vclink53_142653.setStatus("up");
		vclink53_142653.setDateModified(new Date());
		session.save(vclink53_142653);
		this.vcLinks.add(vclink53_142653);
		
		
		HoVcLink vclink26_2653 = new HoVcLink();
		vclink26_2653.setHoVcGroup(vcgroup2653);
		vclink26_2653.setHoVcType(vc4_4c);
		vclink26_2653.setStmLink(stmlink261);
		vclink26_2653.setTimeSlot(1);
		vclink26_2653.setGroupSequence(1);
		vclink26_2653.setStatus("up");
		vclink26_2653.setDateModified(new Date());
		session.save(vclink26_2653);
		this.vcLinks.add(vclink26_2653);
		
		HoVcLink vclink65_2653 = new HoVcLink();
		vclink65_2653.setHoVcGroup(vcgroup2653);
		vclink65_2653.setHoVcType(vc4_4c);
		vclink65_2653.setStmLink(stmlink651);
		vclink65_2653.setTimeSlot(1);
		vclink65_2653.setGroupSequence(2);
		vclink65_2653.setStatus("up");
		vclink65_2653.setDateModified(new Date());
		session.save(vclink65_2653);
		this.vcLinks.add(vclink65_2653);
		
		HoVcLink vclink53_2653 = new HoVcLink();
		vclink53_2653.setHoVcGroup(vcgroup2653);
		vclink53_2653.setHoVcType(vc4_4c);
		vclink53_2653.setStmLink(stmlink531);
		vclink53_2653.setTimeSlot(1);
		vclink53_2653.setGroupSequence(3);
		vclink53_2653.setStatus("up");
		vclink53_2653.setDateModified(new Date());
		session.save(vclink53_2653);
		this.vcLinks.add(vclink53_2653);
		
		
		//// Create VC links (END) ////
		
		tr.commit();
		session.close();
		
	}
	
	
	private void storeVcLinks() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		for (int i=0;i<this.vcLinks.size();i++) {
			HoVcLink vclink = this.vcLinks.get(i);
			//System.out.println(vclink.getHoVcGroup().getHoVcGroup().getVersion().getCreatedBy());
			System.out.println(vclink.getHoVcGroup().getHoVcGroup().getPath().getName());
			//session.persist(vclink);
			//session.save(vclink.getStmLink().getStmLink());
			//session.save(vclink.getHoVcGroup().getHoVcGroup());
			session.save(vclink);
			
		}
		
		tr.commit();
		
		session.close();
		
		
	}
	
	
	private void abstractTopology() {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String hql = "from HoVcGroup";
		Query query = session.createQuery(hql);
		List vcGroups = query.list();
		
		for (int i=0;i<vcGroups.size();i++) {
			HoVcGroup vcGroup = (HoVcGroup)vcGroups.get(i);
			System.out.println(vcGroup.getName());
			
			
			query = session.createQuery("from HoVcLink where ho_vc_group_id=" + vcGroup.getHoVcGroup().getGenericConnectionId() + " order by group_sequence");
			List vcLinks = query.list();
			System.out.println(vcLinks.size());
			for (int j=0;j<vcLinks.size();j++) {
				HoVcLink vcLink = (HoVcLink)vcLinks.get(j);
				System.out.println("> " + vcLink.getGroupSequence());
			}
			
			if (vcLinks.size() == 0) {
				continue;
			}
			
			Node startNode = ((HoVcLink)vcLinks.get(0)).getStmLink().getStmLink().getStartInterface().getNode();
			Node endNode = ((HoVcLink)vcLinks.get(vcLinks.size()-1)).getStmLink().getStmLink().getEndInterface().getNode();
			
			System.out.println("Start Node: " + startNode.getName());
			System.out.println("End Node: " + endNode.getName());
			
			
			
			
		}
		
		session.close();
		
		
	}
	
	
	private void findEdgeNodes() {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from SdhDevice");
		List sdhDevices = query.list();
		
		
		
		query = session.createQuery("from GenericInterface");
		List genericInterfaces = query.list();
		
		List <Node>edgeNodes = new ArrayList<Node>();
		for (int i=0;i<genericInterfaces.size();i++) {
			GenericInterface genericInterface = (GenericInterface)genericInterfaces.get(i);
			Node node = genericInterface.getNode();
			String domainId = genericInterface.getDomainId();
			
			if (domainId == null) {
				if (edgeNodes.indexOf(node) < 0) {
					edgeNodes.add(node);
				}
			}
			
		}
				
		List <SdhDevice>edgeSdhDevices = new ArrayList<SdhDevice>();
		for (int i=0;i<sdhDevices.size();i++) {
			SdhDevice sdhDevice = (SdhDevice)sdhDevices.get(i);
			Node node = sdhDevice.getNode();
			if (edgeNodes.indexOf(node) >= 0) {
				edgeSdhDevices.add(sdhDevice);
			}
			
		}
		
		
		
		for (int i=0;i<edgeSdhDevices.size();i++) {
			SdhDevice d = edgeSdhDevices.get(i);
			System.out.println(">> " + d.getName());
		}

		session.close();
		
	}
	
	
	
	public static void main(String [] args) {
		
		SdhTest st = new SdhTest();
		//st.createTopology();
		
		//st.abstractTopology();
		st.findEdgeNodes();
		
		
		
		/*for (int i=0;i<st.vcLinks.size();i++) {
			HoVcLink vclink = st.vcLinks.get(i);
			System.out.print(vclink.getHoVcGroup().getName() + "\t");
			System.out.print(vclink.getGroupSequence() + "\t");
			System.out.print(vclink.getStmLink().getStmLink().getStartInterface().getNode().getName() + "\t");
			System.out.print(vclink.getStmLink().getStmLink().getEndInterface().getNode().getName() + "\t");
			System.out.print(vclink.getStmLink().getStmType().getName() + "\t");
			System.out.print(vclink.getHoVcType().getName() + "\t");
			
			
			System.out.println();
		}
		*/
		
		
		//st.storeVcLinks();
		
		
	}


	
	

}
