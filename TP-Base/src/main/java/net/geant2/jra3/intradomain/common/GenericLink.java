/**
 * GenericLink.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * This class represents a generic link
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GenericLink", namespace="common.intradomain.jra3.geant2.net", propOrder={
		"version", "startInterface", "endInterface", "direction",
		"protection", "propDelay"
})
public class GenericLink implements Serializable {
	
	private static final long serialVersionUID = -7857873434490722350L;
	@XmlTransient
	private long linkId;
	private VersionInfo version;
	private GenericInterface startInterface;
	private GenericInterface endInterface;
	private String direction;
	private boolean protection;
	private double propDelay;
	
	/**
	 * This method returns the direction of the generic link
	 * 
	 * @return Returns the direction of the generic link
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * This method sets the direction of the generic link
	 * 
	 * @param direction the direction of the generic link
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/**
	 * This method returns the end interface of the generic link
	 * 
	 * @return Returns the end interface of the generic link
	 */
	public GenericInterface getEndInterface() {
		return endInterface;
	}
	
	/**
	 * This method sets the end interface of the generic link
	 * 
	 * @param endInterface the end interface of the generic link
	 */
	public void setEndInterface(GenericInterface endInterface) {
		this.endInterface = endInterface;
	}
	
	/**
	 * This method returns the id of the generic link
	 * 
	 * @return Returns the id of the generic link
	 */
	public long getLinkId() {
		return linkId;
	}
	
	/**
	 * This method sets the id of the generic link
	 * 
	 * @param linkId the id of the generic link
	 */
	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}
	
	/**
	 * This method returns the propagation delay of the generic link
	 * 
	 * @return Returns the propagation delay of the generic link
	 */
	public double getPropDelay() {
		return propDelay;
	}
	
	/**
	 * This method sets the propagation delay of the generic link
	 * 
	 * @param propDelay the propagation delay of the generic link
	 */
	public void setPropDelay(double propDelay) {
		this.propDelay = propDelay;
	}
	
	/**
	 * This method returns true if the generic link is protected
	 * 
	 * @return Returns true if the generic link is protected
	 */
	public boolean getProtection() {
		return protection;
	}
	
	/**
	 * This method sets the protection status of the generic link
	 * 
	 * @param protection the protection status of the generic link
	 */
	public void setProtection(boolean protection) {
		this.protection = protection;
	}
	
	/**
	 * This method returns the start interface of the generic link
	 * 
	 * @return Returns the start interface of the generic link
	 */
	public GenericInterface getStartInterface() {
		return startInterface;
	}
	
	/**
	 * This method sets the start interface of the generic link
	 * 
	 * @param startInterface the start interface of the generic link
	 */
	public void setStartInterface(GenericInterface startInterface) {
		this.startInterface = startInterface;
	}
	
	/**
	 * This method returns version information on the generic link
	 * 
	 * @return Returns version information on the generic link
	 */
	public VersionInfo getVersion() {
		return version;
	}
	
	/**
	 * This method sets version information on the generic link
	 * 
	 * @param version the version information on the generic link
	 */
	public void setVersion(VersionInfo version) {
		this.version = version;
	}
	
    public long getCapacity() {
        return Math.min(getStartInterface().getBandwidth(), 
                getEndInterface().getBandwidth());
    }
    
    public boolean isInterdomain() {
    	if(getStartInterface().getDomainId() == null ||
    			getEndInterface().getDomainId() == null)
    		return false;
    	
    	return !getStartInterface().getDomainId().equals(
				getEndInterface().getDomainId());
    }
    
	@Override
	public String toString() {
		return getStartInterface().getName() + "-" + getEndInterface().getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (linkId ^ (linkId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(!(obj instanceof GenericLink))
			return false;
		final GenericLink other = (GenericLink) obj;
		if (getLinkId() != other.getLinkId())
			return false;
		return true;
	}
}
