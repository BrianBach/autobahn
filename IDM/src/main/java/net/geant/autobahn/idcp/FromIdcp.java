/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.constraints.ConstraintsNames;
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
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.User;

import org.apache.log4j.Logger;

/**
 * Used by OSCARSImpl when receiving reservation coming from an idcp domain
 * @author PCSS
 */
public final class FromIdcp {
	
	private static final Logger log = Logger.getLogger(FromIdcp.class);
	
	public static final int TIME_SCALE = 1000; // any time-related fields multiply by this value
	public static final int BANDWIDTH_SCALE = 1000000; // idcp uses Mbps so we must multiply by this value
	private static Map<Integer, String> states = new HashMap<Integer, String>();
	
	static {
		// states mapping between autobahn and idcp
		states.put(1, "ACCEPTED");
		states.put(2, "INCREATE");
		states.put(3, "INCREATE");
		states.put(4, "INCREATE");
		states.put(5, "SCHEDULED");
		states.put(6, "INTEARDOWN");
		states.put(9, "INSETUP");
		states.put(10, "ACTIVE");
		states.put(11, "INTEARDOWN");
		states.put(21, "FINISHED");
		states.put(22, "CANCELLED");
		states.put(23, "FAILED");
	}
		
	private static ResDetails toResDetails(Reservation res) {
		
		ResDetails rd = new ResDetails();
		rd.setBandwidth((int) res.getCapacity() * BANDWIDTH_SCALE);
		rd.setDescription(res.getDescription());
		rd.setGlobalReservationId(res.getBodID()); 
		rd.setLogin("not set");
		rd.setStartTime(res.getStartTime().getTimeInMillis() * TIME_SCALE);
		rd.setEndTime(res.getEndTime().getTimeInMillis() * TIME_SCALE);
		rd.setStatus(states.get(res.getState()));
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(IdcpClient.PATH_MODE_AUTOMATIC);
		pinfo.setPathType(IdcpClient.PATH_TYPE_LOOSE);
		Layer2Info layer2 = new Layer2Info();
		layer2.setSrcEndpoint(res.getStartPort().getBodID());
		layer2.setDestEndpoint(res.getEndPort().getBodID());
		VlanTag vlanTag = new VlanTag();
		vlanTag.setValue("any");
		vlanTag.setTagged(true);
		layer2.setSrcVtag(vlanTag);
		layer2.setDestVtag(vlanTag);
		pinfo.setLayer2Info(layer2);
		pinfo.setMplsInfo(null);
		pinfo.setLayer3Info(null);		
		rd.setPathInfo(pinfo);
		return rd;
	}
	
	public static HomeDomainReservation create(String resId, String desc, String src, String dst, long startTime, long endTime, int bandwidth, String vlan) {
		
		 IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
		       
	     User u = new User();
	     u.setName("idcp user"); // could be obtained from certificate
	     u.setEmail("idcp@idcp.org");
	        
	     AdminDomain ad = new AdminDomain();
	     ad.setClientDomain(false);
	     ad.setBodID("idcp domain");
	     u.setHomeDomain(ad);

	     Link slink = daos.getLinkDAO().getByBodID(src);
	     Link dlink = daos.getLinkDAO().getByBodID(dst);
	     String domainID = AccessPoint.getInstance().getLocalDomain();
	     Port sport = domainID.equals(slink.getEndDomainID()) ? slink.getStartPort() : slink.getEndPort();
	     Port dport = dlink.getStartPort().isClientPort() ? dlink.getStartPort() : dlink.getEndPort();
         log.info("IDCP request: " + sport + " to " + dport + ", domain ID " + domainID);
	       
         Calendar start = Calendar.getInstance();
         start.setTimeInMillis(startTime * TIME_SCALE);
         Calendar end = Calendar.getInstance();
         end.setTimeInMillis(endTime * TIME_SCALE);
         
         List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>();
         HomeDomainReservation resv = new HomeDomainReservation(sport, dport, start, end, 0);
         resv.setCapacity(bandwidth * BANDWIDTH_SCALE);
         resv.setBodID(resId);
         resv.setDescription(desc);
         	        
	     reservations.add(resv);
	     
         if(vlan != null && !vlan.equals("any")) {
        	 RangeConstraint rcon = new RangeConstraint(vlan);
	         PathConstraints pcon = new PathConstraints();
	         pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
	            
	         resv.setUserIngressConstraints(pcon);
	         resv.setUserEgressConstraints(pcon);
	     }
   
         Service srv = AccessPoint.getInstance().createService("Service from IDCP", u, reservations);
         AccessPoint.getInstance().executeService(srv);

         return (HomeDomainReservation)srv.getReservations().get(0);
	}
	
	public static void cancel(String resId) throws IdcpException {
		
		try {
			AccessPoint.getInstance().cancelReservation(resId);
		} catch (NoSuchReservationException e) {
			log.debug("FromIdcp.cancel - no such reservation " + resId);
			throw new IdcpException(e.getMessage());
		}
	}
	
	public static ResDetails query(String resId) { 
		
		Reservation res = AccessPoint.getInstance().getAutobahnReservation(resId);
		return res != null ? toResDetails(res) : null;
	}
	
	public static List<ResDetails> list() {
		
		List<Service> services = null;
    	
		try {
			IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
			services = daos.getServiceDAO().getActiveServices();
		} catch (Exception e) {
		    log.error("Hibernate error: " + e.getMessage());
		}
    	List<Reservation> reservations = new ArrayList<Reservation>();
		for (Service srv : services) {
			for (Reservation r : srv.getReservations()) {
				if (r != null)
					reservations.add(r);						
			}
		}
		IdmHibernateUtil.getInstance().closeSession();
		
		List<ResDetails> list = new ArrayList<ResDetails>();
		for (Reservation r : reservations)
			list.add(toResDetails(r));
		
		return list;
	}
	
	public static List<Link> getTopology() {
		
		return AccessPoint.getInstance().getTopology();
	}
}
