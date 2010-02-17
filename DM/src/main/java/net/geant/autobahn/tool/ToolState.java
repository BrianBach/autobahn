package net.geant.autobahn.tool;

/**
 * Class representing the current state of Techonology Proxy from the point of
 * view of a single reservation.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class ToolState {
	
	private boolean busy = true;
	private boolean added = true;
	
	/**
	 * Call it when previous task in TP has ended.
	 * 
	 * @param success Flag whether reservation has been added successfully
	 */
	public void taskEnd(boolean success) {
		busy = false;
		added = success;
	}

	/**
	 * Indicates if the TP the previous task is still being processed in the TP.
	 * 
	 * @return result
	 */
	public boolean isBusy() {
		return busy;
	}
	
	/**
	 * Indicates if the reservation has been added to the TP.
	 * 
	 * @return result
	 */
	public boolean isAdded() {
		return added;
	}
}
