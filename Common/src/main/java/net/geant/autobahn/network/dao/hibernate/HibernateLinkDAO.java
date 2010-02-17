/**
 * HibernateLinkDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.network.dao.hibernate;

import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.network.dao.LinkDAO;

import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateLinkDAO extends HibernateGenericDAO<Link, String> implements
        LinkDAO {

    public HibernateLinkDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public Link getByBodID(String bodID) {
        
        List<Link> links = findByCriteria(Expression.eq("bodID", bodID));
        
        if(links == null || links.size() < 1) {
            return null;
        }
        
        return links.get(0);
    }

	public List<Link> getValidLinks() {
		
		return findByCriteria(Expression.eq("operationalState", StateOper.UP));
	}
}
