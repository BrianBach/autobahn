/**
 * 
 */
package net.geant.autobahn.idcp;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.dao.IdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.network.AdminDomain;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.User;

import net.geant.autobahn.idcp.notify.OscarsNotifyClient;

import org.apache.log4j.Logger;

/**
 * OSCARS to AutoBAHN client facade
 * @author Michal
 */

public class Oscars2Autobahn implements ReservationStatusListener {

    private Map<String, String> failures = new HashMap<String, String>();
    private Map<String, Reservation> cache = new HashMap<String, Reservation>();
    
    private static final Logger log = Logger.getLogger(Oscars2Autobahn.class);
    
    public void cancelReservation(String resID) throws IOException {

        try {
            AccessPoint.getInstance().cancelReservation(resID);
        } catch (NoSuchReservationException e) {
            e.printStackTrace();
        }
    }

    public HomeDomainReservation createReservation(String resID, int capacity, Calendar start, Calendar end, String src, String dest, String vlans)
            throws IOException {
        
        IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
        
        User u = new User();
        u.setName("Internet2");
        u.setEmail("ma@nie.da");
        
        AdminDomain ad = new AdminDomain();
        ad.setClientDomain(false);
        ad.setBodID("http://www.es.net/oscars/");
        
        u.setHomeDomain(ad);

        log.info("Sport: " + src + ", dport: " + dest);

        Link slink = daos.getLinkDAO().getByBodID(src);
        Link dlink = daos.getLinkDAO().getByBodID(dest);
        log.debug("slink = " + slink);
        String domainID = AccessPoint.getInstance().getLocalDomain();
        log.debug("DOMAIN ID = " + domainID);
        Port sport = domainID.equals(slink.getEndDomainID()) ? 
                slink.getStartPort() : slink.getEndPort();
        Port dport = dlink.getStartPort().isClientPort() ? dlink.getStartPort()
                : dlink.getEndPort();
            
        log.info("IDCP request: " + sport + " to " + dport);
        
        
        List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>();

        Calendar now = Calendar.getInstance();
        
        // not process now - check if: and start > now
        if (start.compareTo(now) < 0) {
            log.error("wrong reservation time - startTime: " + start.getTime() + " < currentTime: " + now.getTime());
            return null;
        }

        // check if start < end
        if (start.compareTo(end) >= 0) { 
            log.error("wrong reservation time - startTime: " + start.getTime() + " >= endTime: " + end.getTime());
            return null;
        }
        
        // TODO: Check if 0 value for priority is proper
        HomeDomainReservation resv = new HomeDomainReservation(sport, dport,
                start, end, 0);
        
        log.info("StartTime: " + start.getTime());
        log.info("EndTime: " + end.getTime());
        
        resv.setCapacity(capacity * 1000000);
        resv.setBodID(resID);
        
        reservations.add(resv);
            
        // Vlans
        if(vlans != null) {
            RangeConstraint rcon = new RangeConstraint(vlans);
            PathConstraints pcon = new PathConstraints();
            pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
            DomainConstraints dcon = new DomainConstraints();
            dcon.addPathConstraints(pcon);
            
            resv.setUserConstraints(dcon);
        }
        
        resv.addStatusListener(this);
        cache.put(resv.getBodID(), resv);
        
        Service srv = AccessPoint.getInstance().createService(
                    "Service from IDCP", u, reservations);

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

        String failure = failures.get(res1.getBodID());
        
        if(failure != null) {
            log.error("Failure: " + failure);
            res1.setDescription(failure);
        }
            
        log.debug("Returning");
        
        return res1;
    }

    public List<Link> getTopology() throws IOException {
    	
        log.debug("Everything OK!!!");

        return AccessPoint.getInstance().getTopology();
    }

    public List<Reservation> listReservations() throws IOException {
        
    	List<Service> services = null;
    	
		try {
			IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
			services = daos.getServiceDAO().getActiveServices();
		} catch (Exception e) {
		    log.error("Hibernate error: " + e.getMessage());
		}
    	
    	List<Reservation> listResv = new ArrayList<Reservation>();
		
    	try {
			for (Service serv : services) {
				for (Reservation resv : serv.getReservations()) {
					if(resv != null){
						listResv.add(resv);						
					}						
				}
			}
		} catch (Exception e) {
		    log.error("No reservation found: " + e.getMessage());
		}
		
		IdmHibernateUtil.getInstance().closeSession();
    	return listResv;
    }

    public boolean modifyReservation(Reservation resInfo)
            throws IOException {
        
        throw new UnsupportedOperationException();
    }

    public Reservation queryReservation(String resID) throws IOException {
        
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
						return resv;					
				}
			}
		} catch (Exception e) {
		    log.error("Query Reservation Error: " + e.getMessage());
		}
		
		IdmHibernateUtil.getInstance().closeSession();
    	return null;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationActive(java.lang.String)
     */
    public void reservationActive(String reservationId) {
        notifyIDC(reservationId, "PATH_SETUP_COMPLETED");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationCancelled(java.lang.String)
     */
    public void reservationCancelled(String reservationId) {
        notifyIDC(reservationId, "RESERVATION_CANCEL_COMPLETED");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.ReservationStatusListener#reservationFinished(java.lang.String)
     */
    public void reservationFinished(String reservationId) {
        notifyIDC(reservationId, "PATH_TEARDOWN_COMPLETED");
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
        
        notifyIDC(reservationId, "RESERVATION_CREATE_FAILED");
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
        
        notifyIDC(reservationId, "RESERVATION_CREATE_COMPLETED");
    }

    public void reservationModified(String reservationId, boolean success) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * This method sends event notifications to an IDCP instance,
     * every time reservation state changes
     * @param reservationID reservation ID
     * @param eventName event name sent to an IDCP instance
     */
    private void notifyIDC(String reservationID, String eventName) {
    	
    	OscarsNotifyClient oscarsNotify = new OscarsNotifyClient();
    	try {
    		oscarsNotify.Notify(cache.get(reservationID), eventName);
    	} catch (RemoteException e) { }
    }
}