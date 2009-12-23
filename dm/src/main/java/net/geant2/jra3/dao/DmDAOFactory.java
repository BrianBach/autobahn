package net.geant2.jra3.dao;

import net.geant2.jra3.intradomain.common.dao.GenericInterfaceDAO;
import net.geant2.jra3.intradomain.common.dao.GenericLinkDAO;
import net.geant2.jra3.intradomain.dao.IntradomainReservationDAO;
import net.geant2.jra3.intradomain.ethernet.dao.EthLinkDAO;
import net.geant2.jra3.intradomain.ethernet.dao.EthPhysicalPortDAO;
import net.geant2.jra3.intradomain.ethernet.dao.SpanningTreeDAO;
import net.geant2.jra3.intradomain.ethernet.dao.VlanDAO;
import net.geant2.jra3.intradomain.ethernet.dao.VlanPortDAO;
import net.geant2.jra3.intradomain.sdh.dao.SdhDeviceDAO;
import net.geant2.jra3.intradomain.sdh.dao.StmLinkDAO;
import net.geant2.jra3.network.dao.LinkDAO;

/**
 * Interface for obtaining DAO objects needed in the DM.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface DmDAOFactory {
    
    // interdomain
    public LinkDAO getLinkDAO();

    // intradomain
    public VlanDAO getVlanDAO();
    
    public VlanPortDAO getVlanPortDAO();
    
    public SpanningTreeDAO getSpanningTreeDAO();
    
    public EthLinkDAO getEthLinkDAO();
    
    public EthPhysicalPortDAO getEthPhysicalPortDAO ();
    
    public GenericLinkDAO getGenericLinkDAO();
    
    public GenericInterfaceDAO getGenericInterfaceDAO();
    
    public StmLinkDAO getStmLinkDAO();
    
    public SdhDeviceDAO getSdhDeviceDAO();
    
    public net.geant2.jra3.intradomain.common.dao.NodeDAO getNodeDAO();
    
    public IntradomainReservationDAO getIntradomainReservationDAO();
}
