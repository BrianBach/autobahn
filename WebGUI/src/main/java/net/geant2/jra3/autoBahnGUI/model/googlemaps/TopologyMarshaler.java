package net.geant2.jra3.autoBahnGUI.model.googlemaps;


import org.w3c.dom.Node;
/**
 * Interface for XML topology marshaler
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface TopologyMarshaler {
	/**
	 * Marshals the topology to string
	 * @param topology topology data
	 * @return	string with xml representation of topology
	 */
	String topologyToString (Topology topology);
	/**
	 * Marshals the topology to string
	 * @param topology topology data
	 * @return	string with xml representation of topology
	 */
	void topologyToDOMNode (Node node, Topology topology);
	
}
