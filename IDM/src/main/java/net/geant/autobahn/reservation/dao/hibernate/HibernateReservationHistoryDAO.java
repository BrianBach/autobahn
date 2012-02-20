/**
 * HibernateReservationHistoryDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.reservation.dao.hibernate;

import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.reservation.ReservationHistory;
import net.geant.autobahn.reservation.dao.ReservationHistoryDAO;
import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 * 
 */
public class HibernateReservationHistoryDAO extends
        HibernateGenericDAO<ReservationHistory, String> implements ReservationHistoryDAO {

    public HibernateReservationHistoryDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}


    public ReservationHistory getByBodID(String bodID) {
        List<ReservationHistory> reservations = findByCriteria(Expression.eq("bodID", bodID));

        if (reservations == null || reservations.size() < 1) {
            return null;
        }

        return reservations.get(0);
    }
}
