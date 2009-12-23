/*
 * LocalCheck.java
 *
 * 2006-11-28
 */
package net.geant2.jra3.reservation.states.ld;

import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.idm2dm.OversubscribedException;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.reservation.LastDomainReservation;
import net.geant2.jra3.reservation.ReservationErrors;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class LocalCheck extends LastDomainState {

    public LocalCheck(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(LastDomainReservation res) {
        super.run(res);
        
        final String domainID = res.getLocalDomainID();
        
        Path path = res.getPath();
        
        log.info(this + " Acquired path: " + path);
        
        GlobalConstraints constraints = res.getGlobalConstraints();
        
        DomainConstraints dcon = null;
        
        try {
            dcon = res.checkResources();
        } catch (OversubscribedException e) {
        	Link failedLink = res.getPath().getLink(e.getFailedLink());
        	
            res.fail(ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
            return;
		} catch (Exception e) {
            res.fail(ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
            return;
		}

        if(dcon == null || !dcon.isValid()) {
            res.fail(ReservationErrors.CONSTRAINTS_NOT_CORRECT, domainID);
            return;
        }
        
        //Add domain constraint to global
        constraints.addDomainConstraints(domainID, dcon);
        
        res.switchState(LastDomainState.G_CONSTRAINTS);
    }
    
    @Override
    public void recover(LastDomainReservation res) {
        // TODO Auto-generated method stub
        
    }
}
