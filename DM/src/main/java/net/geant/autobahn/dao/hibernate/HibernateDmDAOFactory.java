package net.geant.autobahn.dao.hibernate;

import net.geant.autobahn.dao.DmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.intradomain.common.dao.GenericInterfaceDAO;
import net.geant.autobahn.intradomain.common.dao.GenericLinkDAO;
import net.geant.autobahn.intradomain.common.dao.NodeDAO;
import net.geant.autobahn.intradomain.common.dao.hibernate.HibernateGenericInterfaceDAO;
import net.geant.autobahn.intradomain.common.dao.hibernate.HibernateGenericLinkDAO;
import net.geant.autobahn.intradomain.common.dao.hibernate.HibernateNodeDAO;
import net.geant.autobahn.intradomain.dao.HibernateIntradomainReservationDAO;
import net.geant.autobahn.intradomain.dao.IntradomainReservationDAO;
import net.geant.autobahn.intradomain.ethernet.dao.EthLinkDAO;
import net.geant.autobahn.intradomain.ethernet.dao.EthPhysicalPortDAO;
import net.geant.autobahn.intradomain.ethernet.dao.SpanningTreeDAO;
import net.geant.autobahn.intradomain.ethernet.dao.VlanDAO;
import net.geant.autobahn.intradomain.ethernet.dao.VlanPortDAO;
import net.geant.autobahn.intradomain.ethernet.dao.hibernate.HibernateEthLinkDAO;
import net.geant.autobahn.intradomain.ethernet.dao.hibernate.HibernateEthPhysicalPortDAO;
import net.geant.autobahn.intradomain.ethernet.dao.hibernate.HibernateSpanningTreeDAO;
import net.geant.autobahn.intradomain.ethernet.dao.hibernate.HibernateVlanDAO;
import net.geant.autobahn.intradomain.ethernet.dao.hibernate.HibernateVlanPortDAO;
import net.geant.autobahn.intradomain.mpls.dao.MplsLinkDAO;
import net.geant.autobahn.intradomain.mpls.dao.hibernate.HibernateMplsLinkDAO;
import net.geant.autobahn.intradomain.sdh.dao.SdhDeviceDAO;
import net.geant.autobahn.intradomain.sdh.dao.StmLinkDAO;
import net.geant.autobahn.intradomain.sdh.dao.hibernate.HibernateSdhDeviceDAO;
import net.geant.autobahn.intradomain.sdh.dao.hibernate.HibernateStmLinkDAO;
import net.geant.autobahn.network.dao.LinkDAO;
import net.geant.autobahn.network.dao.hibernate.HibernateLinkDAO;

/**
 * Factory class for obtaining DAO objects for the persistent entities of the
 * Domain Manager. Implementation of the Singleton pattern.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernateDmDAOFactory implements DmDAOFactory {

    private static DmDAOFactory instance = null;
    private HibernateUtil hbm = DmHibernateUtil.getInstance();
    
    private VlanDAO vldao = new HibernateVlanDAO(hbm);
    private VlanPortDAO vlpdao = new HibernateVlanPortDAO(hbm);
    private SpanningTreeDAO stdao = new HibernateSpanningTreeDAO(hbm);
    private EthLinkDAO eldao = new HibernateEthLinkDAO(hbm);
    private EthPhysicalPortDAO eppdao = new HibernateEthPhysicalPortDAO(hbm);
    private GenericLinkDAO gldao = new HibernateGenericLinkDAO(hbm);
    private GenericInterfaceDAO gidao = new HibernateGenericInterfaceDAO(hbm);
    private NodeDAO ndao = new HibernateNodeDAO(hbm);
    private LinkDAO ldao = new HibernateLinkDAO(hbm);
    private StmLinkDAO stm_link_dao = new HibernateStmLinkDAO(hbm);
    private SdhDeviceDAO sdh_device_dao = new HibernateSdhDeviceDAO(hbm);
    private IntradomainReservationDAO intra_res_dao = new HibernateIntradomainReservationDAO(hbm);
    private MplsLinkDAO mldao = new HibernateMplsLinkDAO(hbm);
    
    private HibernateDmDAOFactory() {
    }
    
    /**
     * Returns the only instance of the class.
     * 
     * @return DmDAOFactory instance.
     */
    public static DmDAOFactory getInstance() {
        if(instance == null) {
            instance = new HibernateDmDAOFactory();
        }
        
        return instance;
    }
    
    /**
     * Returns VlanDAO instance.
     */
    public VlanDAO getVlanDAO() {
        return vldao;
    }

    /**
     * Returns VlanPortDAO instance.
     */
    public VlanPortDAO getVlanPortDAO() {
        return vlpdao;
    }
    
    /**
     * Returns spanning tree DAO instance.
     */
    public SpanningTreeDAO getSpanningTreeDAO() {
        return stdao;
    }

    /**
     * Returns EthLinkDAO instance.
     */
    public EthLinkDAO getEthLinkDAO() {
        return eldao;
    }

    /**
     * Returns EthPhysicalPortDAO instance.
     */
    public EthPhysicalPortDAO getEthPhysicalPortDAO() {
        return eppdao;
    }
    
    /**
     * Returns GenericLinkDAO instance
     */
    public GenericLinkDAO getGenericLinkDAO() {
        return gldao;
    }

    /**
     * Returns GenericInterfaceDAO instance
     */
    public GenericInterfaceDAO getGenericInterfaceDAO() {
        return gidao;
    }

    /**
     * Returns NodeDAO instance
     */
    public NodeDAO getNodeDAO() {
        return ndao;
    }

    /**
     * Returns LinkDAO instance
     */
    public LinkDAO getLinkDAO() {
        return ldao;
    }

    /**
     * Returns StmLinkDAO instance
     */
    public StmLinkDAO getStmLinkDAO() {
    	return stm_link_dao;
    }

    /**
     * Returns SdhDeviceDAO instance
     */
    public SdhDeviceDAO getSdhDeviceDAO() {
    	return sdh_device_dao;
    }

    /**
     * Returns IntradomainReservationDAO instance
     */
	public IntradomainReservationDAO getIntradomainReservationDAO() {
		return intra_res_dao;
	}
	
	/**
	 * Returns MplsLinkDAO
	 */
	public MplsLinkDAO getMplsLinkDAO() { 
		return mldao;
	}
}
