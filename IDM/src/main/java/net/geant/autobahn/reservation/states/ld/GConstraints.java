/*
 * GConstraints.java
 *
 * 2006-11-28
 */
package net.geant.autobahn.reservation.states.ld;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.idcp.Autobahn2OscarsConverter;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.ReservationErrors;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class GConstraints extends LastDomainState {

    public GConstraints(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(LastDomainReservation res) {
        super.run(res);
        
        final String domainID = res.getLocalDomainID();
        
        GlobalConstraints constraints = res.getGlobalConstraints();
        
        // compute global constraints
        constraints = constraints.calculateConstraints(
        		res.getReservationParameters(domainID));

        log.info("Selected constraints: " + constraints);
        
        if (constraints == null) {
            res.fail(ReservationErrors.CONSTRAINTS_NOT_AGREED, null);
            return;
        }

        res.setGlobalConstraints(constraints);
        
        if(res.isFake()) {
        	res.reportSchedule(ReservationErrors.OK, "", true);
            
            res.switchState(LastDomainState.SCHEDULED);
            
            return;
        }
        
        // SEND create reservation TO OSCARS Proxy
        if("http://client-domain.internet2.edu".equals(res.getNextDomainAddress()) || 
        		res.getNextDomainAddress().contains("oscars-domain")) {
        	log.debug("Talking with OSCARS Proxy...");
        	
        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter();
        	int res_code = client.scheduleReservation(res);
        	
        	if(res_code != 0) {
        		res.fail(res_code, domainID);
        		return;
        	}
        }
        
        try {
            res.reserveResources();
        } catch (ConstraintsAlreadyUsedException e) {
            log.error("Constraints already used", e);
            res.fail(ReservationErrors.CONSTRAINTS_ALREADY_IN_USE, domainID);
            return;
        } catch (OversubscribedException e) {
        	Link failedLink = res.getPath().getLink(e.getFailedLink());
            res.fail(ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
            return;
        } catch (Exception e1) {
            e1.printStackTrace();
            res.fail(ReservationErrors.COMMUNICATION_ERROR, domainID);
            return;
        }
        
        res.switchState(LastDomainState.SCHEDULED);
        
    	res.reportSchedule(ReservationErrors.OK, "", true);
    }
}
