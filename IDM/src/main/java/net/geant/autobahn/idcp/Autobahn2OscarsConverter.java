/**
 * 
 */
package net.geant.autobahn.idcp;



import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationErrors;

import org.apache.log4j.Logger;

/**
 * AutoBAHN to OSCARS client facade
 * @author Michal
 */
public class Autobahn2OscarsConverter { 
		
	private static Logger log = Logger.getLogger(Autobahn2OscarsConverter.class);
	
    private static Map<String, String> vlans = new HashMap<String, String>();
    private String idcpServer;
    private Properties portsMapping;

    private static final String DEFAULT_VLAN = "3210";  
    static {
        vlans.put("10.12.32.5", "3202"); //HEA
        vlans.put("10.13.32.4", "3204"); //PIO
        vlans.put("10.11.32.6", "3203"); //ATH
        vlans.put("10.11.32.7", "3208"); //CRE
        vlans.put("10.16.32.2", "3205"); //CAR
        vlans.put("10.14.32.2", "3207"); //GAR
    }

    public Autobahn2OscarsConverter(String idcpServer) {
        this.idcpServer = idcpServer;
        this.portsMapping = loadAutobahn2IdcpPorts();
    }
    
    public int scheduleReservation(AutobahnReservation reservation) {
		
    	// Reservations starting from an idcp domain and terminating at autobahn domain cannot be supported as the idcp side is not aware of autobahn 
    	// topology. Still we should allow this kind of reservations, however exceptions thrown by the idcp is less than descriptive so we quit here  
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
	    
        for (Enumeration e = portsMapping.keys(); e.hasMoreElements(); ) {
        	
        	String abEgress = (String)e.nextElement();
        	if (src.equals(abEgress)) { 
        		src = portsMapping.getProperty(((String)e.nextElement())); // found idcp ingress
        		break;
        	}
        }
	    
        try {
        
        	OscarsClient oscars = new OscarsClient(idcpServer);
        	oscars.scheduleReservation(reservation, src, dest, vlan);
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
			log.debug("Warn - no assigned vlan found, assigning default vlan " + DEFAULT_VLAN);
	        vlan = DEFAULT_VLAN;
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
        
        System.out.println("LINK - " + ab2idcp_link);
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
    
    private Properties loadAutobahn2IdcpPorts() {
		
    	/*
    	Idcp servers are defined in idm.properties file as strings that begin with idcp. sequence.
    	Try to find for each idcp server its property file according to the following pattern:
    	if idm.properties has idcp.oscars property, then look for idcp.oscars.properties file
    	*/
		Properties properties = new Properties();
        
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("etc/" + idcpServer + ".properties");

			try {
				properties.load(is);
			} catch (Exception ex) {
				// it gets here, if input stream is null although it should throw declared IOException
				return properties;
			}
			is.close();
			log.debug(properties.size() + " autobahn<->idcp ports loaded");
			
		} catch (IOException e) {
			log.debug("Autobahn2Idcp ports for " + idcpServer + " could not be loaded: " + e.getMessage());
		}
		return properties;
	}
}
