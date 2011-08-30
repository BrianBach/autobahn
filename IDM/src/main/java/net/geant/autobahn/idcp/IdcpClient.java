/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent;

import net.geant.autobahn.network.Link;

/**
 * Simplified idcp client, contains functionality required by autobahn
 * Can be used as a stand-alone client
 * All parameters are expected to follow idcp conventions
 * 
 * @author PCSS
 */
public class IdcpClient {
	
	public static final String PATH_TYPE_LOOSE = "loose"; // have idcp-side figure out proper path
	public static final String PATH_TYPE_STRICT = "strict"; // allows specifying hops in the path, not supported by this client
	
	public static final String PATH_MODE_AUTOMATIC = "timer-automatic"; // poll for the status
	public static final String PATH_MODE_MANUAL = ""; // have to use notifications for the status
	
	private final String url;
	private final OSCARS idcp;
	
	public IdcpClient(String url) {
		
		this.url = url;
		idcp = new OSCARS_Service(url).getOSCARS();
	}
	
	/**
	 * Schedules new reservation
	 * @param resId
	 * @param desc
	 * @param src
	 * @param dst
	 * @param startTime
	 * @param endTime
	 * @param bandwidth
	 * @param vlan
	 * @param pathMode
	 * @throws IdcpException
	 */
	public void schedule(String resId, String desc, String src, String dst, 
				long startTime, long endTime, int bandwidth, String vlan, String pathMode) throws IdcpException {
		
		Holder<String> gri = new Holder<String>(resId);
		PathInfo pinfo = new PathInfo();
		pinfo.setPathSetupMode(pathMode);
		pinfo.setPathType(PATH_TYPE_LOOSE);
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
			idcp.createReservation(gri, startTime, endTime, bandwidth, desc, pathInfo, token, status);
			
			// no matter what, ACCEPTED must be returned
			if (!status.value.equals("ACCEPTED"))
				throw new IdcpException("ACCEPTED must be returned");
			
			// if path automatic, start polling
			if (pathMode.equals(PATH_MODE_AUTOMATIC))
				new IdcpStatePolling(url, resId);
			
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Cancels reservation identified as resId, otherwise throws Exception
	 * @param resId
	 * @throws IdcpException
	 */
	public void cancel(String resId) throws IdcpException {
		
		GlobalReservationId gri = new GlobalReservationId();
		gri.setGri(resId);
		
		try {
			idcp.cancelReservation(gri);
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Asks for reservation details identified by resId
	 * @param resId
	 * @return
	 * @throws IdcpException
	 */
	public ResDetails query(String resId) throws IdcpException {
		
		Holder<String> gri = new Holder<String>();
		Holder<String> login = new Holder<String>();
		Holder<String> status = new Holder<String>();
		Holder<Long> startTime = new Holder<Long>();
		Holder<Long> endTime = new Holder<Long>();
		Holder<Long> createTime = new Holder<Long>();
		Holder<Integer> bandwidth = new Holder<Integer>();
		Holder<String> description = new Holder<String>();
		Holder<PathInfo> pathInfo = new Holder<PathInfo>();
		
		try {
			idcp.queryReservation(resId, gri, login, status, startTime, endTime, createTime, bandwidth, description, pathInfo);
			
			ResDetails res = new ResDetails();
			res.setGlobalReservationId(gri.value);
			res.setLogin(login.value);
			res.setStatus(status.value);
			res.setBandwidth(bandwidth.value);
			res.setDescription(description.value);
			res.setPathInfo(pathInfo.value);
			return res;
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Needed for xml serialization 
	 * @return
	 * @throws IdcpException
	 */
	public GetTopologyResponseContent getRawTopology() throws IdcpException {
		
		GetTopologyContent request = new GetTopologyContent();
		request.setTopologyType("all");

		try {
			GetTopologyResponseContent response = idcp.getNetworkTopology(request);
			return response;
		} catch (Exception e) { 
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Returns a list of links suitable for autobahn representation
	 * @return
	 * @throws IdcpException
	 */
	public List<Link> getTopology() throws IdcpException {
		
		GetTopologyContent request = new GetTopologyContent();
		request.setTopologyType("all");

		try {
			GetTopologyResponseContent response = idcp.getNetworkTopology(request);
			return OscarsConverter.getGeantTopology(response.getTopology().getDomain());
		} catch (Exception e) { 
			throw new IdcpException(e.getMessage());
		}
	}
	
	/**
	 * Returns a list of links in original representation
	 * @return
	 * @throws IdcpException
	 */
	public List<String> getIdcpTopology() throws IdcpException {
		
		GetTopologyContent request = new GetTopologyContent();
		request.setTopologyType("all");

		try {
			GetTopologyResponseContent response = idcp.getNetworkTopology(request);
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
			throw new IdcpException(e.getMessage());
		}
	}
}
