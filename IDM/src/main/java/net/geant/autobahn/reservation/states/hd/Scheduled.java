/*
 * Scheduled.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation.states.hd;


import java.util.Calendar;

import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.proxy.Autobahn2OscarsConverter;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Scheduled extends HomeDomainState {

    public Scheduled(int code, String label) {
        super(code, label);
    }

    @Override
    public void reservationActivated(HomeDomainReservation res, String message,
			boolean success) {
    	
    	if(success) {
   			res.switchState(HomeDomainState.ACTIVATING);
    	} else {
   			withdraw(res);
    	}
    }

	@Override
	public void withdraw(HomeDomainReservation res) {
		if(res.isLastDomain()) {
			// FORWARD TO OSCARS
	        if("http://client-domain.internet2.edu".equals(res.getNextDomainAddress()) || 
	        		res.getNextDomainAddress().contains("oscars-domain")) {
	        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter();
	        	client.cancelReservation(res.getBodID());
	        }
			
	        res.releaseResources();

	        res.fail("Activation failed");
	        return;
		}
		
        res.switchState(HomeDomainState.WITHDRAWING);

        boolean self = false;
        
        try {
			res.forwardWithdraw();
		} catch (NoSuchReservationException e) {
			self = true;
		}
        
        res.releaseResources();
        
        if(self) {
        	res.fail("SELF WITHDRAWAL");
        }
	}

	@Override
    public void cancel(HomeDomainReservation res) {

		if(res.isLastDomain()) {
			// FORWARD TO OSCARS
	        if("http://client-domain.internet2.edu".equals(res.getNextDomainAddress()) || 
	        		res.getNextDomainAddress().contains("oscars-domain")) {
	        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter();
	        	client.cancelReservation(res.getBodID());
	        }
			
	        res.releaseResources();

	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationCancelled(res.getBodID());
	        }
	        
	        res.switchState(HomeDomainState.CANCELLED);
	        return;
		}
		
        res.switchState(HomeDomainState.CANCELLING);
    	
        res.forwardCancel();
        
        res.releaseResources();
    }

	@Override
	public void modify(HomeDomainReservation res, Calendar start, Calendar end) {
		
		boolean possible = res.checkModification(start, end);
		
		if(possible) {
			// The only domain on the path
			if(res.isLastDomain()) {
				reservationModified(res, start, end, "Reservation modified", true);
				return;
			}
			
			try {
			    res.forwardModify(start, end);
            } catch (Exception e) {
                log.error("HomeDomainReservation modification was not possible " + e.getMessage(), e);
                for(ReservationStatusListener listener : res.getStatusListeners()) {
                    listener.reservationModified(res.getBodID(), false);
                }
            }
		} else {
			for(ReservationStatusListener listener : res.getStatusListeners()) {
				listener.reservationModified(res.getBodID(), false);
			}
		}
	}

	@Override
	public void reservationModified(HomeDomainReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {

		if(success) {
			res.modifyResourcesReservation(startTime, endTime);
			
			res.setStartTime(startTime);
			res.setEndTime(endTime);
		}
		
		for(ReservationStatusListener listener : res.getStatusListeners()) {
			listener.reservationModified(res.getBodID(), success);
		}
	}
	
	@Override
	public void recover(HomeDomainReservation res) {
		
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getStartTime()) >= 0) {
			res.releaseResources();
			
			res.fail("WITHDRAW: " + "Activation missed.");
		}
*/
	}
}
