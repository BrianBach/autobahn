package net.geant.autobahn.useraccesspoint;

import net.geant.autobahn.reservation.ReservationStatusListener;

public class CheckListener implements ReservationStatusListener {

	boolean result;
	
	public void reservationActive(String reservationId) {
		
	}

	public void reservationCancelled(String reservationId) {
		
	}

	public void reservationFinished(String reservationId) {
		
	}

	public void reservationModified(String reservationId, boolean success) {
		
	}

	public void reservationProcessingFailed(String reservationId, String cause) {
		result = false;
		
		synchronized(this) {
			this.notify();
		}
	}

	public void reservationScheduled(String reservationId) {
		result = true;
		
		synchronized(this) {
			this.notify();
		}
	}

	public boolean getResult() {
		return result;
	}
}
