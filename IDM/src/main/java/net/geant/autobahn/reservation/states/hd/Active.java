/*
 * Active.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation.states.hd;

import java.util.Calendar;

import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.network.StatisticsEntry;
import net.geant.autobahn.network.dao.StatisticsEntryDAO;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Active extends HomeDomainState {

    public Active(int code, String label) {
        super(code, label);
    }

    @Override
	public void run(HomeDomainReservation res) {
    	super.run(res);
    	
    	StatisticsEntryDAO dao = HibernateIdmDAOFactory.getInstance().getStatisticsEntryDAO();
    	Calendar now = Calendar.getInstance();
        Calendar endTime = res.getEndTime();
        
        long msec = endTime.getTimeInMillis() - now.getTimeInMillis();
        
        log.info("Reservation [" + res + "] is now active, " + (msec / 1000) + " seconds left to finish");
        
        // Calculate time from submission to activation and save in DB statistics
        long setuptime = now.getTimeInMillis() - res.getStartTime().getTimeInMillis();
        dao.update(new StatisticsEntry(res.getBodID(), false, setuptime));
        
		for(ReservationStatusListener listener : res.getStatusListeners()) {
			listener.reservationActive(res.getBodID());
		}
	}

	@Override
    public void cancel(HomeDomainReservation res) {
		HomeDomainState.SCHEDULED.cancel(res);
    }
    
    @Override
    public void reservationFinished(HomeDomainReservation res, String message,
			boolean success) {
        
    	if(success) {
   			res.switchState(HomeDomainState.FINISHING);
    	} else {
			res.switchState(HomeDomainState.FINISHED);
			
			log.info("Reservation [" + res + "] is now finished. Late finish.");
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationFinished(res.getBodID());
	        }
    	}
    }

	@Override
	public void modify(HomeDomainReservation res, Calendar start, Calendar end) {
		HomeDomainState.SCHEDULED.modify(res, start, end);
	}

	@Override
	public void reservationModified(HomeDomainReservation res,
			Calendar startTime, Calendar endTime, String message,
			boolean success) {
		
		HomeDomainState.SCHEDULED.reservationModified(res, startTime, endTime,
				message, success);
	}

	@Override
	public void recover(HomeDomainReservation res) {
		
/*		Calendar now = Calendar.getInstance();
		
		if(now.compareTo(res.getEndTime()) >= 0) {
			res.releaseResources();
			res.switchState(HomeDomainState.FINISHED);
			
	        for(ReservationStatusListener listener : res.getStatusListeners()) {
	            listener.reservationFinished(res.getBodID());
	        }
		}
*/		
	}
}
