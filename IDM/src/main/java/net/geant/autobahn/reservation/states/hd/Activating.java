/**
 * 
 */
package net.geant.autobahn.reservation.states.hd;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.ReservationTimeout;

/**
 * @author jacek
 *
 */
public class Activating extends HomeDomainState {

	public static final int MAX_ACTIVATION_TIME_DEFAULT = 300 * 1000; //in msec
	
	public Activating(int code, String label) {
		super(code, label);
	}

    @Override
	public void run(HomeDomainReservation res) {
		super.run(res);
		
		res.nextDomainActivated();
    	
		if(res.areAllDomainsActivated()) {
			res.switchState(HomeDomainState.ACTIVE);
		} else {
		    int timeout = AccessPoint.getInstance().getTimeoutProperty("timeout.activating");
		    if (timeout == 0) {
		        timeout = MAX_ACTIVATION_TIME_DEFAULT;
		    }
			res.addTimeout(new ActivatingTimeout(res.getBodID()), timeout);
		}
	}

	@Override
    public void reservationActivated(HomeDomainReservation res, String message,
			boolean success) {
    	
    	if(success) {
    		res.nextDomainActivated();
    	
    		if(res.areAllDomainsActivated()) {
    			res.switchState(HomeDomainState.ACTIVE);
    			res.cancelTimeouts();
    		}
    	} else {
    		res.cancelTimeouts();
    		HomeDomainState.SCHEDULED.withdraw(res);
    	}
    }
    
    @Override
    public void reservationFinished(HomeDomainReservation res, String message,
			boolean success) {
        
		res.switchState(HomeDomainState.FAILED);
    	res.cancelTimeouts();

		log.info("Reservation [" + res + "] should now be finished. Activation in some domains failed.");
			
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationProcessingFailed(res.getBodID(), "Activation in some domains failed.");
        }
    }
	
	@Override
    public void cancel(HomeDomainReservation res) {
		
		HomeDomainState.SCHEDULED.cancel(res);
    }
	
	/**
	 * 
	 * @author jacek
	 *
	 */
	class ActivatingTimeout extends ReservationTimeout {

		public ActivatingTimeout(String res) {
			super(res);
		}

		@Override
		public void run() {
			log.warn(resId + " Activation is timed out...");
			try {
				AccessPoint.getInstance().reportActive(resId, "Activation timed out...", false);
			} catch (NoSuchReservationException e) {
				// Ignore it
			}
		}
	}
}
