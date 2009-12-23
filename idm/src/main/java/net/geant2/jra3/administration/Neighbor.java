/**
 * 
 */
package net.geant2.jra3.administration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant2.jra3.network.Link;

/**
 * @author Michal
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Neighbor", namespace="administration.jra3.geant2.net", propOrder={
		"domain", "link" 
})
public class Neighbor {
	
	private String domain;
	private Link link;
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the link
	 */
	public Link getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(Link link) {
		this.link = link;
	}
}
