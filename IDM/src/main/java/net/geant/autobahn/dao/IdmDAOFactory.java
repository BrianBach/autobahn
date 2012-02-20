package net.geant.autobahn.dao;

import net.geant.autobahn.network.dao.AdminDomainDAO;
import net.geant.autobahn.network.dao.IDMNodeDAO;
import net.geant.autobahn.network.dao.LinkDAO;
import net.geant.autobahn.network.dao.PathDAO;
import net.geant.autobahn.network.dao.PortDAO;
import net.geant.autobahn.network.dao.ProvisioningDomainDAO;
import net.geant.autobahn.network.dao.StatisticsEntryDAO;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.dao.ReservationHistoryDAO;
import net.geant.autobahn.reservation.dao.ServiceDAO;
import net.geant.autobahn.reservation.dao.ServiceHistoryDAO;
import net.geant.autobahn.reservation.dao.UserDAO;

public interface IdmDAOFactory {

    public ReservationDAO getReservationDAO();
    
    public ReservationHistoryDAO getReservationHistoryDAO();
    
    public ServiceDAO getServiceDAO();
    
    public ServiceHistoryDAO getServiceHistoryDAO();
    
    public PathDAO getPathDAO();
    
    public UserDAO getUserDAO();
    
    public PortDAO getPortDAO();
    
    public LinkDAO getLinkDAO();
    
    public IDMNodeDAO getIDMNodeDAO();
    
    public AdminDomainDAO getAdminDomainDAO();
    
    public ProvisioningDomainDAO getProvisioningDomainDAO();
    
    public StatisticsEntryDAO getStatisticsEntryDAO();
	
}
