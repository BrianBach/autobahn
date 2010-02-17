/*
 * RoleDAO.java
 *
 * 2008-01-04
 */
package net.geant.autobahn.network.dao;

import java.util.List;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.Role;

/**
 * DAO class used for operations on Role instances.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public interface RoleDAO {
    
    public void create(Role instance);
    
    public Role get(Node node, Link link);

    public List<Role> getAll();
    
    public void update(Role transientObject);
    
    public void delete(Role persistentObject);
}
