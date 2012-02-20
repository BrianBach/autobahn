/*
 * ServiceHistoryDAO.java
 *
 * 2012-02-13
 */
package net.geant.autobahn.reservation.dao;

import java.util.List;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.reservation.ServiceHistory;

/**
 * <code>ServiceHistoryDAO</code> interface is used to access services history data.
 * Contains methods for inserting, updating, retrieving and removing services history. 
 * @author <a href="mailto:kallige@ceid.upatras.gr">Akis Kalligeros</a>
 *
 */
public interface ServiceHistoryDAO extends GenericDAO<ServiceHistory, String> {
    
    /**
     * Return <code>ServiceHistory</code> instance identified by specified bodID
     * 
     * @param bodID <code>String</code> identifier
     * @return <code>ServiceHistory</code> instance, null if no object exists for the
     *         given identifier
     */
    public ServiceHistory getByBodID(String bodID);
    
    public List<ServiceHistory> getServices();

	public List<ServiceHistory> getServicess();
}
