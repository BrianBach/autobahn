package net.geant.autobahn.useraccesspoint;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceResponse", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"userName", "userHomeDomain", "userEmail",
		"reservations"
})
public class ServiceResponse {
		
	private String userName;
	private String userHomeDomain;
	private String userEmail;
	
	@XmlElement(required = true, nillable = true)
	private List<ReservationResponse> reservations;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userHomeDomain
	 */
	public String getUserHomeDomain() {
		return userHomeDomain;
	}

	/**
	 * @param userHomeDomain the userHomeDomain to set
	 */
	public void setUserHomeDomain(String userHomeDomain) {
		this.userHomeDomain = userHomeDomain;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the reservations
	 */
	public List<ReservationResponse> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<ReservationResponse> reservations) {
		this.reservations = reservations;
	}
}
