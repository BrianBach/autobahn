package net.geant.autobahn.administration;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.ReservationHistory;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.ServiceHistory;
import net.geant.autobahn.reservation.User;
import net.geant.autobahn.useraccesspoint.PortType;

public class Translator {
	

	public static ServiceType convert(Service serv) {
		ServiceType stype = new ServiceType();
		
		stype.setBodID(serv.getBodID());
		stype.setJustification(serv.getJustification());
		stype.setPriority(serv.getPriority());
		stype.setUser(serv.getUser());
		
		List<ReservationType> reservations = new ArrayList<ReservationType>();
		for(AutobahnReservation r : serv.getReservations()) {
			reservations.add(convert(r));
		}
		
		stype.setReservations(reservations);
		
		return stype;
	}
	
	public static ServiceType convert(ServiceHistory serv) {
		ServiceType stype = new ServiceType();
		User user = new User();
		AdminDomain adminDomain = new AdminDomain();
		adminDomain.setName(serv.getAdminDomain());
		user.setHomeDomain(adminDomain);
		user.setName(serv.getUserAuth());
		
		stype.setBodID(serv.getBodID());
		stype.setJustification(serv.getJustification());
		stype.setPriority(serv.getPriority());
		stype.setUser(user);		
		
		List<ReservationType> reservations = new ArrayList<ReservationType>();
		for(ReservationHistory r : serv.getReservations()) {
			reservations.add(convert(r));
		}
		
		stype.setReservations(reservations);
		
		return stype;
	}
	
	public static ReservationType convert(AutobahnReservation r) {
		ReservationType res = new ReservationType();

		res.setBodID(r.getBodID());
		res.setStartTime(r.getStartTime());
		res.setEndTime(r.getEndTime());
		res.setBidirectional(r.isBidirectional());
		res.setCapacity(r.getCapacity());
		res.setDescription(r.getDescription());
		res.setMtu(r.getMtu());
		res.setResiliency(r.getResiliency());
		res.setPath(r.getPath());
		res.setState(r.getState());
        if (r instanceof HomeDomainReservation) {
            res.setFailureCause(((HomeDomainReservation) r).getFailureCause());
        }

		GlobalConstraints gcon = r.getGlobalConstraints();
		
		// Start port
		PortType sport = PortType.convert(r.getStartPort());

		// End port
		PortType eport = PortType.convert(r.getEndPort());
		
		if(r.getPath() != null && gcon != null) {
			int sVlan = getVlanNumber(gcon, r.getPath().getHomeDomainID() + "-ingress");
			if(sVlan > 0)
				sport.setVlan(sVlan);
	
			int eVlan = getVlanNumber(gcon, r.getPath().getLastDomainID() + "-egress");
			if(eVlan > 0)
				eport.setVlan(eVlan);
		}
		
		res.setStartPort(sport);
		res.setEndPort(eport);

		return res;
	}
	
	public static ReservationType convert(ReservationHistory r) {
		ReservationType res = new ReservationType();

		res.setBodID(r.getBodID());
		res.setStartTime(r.getStartTime());
		res.setEndTime(r.getEndTime());
		res.setBidirectional(r.isBidirectional());
		res.setCapacity(r.getCapacity());
		res.setDescription(r.getDescription());
		res.setMtu(r.getMtu());
		res.setResiliency(r.getResiliency());
		res.setState(r.getIntState());
		
		// Start port
		PortType sport = PortType.convert(r.getStartPort(), r.getStartVlan(), r.getStartPortDescription());
		res.setStartPort(sport);

		// End port
		PortType eport = PortType.convert(r.getEndPort(), r.getEndVlan(), r.getEndPortDescription());
		res.setEndPort(eport);
		
		return res;
	}
	
    
    public static ReservationHistory convertHistory(AutobahnReservation src) {
        ReservationType rtype = convert(src);
    	ReservationHistory r = new ReservationHistory();
        
        r.setBodID(src.getBodID());
        r.setBidirectional(src.isBidirectional());
        r.setCapacity(src.getCapacity());
        r.setDescription(src.getDescription());
        r.setEndTime(src.getEndTime());
        r.setMaxDelay(src.getMaxDelay());
        r.setPriority(src.getPriority());
        r.setResiliency(src.getResiliency());
        r.setStartTime(src.getStartTime());
        r.setStartPort(src.getStartPort().getBodID());
        r.setEndPort(src.getEndPort().getBodID());
        r.setStartPortDescription(src.getStartPort().getDescription());
        r.setEndPortDescription(src.getEndPort().getDescription());
        r.setMtu(src.getMtu());
        r.setAuthParameters(src.getAuthParameters().getIdentifier());
        
        r.setIntState(rtype.getState());
        r.setStartVlan(rtype.getStartPort().getVlan());
        r.setEndVlan(rtype.getEndPort().getVlan());
        
        return r;
    }
    
    
    public static ServiceHistory convertHistory(Service src) {
        
    	ServiceHistory s = new ServiceHistory();
        
        s.setBodID(src.getBodID());
        s.setJustification(src.getJustification());
        s.setPriority(src.getPriority());
        s.setAdminDomain(src.getUser().getHomeDomain().getName());
        for(AutobahnReservation rsv : src.getReservations()) {
        	s.addReservation(convertHistory(rsv));
        }
        s.setUserAuth(src.getUser().getName());       
        
        return s;
    }
	
	private static int getVlanNumber(GlobalConstraints gcon, String domainId) {
		
		if(gcon == null)
			return -1;
		if(gcon.getDomainConstraints(domainId) == null)
			return -1;
		
		PathConstraints pcon = gcon.getDomainConstraints(domainId).getFirstPathConstraints();
		
		if(pcon != null) {
			RangeConstraint rcon = pcon.getRangeConstraint(ConstraintsNames.VLANS);
			if(rcon != null) {
				return rcon.getFirstValue();
			}
		}
		
		return -1;
	}
}
