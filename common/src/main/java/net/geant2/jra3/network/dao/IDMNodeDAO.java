/*
 * NodeDAO.java
 *
 * 2006-10-31
 */
package net.geant2.jra3.network.dao;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.network.Node;

/**
 * DAO class used for operations on InterdomainNode instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface IDMNodeDAO extends GenericDAO<Node, String> {

    public Node getByBodID(String bodID);
}
