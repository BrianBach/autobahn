package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.aai.UserAuthParameters;

/**
 * Describes single reservation request
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationRequest", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity",
		"userInclude", "userExclude", "userVlanId", "mtu",
		"maxDelay",
		"resiliency", "bidirectional", "processNow",
        "authParameters"
})
public class ReservationRequest implements Serializable {
	
	private static final long serialVersionUID = -612896116488675810L;
	
	private String startPort;
	private String endPort;
	private Calendar startTime;
	private Calendar endTime;
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
	private boolean processNow;

    private UserAuthParameters authParameters=new UserAuthParameters();
    
	public ReservationRequest() {
        this.userInclude = new PathInfo();
        this.userExclude = new PathInfo();
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
     * @return mtu size in bytes
     */
    public int getMtu(){
        return mtu;
    }
    
    /**
     * 
     * Set mtu value in bytes
     */
    public void setMtu(int mtu){
        this.mtu = mtu;
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
        				+ getDescription() + "\n";
        res += "    Requested VLAN: " + getUserVlanId() + ", Requested MTU: " + getMtu() + "\n";
        res += "    Priority: " + getPriority() + "\n";
        
        if (getUserInclude() != null) {
            if (getUserInclude().getDomains() != null) {
                res += "    User-included domains: " + getUserInclude().getDomains().size() + "\n";
                for (int i=0; i < getUserInclude().getDomains().size(); i++) {
                    res += "        " + getUserInclude().getDomains().get(i) + "\n";
                }
            }
            if (getUserInclude().getLinks() != null) {
                res += "    User-included links: " + getUserInclude().getLinks().size() + "\n";
                for (int i=0; i < getUserInclude().getLinks().size(); i++) {
                    res += "        " + getUserInclude().getLinks().get(i) + "\n";
                }
            }
        }
        
        if (getUserExclude() != null) {
            if (getUserExclude().getDomains() != null) {
                res += "    User-excluded domains: " + getUserExclude().getDomains().size() + "\n";
                for (int i=0; i < getUserExclude().getDomains().size(); i++) {
                    res += "        " + getUserExclude().getDomains().get(i) + "\n";
                }
            }
            if (getUserExclude().getLinks() != null) {
                res += "    User-excluded links: " + getUserExclude().getLinks().size() + "\n";
                for (int i=0; i < getUserExclude().getLinks().size(); i++) {
                    res += "        " + getUserExclude().getLinks().get(i) + "\n";
                }
            }
        }
        
        if (getAuthParameters() != null) {
            res += "    User identifier: " + getAuthParameters().getIdentifier() + "\n";
            res += "    User organization: " + getAuthParameters().getOrganization() + "\n";
            res += "    User project membership: " + getAuthParameters().getProjectMembership() + "\n";
            res += "    User project role: " + getAuthParameters().getProjectRole() + "\n";
        }
        
        return res;
	}

    public UserAuthParameters getAuthParameters() {
        return authParameters;
    }

    public void setAuthParameters(UserAuthParameters authParameters) {
        this.authParameters = authParameters;
    }
}
