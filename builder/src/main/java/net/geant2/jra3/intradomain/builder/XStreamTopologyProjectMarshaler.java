package net.geant2.jra3.intradomain.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.EthPhysicalPort;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.ethernet.Vlan;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.SdhPort;
import net.geant2.jra3.intradomain.sdh.StmLink;
import net.geant2.jra3.intradomain.topology.EthernetTopology;
import net.geant2.jra3.intradomain.topology.SDHTopology;
import net.geant2.jra3.intradomain.topology.Topology;

import com.thoughtworks.xstream.XStream;
/**
 * Xstream implementation of ProjectMarshaler
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class XStreamTopologyProjectMarshaler implements ProjectMarshaler {
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectMarshaler#marshal(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.String)
	 */
	public void marshal(TopologyProject project, String path)
			throws CannotStoreFileException {
		File file = new File(path);
		FileWriter writer;
		try {
			writer = new FileWriter(path);
			XStream xstream = new XStream();
			aliasesAndImplication(xstream);
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			xstream.toXML(project, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CannotStoreFileException();
		}

	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectMarshaler#unmarshal(java.lang.String)
	 */
	public TopologyProject unmarshal(String path) throws WrongFileFormatException {
		TopologyProject project = null;
		try {
			FileReader reader = new FileReader(new File(path));

			XStream xstream = new XStream();
			aliasesAndImplication(xstream);
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			project = (TopologyProject) xstream.fromXML(reader);
			if (project == null || project.getTopology()==null|| (project.getTopology().getSdhTopology()==null&& project.getTopology().getEthernetTopology()==null))
				return null;
			if (project.getTopology().getSdhTopology() != null)
				sdhTopologyClearing(project.getTopology());
			if (project.getTopology().getEthernetTopology() != null)
				ethTopologyClearing(project.getTopology());

		} catch (FileNotFoundException e) {
			throw new WrongFileFormatException();
			
		} catch (IOException e) {
			throw new WrongFileFormatException();

		}
		return project;
	}
	/**
	 * Configure aliases for xstream marshaling
	 * 
	 * @param xstream object
	 */
	private void aliasesAndImplication(XStream stream){
		stream.alias("topology-project", TopologyProject.class);
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
		stream.alias("rect", Rect.class);
		 
		stream.omitField(TopologyProject.class,"projectMarshaler");
		stream.omitField(TopologyProject.class,"topologyMarshaler");
		stream.omitField(TopologyProject.class,"creator");
		
	}
	/**
	 * Clears ethernet topology from duplicated objects
	 * @param topology topology to remove duplicates
	 */
	private void ethTopologyClearing(Topology topology) {

		clearGenericTopology(topology);

		ArrayList<GenericInterface> genericIfaces = topology.getInterfaces();
		ArrayList<GenericLink> genericLinks = topology.getLinks();
		ArrayList<EthPhysicalPort> interfaces = topology.getEthernetTopology()
				.getPorts();
		ArrayList<EthLink> links = topology.getEthernetTopology().getLinks();
		ArrayList<SpanningTree> spanningTrees = topology.getEthernetTopology()
				.getSpanningTrees();
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

		for (int i = 0; i < ifacesLenght; i++) {
			port = interfaces.get(i);
			for (int j = 0; j < genericIfaceLenght; j++) {
				if (port.getGenericInterface().getName().equals(
						genericIfaces.get(j).getName())) {
						port.setGenericInterface(genericIfaces.get(j));

					break;
				}

			}
		}
		for (int i = 0; i < linksLenght; i++) {
			link = links.get(i);
			for (int j = 0; j < genericLinkLength; j++) {
				if (link.getGenericLink().getStartInterface().getName().equals(
						genericLinks.get(j).getStartInterface().getName())
						&& link.getGenericLink().getEndInterface().getName()
								.equals(
										genericLinks.get(j).getEndInterface()
												.getName())) {
					link.setGenericLink(genericLinks.get(j));
					break;
				}
			}
		}
		for (int i = 0; i < spanningsLength; i++) {
			spanningTree = spanningTrees.get(i);
			for (int j = 0; j < vlanLength; j++) {
				if (spanningTree.getVlan().getName().equals(
						vlans.get(j).getName())) {
					spanningTree.setVlan(vlans.get(j));
					break;
				}
			}
			for (int j = 0; j < linksLenght; j++) {
				link = links.get(j);
				if (spanningTree.getEthLink().getGenericLink()
						.getStartInterface().getName().equals(
								link.getGenericLink().getStartInterface()
										.getName())
						&& spanningTree.getEthLink().getGenericLink()
								.getEndInterface().getName().equals(
										link.getGenericLink().getEndInterface()
												.getName())) {
					spanningTree.setEthLink(link);

				}
			}
		}

	}
	/**
	 * Clears generic topology from duplicated objects
	 * @param topology topology to remove duplicates
	 */
	private void clearGenericTopology(Topology topology) {
		ArrayList<Node> nodes = topology.getNodes();
		ArrayList<GenericInterface> genericInterfaces = topology
				.getInterfaces();
		ArrayList<GenericLink> genericLinks = topology.getLinks();
		int nodesLenght=0;
		if (nodes!=null)
			nodesLenght = nodes.size();
		
		int genericIfaceLength = genericInterfaces.size();
		int genericLinksLength = genericLinks.size();
		GenericInterface genericInterface;
		GenericLink genericLink;

		for (int i = 0; i < genericIfaceLength; i++) {
			genericInterface = genericInterfaces.get(i);
			for (int j = 0; j < nodesLenght; j++)
				if (genericInterface.getNode().getName().equals(
						nodes.get(j).getName())) {
					genericInterface.setNode(nodes.get(j));
					break;
				}
		}
		for (int i = 0; i < genericLinksLength; i++) {
			genericLink = genericLinks.get(i);
			for (int j = 0; j < genericIfaceLength; j++) {
				if (genericLink.getStartInterface().getName().equals(
						genericInterfaces.get(j).getName())) {
					genericLink.setStartInterface(genericInterfaces.get(j));
					continue;
				}
				if (genericLink.getEndInterface().getName().equals(
						genericInterfaces.get(j).getName())) {
					genericLink.setEndInterface(genericInterfaces.get(j));
					continue;
				}

			}
		}
	}
	/**
	 * Clears sdh topology from duplicated objects
	 * @param topology topology to remove duplicates
	 */
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
		int devicesLength = devices.size();
		int interfacesLength = interfaces.size();
		int genericIfacesLength = genericIfaces.size();
		int linksLength = links.size();
		int genericLinksLength = genericLinks.size();
		for (int i = 0; i < devicesLength; i++) {
			device = devices.get(i);
			for (int j = 0; j < nodesLength; j++) {
				if (device.getNode().getName().equals(nodes.get(j).getName())) {
					device.setNode(nodes.get(j));
					break;
				}
			}

		}
		for (int i = 0; i < interfacesLength; i++) {
			port = interfaces.get(i);
			for (int j = 0; j < genericIfacesLength; j++) {
				if (port.getGenericInterface().getName().equals(
						genericIfaces.get(j).getName())) {
					// if (port.getGenericInterface()!= genericIfaces.get(j));
					System.out.println("Cleared interface");
					port.setGenericInterface(genericIfaces.get(j));
					break;
				}
			}
		}
		for (int i = 0; i < linksLength; i++) {
			link = links.get(i);
			for (int j = 0; j < genericLinksLength; j++) {
				if (link.getStmLink().getStartInterface().getName().equals(
						genericLinks.get(j).getStartInterface().getName())
						&& link.getStmLink().getEndInterface().getName()
								.equals(
										genericLinks.get(j).getEndInterface()
												.getName())) {
					link.setStmLink(genericLinks.get(j));
				}
			}
		}
	}

}
