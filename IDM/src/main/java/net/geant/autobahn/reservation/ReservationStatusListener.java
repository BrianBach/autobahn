package net.geant.autobahn.reservation;

/**
 * <code>ReservationStatusListener</code> interface is used to
 * notify Service object about its Reservations
 * status changes
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public interface ReservationStatusListener {

    /**
     * Reservation allocated resources
     * @param reservationId identifer for reservation
     */
    public void reservationScheduled(String reservationId);
    
    /**
     * Resources are ready for utilization by user
     * @param reservationId identifier for reservation
     */
    public void reservationActive(String reservationId);
    
    /**
     * Reservation completed successfully
     * @param reservationId identifier for reservation
     */
    public void reservationFinished(String reservationId);
    
    /**
     * During resource reservation process some problem occured
     * @param reservationId identifer for reservation
     */
    public void reservationProcessingFailed(String reservationId, String cause);

    /**
     * Reservation was cancelled by user request
     * @param reservationId identifer for reservation
     */
    public void reservationCancelled(String reservationId);
    
    /**
     * 
     * @param reservationId
     * @param success
     */
    public void reservationModified(String reservationId, boolean success);
}
