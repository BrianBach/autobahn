/*
 * ProvisioningDomainDAO.java
 *
 * 2006-10-31
 */
package net.geant2.jra3.network.dao;

import net.geant2.jra3.dao.GenericDAO;
import net.geant2.jra3.network.ProvisioningDomain;

/**
 * DAO class used for operations on ProvisioningDomain instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface ProvisioningDomainDAO extends GenericDAO<ProvisioningDomain, String> {

    public ProvisioningDomain getByBodID(String bodID);
}
