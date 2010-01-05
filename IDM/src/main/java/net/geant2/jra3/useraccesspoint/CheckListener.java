package net.geant2.jra3.useraccesspoint;

import net.geant2.jra3.reservation.ReservationStatusListener;

public class CheckListener implements ReservationStatusListener {

	boolean result;
	
	public void reservationActive(String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void reservationCancelled(String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void reservationFinished(String reservationId) {
		// TODO Auto-generated method stub
		
	}

	public void reservationModified(String reservationId, boolean success) {
		// TODO Auto-generated method stub
		
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
