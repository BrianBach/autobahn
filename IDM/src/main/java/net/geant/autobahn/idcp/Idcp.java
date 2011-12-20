/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.UUID;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.AutobahnReservation;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;

/**
 * Contains constant values for idcp protocol and utility functions used during the conversion process.
 * 
 * @author PCSS
 */
public class Idcp {
	
	private static final Logger log = Logger.getLogger(Idcp.class);
	
	public static final String PATH_TYPE_LOOSE = "loose"; // have idcp-side figure out proper path
	public static final String PATH_TYPE_STRICT = "strict"; // allows specifying hops in the path, not supported by this client
	
	public static final String PATH_MODE_AUTOMATIC = "timer-automatic"; 
	public static final String PATH_MODE_MANUAL = "signal-xml"; 
	
	public static final String TOPIC_IDC = "idc:IDC";
	public static final String TOPIC_INFO = "idc:INFO";
	public static final String TOPIC_DEBUG = "idc:DEBUG";
	public static final String TOPIC_ERROR = "idc:ERROR";
	
	public static final String TOPIC_DIALECT = "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Full";
	public static final String QUERY_DIALECT = "http://www.w3.org/TR/1999/REC-xpath-19991116";
	
	public static final String EVENT_RESERVATION_CREATE_CONFIRMED = "RESERVATION_CREATE_CONFIRMED";
	public static final String EVENT_RESERVATION_MODIFY_CONFIRMED = "RESERVATION_MODIFY_CONFIRMED";
	public static final String EVENT_RESERVATION_CANCEL_CONFIRMED = "RESERVATION_CANCEL_CONFIRMED";
	public static final String EVENT_RESERVATION_CREATE_COMPLETED = "RESERVATION_CREATE_COMPLETED";
	public static final String EVENT_RESERVATION_MODIFY_COMPLETED = "RESERVATION_MODIFY_COMPLETED";
	public static final String EVENT_RESERVATION_CANCEL_COMPLETED = "RESERVATION_CANCEL_COMPLETED";
	public static final String EVENT_RESERVATION_CREATE_FAILED = "RESERVATION_CREATE_FAILED";
	public static final String EVENT_RESERVATION_MODIFY_FAILED = "RESERVATION_MODIFY_FAILED";
	public static final String EVENT_RESERVATION_CANCEL_FAILED = "RESERVATION_CANCEL_FAILED";
	public static final String EVENT_DOWNSTREAM_PATH_SETUP_CONFIRMED = "DOWNSTREAM_PATH_SETUP_CONFIRMED";
	public static final String EVENT_UPSTREAM_PATH_SETUP_CONFIRMED = "UPSTREAM_PATH_SETUP_CONFIRMED";
	public static final String EVENT_DOWNSTREAM_PATH_TEARDOWN_CONFIRMED = "DOWNSTREAM_PATH_TEARDOWN_CONFIRMED";
	public static final String EVENT_UPSTREAM_PATH_TEARDOWN_CONFIRMED = "UPSTREAM_PATH_TEARDOWN_CONFIRMED";
	public static final String EVENT_PATH_SETUP_COMPLETED = "PATH_SETUP_COMPLETED";
	public static final String EVENT_PATH_TEARDOWN_COMPLETED = "PATH_TEARDOWN_COMPLETED";
	public static final String EVENT_PATH_SETUP_FAILED = "PATH_SETUP_FAILED";
	public static final String EVENT_PATH_REFRESH_FAILED = "PATH_REFRESH_FAILED";
	public static final String EVENT_PATH_TEARDOWN_FAILED = "PATH_TEARDOWN_FAILED";

	public static final int TIME_SCALE = 1000; // divide by this when sending to idcp, otherwise multiply
	public static final int BANDWIDTH_SCALE = 1000000; // divide by this when sending to idcp, otherwise multiply
	
	
	/**
	 * Converts autobahn resId to idcp resId
	 * @param resId
	 * @return
	 */
	public static String toIdcpReservationId(String autobahnResId) { 
		
		String[] split = autobahnResId.split("\\@");
    	if (split[0].contains("."))
    		log.info("domain for " + autobahnResId + " contains postfix, another one will be applied");
    	String domain = split[0] + ".net-";
    	split = split[1].split("\\_");
    	return domain + split[0] + split[2];
	}
	
