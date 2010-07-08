package net.geant.autobahn.administration;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;

public class Translator {

	public static ServiceType convert(Service serv) {
		ServiceType stype = new ServiceType();
		
		stype.setBodID(serv.getBodID());
		stype.setJustification(serv.getJustification());
		stype.setPriority(serv.getPriority());
		stype.setUser(serv.getUser());
		
		List<Reservation> reservations = new ArrayList<Reservation>();
		reservations.addAll(serv.getReservations());
		
		stype.setReservations(reservations);
		
		return stype;
	}
	
}
