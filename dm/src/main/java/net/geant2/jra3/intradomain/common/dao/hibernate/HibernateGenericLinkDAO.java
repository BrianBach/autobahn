/*
 * HibernateGenericLinkDAO.java
 *
 * 2007-03-29
 */
package net.geant2.jra3.intradomain.common.dao.hibernate;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.dao.GenericLinkDAO;

/**
 * Implementation of the DAO based on Hibernate.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateGenericLinkDAO extends
        HibernateGenericDAO<GenericLink, Long> implements GenericLinkDAO {

	public HibernateGenericLinkDAO(HibernateUtil hibernate) {
		super(hibernate);
	}
}
