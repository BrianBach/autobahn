/*
 * LinkDAO.java
 *
 * 2006-10-31
 */
package net.geant.autobahn.network.dao;

import java.util.List;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.Link;

/**
 * DAO class used for operations on Link instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface LinkDAO extends GenericDAO<Link, String> {

    public Link getByBodID(String bodID);
    
    public List<Link> getValidLinks();
}
