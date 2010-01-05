package net.geant2.jra3.autoBahnGUI.manager;

import java.util.HashMap;
import java.util.Map;
/**
 * Maps the virtual port name to real one and vice versa
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class PortsMapperImpl implements PortsMapper{
	/**
	 * Map used for mapping real JRA3 IDM port to virtual one
	 */
	protected Map<String, String> portsMapping = new HashMap<String, String>();
	/**
	 * Map used for mapping virtual JRA3 IDM port to real one 
	 */
	protected Map <String, String> deMappingPorts = new HashMap<String, String>();
	
	
	public String mapPort(String port) {		
		if (portsMapping==null)
			return port;
		String mappedPort = portsMapping.get(port);
		if (mappedPort == null)
			return port;
		return mappedPort;
	}
	public String demapPort (String mapping){
		if (deMappingPorts== null)
			return mapping;
		String mappedPort = deMappingPorts.get(mapping );
		if (mappedPort == null)
			return mapping;
		return mappedPort;
	}
	
	
	public Map<String, String> getPortsMapping() {
		return portsMapping;
	}
	public void setPortsMapping(Map<String, String> portsMapping) {
		if (portsMapping==null)return ;
		this.portsMapping = portsMapping;
		for (String key:portsMapping.keySet()){
			deMappingPorts.put(portsMapping.get(key), key);
		}
	}
		
	
}
