package net.geant.autobahn.oscars;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import net.es.oscars.oscars.AAAFaultMessage;
import net.es.oscars.oscars.BSSFaultMessage;
import net.es.oscars.oscars.GetTopologyContent;
import net.es.oscars.oscars.GetTopologyResponseContent;
import net.es.oscars.oscars.GlobalReservationId;
import net.es.oscars.oscars.Layer2Info;
import net.es.oscars.oscars.ListReply;
import net.es.oscars.oscars.ListRequest;
import net.es.oscars.oscars.OscarsConverter;
import net.es.oscars.oscars.PathInfo;
import net.es.oscars.oscars.ResDetails;
import net.es.oscars.oscars.VlanTag;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.proxy.ReservationInfo;

import net.es.oscars.oscars.OSCARS;
import net.es.oscars.oscars.OSCARS_Service;

import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;

/**
 * @author Michal
 * 
 */
public class OscarsClient {

	private Logger log = Logger.getLogger(this.getClass()); // Path signalling
															// mode
	public static final String SIG_DOMAIN = "domain";
	public static final String SIG_USER = "user-xml";
	public static final String SIG_AUTO = "timer-automatic"; // Reservation
																// status
	public static final String ST_PENDING = "PENDING";
	public static final String ST_ACTIVE = "ACTIVE";
	public static final String ST_FINISHED = "FINISHED";
	public static final String ST_CANCELLED = "CANCELLED";
	public static final String ST_FAILED = "FAILED";
	public static final String ST_PENDINGCANCEL = "PENDINGCANCEL";
	public static final String ST_PRECANCEL = "PRECANCEL";

	private OSCARS rc = null;

	public OscarsClient() {
        // The OSCARS location will be retrieved from WSDL
        OSCARS_Service service = new OSCARS_Service();
        rc = service.getOSCARS();
	}
	

	public List<ReservationInfo> getReservationList() throws AAAFaultMessage, BSSFaultMessage {

		ListRequest lreq = new ListRequest();
		ListReply lrep = rc.listReservations(lreq);
		
        List<ReservationInfo> resinfo = new ArrayList<ReservationInfo>();
		if (lrep != null) {
			List<ResDetails> res = lrep.getResDetails();
			for (ResDetails rd : res) {
				ReservationInfo ri = new ReservationInfo();
				ri.setBodID(rd.getGlobalReservationId());
				ri.setDescription(rd.getDescription());
				ri.setCapacity(rd.getBandwidth());
				// TODO
				resinfo.add(ri);
			}
		}
        return resinfo;
	}

	public List<Link> getTopology() throws Exception {
		log.debug("getTopology.begin");

        GetTopologyContent content = new GetTopologyContent();
        content.setTopologyType("all");
        GetTopologyResponseContent resp = rc.getNetworkTopology(content);
        
		List<CtrlPlaneDomainContent> domains = resp.getTopology().getDomain();

		System.out.println("Domains: " + domains.size());

		
		List<Link> links = OscarsConverter.getGeantTopology((CtrlPlaneDomainContent[]) domains.toArray());
		log.debug("getTopology.end");
		return links;
	}

	/**
	 * Schedules reservation in DRAGON. Returns selected VLAN tag.
	 * 
	 * @param reservation
	 * @param src
	 * @param dest
	 * @param vlans
	 * @return Integer - number of VLAN tag selected for the reservation
	 * @throws RemoteException
	 */
	public ReservationInfo scheduleReservation(ReservationInfo reservation,
			String src, String dest, String vlans) throws RemoteException {

		final String resID = reservation.getBodID();

		long startTime = reservation.getStartTime().getTimeInMillis() / 1000;
		long endTime = reservation.getEndTime().getTimeInMillis() / 1000;
		String description = reservation.getDescription();
		Holder<String> globalReservationId = new Holder<String>(resID);
		// Bandwidth in Mbps
		int bandwidth = ((int) (reservation.getCapacity() / 1000000));

		// Path
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(SIG_AUTO);

		Layer2Info l2 = new Layer2Info();
		l2.setSrcEndpoint(src);
		l2.setDestEndpoint(dest);

		VlanTag vlan = new VlanTag();
		vlan.setValue(vlans);
		vlan.setTagged(true);
		l2.setSrcVtag(vlan);
		l2.setDestVtag(vlan);

		pinfo.setLayer2Info(l2);

		Holder <PathInfo> pathInfo = new Holder<PathInfo>(pinfo);

		ReservationInfo result = new ReservationInfo();
		result.setBodID(reservation.getBodID());

		Holder<String> token = new Holder<String>();
		Holder<String> status = new Holder<String>();
		try {
			rc.createReservation(globalReservationId, startTime, endTime, bandwidth, description, pathInfo, token, status);
		} catch (Exception e) {
			System.out.println("Error when schedule oscars: " + e.getMessage());
			result.setDescription(e.getMessage());

			return result;
		}
		
		if (ST_PENDING.equals(status)) {
			// Read and return vlan
			Integer sel_vlan = Integer.valueOf(pathInfo.value
					.getLayer2Info().getSrcVtag().getValue());

			result.setCalculatedConstraints("" + sel_vlan);
		}

		return result;
	}

	public void cancelReservation(String resID) throws RemoteException {

		GlobalReservationId gid = new GlobalReservationId();
		gid.setGri(resID);

		try {
			rc.cancelReservation(gid);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	public String createPath(String resID) throws RemoteException {
	    Holder<String> globalReservationId = new Holder<String>();
	    Holder<String> status = new Holder<String>();
		try {
			rc.createPath(resID, globalReservationId, status);
	        return status.value;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	public void Notify() throws RemoteException {

		Notify notify = new Notify();
		// TODO: Fill the notify message with something useful??
		try {

			rc.notify(notify);

		} catch (Exception e) {

			System.out.println("notify exception " + e.getMessage());
		}
	}
}
