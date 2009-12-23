/*
 * LocalCheck.java
 *
 * 2006-11-28
 */
package net.geant2.jra3.reservation.states.ed;

import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.idm2dm.OversubscribedException;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.reservation.ExternalReservation;
import net.geant2.jra3.reservation.ReservationErrors;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class LocalCheck extends ExternalDomainState {

    public LocalCheck(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(ExternalReservation res) {
        super.run(res);
        
        final String domainID = res.getLocalDomainID();
        
        Path path = res.getPath();
        
        log.info(this + " Acquired path: " + path);

        DomainConstraints dcon = null;
        try {
            dcon = res.checkResources();
        } catch (OversubscribedException e) {
        	Link failedLink = res.getPath().getLink(e.getFailedLink());
        	
            res.fail(ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
            return;
		} catch (Exception e1) {
            log.error("Error while checking resources", e1);
            res.fail(ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
            return;
		}
        
        // Add domain constraint to global
        res.getGlobalConstraints().addDomainConstraints(domainID, dcon);
        
        if(dcon == null || !dcon.isValid()) {
            res.fail(ReservationErrors.CONSTRAINTS_NOT_CORRECT, domainID);
            return;
        }

        res.switchState(ExternalDomainState.SCHEDULING);
        
        try {
        	res.forwardSchedule();
        } catch (Exception e) {
            log.error(this + " schedule: " + e.getMessage(), e);
            res.fail(ReservationErrors.COMMUNICATION_ERROR, "");
            return;
        }
    }
    
    @Override
    public void recover(ExternalReservation res) {
        // TODO Auto-generated method stub
        
    }
}
