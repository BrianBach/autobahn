package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceRequest", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"userName", "userHomeDomain", "userEmail", "justification",
		"reservations"
})
public class ServiceRequest implements Serializable {
	
	private static final long serialVersionUID = -2744900026489569640L;
	
	private String userName;
	private String userHomeDomain;
	private String userEmail;
	private String justification;
	
	@XmlElement(required = true, nillable = true)
	private List<ReservationRequest> reservations = new ArrayList<ReservationRequest>();

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
	 * @return the justification
	 */
	public String getJustification() {
		return justification;
	}

	/**
	 * @param justification the justification to set
	 */
	public void setJustification(String justification) {
		this.justification = justification;
	}

	/**
	 * @return the reservations
	 */
	public List<ReservationRequest> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<ReservationRequest> reservations) {
		this.reservations = reservations;
	}
	
	@Override
	public String toString() {
		String res = "Service request: \n"; 
		res += "  Justification: " + getJustification() + "\n";
		res += "  Number of reservations: " + getReservations().size() + "\n";
		
        int num = 1;
        for(ReservationRequest r : getReservations()) {
            res += "  Reservation #" + (num++) + ":\n" + r + "\n";
        }
        
        return res;
	}
}
