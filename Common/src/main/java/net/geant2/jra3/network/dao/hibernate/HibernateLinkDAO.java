/**
 * HibernateLinkDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.network.dao.hibernate;

import java.util.List;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.StateOper;
import net.geant2.jra3.network.dao.LinkDAO;

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
