/**
 * 
 */
package net.geant.autobahn.intradomain.mpls;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Represents single mpls link between two nodes
 * @author PCSS
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name="MplsLink", namespace="mpls.intradomain.autobahn.geant.net", propOrder={
		"genericLink" 
})
public class MplsLink implements Serializable {
	
	private GenericLink genericLink;
	
	public MplsLink() { 
		
	}
	
	public MplsLink(GenericLink link) { 
		this.genericLink = link;
	}
		
	/**
	 * @return the genericLink
	 */
	public GenericLink getGenericLink() {
		return genericLink;
	}

	/**
	 * @param genericLink the genericLink to set
	 */
	public void setGenericLink(GenericLink genericLink) {
		this.genericLink = genericLink;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof MplsLink))
			return false;
		
		return this.getGenericLink().getLinkId() == ((MplsLink)obj).getGenericLink().getLinkId();
	}
	
	@Override
	public int hashCode() {
	
		return genericLink.hashCode();
	}
	
	@Override
	public String toString() {
	
		return "mpls link - " + genericLink.toString();
	}
}
