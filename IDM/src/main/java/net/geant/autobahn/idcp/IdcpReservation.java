package net.geant.autobahn.idcp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import net.geant.autobahn.reservation.Reservation;

/**
 * Base class for IDCP reservations. Wraps an Autobahn reservation.
 */
public abstract class IdcpReservation
{
	private static final Logger log = Logger.getLogger(IdcpReservation.class);

	private static ConcurrentMap<String, IdcpReservation> reservations =
		new ConcurrentHashMap<String, IdcpReservation>();

	protected enum State { ACCEPTED, INCREATE, PENDING, INSETUP, ACTIVE,
			INMODIFY, INTEARDOWN, CANCELLED, FINISHED, FAILED };

	protected State state = State.ACCEPTED;
	protected final String id;
	protected final Reservation reservation;
	protected final long createTime = System.currentTimeMillis();
	protected PathInfo pathInfo;

	public IdcpReservation(String id, Reservation reservation, PathInfo pathInfo)
	{
		this.id = id;
		this.reservation = reservation;
		this.pathInfo = pathInfo;
		addReservation(id, this);
	}

	public static void addReservation(String id, IdcpReservation res)
	{
		reservations.put(id, res);
	}

	public static IdcpReservation getReservation(String id)
	{
		return reservations.get(id);
	}

	protected boolean isState(State state)
	{
		return this.state == state;
	}

	protected State getState()
	{
		return this.state;
	}

	protected void setState(State state)
	{
		log.debug("Reservation " + id + " changes state from " + this.state + " to " + state);
		this.state = state;
	}

	public ResDetails getResDetails()
	{
		ResDetails resDetails = new ResDetails();
		resDetails.setPathInfo(pathInfo);
		resDetails.setDescription(reservation.getDescription());
		resDetails.setLogin("autobahn");
		resDetails.setStartTime(reservation.getStartTime().getTimeInMillis() / Idcp.TIME_SCALE);
		resDetails.setEndTime(reservation.getEndTime().getTimeInMillis() / Idcp.TIME_SCALE);
		resDetails.setCreateTime(createTime / Idcp.TIME_SCALE);
		resDetails.setBandwidth((int) (reservation.getCapacity() / Idcp.BANDWIDTH_SCALE));
		resDetails.setStatus(getState().toString());
		resDetails.setGlobalReservationId(id);
		return resDetails;
	}

	public void removeReservation()
	{
		reservations.remove(id, this);
	}

	public abstract void notify(EventContent event);

	protected void sendNotification(String eventType, State status, String errorMessage)
	{
		final String desc = reservation.getDescription();
		final long start = reservation.getStartTime().getTimeInMillis();
		final long end = reservation.getEndTime().getTimeInMillis();
		final int bandwidth = (int) reservation.getCapacity();

		for (SubscriptionInfo si: IdcpManager.getSubscribers()) {
			try {
				IdcpNotifyClient client = new IdcpNotifyClient(si.getConsumerUrl());
				client.notification(id, desc, start, end, createTime, bandwidth, pathInfo, eventType,
									status.toString(), errorMessage, si.getSubscriptionId());
				log.info("sent notification to " + si.getConsumerUrl() + ", event " + eventType);
			} catch (IdcpException e) {
				log.info("error sending notification to " + si.getConsumerUrl());
			}
		}
	}
}
