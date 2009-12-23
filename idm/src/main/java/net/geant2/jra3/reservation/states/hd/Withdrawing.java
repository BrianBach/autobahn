/**
 * 
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.reservation.HomeDomainReservation;

/**
 * @author jacek
 *
 */
public class Withdrawing extends HomeDomainState {

	/**
	 * @param code
	 * @param label
	 */
	public Withdrawing(int code, String label) {
		super(code, label);
	}

	@Override
	public void reservationWithdrawn(HomeDomainReservation res, String message,
			boolean success) {
		
//		res.releaseResources();
		
		res.fail("WITHDRAW: " + message);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.states.hd.HomeDomainState#reservationActivated(net.geant2.jra3.reservation.HomeDomainReservation, java.lang.String, boolean)
	 */
	@Override
	public void reservationActivated(HomeDomainReservation res, String message,
			boolean success) {
		// Ignore it as it's already withdrawing
		log.info("Activation received, reservation" + res + " is already withdrawing");
	}

	@Override
	public void cancel(HomeDomainReservation res) {
		// Ignore this event - already withdrawing
	}
}
