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
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.dao.ServiceDAO;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

import org.hibernate.Query;
import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateServiceDAO extends HibernateGenericDAO<Service, String>
        implements ServiceDAO {

    public HibernateServiceDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}

	public String generateNextId() {
        String domain = AccessPoint.getInstance().getProperty("domainName");
        domain = domain.trim().replaceAll("[\\W]", "_");
        
        return domain + "@" + System.currentTimeMillis(); 
    }

    public Service getByBodID(String bodID) {
        List<Service> services = findByCriteria(Expression.eq("bodID", bodID));
        
        if(services == null || services.size() < 1) {
            return null;
        }
        
        return services.get(0);
    }

	@SuppressWarnings("unchecked")
	public List<Service> getActiveServices() {
		
        Query q = getSession().createQuery(
        	"select distinct s " +
        	"  from Service s " +
        	"  join s.reservations as res " +
        	"  where res.state in (?, ?)")
        			.setInteger(0, HomeDomainState.ACTIVE.getCode())
        			.setInteger(1, HomeDomainState.SCHEDULED.getCode());

		return new ArrayList<Service>(q.list());
	}

}
