/**
 * 
 */
package net.geant.autobahn.proxy;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mortbay.log.Log;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.idm.ServiceScheduler;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.User;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

@WebService(name = "Proxy", serviceName = "ProxyService",
        portName = "ProxyPort",
        targetNamespace = "http://proxy.jra3.geant2.net/", 
        endpointInterface = "net.geant.autobahn.proxy.Proxy")
public class ProxyImpl implements Proxy, ReservationStatusListener {

    private Map<String, String> failures = new HashMap<String, String>();
    private Map<String, Reservation> cache = new HashMap<String, Reservation>();
    
    private static final Logger log = Logger.getLogger(ProxyImpl.class);
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#cancelReservation(java.lang.String)
     */
    public void cancelReservation(String resID) throws IOException {

        try {
            AccessPoint.getInstance().cancelReservation(resID);
        } catch (NoSuchReservationException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#createReservation(net.geant.autobahn.proxy.ReservationInfo)
     */
    public ReservationInfo createReservation(ReservationInfo resInfo)
            throws IOException {
        
        IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
        
        User u = new User();
        u.setName("Internet2");
        u.setEmail("ma@nie.da");
        
        AdminDomain ad = new AdminDomain();
        ad.setClientDomain(false);
        ad.setBodID("http://www.es.net/oscars/");
        
        u.setHomeDomain(ad);

        log.info("Sport: " + resInfo.getStartPort() + ", dport: " + resInfo.getEndPort());

        Link slink = daos.getLinkDAO().getByBodID(resInfo.getStartPort());
        Link dlink = daos.getLinkDAO().getByBodID(resInfo.getEndPort());
        log.debug("slink = " + slink);
        String domainID = AccessPoint.getInstance().getLocalDomain();
        log.debug("DOMAIN ID = " + domainID);
        Port sport = domainID.equals(slink.getEndDomainID()) ? 
                slink.getStartPort() : slink.getEndPort();
        Port dport = dlink.getStartPort().isClientPort() ? dlink.getStartPort()
                : dlink.getEndPort();
            
        log.info("DRAGON request: " + sport + " to " + dport);
        
        
        List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>();

        Calendar now = Calendar.getInstance();
        
        // not process now - check if: and start > now
        if (resInfo.getStartTime().compareTo(now) < 0) {
            log.error("wrong reservation time - startTime: " + resInfo.getStartTime().getTime() + " < currentTime: " + now.getTime());
            return null;
        }

        // check if start < end
        if (resInfo.getStartTime().compareTo(resInfo.getEndTime()) >= 0) { 
            log.error("wrong reservation time - startTime: " + resInfo.getStartTime().getTime() + " >= endTime: " + resInfo.getEndTime().getTime());
            return null;
        }
        
        HomeDomainReservation resv = new HomeDomainReservation(sport, dport,
                resInfo.getStartTime(), resInfo.getEndTime(), resInfo.getPriority());
        
        log.info("StartTime: " + resInfo.getStartTime().getTime());
        log.info("EndTime: " + resInfo.getEndTime().getTime());
        
        resv.setDescription(resInfo.getDescription());
        resv.setCapacity(resInfo.getCapacity() * 1000000);
        resv.setMaxDelay(resInfo.getMaxDelay());
        resv.setResiliency(resInfo.getResiliency());
        resv.setBodID(resInfo.getBodID());
        
        reservations.add(resv);
            
        String vlan = resInfo.getUserVlans();
        
        // Vlans
        if(vlan != null) {
            RangeConstraint rcon = new RangeConstraint(vlan);
            PathConstraints pcon = new PathConstraints();
            pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
            DomainConstraints dcon = new DomainConstraints();
            dcon.addPathConstraints(pcon);
            
            resv.setUserConstraints(dcon);
        }
        
        resv.addStatusListener(this);
        cache.put(resv.getBodID(), resv);
        
        Service srv = AccessPoint.getInstance().createService(
                    "Service from dragon", u, reservations);

        AccessPoint.getInstance().executeService(srv);

        synchronized (resv) {
            try {
                resv.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("WAKE UP");
        
        HomeDomainReservation res1 = (HomeDomainReservation) srv.getReservations().get(0);

        resInfo.setState(res1.getState());
        String failure = failures.get(res1.getBodID());
        
        if(failure != null) {
            log.error("Failure: " + failure);
            resInfo.setDescription(failure);
        } else {
            PathConstraints pcon = res1.getGlobalConstraints()
                    .getDomainConstraints().get(0).getPathConstraints().get(0);
            RangeConstraint rcon = pcon.getRangeConstraint(ConstraintsNames.VLANS);
                    
            if(rcon != null)
                resInfo.setCalculatedConstraints("" + rcon.getFirstValue());
            Link[] links = new Link[res1.getPath().getLinks().size()];
            links = res1.getPath().getLinks().toArray(links);
            
            for(int i = 0; i < links.length; i++) {
                Link l = links[i];
                Link l2 = new Link(l.getKind(), l.getStartPort(), l.getEndPort());
                l2.setBodID(l.getBodID());
                l2.setCapacity(l.getCapacity());
                l2.setAdministrativeState(null);
                l2.setOperationalState(null);
                l2.setType(null);
                links[i] = l2;
            }
            
            resInfo.setPath(links);
        }
            
        log.debug("Returning");
        
        return resInfo;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#getTopology()
     */
    public List<Link> getTopology() throws IOException {
    	
        log.debug("Everything OK!!!");

        return AccessPoint.getInstance().getTopology();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#listReservations()
     */
    public List<ReservationInfo> listReservations() throws IOException {
        
    	List<Service> services = null;
    	
		try {
			IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
			services = daos.getServiceDAO().getActiveServices();
		} catch (Exception e) {
		    log.error("Hibernate error: " + e.getMessage());
		}
    	
    	List<ReservationInfo> listResv = new ArrayList<ReservationInfo>();
		
    	try {
			for (Service serv : services) {
				for (Reservation resv : serv.getReservations()) {
					if(resv != null){
						listResv.add(Autobahn2OscarsConverter.convertReservation(resv));						
					}						
				}
			}
		} catch (Exception e) {
		    log.error("No reservation found: " + e.getMessage());
		}
		
		IdmHibernateUtil.getInstance().closeSession();
    	return listResv;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant.autobahn.proxy.ReservationInfo)
     */
    public boolean modifyReservation(ReservationInfo resInfo)
            throws IOException {
        
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#queryReservation(java.lang.String)
     */
    public ReservationInfo queryReservation(String resID) throws IOException {
        
    	List<Service> services = null;
    	
		try {
			IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
			services = daos.getServiceDAO().getActiveServices();
		} catch (Exception e) {
		    log.error("Hibernate Error: " + e.getMessage());
		}
    	
    	try {
			for (Service serv : services) {
				for (Reservation resv : serv.getReservations()) {
					if (resv.getBodID().equals(resID))
						return Autobahn2OscarsConverter.convertReservation(resv);					
				}
			}
		} catch (Exception e) {
		    log.error("Query Reservation Error: " + e.getMessage());
		}
		
		IdmHibernateUtil.getInstance().closeSession();
    	return null;
    }

    /**
     * 
     */
    public void Notify(ReservationInfo resInfo) throws IOException {
        // Ignore this event
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationActive(java.lang.String)
     */
    public void reservationActive(String reservationId) {
        notifyIDC(reservationId);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationCancelled(java.lang.String)
     */
    public void reservationCancelled(String reservationId) {
        notifyIDC(reservationId);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationFinished(java.lang.String)
     */
    public void reservationFinished(String reservationId) {
        notifyIDC(reservationId);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationProcessingFailed(java.lang.String, java.lang.String)
     */
    public void reservationProcessingFailed(String reservationId, String cause) {

        Reservation resv = cache.get(reservationId);

        if(resv == null)
            return;
        
        synchronized (resv) {
            failures.put(reservationId, cause);
            resv.notify();
        }
        
        notifyIDC(reservationId);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationScheduled(java.lang.String)
     */
    public void reservationScheduled(String reservationId) {

        Reservation resv = cache.get(reservationId);

        if(resv == null)
            return;
        
        synchronized (resv) {
            resv.notify();
        }
        
        notifyIDC(reservationId);
    }

    public void reservationModified(String reservationId, boolean success) {
        // TODO Auto-generated method stub
        
    }
    
    private void notifyIDC(String reservationId) {
/*      try {
            ProxyClient proxy = new ProxyClient();
            Reservation res = cache.get(reservationId);
            proxy.Notify(Autobahn2OscarsConverter.convertReservation(res));
        } catch(IOException e) {
            log.error("notify error: " + e.getMessage());
        }
*/  }
}