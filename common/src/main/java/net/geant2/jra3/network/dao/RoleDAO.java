/*
 * RoleDAO.java
 *
 * 2008-01-04
 */
package net.geant2.jra3.network.dao;

import java.util.List;

import net.geant2.jra3.network.Node;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Role;

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
