package net.geant.autobahn.administration;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Service;
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
