package net.geant2.jra3.intradomain.builder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.geant2.jra3.intradomain.builder.creators.EthernetProjectCreator;
import net.geant2.jra3.intradomain.builder.creators.SDHProjectCreator;
import net.geant2.jra3.intradomain.topology.EthernetTopology;
import net.geant2.jra3.intradomain.topology.SDHTopology;
import net.geant2.jra3.intradomain.topology.TopologyMarshaler;
import net.geant2.jra3.intradomain.topology.XstreemTopologyMalshaler;

/**
 *  Factory class for creating and storing various kinds 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ProjectFactory {
	/**
	 * Map with defined project templates
	 */
	public transient static final Map<String, ProjectObjectCreator> types = new HashMap <String, ProjectObjectCreator>();
	/**
	 * Project marshaler
	 */
	public static ProjectMarshaler projectMarshaler = new XStreamTopologyProjectMarshaler();
	/**
	 * Topology marshaler
	 */
	public static TopologyMarshaler topologyMarshaler = new XstreemTopologyMalshaler ();
	/**
	 * Initialize project factory
	 */
	public static void initialize (){
		TopologyProject.setMarshalers(projectMarshaler,topologyMarshaler );
		types.put("ETHERNET",new EthernetProjectCreator());
		types.put("SDH",new SDHProjectCreator());
	}
	/**
	 * Gets iterator names  of defined template of the projects
	 * 
	 * @return Iterator iterator names  of defined template of the projects
	 */
	public static Iterator<String>  getProjectTypesNames (){
		return types.keySet().iterator();
	}
	
	/**
	 * Create new project with chosen template
	 * @param type	name of chosen template
	 * @return	created TopologyProject
	 */
	public static TopologyProject createProject (String type){
		if (type==null)
			return null;
		TopologyProject project = new TopologyProject();  
		ProjectObjectCreator creator = types.get(type);
		if (type.equals("SDH"))
			project.getTopology().setSdhTopology(new SDHTopology());
		if (type.equals("ETHERNET"))
			project.getTopology().setEthernetTopology(new EthernetTopology());
			
		project.setCreator(creator);
		if (creator == null)
			return null;
		return project;
	}	
	/**
	 * Set project related to project template type 
	 *  
	 * @param topologyProject project to set project tools
	 */
	public static void setProjectTools (TopologyProject topologyProject){
		if (topologyProject == null || topologyProject.getTopology()==null)
			return;
	if (topologyProject.getTopology().getEthernetTopology()!=null){
		topologyProject.setCreator(types.get("ETHERNET"));
	}
	if (topologyProject.getTopology().getSdhTopology()!=null){
		topologyProject.setCreator(types.get("SDH"));
	}
	TopologyProject.setMarshalers(projectMarshaler, topologyMarshaler);
	}
	
	
	
}
