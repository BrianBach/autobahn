/*
 * LastDomainState.java
 *
 * 2006-11-17
 */
package net.geant.autobahn.reservation.states.ld;

import java.util.Calendar;

import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.states.State;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class LastDomainState extends State {

    public final static LastDomainState LOCAL_CHECK = new LocalCheck(3, "LOCAL_CHECK");
    public final static LastDomainState G_CONSTRAINTS = new GConstraints(9, "GLOBAL_CONSTRAINTS");
    public final static LastDomainState SCHEDULED = new Scheduled(5, "SCHEDULED");
    public final static LastDomainState ACTIVE = new Active(10, "ACTIVE");
    public final static LastDomainState FINISHED = new FinalState(21, "FINISHED");
    public final static LastDomainState CANCELLED = new FinalState(22, "CANCELLED");
    public final static LastDomainState FAILED = new FinalState(23, "FAILED");
    
    private static LastDomainState[] states = {
        LOCAL_CHECK, G_CONSTRAINTS, SCHEDULED, FAILED,
        CANCELLED, ACTIVE, FINISHED
    };
    
    /**
     * @param code
     * @param label
     */
    public LastDomainState(int code, String label) {
        super(code, label);
    }

    public void stateEnter(LastDomainReservation res) {
        log.debug("ENTERING: " + this + " [" + res + "]");
    }
    
    public void stateExit(LastDomainReservation res) {
        log.debug("EXITTING: " + this + " [" + res + "]");
    }

    public void run(LastDomainReservation res) {
        log.debug("EXECUTING: " + this + " [" + res + "]");
    }

    public void cancel(LastDomainReservation res) {
        log.debug("CANCELLING: " + res);
    }

    public void modify(LastDomainReservation res, Calendar startTime,
			Calendar endTime) {
    	log.debug("WITHDRAWING: " + res);
    }
    
    public void withdraw(LastDomainReservation res) {
    	log.debug("WITHDRAWING: " + res);
    }
    
    public void recover(LastDomainReservation res) {
        log.debug("RECOVERING: " + res);
    }

    public void domainDown(LastDomainReservation res, String domain) {
        
    }

    public void domainUp(LastDomainReservation res, String domain) {
        
    }

    public static LastDomainState getState(int i) {
        for(LastDomainState st : states) {
            if(st.getCode() == i) {
                return st;
            }
        }

        throw new IllegalArgumentException("State [" + i + "] doesn't exist");
    }
}
