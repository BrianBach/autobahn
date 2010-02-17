package net.geant.autobahn.intradomain.topology;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Location;
import net.geant.autobahn.intradomain.common.Node;


import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Wraper class for different topologies types
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
@XStreamAlias ("topology")
public class Topology {
	
	/**
	 * Generic interfaces
	 */
	@XStreamImplicit
	ArrayList <GenericInterface> interfaces = new ArrayList <GenericInterface>();
	/**
	 * Generic nodes
	 */
	@XStreamImplicit
	ArrayList <Node> nodes = new ArrayList <Node>();
	/**
	 * Generic links
	 */
	@XStreamImplicit
	ArrayList <GenericLink> links = new ArrayList<GenericLink>();
	
	/**
	 * SDH topology specific data
	 */
	@XStreamAlias("sdh-topology")
	SDHTopology sdhTopology;
	/**
	 * Ethernet topology specific data
	 */
	
	@XStreamAlias("ethernet-topology")
	EthernetTopology ethernetTopology;
	
	
	public ArrayList<GenericInterface> getInterfaces() {
		return interfaces;
	}

	public void setInerfaces(ArrayList<GenericInterface> inerfaces) {
		this.interfaces = inerfaces;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<GenericLink> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<GenericLink> links) {
		this.links = links;
	}

	public Topology (){
	};
	
	public Topology (SDHTopology topology){
		this.sdhTopology=topology;
	}
	public Topology (EthernetTopology topology){
		this.ethernetTopology=topology;
	}
	
	public SDHTopology getSdhTopology() {
		return sdhTopology;
	}
	public void setSdhTopology(SDHTopology sdhTopology) {
		this.sdhTopology = sdhTopology;
	}
	public EthernetTopology getEthernetTopology() {
		return ethernetTopology;
	}
	public void setEthernetTopology(EthernetTopology ethernetTopology) {
		this.ethernetTopology = ethernetTopology;
	}
	public Node createNode (){
		Node node = new Node();
		node.setLocation(new Location());
		nodes.add(node);
		return node;
}
public void  removeGenericNode ( Node node){
	if (node ==null)
		return;
	int length = interfaces.size();
	List<GenericInterface> toRemove = new ArrayList<GenericInterface>();
	for (int i=0;i<length;i++){
		if (interfaces.get(i).getNode().getName().equals(node.getName()))
		{	
			toRemove.add(interfaces.get(i));
		}
	}
	length = toRemove.size();
	for (int i=0;i<length;i++)
		removeGenericInterface(toRemove.get(i));
	nodes.remove(node);
}
	
	
	public GenericInterface createGenericInterface (Node node){
			GenericInterface genericInterface = new GenericInterface();
			genericInterface.setNode(node);
			interfaces.add(genericInterface);
			return genericInterface;
	}
	public void  removeGenericInterface ( GenericInterface genericInterface){
		if (genericInterface==null)
			return;
		interfaces.remove(genericInterface);
		int length = links.size();
		List<GenericLink> toRemove = new ArrayList<GenericLink>();
		for (int i=0;i<length;i++){
			if (links.get(i).getStartInterface().getName().equals(genericInterface.getName())){
				toRemove.add(links.get(i));	
				continue;
			}
			if (links.get(i).getEndInterface().getName().equals(genericInterface.getName())){
				toRemove.add(links.get(i));
				continue;
			}
			
		}
		length = toRemove.size();
		for (int i=0;i<length;i++)
			removeGenericLink(links.get(i));
	}
	public GenericLink createGenericLink (GenericInterface start, GenericInterface end){
		GenericLink genericlink = new GenericLink();
		genericlink.setStartInterface(start);
		genericlink.setEndInterface(end);
		links.add(genericlink);
		return genericlink;
	}
	public void  removeGenericLink (GenericLink genericLink){
		if (links ==  null)
			return;
		links.remove(genericLink);
	}

	public void setInterfaces(ArrayList<GenericInterface> interfaces) {
		this.interfaces = interfaces;
	}
	
	
	
}
