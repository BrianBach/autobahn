/**
 * 
 */
package net.geant.autobahn.proxy;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.idcp.AAAFaultMessage;
import net.geant.autobahn.idcp.BSSFaultMessage;
import net.geant.autobahn.idcp.OscarsClient;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationErrors;
import net.geant.autobahn.reservation.ReservationStatusListener;

import org.apache.log4j.Logger;

/**
 * Client to proxy client
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

	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) {
		
		try {
			OscarsClient oscars = new OscarsClient();
			oscars.cancelReservation(resID);
		} catch (IOException e) { 
			log.error("ProxyClientConverter: cancelReservation Error: + " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#getTopology()
     */
    public List<Link> getTopology() throws Exception {
    	// If the list is empty, XML will return null.
        // So we need to make sure that in that case an empty list is returned.
        OscarsClient oscars = new OscarsClient();
        List<Link> res = oscars.getTopology();
        if (res==null) {
            res = new ArrayList<Link>();
        }
        return res;
            
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#listReservations()
     */
    public List<ReservationInfo> listReservations() throws IOException {
        
        // If the list is empty, XML will return null.
        // So we need to make sure that in that case an empty list is returned.
        OscarsClient oscars = new OscarsClient();
    	List<ReservationInfo> res = null;
		try {
			res = oscars.getReservationList();
		} catch (AAAFaultMessage e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (BSSFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (res==null) {
            res = new ArrayList<ReservationInfo>();
            //@todo 
        } 
        return res;
        
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#modifyReservation(net.geant.autobahn.proxy.ReservationInfo)
     */
    public boolean modifyReservation(ReservationInfo resInfo)
            throws IOException {
        
        OscarsClient oscars = new OscarsClient();
        ResDetails resTemp = null;
        
        String src = "";
        
        src = resInfo.getStartPort();
        String dest = resInfo.getEndPort();        
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
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.proxy.Proxy#queryReservation(java.lang.String)
     */
    public ReservationInfo queryReservation(String resID) throws IOException {
        
        System.out.println("queryReservation: " + resID);
        OscarsClient oscars = new OscarsClient();
        
        ReservationInfo response = null;
        try {
            response = oscars.queryReservation(resID);
        } catch (RemoteException e) {
            System.out.println("queryReservation: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        
        return response;
    }
    
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#scheduleReservation(net.geant.autobahn.reservation.Reservation)
	 */
	public int scheduleReservation(AutobahnReservation reservation) {
		
		try {
	        ReservationInfo resInfo = Autobahn2OscarsConverter.convertReservation(reservation);
			
			OscarsClient oscars = new OscarsClient();
	        
	        String src = "";
	        
	        src = resInfo.getStartPort();
	        String dest = resInfo.getEndPort();        
	        String vlan = vlans.get(resInfo.getStartPort());

	        if (vlan == null) {
	            System.out.println("Warn - no assigned vlan found");
	            vlan = "3210";
	        }
	        
	        ReservationInfo res = oscars.scheduleReservation(resInfo, src, dest, vlan);
	        
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

	public static ReservationInfo convertReservation(Reservation res) {
		ReservationInfo ri = new ReservationInfo();
		ri.setBidirectional(res.isBidirectional());
		ri.setBodID(res.getBodID());
		ri.setCapacity(res.getCapacity());
		ri.setDescription(res.getDescription());
		ri.setEndPort(res.getEndPort().getBodID());
		ri.setEndTime(res.getEndTime());
		ri.setMaxDelay(res.getMaxDelay());
		ri.setPriority(res.getPriority());
		ri.setResiliency(res.getResiliency());
		ri.setStartPort(res.getStartPort().getBodID());
		ri.setStartTime(res.getStartTime());
		ri.setState(res.getState());
		
		RangeConstraint rcon = res.getGlobalConstraints()
				.getDomainConstraints().get(0).getPathConstraints().get(0)
				.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(rcon != null)
			ri.setCalculatedConstraints("" + rcon.getFirstValue());
		// set path
/*		Link[] path = new Link[res.getPath().getLinks().size()];
		ri.setPath(res.getPath().getLinks().toArray(path));
*/		
		return ri;
	}
}
