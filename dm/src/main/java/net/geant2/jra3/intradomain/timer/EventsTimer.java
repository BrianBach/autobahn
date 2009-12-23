package net.geant2.jra3.intradomain.timer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class allows scheduling certain tasks for reservations.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EventsTimer {

	private Map<String, Timer> timers = new HashMap<String, Timer>();

	/**
	 * Schedules task for the given reservation to be exectued at the given time.
	 * 
	 * @param resID String reservation identifier
	 * @param time Calendar time that the task should be executed
	 * @param task Task to be executed
	 */
	public void schedule(String resID, Calendar time, TimerTask task) {
		Timer t = getTimer(resID);
		
		t.schedule(task, time.getTime());
	}
	
	/**
	 * Schedules task for the given reservation to be exectued immediatelly.
	 * 
	 * @param resID String reservation identifier
	 * @param task Task to be executed
	 */
	public void scheduleNow(String resID, TimerTask task) {
		Timer t = getTimer(resID);
		
		t.schedule(task, 0);
	}
	
	/**
	 * Cancels all tasks scheduled for the given reservation.
	 * 
	 * @param resID String reservation identifier
	 */
	public void cancel(String resID) {
		Timer t = timers.remove(resID);
		
		if(t != null)
			t.cancel();
	}
	
	/**
	 * Cancels all tasks scheduled for all reservations in the system. 
	 */
	public void cancelAll() {
		for(Timer t : timers.values())
			if(t != null)
				t.cancel();
		
		timers.clear();
	}
	
	private Timer getTimer(String resID) {
		Timer timer = timers.get(resID);
		if(timer == null) {
			timer = new Timer(true);
			timers.put(resID, timer);
		}
		
		return timer;
	}
}
