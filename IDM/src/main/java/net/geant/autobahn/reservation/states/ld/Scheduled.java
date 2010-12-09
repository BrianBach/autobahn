/*
 * Scheduled.java
 *
 * 2006-11-28
 */
package net.geant.autobahn.reservation.states.ld;

import java.util.Calendar;

import net.geant.autobahn.idcp.Autobahn2OscarsConverter;
import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Scheduled extends LastDomainState {

    public Scheduled(int code, String label) {
        super(code, label);
    }

    @Override
    public void cancel(LastDomainReservation res) {
    	
        if (res.isIdcpReservation() && res.getNextDomainAddress().contains(res.getIdcpServer())) {
        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter(res.getIdcpServer());
        	client.cancelReservation(res.getBodID());
        }
    	
        res.releaseResources();
        res.reportCancel("Cancel OK", true);
        res.switchState(LastDomainState.CANCELLED);
    }

    /* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.states.ld.LastDomainState#withdraw(net.geant.autobahn.reservation.LastDomainReservation)
	 */
	@Override
	public void withdraw(LastDomainReservation res) {
		
        if (res.isIdcpReservation() && res.getNextDomainAddress().contains(res.getIdcpServer())) {
        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter(res.getIdcpServer());
        	client.cancelReservation(res.getBodID());
        }
		
		res.releaseResources();
        log.info(this + " FAILURE: WITHDRAW");
        res.reportWithdraw("WITHDRAW", true);
        res.switchState(LastDomainState.FAILED);
        
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationProcessingFailed(res.getBodID(), "WITHDRAW");
        }
	}

	@Override
	public void modify(LastDomainReservation res, Calendar startTime,
			Calendar endTime) {
		
		boolean possible = res.checkModification(startTime, endTime);
		
		String msg = "Modification not possible in domain: " + res.getLocalDomainID();

		if(possible) {
			msg = "Reservation modified in all domains";
			res.modifyResourcesReservation(startTime, endTime);
			
			res.setStartTime(startTime);
			res.setEndTime(endTime);
		}
		
		res.reportModification(startTime, endTime, msg, possible);
	}

	@Override
    public void recover(LastDomainReservation res) {
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getStartTime()) >= 0) {
			res.releaseResources();
			
	        log.info(this + " FAILURE: WITHDRAW - Activation missed.");

	        res.switchState(LastDomainState.FAILED);
	        
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationProcessingFailed(res.getBodID(), "FAILURE: WITHDRAW - Activation missed.");
	        }
		}
*/
	}
}
