package net.geant.autobahn.idcp;


import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import net.geant.autobahn.idcp.GetTopologyContent;
import net.geant.autobahn.idcp.GetTopologyResponseContent;
import net.geant.autobahn.idcp.GlobalReservationId;
import net.geant.autobahn.idcp.Layer2Info;
import net.geant.autobahn.idcp.OscarsConverter;
import net.geant.autobahn.idcp.PathInfo;
import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.idcp.VlanTag;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;

import net.geant.autobahn.idcp.OSCARS;
import net.geant.autobahn.idcp.OSCARS_Service;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent;


/**
 * Web Service client for interaction with oscars services
 * This moreless covers the OSCARS interface
 * AAAFault and BSSFault messages are represented as RemoteException
 * @author Michal
 */
public class OscarsClient {

	private Logger log = Logger.getLogger(this.getClass()); 
								
	// scheduling is done automatically, query to find out the reservation's state 
	private final String PATH_AUTOMATIC = "timer-automatic";
	
	// scheduling must be followed by CreatePath, user is notified about state changes
	private final String PATH_MANUAL = "signal-xml";
	
	// relies on an idcp domain to figure out the path
	private final String PATH_LOOSE = "loose";
	
	// allows to specify hops for the path
	private final String PATH_STRICT = "strict";
	
	// before sending a reservation, the capacity by must be divided to adjust to OSCARS bandwidth metric(Mbps)
	private final int BANDWIDTH_SCALE = 1000000;
	
	// idcp implementations use seconds
	private final int TIME_SCALE = 1000;
	
	private final OSCARS oscars;
	private String endpoint;
	private String prefferedPathMode = PATH_AUTOMATIC;

	/**
	 * Creates new instance of OSCARS client
	 * @param endpoint the address where OSCARS service is listening
	 */
	public OscarsClient(String endpoint) {
		
		this.endpoint = endpoint;
		oscars = new OSCARS_Service(endpoint).getOSCARS();
	}
	
