/**
 * 
 */
package net.geant.autobahn.reservation.states.hd;

import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author jacek
 *
 */
public class Finishing extends HomeDomainState {

	/**
	 * @param code
	 * @param label
	 */
	public Finishing(int code, String label) {
		super(code, label);
	}

    @Override
	public void run(HomeDomainReservation res) {
		super.run(res);
		
		domainFinished(res);
	}

	@Override
    public void reservationFinished(HomeDomainReservation res, String message,
			boolean success) {

		domainFinished(res);
    }
	
	private void domainFinished(HomeDomainReservation res) {
		res.nextDomainFinished();
    	
		if(res.areAllDomainsFinished()) {
			res.switchState(HomeDomainState.FINISHED);
			
			log.info("Reservation [" + res + "] is now finished.");
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationFinished(res.getBodID());
	        }
		}
	}

    @Override
    public void cancel(HomeDomainReservation res) {
        // Ignore this event
        log.debug("Reservation: " + res.getBodID() + " is already finishing...");
    }
}
