/**
 * GenericInterface.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represents a generic interface
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="GenericInterface", namespace="common.intradomain.jra3.geant2.net",
	propOrder={ "interfaceId", "version", "interfaceType", "parentInterface", "node",
		"name", "description", "bandwidth", "status", "mtu", "domainId", "clientPort"
})
public class GenericInterface implements Serializable {
	
	private static final long serialVersionUID = -2122703404093352705L;

	//@XmlTransient
	private long interfaceId;
	private VersionInfo version; 
	private InterfaceType interfaceType; 
	private GenericInterface parentInterface; 
	private Node node; 
	private String name; 
	private String description; 
	private long bandwidth; 
	private String status; 
	private String mtu; 
	private String domainId;
	//@XmlTransient
	private boolean clientPort;
	
	/**
	 * This method returns the bandwidth of the generic interface
	 * 
	 * @return Returns the bandwidth of the generic interface
	 */
	public long getBandwidth() {
		return bandwidth;
	}
	
	/**
	 * This method sets the bandwidth of the generic interface
	 * 
	 * @param bandwidth the bandwidth of the generic interface
	 */
	public void setBandwidth(long bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	/**
	 * This method returns the description of the generic interface
	 * 
	 * @return Returns the description of the generic interface
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method sets the description of the generic interface
	 * 
	 * @param description the description of the generic interface
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method returns the domain of the generic interface (NULL if the interface is on an intra-domain link)
	 * 
	 * @return Returns the domain of the generic interface  (NULL if the interface is on an intra-domain link)
	 */
	public String getDomainId() {
		return domainId;
	}
	
	/**
	 * This method sets the domain of the generic interface  (NULL if the interface is on an intra-domain link)
	 * 
	 * @param domainId the domain of the generic interface  (NULL if the interface is on an intra-domain link)
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	/**
	 * This method returns the id of the generic interface
	 * 
	 * @return Returns the id of the generic interface
	 */
	//@XmlTransient
	public long getInterfaceId() {
		return interfaceId;
	}
	
	/**
	 * This method sets the id of the generic interface
	 * 
	 * @param interfaceId the id of the generic interface
	 */
	public void setInterfaceId(long interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	/**
	 * This method returns the type of the generic interface
	 * 
	 * @return Returns the type of the generic interface
	 */
	public InterfaceType getInterfaceType() {
		return interfaceType;
	}
	
	/**
	 * This method sets the type of the generic interface
	 * 
	 * @param interfaceType the type of the generic interface
	 */
	public void setInterfaceType(InterfaceType interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	/**
	 * This method returns the MTU of the generic interface
	 * 
	 * @return Returns the MTU of the generic interface
	 */
	public String getMtu() {
		return mtu;
	}
	
	/**
	 * This method sets the MTU of the generic interface
	 * 
	 * @param mtu the MTU of the generic interface
	 */
	public void setMtu(String mtu) {
		this.mtu = mtu;
	}
	
	/**
	 * This method returns the name of the generic interface
	 * 
	 * @return Returns the name of the generic interface
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the generic interface
	 * 
	 * @param name the name of the generic interface
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the node to which the generic interface belongs
	 * 
	 * @return Returns the node to which the generic interface belongs
	 */
	public Node getNode() {
		return node;
	}
	
	/**
	 * This method sets the node to which the generic interface belongs
	 * 
	 * @param node the node to which the generic interface belongs
	 */
	public void setNode(Node node) {
		this.node = node;
	}
	
	/**
	 * This method returns the parent interface of the generic interface
	 * 
	 * @return Returns the parent interface of the generic interface
	 */
	public GenericInterface getParentInterface() {
		return parentInterface;
	}
	
	/**
	 * This method sets the parent interface of the generic interface
	 * 
	 * @param parentInterface the parent interface of the generic interface
	 */
	public void setParentInterface(GenericInterface parentInterface) {
		this.parentInterface = parentInterface;
	}
	
	/**
	 * This method returns the status of the generic interface
	 * 
	 * @return Returns the status of the generic interface
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * This method sets the status of the generic interface
	 * 
	 * @param status the status of the generic interface
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * This method returns version information for the generic interface
	 * 
	 * @return returns version information for the generic interface
	 */
	public VersionInfo getVersion() {
		return version;
	}
	
	/**
	 * This method sets version information for the generic interface
	 * 
	 * @param version the version information for the generic interface
	 */
	public void setVersion(VersionInfo version) {
		this.version = version;
	}

	/**
	 * @return the clientPort
	 */
	//@XmlTransient
	public boolean isClientPort() {
		return clientPort;
	}

	/**
	 * @param clientPort the clientPort to set
	 */
	public void setClientPort(boolean clientPort) {
		this.clientPort = clientPort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (interfaceId ^ (interfaceId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GenericInterface other = (GenericInterface) obj;
		if (interfaceId != other.interfaceId)
			return false;
		return true;
	}
}
