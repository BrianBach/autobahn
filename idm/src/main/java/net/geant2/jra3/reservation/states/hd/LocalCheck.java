/*
 * LocalCheck.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import java.util.Iterator;

import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.idm2dm.OversubscribedException;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.proxy.Autobahn2OscarsConverter;
import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.ReservationErrors;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class LocalCheck extends HomeDomainState {

    public LocalCheck(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(HomeDomainReservation res) {
        super.run(res);
        
        Iterator<Path> paths = res.getPaths();
        
        if(paths == null || !paths.hasNext()) {
            
            // ask PF one more time
            res.switchState(HomeDomainState.PATHFINDING);
            return;
        }
        
        Path oldPath = res.getPath();
        Path path = paths.next();
        
        if(oldPath != null && oldPath.samePath(path)) {
            log.warn("Found same path as previously!");
            res.fail("No more paths found");
            return;
        }
        
        res.setPath(path);
        
        log.info("" + res + "@" + this + ", trying path " + path);
        final String domainID = res.getLocalDomainID();
        
        if(path.getCapacity() < res.getCapacity()) {
        	pathFailed(res, ReservationErrors.PATH_CAPACITY_NOT_ENOUGH, path.toString());
        	return;
        }
        
        if (!path.isHomeDomain(domainID)) {
            pathFailed(res, ReservationErrors.WRONG_DOMAIN, domainID);
            return;
        }

        // Create empty constraints
        GlobalConstraints globalConstraints = new GlobalConstraints();
        res.setGlobalConstraints(globalConstraints);

        // Create empty constraints
        DomainConstraints dcon = null;
        
        try {
            dcon = res.checkResources();
        } catch (OversubscribedException e) {
        	Link failedLink = res.getPath().getLink(e.getFailedLink());
        	
            res.excludeLink(failedLink);
            pathFailed(res, ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID());
            return;
		} catch (Exception e) {
			log.error("Error while checking resources", e);
			pathFailed(res, ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
			return;
		}

        if(res.getUserConstraints() != null && dcon != null && dcon.isValid()) {
        	DomainConstraints intersection = dcon.intersect(res.getUserConstraints());
            dcon = intersection;
        }
        
        if(dcon == null || !dcon.isValid()) {
            pathFailed(res, ReservationErrors.CONSTRAINTS_NOT_CORRECT, path.toString());
            return;
        }
        
        globalConstraints.addDomainConstraints(domainID, dcon);

        // Check if the homedomain is the only domain
        if(res.isLastDomain()) {
        	selfSchedule(res);
        	return;
        }
        
        res.switchState(HomeDomainState.SCHEDULING);
        
        // send reservation request to another IDM from the chain
        try {
            res.forwardSchedule();
        } catch (Exception e) {
            log.error(this + ", scheduling: " + e.getMessage(), e);
            pathFailed(res, ReservationErrors.COMMUNICATION_ERROR, res.getNextDomainAddress());
            return;
        }
    }

    @Override
    public void recover(HomeDomainReservation res) {
        res.setPath(null);
        res.clearExcluded();
        
        res.setState(HomeDomainState.PATHFINDING);
        res.run();
    }

    private void pathFailed(HomeDomainReservation res, int code, String args) {
        
        Iterator<Path> paths = res.getPaths();
        
        res.pathFailed(code, args);
        
        if (paths == null || !paths.hasNext()) {
            // all paths has failed
            res.switchState(HomeDomainState.PATHFINDING);
        } else {
            res.switchState(HomeDomainState.LOCAL_CHECK);
        }
    }
    
    private void selfSchedule(HomeDomainReservation res) {
    	final String domainID = res.getLocalDomainID();
    	
        // compute global constraints
    	GlobalConstraints constraints = res.getGlobalConstraints();
        constraints = constraints.calculateConstraints(
        		res.getReservationParameters(domainID));

        if (constraints == null) {
            res.fail();
            return;
        }

        res.setGlobalConstraints(constraints);
        
        // SEND create reservation TO OSCARS Proxy
        if("http://client-domain.internet2.edu".equals(res.getNextDomainAddress()) || 
        		res.getNextDomainAddress().contains("oscars-domain")) {
        	log.debug("Talking with OSCARS Proxy...");
        	
        	Autobahn2OscarsConverter client = new Autobahn2OscarsConverter();
        	int res_code = client.scheduleReservation(res);
        	
        	if(res_code != 0) {
        		res.fail(ReservationErrors.getInfo(res_code, res.getNextDomainAddress()));
        		return;
        	}
        }
        
        try {
            res.reserveResources();
        } catch (ConstraintsAlreadyUsedException e1) {
            log.error("Constraints already used", e1);
            res.pathFailed(ReservationErrors.CONSTRAINTS_ALREADY_IN_USE, null);
            res.fail();
            return;
        } catch (Exception e1) {
            log.error("Error while reserving resources", e1);
            res.pathFailed(ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID);
            res.fail();
            return;
        }
        
    	res.success("OK");
    }
}
