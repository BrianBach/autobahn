/**
 * Location.java
 * 2007-03-28
 */

package net.geant.autobahn.intradomain.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represents a location
 * 
 * @author <a href="mailto:alyf@di.uoa.gr">George Alyfantis</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Location", namespace="common.intradomain.autobahn.geant.net", 
		propOrder={ "name", "description", "country", "institution",
		"street", "floor", "roomSuite", "row_", "cabinet", "zipCode",
		"phoneNumber", "eMailAddress", "geoLatitude", "geoLongitude",
		"type", "zone", "altitude"
})
public class Location implements Serializable {
	
	private static final long serialVersionUID = 6451104489831796777L;
	
	@XmlTransient
	private long locationId; 
	private String name; 
	private String description; 
	private String country;
	private String institution; 
	private String street; 
	private int floor; 
	private int roomSuite; 
	private int row_; 
	private int cabinet; 
	private String zipCode; 
	private String phoneNumber; 
	private String eMailAddress; 
	private double geoLatitude; 
	private double geoLongitude; 
	private String type; 
	private String zone;
	private double altitude;
	
	/**
	 * This method returns the altitute of the location
	 * 
	 * @return Returns the altitute of the location
	 */
	public double getAltitude() {
		return altitude;
	}
	
	/**
	 * This method sets the altitute of the location
	 * 
	 * @param altitude the altitute of the location
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
	/**
	 * This method returns the cabinet of the location
	 * 
	 * @return Returns the cabinet of the location
	 */
	public int getCabinet() {
		return cabinet;
	}
	
	/**
	 * This method sets the cabinet of the location
	 * 
	 * @param cabinet the cabinet of the location
	 */
	public void setCabinet(int cabinet) {
		this.cabinet = cabinet;
	}
	
	/**
	 * This method returns the country of the location
	 * 
	 * @return Returns the country of the location
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * This method sets the country of the location
	 * 
	 * @param country the country of the location
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * This method returns the description of the location
	 * 
	 * @return Returns the description of the location
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method sets the description of the location
	 * 
	 * @param description the description of the location
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method returns the email contact for the location
	 * 
	 * @return Returns the email contact for the location
	 */
	public String geteMailAddress() {
		return eMailAddress;
	}
	
	/**
	 * This method sets the email contact for the location
	 * 
	 * @param mailAddress the email contact for the location
	 */
	public void seteMailAddress(String mailAddress) {
		eMailAddress = mailAddress;
	}
	
	/**
	 * This method returns the floor of the location
	 * 
	 * @return Returns the floor of the location
	 */
	public int getFloor() {
		return floor;
	}
	
	/**
	 * This method sets the floor of the location
	 * 
	 * @param floor the floor of the location
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	/**
	 * This method returns the latitude of the location
	 * 
	 * @return Returns the latitude of the location
	 */
	public double getGeoLatitude() {
		return geoLatitude;
	}
	
	/**
	 * This method sets the latitude of the location
	 * 
	 * @param geoLatitude the latitude of the location
	 */
	public void setGeoLatitude(double geoLatitude) {
		this.geoLatitude = geoLatitude;
	}
	
	/**
	 * This method returns the longitute of the location
	 * 
	 * @return Returns the longitute of the location
	 */
	public double getGeoLongitude() {
		return geoLongitude;
	}
	
	/**
	 * This method sets the longitute of the location
	 * 
	 * @param geoLongitude the longitute of the location
	 */
	public void setGeoLongitude(double geoLongitude) {
		this.geoLongitude = geoLongitude;
	}
	
	/**
	 * This method returns the institution of the location
	 * 
	 * @return Returns the institution of the location
	 */
	public String getInstitution() {
		return institution;
	}
	
	/**
	 * This method sets the institution of the location
	 * 
	 * @param institution the institution of the location
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	/**
	 * This method returns the id of the location
	 * 
	 * @return Returns the id of the location
	 */
	public long getLocationId() {
		return locationId;
	}
	
	/**
	 * This method sets the id of the location
	 * 
	 * @param locationId the id of the location
	 */
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	
	/**
	 * This method returns the name of the location
	 * 
	 * @return Returns the name of the location
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method sets the name of the location
	 * 
	 * @param name the name of the location
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the phone number contact for the location
	 * 
	 * @return Returns the phone number contact for the location
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * This method sets the phone number contact for the location
	 * 
	 * @param phoneNumber the phone number contact for the location
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * This method returns the room suite of the location
	 * 
	 * @return Returns the room suite of the location
	 */
	public int getRoomSuite() {
		return roomSuite;
	}
	
	/**
	 * This method sets the room suite of the location
	 * 
	 * @param roomSuite the room suite of the location
	 */
	public void setRoomSuite(int roomSuite) {
		this.roomSuite = roomSuite;
	}
	
	/**
	 * This method returns the row of the location
	 * 
	 * @return Returns the row of the location
	 */
	public int getRow_() {
		return row_;
	}
	
	/**
	 * This method sets the row of the location
	 * 
	 * @param row_ the row of the location
	 */
	public void setRow_(int row_) {
		this.row_ = row_;
	}
	
	/**
	 * This method returns the street of the location
	 * 
	 * @return Returns the row of the location
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * This method sets the row of the location
	 * 
	 * @param street the row of the location
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * This method returns the type of the location
	 * 
	 * @return Returns the type of the location
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * This method sets the type of the location
	 * 
	 * @param type the type of the location
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * This method returns the zip code of the location
	 * 
	 * @return Returns the zip code of the location
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * This method sets the zip code of the location
	 * 
	 * @param zipCode the zip code of the location
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	/**
	 * This method returns the zone of the location
	 * 
	 * @return Returns the zone of the location
	 */
	public String getZone() {
		return zone;
	}
	
	/**
	 * This method sets the zone of the location
	 * 
	 * @param zone the zone of the location
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

		

}
