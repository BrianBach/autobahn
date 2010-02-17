/*
 * ProvisioningDomainDAO.java
 *
 * 2006-10-31
 */
package net.geant.autobahn.network.dao;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.network.ProvisioningDomain;

/**
 * DAO class used for operations on ProvisioningDomain instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface ProvisioningDomainDAO extends GenericDAO<ProvisioningDomain, String> {

    public ProvisioningDomain getByBodID(String bodID);
}
