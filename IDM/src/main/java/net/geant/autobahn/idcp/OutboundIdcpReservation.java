package net.geant.autobahn.idcp;

import org.apache.log4j.Logger;

import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;

import static net.geant.autobahn.idcp.IdcpReservation.State.*;

/**
 * Responsible for sending proper notification to idcp
 * This class is used in ab->idcp cases.
 *
 * @author PCSS
 */
public class OutboundIdcpReservation
	extends IdcpReservation
	implements ReservationStatusListener
{
	private static final Logger log =
		Logger.getLogger(OutboundIdcpReservation.class);

	private boolean isScheduled;
	private boolean isCreateConfirmed;
	private boolean isActive;
	private boolean isFinished;
	private boolean isCancelled;
	private boolean isCancelConfirmed;

	public OutboundIdcpReservation(String idcpResId, Reservation res, PathInfo pathInfo)
	{
		super(idcpResId, res, pathInfo);
		setState(INCREATE);
	}

	@Override
	public synchronized void notify(EventContent event)
	{
		String message = event.getType();
		if (Idcp.EVENT_RESERVATION_CREATE_CONFIRMED.equals(message) && isState(INCREATE)) {
			isCreateConfirmed = true;
			setPathInfo(event.getResDetails().getPathInfo());
			if (isScheduled) {
				gotoPending();
			}
		} else if (Idcp.EVENT_DOWNSTREAM_PATH_SETUP_CONFIRMED.equals(message) && isState(INSETUP)) {
			sendNotification(Idcp.EVENT_DOWNSTREAM_PATH_SETUP_CONFIRMED, INSETUP, null);
			gotoActive();
		} else if (Idcp.EVENT_DOWNSTREAM_PATH_TEARDOWN_CONFIRMED.equals(message) && isState(INTEARDOWN)) {
			sendNotification(Idcp.EVENT_DOWNSTREAM_PATH_TEARDOWN_CONFIRMED, INTEARDOWN, null);
			setState(FINISHED);
			sendNotification(Idcp.EVENT_PATH_TEARDOWN_COMPLETED, FINISHED, null);
			removeReservation();
		} else if (Idcp.EVENT_RESERVATION_CANCEL_CONFIRMED.equals(message)) {
			isCancelConfirmed = true;
			if (isCancelled) {
				gotoCancelled();
			}
		}
	}

	private void setPathInfo(PathInfo pi)
	{
		this.pathInfo = pi;
		int vlanNumber = reservation.getGlobalConstraints().getDomainConstraints().get(0).getFirstPathConstraints().getRangeConstraints().get(0).getFirstValue();
		String domain = IdcpManager.getDomainName();
		Idcp.setVlans(pi, domain, vlanNumber);
	}

	private void gotoPending()
	{
		assert(isCreateConfirmed && isScheduled && isState(INCREATE));
		setState(PENDING);
		sendNotification(Idcp.EVENT_RESERVATION_CREATE_COMPLETED, PENDING, null);
		if (isActive) {
			gotoInSetup();
		}
	}

	private void gotoInSetup()
	{
		assert(isActive && isState(PENDING));
		setState(INSETUP);
		sendNotification(Idcp.EVENT_UPSTREAM_PATH_SETUP_CONFIRMED, INSETUP, null);
	}

	private void gotoActive()
	{
		setState(ACTIVE);
		sendNotification(Idcp.EVENT_PATH_SETUP_COMPLETED, ACTIVE, null);
		if (isFinished) {
			gotoInTeardown();
		}
	}

	private void gotoInTeardown()
	{
		assert(isFinished && isState(ACTIVE));

		setState(INTEARDOWN);
		sendNotification(Idcp.EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED, INTEARDOWN, null);
	}

	private void gotoCancelled()
	{
		assert(isCancelled && isCancelConfirmed);

		setState(CANCELLED);
		sendNotification(Idcp.EVENT_RESERVATION_CANCEL_COMPLETED, CANCELLED, null);
		removeReservation();
	}

	@Override
	public synchronized void reservationScheduled(String reservationId)
	{
		isScheduled = true;
		if (isCreateConfirmed) {
			gotoPending();
		}
	}

	@Override
	public synchronized void reservationActive(String reservationId)
	{
		isActive = true;
		if (isState(PENDING)) {
			gotoInSetup();
		}
	}

	@Override
	public synchronized void reservationFinished(String reservationId)
	{
		isFinished = true;
		if (isState(ACTIVE)) {
			gotoInTeardown();
		}
	}

	@Override
	public synchronized void reservationProcessingFailed(String reservationId, String cause)
	{
		setState(FAILED);
		if (!isScheduled) {
			sendNotification(Idcp.EVENT_RESERVATION_CREATE_FAILED, FAILED, cause);
		} else {
			sendNotification(Idcp.EVENT_PATH_SETUP_FAILED, FAILED, cause);
		}
		removeReservation();
	}

	@Override
	public synchronized void reservationCancelled(String reservationId)
	{
		isCancelled = true;
		if (isCancelConfirmed) {
			gotoCancelled();
		}
	}

	@Override
	public synchronized void reservationModified(String reservationId, boolean success) {

		if (success) {
			sendNotification(Idcp.EVENT_RESERVATION_MODIFY_COMPLETED, getState(), null);
		} else {
			sendNotification(Idcp.EVENT_RESERVATION_MODIFY_FAILED, FAILED, null);
			removeReservation();
		}
	}
}
