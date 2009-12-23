/*
 * HibernateGenericInterfaceDAO.java
 *
 * 2007-03-29
 */
package net.geant2.jra3.intradomain.common.dao.hibernate;

import net.geant2.jra3.dao.hibernate.HibernateGenericDAO;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.common.GenericInterface;
import net.geant2.jra3.intradomain.common.dao.GenericInterfaceDAO;

/**
 * Implementation of the DAO based on Hibernate.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateGenericInterfaceDAO extends HibernateGenericDAO<GenericInterface, Long> implements
        GenericInterfaceDAO {

	public HibernateGenericInterfaceDAO(HibernateUtil hibernate) {
		super(hibernate);
	}
	
}
