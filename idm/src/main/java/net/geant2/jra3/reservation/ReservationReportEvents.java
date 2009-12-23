/**
 * 
 */
package net.geant2.jra3.reservation;

import java.util.Calendar;

import net.geant2.jra3.constraints.GlobalConstraints;

/**
 * @author jacek
 *
 */
public interface ReservationReportEvents {
	
    /**
     * Answer for <code>scheduleReseration</code> request
     * from remote system
     * @param success boolean value indicating status of reservation
     * @param msgCode int error code
     * @param arguments String with additional info
     * @param GlobalConstraints global constraints agreed along the path
     */
    void reservationScheduled(int msgCode,
            String arguments, boolean success, GlobalConstraints global);

    /**
     * Answer from another domain for cancel request.
     * @param success whether cancelation succeeded
     * @param message justification message 
     */
    void reservationCancelled(String message, boolean success);
    
    /**
     * 
     * @param message
     * @param success
     */
    void reservationWithdrawn(String message, boolean success);
    
    /**
     * Informs whether activation of reservation was successful
     * @param success true if activation was successful, otherwise false
     * @param message message with additional info
     */
    void reservationActivated(String message, boolean success);

    /**
     * 
     * @param startTime
     * @param endTime
     * @param message
     * @param success
     */
    void reservationModified(Calendar startTime, Calendar endTime,
			String message, boolean success);
    
    /**
     * Informs that reservation has finished.
     * @param success true if activation was successful, otherwise false
     * @param message with additional info
     */
    void reservationFinished(String message, boolean success);
}