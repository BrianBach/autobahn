package net.geant.autobahn.interdomain;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.TimeRange;

/**
 * Exposes web service methods to other IDMs. It is used by IDM in Interdomain 
 * communication to exchange reservation messages.
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */
@WebService(targetNamespace = "http://interdomain.autobahn.geant.net/", name = "Interdomain")
public interface Interdomain {
    
    /**
     * Used to create a new reservation in other domain
     * @param reservation <code>ReservationAxis</code> object
     */
    void scheduleReservation(@WebParam(name="reservation")Reservation reservation);

    /**
     * 
     * @param resID
     * @param startTime
     * @param endTime
     */
    void modifyReservation(@WebParam(name="resID")String resID, 
    		@WebParam(name="time")TimeRange time);
    
    /**
     * Used to cancel reservation by user
     * @param resID identifier of reservation
     */
    void cancelReservation(@WebParam(name="resID")String resID) throws NoSuchReservationException;
    
    /**
     * 
     * @param resID
     */
    void withdrawReservation(@WebParam(name="resID")String resID) throws NoSuchReservationException;
        
    /**
     * Used to receive information on reservation status
     * @param resID identifier of reservation
     * @param code error code
     * @param message message with additional info
     * @param success true if reservation has been scheduled, otherwise false
     * @param global <code>GlobalConstraints</code> globally agreed constraints, 
     *               null if not agreed or an error occured
     */
    void reportSchedule(@WebParam(name="resID")String resID, 
    		@WebParam(name="code")int code, @WebParam(name="message")String message, 
    		@WebParam(name="success")boolean success, @WebParam(name="global")GlobalConstraints global);
    
    /**
     * Used to report whether cancellation of reservation was successful
     * @param resID identifier of reservation
     * @param message message with additional info 
     * @param success true if reservation has been cancelled, otherwise false
     */
    void reportCancellation(@WebParam(name="resID")String resID, 
    		@WebParam(name="message")String message, @WebParam(name="success")boolean success);
    
    /**
     * 
     * @param resID
     * @param message
     * @param success
     */
    void reportModify(@WebParam(name="resID")String resID, 
    		@WebParam(name="time")TimeRange time, @WebParam(name="message")String message, 
    		@WebParam(name="success")boolean success);
    
    /**
     * 
     * @param resID
     * @param message
     * @param success
     */
    void reportWithdraw(@WebParam(name="resID")String resID, 
    		@WebParam(name="message")String message, @WebParam(name="success")boolean success);
    
    /**
     * Used to report whether activation of reservation was successful
     * @param resID identifier of reservation
     * @param message message with additional info
     * @param success true if reservation has been activated, otherwise false
     */
    void reportActive(@WebParam(name="resID")String resID, 
    		@WebParam(name="message")String message, @WebParam(name="success")boolean success) throws NoSuchReservationException;

    /**
     * Used to report whether finish of reservation was successful
     * @param resID identifier of reservation
     * @param message message with additional info
     * @param success true if reservation has been finished, otherwise false
     */
    void reportFinished(@WebParam(name="resID")String resID,
    		@WebParam(name="message")String message, @WebParam(name="success")boolean success);
    
    /**
     * Used to check availbility of neighbor domain, should always return true
     * @return always true
     */
    @WebResult(name="True")
    boolean hello();
    
    @WebResult(name="bodId")
	public LinkIdentifiers getIdentifiers(@WebParam(name="portName")String portName, 
			@WebParam(name="linkBodId")String bodId);
}
