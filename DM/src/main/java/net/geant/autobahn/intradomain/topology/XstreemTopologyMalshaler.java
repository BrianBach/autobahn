package net.geant.autobahn.intradomain.topology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.ethernet.EthLink;
import net.geant.autobahn.intradomain.ethernet.EthPhysicalPort;
import net.geant.autobahn.intradomain.ethernet.SpanningTree;
import net.geant.autobahn.intradomain.ethernet.Vlan;
import net.geant.autobahn.intradomain.sdh.SdhDevice;
import net.geant.autobahn.intradomain.sdh.SdhPort;
import net.geant.autobahn.intradomain.sdh.StmLink;

import com.thoughtworks.xstream.XStream;

public class XstreemTopologyMalshaler implements TopologyMarshaler {

	public void marshal(Topology topology, String path)
			throws CannotStoreFileException {
		File file = new File (path);
		FileWriter writer;
		try {
			writer = new FileWriter(path);
			XStream xstream = new XStream();
			aliasesAndImplication(xstream);
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			xstream.toXML(topology,writer);
			writer.close();
		} catch (IOException e) {
			throw new CannotStoreFileException();
		}
		
		
	}

	private void aliasesAndImplication(XStream stream){
		
		stream.alias("topology", Topology.class);
		stream.alias("sdh-topology", SDHTopology.class);
		stream.alias("ethernet-topology", EthernetTopology.class);
		stream.alias("node", Node.class);
		stream.alias("generic-interface", GenericInterface.class);
		stream.alias("generic-link", GenericLink.class);
		stream.alias("sdh-device", SdhDevice.class);
		stream.alias("sdh-port", SdhPort.class);
		stream.alias("stm-link", StmLink.class);
		stream.alias("eth-port", EthPhysicalPort.class);
		stream.alias("eth-link", EthLink.class);
		stream.alias("vlan", Vlan.class);
		stream.alias("spanning-tree", SpanningTree.class);
		 
		/*
		stream.addImplicitCollection(Topology.class, "nodes");
		stream.addImplicitCollection(Topology.class, "links");
		stream.addImplicitCollection(Topology.class, "interfaces");
		
		stream.addImplicitCollection(EthernetTopology.class, "ports");
		stream.addImplicitCollection(EthernetTopology.class, "links");
		stream.addImplicitCollection(EthernetTopology.class, "vlans");
		stream.addImplicitCollection(EthernetTopology.class, "spanningTrees");
		
		stream.addImplicitCollection(SDHTopology.class, "ports");
		stream.addImplicitCollection(SDHTopology.class, "links");
		stream.addImplicitCollection(SDHTopology.class, "devices");
		
		*/
		
	}
	
	public Topology unmarshal(String path) throws WrongFileFormatException {
		Topology topology=null;
		try {
			FileReader reader = new FileReader(new File(path));
			
			XStream xstream = new XStream();
			aliasesAndImplication(xstream);
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			topology = (Topology) xstream.fromXML(reader);
			
			if (topology.getSdhTopology()!= null)
				sdhTopologyClearing(topology);
			if (topology.getEthernetTopology()!= null)
				ethTopologyClearing(topology);
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WrongFileFormatException ();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new WrongFileFormatException ();
			// TODO Auto-generated catch block
			
		}
		return topology;
	}
	
	private void ethTopologyClearing(Topology topology) {
		 
		clearGenericTopology(topology);
		
		ArrayList<GenericInterface> genericIfaces = topology.getInterfaces();
		ArrayList<GenericLink> genericLinks = topology.getLinks();
		ArrayList<EthPhysicalPort> interfaces = topology.getEthernetTopology().getPorts();
		ArrayList<EthLink> links = topology.getEthernetTopology().getLinks();
		ArrayList<SpanningTree> spanningTrees = topology.getEthernetTopology().getSpanningTrees();
		ArrayList<Vlan> vlans = topology.getEthernetTopology().getVlans();
		 
		int ifacesLenght = interfaces.size();
		int linksLenght = links.size();
		int spanningsLength = spanningTrees.size();
		int vlanLength = vlans.size();
		int genericIfaceLenght = genericIfaces.size();
		int genericLinkLength = genericLinks.size(); 
		EthPhysicalPort port;
		 EthLink link;
		 SpanningTree spanningTree;
		 Vlan vlan;
		 
		 for (int i=0;i<ifacesLenght;i++){
			 port = interfaces.get(i);
			 for (int j=0;j<genericIfaceLenght;j++){
				 if (port.getGenericInterface().getName().equals(genericIfaces.get(j).getName())){
					 if (port.getGenericInterface()!= genericIfaces.get(j));
					 System.out.println ("Cleared interface");
					port.setGenericInterface(genericIfaces.get(j));
					
					break;
				 }
				 
			 }
		 }
		 for (int i=0;i<linksLenght;i++){
			 link = links.get(i);
			 for (int j=0;j<genericLinkLength;j++)
			 {
				 if (link.getGenericLink().getStartInterface().getName().equals(genericLinks.get(j).getStartInterface().getName())&&
						 link.getGenericLink().getEndInterface().getName().equals(genericLinks.get(j).getEndInterface().getName())){
					 link.setGenericLink(genericLinks.get(j));
					 break;
				 }
			 }
		 }
		 for (int i=0;i<spanningsLength;i++){
			 spanningTree = spanningTrees.get(i);
			 for (int j=0;j<vlanLength;j++){
				 if (spanningTree.getVlan().getName().equals(vlans.get(j).getName())){
					spanningTree.setVlan(vlans.get(j));
					break;
				 }
			 }
			 for (int j=0;j<linksLenght;j++){
				 link = links.get(j);
				 if (spanningTree.getEthLink().getGenericLink().getStartInterface().getName().equals(link.getGenericLink().getStartInterface().getName())&&
						 spanningTree.getEthLink().getGenericLink().getEndInterface().getName().equals(link.getGenericLink().getEndInterface().getName())){
					 spanningTree.setEthLink(link);
					 
				 }
			 }
		 }
		 
		}
		 
