/**
 * 
 */
package net.geant.autobahn.idcp;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

/**
 * Sends events info to subscribers in response to certain events
 * @author PCSS
 */
public class IdcpReservationNotifier implements ReservationStatusListener {
	
	private static final Logger log = Logger.getLogger(IdcpReservationNotifier.class);
	
	private Reservation reservation;
	private PathInfo pathInfo; 
	private boolean scheduled, activated; 
	
	public IdcpReservationNotifier(Reservation reservation, PathInfo pathInfo) { 
		
		this.reservation = reservation;
		this.pathInfo = pathInfo;
	}
	
	private void updatePathInfo() { 
		
		// check first vlan
		int vlan = reservation.getGlobalConstraints().getDomainConstraints().get(0).getFirstPathConstraints().getRangeConstraints().get(0).getFirstValue();
		
		for (CtrlPlaneHopContent hop : pathInfo.getPath().getHop()) {
			
			String linkId = hop.getLink().getId();
			if (linkId.contains(IdcpManager.getDomainName())) {
				hop.getLink().getSwitchingCapabilityDescriptors().getSwitchingCapabilitySpecificInfo().setVlanRangeAvailability(String.valueOf(vlan));
			}
		}
	}
	
	private void sendNotification(String resId, String eventType, String status, String errorMessage) {
		
		final String desc = reservation.getDescription();
		final long start = reservation.getStartTime().getTimeInMillis();
		final long end = reservation.getEndTime().getTimeInMillis();
		final int bandwidth = (int)reservation.getCapacity();
		
		for (SubscriptionInfo si : IdcpManager.getSubscribers()) {

			try {
				
				IdcpNotifyClient notify = new IdcpNotifyClient(si.getConsumerUrl());
				notify.notification(resId, desc, start, end, bandwidth, pathInfo, eventType, status, errorMessage, si.getSubscriptionId());
				log.info("sent notification to " + si.getConsumerUrl() + ", event " + eventType);
			} catch (IdcpException e) { 
				log.info("error sending notification to " + si.getConsumerUrl());
			}
		}
	}
	
	@Override
	public void reservationScheduled(String reservationId) {
		
		scheduled = true;
		
		updatePathInfo();
		sendNotification(reservationId, Idcp.EVENT_RESERVATION_CREATE_CONFIRMED, "SCHEDULED", null);
		sendNotification(reservationId, Idcp.EVENT_RESERVATION_CREATE_COMPLETED, "SCHEDULED", null);
	}

	@Override
	public void reservationActive(String reservationId) {
		
		activated = true;
		
		sendNotification(reservationId, Idcp.EVENT_DOWNSTREAM_PATH_SETUP_CONFIRMED, "ACTIVATED", null);
		sendNotification(reservationId, Idcp.EVENT_UPSTREAM_PATH_SETUP_CONFIRMED, "ACTIVATED", null);
		sendNotification(reservationId, Idcp.EVENT_PATH_SETUP_COMPLETED, "ACTIVATED", null);
	}

	@Override
	public void reservationFinished(String reservationId) {
		
		sendNotification(reservationId, Idcp.EVENT_DOWNSTREAM_PATH_TEARDOWN_CONFIRMED, "FINISHED", null);
		sendNotification(reservationId, Idcp.EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED, "FINISHED", null);
		sendNotification(reservationId, Idcp.EVENT_PATH_TEARDOWN_COMPLETED, "FINISHED", null);
	}

	@Override
	public void reservationProcessingFailed(String reservationId, String cause) {
		
		if (!scheduled) {
			
			sendNotification(reservationId, Idcp.EVENT_RESERVATION_CREATE_FAILED, "FAILED", cause);
		} else {
			
			// must be activation that failed
			sendNotification(reservationId, Idcp.EVENT_PATH_SETUP_FAILED, "FAILED", cause);
		}
	}

	@Override
	public void reservationCancelled(String reservationId) {
		
		sendNotification(reservationId, Idcp.EVENT_RESERVATION_CANCEL_CONFIRMED, "CANCELLED", null);
		sendNotification(reservationId, Idcp.EVENT_RESERVATION_CANCEL_COMPLETED, "CANCELLED", null);
	}

	@Override
	public void reservationModified(String reservationId, boolean success) {
		
		sendNotification(reservationId, Idcp.EVENT_RESERVATION_MODIFY_CONFIRMED, "MODIFIED", null);
		if (success)
			sendNotification(reservationId, Idcp.EVENT_RESERVATION_MODIFY_COMPLETED, "MODIFIED", null);
		else
			sendNotification(reservationId, Idcp.EVENT_RESERVATION_MODIFY_FAILED, "FAILED", null);
	}
}
