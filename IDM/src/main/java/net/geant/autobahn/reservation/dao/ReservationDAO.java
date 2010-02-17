/*
 * ReservationDAO.java
 *
 * 2006-02-16
 */
package net.geant.autobahn.reservation.dao;

import java.util.List;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.reservation.Reservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public interface ReservationDAO extends GenericDAO<Reservation, String> {
    
    /**
     * Returns all reservations that are not in final state. Where final means: FINISHED or FAILED or CANCELLED
     * 
     * @return List of Reservation objects that has not finished
     */
    public List<Reservation> getRunningReservations();
    
    /**
     * Returns list of reservations that includes specified domain.
     * 
     * @param domainID String identifier of domain
     * @return List of Reservation objects that includes specified domain
     */
    public List<Reservation> getReservationsThroughDomain(String domainID);

    /**
     * Returns reservation that are scheduled through specified link.
     * 
     * @param linkID String identifier of the link
     * @return List of Reservation objects, null if not found
     */
    public List<Reservation> getReservationsThroughLink(String linkID);
    
    /**
     * Return <code>Reservation</code> instance identified by specified bodID
     * 
     * @param bodID <code>String</code> identifier
     * @return <code>Reservation</code> instance, null if no object exists for the
     *         given identifier
     */
    public Reservation getByBodID(String bodID);
}
