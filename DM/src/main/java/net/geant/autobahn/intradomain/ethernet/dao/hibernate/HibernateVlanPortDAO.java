/*
 * HibernateVlanDAO.java
 *
 * 2007-03-29
 */
package net.geant.autobahn.intradomain.ethernet.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.ethernet.VlanPort;
import net.geant.autobahn.intradomain.ethernet.dao.VlanPortDAO;

/**
 * Hibernate implementation of the DAO class.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateVlanPortDAO extends HibernateGenericDAO<VlanPort, Long> implements
        VlanPortDAO {

	public HibernateVlanPortDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

}
