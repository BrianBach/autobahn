/**
 * 
 */
package net.geant2.jra3.reservation.states.ed;

import net.geant2.jra3.reservation.ExternalReservation;
import net.geant2.jra3.reservation.ReservationStatusListener;

/**
 * @author jacek
 *
 */
public class Withdrawing extends ExternalDomainState {

	/**
	 * @param code
	 * @param label
	 */
	public Withdrawing(int code, String label) {
		super(code, label);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.states.ed.ExternalDomainState#reservationWithdrawn(net.geant2.jra3.reservation.ExternalReservation, java.lang.String, boolean)
	 */
	@Override
	public void reservationWithdrawn(ExternalReservation res, String message,
			boolean success) {
		
		log.info(res + " WITHDRAWN: " + message);
		
		res.reportWithdraw(message, success);
		
		res.switchState(ExternalDomainState.FAILED);
		
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationProcessingFailed(res.getBodID(), message);
        }
	}

	@Override
	public void withdraw(ExternalReservation res) {
		// Ignore it - already withdrawing
	}
}
