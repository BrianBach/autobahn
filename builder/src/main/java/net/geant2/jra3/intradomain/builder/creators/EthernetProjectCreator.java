package net.geant2.jra3.intradomain.builder.creators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.builder.ProjectObjectCreator;
import net.geant2.jra3.intradomain.builder.TopologyProject;
import net.geant2.jra3.intradomain.builder.models.EthLinkTableModel;
import net.geant2.jra3.intradomain.builder.models.EthPortTableModel;
import net.geant2.jra3.intradomain.builder.models.NodeTableModel;
import net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.EthPhysicalPort;
import net.geant2.jra3.intradomain.topology.Topology;

/**
 * Creates objects for Ethernet based project
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class EthernetProjectCreator implements ProjectObjectCreator{
	/**
	 * Table properties models
	 */
	protected Map <String, ObjectPropertiesModel> propertiesModels = new HashMap <String,ObjectPropertiesModel>();
	/**
	 * Creates EthernetProjectCreator
	 */
	public EthernetProjectCreator(){
		super();
		propertiesModels.put(Node.class.getName(), new NodeTableModel());
		propertiesModels.put(EthPhysicalPort.class.getName(),new  EthPortTableModel());
		propertiesModels.put(EthLink.class.getName(),new  EthLinkTableModel());	
	}
	
	/*public EthernetProjectCreator(EthernetProjectCreator template) {
			this();
	}*/
	
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createInterface(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public Object createInterface(TopologyProject project,Object node) {
		Node nod = (Node)node;
		GenericInterface interf = project.getTopology().createGenericInterface(nod);
		EthPhysicalPort port =  new EthPhysicalPort();
		port.setGenericInterface(interf);
		port.setInterfaceName(MessagesProvider.getMessage("eth.port")+"-"+project.getLastInterfaceIdentifier());
		port.getGenericInterface().setName(port.getInterfaceName());
		project.getTopology().getEthernetTopology().addEthPort(port);
		return port;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createLink(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object, java.lang.Object)
	 */
	public Object createLink(TopologyProject project,Object start, Object end) {
		EthPhysicalPort startPort = (EthPhysicalPort) start;
		EthPhysicalPort endPort = (EthPhysicalPort) end;
		GenericLink link = project.getTopology().createGenericLink(startPort.getGenericInterface(), endPort.getGenericInterface());		
		EthLink ethLink = new EthLink();
		ethLink.setGenericLink(link);
		project.getTopology().getEthernetTopology().addEthLink(ethLink);
		return ethLink;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createNode(net.geant2.jra3.intradomain.builder.TopologyProject)
	 */
	public Object createNode(TopologyProject project) {
		Node node = project.getTopology().createNode();
		node.setName(MessagesProvider.getMessage("eth.node")+"-"+project.getLastNodeIdentifier());
		return node;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isInterface(java.lang.Object)
	 */
	public boolean isInterface(Object object) {
		if (object == null)return false;
		if (object instanceof EthPhysicalPort) 
			return true;
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isLink(java.lang.Object)
	 */
	public boolean isLink(Object object) {
		if (object == null)return false;
		if (object instanceof EthLink) 
			return true;
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isNode(java.lang.Object)
	 */
	public boolean isNode(Object object) {
		if (object == null)
			return false;
		if ( object instanceof Node) 
			return true;
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeInterface(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeInterface(TopologyProject project,Object object) {
		EthPhysicalPort port =(EthPhysicalPort) object;
		project.getTopology().removeGenericInterface(port.getGenericInterface());
		project.getTopology().getEthernetTopology().removeEthPort(port);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeLink(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeLink(TopologyProject project,Object object) {
		EthLink ethLink = (EthLink) object;
		project.getTopology().removeGenericLink(ethLink.getGenericLink());
		project.getTopology().getEthernetTopology().removeEthLink(ethLink);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeNode(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeNode(TopologyProject project,Object object) {
		Node node = (Node)object;
		project.getTopology().removeGenericNode(node);;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#getPropertiesModel(java.lang.String)
	 */
	public ObjectPropertiesModel getPropertiesModel(String model) {
		return propertiesModels.get(model);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeElement(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeElement(TopologyProject project, Object object) {
		if (isNode(object)){
			removeNode(project, object);
			return;
		}
		if (isLink(object)){
			removeLink(project, object);
			return;
		}
		if (isInterface(object)){
			removeInterface(project, object);
			return;
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#checkIfLinkExist(net.geant2.jra3.intradomain.topology.Topology, java.lang.Object, java.lang.Object)
	 */
	public boolean checkIfLinkExist(Topology topology, Object start, Object end) {
		EthPhysicalPort startPort  = (EthPhysicalPort)start;
		EthPhysicalPort endPort = (EthPhysicalPort)end;
		ArrayList<EthLink>links = topology.getEthernetTopology().getLinks();
		int length = links.size();
		boolean found = false;
		EthLink link;
		for (int i=0;i<length;i++){
			link = links.get(i);
			if (link.getGenericLink().getStartInterface().getName().equals(startPort.getGenericInterface().getName())||
					link.getGenericLink().getStartInterface().getName().equals(endPort.getGenericInterface().getName())||
							link.getGenericLink().getEndInterface().getName().equals(startPort.getGenericInterface().getName())||
							link.getGenericLink().getEndInterface().getName().equals(endPort.getGenericInterface().getName()))
					{
						found= true;
						break;
					}
		}
		return found;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#switchLinkDirection(java.lang.Object)
	 */
	public void switchLinkDirection(Object link) {
		EthLink ethLink = (EthLink)link;
		GenericLink generic = ethLink.getGenericLink();
		GenericInterface start = generic.getStartInterface();
		GenericInterface end = generic.getEndInterface();
		generic.setStartInterface(end);
		generic.setEndInterface(start);
	}
	/*
	public List getLinksForPort (Topology topology,Object node){
		if (node == null || !isNode(node))
			return null;
		List portsForNode = new ArrayList();
		List <EthPhysicalPort> ports = topology.getEthernetTopology().getPorts();
		int length = ports.size();
		Node device = (Node)node;
		for (int i=0;i<length;i++){
			if (ports.get(i).getGenericInterface().getNode().equals(node)){
				portsForNode.add(ports.get(i));
			}
		}
		return portsForNode;
	}
	
	public List getInterfacesForNode (Topology topology,Object node){
		if (node == null || !isNode(node))
			return null;
		List portsForNode = new ArrayList();
		List <EthPhysicalPort> ports = topology.getEthernetTopology().getPorts();
		int length = ports.size();
		Node device = (Node)node;
		for (int i=0;i<length;i++){
			if (ports.get(i).getGenericInterface().getNode().equals(node)){
				portsForNode.add(ports.get(i));
			}
		}
		return portsForNode;
	}*/

}
