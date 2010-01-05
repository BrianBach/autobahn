package net.geant2.jra3.reservation;

import java.util.Calendar;

public interface ReservationEvents {

	public void cancel();
	
	public void withdraw();

	public void recover();
	
	public void activate(boolean success);
	
	public void finish(boolean success);
	
	public void modify(Calendar startTime, Calendar endTime);
}
