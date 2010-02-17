/**
 * OSCARSSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
package net.geant.autobahn.oscars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.es.oscars.oscars.CancelReservationResponse;
import net.es.oscars.oscars.CreatePathContent;
import net.es.oscars.oscars.CreatePathResponseContent;
import net.es.oscars.oscars.CreateReply;
import net.es.oscars.oscars.CreateReservationResponse;
import net.es.oscars.oscars.ForwardReply;
import net.es.oscars.oscars.ForwardResponse;
import net.es.oscars.oscars.GetNetworkTopologyResponse;
import net.es.oscars.oscars.GetTopologyResponseContent;
import net.es.oscars.oscars.GlobalReservationId;
import net.es.oscars.oscars.Layer2Info;
import net.es.oscars.oscars.ListReply;
import net.es.oscars.oscars.ListReservationsResponse;
import net.es.oscars.oscars.ModifyResContent;
import net.es.oscars.oscars.ModifyResReply;
import net.es.oscars.oscars.ModifyReservationResponse;
import net.es.oscars.oscars.PathInfo;
import net.es.oscars.oscars.QueryReservationResponse;
import net.es.oscars.oscars.ResCreateContent;
import net.es.oscars.oscars.ResDetails;
import net.es.oscars.oscars.TeardownPathContent;
import net.es.oscars.oscars.TeardownPathResponseContent;
import net.es.oscars.oscars.VlanTag;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.proxy.ProxyClient;
import net.geant.autobahn.proxy.ReservationInfo;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneTopologyContent;

/**
 * OSCARSSkeleton java skeleton for the axisService
 */
public class OSCARSSkeleton {

	private Logger log = Logger.getLogger(this.getClass());

