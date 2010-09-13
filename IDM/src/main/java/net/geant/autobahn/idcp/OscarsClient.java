package net.geant.autobahn.idcp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.ws.Holder;

import net.geant.autobahn.idcp.AAAFaultMessage;
import net.geant.autobahn.idcp.BSSFaultMessage;
import net.geant.autobahn.idcp.GetTopologyContent;
import net.geant.autobahn.idcp.GetTopologyResponseContent;
import net.geant.autobahn.idcp.GlobalReservationId;
import net.geant.autobahn.idcp.Layer2Info;
import net.geant.autobahn.idcp.ListReply;
import net.geant.autobahn.idcp.ListRequest;
import net.geant.autobahn.idcp.OscarsConverter;
import net.geant.autobahn.idcp.PathInfo;
import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.idcp.VlanTag;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;

import net.geant.autobahn.idcp.OSCARS;
import net.geant.autobahn.idcp.OSCARS_Service;
import net.geant.autobahn.idm.AccessPoint;

import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent;
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

	public OscarsClient(String endpoint) {
	    OSCARS_Service service;
        service = new OSCARS_Service(endpoint);
        rc = service.getOSCARS();
	}
	

	public List<Reservation> getReservationList() throws AAAFaultMessage, BSSFaultMessage {

		ListRequest lreq = new ListRequest();
		ListReply lrep = rc.listReservations(lreq);

        List<Reservation> resinfo = new ArrayList<Reservation>();
		if (lrep != null) {
			List<ResDetails> res = lrep.getResDetails();
			for (ResDetails rd : res) {
				Reservation ri = new Reservation();
				ri.setBodID(rd.getGlobalReservationId());
				ri.setDescription(rd.getDescription());
				ri.setCapacity(rd.getBandwidth());
				// TODO
				resinfo.add(ri);
			}
		}
        return resinfo;
	}

	@SuppressWarnings("deprecation")
	public List<Link> getTopology() throws Exception {
		log.debug("getTopology.begin");

        GetTopologyContent content = new GetTopologyContent();
        content.setTopologyType("all");
        
        GetTopologyResponseContent resp = rc.getNetworkTopology(content);
                
		List<CtrlPlaneDomainContent> domains = resp.getTopology().getDomain();

		System.out.println("Domains: " + domains.size());
		
		CtrlPlaneDomainContent[] ctrlDomains = new CtrlPlaneDomainContent[domains.size()];

		for (int i=0; i < domains.size(); i++) {
			ctrlDomains[i] = domains.get(i);
		}
		
		//CtrlPlaneDomainContent[] test = (CtrlPlaneDomainContent[]) domains.toArray();
		List<Link> links = OscarsConverter.getGeantTopology(ctrlDomains);
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
	public Reservation scheduleReservation(Reservation reservation,
			String src, String dest, String vlans) throws RemoteException {

		final String resID = reservation.getBodID();
		final int bandwidthScale = 1000000;

		long startTime = reservation.getStartTime().getTimeInMillis() / 1000;
		long endTime = reservation.getEndTime().getTimeInMillis() / 1000;
		String description = reservation.getDescription();
		
		Holder<String> globalReservationId = new Holder<String>(resID);
		// Bandwidth in Mbps
		int bandwidth = ((int) (reservation.getCapacity() / bandwidthScale));
		if (bandwidth <= 0) { // happens if requested bandwidth is lower than bandwidthScale
			log.info("Requested bandwidth for " + resID + " within an idcp domain should be greater than zero, assigning 1");
			bandwidth = 1;
		}
		
		// Path
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(SIG_AUTO);
		
		Layer2Info l2 = new Layer2Info();
				
		//l2.setSrcEndpoint(src);
		//l2.setDestEndpoint(dest);
		l2.setSrcEndpoint(AccessPoint.getInstance().getProperty(src));
		l2.setDestEndpoint(AccessPoint.getInstance().getProperty(dest));
		
		VlanTag vlan = new VlanTag();
		vlan.setValue(vlans);
		vlan.setTagged(true);
		l2.setSrcVtag(vlan);
		l2.setDestVtag(vlan);
		
		pinfo.setLayer2Info(l2);

		Holder <PathInfo> pathInfo = new Holder<PathInfo>(pinfo);

		Holder<String> token = new Holder<String>();
		Holder<String> status = new Holder<String>();
		
		try {
			
			rc.createReservation(globalReservationId, startTime, endTime, bandwidth, description, pathInfo, token, status);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("IDCP reservation schedule error: " + e.getMessage());
			//reservation.setDescription(e.getMessage());
			reservation.setDescription("error");
			return reservation;
		}
		
		/*
		if (ST_PENDING.equals(status)) {
			// Read and return vlan
			
			Integer sel_vlan = Integer.valueOf(pathInfo.value
					.getLayer2Info().getSrcVtag().getValue());
			
			result.setCalculatedConstraints("" + sel_vlan);
		}*/
		// should we trigger something??
		
		return reservation;
	}

	public String cancelReservation(String resID) throws RemoteException {

		GlobalReservationId gid = new GlobalReservationId();
		gid.setGri(resID);
		
		String response = null;

		try {
			response = rc.cancelReservation(gid);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e.getCause());
		}
		
		return response;
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
	
	public ResDetails modifyReservation(Reservation reservation, String src, String dest, String vlans) throws RemoteException {

		final String resID = reservation.getBodID();
		
		long startTime = reservation.getStartTime().getTimeInMillis() / 1000;
		long endTime = reservation.getEndTime().getTimeInMillis() / 1000;
		String description = reservation.getDescription();
		
		String globalReservationId = resID;
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

        ResDetails resD = null;
        try {
            resD = rc.modifyReservation(globalReservationId, startTime, endTime, bandwidth, description, pinfo);
        
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e.getCause());
        }
        /*
        if (ST_PENDING.equals(status)) {
            // Read and return vlan
            Integer sel_vlan = Integer.valueOf(pathInfo.value
                    .getLayer2Info().getSrcVtag().getValue());

            result.setCalculatedConstraints("" + sel_vlan);
        } */
        //ResDetails temp = null;
        
        return resD;
    }
	
	public Reservation queryReservation(String resID) throws RemoteException {

		GlobalReservationId gid = new GlobalReservationId();
		gid.setGri(resID);
		
		Holder<String> globalReservationId = new Holder<String>();
		Holder<String> login = new Holder<String>();
		Holder<String> status = new Holder<String>();
		Holder<Long> startTime = new Holder<Long>();
		Holder<Long> endTime = new Holder<Long>();
		Holder<Long> createTime = new Holder<Long>();
		Holder<Integer> bandwidth = new Holder<Integer>();
		Holder<String> description = new Holder<String>();
		Holder<PathInfo> pathInfo = new Holder<PathInfo>();

		try {
			rc.queryReservation(resID, globalReservationId, login, status, startTime, endTime, createTime, bandwidth, description, pathInfo);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e.getCause());
		}
		
		// Time
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(startTime.value * 1000);
        
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(endTime.value * 1000);
        
        // Ports
        String dest = pathInfo.value.getLayer2Info().getDestEndpoint();        
        String src = pathInfo.value.getLayer2Info().getSrcEndpoint();
        
        // Vlans
        String vlans = pathInfo.value.getLayer2Info().getSrcVtag().getValue();
        
        //CtrlPlaneHopContent[] hops = (CtrlPlaneHopContent[]) pathInfo.value.getPath().getHop().toArray();
        CtrlPlaneHopContent[] hops = new CtrlPlaneHopContent[pathInfo.value.getPath().getHop().size()];
		
        for (int i=0; i < pathInfo.value.getPath().getHop().size(); i++) {
			hops[i] = pathInfo.value.getPath().getHop().get(i);
		}
		
        System.out.println("Hops received: " + hops.length);
        
        //Original implementation
        /*
        CtrlPlaneHopContent srcHop = hops[hops.length - 2];
        String src = srcHop.getLinkIdRef();
        CtrlPlaneLinkContent srcLink = hops[hops.length - 2].getLink();
        String src = srcLink.getId();
        */
        
        String bodId = globalReservationId.value;
        
        Reservation resInfo = new Reservation();
        
        src = src.substring(src.indexOf(":link=") + 6);
        dest = dest.substring(dest.indexOf(":link=") + 6);
        
		resInfo.setBodID(bodId);
		resInfo.setDescription(description.value);
		resInfo.setCapacity(bandwidth.value);
		resInfo.setStartTime(start);
        resInfo.setEndTime(end);
        // TODO: Properly handle start/end ports and vlans
        //resInfo.setStartPort(src);
        //resInfo.setEndPort(dest);
        //resInfo.setUserVlans(vlans);		
		
		return resInfo;
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
