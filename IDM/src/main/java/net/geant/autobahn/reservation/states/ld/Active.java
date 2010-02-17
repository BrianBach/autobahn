/*
 * Active.java
 *
 * 2006-11-28
 */
package net.geant.autobahn.reservation.states.ld;

import java.util.Calendar;

import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Active extends LastDomainState {

    public Active(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(LastDomainReservation res) {
        super.run(res);
        
        Calendar now = Calendar.getInstance();
        Calendar endTime = res.getEndTime();
        
        long msec = endTime.getTimeInMillis() - now.getTimeInMillis();
        
        log.info("Reservation [" + res + "] is now active, " + (msec / 1000) + " seconds left to finish");

        res.reportActive("Activation OK", true);
        
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationActive(res.getBodID());
        }
    }
    
    @Override
    public void cancel(LastDomainReservation res) {
    	LastDomainState.SCHEDULED.cancel(res);
    }
    
    /* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.states.ld.LastDomainState#withdraw(net.geant.autobahn.reservation.LastDomainReservation)
	 */
	@Override
	public void withdraw(LastDomainReservation res) {
		LastDomainState.SCHEDULED.withdraw(res);
	}

	@Override
	public void modify(LastDomainReservation res, Calendar startTime,
			Calendar endTime) {
		LastDomainState.SCHEDULED.modify(res, startTime, endTime);
	}

	@Override
    public void recover(LastDomainReservation res) {
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getEndTime()) >= 0) {
			res.releaseResources();
			res.switchState(ExternalDomainState.FINISHED);
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationFinished(res.getBodID());
	        }
		}
*/		
    }
}
