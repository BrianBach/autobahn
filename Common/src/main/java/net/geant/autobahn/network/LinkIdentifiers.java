package net.geant.autobahn.network;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class contains abstract identifiers for Node, Port and Link associated with a
 * Port. Used in the abstraction process.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="LinkIdentifiers", namespace="network.autobahn.geant.net", propOrder={
		"nodeId", "portId", "linkId"
})
public class LinkIdentifiers {

	private String nodeId;
	private String portId;
	private String linkId;
	
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * @return the portId
	 */
	public String getPortId() {
		return portId;
	}
	
	/**
	 * @param portId the portId to set
	 */
	public void setPortId(String portId) {
		this.portId = portId;
	}
	
	/**
	 * @return the linkId
	 */
	public String getLinkId() {
		return linkId;
	}
	
	/**
	 * @param linkId the linkId to set
	 */
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
}
