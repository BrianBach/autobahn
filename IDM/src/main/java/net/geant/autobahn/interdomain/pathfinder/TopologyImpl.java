/**
 * TopologyImpl.java
 * 2006-11-13
 */
package net.geant.autobahn.interdomain.pathfinder;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.idm.TopologyMerge;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Node;
import net.geant.autobahn.network.ProvisioningDomain;
import net.geant.autobahn.network.StateOper;
import net.geant.autobahn.network.dao.LinkDAO;
import net.geant.autobahn.ospf.Ospf;
import net.geant.autobahn.ospf.OspfAsync;
import net.geant.autobahn.ospf.OspfException;
import net.geant.autobahn.ospf.OspfImpl;
import net.geant.autobahn.ospf.lsa.OspfLsa;
import net.geant.autobahn.ospf.lsa.OspfLsaOpaque;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Implements <code>Topology</code> interaface
 * Can be used with or without OSPF API
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public final class TopologyImpl implements Topology, OspfAsync, Closeable {

	private Logger log = Logger.getLogger(this.getClass());
    private IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
    private LinkDAO ldao = daos.getLinkDAO();
    private TopologyMerge topoMerge = new TopologyMerge();
    private Ospf ospf;
    private int lsaType;
    private String ifAddr, areaId;
    private int opaqueType, opaqueId;
    
    public void init(String ospfApiEndpoint, int port, int lsaType, String ifAddr, 
    				String areaId, int opaqueType, int opaqueId) throws 
    				OspfException, IOException {

    	this.lsaType = lsaType;
    	this.ifAddr = ifAddr;
    	this.areaId = areaId;
    	this.opaqueType = opaqueType;
    	this.opaqueId = opaqueId;
    	ospf = new OspfImpl(this);
    	ospf.connect(ospfApiEndpoint, port);
    	ospf.registerEvents(0xFFFFFFFF);
    	try {
    		ospf.registerOpaqueType(lsaType, opaqueType);
    	} catch (OspfException e) {
    		log.error("Register exception", e);
    	} // already exists, ignore
    	ospf.synchronizeLsdb();
    	// wait a bit to gather opaques 
    	try {
    		Thread.sleep(500);
    	} catch (InterruptedException e) { }
    	log.debug("ospf connected to " + ospfApiEndpoint + ":" + port);
    }    	
    
    public boolean isOspfUsed() {
    	
    	return ospf != null && ospf.isConnected();
    }
    
    /* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public void close() {

		if (isOspfUsed()) {
			try {
				ospf.disconnect();
			} catch (OspfException e) { 
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ospf = null;
		}
		log.debug("ospf closed");
	}

	/* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Quagga#getDomains()
     */
    public List<AdminDomain> getDomains() {
        
        List<Link> links = ldao.getValidLinks();
        Set<AdminDomain> adomains = new HashSet<AdminDomain>();
        
        for (Link l : links) {
            adomains.add(l.getStartPort().getNode().getProvisioningDomain().getAdminDomain());
            adomains.add(l.getEndPort().getNode().getProvisioningDomain().getAdminDomain());
        }
        return new ArrayList<AdminDomain>(adomains);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Quagga#getDomains()
     */
    public List<ProvisioningDomain> getProvDomains() {
        
        List<Link> links = ldao.getValidLinks();
        Set<ProvisioningDomain> pdomains = new HashSet<ProvisioningDomain>();
        
        for (Link l : links) {
            pdomains.add(l.getStartPort().getNode().getProvisioningDomain());
            pdomains.add(l.getEndPort().getNode().getProvisioningDomain());
        }
        return new ArrayList<ProvisioningDomain>(pdomains);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Quagga#getLinks()
     */
    public List<Link> getLinks() {
        
        return ldao.getValidLinks();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Quagga#getNodes()
     */
    public List<Node> getNodes() {
        
        List<Link> links = ldao.getValidLinks();
        Set<Node> nodes = new HashSet<Node>();
        
        for (Link l : links) {
            // Use our custom function addDiscreteNode to make sure that
            // no duplicate nodes are added
            nodes.add(l.getStartPort().getNode());
            nodes.add(l.getEndPort().getNode()); 
        }
        
        return new ArrayList<Node>(nodes);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Topology#insertLink(net.geant.autobahn.pnetwork.Link)
     */
    public boolean insertLink(Link link) {

    	// Save into db
    	HibernateUtil hbm = IdmHibernateUtil.getInstance();
		Transaction t = hbm.beginTransaction();
		Link l = topoMerge.merge(link);
		ldao.update(l);
		t.commit();
		
		hbm.closeSession();
    	
    	if (isOspfUsed()) {
    		try {
    			ospf.originateLink(ifAddr, areaId, lsaType, opaqueType, opaqueId, link);
    			
    			Thread.sleep(1000);
    			
    			opaqueId++;
    		} catch (Exception e) {
    			log.debug("originateLink - " + e.getMessage());
    			return false;
    		}
    	}
        return true;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.pathfinder.interdomain.Topology#removeLink(net.geant.autobahn.pnetwork.Link)
     */
    public boolean removeLink(Link link) {
    	
    	if (isOspfUsed()) {
    		try {
    			ospf.deleteLsa(areaId, lsaType, opaqueType, opaqueId);
    		} catch (Exception e) {
    			log.debug("deleteLsa - " + e.getMessage());
    			return false;
    		}
    	}
        return true;
    }

    // OSPF ASYNC

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#deleteNotify(java.lang.String, java.lang.String, int, net.geant.autobahn.ospf.lsa.OspfLsa)
	 */
	public void deleteNotify(String ifAddr, String areaId, int selfOrigin,
			OspfLsa lsa) {

		if (lsa instanceof OspfLsaOpaque) {
			OspfLsaOpaque opaque = (OspfLsaOpaque)lsa;
			if (opaque.getData() instanceof Link) {
				Link link = (Link)opaque.getData();
				
				HibernateUtil hbm = IdmHibernateUtil.getInstance();
				Transaction t = hbm.beginTransaction();

				Link oldLink = ldao.get(link.getBodID());
				if(oldLink != null) {
					oldLink.setOperationalState(StateOper.DOWN);
				} else {
					ldao.update(link);
				}
				log.info("Link " + link.getBodID() + " removed from Ospf");
				
				t.commit();
					
				hbm.closeSession();
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#delInterface(java.lang.String)
	 */
	public void delInterface(String ifAddr) {
		// ignore this event
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#ismChange(java.lang.String, java.lang.String, int)
	 */
	public void ismChange(String ifAddr, String areaId, int status) {
		// ignore this event
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#newInterface(java.lang.String, java.lang.String)
	 */
	public void newInterface(String ifAddr, String areaId) {
		// ignore this event
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#nsmChange(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public void nsmChange(String ifAddr, String nbrAddr, String routerId,
			int status) {

		// ignore this event
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#readyNotify(int, int, java.lang.String)
	 */
	public void readyNotify(int lsaType, int opaqueType, String addr) {

		// ignore this event		
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.ospf.OspfAsync#updateNotify(java.lang.String, java.lang.String, int, net.geant.autobahn.ospf.lsa.OspfLsa)
	 */
	public void updateNotify(String ifAddr, String areaId, int selfOrigin,
			OspfLsa lsa) {

		if (lsa instanceof OspfLsaOpaque) {
			
			OspfLsaOpaque opaque = (OspfLsaOpaque)lsa;
			if (opaque.getData() instanceof Link) {
				Link link = (Link)opaque.getData();
				
				link.setOperationalState(StateOper.UP);

				// Check if exists
				Link old = ldao.get(link.getBodID());
				if(old != null && old.getOperationalState().equals(StateOper.UP))
					return;
				
				HibernateUtil hbm = IdmHibernateUtil.getInstance();
				hbm.currentSession().clear();
				
				log.info("Link " + link.getBodID() + " acquired from Ospf");
				
				Transaction t = hbm.beginTransaction();
				ldao.update(link);
				t.commit();
				
				hbm.closeSession();
			}
		}
	}
}
