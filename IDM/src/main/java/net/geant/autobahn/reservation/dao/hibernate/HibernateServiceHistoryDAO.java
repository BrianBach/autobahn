/**
 * HibernateServiceDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.reservation.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.ServiceHistory;
import net.geant.autobahn.reservation.dao.ServiceHistoryDAO;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

import org.hibernate.Query;
import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 *
 */
public class HibernateServiceHistoryDAO extends HibernateGenericDAO<ServiceHistory, String>
        implements ServiceHistoryDAO {

    public HibernateServiceHistoryDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}

    public ServiceHistory getByBodID(String bodID) {
        List<ServiceHistory> services = findByCriteria(Expression.eq("bodID", bodID));
        
        if(services == null || services.size() < 1) {
            return null;
        }
        
        return services.get(0);
    }
    
    public List<ServiceHistory> getServices() {
    	return this.getAll();
    }
    
	@SuppressWarnings("unchecked")
	public List<ServiceHistory> getServicess() {
		
        Query q = getSession().createQuery(
        	"select distinct s " +
        	"  from ServiceHistory s " +
        	"  join s.reservations as res ");

		return new ArrayList<ServiceHistory>(q.list());
	}

}
