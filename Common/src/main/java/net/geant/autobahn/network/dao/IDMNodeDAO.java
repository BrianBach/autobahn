/*
 * NodeDAO.java
 *
 * 2006-10-31
 */
package net.geant.autobahn.network.dao;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.Node;

/**
 * DAO class used for operations on InterdomainNode instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface IDMNodeDAO extends GenericDAO<Node, String> {

    public Node getByBodID(String bodID);
}
