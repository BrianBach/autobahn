package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;

import org.apache.log4j.Logger;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.dao.PortDAO;
import net.geant.autobahn.reservation.HomeDomainReservation;

public class RequestConverter {

	private IdmDAOFactory daos = null;
	
    private static final Logger log = Logger.getLogger(RequestConverter.class);
    
	public RequestConverter(IdmDAOFactory daos) {
		this.daos = daos;
	}
	
	public HomeDomainReservation createReservation(ReservationRequest req) throws UserAccessPointException {
		Calendar now = Calendar.getInstance();
		Calendar startTime = req.getStartTime();
		
		if(!req.isProcessNow()) {
			// not process now - check if: and start > now
            if (startTime.compareTo(now) < 0) {
                String message = "wrong reservation time - startTime: " + startTime.getTime() + " < currentTime: " + now.getTime();
                log.error(message);
            	throw new UserAccessPointException(message);
            }
		} else {
			req.setStartTime(now);
		}
		
		// check if start < end
        if (req.getStartTime().compareTo(req.getEndTime()) >= 0) {
            String message = "wrong reservation time - startTime: " + req.getStartTime().getTime() + " >= endTime: " + req.getEndTime().getTime();
            log.error(message);
            throw new UserAccessPointException(message);
        }
        
        //check if start port equals end port
        if (req.getStartPort().getAddress().equals(req.getEndPort().getAddress())) {
        	throw new UserAccessPointException("reservation start port " + req.getStartPort().getAddress() + " and end port " + req.getEndPort().getAddress() + " are the same.");
        }
		
        PortDAO pdao = daos.getPortDAO();
        Port start = pdao.getByBodID(req.getStartPort().getAddress());
        if (start == null) {
            throw new UserAccessPointException("reservation start port " + req.getStartPort() + " could not be found");
        }
        Port end = pdao.getByBodID(req.getEndPort().getAddress());
        if (end == null) {
            throw new UserAccessPointException("reservation end port " + req.getEndPort() + " could not be found");
        }
        
        HomeDomainReservation resv = new HomeDomainReservation(start, end,
                req.getStartTime(), req.getEndTime(), req.getPriority().ordinal());
        
        resv.setProcessNow(req.isProcessNow());
        resv.setDescription(req.getDescription());
        resv.setCapacity(req.getCapacity());
        if (req.getCapacity() <= 0) {
            throw new UserAccessPointException("reservation capacity cannot be negative or zero");
        }
        resv.setMaxDelay(req.getMaxDelay());
        if (req.getMaxDelay() < 0) {
            throw new UserAccessPointException("reservation max delay cannot be negative");
        }
        resv.setResiliency(req.getResiliency().name());
        resv.setBidirectional(req.isBidirectional());
        resv.setUserInclude(req.getUserInclude());
        resv.setUserExclude(req.getUserExclude());

        // Fill in the user constraints fields
        int inVlan = req.getStartPort().getVlan();
        if(inVlan > 0) {
        	PathConstraints pcon = new PathConstraints();
        	pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(inVlan, inVlan));
        	resv.setUserIngressConstraints(pcon);
        }
        
        int egVlan = req.getEndPort().getVlan();
        if(egVlan > 0) {
        	PathConstraints pcon = new PathConstraints();
        	pcon.addRangeConstraint(ConstraintsNames.VLANS, new RangeConstraint(egVlan, egVlan));
        	resv.setUserEgressConstraints(pcon);
        }
        
        //mtu info
        resv.setMtu(req.getMtu());
        if (req.getMtu() < 0) {
            throw new UserAccessPointException("reservation user MTU cannot be negative");
        }
        
        resv.setAuthParameters(req.getAuthParameters());
        
        return resv;
	}
}
