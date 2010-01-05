/*
 * Scheduling.java
 *
 * 2006-11-28
 */
package net.geant2.jra3.reservation.states.ed;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.idm2dm.OversubscribedException;
import net.geant2.jra3.interdomain.NoSuchReservationException;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.reservation.ExternalReservation;
import net.geant2.jra3.reservation.ReservationErrors;
import net.geant2.jra3.reservation.ReservationStatusListener;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Scheduling extends ExternalDomainState {

    public Scheduling(int code, String label) {
        super(code, label);
    }

    @Override
    public void reservationScheduled(ExternalReservation res, int msgCode,
			String args, boolean success, GlobalConstraints global) {

        if (success) {
        	if(res.isFake()) {
        		res.reportSchedule(ReservationErrors.OK, "", true);
        		return;
        	}
        	
        	final String domainID = res.getLocalDomainID();
        	
            // Update GlobalConstraints
            res.setGlobalConstraints(global);
            
            try {
                res.reserveResources();
            } catch (ConstraintsAlreadyUsedException e1) {
                log.error("Constraints already used", e1);
                res.fail(ReservationErrors.CONSTRAINTS_ALREADY_IN_USE, null);
                return;
            } catch (OversubscribedException e) {
            	Link failedLink = res.getPath().getLink(e.getFailedLink());
                res.fail(ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
                return;
            } catch (Exception e1) {
                e1.printStackTrace();
                res.fail(ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
                return;
            }

            res.switchState(ExternalDomainState.SCHEDULED);
            
        	res.reportSchedule(ReservationErrors.OK, "", true);
        } else {
            res.fail(msgCode, args);
        }
    }
    
    @Override
	public void withdraw(ExternalReservation res) {
        log.info(this + " FAILURE: Scheduling timeout ...");
        
		try {
			res.forwardWithdraw();
		} catch (NoSuchReservationException e) {
			// Ignore it
		}
        
        res.switchState(ExternalDomainState.FAILED);
        
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationProcessingFailed(res.getBodID(), "Scheduling timeout ...");
        }
	}

	@Override
    public void recover(ExternalReservation res) {
        log.info("Recovering from SCHEDULING");
        
        res.switchState(ExternalDomainState.FAILED);
        
        for(ReservationStatusListener listener : res.getStatusListeners()) {
            listener.reservationProcessingFailed(res.getBodID(), "Report schedule missed");
        }
    }
}
