/*
 * Scheduled.java
 *
 * 2006-11-28
 */
package net.geant.autobahn.reservation.states.ed;

import java.util.Calendar;

import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.reservation.ExternalReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Scheduled extends ExternalDomainState {

    public Scheduled(int code, String label) {
        super(code, label);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.states.State#cancel()
     */
    @Override
    public void cancel(ExternalReservation res) {

        res.switchState(ExternalDomainState.CANCELLING);

        res.forwardCancel();
        
        res.releaseResources();
    }

    /* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.states.ed.ExternalDomainState#withdraw(net.geant.autobahn.reservation.ExternalReservation)
	 */
	@Override
	public void withdraw(ExternalReservation res) {
		
		res.switchState(ExternalDomainState.WITHDRAWING);
		
		boolean self = false;
		
		try {
			res.forwardWithdraw();
		} catch (NoSuchReservationException e) {
			// Means that the reservation has been already withdrawn in the next domains.
			// Probably the domain was down.
			self = true;
		}
		
		res.releaseResources();
		
		if(self) {
			log.info(res + " SELF WITHDRAWAL");
			
			res.reportWithdraw("WITHDRAW OK", false);
			
			res.switchState(ExternalDomainState.FAILED);
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationProcessingFailed(res.getBodID(), "SELF WITHDRAWAL");
	        }
		}
	}

	@Override
	public void modify(ExternalReservation res, Calendar startTime,
			Calendar endTime) {
		
		boolean possible = res.checkModification(startTime, endTime);
		
		if(possible) {
			try {
                res.forwardModify(startTime, endTime);
            } catch (Exception e) {
                log.error("Modification was not possible " + e.getMessage(), e);
                res.reportModification(startTime, endTime,
                        "Modification not possible in domain: " + res.getLocalDomainID() +
                        " due to exception: " + e.getMessage(),
                        false);
            }
		} else {
			res.reportModification(startTime, endTime,
					"Modification not possible in domain: " + res.getLocalDomainID(),
					false);
		}
	}

	@Override
	public void reservationModified(ExternalReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {
		
		if(success) {
			res.modifyResourcesReservation(startTime, endTime);
			
			res.setStartTime(startTime);
			res.setEndTime(endTime);
		}
		
		res.reportModification(startTime, endTime, message, success);
	}

	@Override
    public void recover(ExternalReservation res) {
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getStartTime()) >= 0) {
			res.releaseResources();
			
	        log.info(this + " FAILURE: WITHDRAW - Activation missed.");

	        res.switchState(ExternalDomainState.FAILED);
	        
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationProcessingFailed(res.getBodID(), "FAILURE: WITHDRAW - Activation missed.");
	        }
		}
*/
	}
}
