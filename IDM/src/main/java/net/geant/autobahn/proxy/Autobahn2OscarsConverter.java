/**
 * 
 */
package net.geant.autobahn.proxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationErrors;
import net.geant.autobahn.reservation.ReservationStatusListener;

import org.apache.log4j.Logger;

/**
 * Client to proxy client
 * @author Michal
 */
public class Autobahn2OscarsConverter implements ReservationStatusListener {
		
	private static Logger log = Logger.getLogger(Autobahn2OscarsConverter.class);
	
	private Map<String, Reservation> cache = new HashMap<String, Reservation>();

	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) {
		
		try {
			ProxyClient proxy = new ProxyClient();
			proxy.cancelReservation(resID);
		} catch (IOException e) { 
			log.error("ProxyClientConverter: cancelReservation Error: + " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#scheduleReservation(net.geant.autobahn.reservation.Reservation)
	 */
	public int scheduleReservation(AutobahnReservation reservation) {
		
		try {
			ProxyClient proxy = new ProxyClient();
			ReservationInfo res = proxy.createReservation(Autobahn2OscarsConverter.convertReservation(reservation));
			
			String errorDesc = res.getDescription();
			
			if(errorDesc != null && !errorDesc.equals("")) {
	    		if(errorDesc.contains("VLAN")) {
	    			return ReservationErrors.CONSTRAINTS_NOT_AGREED;
	    		} else if (errorDesc.contains("oversubscribed")) {
	    			return ReservationErrors.NOT_ENOUGH_CAPACITY;
	    		} else {
	    			return ReservationErrors.COMMUNICATION_ERROR;
	    		}
			}
			
			cache.put(reservation.getBodID(), reservation);
			reservation.addStatusListener(this);
			
		} catch (IOException e) { 
			log.error("ProxyClientConverter: scheduleReservation Error: " + e.getMessage());
			return ReservationErrors.COMMUNICATION_ERROR;
		}
		
		return 0;
	}

	public void reservationActive(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationCancelled(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationFinished(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationModified(String reservationId, boolean success) {
		//not implemented
	}

	public void reservationProcessingFailed(String reservationId, String cause) {
		notifyIDC(reservationId);
	}

	public void reservationScheduled(String reservationId) {
		notifyIDC(reservationId);
	}
	
	private void notifyIDC(String reservationId) {
/*		try {
			ProxyClient proxy = new ProxyClient();
			Reservation res = cache.get(reservationId);
			proxy.Notify(convertReservation(res));
		} catch(IOException e) {
			log.error("Notify error: " + e.getMessage());
		}
*/	}

	public static ReservationInfo convertReservation(Reservation res) {
		ReservationInfo ri = new ReservationInfo();
		ri.setBidirectional(res.isBidirectional());
		ri.setBodID(res.getBodID());
		ri.setCapacity(res.getCapacity());
		ri.setDescription(res.getDescription());
		ri.setEndPort(res.getEndPort().getBodID());
		ri.setEndTime(res.getEndTime());
		ri.setMaxDelay(res.getMaxDelay());
		ri.setPriority(res.getPriority());
		ri.setResiliency(res.getResiliency());
		ri.setStartPort(res.getStartPort().getBodID());
		ri.setStartTime(res.getStartTime());
		ri.setState(res.getState());
		
		RangeConstraint rcon = res.getGlobalConstraints()
				.getDomainConstraints().get(0).getPathConstraints().get(0)
				.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(rcon != null)
			ri.setCalculatedConstraints("" + rcon.getFirstValue());
		// set path
/*		Link[] path = new Link[res.getPath().getLinks().size()];
		ri.setPath(res.getPath().getLinks().toArray(path));
*/		
		return ri;
	}
}
