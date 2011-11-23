/*
 * Reservation3.java
 *
 * 2006-11-13
 */
package net.geant.autobahn.reservation;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.aai.UserAuthParameters;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.StateOper;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Reservation", namespace="reservation.autobahn.geant.net", propOrder={
		"bodID", "startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", "mtu", "maxDelay",
		"resiliency", "bidirectional", "globalConstraints", "path", "intState", "fake", "authParameters"})		
public class Reservation implements Serializable {

	private static final long serialVersionUID = -98008799451486690L;	
	
	protected String bodID;
	
    protected Port startPort;
    protected Port endPort;
    protected Calendar startTime;
    protected Calendar endTime;

    protected int priority;
    protected String description;
    
    protected long capacity;
    protected int mtu;
    protected int maxDelay;
    protected String resiliency;
    protected boolean bidirectional;
    
    protected GlobalConstraints globalConstraints;
    protected Path path;
    protected int intState;
    
    protected boolean fake;

    @XmlTransient
    protected StateOper operationalStatus;
    
    private UserAuthParameters authParameters = new UserAuthParameters();
    
    
    /**
     * Default constructor
     *
     */
    public Reservation() {
        
    }

    /**
     * Creates proper <code>Reservation</code> object and puts it into
     * its initial state.
     * 
     * @param startPort <code>Port</code> start port
     * @param endPort <code>Port</code> end port
     * @param startTime <code>Calendar</code> start time
     * @param endTime <code>Calendar</code> end time
     * @param priority <code>int</code> priority of the reservation
     */
    public Reservation(Port startPort, Port endPort, Calendar startTime,
            Calendar endTime, int priority) {
        this.startPort = startPort;
        this.endPort = endPort;
        setStartTime(startTime);
        setEndTime(endTime);
        this.priority = priority;
    }

    /**
     * @return Returns the bidirectional.
     */
    public boolean isBidirectional() {
        return bidirectional;
    }

    /**
     * @param bidirectional The bidirectional to set.
     */
    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    /**
     * @return Returns the constraints.
     */
    public GlobalConstraints getGlobalConstraints() {
        return globalConstraints;
    }

