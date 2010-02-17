package net.geant.autobahn.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

import net.geant.autobahn.idm.ReservationProcessor;
import net.geant.autobahn.idm.ServiceScheduler;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

/**
 * Manages set of reservations 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Service", namespace="reservation.autobahn.geant.net", propOrder={
		"state", "bodID", "justification", "priority", 
		"user", "reservations"
})
public class Service implements ReservationStatusListener {

	@XmlTransient
	private Logger log = Logger.getLogger(Service.class);
	@XmlAccessorType(XmlAccessType.FIELD)
	private enum State { SCHEDULING, SCHEDULED, FAILED, CANCELLING, CANCELLED, FINISHED };
	private State state;
    private String bodID;
    private String justification;
    private int priority;
    private User user;
    private List<AutobahnReservation> reservations = new ArrayList<AutobahnReservation>();
    @XmlTransient
    private ReservationProcessor reservationProcessor;
    @XmlTransient
    private String curResv;
    @XmlTransient
    private boolean cancel;
    @XmlTransient
    private int numScheduled, numCancelled, numFinished;
    @XmlTransient
    private ServiceStatusListener listener;
    @XmlTransient
    private List<String> scheduled = new ArrayList<String>();
    
    
    public Service() {
    	
    }
    
    public Service(String bodID, ReservationProcessor reservationProcessor) {
    			
    	this.bodID = bodID;
    	this.reservationProcessor = reservationProcessor;
    }
    
	/**
	 * @param listener the listener to set
	 */
	public void setListener(ServiceStatusListener listener) {
		this.listener = listener;
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
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the reservations
	 */
	public List<AutobahnReservation> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<AutobahnReservation> reservations) {
		this.reservations = reservations;
	}
	
    /* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationActive(java.lang.String)
	 */
	public void reservationActive(String reservationId) {

		// can safely ignore this event
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationCancelled(java.lang.String)
	 */
	public void reservationCancelled(String reservationId) {

		if (!reservationId.equals(curResv)) {
			log.error("reservationCancelled - " + reservationId + ", should be " + curResv);
			return;
		}
		
		// this event is valid only during CANCELLING
		if (state != State.CANCELLING)
			log.error("reservationCancelled - invalid state: " + state.name());
		
		numCancelled++;
		synchronized (this) {
			this.notify();
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationFinished(java.lang.String)
	 */
	public void reservationFinished(String reservationId) {
		numFinished++;
		scheduled.remove(reservationId);
		numScheduled--;
		
		if(numFinished == reservations.size())
			state = State.FINISHED;
	}

	public void reservationModified(String reservationId, boolean success) {
		// ignore it
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationProcessingFailed(java.lang.String, java.lang.String)
	 */
	public void reservationProcessingFailed(String reservationId, String cause) {
		
		// if service is scheduling, issue rollback
		if (state == State.SCHEDULING) {
			log.debug(reservationId + " failed, rollback");
			// do not cancel this reservation
			if(scheduled.contains(reservationId)) {
				scheduled.remove(reservationId);
				numScheduled--;
			}
			cancel = true;
			synchronized (this) {
				this.notify();
			}
		} else if (state == State.SCHEDULED) {
			
			log.debug(reservationId + " failed, rollback");
			// do not cancel this reservation
			scheduled.remove(reservationId);
			// issue rollback
			listener.failed(this.getBodID());
		} else if (state == State.CANCELLING) {
			scheduled.remove(reservationId);
		} else {
			log.warn(reservationId + " call in wrong context, service is " + state.name());
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationScheduled(java.lang.String)
	 */
	public void reservationScheduled(String reservationId) {
		
		if(state == State.CANCELLED || state == State.CANCELLING || state == State.FAILED) {
			log.info("Reservation " + reservationId + " scheduled, but service is CANCELLED");
			
			try {
				reservationProcessor.cancelReservation(reservationId);
			} catch (NoSuchReservationException e) {
				log.warn("No reservation " + reservationId, e);
			}
			
			return;
		}
		
		// this event is valid only during SCHEDULING
		if (state != State.SCHEDULING)
			log.warn("reservationScheduled - invalid state: " + state.name());
		
		numScheduled++;
		scheduled.add(reservationId);
		synchronized (this) {
			this.notify();
		}
	}
	
	/**
	 * Tries to cancel running or scheduled service
	 */
	public synchronized void cancel() {

		// if service is running issue rollback
		if (state == State.SCHEDULING) {
			
			log.debug("cancel request - " + numScheduled + "/" + reservations.size() + " reservations");
			cancel = true;
		} else if (state == State.SCHEDULED) {

			log.debug("cancel request - " + numScheduled + " reservations scheduled");
			rollback();
		} else {
			log.debug("cancel request - wrong context, service is " + state.name());
		}
	}
	
	private void rollback() {
		
		state = State.CANCELLING;
		List<AutobahnReservation> toCancel = new ArrayList<AutobahnReservation>();
		
		for(int i = 0; i < reservations.size(); i++) {
			AutobahnReservation res = reservations.get(i);

			// get scheduled reservation which are not being withdrawn in the moment
			if(scheduled.contains(res.getBodID()) && 
					!(res.getStateObject().equals(HomeDomainState.WITHDRAWING) || 
					  res.getStateObject().equals(HomeDomainState.CANCELLING))) {
				toCancel.add(res);
			}
		}

		log.debug("Rollback - Cancelling: " + toCancel.size() + " reservations");
		
		for(AutobahnReservation res : toCancel) {
			curResv = res.getBodID();
			log.debug("Rollback - Cancelling: " + curResv);
			
			try {
				reservationProcessor.cancelReservation(curResv);
			} catch (NoSuchReservationException e) {
				log.warn("No reservation " + curResv, e);
			}

			synchronized (this) { 
				try {
					this.wait();
				} catch (InterruptedException e) { } 
			}
		}
		
		log.debug("Cancelled: " + numCancelled + " of: " + toCancel.size());
		if (numCancelled == toCancel.size()) {
			state = State.CANCELLED;
		} else 
			state = State.FAILED;

		log.debug("rollback service - " + this.getBodID() + " - " + state.name());
	}
	
	public void execute() {
		
		state = State.SCHEDULING;
		for (AutobahnReservation resv : reservations) {
			
			curResv = resv.getBodID();
			reservationProcessor.runReservation(resv);
			
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) { }
			}
			// reservation failed, rollback service
			if (cancel) {
				log.debug("Cancelling: " + numScheduled + " reservations");
				rollback();
				break;
			}
		}
		if (numScheduled == reservations.size()) {
			state = State.SCHEDULED;
			listener.scheduled(this.getBodID());
		} else if (numCancelled != 0) {
			state = State.CANCELLED;
		} else {
			state = State.FAILED;
			// do not call failed
		}
		log.debug("exec service end - " + this.getBodID() + " - " + state.name());
	}

	/**
     * Adds reservation to service, reservation is assigned an unique identifier.
     * 
     * @param res <code>Reservation</code> object
     */
    public void addReservation(HomeDomainReservation res) {
        if(res.getBodID() == null)
        	res.setBodID(getBodID() + "_res_" + (reservations.size() + 1));
        
    	res.addStatusListener(this);
        this.reservations.add(res);
    }

    /**
     * Removes reservation from the service.
     * 
     * @param res <code>Reservation</code> object to be removed
     */
    public void removeReservation(Reservation res) {
        reservations.remove(res);
    }
	
    /**
     * 
     * @param resProcessor
     * @param scheduler
     */
    public void recover(ReservationProcessor resProcessor, ServiceScheduler scheduler) {
    	
    	this.reservationProcessor = resProcessor;
    	scheduler.addService(this);
    	this.state = State.SCHEDULED;
    	
    	for(Reservation r : reservations) {
    		HomeDomainReservation res = (HomeDomainReservation) r;
    		
    		if(res.getStateObject().equals(HomeDomainState.ACTIVE)
    				|| res.getStateObject().equals(HomeDomainState.SCHEDULED)) {
    			scheduled.add(res.getBodID());
    		}
    		
    		res.addStatusListener(this);
    	}
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
        return "Service - bodId: " + bodID + 
               ", justification: " + justification + 
               ", priority: " + priority + 
               ", numReservations: " + (reservations != null ? reservations.size() : 0) +
               ", user: " + (user != null ? user.getName() : "null"); 
    }
}
