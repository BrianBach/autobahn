/*
 * Active.java
 *
 * 2006-11-28
 */
package net.geant2.jra3.reservation.states.ed;

import java.util.Calendar;

import net.geant2.jra3.reservation.ExternalReservation;
import net.geant2.jra3.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Active extends ExternalDomainState {

    /**
     * @param code
     */
    public Active(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(ExternalReservation res) {
        super.run(res);
        
        Calendar now = Calendar.getInstance();
        Calendar endTime = res.getEndTime();
        
        long msec = endTime.getTimeInMillis() - now.getTimeInMillis();
        
        log.info("Reservation [" + res + "] is now active, " + (msec / 1000) + " seconds left to finish");
        
        //Report previous domain
        res.reportActive("Active OK", true);
        
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationActive(res.getBodID());
        }
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.reservation.states.State#cancel()
     */
    @Override
    public void cancel(ExternalReservation res) {

    	ExternalDomainState.SCHEDULED.cancel(res);
    }
    
    /* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.states.ed.ExternalDomainState#withdraw(net.geant2.jra3.reservation.ExternalReservation)
	 */
	@Override
	public void withdraw(ExternalReservation res) {
		
		ExternalDomainState.SCHEDULED.withdraw(res);
	}

	@Override
	public void modify(ExternalReservation res, Calendar startTime,
			Calendar endTime) {
		
		ExternalDomainState.SCHEDULED.modify(res, startTime, endTime);
	}

	@Override
	public void reservationModified(ExternalReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {
		
		ExternalDomainState.SCHEDULED.reservationModified(res, startTime,
				endTime, message, success);
	}
	
	@Override
	public void reservationFinished(ExternalReservation res, String message,
			boolean success) {
    	
    	if(success) {
    		res.reportFinish(message, success);
    	} else {
    		// report Finish for all the domains after this one plus this one
    		int repeats = res.getPath().getAfterDomainCount(res.getLocalDomainID()) + 1;
    		
    		for(int i = 0; i < repeats; i++) {
    			res.reportFinish("Late finish", false);
    		}
    	}
    	
		res.switchState(ExternalDomainState.FINISHED);
		
		log.info("Reservation [" + res + "] is now finished.");
		
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationFinished(res.getBodID());
        }
	}

	@Override
	public void recover(ExternalReservation res) {
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getEndTime()) >= 0) {
			res.releaseResources();
			res.switchState(ExternalDomainState.FINISHED);
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationFinished(res.getBodID());
	        }
		}
*/	}
	
}
