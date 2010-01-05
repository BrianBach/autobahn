package net.geant2.jra3.oscars;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import net.es.oscars.oscars.CancelReservation;
import net.es.oscars.oscars.CancelReservationResponse;
import net.es.oscars.oscars.CreatePath;
import net.es.oscars.oscars.CreatePathContent;
import net.es.oscars.oscars.CreatePathResponse;
import net.es.oscars.oscars.CreateReply;
import net.es.oscars.oscars.CreateReservation;
import net.es.oscars.oscars.CreateReservationResponse;
import net.es.oscars.oscars.GetNetworkTopology;
import net.es.oscars.oscars.GetNetworkTopologyResponse;
import net.es.oscars.oscars.GetTopologyContent;
import net.es.oscars.oscars.GlobalReservationId;
import net.es.oscars.oscars.Layer2Info;
import net.es.oscars.oscars.ListRequest;
import net.es.oscars.oscars.ListReservations;
import net.es.oscars.oscars.ListReservationsResponse;
import net.es.oscars.oscars.PathInfo;
import net.es.oscars.oscars.ResCreateContent;
import net.es.oscars.oscars.ResDetails;
import net.es.oscars.oscars.VlanTag;
import net.geant2.jra3.main.ProxyServlet;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.proxy.ReservationInfo;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.MessageType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
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
	private final static String repo = ProxyServlet.getAppPath() + "/etc/security";
	private final static String localRepo = "C:/Repo";
	private OSCARSStub stub;

	public OscarsClient(String endPoint) throws AxisFault {
		
		this(endPoint, repo);
	}
	
	public OscarsClient(String endPoint, String repoPath) throws AxisFault {

		ConfigurationContext context = ConfigurationContextFactory.createConfigurationContextFromFileSystem(repoPath, null);
		stub = new OSCARSStub(context, endPoint);
		ServiceClient service = stub._getServiceClient();
		Options opts = service.getOptions();
		opts.setTimeOutInMilliSeconds(120000);
		service.setOptions(opts);
		stub._setServiceClient(service);
	}

	public List<ReservationInfo> getReservationList() throws RemoteException {

		ListReservations lres = new ListReservations();
		ListRequest lreq = new ListRequest();
		lres.setListReservations(lreq);
		ListReservationsResponse resp = null;
		try {
			stub.listReservations(lres);
		} catch (Exception e) {
			System.out.println("getReservationList: " + e.getMessage());
		}
		if (resp != null) {

			ResDetails[] res = resp.getListReservationsResponse()
					.getResDetails();
			List<ReservationInfo> resinfo = new ArrayList<ReservationInfo>();
			for (ResDetails rd : res) {

				ReservationInfo ri = new ReservationInfo();
				ri.setBodID(rd.getGlobalReservationId());
				ri.setDescription(rd.getDescription());
				ri.setCapacity(rd.getBandwidth());
				// TODO
				resinfo.add(ri);
			}
			return resinfo;
		}
		return null;
	}

	public List<Link> getTopology() throws Exception {
		log.debug("getTopology.begin");
		GetNetworkTopology param = new GetNetworkTopology();
		GetTopologyContent content = new GetTopologyContent();
		content.setTopologyType("all");
		param.setGetNetworkTopology(content);
		GetNetworkTopologyResponse response = stub.getNetworkTopology(param);
		CtrlPlaneDomainContent[] domains = response
				.getGetNetworkTopologyResponse().getTopology().getDomain();

		System.out.println("Domains: " + domains.length);

		List<Link> links = OscarsConverter.getGeantTopology(domains);
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

		ResCreateContent content = new ResCreateContent();

		content
				.setStartTime(reservation.getStartTime().getTimeInMillis() / 1000);
		content.setEndTime(reservation.getEndTime().getTimeInMillis() / 1000);
		content.setDescription(reservation.getDescription());
		content.setGlobalReservationId(resID);
		// Bandwidth in Mbps
		content.setBandwidth((int) (reservation.getCapacity() / 1000000));

		// Path
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(SIG_AUTO);

		Layer2Info l2 = new Layer2Info();
		l2.setSrcEndpoint(src);
		l2.setDestEndpoint(dest);

		VlanTag vlan = new VlanTag();
		vlan.setString(vlans);
		vlan.setTagged(true);
		l2.setSrcVtag(vlan);
		l2.setDestVtag(vlan);

		pinfo.setLayer2Info(l2);

		content.setPathInfo(pinfo);

		CreateReservation param = new CreateReservation();
		param.setCreateReservation(content);

		CreateReservationResponse response = null;

		ReservationInfo result = new ReservationInfo();
		result.setBodID(reservation.getBodID());

		try {
			response = stub.createReservation(param);
		} catch (Exception e) {
			System.out.println("Error when schedule oscars: " + e.getMessage());
			result.setDescription(e.getMessage());

			return result;
		}

		CreateReply resp = response.getCreateReservationResponse();

		if (ST_PENDING.equals(resp.getStatus())) {
			// Read and return vlan
			Integer sel_vlan = Integer.valueOf(resp.getPathInfo()
					.getLayer2Info().getSrcVtag().getString());

			result.setCalculatedConstraints("" + sel_vlan);
		}

		return result;
	}

	public void cancelReservation(String resID) throws RemoteException {
		CancelReservation param = new CancelReservation();

		GlobalReservationId gid = new GlobalReservationId();
		gid.setGri(resID);

		param.setCancelReservation(gid);

		try {
			CancelReservationResponse resp = stub.cancelReservation(param);
			System.out.println("Cancel resp from OSCARS: "
					+ resp.getCancelReservationResponse());
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	public String createPath(String resID) throws RemoteException {
		CreatePath cp = new CreatePath();
		CreatePathContent content = new CreatePathContent();

		content.setGlobalReservationId(resID);

		cp.setCreatePath(content);

		CreatePathResponse resp;
		try {
			resp = stub.createPath(cp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e.getCause());
		}

		return resp.getCreatePathResponse().getStatus();
	}

	public void Notify() throws RemoteException {

		Notify notify = new Notify();
		NotificationMessageHolderType message = new NotificationMessageHolderType();

		MessageType messageType = new MessageType();

		message.setMessage(messageType);

		notify.addNotificationMessage(message);

		try {

			stub.Notify(notify);

		} catch (Exception e) {

			System.out.println("notify exception " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {

		String addr = "http://localhost:8080/autobahn-proxy/services/OSCARS";
		
		OscarsClient oscars = new OscarsClient(addr, localRepo);
		oscars.getTopology();

		System.out.println("program exit...");
	}
}
