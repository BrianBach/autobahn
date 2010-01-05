/*
 * HibernateIdmDAOFactory.java
 *
 * 2007-07-03
 */
package net.geant2.jra3.dao.hibernate;

import net.geant2.jra3.dao.IdmDAOFactory;
import net.geant2.jra3.network.dao.AdminDomainDAO;
import net.geant2.jra3.network.dao.IDMNodeDAO;
import net.geant2.jra3.network.dao.LinkDAO;
import net.geant2.jra3.network.dao.PathDAO;
import net.geant2.jra3.network.dao.PortDAO;
import net.geant2.jra3.network.dao.ProvisioningDomainDAO;
import net.geant2.jra3.network.dao.hibernate.HibernateAdminDomainDAO;
import net.geant2.jra3.network.dao.hibernate.HibernateIDMNodeDAO;
import net.geant2.jra3.network.dao.hibernate.HibernateLinkDAO;
import net.geant2.jra3.network.dao.hibernate.HibernatePathDAO;
import net.geant2.jra3.network.dao.hibernate.HibernatePortDAO;
import net.geant2.jra3.network.dao.hibernate.HibernateProvisioningDomainDAO;
import net.geant2.jra3.reservation.dao.ReservationDAO;
import net.geant2.jra3.reservation.dao.ServiceDAO;
import net.geant2.jra3.reservation.dao.UserDAO;
import net.geant2.jra3.reservation.dao.hibernate.HibernateReservationDAO;
import net.geant2.jra3.reservation.dao.hibernate.HibernateServiceDAO;
import net.geant2.jra3.reservation.dao.hibernate.HibernateUserDAO;

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
     * @see net.geant2.jra3.dao.DAOFactory#getReservationDAO()
     */
    public ReservationDAO getReservationDAO() {
        return rdao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getServiceDAO()
     */
    public ServiceDAO getServiceDAO() {
        return sdao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getPathDAO()
     */
    public PathDAO getPathDAO() {
        return pth_dao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getUserDAO()
     */
    public UserDAO getUserDAO() {
        return udao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getPortDAO()
     */
    public PortDAO getPortDAO() {
        return pdao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getLinkDAO()
     */
    public LinkDAO getLinkDAO() {
        return ldao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getNodeDAO()
     */
    public IDMNodeDAO getIDMNodeDAO() {
        return idmndao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getAdminDomainDAO()
     */
    public AdminDomainDAO getAdminDomainDAO() {
        return ad_dao;
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.dao.DAOFactory#getProvisioningDomainDAO()
     */
    public ProvisioningDomainDAO getProvisioningDomainDAO() {
        return pd_dao;
    }
}
