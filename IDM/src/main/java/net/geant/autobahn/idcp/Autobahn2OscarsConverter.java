/**
 * 
 */
package net.geant.autobahn.idcp;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.network.Link;
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
    

    static {
        vlans.put("10.12.32.5", "3202"); //HEA
        vlans.put("10.13.32.4", "3204"); //PIO
        vlans.put("10.11.32.6", "3203"); //ATH
        vlans.put("10.11.32.7", "3208"); //CRE
        vlans.put("10.16.32.2", "3205"); //CAR
        vlans.put("10.14.32.2", "3207"); //GAR
    }

	public void cancelReservation(String resID) {
		
		try {
			OscarsClient oscars = new OscarsClient();
			oscars.cancelReservation(resID);
		} catch (IOException e) { 
			log.error("Autobahn2OscarsConverter: cancelReservation Error: + " + e.getMessage());
		}
	}
	
    public List<Link> getTopology() throws Exception {
        OscarsClient oscars = new OscarsClient();
        return oscars.getTopology();
    }
    
    public List<Reservation> listReservations() throws IOException {
        
        OscarsClient oscars = new OscarsClient();
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
        
        OscarsClient oscars = new OscarsClient();
        ResDetails resTemp = null;
        
        String src = resInfo.getStartPort().getBodID();
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
        OscarsClient oscars = new OscarsClient();
        
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
			OscarsClient oscars = new OscarsClient();
	        
	        String src = "";
	        
	        src = reservation.getStartPort().getBodID();
	        String dest = reservation.getEndPort().getBodID();        
	        String vlan = vlans.get(src);

	        if (vlan == null) {
	            System.out.println("Warn - no assigned vlan found");
	            vlan = "3210";
	        }
	        
	        Reservation res = oscars.scheduleReservation(reservation, src, dest, vlan);
	        
			String errorDesc = res.getDescription();
			
			if(errorDesc != null && !errorDesc.equals("")) {
	    		if(errorDesc.contains("VLAN")) {
	    			return ReservationErrors.CONSTRAINTS_NOT_AGREED;
	    		} else if (errorDesc.contains("oversubscribed")) {
	    			return ReservationErrors.NOT_ENOUGH_CAPACITY;
	    		} else {
	    			return ReservationErrors.COMMUNICATION_ERROR;
	    		}
			}
			
			cache.put(reservation.getBodID(), reservation);
			reservation.addStatusListener(this);
			
		} catch (IOException e) { 
			log.error("ProxyClientConverter: scheduleReservation Error: " + e.getMessage());
			return ReservationErrors.COMMUNICATION_ERROR;
		}
		
		return 0;
	}
	
	
	public void reservationActive(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationCancelled(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationFinished(String reservationId) {
		notifyIDC(reservationId);
	}

	public void reservationModified(String reservationId, boolean success) {
		//not implemented
	}

	public void reservationProcessingFailed(String reservationId, String cause) {
		notifyIDC(reservationId);
	}

	public void reservationScheduled(String reservationId) {
		notifyIDC(reservationId);
	}
	
	private void notifyIDC(String reservationId) {
/*		try {
			ProxyClient proxy = new ProxyClient();
			Reservation res = cache.get(reservationId);
			proxy.Notify(convertReservation(res));
		} catch(IOException e) {
			log.error("Notify error: " + e.getMessage());
		}
*/	}

}
