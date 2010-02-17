/*
 * PortDAO.java
 *
 * 2006-10-31
 */
package net.geant.autobahn.network.dao;

import java.util.List;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.Port;

/**
 * DAO class used for operations on Port instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface PortDAO extends GenericDAO<Port, String> {

    public Port getByBodID(String bodID);
    
    public List<Port> getClientPorts();
    
    public List<Port> getDomainClientPorts(String domainID);
}
