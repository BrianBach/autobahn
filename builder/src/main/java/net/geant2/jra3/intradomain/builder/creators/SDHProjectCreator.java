package net.geant2.jra3.intradomain.builder.creators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.builder.ProjectObjectCreator;
import net.geant2.jra3.intradomain.builder.TopologyProject;
import net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel;
import net.geant2.jra3.intradomain.builder.models.SDHDeviceTableModel;
import net.geant2.jra3.intradomain.builder.models.SDHLinkTableModel;
import net.geant2.jra3.intradomain.builder.models.SDHPortTableModel;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;
import net.geant2.jra3.intradomain.sdh.Och;
import net.geant2.jra3.intradomain.sdh.SdhDevice;
import net.geant2.jra3.intradomain.sdh.SdhPort;
import net.geant2.jra3.intradomain.sdh.StmLink;
import net.geant2.jra3.intradomain.topology.Topology;


/**
 * Creates objects for Ethernet based project
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class SDHProjectCreator  implements ProjectObjectCreator  {
	/**
	 * Table properties models
	 */
	protected Map <String, ObjectPropertiesModel> propertiesModels = new HashMap <String,ObjectPropertiesModel>();
	
	/**
	 * Creates SDHProjectCreator object
	 */
	public SDHProjectCreator() {
		propertiesModels.put(SdhDevice.class.getName(), new SDHDeviceTableModel());
		propertiesModels.put(SdhPort.class.getName(), new SDHPortTableModel());
		propertiesModels.put(StmLink.class.getName(), new SDHLinkTableModel());
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createInterface(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public Object createInterface(TopologyProject project, Object node) {
		
		SdhDevice device = (SdhDevice)node;
		SdhPort port = new SdhPort();
		GenericInterface generic = project.getTopology().createGenericInterface(device.getNode());
		port.setGenericInterface(generic);
		generic.setNode(device.getNode());
		int id = project.getLastInterfaceIdentifier(); 
		generic.setName(MessagesProvider.getMessage("sdh.port")+"-"+id);
		project.getTopology().getSdhTopology().addSdhPort(port);
		return port;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createLink(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object, java.lang.Object)
	 */
	public Object createLink(TopologyProject project, Object start, Object end) {
		SdhPort startPort  = (SdhPort)start;
		SdhPort endPort  = (SdhPort)end;
		StmLink link = new StmLink();
		GenericLink generic = project.getTopology().createGenericLink(startPort.getGenericInterface(), endPort.getGenericInterface());
		link.setStmLink(generic);
		link.setOch(new Och());
		project.getTopology().getSdhTopology().addStmLink(link);
		
		//DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
		//newNode.setUserObject(link);
		//inks.add(newNode);
		return link;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#createNode(net.geant2.jra3.intradomain.builder.TopologyProject)
	 */
	public Object createNode(TopologyProject project) {
		SdhDevice device = new SdhDevice();
		int id = project.getLastNodeIdentifier();
		device.setName(MessagesProvider.getMessage("sdh.node")+"-"+(id));
		Node node = project.getTopology().createNode();
		node.setName(device.getName());
		device.setNode(node);
		
		//DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
		//newNode.setUserObject(device);
		
		//nodes.add(newNode);
		project.getTopology().getSdhTopology().addSdhDevice(device);
		return device;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isInterface(java.lang.Object)
	 */
	public boolean isInterface(Object object) {
		if (object == null)
			return false;
		if (object instanceof SdhPort) 
			return true;
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isLink(java.lang.Object)
	 */
	public boolean isLink(Object object) {
		if (object == null )
			return false;
		if (object instanceof StmLink)
			return true;
		else
			return false;
			
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#isNode(java.lang.Object)
	 */
	public boolean isNode (Object object){
		try{
		if (object == null)
			return false;
		if (object instanceof SdhDevice)
			return true;
		else
			return false;
		}catch (ClassCastException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeElement(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeElement (TopologyProject project,Object object){
		if (object ==null)
			return;
		if (isNode(object)){
			removeNode(project,object);
			return;
		}
		if (isInterface(object)){
			removeInterface(project,object);
			return;
		}
		if (isLink(object)){
			removeLink(project,object);
			return;
		}
			
			
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeInterface(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeInterface(TopologyProject project,Object object) {
		SdhPort port =(SdhPort) object;
		project.getTopology().removeGenericInterface(port.getGenericInterface());
		project.getTopology().getSdhTopology().removeSdhPort(port);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeLink(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeLink(TopologyProject project,Object object) {
		StmLink stmLink = (StmLink) object;
		project.getTopology().removeGenericLink(stmLink.getStmLink());
		project.getTopology().getSdhTopology().removeStmLink(stmLink);
	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#removeNode(net.geant2.jra3.intradomain.builder.TopologyProject, java.lang.Object)
	 */
	public void removeNode(TopologyProject project,Object object) {
		SdhDevice device = (SdhDevice) object;
		project.getTopology().removeGenericNode(device.getNode());
		project.getTopology().getSdhTopology().removeSdhDevice(device);
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
	 * @see net.geant2.jra3.intradomain.builder.ProjectObjectCreator#checkIfLinkExist(net.geant2.jra3.intradomain.topology.Topology, java.lang.Object, java.lang.Object)
	 */
	public boolean checkIfLinkExist(Topology topology, Object start, Object end) {
		SdhPort startPort  = (SdhPort)start;
		SdhPort endPort = (SdhPort)end;
		ArrayList<StmLink>links = topology.getSdhTopology().getLinks();
		int length = links.size();
		boolean found = false;
		StmLink link;
		for (int i=0;i<length;i++){
			link = links.get(i);
			if (link.getStmLink().getStartInterface().getName().equals(startPort.getGenericInterface().getName())||
			link.getStmLink().getStartInterface().getName().equals(endPort.getGenericInterface().getName())||
					link.getStmLink().getEndInterface().getName().equals(startPort.getGenericInterface().getName())||
					link.getStmLink().getEndInterface().getName().equals(endPort.getGenericInterface().getName()))
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
		StmLink stmLink = (StmLink)link;
		GenericLink generic = stmLink.getStmLink();
		GenericInterface start = generic.getStartInterface();
		GenericInterface end = generic.getEndInterface();
		generic.setStartInterface(end);
		generic.setEndInterface(start);
	}
}
