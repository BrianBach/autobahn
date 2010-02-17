/*
 * HibernateNodeDAO.java
 *
 * 2007-03-29
 */
package net.geant.autobahn.intradomain.common.dao.hibernate;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.common.dao.NodeDAO;

/**
 * Implementation of the DAO based on Hibernate.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernateNodeDAO extends HibernateGenericDAO<Node, Long> implements
        NodeDAO {

	public HibernateNodeDAO(HibernateUtil hibernate) {
		super(hibernate);
	}

}
