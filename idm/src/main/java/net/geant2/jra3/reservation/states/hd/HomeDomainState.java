/*
 * State.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import java.util.Calendar;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.states.State;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class HomeDomainState extends State {

    public static HomeDomainState ACCEPTED = new Accepted(1, "ACCEPTED");
    public static HomeDomainState PATHFINDING = new Pathfinding(2, "PATHFINDING");
    public static HomeDomainState LOCAL_CHECK = new LocalCheck(3, "LOCAL_CHECK");
    public static HomeDomainState SCHEDULING = new Scheduling(4, "SCHEDULING");
    public static HomeDomainState SCHEDULED = new Scheduled(5, "SCHEDULED");
    public static HomeDomainState CANCELLING = new Cancelling(6, "CANCELLING");
    public static HomeDomainState DEFERRED_CANCEL = new DeferredCancel(7, "DEFERRED_CANCEL");
    public static HomeDomainState WITHDRAWING = new Withdrawing(8, "WITHDRAWING");
    public static HomeDomainState ACTIVATING = new Activating(9, "ACTIVATING");
    public static HomeDomainState ACTIVE = new Active(10, "ACTIVE");
    public static HomeDomainState FINISHING = new Finishing(11, "FINISHING");
    public static HomeDomainState FINISHED = new FinalState(21, "FINISHED");
    public static HomeDomainState CANCELLED = new FinalState(22, "CANCELLED");
    public static HomeDomainState FAILED = new FinalState(23, "FAILED");

    private static HomeDomainState[] states = {
        ACCEPTED, PATHFINDING, LOCAL_CHECK, SCHEDULING, SCHEDULED,
        	CANCELLING, WITHDRAWING, DEFERRED_CANCEL, ACTIVATING, ACTIVE,
        	FINISHING, FINISHED, CANCELLED, FAILED
    };
    
    public HomeDomainState(int code, String label) {
        super(code, label);
    }
    
    public void run(HomeDomainReservation res) {
        log.debug("EXECUTING: " + this + " [" + res + "]");
    }

    public void stateEnter(HomeDomainReservation res) {
        log.debug("ENTERING: " + this + " [" + res + "]");
    }
    
    public void stateExit(HomeDomainReservation res) {
        log.debug("EXITTING: " + this + " [" + res + "]");
    }

    public void cancel(HomeDomainReservation res) {
        throw new IllegalStateException("Cancel - wrong state: " + this);
    }

    public void withdraw(HomeDomainReservation res) {
        throw new IllegalStateException("Withdraw - wrong state: " + this);
    }

    public void modify(HomeDomainReservation res, Calendar start, Calendar end) {
        throw new IllegalStateException("Modify - wrong state: " + this);
    }
    
    public void reservationWithdrawn(HomeDomainReservation res, String message,
			boolean success) {
        throw new IllegalStateException("Withdrawn - wrong state: " + this);
    }
    
    public void reservationActivated(HomeDomainReservation res, String message, boolean success) {
        throw new IllegalStateException("Activated - wrong state" + this);
    }

    public void reservationModified(HomeDomainReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {
        throw new IllegalStateException("Modified - wrong state" + this);
    }
    
    public void reservationCancelled(HomeDomainReservation res, String message, boolean success) {
        throw new IllegalStateException("Cancelled - wrong state" + this);
    }

    public void reservationFinished(HomeDomainReservation res, String message, boolean success) {
    	log.debug("Reservation finished " + res.getBodID() + " wrong state ?");
        //throw new IllegalStateException("Finished - wrong state! " + this);
    }

    public void reservationScheduled(HomeDomainReservation res, int msgCode, String args,
    		boolean success, GlobalConstraints global) {
        throw new IllegalStateException("Scheduled - wrong state! " + this);
    }
    
    public void recover(HomeDomainReservation res) {
        log.debug(res + " RECOVERING..." );
    }
    
    public void domainDown(HomeDomainReservation res, String domain) {
        log.debug("Domain down: " + domain);
    }

    public void domainUp(HomeDomainReservation res, String domain) {
        log.debug("Domain up: " + domain);
    }

    public static HomeDomainState getState(int i) {
        for(HomeDomainState st : states) {
            if(st.getCode() == i) {
                return st;
            }
        }

        throw new IllegalArgumentException("State [" + i + "] doesn't exist");
    }
}
