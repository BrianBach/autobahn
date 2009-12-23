/*
 * ServiceDAO.java
 *
 * 2006-02-16
 */
package net.geant2.jra3.reservation.dao;

import java.util.List;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.reservation.Service;

/**
 * <code>ServiceDAO</code> interface is used to access services data.
 * Contains methods for inserting, updating, retrieving and removing services. 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface ServiceDAO extends GenericDAO<Service, String> {
    
    /**
     * Generates unique identifier for a service.
     * 
     * @return <code>String</code> unique identifier
     */
    public String generateNextId();
    
    /**
     * Return <code>Service</code> instance identified by specified bodID
     * 
     * @param bodID <code>String</code> identifier
     * @return <code>Service</code> instance, null if no object exists for the
     *         given identifier
     */
    public Service getByBodID(String bodID);
    
    /**
     * 
     * @return
     */
    public List<Service> getActiveServices(); 
}
