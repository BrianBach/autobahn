/**
 * HibernateAdminDomainDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.network.dao.hibernate;

import java.util.List;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.dao.AdminDomainDAO;

import org.hibernate.criterion.Expression;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateAdminDomainDAO extends
        HibernateGenericDAO<AdminDomain, String> implements AdminDomainDAO {

    public HibernateAdminDomainDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public AdminDomain getByBodID(String id) {
        List<AdminDomain> domains = findByCriteria(Expression.eq("bodID", id));
        
        if(domains == null || domains.size() < 1) {
            return null;
        }
        
        return domains.get(0);
    }

}
