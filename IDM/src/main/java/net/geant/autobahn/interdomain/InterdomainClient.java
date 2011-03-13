package net.geant.autobahn.interdomain;

import java.net.MalformedURLException;
import java.net.URL;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

public class InterdomainClient implements Interdomain {
	
	private final static Logger log = Logger.getLogger(Interdomain.class);
	
	private Interdomain interdomain;
	private String endPoint;
	
	public InterdomainClient(String endPoint) throws MalformedURLException {
	    log.debug("Initial IDM endpoint is: " + endPoint);
        String finalEndPoint = endPoint;
        
		// Query IDM to Lookup
		String host = AccessPoint.getInstance().getProperty("lookuphost");
        String idmLocation = "";
        
        if (isLSavailable(host)) {
            LookupService lookup = new LookupService(host);
            try {
            	idmLocation = lookup.QueryIdmLocation(endPoint);
                log.debug("IDM endpoint retrieved from LS: " + idmLocation);
            } catch (LookupServiceException e) {
                log.info("No query to the Lookup Service could be performed in order to locate IDM.");
            	log.info(e.getMessage());
            }
        }
        
        if (idmLocation != null && !idmLocation.equals("")) {
            // If the Lookup contained the required information, this will be
            // used as the endpoint
        	finalEndPoint = idmLocation;
        } else {
            log.info("IDM location for domain <" + endPoint + "> could not be obtained from Lookup Service");
            // This is not a fatal situation, as the IDM location might be
            // available through another channel (e.g. in properties files), and
            // so the provided endPoint parameter might be a valid URL
            
            new URL(finalEndPoint);
            log.info("IDM name ("+ endPoint +") seems a valid URL, trying to connect to it");
        }
        
        log.debug("IDM endpoint finally to be used for IDM -> IDM communication: " + finalEndPoint);
		InterdomainService service = new InterdomainService(finalEndPoint);
		this.endPoint = finalEndPoint;
		this.interdomain = service.getInterdomainPort();
	}
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#scheduleReservation(net.geant.autobahn.reservation.Reservation)
	 */
	public void scheduleReservation(Reservation reservation) {
		log.info("IDM -> IDM: Sending schedule reservation: " + reservation.getBodID() + " to: " + endPoint);
		interdomain.scheduleReservation(reservation);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Sending cancel reservation: " + resID + " to: " + endPoint);
		interdomain.cancelReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#hello()
	 */
	public boolean hello() {
		return interdomain.hello();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportActive(java.lang.String, java.lang.String, boolean)
	 */
	public void reportActive(String resID, String message, boolean success) throws NoSuchReservationException {
		log.info("IDM -> IDM: Sending report active: " + resID + " to: " + endPoint);
		interdomain.reportActive(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportCancellation(java.lang.String, java.lang.String, boolean)
	 */
	public void reportCancellation(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Sending report cancelled: " + resID + " to: " + endPoint);
		interdomain.reportCancellation(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportFinished(java.lang.String, java.lang.String, boolean)
	 */
	public void reportFinished(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Sending report finished: " + resID + " to: " + endPoint);
		interdomain.reportFinished(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportSchedule(java.lang.String, int, java.lang.String, boolean, net.geant.autobahn.constraints.GlobalConstraints)
	 */
	public void reportSchedule(String resID, int code, String message,
			boolean success, GlobalConstraints global) {
		log.info("IDM -> IDM: Sending report schedule: " + resID + " to: " + endPoint);
		interdomain.reportSchedule(resID, code, message, success, global);
	}

	public void withdrawReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Sending withdraw reservation: " + resID + " to: " + endPoint);
		interdomain.withdrawReservation(resID);
	}
	
	public void reportWithdraw(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Sending report withdraw: " + resID + " to: " + endPoint);
		interdomain.reportWithdraw(resID, message, success);
	}

	public void modifyReservation(String resID, TimeRange time) {
		log.info("IDM -> IDM: Sending modify: " + resID + " to: " + endPoint);
		interdomain.modifyReservation(resID, time);
	}
	
	public void reportModify(String resID, TimeRange time, String message,
			boolean success) {
		log.info("IDM -> IDM: Sending report modify: " + resID + " to: " + endPoint);
		interdomain.reportModify(resID, time, message, success);
	}

	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
		return interdomain.getIdentifiers(portName, bodId);
	}

    private boolean isLSavailable(String ls) {
        if ((ls == null) || ls.equals("none") || ls.equals("")) {
            return false;
        }
        // Check if it is a proper URL
        try {
            new URL(ls);
        } catch (MalformedURLException e) {
            log.debug(ls + " is not a proper URL for LS");
            return false;
        }
        return true;
    }
}
