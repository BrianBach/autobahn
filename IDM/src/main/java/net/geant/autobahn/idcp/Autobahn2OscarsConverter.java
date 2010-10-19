/**
 * 
 */
package net.geant.autobahn.idcp;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.idcp.notify.OscarsNotifyClient;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationErrors;
import net.geant.autobahn.reservation.ReservationStatusListener;

import org.apache.log4j.Logger;

/**
 * AutoBAHN to OSCARS client facade
 * @author Michal
 */
public class Autobahn2OscarsConverter implements ReservationStatusListener {
		
	private static Logger log = Logger.getLogger(Autobahn2OscarsConverter.class);
	
	private Map<String, Reservation> cache = new HashMap<String, Reservation>();
    private static Map<String, String> vlans = new HashMap<String, String>();
    private String idcpServer;

    private static final String DEFAULT_VLAN = "3210";  
    static {
        vlans.put("10.12.32.5", "3202"); //HEA
        vlans.put("10.13.32.4", "3204"); //PIO
        vlans.put("10.11.32.6", "3203"); //ATH
        vlans.put("10.11.32.7", "3208"); //CRE
        vlans.put("10.16.32.2", "3205"); //CAR
        vlans.put("10.14.32.2", "3207"); //GAR
    }

    public Autobahn2OscarsConverter() { }
    
    public Autobahn2OscarsConverter(String idcpServer) {
        this.idcpServer = idcpServer;
    }
    
    public int scheduleReservation(AutobahnReservation reservation) {
		
    	// reservations starting at idcp and terminating at autobahn cannot be supported as idcp side does not have autobahn topology
	    // we should schedule it and catch an exception, unfortunately it is not much descriptive so we quit here
	    if (reservation.isIdcp2AbReservation() && !reservation.isAb2IdcpReservation())
	    	return ReservationErrors.RESERVATION_NOTSUPPORTED;
    	
        String src = "";
        
        try {
            src = this.getIdcpIngressPort(reservation);
        }
        catch (Exception e) {
        	//This is supposed to be an IDCP reservation, so this is an error
            log.debug("Trying to send to IDCP non-IDCP reservation: " + e.getMessage());
            return ReservationErrors.WRONG_DOMAIN;
	    }
	        
	    String dest = reservation.getEndPort().getBodID();

	    // if this is an idcp reservation, we need to strip off dummyPort part and assembly the original identifiers
        if (reservation.isIdcpReservation() && src.endsWith("_dummyPort")) {
        	
        	src = src.substring(0, src.indexOf("_dummyPort"));
        
        	try {
        		// this is slow and painful way as we call getTopology on idcp domain
        		for (Link l : this.getLinks()) { 
        			
        			if (l.getBodID().contains(src))
        				src = l.getBodID();
        			else if (l.getBodID().contains(dest))
        				dest = l.getBodID();
        		}
        		
        	} catch (Exception e) {
        		
        		log.debug("scheduleReseration - could not restore original links");
        		return ReservationErrors.COMMUNICATION_ERROR;
        	}
        }
	    	    
	    String vlan = vlans.get(src);
	    if (vlan == null) {
	    	log.debug("Warn - no assigned vlan found, assigning default vlan " + DEFAULT_VLAN);
	        vlan = DEFAULT_VLAN;
	    }
	    
        try {
        
        	OscarsClient oscars = new OscarsClient(idcpServer);
        	oscars.scheduleReservation(reservation, src, dest, vlan);
            cache.put(reservation.getBodID(), reservation);
            reservation.addStatusListener(this);
            return 0;
        	
        } catch (RemoteException e) {
        	
        	log.debug("scheduleReservation error - " + e.getMessage());
        	// AAA and BSS exceptions are seen as RemoteException so additional info must be extracted from the exception message
        	String error = e.getMessage().toLowerCase();
        	if (error.contains("vlan"))
        		return ReservationErrors.CONSTRAINTS_NOT_AGREED;
        	else if (error.contains("oversubscribed"))
        		return ReservationErrors.NOT_ENOUGH_CAPACITY;
        	else 
        		return ReservationErrors.COMMUNICATION_ERROR;
        }
    }
    
