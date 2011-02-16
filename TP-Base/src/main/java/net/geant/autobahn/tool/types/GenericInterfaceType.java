package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.common.InterfaceType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="GenericInterfaceType", namespace="types.tool.autobahn.geant.net",
	propOrder={ "interfaceType", "node", "name", "description", "bandwidth", "status", 
		"mtu", "domainId", "clientPort"
})
public class GenericInterfaceType {
	
	private InterfaceType interfaceType; 
	private NodeType node; 
	private String name; 
	private String description; 
	private long bandwidth; 
	private String status; 
	private int mtu; 
	private String domainId;
	private boolean clientPort;
	
	
	public GenericInterfaceType() {
		
	}

	/**
	 * @return the interfaceType
	 */
	public InterfaceType getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @param interfaceType the interfaceType to set
	 */
	public void setInterfaceType(InterfaceType interfaceType) {
		this.interfaceType = interfaceType;
	}

	/**
	 * @return the node
	 */
	public NodeType getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(NodeType node) {
		this.node = node;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the bandwidth
	 */
	public long getBandwidth() {
		return bandwidth;
	}

	/**
	 * @param bandwidth the bandwidth to set
	 */
	public void setBandwidth(long bandwidth) {
		this.bandwidth = bandwidth;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the mtu
	 */
	public int getMtu() {
		return mtu;
	}

	/**
	 * @param mtu the mtu to set
	 */
	public void setMtu(int mtu) {
		this.mtu = mtu;
	}

	/**
	 * @return the domainId
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	/**
	 * @return the clientPort
	 */
	public boolean isClientPort() {
		return clientPort;
	}

	/**
	 * @param clientPort the clientPort to set
	 */
	public void setClientPort(boolean clientPort) {
		this.clientPort = clientPort;
	}
	
}
