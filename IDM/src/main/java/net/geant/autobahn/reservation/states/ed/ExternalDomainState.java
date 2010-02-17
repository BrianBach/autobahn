/*
 * ExternalDomainState.java
 *
 * 2006-11-21
 */
package net.geant.autobahn.reservation.states.ed;

import java.util.Calendar;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.reservation.ExternalReservation;
import net.geant.autobahn.reservation.states.State;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class ExternalDomainState extends State {

    public static final ExternalDomainState LOCAL_CHECK = new LocalCheck(3, "LOCAL_CHECK");
    public static final ExternalDomainState SCHEDULING = new Scheduling(4, "SCHEDULING");
    public static final ExternalDomainState SCHEDULED = new Scheduled(5, "SCHEDULED");
    public static final ExternalDomainState CANCELLING = new Cancelling(6, "CANCELLING");
    public static final ExternalDomainState WITHDRAWING = new Withdrawing(8, "WITHDRAWING");
    public static final ExternalDomainState ACTIVE = new Active(10, "ACTIVE");
    public static final ExternalDomainState FINISHED = new FinalState(21, "FINISHED");
    public static final ExternalDomainState CANCELLED = new FinalState(22, "CANCELLED");
    public static final ExternalDomainState FAILED = new FinalState(23, "FAILED");
    
    private static ExternalDomainState[] states = {
        LOCAL_CHECK, SCHEDULING, SCHEDULED, CANCELLING, WITHDRAWING,
        ACTIVE, FINISHED, CANCELLED, FAILED
    };
    
    public ExternalDomainState(int code, String label) {
        super(code, label);
    }

    public void stateEnter(ExternalReservation res) {
        log.debug("ENTERING: " + this + " [" + res + "]");
    }

    public void stateExit(ExternalReservation res) {
        log.debug("EXITTING: " + this + " [" + res + "]");
    }
    
    public void run(ExternalReservation res) {
        log.debug("EXECUTING: " + this + " [" + res + "]");
    }

    public void cancel(ExternalReservation res) {
        throw new IllegalStateException("Cancel - wrong state: " + this);
    }

    public void withdraw(ExternalReservation res) {
        throw new IllegalStateException("Withdraw - wrong state: " + this);
    }
    
    public void modify(ExternalReservation res, Calendar startTime, Calendar endTime) {
    	throw new IllegalStateException("Modify - wrong state: " + this);
    }
    
    public void recover(ExternalReservation res) {
        log.debug(res + " RECOVERING..." );
    }
    
    public void reservationActivated(ExternalReservation res, String message,
			boolean success) {
        throw new IllegalStateException("Activated - wrong state" + this);
    }

    public void reservationCancelled(ExternalReservation res, String message,
			boolean success) {
        throw new IllegalStateException("Cancelled - wrong state" + this);
    }

	public void reservationWithdrawn(ExternalReservation res, String message,
			boolean success) {
		throw new IllegalStateException("Withdrawn - wrong state" + this);
	}
    
    public void reservationFinished(ExternalReservation res, String message,
			boolean success) {
        throw new IllegalStateException("Finished - wrong state" + this);
    }

    public void reservationScheduled(ExternalReservation res, int msgCode,
			String args, boolean success, GlobalConstraints global) {
        throw new IllegalStateException("Scheduled - wrong state" + this);
    }
    
    public void reservationModified(ExternalReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {
        throw new IllegalStateException("Modified - wrong state" + this);
    }
    
    public void domainDown(ExternalReservation res, String domain) {
        // TODO Auto-generated method stub
        
    }

    public void domainUp(ExternalReservation res, String domain) {
        // TODO Auto-generated method stub
        
    }

    public static ExternalDomainState getState(int i) {
        for(ExternalDomainState st : states) {
            if(st.getCode() == i) {
                return st;
            }
        }

        throw new IllegalArgumentException("State [" + i + "] doesn't exist");
    }
}