	private void clearGenericTopology(Topology topology) {
		ArrayList<Node> nodes = topology.getNodes();
		 ArrayList<GenericInterface> genericInterfaces = topology.getInterfaces();
		 ArrayList<GenericLink> genericLinks = topology.getLinks();
		 int nodesLenght =nodes.size();
		 int genericIfaceLength = genericInterfaces.size();
		 int genericLinksLength = genericLinks.size();
		 GenericInterface genericInterface;
		 GenericLink genericLink;
		 
		 for (int i=0;i<genericIfaceLength;i++){
			 genericInterface = genericInterfaces.get(i);
			 for (int j=0;j<nodesLenght;j++)
				 if (genericInterface.getNode().getName().equals(nodes.get(j).getName())){
					 genericInterface.setNode(nodes.get(j));
					 break;
				 }
		 }
		 for (int i=0;i<genericLinksLength;i++){
			 genericLink = genericLinks.get(i);
			 for (int j=0;j<genericIfaceLength;j++){
				 if (genericLink.getStartInterface().getName().equals(genericInterfaces.get(j).getName())){
					 genericLink.setStartInterface(genericInterfaces.get(j));
					 continue;
				 }
				 if (genericLink.getEndInterface().getName().equals(genericInterfaces.get(j).getName())){
					 genericLink.setEndInterface(genericInterfaces.get(j));
					 continue;
				 }
				 
			 }
		 }
	}
	private void sdhTopologyClearing(Topology topology) {
		 clearGenericTopology(topology);
		 List<Node> nodes = topology.getNodes();
		 List<SdhDevice> devices = topology.getSdhTopology().getDevices();
		 List<SdhPort> interfaces = topology.getSdhTopology().getPorts();
		 List<StmLink> links = topology.getSdhTopology().getLinks();
		 List<GenericInterface> genericIfaces = topology.getInterfaces();
		 List<GenericLink> genericLinks = topology.getLinks();
		 SdhDevice device;
		 SdhPort port;
		 StmLink link;
		 int nodesLength = nodes.size();
		 int devicesLength =devices.size();
		 int interfacesLength = interfaces.size();
		 int genericIfacesLength = genericIfaces.size();
		 int linksLength = links.size();
		 int genericLinksLength = genericLinks.size(); 
		 for (int i=0;i<devicesLength;i++ ){
			device = devices.get(i);
			for (int j=0;j<nodesLength;j++){
				if (device.getNode().getName().equals(nodes.get(j).getName())){
					device.setNode (nodes.get(j));
					break;
				}
			}
			
		 }
		 for (int i=0;i<interfacesLength;i++){
			 port = interfaces.get(i);
			 for (int j=0;j<genericIfacesLength;j++){
				 if (port.getGenericInterface().getName().equals(genericIfaces.get(j).getName())){
					 //if (port.getGenericInterface()!= genericIfaces.get(j));
					 System.out.println ("Cleared interface");
					 port.setGenericInterface(genericIfaces.get(j));
					 break;
				 }
			 }
		 } 
		 for (int i=0;i<linksLength;i++){
			 link = links.get(i);
			 for (int j=0;j<genericLinksLength;j++){
				 if (link.getStmLink().getStartInterface().getName().equals(genericLinks.get(j).getStartInterface().getName())&&
						 link.getStmLink().getEndInterface().getName().equals(genericLinks.get(j).getEndInterface().getName())){
					 link.setStmLink(genericLinks.get(j));
				 }
			 }
		 }
	}
}
