/*
 * ReservationHistory.java
 *
 * 2012-2-13
 */
package net.geant.autobahn.reservation;

import java.io.Serializable;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationHistory", namespace="reservation.autobahn.geant.net", propOrder={
		"bodID", "description", "startPort", "endPort", "startPortDescription", "endPortDescription",
		"resiliency", "startTime", "endTime", "capacity", "bidirectional", "maxDelay", "mtu", "priority",
		"intState", "startVlan", "endVlan", "authParameters"
})
public class ReservationHistory implements Serializable {

	private static final long serialVersionUID = -98008799451486690L;	
	
	protected String bodID;
	
    protected String startPort;
    protected String endPort;
    protected String startPortDescription;
    protected String endPortDescription;
    protected String description;
    protected String resiliency;
    protected Calendar startTime;
    protected Calendar endTime;
    
    protected long capacity;
    protected boolean bidirectional;
    
    protected int maxDelay;
    protected int mtu;
    protected int priority;
    protected int intState;
    protected int startVlan;
    protected int endVlan;
    
    private String authParameters;
    
    /**
     * Default constructor
     *
     */
    public ReservationHistory() {
        
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns identifier of the <code>ReservationHistory</code> at application level.
     * 
     * @return <code>String</code> identifier
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets identifier of the <code>ReservationHistory</code> at application level.
     * Should not be used manually - reservation is assigned an identifier
     * automatically when an instance is added to a service.
     * 
     * @param bodID identifier to set
     */
    public void setBodID(String bodID) {
        this.bodID = bodID;
    }
    
    /**
	 * @return the startPort
	 */
	public String getStartPort() {
		return startPort;
	}

	/**
	 * @param startPort the startPort to set
	 */
	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}

	/**
	 * @return the endPort
	 */
	public String getEndPort() {
		return endPort;
	}

	/**
	 * @param endPort the endPort to set
	 */
	public void setEndPort(String endPort) {
		this.endPort = endPort;
	}

	/**
	 * @return the startPortDescription
	 */
	public String getStartPortDescription() {
		return startPortDescription;
	}

	/**
	 * @param startPortDescription the startPortDescription to set
	 */
	public void setStartPortDescription(String startPortDescription) {
		this.startPortDescription = startPortDescription;
	}

	/**
	 * @return the endPortDescription
	 */
	public String getEndPortDescription() {
		return endPortDescription;
	}

	/**
	 * @param endPortDescription the endPortDescription to set
	 */
	public void setEndPortDescription(String endPortDescription) {
		this.endPortDescription = endPortDescription;
	}

	/**
	 * @return the startTime
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
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
	 * @return the capacity
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the mtu
	 */
	public int getMtu() {
		return mtu;
	}

	/**
	 * @param mtu the mtu to set
	 */
	public void setMtu(int mtu) {
		this.mtu = mtu;
	}

	/**
	 * @return the maxDelay
	 */
	public int getMaxDelay() {
		return maxDelay;
	}

	/**
	 * @param maxDelay the maxDelay to set
	 */
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	/**
	 * @return the resiliency
	 */
	public String getResiliency() {
		return resiliency;
	}

	/**
	 * @param resiliency the resiliency to set
	 */
	public void setResiliency(String resiliency) {
		this.resiliency = resiliency;
	}

	/**
	 * @return the bidirectional
	 */
	public boolean isBidirectional() {
		return bidirectional;
	}

	/**
	 * @param bidirectional the bidirectional to set
	 */
	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	/**
	 * @return the intState
	 */
	public int getIntState() {
		return intState;
	}

	/**
	 * @param intState the intState to set
	 */
	public void setIntState(int intState) {
		this.intState = intState;
	}

	/**
	 * @return the startVlan
	 */
	public int getStartVlan() {
		return startVlan;
	}

	/**
	 * @param startVlan the startVlan to set
	 */
	public void setStartVlan(int startVlan) {
		this.startVlan = startVlan;
	}

	/**
	 * @return the endVlan
	 */
	public int getEndVlan() {
		return endVlan;
	}

	/**
	 * @param endVlan the endVlan to set
	 */
	public void setEndVlan(int endVlan) {
		this.endVlan = endVlan;
	}

	/**
	 * @return the authParameters
	 */
	public String getAuthParameters() {
		return authParameters;
	}

	/**
	 * @param authParameters the authParameters to set
	 */
	public void setAuthParameters(String authParameters) {
		this.authParameters = authParameters;
	}

	@Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        
        if(obj == null || !(obj instanceof Reservation))
            return false;
        
        Reservation r2 = (Reservation) obj;
        
        return getBodID().equals(r2.getBodID());
    }

    @Override
    public int hashCode() {
        return getBodID().hashCode();
    }

    @Override
    public String toString() {
        return bodID;
    }
}
