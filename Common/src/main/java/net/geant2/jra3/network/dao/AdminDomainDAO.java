/*
 * AdminDomainDAO.java
 *
 * 2006-10-31
 */
package net.geant2.jra3.network.dao;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.network.AdminDomain;

/**
 * DAO class used for operations on AdminDomain instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface AdminDomainDAO extends GenericDAO<AdminDomain, String> {

    public AdminDomain getByBodID(String id);
}
