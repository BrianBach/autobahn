/**
 * 
 */
package net.geant.autobahn.reservation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.reservation.states.State;

import org.apache.log4j.Logger;

/**
 * @author jacek
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AutobahnReservation", namespace="reservation.autobahn.geant.net", propOrder={
		"bodID", "startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", 
	    "userInclude", "userExclude", "userVlanId", "mtu",
	    "maxDelay",
		"resiliency", "bidirectional", "globalConstraints", "path", "intState", "fake", "authParameters" })
public abstract class AutobahnReservation extends Reservation implements Runnable,
		ReservationEvents, ReservationReportEvents {

	@XmlTransient
    protected static Logger log = Logger.getLogger(AutobahnReservation.class);
    
    @XmlTransient
    protected List<ReservationStatusListener> statusListeners = new ArrayList<ReservationStatusListener>();
    
    @XmlTransient
    protected Idm2Dm resourcesReservation = null;
    @XmlTransient
    private String localDomainID;

    
    public AutobahnReservation() {
    	
    }
    
    public AutobahnReservation(Port startPort, Port endPort,
            Calendar startTime, Calendar endTime, int priority) {
        super(startPort, endPort, startTime, endTime, priority);
        gotoInitialState();
    }

    /**
     * 
     * @return Returns state object
     */
    public abstract State getStateObject();

    public abstract void gotoInitialState();

    /**
     * 
     * @return Returns int value of state
     */
    public int getState() {
    	if(getStateObject() == null)
    		return -1;
        return getStateObject().getCode();
    }
    
    /**
	 * @return the localDomainID
	 */
	public String getLocalDomainID() {
		return localDomainID;
	}

	/**
	 * @param localDomainID the localDomainID to set
	 */
	public void setLocalDomainID(String localDomainID) {
		this.localDomainID = localDomainID;
	}
	
    /**
     * @return Returns the statusListeners.
     */
    public List<ReservationStatusListener> getStatusListeners() {
        return statusListeners;
    }

    /**
     * @param statusListeners The statusListeners to set.
     */
    public void setStatusListeners(List<ReservationStatusListener> statusListeners) {
        this.statusListeners = statusListeners;
    }

    /**
     * 
     * @param listener
     */
    public void addStatusListener(ReservationStatusListener listener) {
        statusListeners.add(listener);
    }

    /**
     * 
     * @param listener
     */
    public void removeStatusListener(ReservationStatusListener listener) {
        statusListeners.remove(listener);
    }
	
    /**
     * 
     * @param resourcesReservation
     */
    public void setResourcesReservation(Idm2Dm resourcesReservation) {
        this.resourcesReservation = resourcesReservation;
    }
    
	/**
	 * 
	 * @return
	 * @throws OversubscribedException
	 */
	public DomainConstraints checkResources() throws OversubscribedException {
        Link[] links = path.getLinksForDomain(localDomainID);
        ReservationParams params = getReservationParameters(localDomainID);
        
        return resourcesReservation.checkResources(links, params);
    }
    
    /**
     * 
     * @throws ConstraintsAlreadyUsedException 
     * @throws OversubscribedException 
     * @throws RemoteException 
     */
    public void reserveResources() throws ConstraintsAlreadyUsedException,
    		OversubscribedException {
        Link[] links = path.getLinksForDomain(localDomainID);
        ReservationParams params = getReservationParameters(localDomainID);
        
        resourcesReservation.addReservation(bodID, links, params);
    }
    
    /**
     * Releases resources reserved by the reservation. Released resources are
     * capacity on the links, as well as other network resources such as
     * reserved VLAN identifiers.
     * @throws RemoteException 
     */
    public void releaseResources() {
        try {
        	resourcesReservation.removeReservation(bodID);
        } catch (Exception e) {
            log.error(this + " - Can't connect with DM. " + e.getMessage(), e);
        }
    }

    /**
     * 
     * @param startTime
     * @param endTime
     */
    public boolean checkModification(Calendar startTime, Calendar endTime) {
    	TimeRange time = new TimeRange();
    	time.setStartTime(startTime);
    	time.setEndTime(endTime);
    	return resourcesReservation.checkModification(bodID, time);
    }
    
    /**
     * 
     * @param startTime
     * @param endTime
     */
    public void modifyResourcesReservation(Calendar startTime, Calendar endTime) {
    	TimeRange time = new TimeRange();
    	time.setStartTime(startTime);
    	time.setEndTime(endTime);
    	resourcesReservation.modifyReservation(bodID, time);
    }
    
    /**
     * Returns address of the previous domain on the reservation's path before
     * specified domain.
     * 
     * @param domainID <code>String</code> identifier of the domain
     * @return <code>String</code> address of the previous domain
     */
    public String getPrevDomainAddress() {
        Link ingress = getPath().getIngress(localDomainID);
        
        if(localDomainID.equals(ingress.getStartDomainID()))
            return ingress.getEndDomainID();
            
        return ingress.getStartDomainID();
    }

    /**
     * Returns address of the next domain on the reservation's path after
     * specified domain.
     * 
     * @param domainID <code>String</code> identifier of the domain
     * @return <code>String</code> address of the next domain
     */
    public String getNextDomainAddress() {
        Link egress = getPath().getEgress(localDomainID);
        
        if(localDomainID.equals(egress.getEndDomainID()))
            return egress.getStartDomainID();
        
        return egress.getEndDomainID();
    }
    
    /**
     * Indicates whether specified domain is first domain on the reservation's
     * path.
     * 
     * @param domainID <code>String</code> identifier of the domain
     * @return <code>boolean</code> true if domain is first domain, false -
     *         otherwise
     */
    public boolean isHomeDomain() {
        return getPath().isHomeDomain(localDomainID);
    }

    /**
     * Indicates whether specified domain is Last domain on the reservation's
     * path.
     * 
     * @param domainID <code>String</code> identifier of the domain
     * @return <code>boolean</code> true if domain is last domain, false -
     *         otherwise
     */
    public boolean isLastDomain() {
        return getPath().isLastDomain(localDomainID);
    }

	public void forwardCancel() {
    	String address = getNextDomainAddress();
        
        try {
        	Interdomain client = new InterdomainClient(address);
        	client.cancelReservation(getBodID());
        } catch (NoSuchReservationException e1) {
        	log.warn(this + " cancel: No such reservation in the next domain");
        } catch (Exception e) {
            log.error(this + " cancel: " + e.getMessage(), e);
        }
    }

    public void forwardWithdraw() throws NoSuchReservationException {
    	String address = getNextDomainAddress();
        
        try {
        	Interdomain client = new InterdomainClient(address);
        	client.withdrawReservation(getBodID());
        } catch (NoSuchReservationException e1) {
        	log.warn(this + " withdraw: No such reservation in the next domain");
        	throw e1;
        } catch (Exception e) {
            throw new NoSuchReservationException(this + " " + e.getMessage());
        }
    }
    
    public void forwardSchedule() throws Exception {
    	String address = getNextDomainAddress();
    	
       	Interdomain client = new InterdomainClient(address);
        client.scheduleReservation(this);
    }
 
    public void forwardModify(Calendar start, Calendar end) throws Exception {
    	String address = getNextDomainAddress();
    	
       	Interdomain client = new InterdomainClient(address);
    	client.modifyReservation(getBodID(), new TimeRange(start, end));
    }
    
    /**
     * Factory method for obtaining object of proper <code>Reservation</code>
     * subclass for specified domain. Returned <code>Reservation</code>
     * instance has its path already set.
     * 
     * @param domainID 
     *            <code>String</code> identifier of the domain
     * @param path
     *            <code>Path</code> object to determine which concrete
     *            <code>Reservation</code> is to be created.
     * @return concrete <code>Reservation</code> object
     */
    public static AutobahnReservation createReservation(Reservation src, String domainID) {
        
        AutobahnReservation r = null;
        
        if(src.getPath().isHomeDomain(domainID)) {
            r = new HomeDomainReservation();
        } else if(src.getPath().isLastDomain(domainID)) {
            r = new LastDomainReservation();
        } else {
            r = new ExternalReservation();
        }
        
        r.setPath(src.getPath());
        r.setBodID(src.getBodID());
        r.setBidirectional(src.isBidirectional());
        r.setCapacity(src.getCapacity());
        r.setDescription(src.getDescription());
        r.setEndTime(src.getEndTime());
        r.setGlobalConstraints(src.getGlobalConstraints());
        r.setMaxDelay(src.getMaxDelay());
        r.setPriority(src.getPriority());
        r.setResiliency(src.getResiliency());
        r.setStartTime(src.getStartTime());
        r.setStartPort(src.getStartPort());
        r.setEndPort(src.getEndPort());
        r.setFake(src.isFake());
        r.setMtu(src.getMtu());
        r.setAuthParameters(src.getAuthParameters());

        r.setLocalDomainID(domainID);
        r.gotoInitialState();
        
        return r;
    }

	@Override
	public String getInfo() {
		return getBodID() + " [" + getStartPort() + " - " + getEndPort() + "] "
				+ "(" + getStartTime().getTime() + " - " + getEndTime().getTime() + ") " 
				+ getCapacity() + " bps | " + getStateObject();
	}
}
