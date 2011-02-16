package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.common.Location;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NodeType", namespace="types.tool.autobahn.geant.net", 
		propOrder={"location", "name", "description", "status", "vendor", "model", "osName", 
			"osVersion", "ipAddress", "vlanTranslationSupport"
})
public class NodeType {

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
	
	public NodeType() {
		
	}
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
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
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}
	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the osName
	 */
	public String getOsName() {
		return osName;
	}
	/**
	 * @param osName the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}
	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}
	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the vlanTranslationSupport
	 */
	public boolean isVlanTranslationSupport() {
		return vlanTranslationSupport;
	}
	/**
	 * @param vlanTranslationSupport the vlanTranslationSupport to set
	 */
	public void setVlanTranslationSupport(boolean vlanTranslationSupport) {
		this.vlanTranslationSupport = vlanTranslationSupport;
	}
	
}