	private ReservationInfo makeReservation(ResCreateContent content)
			throws IOException {

		int capacity = content.getBandwidth();

		// Time
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(content.getStartTime() * 1000);

		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(content.getEndTime() * 1000);

		// Ports
		String dest = content.getPathInfo().getLayer2Info().getDestEndpoint();

		// Vlans
		String vlans = content.getPathInfo().getLayer2Info().getSrcVtag()
				.getString();

		CtrlPlaneHopContent[] hops = content.getPathInfo().getPath().getHop();
		System.out.println("Hops received: " + hops.length);

		CtrlPlaneHopContent srcHop = hops[hops.length - 2];
		String src = srcHop.getLinkIdRef();
		String resID = content.getGlobalReservationId();

		ReservationInfo resInfo = new ReservationInfo();

		src = src.substring(src.indexOf(":link=") + 6);
		dest = dest.substring(dest.indexOf(":link=") + 6);

		resInfo.setBodID(resID);
		resInfo.setCapacity(capacity);
		resInfo.setStartTime(start);
		resInfo.setEndTime(end);
		resInfo.setStartPort(src);
		resInfo.setEndPort(dest);
		resInfo.setUserVlans(vlans);

		// TODO vlans, port, bodID?

		ProxyClient proxy = new ProxyClient();
		ReservationInfo resp = null;
		resp = proxy.createReservation(resInfo);
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param cancelReservation
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.CancelReservationResponse cancelReservation(
			net.es.oscars.oscars.CancelReservation cancelReservation)
			throws BSSFaultMessage, AAAFaultMessage {

		log.debug("cancelReservation.begin");
		String resID = cancelReservation.getCancelReservation().getGri();

		try {
			ProxyClient proxy = new ProxyClient();
			proxy.cancelReservation(resID);
		} catch (IOException e) {
			log.debug("cancelReservation exc - " + e.getMessage());
			throw new BSSFaultMessage(e.getMessage());
		}

		CancelReservationResponse resp = new CancelReservationResponse();
		resp.setCancelReservationResponse(resID);
		log.debug("cancelReservation.end");
		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createReservation
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.CreateReservationResponse createReservation(
			net.es.oscars.oscars.CreateReservation createReservation)
			throws BSSFaultMessage, AAAFaultMessage {

		ResCreateContent content = createReservation.getCreateReservation();

		ReservationInfo resInfo = null;
		try {
			resInfo = makeReservation(content);
		} catch (Exception e) {
			System.out.println("Exception makeReservation! " + e.getMessage());
			throw new BSSFaultMessage(e.getMessage(), e);
		}

		if (resInfo == null)
			return null;

		CreateReservationResponse resp = new CreateReservationResponse();
		CreateReply content2 = new CreateReply();
		resp.setCreateReservationResponse(content2);

		System.out.println("Sending resp to client");

		content2.setGlobalReservationId(resInfo.getBodID());
		// content2.setGlobalReservationId(content.getGlobalReservationId());
		content2.setStatus("ACCEPTED");

		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode("domain");
		content2.setPathInfo(pinfo);

		pinfo.setPath(content.getPathInfo().getPath());

		Layer2Info l2info = new Layer2Info();
		pinfo.setLayer2Info(l2info);

		VlanTag vlanTag = new VlanTag();
		// vlanTag.setString(resInfo.getCalculatedConstraints());
		vlanTag.setString(content.getPathInfo().getLayer2Info().getSrcVtag()
				.getString());
		vlanTag.setTagged(true);

		l2info.setSrcVtag(vlanTag);
		l2info.setDestVtag(vlanTag);

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param queryReservation
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.QueryReservationResponse queryReservation(
			net.es.oscars.oscars.QueryReservation queryReservation)
			throws BSSFaultMessage, AAAFaultMessage {

		log.debug("queryReservation.begin");
		String resId = queryReservation.getQueryReservation().getGri();

		ResDetails res = new ResDetails();
		res.setGlobalReservationId("reservation not found");
		res.setLogin("autobahn");
		res.setStatus("ok");
		res.setGlobalReservationId("aaa");

		try {
			// find reservation
			ProxyClient proxy = new ProxyClient();
			ReservationInfo resInfo = proxy.queryReservation(resId);

			if (resInfo != null) {
				log.debug("found res: " + resId);
				res.setBandwidth((int) resInfo.getCapacity());
				res.setDescription(resInfo.getDescription());
				res.setGlobalReservationId(resInfo.getBodID());
				res.setLogin("DRAGON");
				res.setStatus(String.valueOf(resInfo.getState()));
				res.setStartTime(resInfo.getStartTime().getTimeInMillis());
				res.setEndTime(resInfo.getEndTime().getTimeInMillis());
			}

		} catch (Exception e) {
			log.info("query - " + e.getMessage());
		}

		QueryReservationResponse resp = new QueryReservationResponse();
		resp.setQueryReservationResponse(res);
		log.debug("queryReservation.end");

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param refreshPath
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.RefreshPathResponse refreshPath(
			net.es.oscars.oscars.RefreshPath refreshPath)
			throws BSSFaultMessage, AAAFaultMessage {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#refreshPath");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param teardownPath
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.TeardownPathResponse teardownPath(
			net.es.oscars.oscars.TeardownPath teardownPath)
			throws BSSFaultMessage, AAAFaultMessage {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#teardownPath");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param createPath
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.CreatePathResponse createPath(
			net.es.oscars.oscars.CreatePath createPath) throws BSSFaultMessage,
			AAAFaultMessage {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#createPath");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getNetworkTopology
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.GetNetworkTopologyResponse getNetworkTopology(
			net.es.oscars.oscars.GetNetworkTopology getNetworkTopology)
			throws BSSFaultMessage, AAAFaultMessage {

		log.debug("getNetworkTopology.begin");

		List<Link> links = new ArrayList<Link>();

		try {
			ProxyClient proxy = new ProxyClient();
			links = proxy.getTopology();
		} catch (IOException e) {
			throw new BSSFaultMessage(e.getMessage());
		}

		GetNetworkTopologyResponse resp = new GetNetworkTopologyResponse();
		GetTopologyResponseContent cont = new GetTopologyResponseContent();
		CtrlPlaneTopologyContent ctrlTopology = new CtrlPlaneTopologyContent();
		ctrlTopology.setId("GEANT2");
		ctrlTopology.setIdcId("GEANT2");
		CtrlPlaneDomainSignatureContent[] ctrlSigns = new CtrlPlaneDomainSignatureContent[1];
		ctrlSigns[0] = new CtrlPlaneDomainSignatureContent();
		ctrlSigns[0].setDomainId("NOT SET");
		ctrlTopology.setDomainSignature(ctrlSigns);
		CtrlPlaneDomainContent[] ctrlDomains = OscarsConverter
				.getOscarsTopology(links);
		ctrlTopology.setDomain(ctrlDomains);
		cont.setTopology(ctrlTopology);
		resp.setGetNetworkTopologyResponse(cont);

		log.debug("getNetworkTopolgoy.finish");

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param modifyReservation
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.ModifyReservationResponse modifyReservation(
			net.es.oscars.oscars.ModifyReservation modifyReservation)
			throws BSSFaultMessage, AAAFaultMessage {

		ModifyResContent mod = modifyReservation.getModifyReservation();
		ReservationInfo resInfo = new ReservationInfo();
		resInfo.setBodID(mod.getGlobalReservationId());
		resInfo.setCapacity(mod.getBandwidth());
		resInfo.setDescription(mod.getDescription());
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(mod.getStartTime());
		resInfo.setStartTime(start);
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(mod.getEndTime());
		resInfo.setEndTime(end);

		try {
			ProxyClient proxy = new ProxyClient();
			boolean succ = proxy.modifyReservation(resInfo);
			if (succ == false) {
				throw new BSSFaultMessage("could not modify reservation");
			}
		} catch (IOException e) {
			throw new BSSFaultMessage(e.getMessage());
		}

		ModifyReservationResponse resp = new ModifyReservationResponse();
		ModifyResReply reply = new ModifyResReply();
		resp.setModifyReservationResponse(reply);

		ResDetails rd = new ResDetails();
		reply.setReservation(rd);
		rd.setBandwidth(mod.getBandwidth());
		rd.setDescription(mod.getDescription());
		rd.setEndTime(mod.getEndTime());
		rd.setStartTime(mod.getStartTime());
		rd.setGlobalReservationId(mod.getGlobalReservationId());
		rd.setLogin("no login");
		rd.setStatus("no status");

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param notify
	 */

	public void Notify(org.oasis_open.docs.wsn.b_2.Notify notify) {
		// TODO : fill this with the necessary business logic

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param forward
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.ForwardResponse forward(
			net.es.oscars.oscars.Forward forward) throws BSSFaultMessage,
			AAAFaultMessage {

		ResCreateContent createReservation = forward.getPayload()
				.getCreateReservation();
		GlobalReservationId cancel = forward.getPayload()
				.getCancelReservation();
		CreatePathContent createPath = forward.getPayload().getCreatePath();
		TeardownPathContent teardownPath = forward.getPayload()
				.getTeardownPath();

		ForwardResponse fresp = new ForwardResponse();
		ForwardReply reply = new ForwardReply();
		fresp.setForwardResponse(reply);
		reply.setContentType("all");

		if (createReservation != null) {
			ReservationInfo res = null;

			try {
				res = makeReservation(createReservation);
			} catch (IOException e) {
				throw new BSSFaultMessage(e.getMessage(), e);
			}

			String failure = res.getDescription();
			if (res != null && failure == null) {
				System.out.println("Okay, generating response...");

				CreateReservationResponse resp = new CreateReservationResponse();
				CreateReply content2 = new CreateReply();
				resp.setCreateReservationResponse(content2);

				content2.setGlobalReservationId(res.getBodID());
				content2.setStatus(OscarsClient.ST_PENDING);

				PathInfo pinfo = new PathInfo();
				pinfo.setPathSetupMode("domain");
				content2.setPathInfo(pinfo);

				pinfo.setPath(createReservation.getPathInfo().getPath());

				Layer2Info l2info = new Layer2Info();
				pinfo.setLayer2Info(l2info);

				VlanTag reqVlan = createReservation.getPathInfo()
						.getLayer2Info().getSrcVtag();

				VlanTag vlanTag = new VlanTag();
				// vlanTag.setString(res.getCalculatedConstraints());
				vlanTag.setString(reqVlan.getString());
				vlanTag.setTagged(true);

				l2info.setSrcVtag(vlanTag);
				l2info.setDestVtag(vlanTag);

				l2info.setSrcEndpoint(createReservation.getPathInfo()
						.getLayer2Info().getSrcEndpoint());
				l2info.setDestEndpoint(createReservation.getPathInfo()
						.getLayer2Info().getDestEndpoint());

				reply.setCreateReservation(content2);
			} else {
				System.out.println("Failure: " + failure);
				throw new BSSFaultMessage(failure);
			}

		}

		String cancelRes = null;
		if (cancel != null && cancel.getGri() != null
				&& !"".equals(cancel.getGri())) {
			cancelRes = cancel.getGri();
		}

		if (cancelRes != null) {
			try {
				ProxyClient proxy = new ProxyClient();
				proxy.cancelReservation(cancelRes);
			} catch (IOException e) {
				log.debug("forward.ProxyException: ", e);
			}

			reply.setCancelReservation(cancelRes);
		}

		if (createPath != null) {
			String resID = createPath.getGlobalReservationId();

			System.out.println("CreatePath received: " + resID);

			// Just ignore, create empty reply
			CreatePathResponseContent par2 = new CreatePathResponseContent();
			par2.setGlobalReservationId(resID);
			par2.setStatus(OscarsClient.ST_ACTIVE);

			reply.setCreatePath(par2);
		}

		if (teardownPath != null) {
			String resID = teardownPath.getGlobalReservationId();

			log.info("TeardownPath received: " + resID);

			// Just ignore, create empty reply
			TeardownPathResponseContent par2 = new TeardownPathResponseContent();
			par2.setGlobalReservationId(resID);
			par2.setStatus(OscarsClient.ST_FINISHED);

			reply.setTeardownPath(par2);
		}

		return fresp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param listReservations
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.ListReservationsResponse listReservations(
			net.es.oscars.oscars.ListReservations listReservations)
			throws BSSFaultMessage, AAAFaultMessage {

		log.debug("listReservations.begin");

		List<ReservationInfo> reservations = new ArrayList<ReservationInfo>();

		try {
			ProxyClient proxy = new ProxyClient();
			reservations = proxy.listReservations();
		} catch (IOException e) {
			throw new BSSFaultMessage(e.getMessage());
		}

		ResDetails[] resDetails = new ResDetails[reservations.size()];
		int index = 0;

		for (ReservationInfo ri : reservations) {

			ResDetails rd = new ResDetails();
			rd.setBandwidth((int) ri.getCapacity());
			rd.setDescription(ri.getDescription());
			rd.setGlobalReservationId(ri.getBodID());
			rd.setLogin("no login");
			rd.setStatus(String.valueOf(ri.getState()));
			rd.setStartTime(ri.getStartTime().getTimeInMillis());
			rd.setEndTime(ri.getEndTime().getTimeInMillis());
			resDetails[index++] = rd;
		}

		ListReply list = new ListReply();
		list.setResDetails(resDetails);
		ListReservationsResponse resp = new ListReservationsResponse();
		resp.setListReservationsResponse(list);

		log.debug("listReservations.end");

		return resp;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param initiateTopologyPull
	 * @throws BSSFaultMessage
	 *             :
	 * @throws AAAFaultMessage
	 *             :
	 */

	public net.es.oscars.oscars.InitiateTopologyPullResponse initiateTopologyPull(
			net.es.oscars.oscars.InitiateTopologyPull initiateTopologyPull)
			throws BSSFaultMessage, AAAFaultMessage {
		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#initiateTopologyPull");
	}

}
