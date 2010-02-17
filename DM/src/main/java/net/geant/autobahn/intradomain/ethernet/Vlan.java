/**
 * Vlan.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a range of Ethernet VLANs
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Vlan", namespace="ethernet.intradomain.autobahn.geant.net", propOrder={
		"vlanId", "vtpDomain", "name", "lowNumber", "highNumber"
})
public class Vlan implements Serializable {
	
	private static final long serialVersionUID = 4582484688017808865L;
	//@XmlTransient
	private long vlanId;
	private VtpDomain vtpDomain; 
	private String name; 
	private long lowNumber;
    private long highNumber;
    
    public Vlan() { }
    
    public Vlan(long vlanId, String name, long lowNumber, long highNumber) {
        
        this.vlanId = vlanId;
        this.name = name;
        this.lowNumber = lowNumber;
        this.highNumber = highNumber;
    }
    
    public Vlan(VtpDomain domain, long vlanId, String name, 
                        long lowNumber, long highNumber) {
        
        this(vlanId, name, lowNumber, highNumber);
        this.vtpDomain = domain;
    }
	
	/**
	 * This method returns the name of the VLAN range
	 * 
	 * @return Returns the name of the VLAN range
	 */
    public String getName() {
		return name;
	}
	
    /**
     * This method sets the name of the VLAN range
     * 
     * @param name the name of the VLAN range
     */
    public void setName(String name) {
		this.name = name;
	}
	
    /**
     * This method returns the lowest VLAN number on the range
     * 
     * @return Returns the lowest VLAN number on the range
     */
    public long getLowNumber() {
		return lowNumber;
	}
	
    /**
     * This method sets the lowest VLAN number on the range
     * 
     * @param lowNumber the lowest VLAN number on the range
     */
    public void setLowNumber(long lowNumber) {
		this.lowNumber = lowNumber;
	}
	
    /**
     * This method returns the highest VLAN number on the range
     * 
     * @return Returns the highest VLAN number on the range
     */
    public long getHighNumber() {
        return highNumber;
    }
    
    /**
     * This method sets the highest VLAN number on the range
     * 
     * @param highNumber the highest VLAN number on the range
     */
    public void setHighNumber(long highNumber) {
        this.highNumber = highNumber;
    }
    
    /**
     * This method returns the id of the VLAN range
     * 
     * @return Returns the id of the VLAN range
     */
    public long getVlanId() {
		return vlanId;
	}
	
    /**
     * This method sets the id of the VLAN range
     * 
     * @param vlanId the id of the VLAN range
     */
    public void setVlanId(long vlanId) {
		this.vlanId = vlanId;
	}
	
    /**
     * This method returns the VTP domain of the VLAN range
     * 
     * @return Returns the VTP domain of the VLAN range
     */
    public VtpDomain getVtpDomain() {
		return vtpDomain;
	}
	
    /**
     * This method sets the VTP domain of the VLAN range
     * 
     * @param vtpDomain the VTP domain of the VLAN range
     */
    public void setVtpDomain(VtpDomain vtpDomain) {
		this.vtpDomain = vtpDomain;
	}

    @Override
    public String toString() {
        return "" + getVlanId();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (vlanId ^ (vlanId >>> 32));
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
		final Vlan other = (Vlan) obj;
		if (vlanId != other.vlanId)
			return false;
		return true;
	}
}
