/**
 * HibernatePathDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.network.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.dao.PathDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernatePathDAO extends HibernateGenericDAO<Path, Long> implements
        PathDAO {

	public HibernatePathDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

}
