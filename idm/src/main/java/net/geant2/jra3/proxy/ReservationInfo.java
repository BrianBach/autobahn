package net.geant2.jra3.proxy;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant2.jra3.network.Link;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationInfo", propOrder={
		"bodID", "startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", "maxDelay",
		"resiliency", "bidirectional", "userVlans", "calculatedConstraints", "path", "state" })
public class ReservationInfo implements Serializable {
	
	private static final long serialVersionUID = 1298057546075119213L;

	protected String bodID;
	
    private String startPort;
    private String endPort;
    private Calendar startTime;
    private Calendar endTime;

    private int priority;
    private String description;
    
    private long capacity;
    private int maxDelay;
    private String resiliency;
    private boolean bidirectional;
    
    private String userVlans;
    private String calculatedConstraints;
    private Link[] path;
    private int state;
    
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
	 * @return the calculatedConstraints
	 */
	public String getCalculatedConstraints() {
		return calculatedConstraints;
	}
	/**
	 * @param calculatedConstraints the calculatedConstraints to set
	 */
	public void setCalculatedConstraints(String calculatedConstraints) {
		this.calculatedConstraints = calculatedConstraints;
	}
	/**
	 * @return the userVlans
	 */
	public String getUserVlans() {
		return userVlans;
	}
	/**
	 * @param userVlans the userVlans to set
	 */
	public void setUserVlans(String userVlans) {
		this.userVlans = userVlans;
	}
	/**
	 * @return the path
	 */
	public Link[] getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(Link[] path) {
		this.path = path;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
}
