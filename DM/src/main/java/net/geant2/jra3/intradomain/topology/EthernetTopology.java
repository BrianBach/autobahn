package net.geant2.jra3.intradomain.topology;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.EthPhysicalPort;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.ethernet.Vlan;

@XStreamAlias ("ethernet-topology")
public class EthernetTopology {
	
	/**
	 * List of created ports
	 */
	@XStreamImplicit
	ArrayList <EthPhysicalPort> ports = new ArrayList<EthPhysicalPort>();
	/**
	 * List of created links
	 */
	@XStreamImplicit
	ArrayList <EthLink> links = new ArrayList<EthLink>();
	/**
	 * List of spanning trees
	 */
	@XStreamImplicit
	ArrayList<SpanningTree> spanningTrees = new ArrayList<SpanningTree>();
	/**
	 * List of vlans
	 */
	@XStreamImplicit
	ArrayList<Vlan> vlans = new ArrayList<Vlan>();
	
	
	int lastVlanId = 0;
	int lastSpanningId =0;

	public void addEthPort (EthPhysicalPort port){
		if (port!= null)
			ports.add(port);
	}

	public void removeEthPort (EthPhysicalPort port){
		if (port!= null)
			ports.remove(port);
	}

	public void addEthLink (EthLink link){
		if (link!= null)
			links.add(link);
	}

	public void removeEthLink (EthLink link){
		if (link!= null) 
			links.remove(link);
	}
	
	public void addSpanningTree (SpanningTree spanning){
		if (spanning!= null)
			spanningTrees.add(spanning);
	}

	public void removeSpanningTree (SpanningTree spanning){
		if (spanning!= null){
			vlans.remove(spanning.getVlan());
			spanningTrees.remove(spanning);
		}
		
	}
	
	public void addVlan (Vlan vlan){
		if (vlan!= null)
			vlans.add(vlan);
	}

	public void removeVlan (Vlan vlan){
		if (vlan!= null)
			vlans.remove(vlan);
	}

	public ArrayList<EthPhysicalPort> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<EthPhysicalPort> ports) {
		this.ports = ports;
	}

	public ArrayList<EthLink> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<EthLink> links) {
		this.links = links;
	}

	public ArrayList<SpanningTree> getSpanningTrees() {
		return spanningTrees;
	}

	public void setSpanningTrees(ArrayList<SpanningTree> spanningTrees) {
		this.spanningTrees = spanningTrees;
	}

	public ArrayList<Vlan> getVlans() {
		return vlans;
	}

	public void setVlans(ArrayList<Vlan> vlans) {
		this.vlans = vlans;
	}
	public SpanningTree createSpanningTree (String vlanName,String state, int vlanLow, int vlanHeight,long cost, EthLink  link){
		SpanningTree tree = new SpanningTree ();
		Vlan vlan = new Vlan ();
		vlan.setName(vlanName);
		vlan.setLowNumber(vlanLow);
		vlan.setHighNumber(vlanHeight);
		addVlan(vlan);
		tree.setCost(cost);
		tree.setEthLink(link);
		tree.setVlan(vlan);
		tree.setState(state);
		addSpanningTree(tree);
		return tree;
	}
	public int getLastVlanId (){
		return lastVlanId++;
	}
	public int getLastSpanningId (){
		return lastSpanningId++;
	}
	public List getSpanningTreesForLink (EthLink link){
		List list = new ArrayList ();
		int length = spanningTrees.size();
		SpanningTree tree =null;
		for (int i=0;i<length;i++){
			tree = spanningTrees.get(i);
			if (tree.getEthLink()==link){
				list.add(tree);
			}
		}
		return list;
	}
	public boolean checkVlanName (String name){
		int length = vlans.size();
		boolean found = false;
		for (int i=0;i<length;i++){
			if (vlans.get(i).getName().equals(name)){
				found = true;
				break;
			}
		}
		return found;
	}

	public void removeSpanningsTreeForLink(Object object) {
		 if (object instanceof EthLink) {
			EthLink link = (EthLink)object;
			int length = spanningTrees.size();
			SpanningTree tree;
			List<SpanningTree> toRemove = new ArrayList<SpanningTree>();
			for (int i=0;i<length;i++){
				tree= spanningTrees.get(i);
				if (tree.getEthLink()==link)
					toRemove.add(tree);
			}
			 length = toRemove.size();
			 for (int i=0;i<length;i++){
				 removeSpanningTree(toRemove.get(i));
			 }
			 
		 }
	}
}
