/**
 * 
 */
package net.geant.autobahn.administration;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.network.Path;
import net.geant.autobahn.useraccesspoint.PortType;

/**
 * @author Jacek
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationType", namespace="administration.autobahn.geant.net", propOrder={
		"bodID", "startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", "mtu", "maxDelay",
		"resiliency", "bidirectional", "path", "state", "failureCause"
})
public class ReservationType implements Serializable {

	private static final long serialVersionUID = -8452872563877633746L;

	protected String bodID;

	protected PortType startPort;
	protected PortType endPort;
	protected Calendar startTime;
	protected Calendar endTime;

	protected int priority;
	protected String description;

	protected long capacity;
	protected int mtu;
	protected int maxDelay;
	protected String resiliency;
	protected boolean bidirectional;

	protected Path path;
	protected int state;
	
	protected String failureCause;
	
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
	public PortType getStartPort() {
		return startPort;
	}
	/**
	 * @param startPort the startPort to set
	 */
	public void setStartPort(PortType startPort) {
		this.startPort = startPort;
	}
	/**
	 * @return the endPort
	 */
	public PortType getEndPort() {
		return endPort;
	}
	/**
	 * @param endPort the endPort to set
	 */
	public void setEndPort(PortType endPort) {
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
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	/**
	 * @return the intState
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param intState the intState to set
	 */
	public void setState(int intState) {
		this.state = intState;
	}
    /**
     * @return the failureCause
     */
    public String getFailureCause() {
        return failureCause;
    }
    /**
     * @param failureCause the failureCause to set
     */
    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

	public String getInfo() {
		return getBodID() + " [" + getStartPort() + " - " + getEndPort() + "] "
		+ "(" + getStartTime().getTime() + " - " + getEndTime().getTime() + ") " 
		+ getCapacity() + " bps | " + getState();
	}
	
    @Override
    public String toString() {
        return bodID;
    }
}
