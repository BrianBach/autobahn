/*
 * HibernateEthLinkDAO.java
 *
 * 2007-03-29
 */
package net.geant.autobahn.intradomain.ethernet.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.ethernet.EthLink;
import net.geant.autobahn.intradomain.ethernet.dao.EthLinkDAO;

/**
 * Hibernate implementation of the DAO class.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateEthLinkDAO extends HibernateGenericDAO<EthLink, Long>
        implements EthLinkDAO {

	public HibernateEthLinkDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

}
