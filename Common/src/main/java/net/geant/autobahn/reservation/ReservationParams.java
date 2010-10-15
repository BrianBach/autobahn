/**
 * 
 */
package net.geant.autobahn.reservation;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.PathConstraints;

import net.geant.autobahn.aai.UserAuthParameters;

/**
 * Keeps reservation parameters: capacity, start and end time, network
 * constraints and other.
 * 
 * @author Michal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationParams", namespace="reservation.autobahn.geant.net", propOrder={
		"capacity", "maxDelay", "resiliency", "bidirectional",
		"startTime", "endTime", "pathConstraints", "userVlanId", "mtu",
		"authParameters"
})
public class ReservationParams {
	@XmlTransient
	private long id;
	
	private long capacity;
    private int maxDelay;
    private String resiliency;
    private boolean bidirectional;

    private Calendar startTime;
    private Calendar endTime;

    private PathConstraints pathConstraints;
    
    private int userVlanId;
    private int mtu;
    
    private UserAuthParameters authParameters=new UserAuthParameters();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Capacity in bps
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity Capacity to be set (in bps)
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public int getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	public String getResiliency() {
		return resiliency;
	}

	public void setResiliency(String resiliency) {
		this.resiliency = resiliency;
	}

	/**
	 * @return Whether the reservation is bidirectional
	 */
	public boolean isBidirectional() {
		return bidirectional;
	}

	/**
	 * @param bidirectional Whether the reservation is bidirectional
	 */
	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	/**
	 * @return Calendar start time of a reservation
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime Calendar start time to be set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Calendar end time of a reservation
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime Calendar end time to set
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Returns network constraints for a reservation.
	 * 
	 * @return the pathConstraints
	 */
	public PathConstraints getPathConstraints() {
		return pathConstraints;
	}

	/**
	 * @param pathConstraints the pathConstraints to set
	 */
	public void setPathConstraints(PathConstraints pathConstraints) {
		this.pathConstraints = pathConstraints;
	}

    /**
     * @return the userVlanId
     */
    public int getUserVlanId() {
        return userVlanId;
    }

    /**
     * @param userVlanId the userVlanId to set
     */
    public void setUserVlanId(int userVlanId) {
        this.userVlanId = userVlanId;
    }
    
    /**
     * @return the userVlanId
     */
    public int getMtu() {
        return mtu;
    }

    /**
     * @param userVlanId the userVlanId to set
     */
    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public UserAuthParameters getAuthParameters() {
        return authParameters;
    }

    public void setAuthParameters(UserAuthParameters authParameters) {
        this.authParameters = authParameters;
    }
}
