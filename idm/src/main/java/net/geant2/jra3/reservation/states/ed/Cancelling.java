/*
 * Cancelling.java
 *
 * 2006-11-28
 */
package net.geant2.jra3.reservation.states.ed;

import net.geant2.jra3.reservation.ExternalReservation;


/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Cancelling extends ExternalDomainState {

    public Cancelling(int code, String label) {
        super(code, label);
    }

    @Override
    public void reservationCancelled(ExternalReservation res, String message,
			boolean success) {
        
        res.reportCancel(message, success);
        
        res.switchState(ExternalDomainState.CANCELLED);
    }
    
    @Override
    public void recover(ExternalReservation res) {
        // TODO Auto-generated method stub
        
    }
}
