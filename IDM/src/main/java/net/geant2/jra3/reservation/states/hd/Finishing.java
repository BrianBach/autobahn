/**
 * 
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.ReservationStatusListener;

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

}
