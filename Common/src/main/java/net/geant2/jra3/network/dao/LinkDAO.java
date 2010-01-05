/*
 * LinkDAO.java
 *
 * 2006-10-31
 */
package net.geant2.jra3.network.dao;

import java.util.List;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.network.Link;

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
