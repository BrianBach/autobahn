/**
 * HibernatePathDAO.java
 *
 * 2007-01-19
 */
package net.geant2.jra3.network.dao.hibernate;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.network.Path;
import net.geant2.jra3.network.dao.PathDAO;

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
