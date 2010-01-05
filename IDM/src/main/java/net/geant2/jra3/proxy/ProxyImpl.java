/**
 * 
 */
package net.geant2.jra3.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import net.geant2.jra3.constraints.ConstraintsNames;
import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.constraints.RangeConstraint;
import net.geant2.jra3.dao.IdmDAOFactory;
import net.geant2.jra3.dao.hibernate.HibernateIdmDAOFactory;
import net.geant2.jra3.idm.AccessPoint;
import net.geant2.jra3.interdomain.NoSuchReservationException;
import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Port;
import net.geant2.jra3.reservation.HomeDomainReservation;
import net.geant2.jra3.reservation.Reservation;
import net.geant2.jra3.reservation.ReservationStatusListener;
import net.geant2.jra3.reservation.Service;
import net.geant2.jra3.reservation.User;

/**
 * @author Michal
 */

@WebService(name = "Proxy", serviceName = "ProxyService",
        portName = "ProxyPort",
        targetNamespace = "http://proxy.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.proxy.Proxy")
public class ProxyImpl implements Proxy, ReservationStatusListener {

	private Map<String, String> failures = new HashMap<String, String>();
	private Map<String, Reservation> cache = new HashMap<String, Reservation>();
	
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws IOException {

		try {
			AccessPoint.getInstance().cancelReservation(resID);
		} catch (NoSuchReservationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ReservationInfo)
	 */
	public ReservationInfo createReservation(ReservationInfo resInfo)
			throws IOException {
		
		IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
		
		User u = new User();
		u.setName("dragon");
		u.setEmail("ma@nie.da");
		
		AdminDomain ad = new AdminDomain();
		ad.setClientDomain(false);
		ad.setBodID("http://dragon.com/");
		
		u.setHomeDomain(ad);

		System.out.println("Sport: " + resInfo.getStartPort() + ", dport: " + resInfo.getEndPort());

		Link slink = daos.getLinkDAO().getByBodID(resInfo.getStartPort());
		Link dlink = daos.getLinkDAO().getByBodID(resInfo.getEndPort());

		String domainID = AccessPoint.getInstance().getLocalDomain();
		
		Port sport = domainID.equals(slink.getEndDomainID()) ? 
				slink.getStartPort() : slink.getEndPort();
		Port dport = dlink.getStartPort().isClientPort() ? dlink.getStartPort()
				: dlink.getEndPort();
				
		System.out.println("DRAGON request: " + sport + " to " + dport);
		
		
		List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>();

		Calendar now = Calendar.getInstance();
		
		// not process now - check if: and start > now
        if (resInfo.getStartTime().compareTo(now) < 0) {
        	System.out.println("wrong reservation time - startTime: " + resInfo.getStartTime().getTime() + " < currentTime: " + now.getTime());
        	return null;
        }

        // check if start < end
        if (resInfo.getStartTime().compareTo(resInfo.getEndTime()) >= 0) { 
        	System.out.println("wrong reservation time - startTime: " + resInfo.getStartTime().getTime() + " >= endTime: " + resInfo.getEndTime().getTime());
        	return null;
        }
		
        HomeDomainReservation resv = new HomeDomainReservation(sport, dport,
        		resInfo.getStartTime(), resInfo.getEndTime(), resInfo.getPriority());
        
        System.out.println("StartTime: " + resInfo.getStartTime().getTime());
        System.out.println("EndTime: " + resInfo.getEndTime().getTime());
        
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

		System.out.println("WAKE UP");
		
		HomeDomainReservation res1 = (HomeDomainReservation) srv.getReservations().get(0);

		resInfo.setState(res1.getState());
		String failure = failures.get(res1.getBodID());
		
		if(failure != null) {
			System.out.println("Failure: " + failure);
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
			
		System.out.println("Returning");
		
		return resInfo;
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#getTopology()
	 */
	public List<Link> getTopology() throws IOException {

		return AccessPoint.getInstance().getTopology();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#listReservations()
	 */
	public List<ReservationInfo> listReservations() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#modifyReservation(net.geant2.jra3.proxy.ReservationInfo)
	 */
	public boolean modifyReservation(ReservationInfo resInfo)
			throws IOException {
		
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.proxy.Proxy#queryReservation(java.lang.String)
	 */
	public ReservationInfo queryReservation(String resID) throws IOException {
		
		Reservation res = AccessPoint.getInstance().getReservation(resID);
		return Autobahn2OscarsConverter.convertReservation(res);
	}

	/**
	 * 
	 */
	public void Notify(ReservationInfo resInfo) throws IOException {
		// Ignore this event
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.ReservationStatusListener#reservationActive(java.lang.String)
	 */
	public void reservationActive(String reservationId) {
		notifyIDC(reservationId);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.ReservationStatusListener#reservationCancelled(java.lang.String)
	 */
	public void reservationCancelled(String reservationId) {
		notifyIDC(reservationId);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.ReservationStatusListener#reservationFinished(java.lang.String)
	 */
	public void reservationFinished(String reservationId) {
		notifyIDC(reservationId);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.reservation.ReservationStatusListener#reservationProcessingFailed(java.lang.String, java.lang.String)
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
	 * @see net.geant2.jra3.reservation.ReservationStatusListener#reservationScheduled(java.lang.String)
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
/*		try {
			ProxyClient proxy = new ProxyClient();
			Reservation res = cache.get(reservationId);
			proxy.Notify(Autobahn2OscarsConverter.convertReservation(res));
		} catch(IOException e) {
			System.out.println("notify error: " + e.getMessage());
		}
*/	}
}
