package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;

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
        Port start = pdao.getByBodID(req.getStartPort());
        if (start == null) {
            throw new UserAccessPointException("reservation start port " + req.getStartPort() + " could not be found");
        }
        Port end = pdao.getByBodID(req.getEndPort());
        if (end == null) {
            throw new UserAccessPointException("reservation end port " + req.getEndPort() + " could not be found");
        }
        
        HomeDomainReservation resv = new HomeDomainReservation(start, end,
                req.getStartTime(), req.getEndTime(), req.getPriority().ordinal());
        
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
        resv.setUserVlanId(req.getUserVlanId());
        if (req.getUserVlanId() < 0) {
            throw new UserAccessPointException("reservation user VLAN cannot be negative");
        }
        
        return resv;
	}
}
