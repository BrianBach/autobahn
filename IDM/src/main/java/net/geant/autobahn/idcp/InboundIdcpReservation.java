package net.geant.autobahn.idcp;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

import static net.geant.autobahn.idcp.IdcpReservation.State.*;

/**
 * Reservation wrapper for inbound IDCP reservations.
 *
 * @author PCSS
 */
public class InboundIdcpReservation
	extends IdcpReservation
	implements ReservationStatusListener
{
	private static final Logger log = Logger.getLogger(InboundIdcpReservation.class);

	private State state = ACCEPTED;
	private State stateBeforeModify;

	private boolean isReservationActive;
	private boolean isReservationFinished;

	public InboundIdcpReservation(String idcpResId, Reservation reservation, PathInfo pathInfo)
	{
		super(idcpResId, reservation, pathInfo);
	}

	@Override
	public synchronized void notify(EventContent event)
	{
		String type = event.getType();
		if (Idcp.EVENT_RESERVATION_CREATE_COMPLETED.equals(type) && isState(INCREATE)) {
			setState(PENDING);
		} else if (Idcp.EVENT_RESERVATION_CREATE_FAILED.equals(type)) {
			// We should clean up the reservation (FIXME)
			setState(FAILED);
		} else if (Idcp.EVENT_UPSTREAM_PATH_SETUP_CONFIRMED.equals(type) &&
				   (isState(INCREATE) || isState(PENDING))) {
			setState(INSETUP);
			if (isReservationActive) {
				activate();
			}
		} else if (Idcp.EVENT_PATH_SETUP_FAILED.equals(type)) {
			// We should clean up the reservation (FIXME)
			setState(FAILED);
		} else if (Idcp.EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED.equals(type) && isState(ACTIVE)) {
			setState(INTEARDOWN);
			if (isReservationFinished) {
				teardown();
			}
		} else if (Idcp.EVENT_RESERVATION_CANCEL_COMPLETED.equals(type) && isState(INTEARDOWN)) {
			setState(CANCELLED);
		} else if (Idcp.EVENT_RESERVATION_MODIFY_COMPLETED.equals(type) && isState(INMODIFY)) {
			setState(stateBeforeModify);
		} else if (Idcp.EVENT_RESERVATION_MODIFY_FAILED.equals(type) && isState(INMODIFY)) {
			// We should revert the modification at this point (FIXME)
			setState(stateBeforeModify);
		}
	}

	private void updatePathInfo()
	{
		// check first vlan
		int vlan = reservation.getGlobalConstraints().getDomainConstraints().get(0).getFirstPathConstraints().getRangeConstraints().get(0).getFirstValue();

		for (CtrlPlaneHopContent hop: pathInfo.getPath().getHop()) {
			String linkId = hop.getLink().getId();
			if (linkId.contains(IdcpManager.getDomainName())) {
				hop.getLink().getSwitchingCapabilityDescriptors().getSwitchingCapabilitySpecificInfo().setVlanRangeAvailability(String.valueOf(vlan));
			}
		}
	}

	private void activate()
	{
		assert(isState(INSETUP) && isReservationActive);
		sendNotification(Idcp.EVENT_UPSTREAM_PATH_SETUP_CONFIRMED, INSETUP, null);
		sendNotification(Idcp.EVENT_DOWNSTREAM_PATH_SETUP_CONFIRMED, INSETUP, null);
		setState(ACTIVE);
		sendNotification(Idcp.EVENT_PATH_SETUP_COMPLETED, ACTIVE, null);
	}

	private void teardown()
	{
		assert(isState(INTEARDOWN) && isReservationFinished);
		sendNotification(Idcp.EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED, INTEARDOWN, null);
		sendNotification(Idcp.EVENT_DOWNSTREAM_PATH_TEARDOWN_CONFIRMED, INTEARDOWN, null);
		setState(FINISHED);
		sendNotification(Idcp.EVENT_PATH_TEARDOWN_COMPLETED, FINISHED, null);
		removeReservation();
	}

	@Override
	public synchronized void reservationScheduled(String reservationId)
	{
		setState(INCREATE);
		updatePathInfo();
		sendNotification(Idcp.EVENT_RESERVATION_CREATE_CONFIRMED, INCREATE, null);
	}

	@Override
	public synchronized void reservationActive(String reservationId)
	{
		isReservationActive = true;
		if (isState(INSETUP)) {
			activate();
		}
	}

	@Override
	public synchronized void reservationFinished(String reservationId)
	{
		isReservationFinished = true;
		if (isState(INTEARDOWN)) {
			teardown();
		}
	}

	@Override
	public synchronized void reservationProcessingFailed(String reservationId, String cause)
	{
		switch (getState()) {
		case ACCEPTED:
			sendNotification(Idcp.EVENT_RESERVATION_CREATE_FAILED, FAILED, cause);
			break;
		case INCREATE:
		case PENDING:
			sendNotification(Idcp.EVENT_PATH_SETUP_FAILED, FAILED, cause);
			break;
		}
		setState(FAILED);
		removeReservation();
	}

	@Override
	public synchronized void reservationCancelled(String reservationId)
	{
		if (isState(ACTIVE)) {
			setState(INTEARDOWN);
		} else {
			setState(CANCELLED);
		}
		sendNotification(Idcp.EVENT_RESERVATION_CANCEL_CONFIRMED, getState(), null);
		removeReservation();
	}

	@Override
	public synchronized void reservationModified(String reservationId, boolean success)
	{
		if (success) {
			stateBeforeModify = getState();
			setState(INMODIFY);
			sendNotification(Idcp.EVENT_RESERVATION_MODIFY_CONFIRMED, INMODIFY, null);
		} else {
			sendNotification(Idcp.EVENT_RESERVATION_MODIFY_FAILED, getState(), null);
		}
	}
}