	/**
	 * Converts idcp resId to autobahn resId
	 * @param idcpResId
	 * @return
	 */
	public static String toAutobahnReservationId(String idcpResId) { 
		
		String[] split = idcpResId.split("\\-");
    	String domain = split[0].replace(".net", "");
    	int length = split[1].length();
    	String resNum = String.valueOf(split[1].charAt(length - 1));
    	String num = split[1].substring(0, length - 1);
    	return domain + "@" + num + "_res_" + resNum;
	}
	
	/**
	 * Converts autobahn link (end port) to idcp string identifier
	 * @param link
	 * @return
	 */
	public static String autobahnToIdcpLink(Link link) {
		
		Port end = link.getEndPort();
		return "urn:ogf:network:domain=" + end.getAdminDomainID() + ":node=" + end.getNode().getBodID() + 
			   ":port=" + end.getBodID() + ":link=" + link.getBodID(); 
	}
	
	/**
	 * Converts autobahn port to idcp link
	 * @param portId
	 * @return
	 */
	public static String portToIdcpLink(String portId) {
		
		Link link = AccessPoint.getInstance().getLinkByPortId(portId);
		if (link == null)
			return null;
		
		return "urn:ogf:network:domain=" + IdcpManager.getDomainName() + ":node=" + link.getEndPort().getNode().getBodID() + 
				":port=" + portId + ":link=" + link.getBodID();
	}
	
	/**
	 * Converts back portId (domain:node:port:link) to full idcp representation (urn:ogf:network:domain=*:node=*:port=*:link=*)
	 * @param linkId
	 * @return
	 */
	public static String restorePortId(String portId) {
		
		String[] split = portId.split("\\:");
    	return "urn:ogf:network:domain=" + split[0] + ":node=" + split[1] + ":port=" + split[2] + ":link=" + split[3]; 
	}
	
	/**
	 * Generates subscription id used for notifications
	 * @return
	 */
	public static String generateSubscriptionId() {
		
		UUID id = UUID.randomUUID();
		return id.toString();
	}

	/**
	 * Prints all hops included in PathInfo, used for debugging
	 * @param pathInfo
	 */
	public static void printPathInfo(PathInfo pathInfo) { 
		
		log.info("pathMode: " + pathInfo.getPathSetupMode() + ", numHops: " + pathInfo.getPath().getHop().size());
		for (CtrlPlaneHopContent hop : pathInfo.getPath().getHop()) {
			log.info("linkId: " + hop.getLink().getId() + ", remoteLinkId: " + hop.getLink().getRemoteLinkId());
		}
	}
	
	/**
	 * Sets agreeded vlans as returned in pathInfo from idcp domains
	 * @param pathInfo
	 * @param domain
	 * @param vlan
	 */
	public static void setVlans(PathInfo pathInfo, String domain, int vlan) {
		
		for (CtrlPlaneHopContent hop : pathInfo.getPath().getHop()) {
			String link = hop.getLink().getId();
			//System.out.println("checking link: " + link);
			if (link.contains(domain)) {
				
				hop.getLink().getSwitchingCapabilityDescriptors().getSwitchingCapabilitySpecificInfo().setVlanRangeAvailability(String.valueOf(vlan));
			}
		}
	}
	
	public static void printReservation(String method, AutobahnReservation res) { 
		
		System.out.println("IDCP - " + method + ", isHomeDomain: " + res.isHomeDomain() + ", isLastDomain: " + res.isLastDomain());
		System.out.println("IDCP - isIdcp: " + res.isIdcpReservation() + ", isAb2Idcp: " + 
					res.isAb2IdcpReservation() + ", isIdcp2Ab: " + res.isIdcp2AbReservation());
		System.out.println("nextDomainAddress: " + res.getNextDomainAddress() + ", idcpServer: " + res.getIdcpServer());
		
	}
}
