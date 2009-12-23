/*
 * Scheduling.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.idm2dm.OversubscribedException;
import net.geant2.jra3.interdomain.NoSuchReservationException;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.ReservationErrors;
import net.geant2.jra3.reservation.ReservationTimeout;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Scheduling extends HomeDomainState {

	public static final int TIMEOUT = 60 * 1000;
	
    public Scheduling(int code, String label) {
        super(code, label);
    }
    
    @Override
	public void run(HomeDomainReservation res) {
    	res.addTimeout(new SchedulingTimeout(res), TIMEOUT);
	}

	@Override
    public void reservationScheduled(HomeDomainReservation res, int msgCode,
			String args, boolean success, GlobalConstraints global) {

        final String domainID = res.getLocalDomainID();
        String message = ReservationErrors.getInfo(msgCode, args);
        
        res.cancelTimeouts();
        
        if(success) {
            
        	if(res.isFake()) {
        		res.success(message);
        		return;
        	}
        	
            res.setGlobalConstraints(global);
            
            try {
                res.reserveResources();
            } catch (ConstraintsAlreadyUsedException e1) {
                log.error("Constraints already used", e1);
                res.pathFailed(ReservationErrors.CONSTRAINTS_ALREADY_IN_USE, null);
                res.fail();
                return;
            } catch (OversubscribedException e) {
            	Link failedLink = res.getPath().getLink(e.getFailedLink());
                res.excludeLink(failedLink);
            	res.pathFailed(ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
            	res.fail();
                return;
            } catch (Exception e1) {
                log.error("Error while reserving resources", e1);
                res.pathFailed(ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
                res.fail();
                return;
            }
            
            res.success(message);
        } else {
            res.pathFailed(msgCode, args);
            
            Link cause = ReservationErrors.getLink(msgCode, args);
            if(cause != null) {
                res.excludeLink(cause);
            }
            
            res.switchState(HomeDomainState.LOCAL_CHECK);
        }
    }

    @Override
    public void cancel(HomeDomainReservation res) {
        res.switchState(DEFERRED_CANCEL);
    }
    
    class SchedulingTimeout extends ReservationTimeout {

    	private HomeDomainReservation res;
    	
		public SchedulingTimeout(HomeDomainReservation res) {
			super(res.getBodID());
			this.res = res;
		}

		@Override
		public void run() {
			log.warn(resId + " Scheduling is timed out...");

			try {
				res.forwardWithdraw();
			} catch (NoSuchReservationException e) {
				// Ignore it
			}
			
            res.pathFailed(ReservationErrors.COMMUNICATION_ERROR, "");
            
            res.switchState(HomeDomainState.LOCAL_CHECK);
		}
    }
}
