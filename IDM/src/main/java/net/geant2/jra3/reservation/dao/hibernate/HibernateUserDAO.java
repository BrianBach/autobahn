/**
 * HibernateUserDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.reservation.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Expression;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.IdmHibernateUtil;
import net.geant2.jra3.reservation.User;
import net.geant2.jra3.reservation.dao.UserDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateUserDAO extends HibernateGenericDAO<User, String> implements
        UserDAO {

    public HibernateUserDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}

	public User getByName(String name) {
        List<User> users = findByCriteria(Expression.eq("name", name));
        
        if(users == null || users.size() < 1) {
            return null;
        }
        
        return users.get(0);
    }

}
