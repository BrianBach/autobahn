/*
 * ExternalReservation.java
 *
 * 2006-02-20
 */
package net.geant.autobahn.reservation;

import java.util.Calendar;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.reservation.states.State;
import net.geant.autobahn.reservation.states.ed.ExternalDomainState;

/**
 * Subclass of <code>Reservation</code>. Instances of the class are executed
 * in the domains along the path except of first one (home domain) and last one.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class ExternalReservation extends AutobahnReservation {

    private ExternalDomainState stateObject = null;
    
    /**
     * Default constructor
     */
    public ExternalReservation() {
        
    }
    
    /**
     * Creates an <code>ExternalDomainReservation</code> object and puts it into
     * its initial state.
     * 
     * @param startPort <code>Port</code> start port
     * @param endPort <code>Port</code> end port
     * @param startTime <code>Calendar</code> start time
     * @param endTime <code>Calendar</code> end time
     * @param priority <code>int</code> priority of the reservation
     */
    public ExternalReservation(Port startPort, Port endPort,
            Calendar startTime, Calendar endTime, int priority) {
        super(startPort, endPort, startTime, endTime, priority);
    }
    
    public void run() {
    	stateObject.run(this);
    }

    public void cancel() {
    	stateObject.cancel(this);
    }

    public void withdraw() {
    	stateObject.withdraw(this);
    }
    
    public void modify(Calendar startTime, Calendar endTime) {
    	stateObject.modify(this, startTime, endTime);
	}

	public void recover() {
    	stateObject.recover(this);
    }
    
    public void reservationScheduled(int msgCode, String arguments,
			boolean success, GlobalConstraints global) {
    	stateObject.reservationScheduled(this, msgCode, arguments, success, global);
    }

    public void reservationCancelled(String message, boolean success) {
    	stateObject.reservationCancelled(this, message, success);
    }

	public void reservationWithdrawn(String message, boolean success) {
		stateObject.reservationWithdrawn(this, message, success);
	}

	public void reservationActivated(String message, boolean success) {
        // Just forward it
    	reportActive(message, success);
    }

    public void reservationModified(Calendar startTime, Calendar endTime,
			String message, boolean success) {
    	stateObject.reservationModified(this, startTime, endTime, message, success);
	}

    public void reservationFinished(String message, boolean success) {
    	stateObject.reservationFinished(this, message, success);
    }

    public void domainDown(String domain) {
    	stateObject.domainDown(this, domain);
    }

    public void domainUp(String domain) {
    	stateObject.domainUp(this, domain);
    }
    
    /**
     * Called when the reservation has failed for some reasons. Report is passed
     * to the previous domain of the path. Report contain information used to
     * determine reson of the failure - code of the failure and optional
     * arguments that could be use i.e. to determine concrete network element
     * that causes a failure.
     * 
     * @param code <code>int</code> code of the failure's reason
     * @param args <code>String</code> optional arguments
     */
    public void fail(int code, String args) {
    	String msg = ReservationErrors.getInfo(code, args);
    	
        log.info(this + " FAILURE: " + msg);

        reportSchedule(code, args, false);
        
        switchState(ExternalDomainState.FAILED);
        
        for(ReservationStatusListener listener : getStatusListeners()) {
            listener.reservationProcessingFailed(getBodID(), msg);
        }
    }
    
    @Override
    public State getStateObject() {
        return stateObject;
    }

    @Override
    public void setState(int state) {
        this.intState = state;
        this.stateObject = ExternalDomainState.getState(state);
        this.stateObject.stateEnter(this);
    }
    
    /**
     * Reservation's state is changed to a new state. New state is not being
     * executed.
     * 
     * @param newState <code>ExternalDomainState</code> state to set
     */
    public void setState(ExternalDomainState newState) {
        if(stateObject != null) {
            stateObject.stateExit(this);
        }

        this.intState = newState.getCode();
        this.stateObject = newState;
        
        stateObject.stateEnter(this);
    }
    
    /**
     * Reservation switches to a new state and executes it.
     * 
     * @param newState <code>ExternalDomainState</code> state to switch to
     */
    public void switchState(ExternalDomainState newState) {
        setState(newState);
        
        // execute the state
        newState.run(this);
    }

    @Override
    public void gotoInitialState() {
        setState(ExternalDomainState.LOCAL_CHECK);
    }

    /**
     * 
     * @param message
     * @param success
     */
    public void reportCancel(String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportCancellation(getBodID(), message, success);
        } catch (Exception e) {
            log.error(this + " reportCancel: " + e.getMessage(), e);
        }
    }

    /**
     * 
     * @param message
     * @param success
     */
    public void reportWithdraw(String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportWithdraw(getBodID(), message, success);
        } catch (Exception e) {
            //log.error(this + " reportWithdraw: " + e.getMessage(), e);
            log.warn("Reservation [" + this + "] reportWithdraw failed: " + e.getMessage());
        }
    }
    
    /**
     * 
     * @param code
     * @param message
     * @param success
     */
    public void reportSchedule(int code, String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportSchedule(getBodID(), code, message, success, getGlobalConstraints());
        } catch (Exception e) {
        	// Means that previous IDM is down and we need to withdraw
        	log.warn("Reservation [" + this + "] reportSchedule failed: " + e.getMessage() + 
			", Withdrawing reservation...");
            
            this.withdraw();
        }
    }
    
    /**
     * 
     * @param message
     * @param success
     */
    public void reportActive(String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportActive(getBodID(), message, success);
        } catch (Exception e) {
        	// Means that previous IDM is down and we need to withdraw
        	log.warn("Reservation [" + this + "] reportActive failed: " + e.getMessage() + 
        			", Withdrawing reservation...");
        	this.withdraw();
        }
    }
    
    /**
     * 
     * @param message
     * @param success
     */
    public void reportFinish(String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportFinished(getBodID(), message, success);
        } catch (Exception e) {
            //log.error(this + " reportFinish: " + e.getMessage(), e);
        	log.warn("Reservation [" + this + "] reportFinish Error, " + e.getMessage());
        }
    }
    
    /**
     * 
     * @param message
     * @param success
     */
    public void reportModification(Calendar sTime, Calendar eTime,
			String message, boolean success) {
        try {
        	Interdomain client = new InterdomainClient(getPrevDomainAddress());
        	client.reportModify(bodID, new TimeRange(sTime, eTime), message, success);
        } catch (Exception e) {
            log.error(this + " reportModification: " + e.getMessage(), e);
        }
    }
    
	public void activate(boolean success) {
		if(success) {
			switchState(ExternalDomainState.ACTIVE);
		} else {
			reportActive("Activation Failed", false);
		}
	}

	public void finish(boolean success) {
		reservationFinished("Finished", success);
	}
}
