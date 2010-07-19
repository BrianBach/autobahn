package net.geant.autobahn.tool;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.reservation.ReservationParams;

import org.apache.log4j.Logger;

/**
 * Web services client for the Technology Proxy module. 
 * 
 * @author Michal
 *
 */
public class ToolClient implements Tool {
	
	private final static Logger log = Logger.getLogger(Tool.class);
	
	private Tool tool;
	private Map<String, ToolState> states = new HashMap<String, ToolState>();
	
	
	/**
	 * 
	 * @param endPoint
	 */
	public ToolClient(String endPoint) {
		if(endPoint.equals("none")) {
            log.info("TP location was specified as none, DM->TP communication impossible");
            return;
        }
        
        try {
            new URL(endPoint);
            log.debug("TP location seems a valid URL, trying to connect to it");
        } catch (MalformedURLException e) {
            log.error("No valid TP location ("+ endPoint +") could be found, DM->TP communication impossible");
            return;
        }
		
		ToolService service = new ToolService(endPoint);
		tool = service.getToolPort();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException {
		
		ToolState state = new ToolState();
		states.put(resID, state);
		
		log.info("DM -> Tool: Adding reservation " + resID + " begin");
		if(tool != null) {
			try {
				tool.addReservation(secureId(resID), links, params);
			} catch(AAIException e) {
				releaseLock(resID, false);
				throw e;
			} catch(RequestException e) {
				releaseLock(resID, false);
				throw e;
			} catch(SystemException e) {
				releaseLock(resID, false);
				throw e;
			} catch(ResourceNotFoundException e) {
				releaseLock(resID, false);
				throw e;
			}
		}
		
		releaseLock(resID, true);
		log.info("DM -> Tool: Adding reservation  " + resID + " end");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.tool.Tool#removeReservation(java.lang.String, java.util.List, net.geant.autobahn.reservation.ReservationParams)
	 */
	public void removeReservation(String resID, List<GenericLink> links,
			ReservationParams params) throws AAIException,
			RequestException, SystemException, ReservationNotFoundException {
		
		ToolState state = states.get(resID);
		if(state == null) {
			log.warn("Removing in Tool: reservation " + resID + " not added");
			return;
		}
		
		synchronized (state) {
			if(state.isBusy()) {
				try {
					log.info("Waiting for previous operation to end: " + resID);
					state.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(!state.isAdded()) {
					log.info("Nothing to remove in Tool: " + resID);
					return;
				}
			}
		}
		
		log.info("DM -> Tool: Removing reservation  " + resID + " begin");
		if(tool != null)
			tool.removeReservation(secureId(resID), links, params);
		log.info("DM -> Tool: Removing reservation  " + resID + " end");		
	}

	/**
	 * Indicates that reservation has been previously added to the TP. Used to
	 * restore the proper state.
	 * 
	 * @param resID String reservation idenitfier
	 */
	public void setReservationAdded(String resID) {
		ToolState st = new ToolState();
		st.taskEnd(true);
		states.put(resID, st);
	}
	
	private void releaseLock(String resID, boolean success) {
		ToolState state = states.get(resID);
		
		if(state == null) {
			log.info("No lock for this reservation: " + resID);
			return;
		}
		
		state.taskEnd(success);
		synchronized (state) {
			state.notify();
		}
	}
	
	private static String secureId(String resId) {
		return resId.replaceAll(":", "_");
	}
}
