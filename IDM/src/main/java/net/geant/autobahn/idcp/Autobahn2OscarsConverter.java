/**
 * 
 */
package net.geant.autobahn.idcp;

import java.io.IOException;
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
    }

    public Autobahn2OscarsConverter() {
    }

	public void cancelReservation(String resID) {
		
		try {
			OscarsClient oscars = new OscarsClient(idcpServer);
			oscars.cancelReservation(resID);
		} catch (IOException e) { 
			log.error("Autobahn2OscarsConverter: cancelReservation Error: + " + e.getMessage());
		}
	}
	
    public List<Link> getTopology() throws Exception {
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
    
    public List<Reservation> listReservations() throws IOException {
        
        OscarsClient oscars = new OscarsClient(idcpServer);
    	List<Reservation> res = null;
		try {
			res = oscars.getReservationList();
		} catch (AAAFaultMessage e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (BSSFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return res;
        
    }
    
    public boolean modifyReservation(Reservation resInfo)
            throws IOException {
        
        OscarsClient oscars = new OscarsClient(idcpServer);
        ResDetails resTemp = null;

        String src;
        try {
            src = this.getIdcpIngressPort(resInfo);
        }
        catch (Exception e) {
            //This is supposed to be an IDCP reservation, so this is an error
            log.debug("Trying to send to IDCP modification for non-IDCP reservation");
            throw new IOException(e.getMessage());
        }
        //String src = resInfo.getStartPort().getBodID();
        String dest = resInfo.getEndPort().getBodID();
        String vlan = vlans.get(resInfo.getStartPort());

        if (vlan == null) {
            System.out.println("Warn - no assigned vlan found");
            vlan = "3210";
        }

        try {
            // do not support for now
            //throw new RemoteException("not implemented");
        	resTemp = oscars.modifyReservation(resInfo, src, dest, vlan);

        } catch (RemoteException e) {
        	throw new IOException(e.getMessage());
        }
        
        return ((resTemp != null) ? true : false);
    }
    
    public Reservation queryReservation(String resID) throws IOException {
        
        System.out.println("queryReservation: " + resID);
        OscarsClient oscars = new OscarsClient(idcpServer);
        
        Reservation response = null;
        try {
            response = oscars.queryReservation(resID);
        } catch (RemoteException e) {
            System.out.println("queryReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        
        return response;
    }
    
	public int scheduleReservation(AutobahnReservation reservation) {
		
		try {
			
	        String src = "";
	        
	        try {
	            src = this.getIdcpIngressPort(reservation);
	        }
	        catch (Exception e) {
                //This is supposed to be an IDCP reservation, so this is an error
                log.debug("Trying to send to IDCP non-IDCP reservation: " + e.getMessage());
                return ReservationErrors.WRONG_DOMAIN;
	        }
	        
	        //src = reservation.getStartPort().getBodID();
	        String dest = reservation.getEndPort().getBodID();        
	        String vlan = vlans.get(src);

	        if (vlan == null) {
	            log.debug("Warn - no assigned vlan found, assigning vlan 3210");
	            vlan = "3210";
	        }
	        
	        //log.info("OSCARS: src - " + src + ", end: " + dest);
	        
            OscarsClient oscars = new OscarsClient(idcpServer);
	        Reservation res = oscars.scheduleReservation(reservation, src, dest, vlan);
	        
			String errorDesc = res.getDescription();
			
			if(errorDesc != null && !errorDesc.equals("")) {
	    		if(errorDesc.contains("VLAN")) {
	    			return ReservationErrors.CONSTRAINTS_NOT_AGREED;
	    		} else if (errorDesc.contains("oversubscribed")) {
	    			return ReservationErrors.NOT_ENOUGH_CAPACITY;
	    		} else if (errorDesc.contains("error")) {
	    			return ReservationErrors.COMMUNICATION_ERROR;
	    		}
			}
			
			cache.put(reservation.getBodID(), reservation);
			reservation.addStatusListener(this);
			
		} catch (Exception e) { 
			log.info("IdcpConverter - scheduleReservation error: " + e.getMessage());
			return ReservationErrors.COMMUNICATION_ERROR;
		}
		
		return 0;
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
		
		try {
			
			OscarsNotifyClient client = new OscarsNotifyClient();
			Reservation res = cache.get(reservationId);
			client.Notify(res, eventName);
		} catch(Exception e) {
			log.info("Notify sending error: " + e.getMessage());
		}
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
}
