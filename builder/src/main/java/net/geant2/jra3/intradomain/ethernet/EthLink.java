/**
 * EthLink.java
 * 2007-03-28
 */

package net.geant2.jra3.intradomain.ethernet;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant2.jra3.intradomain.common.GenericLink;

/** This class represents an Ethernet link. 
 * It extends the GenericLink class
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EthLink", propOrder={
		"genericLink", "discoveryProtocol", "isTrunk", 
		"isL2Bndry", "nativeVlan"
})
public class EthLink implements Serializable {
	
	private static final long serialVersionUID = -2585680993224699594L;
	
	private GenericLink genericLink; 
	private String discoveryProtocol;
	private boolean isTrunk; 
	private boolean isL2Bndry;
	private long nativeVlan;
	
    public EthLink() { }
    
    public EthLink(String discoProtocol, boolean isTrunk, 
            boolean isL2Boundary, long nativeVlan) {
        
        this.discoveryProtocol = discoProtocol;
        this.isTrunk = isTrunk;
        this.isL2Bndry = isL2Boundary;
        this.nativeVlan = nativeVlan;
    }
    
    public EthLink(GenericLink genericLink, String discoProtocol,
           boolean isTrunk, boolean isL2Boundary, long nativeVlan) {
        
        this(discoProtocol, isTrunk, isL2Boundary, nativeVlan);
        this.genericLink = genericLink;
    }
    
    /** This method returns the discovery protocol of the Ethernet link
     * 
     * @return Returns the discovery protocol of the Ethernet link
     */
	public String getDiscoveryProtocol() {
		return discoveryProtocol;
	}
	
	/**
	 * This method sets the discovery protocol of the Ethernet link
	 * 
	 * @param discoveryProtocol the discovery protocol of the Ethernet link
	 */
	public void setDiscoveryProtocol(String discoveryProtocol) {
		this.discoveryProtocol = discoveryProtocol;
	}
	
	/**
	 * This method returns the generic link that is associated with the Ethernet link
	 * 
	 * @return Returns the generic link that is associated with the Ethernet link
	 */
	public GenericLink getGenericLink() {
		return genericLink;
	}
	
	/**
	 * This method sets the generic link that is associated with the Ethernet link
	 * 
	 * @param genericLink the generic link that is associated with the Ethernet link
	 */
	public void setGenericLink(GenericLink genericLink) {
		this.genericLink = genericLink;
	}
	
	/**
	 * This method returns true if the Ethernet link is at the boundary of the L2 network
	 * 
	 * @return Returns true if the Ethernet link is at the boundary of the L2 network
	 */
	public boolean getIsL2Bndry() {
		return isL2Bndry;
	}
	
	/**
	 * This method sets the state of the Ethernet link with regards to whether it is at the boundary of the L2 network
	 * 
	 * @param isL2Bndry the state of the Ethernet link with regards to whether it is at the boundary of the L2 network
	 */
	public void setIsL2Bndry(boolean isL2Bndry) {
		this.isL2Bndry = isL2Bndry;
	}
	
	/**
	 * This method returns true if the Ethernet link is a trunk 
	 * 
	 * @return Returns true if the Ethernet link is a trunk
	 */
	public boolean getIsTrunk() {
		return isTrunk;
	}
	
	/**
	 * This method sets the state of the Ethernet link with regards to whether it is a trunk
	 * 
	 * @param isTrunk the state of the Ethernet link with regards to whether it is a trunk
	 */
	public void setIsTrunk(boolean isTrunk) {
		this.isTrunk = isTrunk;
	}
	
	/**
	 * This method returns the native VLAN of the Ethernet link
	 * 
	 * @return Returns the native VLAN of the Ethernet link
	 */
	public long getNativeVlan() {
		return nativeVlan;
	}
	
	/**
	 * This method sets the native VLAN of the Ethernet link
	 * 
	 * @param nativeVlan the native VLAN of the Ethernet link
	 */
	public void setNativeVlan(long nativeVlan) {
		this.nativeVlan = nativeVlan;
	}

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof EthLink))
            return false;
        
        EthLink l2 = (EthLink) obj;
        
        return getGenericLink().getLinkId() == l2.getGenericLink().getLinkId();
    }

    @Override
    public int hashCode() {
        return genericLink.hashCode();
    }

    @Override
    public String toString() {
    	if (genericLink!= null)
    		return genericLink.toString();
    	return "EthLink";
    }
}
