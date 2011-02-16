package net.geant.autobahn.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.reservation.User;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceType", namespace="administration.autobahn.geant.net", propOrder={
		"bodID", "justification", "priority", "user", "reservations"
})
public class ServiceType implements Serializable {
	
	private static final long serialVersionUID = 7426855467383158387L;
	
	private String bodID;
    private String justification;
    private int priority;
    private User user;
    private List<ReservationType> reservations = new ArrayList<ReservationType>();
    
    public ServiceType() {
    	
    }

	/**
	 * @return the bodID
	 */
	public String getBodID() {
		return bodID;
	}

	/**
	 * @param bodID the bodID to set
	 */
	public void setBodID(String bodID) {
		this.bodID = bodID;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReservationType> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationType> reservations) {
		this.reservations = reservations;
	}
}
