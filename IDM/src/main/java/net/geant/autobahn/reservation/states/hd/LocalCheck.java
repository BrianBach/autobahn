/*
 * LocalCheck.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation.states.hd;

import java.util.Iterator;
import java.util.List;

import org.mortbay.log.Log;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.proxy.Autobahn2OscarsConverter;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.ReservationErrors;

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
        
        // The above check is not enough, as there may be multiple paths
        // returned by the PF. So we need to check if the current path under
        // examination has already been checked and failed, not only in the 
        // immediate previous check, but any time in the past.
        List<Path> failedPaths = res.getFailedPaths();
        if (failedPaths != null) {
            while (true) {
                log.debug("Checking whether path " + path + " belongs to already failed paths list...");
                if (Path.containedInList_LbL(path, failedPaths)) {   // This is a path that failed in the past
                    log.debug("It is an already failed path");
                    if (paths.hasNext()) {
                        path = paths.next();
                        log.debug("PF has returned more paths, get next one: " + path);
                        continue;
                    }
                    else {
                        log.debug("Found same paths as previously failed checks!");
                        res.fail("No more paths found");
                        return;
                    }
                } else {    // This is a new path, so we can move on to check it
                    log.debug("It is a new path");
                    break;
                }
            }
        }
        
        res.setPath(path);
        
        log.info("" + res + "@" + this + ", trying path " + path);
        final String domainID = res.getLocalDomainID();
        
        if(path.getCapacity() < res.getCapacity()) {
        	pathFailed(res, ReservationErrors.PATH_CAPACITY_NOT_ENOUGH, path.toString(), path);
        	return;
        }
        
        if (!path.isHomeDomain(domainID)) {
            pathFailed(res, ReservationErrors.WRONG_DOMAIN, domainID, path);
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
            pathFailed(res, ReservationErrors.NOT_ENOUGH_CAPACITY, failedLink.getBodID(), path);
            return;
		} catch (Exception e) {
			log.error("Error while checking resources", e);
			pathFailed(res, ReservationErrors.LOCAL_COMMUNICATION_ERROR, domainID, path);
			return;
		}

        if(res.getUserConstraints() != null && dcon != null && dcon.isValid()) {
        	DomainConstraints intersection = dcon.intersect(res.getUserConstraints());
            dcon = intersection;
        }
        
        if(dcon == null || !dcon.isValid()) {
            pathFailed(res, ReservationErrors.CONSTRAINTS_NOT_CORRECT, path.toString(), path);
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
            pathFailed(res, ReservationErrors.COMMUNICATION_ERROR, res.getNextDomainAddress(), path);
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

    private void pathFailed(HomeDomainReservation res, int code, String args, Path p) {
        
        Iterator<Path> paths = res.getPaths();
        
        res.pathFailed(code, args);
        res.addToFailedPaths(p);    // Keep a list of all paths that were tested and failed
        
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
