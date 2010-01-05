/*
 * DeferredCancel.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.reservation.HomeDomainReservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class DeferredCancel extends HomeDomainState {

    public DeferredCancel(int code, String label) {
        super(code, label);
    }

    @Override
    public void reservationScheduled(HomeDomainReservation res, int msgCode, String args,
    		boolean success, GlobalConstraints global) {
        
        if(success) {
        	res.forwardCancel();
            
            res.switchState(HomeDomainState.CANCELLING);
        } else {
            res.pathFailed(msgCode, args);
            res.fail();
        }
    }
}
