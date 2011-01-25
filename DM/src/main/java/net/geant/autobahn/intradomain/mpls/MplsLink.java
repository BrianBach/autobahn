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
		"link" 
})
public class MplsLink implements Serializable {
	
	private GenericLink link;
	
	public MplsLink() { 
		
	}
	
	public MplsLink(GenericLink link) { 
		this.link = link;
	}

	public GenericLink getLink() {
		return link;
	}

	public void setLink(GenericLink link) {
		this.link = link;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof MplsLink))
			return false;
		
		return this.getLink().getLinkId() == ((MplsLink)obj).getLink().getLinkId();
	}
	
	@Override
	public int hashCode() {
	
		return link.hashCode();
	}
	
	@Override
	public String toString() {
	
		return "mpls link - " + link.toString();
	}
}
