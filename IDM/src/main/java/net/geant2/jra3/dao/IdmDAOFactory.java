package net.geant2.jra3.dao;

import net.geant2.jra3.network.dao.AdminDomainDAO;
import net.geant2.jra3.network.dao.IDMNodeDAO;
import net.geant2.jra3.network.dao.LinkDAO;
import net.geant2.jra3.network.dao.PathDAO;
import net.geant2.jra3.network.dao.PortDAO;
import net.geant2.jra3.network.dao.ProvisioningDomainDAO;
import net.geant2.jra3.reservation.dao.ReservationDAO;
import net.geant2.jra3.reservation.dao.ServiceDAO;
import net.geant2.jra3.reservation.dao.UserDAO;

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
