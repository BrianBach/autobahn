/*
 * Cancelling.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Cancelling extends HomeDomainState {

    public Cancelling(int code, String label) {
        super(code, label);
    }

    @Override
    public void reservationCancelled(HomeDomainReservation res, String message,
			boolean success) {

        if(success) {
        	//res.releaseResources();
        	
            for(ReservationStatusListener listener : res.getStatusListeners()) {
                listener.reservationCancelled(res.getBodID());
            }
            
            res.switchState(HomeDomainState.CANCELLED);
        }
    }

	@Override
	public void reservationActivated(HomeDomainReservation res, String message,
			boolean success) {
		// Ignore this event
	}

	@Override
	public void cancel(HomeDomainReservation res) {
		// Ignore this event
		log.debug("Reservation: " + res.getBodID() + " is already cancelling...");
	}
}
