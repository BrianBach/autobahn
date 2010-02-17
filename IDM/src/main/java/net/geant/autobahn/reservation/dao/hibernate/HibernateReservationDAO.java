/**
 * HibernateReservationDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.reservation.dao.hibernate;

import java.util.Collections;
import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernateReservationDAO extends
        HibernateGenericDAO<Reservation, String> implements ReservationDAO {

    public HibernateReservationDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}

	public List<Reservation> getRunningReservations() {
        List<Reservation> reservations = findByCriteria(
        		Restrictions.in("state", new Object[] {
        					HomeDomainState.ACTIVE.getCode(), 
        					HomeDomainState.SCHEDULED.getCode(),
        					HomeDomainState.SCHEDULING.getCode()        					
        				})); 

        return reservations;
    }

    public List<Reservation> getReservationsThroughDomain(String domainID) {
        // TODO Auto-generated method stub
        return null;
    }

    public Reservation getByBodID(String bodID) {
        List<Reservation> reservations = findByCriteria(Expression.eq("bodID", bodID));

        if (reservations == null || reservations.size() < 1) {
            return null;
        }

        return reservations.get(0);
    }

    @SuppressWarnings("unchecked")
	public List<Reservation> getReservationsThroughLink(String linkBodID) {
        
        Criteria crit = getSession().createCriteria(Link.class);
        crit.add(Expression.eq("bodID", linkBodID));
        
        Link l = (Link) crit.uniqueResult(); 
        
        if(l == null) {
            return Collections.EMPTY_LIST;
        }
        
        Query q = getSession().createQuery("from Reservation r " +
                "where :link in elements(r.path.links)");
        
        q.setEntity("link", l);
        
        return q.list();
    }
}
