package net.geant.autobahn.gui;

import java.net.MalformedURLException;

import net.geant.autobahn.administration.Status;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.Service;

/**
 * Periodically sends status and other events
 * @author Michal
 *
 */

public class GuiNotifier implements Runnable, ReservationStatusListener {
	
	private Gui gui;
	private int update;
	private String domainName;
	private String domainUrl;
	private Thread t;
	private boolean quit;
	
	public GuiNotifier(String guiAddress, int update) throws MalformedURLException {
		
		gui = new GuiClient(guiAddress);
		this.update = update;
		this.domainName = AccessPoint.getInstance().getLocalDomain();
        this.domainUrl = AccessPoint.getInstance().getLocalDomainURL();		
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Tells notifier to stop sending updates
	 */
	public void stop() {
		
		quit = true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		while (!quit) {
						
			Status status = AccessPoint.getInstance().getStatus();
			gui.statusUpdated(domainName, domainUrl, status);
			
			try {
				Thread.sleep(update * 1000);
			} catch (InterruptedException e) { }
		}
	}

	private String getServiceId(String resId) {
		Service srv = AccessPoint.getInstance().getServiceForReservation(resId);
		if(srv != null)
			return srv.getBodID();
		
		return "";
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationActive(java.lang.String)
	 */
	public void reservationActive(String reservationId) {

		gui.reservationChanged(domainName, getServiceId(reservationId), reservationId, 
				ReservationChangedType.ACTIVE, "ACTIVE");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationCancelled(java.lang.String)
	 */
	public void reservationCancelled(String reservationId) {

		gui.reservationChanged(domainName, getServiceId(reservationId), reservationId, 
				ReservationChangedType.CANCELLED, "CANCELLED");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationFinished(java.lang.String)
	 */
	public void reservationFinished(String reservationId) {
		
		gui.reservationChanged(domainName, getServiceId(reservationId), reservationId, 
				ReservationChangedType.FINISHED, "FINISHED");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationProcessingFailed(java.lang.String, java.lang.String)
	 */
	public void reservationProcessingFailed(String reservationId, String cause) {

		gui.reservationChanged(domainName, getServiceId(reservationId), reservationId, 
				ReservationChangedType.FAILED, "FAILED - " + cause);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationScheduled(java.lang.String)
	 */
	public void reservationScheduled(String reservationId) {

		gui.reservationChanged(domainName, getServiceId(reservationId), reservationId, 
				ReservationChangedType.SCHEDULED, "SCHEDULED");
	}
	
	public void reservationModified(String reservationId, boolean success) {
		
		String action = success ? "modified" : "not modified";
		System.out.println("Reservation " + reservationId + " " + action);
	}
}
