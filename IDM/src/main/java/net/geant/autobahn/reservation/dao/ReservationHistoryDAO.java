/*
 * ReservationHistoryDAO.java
 *
 * 2012-02-13
 */
package net.geant.autobahn.reservation.dao;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.reservation.ReservationHistory;

/**
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 * 
 */
public interface ReservationHistoryDAO extends GenericDAO<ReservationHistory, String> {
    
    /**
     * Return <code>ReservationHistory</code> instance identified by specified bodID
     * 
     * @param bodID <code>String</code> identifier
     * @return <code>ReservationHistory</code> instance, null if no object exists for the
     *         given identifier
     */
    public ReservationHistory getByBodID(String bodID);
}
