/**
 * HibernateProvisioningDomainDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.network.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Expression;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.network.dao.ProvisioningDomainDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateProvisioningDomainDAO 
        extends HibernateGenericDAO<ProvisioningDomain, String> 
        implements ProvisioningDomainDAO {

    public HibernateProvisioningDomainDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

	public ProvisioningDomain getByBodID(String bodID) {
        List<ProvisioningDomain> domains = findByCriteria(Expression.eq("bodID", bodID));
        
        if(domains == null || domains.size() < 1) {
            return null;
        }
        
        return domains.get(0);
    }

}
