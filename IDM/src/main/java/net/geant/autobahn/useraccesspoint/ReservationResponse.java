package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationResponse", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"bodID", "state", "message",
		"startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", 
        "userInclude", "userExclude", "userVlanId", "mtu",
		"maxDelay",
		"resiliency", "bidirectional"
})
public class ReservationResponse {
	
	private String bodID;
	private State state;
	private String message;
	private String startPort,  endPort;
	private Calendar startTime, endTime;
	private Priority priority;
	private String description;
	private long capacity;
    private PathInfo userInclude;
    private PathInfo userExclude;
    private int userVlanId;
    private int mtu;
	private int maxDelay;
	private Resiliency resiliency;
	private boolean bidirectional;
		
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
     * Gets the value of the userInclude property.
     * 
     * @return
     *     possible object is
     *     {@link PathInfo }
     *     
     */
    public PathInfo getUserInclude() {
        return userInclude;
    }

    /**
     * Sets the value of the userInclude property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathInfo }
     *     
     */
    public void setUserInclude(PathInfo value) {
        this.userInclude = value;
    }

    /**
     * Gets the value of the userExclude property.
     * 
     * @return
     *     possible object is
     *     {@link PathInfo }
     *     
     */

    public PathInfo getUserExclude() {
        return userExclude;
    }

    /**
     * Sets the value of the userExclude property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathInfo }
     *     
     */
    public void setUserExclude(PathInfo value) {
        this.userExclude = value;
    }

    /**
     * Gets the value of the userVlanId property.
     * 
     */
    public int getUserVlanId() {
        return userVlanId;
    }

    /**
     * Sets the value of the userVlanId property.
     * 
     */
    public void setUserVlanId(int value) {
        this.userVlanId = value;
    }
    
    /**
     * 
     * Gets mtu size in bytes
     */
    public int getMtu(){
        return mtu;
    }
    
    /**
     * 
     * Sets mtu size in bytes
     */
    public void setMtu(int mtu){
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
	public Resiliency getResiliency() {
		return resiliency;
	}
	/**
	 * @param resiliency the resiliency to set
	 */
	public void setResiliency(Resiliency resiliency) {
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
}
