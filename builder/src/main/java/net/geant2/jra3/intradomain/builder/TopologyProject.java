package net.geant2.jra3.intradomain.builder;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel;
import net.geant2.jra3.intradomain.topology.Topology;
import net.geant2.jra3.intradomain.topology.TopologyMarshaler;

/**
 * Abstract class for modelling the project in AutoBAHN Topology Builder 
 * @author Lucas Dolata <ldolata@gmail.com>
 *
 */
@XStreamAlias ("topology-project")
public class TopologyProject{
	
	/**
	 * ProjectMarshaler use for storing and restoring project data
	 */
	@XStreamOmitField
	public static ProjectMarshaler projectMarshaler;
	/**
	 * ProjectMarshaler use for storing and restoring project data
	 */
	@XStreamOmitField
	public static TopologyMarshaler topologyMarshaler;
	
	
	/**
	 * Project creator use to create object dependent on architecture
	 */
	@XStreamOmitField
	public ProjectObjectCreator creator;
	
	/**
	 * Properties table model for Project specific objects;
	 * 
	 */
	protected Map <String, Rect> graphProperties;
	
	/**
	 * Project name
	 */
	@XStreamAlias ("name")
	protected String name;
	/**
	 * Project path
	 */
	@XStreamAlias ("path")
	protected String path;
	/**
	 * Project description
	 */
	@XStreamAlias ("description")
	protected String description;
	/**
	 * Project version
	 */
	@XStreamAlias ("version")
	protected Version version;
	/**
	 * Sequencer for node name
	 */
	@XStreamAlias ("last-node-identifier")
	protected int lastNodeIdentifier = 0;
	/**
	 * Sequencer for link name
	 */
	@XStreamAlias ("last-link-identifier")
	protected int lastLinkIdentifier = 0;
	/**
	 * Sequencer interface name
	 */
	@XStreamAlias ("last-interface-identifier")
	protected int lastInterfaceIdentifier = 0;
	/**
	 * Topology wrapper
	 */
	@XStreamAlias ("topology")
	protected Topology topology = new Topology ();
		
	
	/**
	 * Creates TopologyProject object
	 */
	public TopologyProject(){
		graphProperties= new HashMap<String, Rect> ();
	}

	/**
	 * Gets project marshaler
	 * @return configured project marshaler
	 */
	public static ProjectMarshaler getMarshaler() {
		return projectMarshaler;
	}
	/**
	 * Set project and topology marshaler
	 * 
	 * @param projectMarshaler project marshaler
	 * @param topologyMarshaler topology marshaler
	 */
	public static void setMarshalers(ProjectMarshaler projectMarshaler,TopologyMarshaler topologyMarshaler) {
		TopologyProject.projectMarshaler = projectMarshaler;
		TopologyProject.topologyMarshaler=topologyMarshaler;
	}
	/**
	 * Gets last node identifier
	 * @return 
	 */
	public int getLastNodeIdentifier() {
		return lastNodeIdentifier++;
	}
	/**
	 * Gets last link identifier
	 * @return last link identifier
	 */
	public int getLastLinkIdentifier() {
		return lastLinkIdentifier++;
	}
	/**
	 * Gets last interface identifier
	 * @return  last interface identifier
	 */
	public int getLastInterfaceIdentifier() {
		return lastInterfaceIdentifier++;
	}
	/**
	 * Gets project name
	 * @return  project name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets project name
	 * @param  project name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets project path
	 * @return  project path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Sets project path
	 * @param  project path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * Gets project description
	 * @return project description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets project description
	 * @param  project description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets project version
	 * @return  project version
	 */
	public Version getVersion() {
		return version;
	}
	/**
	 * Sets project version
	 * @param  project version
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	/**
	 * Creates valid node object for the type of project template
	 * 
	 * @return created node
	 */
	public Object createNode (){
		if (creator==null)
			return null;
		return creator.createNode(this);
	}
	/**
	 * Creates and adds valid link object for the type of project template
	 * 
	 * @return created link
	 */
	public  Object createLink (Object start, Object end){
		if (creator == null)
			return null;
		return creator.createLink(this, start, end);
	}
	/**
	 * Creates and adds valid interface object for the type of project template
	 * 
	 * @return created interface
	 */
	public  Object createInterface (Object node){
		if (creator==null)
			return null;
		return creator.createInterface(this, node);
	}
	
