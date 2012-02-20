package net.geant.autobahn.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Manages set of reservation history objects
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceHistory", namespace="reservation.autobahn.geant.net", propOrder={
		"bodID", "justification", "adminDomain", 
		"userAuth", "reservations", "priority"
})
public class ServiceHistory {

    private String bodID;
    private String justification;
    private String userAuth;
    private String adminDomain;
    
    private int priority;
    private List<ReservationHistory> reservations = new ArrayList<ReservationHistory>();
    
    public ServiceHistory() {
    	
    }
    
    public ServiceHistory(String bodID) {
    	this.bodID = bodID;
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
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the user
	 */
	public String getUserAuth() {
		return userAuth;
	}

	/**
	 * @param user the user to set
	 */
	public void setUserAuth(String user) {
		this.userAuth = user;
	}

	/**
	 * @return the adminDomain
	 */
	public String getAdminDomain() {
		return adminDomain;
	}

	/**
	 * @param adminDomain the adminDomain to set
	 */
	public void setAdminDomain(String adminDomain) {
		this.adminDomain = adminDomain;
	}

	/**
	 * @return the history reservations
	 */
	public List<ReservationHistory> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the history reservations to set
	 */
	public void setReservations(List<ReservationHistory> reservations) {
		this.reservations = reservations;
	}
	
	/**
     * Adds reservation history to service history, reservation is assigned an unique identifier.
     * 
     * @param res <code>ReservationHistory</code> object
     */
    public void addReservation(ReservationHistory res) {
        if(res.getBodID() == null)
        	res.setBodID(getBodID() + "_res_" + (reservations.size() + 1));
        
        this.reservations.add(res);
    }
	
    
	@Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof Service))
            return false;
        
        Service s2 = (Service) obj;
        return bodID.equals(s2.getBodID());
    }

    @Override
    public int hashCode() {
        return bodID.hashCode();
    }

    @Override
    public String toString() {
        return "Service History- bodId: " + bodID + 
               ", justification: " + justification + 
               ", priority: " + priority + 
               ", numReservations: " + (reservations != null ? reservations.size() : 0) +
               ", user: " + (userAuth != null ? userAuth : "null"); 
    }
}
