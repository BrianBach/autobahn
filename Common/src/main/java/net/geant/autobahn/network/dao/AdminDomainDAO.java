/*
 * AdminDomainDAO.java
 *
 * 2006-10-31
 */
package net.geant.autobahn.network.dao;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.AdminDomain;

/**
 * DAO class used for operations on AdminDomain instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface AdminDomainDAO extends GenericDAO<AdminDomain, String> {

    public AdminDomain getByBodID(String id);
}
