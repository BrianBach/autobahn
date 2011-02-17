/*
 * LocalCheck.java
 *
 * 2006-11-28
 */
package net.geant.autobahn.reservation.states.ed;

import static net.geant.autobahn.reservation.states.hd.LocalCheck.areDomainConstraintsValid;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.reservation.ExternalReservation;
import net.geant.autobahn.reservation.ReservationErrors;

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

        DomainConstraints[] dcons = null;
        try {
            dcons = res.checkResources();
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
        res.getGlobalConstraints().addDomainConstraints(domainID + "-ingress", dcons[0]);
        res.getGlobalConstraints().addDomainConstraints(domainID + "-egress", dcons[1]);
        
        if(!areDomainConstraintsValid(dcons)) {
            res.fail(ReservationErrors.CONSTRAINTS_NOT_CORRECT, domainID);
            return;
        }

        res.switchState(ExternalDomainState.SCHEDULING);
        
        try {
        	res.forwardSchedule();
        } catch (Exception e) {
            log.error(this + " schedule: " + e.getMessage());
            res.fail(ReservationErrors.COMMUNICATION_ERROR, "");
            return;
        }
    }
    
    @Override
    public void recover(ExternalReservation res) {
        
    }
}
