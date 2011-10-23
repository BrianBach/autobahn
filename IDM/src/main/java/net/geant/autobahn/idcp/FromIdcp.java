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
import net.geant.autobahn.reservation.TimeRange;
import net.geant.autobahn.reservation.User;

import org.apache.log4j.Logger;

/**
 * Called from OSCARSImpl 
 * 
 * @author PCSS
 */
public final class FromIdcp {

	private static final Logger log = Logger.getLogger(FromIdcp.class);

	private static Map<Integer, String> states = new HashMap<Integer, String>();
	
	private static ResDetails toResDetails(Reservation res) {

		ResDetails rd = new ResDetails();
		rd.setBandwidth((int) res.getCapacity() * Idcp.BANDWIDTH_SCALE);
		rd.setDescription(res.getDescription());
		rd.setGlobalReservationId(res.getBodID());
		rd.setLogin("not set");
		rd.setStartTime(res.getStartTime().getTimeInMillis() * Idcp.TIME_SCALE);
		rd.setEndTime(res.getEndTime().getTimeInMillis() * Idcp.TIME_SCALE);
		rd.setStatus(states.get(res.getState()));
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(Idcp.PATH_MODE_AUTOMATIC);
		pinfo.setPathType(Idcp.PATH_TYPE_LOOSE);
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
	
	private static String toAutobahnPort(String idcpPort) {
		
		final String prefix = "urn:ogf:network";
		if (!idcpPort.startsWith(prefix))
			return null;
		
		String[] split = idcpPort.split("\\:");
		if (split.length != 7)
			return null;
		
		split = split[6].split("\\=");
		return split[1];
	}

	public static void create(String resId, String desc, String src, String dst, long startTime, long endTime,
			int bandwidth, String vlan, PathInfo pathInfo) throws IdcpException {

		src = toAutobahnPort(src);
		if (src == null)
			throw new IdcpException("srcPort cannot be null");
		
		dst = toAutobahnPort(dst);
		if (dst == null)
			throw new IdcpException("dstPort cannot be null");
		
		IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
		User u = new User();
		u.setName("idcp");
		u.setEmail("idcp@geant.net");
		AdminDomain ad = new AdminDomain();
		ad.setClientDomain(false);
		ad.setBodID("idcp domain");
		u.setHomeDomain(ad);

		final Link startLink = daos.getLinkDAO().getByBodID(src);
		if (startLink == null) {
			log.info("start link " + src + " not found");
			throw new IdcpException("start link " + src + " not found");
		}

		final Link endLink = daos.getLinkDAO().getByBodID(dst);
		if (endLink == null) {
			log.info("end link " + dst + " not found");
			throw new IdcpException("end link " + dst + " not found");
		}
		
		String domainID = AccessPoint.getInstance().getLocalDomain();
		Port sport = domainID.equals(startLink.getEndDomainID()) ? startLink.getStartPort() : startLink.getEndPort();
		Port dport = endLink.getStartPort().isClientPort() ? endLink.getStartPort()	: endLink.getEndPort();
		log.info("IDCP request: " + sport + " to " + dport + ", domain ID "	+ domainID);

		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(startTime * Idcp.TIME_SCALE);
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(endTime * Idcp.TIME_SCALE);
		
		HomeDomainReservation resv = new HomeDomainReservation(sport, dport, start, end, 0);
		resv.setBodID(resId);
		resv.setDescription(desc);
		resv.setCapacity(bandwidth * Idcp.BANDWIDTH_SCALE);

		if (vlan != null) {
			RangeConstraint rcon = new RangeConstraint(vlan);
			PathConstraints pcon = new PathConstraints();
			pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
			resv.setUserIngressConstraints(pcon);
			resv.setUserEgressConstraints(pcon);
		}
				
		resv.addStatusListener(new IdcpReservationNotifier(resv, pathInfo));
		List<HomeDomainReservation> reservations = new ArrayList<HomeDomainReservation>();
		reservations.add(resv);
		Service srv = AccessPoint.getInstance().createService("idcp service with reservation " + resId, u, reservations);
		AccessPoint.getInstance().executeService(srv);
	}

	public static void cancel(String resId) throws IdcpException {

		try {
			AccessPoint.getInstance().cancelReservation(resId);
		} catch (NoSuchReservationException e) {
			log.debug("FromIdcp.cancel - no such reservation " + resId);
			System.out.println("FromIdcp.cancel - no such reservation " + resId);
			throw new IdcpException(e.getMessage());
		}
	}

	public static void modify(String resId, long start, long end) throws IdcpException {
		
		try {
			
			Calendar startTime = Calendar.getInstance();
			startTime.setTimeInMillis(start * Idcp.TIME_SCALE);
			Calendar endTime = Calendar.getInstance();
			endTime.setTimeInMillis(end * Idcp.TIME_SCALE);
			TimeRange time = new TimeRange(startTime, endTime);
			AccessPoint.getInstance().modifyReservation(resId, time);
		} catch (Exception e) {
			log.debug("FromIdcp.modify exception - " + e.getMessage());
			System.out.println("FromIdcp.modify exception - " + e.getMessage());
			throw new IdcpException(e.getMessage());
		}
	}
	
	public static ResDetails query(String resId) {

		Reservation res = AccessPoint.getInstance().getAutobahnReservation(
				resId);
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
	
	public static List<Link> getTopology(boolean withIdcp) {

		List<Link> links = AccessPoint.getInstance().getTopology();

		if (withIdcp)
			return links;
		else {

			List<Link> abLinks = new ArrayList<Link>();
			for (Link l : links) {
				if (!l.isDummyIdcpLink() && !l.isIdcpLink() && !l.getBodID().contains("dummyLink"))
					abLinks.add(l);
			}
			return abLinks;
		}
	}
}
