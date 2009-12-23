package net.geant2.jra3.useraccesspoint;

import java.util.Calendar;

import net.geant2.jra3.dao.IdmDAOFactory;
import net.geant2.jra3.network.Port;
import net.geant2.jra3.network.dao.PortDAO;
import net.geant2.jra3.reservation.HomeDomainReservation;

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
        Port end = pdao.getByBodID(req.getEndPort());
        
        HomeDomainReservation resv = new HomeDomainReservation(start, end,
                req.getStartTime(), req.getEndTime(), req.getPriority().ordinal());
        
        resv.setDescription(req.getDescription());
        resv.setCapacity(req.getCapacity());
        resv.setMaxDelay(req.getMaxDelay());
        resv.setResiliency(req.getResiliency().name());
        resv.setBidirectional(req.isBidirectional());
        
        return resv;
	}
}
