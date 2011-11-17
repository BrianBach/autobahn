/**
 * Node.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a node
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="Node", namespace="common.intradomain.autobahn.geant.net", 
		propOrder={"nodeId", "version", "location", "name", "description",
		"status", "vendor", "model", "osName", "osVersion", "ipAddress", "vlanTranslationSupport"
})
public class Node implements Serializable {
	
	private static final long serialVersionUID = 5535332294663547306L;

	//@XmlTransient
	private long nodeId;
	private VersionInfo version;
	private Location location;
	private String name;
	private String description;
	private String status;
	private String vendor;
	private String model;
	private String osName;
	private String osVersion;
	private String ipAddress;
	private boolean vlanTranslationSupport;
	
	/**
	 * This method returns the description of the node
	 * 
	 * @return Returns the description of the node
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method sets the description of the node
	 * 
	 * @param description the description of the node
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method returns the IP address of the node
	 * 
	 * @return Returns the IP address of the node
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * This method sets the IP address of the node
	 * 
	 * @param ipAddress the IP address of the node
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * This method returns the location of the node
	 * 
	 * @return Returns the location of the node
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * This method sets the location of the node
	 * 
	 * @param location the location of the node
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * This method returns the model of the node
	 * 
	 * @return Returns the model of the node
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * This method sets the model of the node
	 * 
	 * @param model the model of the node
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * This method returns the name of the node 
	 * 
	 * @return Returns the model of the node
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the model of the node
	 * 
	 * @param name the model of the node
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the id of the node 
	 * 
	 * @return Returns the id of the node
	 */
	public long getNodeId() {
		return nodeId;
	}
	
	/**
	 * This method sets the id of the node
	 * 
	 * @param nodeId the id of the node
	 */
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * This method returns the OS name of the node
	 * 
	 * @return Returns the OS name of the node
	 */
	public String getOsName() {
		return osName;
	}
	
	/**
	 * This method sets the OS name of the node
	 * 
	 * @param osName the OS name of the node
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	/**
	 * This method returns the OS version of the node
	 * 
	 * @return Returns the OS version of the node 
	 */
	public String getOsVersion() {
		return osVersion;
	}
	
	/**
	 * This method sets the OS version of the node
	 * 
	 * @param osVersion the OS version of the node
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	/**
	 * This method returns the status of the node
	 * 
	 * @return Returns the status of the node
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * This method sets the status of the node
	 * 
	 * @param status the status of the node
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * This method returns the vendor of the node
	 * 
	 * @return Returns the vendor of the node
	 */
	public String getVendor() {
		return vendor;
	}
	
	/**
	 * This method sets the vendor of the node
	 * 
	 * @param vendor the vendor of the node
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public boolean isVlanTranslationSupport() {
		return vlanTranslationSupport;
	}

	public void setVlanTranslationSupport(boolean vlanTranslationSupport) {
		this.vlanTranslationSupport = vlanTranslationSupport;
	}

	/**
	 * This method returns version information of the node
	 * 
	 * @return Returns version information of the node
	 */
	public VersionInfo getVersion() {
		return version;
	}
	
	/**
	 * This method sets version information of the node
	 * 
	 * @param version the version information of the node
	 */
	public void setVersion(VersionInfo version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (nodeId ^ (nodeId >>> 32));
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
		final Node other = (Node) obj;
		if (getNodeId() != other.getNodeId())
			return false;
		if (!getName().equals(other.getName()))
		    return false;
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Node [nodeId=" + nodeId + ", version=" + version
                + ", location=" + location + ", name=" + name
                + ", description=" + description + ", status=" + status
                + ", vendor=" + vendor + ", model=" + model + ", osName="
                + osName + ", osVersion=" + osVersion + ", ipAddress="
                + ipAddress + ", vlanTranslationSupport="
                + vlanTranslationSupport + "]";
    }
}
