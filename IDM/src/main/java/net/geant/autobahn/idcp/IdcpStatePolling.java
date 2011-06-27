/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.reservation.Reservation;

/**
 * Asks every queryTime for reservation status identified by resId
 * @author PCSS
 */
public final class IdcpStatePolling extends Thread {
	
	private static Logger log = Logger.getLogger(IdcpStatePolling.class);
	private final String url;
	private final String resId;
	private final int queryTime = 10 * 1000;
	private final boolean updateReservation;
	private final IdcpClient idcp;
	private static Map<String, Integer> states = new HashMap<String, Integer>();
	
	static {
		
		states.put("ACCEPTED", 10);
		states.put("PENDING", 21);
		states.put("INSETUP", 9);
		states.put("ACTIVE", 10);
		states.put("INTEARDOWN", 11);
		states.put("FINISHED", 21);
		states.put("CANCELLED", 22);
		states.put("FAILED", 23);
	}

	public IdcpStatePolling(String url, String resId) {
		
		this(url, resId, true);
	}
		
	public IdcpStatePolling(String url, String resId, boolean updateReservation) {
		
		this.url = url;
		this.resId = resId;
		this.updateReservation = updateReservation;
		this.idcp = new IdcpClient(url);
		this.start();
	}
	
	@Override
	public void run() {
		
		final String abResId = ToIdcp.restoreResId(resId);
		final Reservation res = updateReservation ? AccessPoint.getInstance().getAutobahnReservation(abResId) : null;
		String prevStatus = "UNKNOWN";
		int numInSetup = 0; // for detecting hanging reservations, happens for some port combinations
		
		while (true) {
			
			try {
				ResDetails rd = idcp.query(resId);
				String status = rd.status;
				
				if (status.equals("FAILED")) {
					if (updateReservation) {
						log.info("idcp reservation " + abResId + " FAILED");
						res.setState(states.get("FAILED"));
					}
				} else if (status.equals("ACTIVE")) {
					
					if (!status.equals(prevStatus))
						log.info("idcp reservation " + abResId + " is now ACTIVE");
				} else if (status.equals("INSETUP")) {
					
					if (++numInSetup > 10) {
						log.info("idcp reservation " + abResId + " is hanging, switching to FAILED");
						res.setState(states.get("FAILED"));
						break;
					}
				}
				prevStatus = status;
				// check exit condition
				if (status.equals("FAILED") || status.equals("CANCELLED") || status.equals("FINISHED")) {
					log.info("idcp reservation " + abResId + " ended with status " + status);
					break;
				}
				Thread.sleep(queryTime);
				
			} catch (Exception e) {
				log.info("idcp reservation " + abResId + " FAILED - " + e.getMessage());
				if (updateReservation)
					res.setState(states.get("FAILED"));
				break;
			}
		}
	}
}