    public void cancelReservation(String resID) {
		
    	OscarsClient oscars = new OscarsClient(idcpServer);
    	
		try {
			
			oscars.cancelReservation(resID);
		} catch (RemoteException e) { 
			
			log.debug("cancelReservation error - " + e.getMessage());
		}
	}
	
	public boolean modifyReservation(Reservation reservation) throws RemoteException, Exception {

		OscarsClient oscars = new OscarsClient(idcpServer);

		String src;
		try {
			src = this.getIdcpIngressPort(reservation);
		}
		catch (Exception e) {
			//	This is supposed to be an IDCP reservation, so this is an error
			log.debug("Trying to send to IDCP modification for non-IDCP reservation");
			throw e;
		}
		//	String src = resInfo.getStartPort().getBodID();
		String dest = reservation.getEndPort().getBodID();
		String vlan = vlans.get(reservation.getStartPort());

		if (vlan == null) {
			System.out.println("Warn - no assigned vlan found");
			vlan = "3210";
		}

		ResDetails res;
		try {
			res = oscars.modifyReservation(reservation);
		} catch (RemoteException e) {
			throw e;
		}
		return ((res != null) ? true : false);
	}
	
	public ResDetails queryReservation(String resID) throws RemoteException {
        
        OscarsClient oscars = new OscarsClient(idcpServer);
        return oscars.queryReservation(resID);
    }
	
    public List<Link> getTopology() throws RemoteException {
    
    	// we really need to cache this somehow
   		OscarsClient oscars = new OscarsClient(idcpServer);
   		return oscars.getTopology();
    }
    
    public List<Link> getEndpoints() throws Exception {
    	
    	List<Link> links = this.getTopology();
    	List<Link> endpoints = new ArrayList<Link>();
    	
    	for (int i=0; i<links.size(); i++) {
    		
    		String[] split = links.get(i).getBodID().split(":");
    		
    		if (split[6].equals("*")) {
    			endpoints.add(links.get(i));
    		}
    	}
    	
    	return endpoints;
    }
    
    public List<Link> getLinks() throws Exception {
    	
    	List<Link> links = this.getTopology();
    	List<Link> oscarsLinks = new ArrayList<Link>();
    	
    	for (int i=0; i<links.size(); i++) {
    		
    		String[] split = links.get(i).getBodID().split(":");
    		    		
    		if (!split[6].equals("*")) {
    			oscarsLinks.add(links.get(i));
    		}    		   		
    	}
    	
    	return oscarsLinks;
    }
    
    private String getIdcpIngressPort(Reservation reservation) throws Exception {
        // The original reservation defines an AutoBAHN port as source and an
        // IDCP one as destination. We have to change the source port to the IDCP
        // ingress port before sending the reservation over to IDCP.
        Path res_path = reservation.getPath();
        Link ab2idcp_link = res_path.getIngress(idcpServer);
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
    
	public void reservationActive(String reservationId) {
		notifyIDC(reservationId, "PATH_SETUP_COMPLETED");
	}

	public void reservationCancelled(String reservationId) {
		notifyIDC(reservationId, "RESERVATION_CANCEL_COMPLETED");
	}

	public void reservationFinished(String reservationId) {
		notifyIDC(reservationId, "RESERVATION_CREATE_COMPLETED");
	}

	public void reservationModified(String reservationId, boolean success) {
		//not implemented
	}

	public void reservationProcessingFailed(String reservationId, String cause) {
		notifyIDC(reservationId, "RESERVATION_CREATE_FAILED");
	}

	public void reservationScheduled(String reservationId) {
		notifyIDC(reservationId, "RESERVATION_CREATE_CONFIRMED");
	}
	
	private void notifyIDC(String reservationId, String eventName) {
		
		Reservation res = cache.get(reservationId);
		if (res != null) { 
			  
			OscarsNotifyClient client = new OscarsNotifyClient(res.getIdcpServer() + "Notify");
			
			try {
			
				client.Notify(res, eventName);
			} catch(Exception e) {
				log.info("Notify sending error: " + e.getMessage());
			}
		}
	}
}
