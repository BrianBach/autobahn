package net.geant.autobahn.dao;

import net.geant.autobahn.intradomain.common.dao.GenericInterfaceDAO;
import net.geant.autobahn.intradomain.common.dao.GenericLinkDAO;
import net.geant.autobahn.intradomain.dao.IntradomainReservationDAO;
import net.geant.autobahn.intradomain.ethernet.dao.EthLinkDAO;
import net.geant.autobahn.intradomain.ethernet.dao.EthPhysicalPortDAO;
import net.geant.autobahn.intradomain.ethernet.dao.SpanningTreeDAO;
import net.geant.autobahn.intradomain.ethernet.dao.VlanDAO;
import net.geant.autobahn.intradomain.ethernet.dao.VlanPortDAO;
import net.geant.autobahn.intradomain.mpls.dao.MplsLinkDAO;
import net.geant.autobahn.intradomain.sdh.dao.SdhDeviceDAO;
import net.geant.autobahn.intradomain.sdh.dao.StmLinkDAO;
import net.geant.autobahn.network.dao.LinkDAO;

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
    
    public net.geant.autobahn.intradomain.common.dao.NodeDAO getNodeDAO();
    
    public IntradomainReservationDAO getIntradomainReservationDAO();
    
    public MplsLinkDAO getMplsLinkDAO();
}
