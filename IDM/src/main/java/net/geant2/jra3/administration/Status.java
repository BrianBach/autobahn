package net.geant2.jra3.administration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes status of IDM
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Status", namespace="administration.jra3.geant2.net", propOrder={
		"domain", "longitude", "latitude", "neighbors" 
})
public class Status {
	
	private String domain;
	private float longitude;
	private float latitude;
	@XmlElement(required = true, nillable = true)
	private List<Neighbor> neighbors;
	
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
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the neighbors
	 */
	public List<Neighbor> getNeighbors() {
		return neighbors;
	}
	/**
	 * @param neighbors the neighbors to set
	 */
	public void setNeighbors(List<Neighbor> neighbors) {
		this.neighbors = neighbors;
	}
}
