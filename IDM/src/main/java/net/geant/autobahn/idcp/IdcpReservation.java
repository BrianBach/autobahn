/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * Responsible for sending proper notification to idcp 
 * This class is used in ab->idcp cases
 * @author PCSS
 */
public class IdcpReservation implements ReservationStatusListener {
	
	private static final Logger log = Logger.getLogger(IdcpReservation.class);
	
	private static Map<String, IdcpReservation> reservations = new HashMap<String, IdcpReservation>();
	
	private boolean resConfirmedReceived;
	private boolean resCancelReceived;
	private final String idcpResId;
	private final Reservation res;
	private final PathInfo pathInfo;
	private boolean scheduled, activated;
	
	public IdcpReservation(String idcpResId, Reservation res, PathInfo pathInfo) { 
		
		this.idcpResId = idcpResId;
		this.res = res;
		this.pathInfo = pathInfo;
		reservations.put(idcpResId, this);
	}
	
	public synchronized static void addIdcpReservation(String resId, IdcpReservation res) { 
		
		reservations.put(resId, res);
	}
	
	public synchronized static IdcpReservation getReservation(String resId) { 
		
		return reservations.get(resId);
	}
		
	public void setMessage(String msg) {
		
		if (msg.equals(Idcp.EVENT_RESERVATION_CREATE_CONFIRMED)) {
			resConfirmedReceived = true;
		} else if (msg.equals(Idcp.EVENT_RESERVATION_CANCEL_CONFIRMED)) {
			resCancelReceived = true;
		} 
	}
	
	private void sendNotification(String resId, String eventType, String status, String errorMessage) {
		
		final String desc = res.getDescription();
		final long start = res.getStartTime().getTimeInMillis();
		final long end = res.getEndTime().getTimeInMillis();
		final int bandwidth = (int)res.getCapacity();
		
		for (SubscriptionInfo si : IdcpManager.getSubscribers()) {

			try {
				
				IdcpNotifyClient notify = new IdcpNotifyClient(si.getConsumerUrl());
				notify.notification(idcpResId, desc, start, end, bandwidth, pathInfo, eventType, status, errorMessage, si.getSubscriptionId());
				log.info("sent notification to " + si.getConsumerUrl() + ", event " + eventType);
			} catch (IdcpException e) { 
				log.info("error sending notification to " + si.getConsumerUrl());
			}
		}
	}

	@Override
	public void reservationScheduled(String reservationId) {
		
		scheduled = true;
		
		// send if conf received
		if (resConfirmedReceived) {
			sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CREATE_COMPLETED, "SCHEDULED", null);
		} else {
			
			(new Thread() {
				@Override
				public void run() {
					try {
						// wait for res confirmed
						Thread.sleep(1000 * 60); 
					} catch (Exception e) { }
					
					if (resConfirmedReceived)
						sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CREATE_COMPLETED, "SCHEDULED", null);
					else
						sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CREATE_FAILED, "FAILED", "did not receive " + Idcp.EVENT_RESERVATION_CREATE_CONFIRMED);
				}
			}).start();
		}
	}

	@Override
	public void reservationActive(String reservationId) {
		
		activated = true;
		sendNotification(idcpResId, Idcp.EVENT_UPSTREAM_PATH_SETUP_CONFIRMED, "ACTIVATED", null);
	}

	@Override
	public void reservationFinished(String reservationId) {

		sendNotification(idcpResId, Idcp.EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED, "FINISHED", null);
	}

	@Override
	public void reservationProcessingFailed(String reservationId, String cause) {
		
		if (!scheduled) {
			
			sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CREATE_FAILED, "FAILED", cause);
		} else {
			
			// must be activation that failed
			sendNotification(idcpResId, Idcp.EVENT_PATH_SETUP_FAILED, "FAILED", cause);
		}
	}

	@Override
	public void reservationCancelled(String reservationId) {
 
		if (resCancelReceived)
			sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CANCEL_COMPLETED, "CANCELLED", null);
		else {
			
			(new Thread() {
				@Override
				public void run() {
					try {
						// wait for cancel confirmed
						Thread.sleep(1000 * 60); 
					} catch (Exception e) { }
					
					if (resConfirmedReceived)
						sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CANCEL_COMPLETED, "CANCELLED", null);
					else
						sendNotification(idcpResId, Idcp.EVENT_RESERVATION_CANCEL_FAILED, "FAILED", "did not receive " + Idcp.EVENT_RESERVATION_CANCEL_FAILED);
				}
			}).start();
		}
	}

	@Override
	public void reservationModified(String reservationId, boolean success) {
		
		if (success)
			sendNotification(idcpResId, Idcp.EVENT_RESERVATION_MODIFY_COMPLETED, "MODIFIED", null);
		else
			sendNotification(idcpResId, Idcp.EVENT_RESERVATION_MODIFY_FAILED, "FAILED", null);
	}
}
