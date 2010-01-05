package net.geant2.jra3.reservation;

import java.util.TimerTask;

/**
 * 
 * @author jacek
 *
 */
public abstract class ReservationTimeout extends TimerTask {

	protected String resId;
	
	public ReservationTimeout(String resId) {
		this.resId = resId;
	}

}