    /**
     * @param constraints The constraints to set.
     */
    public void setGlobalConstraints(GlobalConstraints constraints) {
        this.globalConstraints = constraints;
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
     * @return Returns the resiliency.
     */
    public String getResiliency() {
        return resiliency;
    }

    /**
     * @param resiliency The resiliency to set.
     */
    public void setResiliency(String resiliency) {
        this.resiliency = resiliency;
    }

    /**
     * @return Returns the maxDelay.
     */
    public int getMaxDelay() {
        return maxDelay;
    }

    /**
     * @param maxDelay The maxDelay to set.
     */
    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    /**
     * @return Returns the path.
     */
    public Path getPath() {
        return path;
    }

    /**
     * @param path The path to set.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * @return Returns the priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority The priority to set.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Returns identifier of the <code>Reservation</code> at application level.
     * 
     * @return <code>String</code> identifier
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets identifier of the <code>Reservation</code> at application level.
     * Should not be used manually - reservation is assigned an identifier
     * automatically when an instance is added to a service.
     * 
     * @param bodID identifier to set
     */
    public void setBodID(String bodID) {
        this.bodID = bodID;
    }

    /**
     * @return Returns the capacity.
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * @param capacity The capacity to set.
     */
    public void setCapacity(long capacity) {
        this.capacity = capacity;
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
     * Set mtu size in bytes
     */
    public void setMtu(int mtu){
        this.mtu = mtu;
    }
    
    public StateOper getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(StateOper operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

    /**
     * @return Returns the endPort.
     */
    public Port getEndPort() {
        return endPort;
    }

    /**
     * @param endPort The endPort to set.
     */
    public void setEndPort(Port endPort) {
        this.endPort = endPort;
    }

    /**
     * @return Returns the endTime.
     */
    public Calendar getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The endTime to set.
     */
    public void setEndTime(Calendar endTime) {
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(endTime.getTime());
        
        this.endTime = tmp;
    }

    /**
     * @return Returns the startPort.
     */
    public Port getStartPort() {
        return startPort;
    }

    /**
     * @param startPort The startPort to set.
     */
    public void setStartPort(Port startPort) {
        this.startPort = startPort;
    }

    /**
     * @return Returns the startTime.
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The startTime to set.
     */
    public void setStartTime(Calendar startTime) {
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(startTime.getTime());
        
        this.startTime = tmp;
    }
    
    /**
     * 
     * @return
     */
    public int getState() {
    	return intState;
    }

    /**
     * Sets proper state, specified specified code.
     * 
     * @param state <code>int</code> code
     */
    public void setState(int state) {
    	this.intState = state;
    }

    /**
     * 
     * @return
     */
    public boolean isFake() {
		return fake;
	}

    /**
     * 
     * @param fake
     */
	public void setFake(boolean fake) {
		this.fake = fake;
	}

    /**
     * Whether this is a Reservation that spans an IDCP cloud
     * 
     * @return true if the reservation is an IDCP one, false if it is within AutoBAHN only.
     */
    public boolean isIdcpReservation() {
        return getStartPort().isIdcpPort() || getEndPort().isIdcpPort();
    }

    /**
     * Whether this is a Reservation that originates from AutoBAHN and
     * ends in an IDCP cloud
     * 
     * @return true if the reservation is from AutoBAHN to an IDCP cloud, false otherwise
     */
    public boolean isAb2IdcpReservation() {
        return getEndPort().isIdcpPort();
    }

    /**
     * Whether this is a Reservation that originates from an IDCP cloud and
     * ends in AutoBAHN
     * 
     * @return true if the reservation is from an IDCP cloud to AutoBAHN, false otherwise
     */
    public boolean isIdcp2AbReservation() {
        return getStartPort().isIdcpPort();
    }

    /**
     * Returns the IDCP server where the request should be send, if this
     * reservation spans an IDCP cloud
     * 
     * @return the IDCP server if this is an IDCP reservation, null otherwise
     */
    public String getIdcpServer() {
        String startIdcpServer = getStartPort().getNode().getProvisioningDomain().getAdminDomain().getIdcpServer();
        String endIdcpServer = getEndPort().getNode().getProvisioningDomain().getAdminDomain().getIdcpServer();
        return (startIdcpServer==null) ? endIdcpServer : startIdcpServer;
    }

	/**
	 * Creates object with reservation parameters needed to reserve proper path
	 * inside domain.
	 * 
	 * @param domainID
	 * @return
	 */
    public ReservationParams getReservationParameters(String domainID) {
        ReservationParams par = new ReservationParams();
        
        par.setStartTime(startTime);
        par.setEndTime(endTime);
        par.setCapacity(capacity);
        par.setMaxDelay(maxDelay);
        par.setResiliency(resiliency);
        par.setBidirectional(bidirectional);
        
        // Ingress constraints
        DomainConstraints dcon = globalConstraints.getDomainConstraints(domainID + "-ingress");
        if(dcon != null) {
	        if(!dcon.isValid())
	        	throw new IllegalStateException("Domain Constraints empty");

	        par.setPathConstraintsIngress(dcon.getFirstPathConstraints());
        }

        // Egress constraints
        dcon = globalConstraints.getDomainConstraints(domainID + "-egress");
        if(dcon != null) {
	        if(!dcon.isValid())
	        	throw new IllegalStateException("Domain Constraints empty");

	        par.setPathConstraintsEgress(dcon.getFirstPathConstraints());
        }
        
        //requested VLAN for the domain
        par.setMtu(mtu);
        par.setAuthParameters(authParameters);
        
        return par;
    }
	
    /**
     * 
     * @return
     */
    public String getInfo() {
    	return this.toString();
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

    public UserAuthParameters getAuthParameters() {
        return authParameters;
    }


    public void setAuthParameters(UserAuthParameters authParameters) {
        this.authParameters = authParameters;
    }
}
