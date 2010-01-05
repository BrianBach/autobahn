package net.geant2.jra3.useraccesspoint.callback;

import java.net.MalformedURLException;

import net.geant2.jra3.reservation.ReservationStatusListener;

public class UapCallbackClient implements UapCallback, ReservationStatusListener {

	private UapCallback user;
	
	/**
	 * 
	 * @param endPoint
	 * @throws MalformedURLException
	 */
	public UapCallbackClient(String endPoint) {
		UapCallbackService service = new UapCallbackService(endPoint);
		user = service.getUapCallbackPort();
	}
	
	public void reservationActive(final String reservationId) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationActive(reservationId);
			}};
		
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void reservationCancelled(final String reservationId) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationCancelled(reservationId);
			}};
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void reservationFinished(final String reservationId) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationFinished(reservationId);
			}};
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void reservationModified(final String reservationId, final boolean success) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationModified(reservationId, success);
			}};
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void reservationProcessingFailed(final String reservationId, final String cause) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationProcessingFailed(reservationId, cause);
			}};
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void reservationScheduled(final String reservationId) {
		Runnable cmd = new Runnable() {
			public void run() {
				user.reservationScheduled(reservationId);
			}};
		//ThreadExecutor.getInstance().execute(r);
		new Thread(cmd).start();
	}

	public void domainUp(String address) {
		user.domainUp(address);
	}
	
	public static void main(String[] args) {
		UapCallback cli = new UapCallbackService(
				"http://localhost:9090/callback").getUapCallbackPort();
		
		cli.domainUp("ziom");
	}
}