	/**
	 * Schedules a reservation within an idcp domain
	 * @param resv the object that was passed as argument
	 * @param src the source port
	 * @param dst the destination port
	 * @param vlan
	 * @return
	 * @throws RemoteException 
	 */
	public void scheduleReservation(Reservation resv, String src, String dst, String vlan) throws RemoteException {
		
		Holder<String> grid = new Holder<String>(resv.getBodID());
		long startTime = resv.getStartTime().getTimeInMillis() / TIME_SCALE;
		long endTime = resv.getEndTime().getTimeInMillis() / TIME_SCALE;
		int bandwidth = ((int) (resv.getCapacity() / BANDWIDTH_SCALE));
		if (bandwidth <= 0) { // happens if requested bandwidth is lower than BANDWIDTH_SCALE
			log.info("Requested bandwidth for " + grid.value + " within an idcp domain should be greater than zero, assigning 1");
			bandwidth = 1;
		}

		bandwidth = 50;
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(PATH_AUTOMATIC);
		pinfo.setPathType(PATH_LOOSE);
		Layer2Info layer2 = new Layer2Info();
		layer2.setSrcEndpoint(src);
		layer2.setDestEndpoint(dst);
		VlanTag vlanTag = new VlanTag();
		vlanTag.setValue(vlan);
		vlanTag.setTagged(true);
		layer2.setSrcVtag(vlanTag);
		layer2.setDestVtag(vlanTag);
		pinfo.setLayer2Info(layer2);
		
		// intentionally no layer3 or mpls info
		pinfo.setMplsInfo(null);
		pinfo.setLayer3Info(null);
		Holder<PathInfo> pathInfo = new Holder<PathInfo>(pinfo);

		Holder<String> token = new Holder<String>();
		Holder<String> status = new Holder<String>();
		
		try {
			oscars.createReservation(grid, startTime, endTime, bandwidth, resv.getDescription(), pathInfo, token, status);
			// after create ACCEPTED must be returned
			if (!status.value.equals("ACCEPTED"))
				throw new RemoteException("reservation " + resv.getBodID() + " returned in wrong state - " + status.value);
			
			
			if (prefferedPathMode.equals(PATH_AUTOMATIC)) { // poll for reservation states
				
				new ResStatePolling(endpoint, resv.getBodID());
			}
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * Initiates cancellation of reservation with resId
	 * @param resId
	 * @return
	 * @throws RemoteException
	 */
	public String cancelReservation(String resId) throws RemoteException { 
		
		GlobalReservationId grid = new GlobalReservationId();
		grid.setGri(resId);
		
		try {
			return oscars.cancelReservation(grid);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * Queries for a reservation by id, returned reservation is a newly created object for informational purposes only
	 * @param resId
	 * @return
	 * @throws RemoteException
	 */
	public ResDetails queryReservation(String resId) throws RemoteException {
		
		Holder<String> grid = new Holder<String>();
		Holder<String> login = new Holder<String>();
		Holder<String> status = new Holder<String>();
		Holder<Long> startTime = new Holder<Long>();
		Holder<Long> endTime = new Holder<Long>();
		Holder<Long> createTime = new Holder<Long>();
		Holder<Integer> bandwidth = new Holder<Integer>();
		Holder<String> description = new Holder<String>();
		Holder<PathInfo> pathInfo = new Holder<PathInfo>();
		
		try {
			
			oscars.queryReservation(resId, grid, login, status, startTime, endTime, createTime, bandwidth, description, pathInfo);
			
			ResDetails res = new ResDetails();
			res.setGlobalReservationId(grid.value);
			res.setLogin(login.value);
			res.setStatus(status.value);
			res.setBandwidth(bandwidth.value);
			res.setDescription(description.value);
			res.setPathInfo(pathInfo.value);
			return res;
			
		} catch (Exception e){ 
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * Modifies an existing reservation
	 * @param resv
	 * @return
	 * @throws RemoteException
	 */
	public ResDetails modifyReservation(Reservation resv) throws RemoteException {

		long startTime = resv.getStartTime().getTimeInMillis() / TIME_SCALE;
		long endTime = resv.getEndTime().getTimeInMillis() / TIME_SCALE;
		int bandwidth = ((int) (resv.getCapacity() / BANDWIDTH_SCALE));
		if (bandwidth <= 0) { // happens if requested bandwidth is lower than BANDWIDTH_SCALE
			log.info("Requested bandwidth for " + resv.getBodID() + " within an idcp domain should be greater than zero, assigning 1");
			bandwidth = 1;
		}
		
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(PATH_AUTOMATIC);

		final String sport = "urn:ogf:network:domain=dev.es.net:node=star-cr1:port=xe-1/1/0:link=*";
		final String eport = "urn:ogf:network:domain=dev.es.net:node=bnl-mr3:port=xe-7/2/0:link=*";
		Layer2Info l2 = new Layer2Info();
		l2.setSrcEndpoint(sport);
		l2.setDestEndpoint(eport);
		VlanTag vlan = new VlanTag();
		//TODO: set the proper vlan identifier here
		//vlan.setValue(String.valueOf(resv.getUserVlanId()));
		vlan.setValue("any");
		vlan.setTagged(true);
		l2.setSrcVtag(vlan);
		l2.setDestVtag(vlan);
		pinfo.setLayer2Info(l2);
		
		// intentionally no layer3 nor mpls info
		pinfo.setMplsInfo(null);
		pinfo.setLayer3Info(null);
		
		try {
			
			ResDetails res = oscars.modifyReservation(resv.getBodID(), startTime, endTime, bandwidth, resv.getDescription(), pinfo);
			return res;
		} catch (Exception e) { 
			throw new RemoteException(e.getMessage());
		}
	}
	
	public void createPath(String resId) throws RemoteException {

		Holder<String> grid = new Holder<String>(resId);
		Holder<String> status = new Holder<String>();
		
		try {
			
			oscars.createPath(resId, grid, status);
						
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	public void list() throws RemoteException {
		
		ListRequest request = new ListRequest();
		
		
		
	}
	
	/**
	 * Returns a list of idcp link identifiers witout performing conversion to autobahn format
	 * @return a list of idcp link identifiers
	 * @throws RemoteException
	 */
	public List<String> getIdcpTopology() throws RemoteException {
		
		GetTopologyContent request = new GetTopologyContent();
		request.setTopologyType("all");

		try {
			
			GetTopologyResponseContent response = oscars.getNetworkTopology(request);
			if (response.getTopology().getDomain() == null)
				return null;
			
			List<String> links = new ArrayList<String>();
			for (CtrlPlaneDomainContent domain : response.getTopology().getDomain()) {
				if (domain.getNode() == null)  
					continue;
				for (CtrlPlaneNodeContent node : domain.getNode()) {
					if (node.getPort() == null)
						continue;
					for (CtrlPlanePortContent port : node.getPort()) {
						if (port.getLink() == null)
							continue;
						for (CtrlPlaneLinkContent link : port.getLink()) {
						
							links.add(link.getId());
						}
					}
				}
			}
			return links;
			
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
			
	/**
	 * Reads an idcp topology and converts it into autobahn representation
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public List<Link> getTopology() throws RemoteException {
		
		GetTopologyContent request = new GetTopologyContent();
		request.setTopologyType("all");

		try {
			GetTopologyResponseContent response = oscars.getNetworkTopology(request);
			return OscarsConverter.getGeantTopology(response.getTopology().getDomain());
		} catch (Exception e) { 
			throw new RemoteException(e.getMessage());
		}
	}
}