	/**
	 * Creates and adds valid interface object for the type of project template
	 * 
	 * @return created interface
	 */
	public  void removeInterface (Object object){
		if (creator==null)
			return;
		creator.removeInterface(this, object);
	}
	/**
	 * Removes node object from the project
	 * 
	 * @return node to remove
	 */
	public  void removeNode (Object object){
		if (creator==null)
			return;
		creator.removeNode(this, object);
	}
	/**
	 * Removes link object from the project
	 * 
	 * @return link to remove
	 */
	public  void removeLink (Object object){
		if (creator==null)
			return;
		creator.removeLink(this, object);
	}
	/**
	 * Removes element from project. Check type of object and chose valid method
	 * 
	 * @return element to remove
	 */
	public  void removeElement (Object object){
		if (creator==null)
			return;
		creator.removeElement(this, object);
	}
	/**
	 * Checks if object is instance of node
	 * @param object object to check
	 * @return true if object is instance of node
	 */
	public  boolean isNode (Object object){
		if (creator==null)
			return false;
		return creator.isNode(object);
	}
	/**
	 * Checks if object is instance of interface
	 * @param object object to check
	 * @return true if object is instance of interface
	 */
	public  boolean isInterface (Object object){
		if (creator==null)
			return false;
		return creator.isInterface(object);
	}
	/**
	 * Checks if object is instance of link
	 * @param object object to check
	 * @return true if object is instance of link
	 */
	public  boolean isLink (Object object){
		if (creator==null)
			return false;
		return creator.isLink(object);
	}
	
	
	/**
	 * Exports project in xml format to file
	 * @param path path to file
	 */
	public static void exportProject (TopologyProject project, String path){	
		try {
			projectMarshaler.marshal(project, path);
		} catch (CannotStoreFileException e) {
			JOptionPane.showMessageDialog(null,"Problem with storing topology");
		}
	}
	/**
	 * Exports project in xml format to file
	 * @param path path to file
	 */
	public static TopologyProject importProject (String path){	
			try {
				return projectMarshaler.unmarshal(path);
			} catch (WrongFileFormatException e) {
				JOptionPane.showMessageDialog(null, "Wrong file format for project description");
			}
		return null;
	}
	
	
	
	/**
	 * Exports topology in xml format to file
	 * @param path path to file
	 */
	public static void exportTopolgy (Topology topology, String path){	
		try {
			topologyMarshaler.marshal(topology, path);
		} catch (net.geant2.jra3.intradomain.topology.CannotStoreFileException e) {
			JOptionPane.showMessageDialog(null,"Problem with storing topology");
		}
	}
	
	/**
	 * Exports topology in xml format to file
	 * @param path path to file
	 */
	public static Topology importTopolgy (String path){
		Topology topology=null;
		try {
			topology = topologyMarshaler.unmarshal(path);
		} catch (net.geant2.jra3.intradomain.topology.WrongFileFormatException e) {
			JOptionPane.showMessageDialog(null, "Wrong file format");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topology;
	}

	/*public void setLastNodeIdentifier(int lastNodeIdentifier) {
		this.lastNodeIdentifier = lastNodeIdentifier;
	}

	public void setLastLinkIdentifier(int lastLinkIdentifier) {
		this.lastLinkIdentifier = lastLinkIdentifier;
	}

	public void setLastInterfaceIdentifier(int lastInterfaceIdentifier) {
		this.lastInterfaceIdentifier = lastInterfaceIdentifier;
	}
*/
	/**
	 * Gets project topology
	 * @return project topology
	 */
	public Topology getTopology() {
		return topology;
	}
	/**
	 * Gets project topology
	 * @return project topology
	 */
	public void setTopology(Topology topology) {
		this.topology = topology;
	}
	/**
	 * Put graph properties
	 * @param name	
	 * @param rect
	 */
	public void putGraphPropertes (String name,Rect rect){
		if (name == null || rect ==null)
			return;
		graphProperties.put(name, rect);
	}
	
	public void removeGraphPropertes (String name){
		if (name == null)
			return;
		graphProperties.remove(name);
	}
	
	public Rect removeGraphProperties (String name){
		if (name == null)
			return null;
		return graphProperties.remove(name);
	}
	
	public void moveGraphProperties (String previous, String name){
		Rect  rect = removeGraphProperties(previous);
		putGraphPropertes(name, rect);
	}
	public String checkNames (String previous,String name){
		int nodesLenght = topology.getNodes().size();
		int interfacesLength = topology.getInterfaces().size();
		int linkLength = topology.getLinks().size();
		int max = Math.max(nodesLenght, interfacesLength);
		Math.max(max, linkLength);
		boolean found =false;
		
		for (int i=0;i<max;i++){
			if ((i< nodesLenght)&& topology.getNodes().get(i).getName().equals(name))
			{
				found = true;
				break;
			}
			if ((i< interfacesLength)&& topology.getInterfaces().get(i).getName().equals(name))
			{
				found = true;
				break;
			}
			
		}
		if (found){
			JOptionPane.showMessageDialog(null, "Name exist. Choose another one.");
			return previous;
		}else
			return name;
	}

	public Map<String, Rect> getGraphProperties() {
		return graphProperties;
	}

	public void setGraphProperties(Map<String, Rect> graphProperties) {
		this.graphProperties = graphProperties;
	}
	
	public ObjectPropertiesModel getPropertiesModel (String model){
		if (creator==null)
			return null;
		return creator.getPropertiesModel(model);
	}

	public ProjectObjectCreator getCreator() {
		return creator;
	}

	public void setCreator(ProjectObjectCreator creator) {
		this.creator = creator;
	}
	
	public boolean checkIfLinkExist (Object start, Object end){
		return creator.checkIfLinkExist(topology, start, end);
	}


	public void switchLinkDirection(Object link) {
		creator.switchLinkDirection(link);
	}
	
}
