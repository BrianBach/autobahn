/**
 * HibernateNodeDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.network.dao.hibernate;

import java.util.List;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.network.Node;
import net.geant2.jra3.network.dao.IDMNodeDAO;

import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateIDMNodeDAO extends HibernateGenericDAO<Node, String> implements
        IDMNodeDAO {

    public HibernateIDMNodeDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public Node getByBodID(String bodID) {
        List<Node> nodes = findByCriteria(Expression.eq("bodID", bodID));
        
        if(nodes == null || nodes.size() < 1) {
            return null;
        }
        
        return nodes.get(0);
    }

}
