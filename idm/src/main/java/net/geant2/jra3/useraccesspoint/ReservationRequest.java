package net.geant2.jra3.useraccesspoint;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes single reservation request
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationRequest", namespace="useraccesspoint.jra3.geant2.net", propOrder={
		"startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", "maxDelay",
		"resiliency", "bidirectional", "processNow"
})
public class ReservationRequest {
	
	private String startPort;
	private String endPort;
	private Calendar startTime;
	private Calendar endTime;
	private Priority priority;
	private String description;
	private long capacity;
	private int maxDelay;
	private Resiliency resiliency;
	private boolean bidirectional;
	private boolean processNow;
	
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
	 * @return the processNow
	 */
	public boolean isProcessNow() {
		return processNow;
	}
	/**
	 * @param processNow the processNow to set
	 */
	public void setProcessNow(boolean processNow) {
		this.processNow = processNow;
	}
	
	@Override
	public String toString() {
		String res = "    Start port: " + getStartPort() + ", End port: " + getEndPort() + "\n";
        res += "    Start time: " + getStartTime().getTime() + ", End time: " 
        		+ getEndTime().getTime() + "\n";
        res += "    Capacity: " + getCapacity() + ", Delay: " + getMaxDelay() 
        				+ ", Resiliency: " + getResiliency() + ", Description: " 
        				+ getDescription();
        
        return res;
	}
}
