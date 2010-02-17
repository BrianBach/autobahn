/*
 * HibernateIdmDAOFactory.java
 *
 * 2007-07-03
 */
package net.geant.autobahn.dao.hibernate;

import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.network.dao.AdminDomainDAO;
import net.geant.autobahn.network.dao.IDMNodeDAO;
import net.geant.autobahn.network.dao.LinkDAO;
import net.geant.autobahn.network.dao.PathDAO;
import net.geant.autobahn.network.dao.PortDAO;
import net.geant.autobahn.network.dao.ProvisioningDomainDAO;
import net.geant.autobahn.network.dao.hibernate.HibernateAdminDomainDAO;
import net.geant.autobahn.network.dao.hibernate.HibernateIDMNodeDAO;
import net.geant.autobahn.network.dao.hibernate.HibernateLinkDAO;
import net.geant.autobahn.network.dao.hibernate.HibernatePathDAO;
import net.geant.autobahn.network.dao.hibernate.HibernatePortDAO;
import net.geant.autobahn.network.dao.hibernate.HibernateProvisioningDomainDAO;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.dao.ServiceDAO;
import net.geant.autobahn.reservation.dao.UserDAO;
import net.geant.autobahn.reservation.dao.hibernate.HibernateReservationDAO;
import net.geant.autobahn.reservation.dao.hibernate.HibernateServiceDAO;
import net.geant.autobahn.reservation.dao.hibernate.HibernateUserDAO;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class HibernateIdmDAOFactory implements IdmDAOFactory {

    private static IdmDAOFactory instance = null;
    private IdmHibernateUtil hbm = IdmHibernateUtil.getInstance();
    
    private AdminDomainDAO ad_dao = new HibernateAdminDomainDAO(hbm);
    private ProvisioningDomainDAO pd_dao = new HibernateProvisioningDomainDAO(hbm);
    private IDMNodeDAO idmndao = new HibernateIDMNodeDAO(hbm);
    private PortDAO pdao = new HibernatePortDAO(hbm);
    private LinkDAO ldao = new HibernateLinkDAO(hbm);
    private PathDAO pth_dao = new HibernatePathDAO(hbm);
    
    private ReservationDAO rdao = new HibernateReservationDAO(hbm);
    private ServiceDAO sdao = new HibernateServiceDAO(hbm);
    private UserDAO udao = new HibernateUserDAO(hbm);

    
    private HibernateIdmDAOFactory() {
        
    }
    
    public static IdmDAOFactory getInstance() {
        if(instance == null) {
            instance = new HibernateIdmDAOFactory();
        }
        
        return instance;
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getReservationDAO()
     */
    public ReservationDAO getReservationDAO() {
        return rdao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getServiceDAO()
     */
    public ServiceDAO getServiceDAO() {
        return sdao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getPathDAO()
     */
    public PathDAO getPathDAO() {
        return pth_dao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getUserDAO()
     */
    public UserDAO getUserDAO() {
        return udao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getPortDAO()
     */
    public PortDAO getPortDAO() {
        return pdao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getLinkDAO()
     */
    public LinkDAO getLinkDAO() {
        return ldao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getNodeDAO()
     */
    public IDMNodeDAO getIDMNodeDAO() {
        return idmndao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getAdminDomainDAO()
     */
    public AdminDomainDAO getAdminDomainDAO() {
        return ad_dao;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.dao.DAOFactory#getProvisioningDomainDAO()
     */
    public ProvisioningDomainDAO getProvisioningDomainDAO() {
        return pd_dao;
    }
}
