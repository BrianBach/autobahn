package net.geant.autobahn.dao;

import net.geant.autobahn.network.dao.AdminDomainDAO;
import net.geant.autobahn.network.dao.IDMNodeDAO;
import net.geant.autobahn.network.dao.LinkDAO;
import net.geant.autobahn.network.dao.PathDAO;
import net.geant.autobahn.network.dao.PortDAO;
import net.geant.autobahn.network.dao.ProvisioningDomainDAO;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.dao.ServiceDAO;
import net.geant.autobahn.reservation.dao.UserDAO;

public interface IdmDAOFactory {

    public ReservationDAO getReservationDAO();
    
    public ServiceDAO getServiceDAO();
    
    public PathDAO getPathDAO();
    
    public UserDAO getUserDAO();
    
    public PortDAO getPortDAO();
    
    public LinkDAO getLinkDAO();
    
    public IDMNodeDAO getIDMNodeDAO();
    
    public AdminDomainDAO getAdminDomainDAO();
    
    public ProvisioningDomainDAO getProvisioningDomainDAO();
    
}
