/**
 * 
 */
package net.geant.autobahn.idcp;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationErrors;

/**
 * Responsible for translating autobahn to idcp parameters
 * 
 * @author PCSS
 */
public final class ToIdcp {
	
	private static Logger log = Logger.getLogger(ToIdcp.class);
	
	public static final int TIME_SCALE = 1000; // divide time by this value before sending
	public static final int BANDWIDTH_SCALE = 1000000; // idcp uses Mbps so we must divide by this value
	private static final String DUMMY_PORT = "_dummyPort"; 
	private final String url;
	private final IdcpClient idcp;
	
	// idcp.*.properties should have
	// url=
	// url.notify=
	// ab_port=idcp_port
	
	public ToIdcp(String url) {
		
		this.url = url;
		this.idcp = new IdcpClient(url);
	}
	
   /**
     * Returns transformed reservation identifier that is suitable for idcp 
     * @param resId
     * @return
     */
    public static String convertResId(String resId) {
    	
    	String[] split = resId.split("\\@");
    	if (split[0].contains("."))
    		log.info("domain for " + resId + " contains postfix, another one will be applied");
    	String domain = split[0] + ".net-";
    	split = split[1].split("\\_");
    	return domain + split[0] + split[2];
    }
    
    /**
     * Restores idcp identifier to autobahn reservation identifier
     * @param resId
     * @return
     */
    public static String restoreResId(String resId) {
    	
    	String[] split = resId.split("\\-");
    	String domain = split[0].replace(".net", "");
    	int length = split[1].length();
    	String resNum = String.valueOf(split[1].charAt(length - 1));
    	String num = split[1].substring(0, length - 1);
    	return domain + "@" + num + "_res_" + resNum;
    }
	
	/**
	 * Converts back linkId (domain:node:port) to full idcp representation (urn:ogf:network:domain=*:node=*:port=*:link=*)
	 * @param linkId
	 * @return
	 */
	public static String restorePortId(String portId) {
		
		String[] split = portId.split("\\:");
    	return "urn:ogf:network:domain=" + split[0] + ":node=" + split[1] + ":port=" + split[2] + ":link=*"; 
	}
	
	/**
	 * Sends schedule reservation to idcp domain
	 * @param reservation
	 * @return
	 */
	public int schedule(AutobahnReservation reservation) {
		
		if (!reservation.isIdcpReservation())
			return ReservationErrors.RESERVATION_NOTSUPPORTED;
			
		// Reservations starting from an idcp domain and terminating at autobahn domain cannot be supported as the idcp side is not aware of autobahn 
    	// topology. Still we should allow this kind of reservations, however exceptions thrown by the idcp is less than descriptive so we quit here  
	    if (reservation.isIdcp2AbReservation() && !reservation.isAb2IdcpReservation()) 
	    	return ReservationErrors.RESERVATION_NOTSUPPORTED;

	    String src;
	    try {
            src = this.getIdcpIngressPort(reservation);
    	    if (src.endsWith(DUMMY_PORT)) 
    	    	src = src.substring(0, src.indexOf(DUMMY_PORT));
        } catch (Exception e) {
        	//This is supposed to be an IDCP reservation, so this is an error
            log.debug("Trying to send to IDCP non-IDCP reservation: " + e.getMessage());
            return ReservationErrors.WRONG_DOMAIN;
	    }
        Properties properties = IdcpManager.getProperties(url);
	    
	    // check if link mapping (autobahn's egress to idcp ingress) has been provided by cnis (port desc property)
	    String portDesc = reservation.getEndPort().getDescription();
	    if (portDesc != null && portDesc.contains("idcplink")) {
	    	String linkMapping = portDesc.substring(portDesc.indexOf("idcplink"));
	    	src = linkMapping.split("\\=")[1];
	    } else {
	     	if (properties.containsKey(src)) {
	     		src = properties.getProperty(src);
	     	} else
	     		log.info("ToIdcp - source port " + src + " with no link mapping");
	    }
	    String dst = reservation.getEndPort().getBodID();
	    dst = ToIdcp.restorePortId(dst);
	    	    
	    final String resId = convertResId(reservation.getBodID());
	    final String desc =  reservation.getDescription();
	    final long startTime = reservation.getStartTime().getTimeInMillis() / TIME_SCALE;
	    final long endTime = reservation.getEndTime().getTimeInMillis() / TIME_SCALE;
	    final int bandwidth = ((int)(reservation.getCapacity() / BANDWIDTH_SCALE));
	    final String vlan = "any"; 
	    final String pathMode = properties.containsKey(IdcpManager.NOTIFY_URL) ? IdcpClient.PATH_MODE_MANUAL : IdcpClient.PATH_MODE_AUTOMATIC;
	    log.info("ToIdcp - scheduling " + resId + ", src - " + src + ", dst - " + dst);
	    
	    try {
	    	idcp.schedule(resId, desc, src, dst, startTime, endTime, bandwidth, vlan, pathMode);
	    	return 0;
	    } catch (IdcpException e) {
	    	log.info("ToIdcp - schedule failed - " + e.getMessage());
	    	String error = e.getMessage().toLowerCase();
	    	if (error.contains("vlan"))
        		return ReservationErrors.CONSTRAINTS_NOT_AGREED;
        	else if (error.contains("oversubscribed"))
        		return ReservationErrors.NOT_ENOUGH_CAPACITY;
        	else 
        		return ReservationErrors.COMMUNICATION_ERROR;
	    }
	}
	
	/**
	 * Sends cancel message to idcp domain
	 * @param resId
	 */
	public void cancel(String resId) { 
		
		final String rid = convertResId(resId);
		log.info("ToIdcp - cancelling " + rid);
		
		try {
			idcp.cancel(rid);
		} catch (IdcpException e) {
			log.info("ToIdcp - cancel failed - " + e.getMessage());
			// should we rethrow?
		}
	}
	
	private String getIdcpIngressPort(Reservation reservation) throws Exception {
        // The original reservation defines an AutoBAHN port as source and an
        // IDCP one as destination. We have to change the source port to the IDCP
        // ingress port before sending the reservation over to IDCP.
        Path res_path = reservation.getPath();
        Link ab2idcp_link = res_path.getIngress(url);
        
        Port srcPort;
        if (ab2idcp_link.getStartPort().isIdcpPort()) {
            srcPort = ab2idcp_link.getStartPort();
        }
        else if (ab2idcp_link.getEndPort().isIdcpPort()) {
            srcPort = ab2idcp_link.getEndPort();
        }
        else {
            throw new Exception("This reservation does not include IDCP cloud");
        }
        return srcPort.getBodID();
	}
	
	/**
	 * Returns a list of links that matches filer (:link=filter)
	 * @param filter
	 * @return
	 * @throws IdcpException
	 */
	public List<Link> getTopology(String filter) throws IdcpException {
		
		List<Link> links = idcp.getTopology();
		List<Link> filtered = new ArrayList<Link>();
		
		for (Link l : links) {
			if (l.getBodID().endsWith(filter))
				filtered.add(l);
		}
		return filtered;
	}
}
