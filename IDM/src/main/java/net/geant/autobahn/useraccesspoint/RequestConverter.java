package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.network.dao.PortDAO;
import net.geant.autobahn.reservation.HomeDomainReservation;

public class RequestConverter {

	private IdmDAOFactory daos = null;
	
	public RequestConverter(IdmDAOFactory daos) {
		this.daos = daos;
	}
	
	public HomeDomainReservation createReservation(ReservationRequest req) throws UserAccessPointException {
		Calendar now = Calendar.getInstance();
		Calendar startTime = req.getStartTime();
		
		if(!req.isProcessNow()) {
			// not process now - check if: and start > now
            if (startTime.compareTo(now) < 0) 
            	throw new UserAccessPointException("wrong reservation time - startTime: " + startTime.getTime() + " < currentTime: " + now.getTime());
		} else {
			req.setStartTime(now);
		}

		// check if start < end
        if (startTime.compareTo(req.getEndTime()) >= 0) 
        	throw new UserAccessPointException("wrong reservation time - startTime: " + startTime.getTime() + " >= endTime: " + req.getEndTime().getTime());
		
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
