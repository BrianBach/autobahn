package net.geant2.jra3.intradomain.builder;

import net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel;
import net.geant2.jra3.intradomain.topology.Topology;

/**
 * Interface for creator of project objects
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface ProjectObjectCreator {
	/**
	 * Creates node object valid for project template type
	 * @param project project on which creator operates
	 * @return	created node object
	 */
	public Object createNode (TopologyProject project);
	/**
	 * Creates interface object valid for project template type
	 * @param project project on which creator operates
	 * @param node node object for which interface is added
	 * @return created interface
	 */
	public Object createInterface (TopologyProject project,Object node);
	/**
	 * Creates link object valid for project template type
	 * @param project project on which creator operates
	 * @param start_interface object identified as start interface of the link
	 * @param end_interface object identified as end interface  of the link
	 * @return	created link object
	 */	
	public Object createLink (TopologyProject project,Object start_interface, Object end_interface);
	/**
	 * Removes interface object from project
	 * @param project project on which creator operates
	 * @param object interface to remove
	 */
	public void removeInterface (TopologyProject project, Object object);
	/**
	 * Removes node object from project
	 * @param project project on which creator operates
	 * @param object node to remove
	 */
	public void removeNode (TopologyProject project, Object object);
	/**
	 * Removes link object from project
	 * @param project project on which creator operates
	 * @param object link to remove
	 */
	public void removeLink (TopologyProject project, Object object);
	/**
	 * Removes element object from project. Method check the type of object and invoke valid method.
	 * @param project project on which creator operates
	 * @param object element to remove
	 */
	public void removeElement (TopologyProject project, Object object);
	/**
	 * Checks if object is instance of node 
	 * @param object object to check
	 * @return	true if object is instance of node
	 */
	public boolean isNode (Object object);
	/**
	 * Checks if object is instance of interface 
	 * @param object object to check
	 * @return	true if object is instance of node
	 */
	public boolean isInterface (Object object);
	/**
	 * Checks if object is instance of link 
	 * @param object object to check
	 * @return	true if object is instance of node
	 */
	public boolean isLink (Object object);
	/**
	 * Gets valid object table properties model
	 * @param model model name	
	 * @return valid table properties model 
	 */
	public ObjectPropertiesModel getPropertiesModel (String model);
	/**
	 * Checks if link between two interfaces exist
	 * 
	 * @param topology topology to check
	 * @param intrface1	start interface of checked link
	 * @param interface2 end interface of checked link
	 * @return
	 */
	public boolean checkIfLinkExist (Topology topology, Object intrface1, Object interface2);
	/**
	 * Switches direction of the link
	 * @param link	link object
	 */
	public void switchLinkDirection(Object link);
}
