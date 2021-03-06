/*
 * HibernateVlanDAO.java
 *
 * 2007-03-29
 */
package net.geant.autobahn.intradomain.ethernet.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.ethernet.Vlan;
import net.geant.autobahn.intradomain.ethernet.dao.VlanDAO;

/**
 * Hibernate implementation of the DAO class.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateVlanDAO extends HibernateGenericDAO<Vlan, Long> implements
        VlanDAO {

	public HibernateVlanDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

}
