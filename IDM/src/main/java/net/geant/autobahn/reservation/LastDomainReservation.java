/*
 * LastDomainReservation.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation;

import java.util.Calendar;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.states.State;
import net.geant.autobahn.reservation.states.ld.LastDomainState;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class LastDomainReservation extends ExternalReservation {

    private LastDomainState stateObject = null;
    
    /**
     * Default constructor
     */
    public LastDomainReservation() {
        
    }

    /**
     * Creates a <code>LastDomainReservation</code> object and puts it into
     * its initial state.
     * 
     * @param startPort <code>Port</code> start port
     * @param endPort <code>Port</code> end port
     * @param startTime <code>Calendar</code> start time
     * @param endTime <code>Calendar</code> end time
     * @param priority <code>int</code> priority of the reservation
     */
    public LastDomainReservation(Port startPort, Port endPort,
            Calendar startTime, Calendar endTime, int priority) {
        super(startPort, endPort, startTime, endTime, priority);
    }

    @Override
    public void run() {
        stateObject.run(this);
    }

    @Override
    public void cancel() {
    	stateObject.cancel(this);
    }

    @Override
	public void modify(Calendar startTime, Calendar endTime) {
    	stateObject.modify(this, startTime, endTime);
	}

	@Override
    public void withdraw() {
    	stateObject.withdraw(this);
    }
    
    public void recover() {
    	stateObject.recover(this);
    }
    
    @Override
    public State getStateObject() {
        return stateObject;
    }

    @Override
    public void setState(int state) {
        this.intState = state;
        this.stateObject = LastDomainState.getState(state);
        this.stateObject.stateEnter(this);
    }

    /**
     * Reservation's state is changed to a new state. New state is not being
     * executed.
     * 
     * @param newState <code>LastDomainState</code> state to set
     */
    public void setState(LastDomainState newState) {
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
     * @param newState <code>LastDomainState</code> state to switch to
     */
    public void switchState(LastDomainState newState) {
        setState(newState);
        
        // execute the state
        newState.run(this);
    }
    
    @Override
    public void gotoInitialState() {
        setState(LastDomainState.LOCAL_CHECK);
    }

    @Override
    public void fail(int code, String args) {
    	String msg = ReservationErrors.getInfo(code, args);
    	
        log.info(this + " FAILURE: " + msg);

        switchState(LastDomainState.FAILED);
        
        for(ReservationStatusListener listener : getStatusListeners()) {
            listener.reservationProcessingFailed(getBodID(), msg);
        }
        
        reportSchedule(code, args, false);
    }
    
	@Override
	public void activate(boolean success) {
		if(success) {
			switchState(LastDomainState.ACTIVE);
		} else {
			reportActive("Activation Failed", success);
		}
	}

	@Override
	public void finish(boolean success) {
		if(success) {
	        log.info("Reservation [" + getBodID() + "] has finished");
		} else {
	        log.info("Reservation [" + getBodID() + "] has finished late");
		}

        switchState(LastDomainState.FINISHED);
        
        for(ReservationStatusListener listener : statusListeners) {
            listener.reservationFinished(getBodID());
        }
		
		reportFinish("Late finish", success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.Reservation#getReservationParameters(java.lang.String)
	 */
	@Override
	public ReservationParams getReservationParameters(String domainID) {
		ReservationParams params = super.getReservationParameters(domainID);

		DomainConstraints ucons = globalConstraints.getDomainConstraints("user-egress");
		
		if(ucons != null) {
			params.setPathConstraintsEgress(ucons.getFirstPathConstraints());
		}
		
		return params;
	}
	
}
