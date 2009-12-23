package net.geant2.jra3.interdomain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.lookup.LookupService;
import net.geant2.jra3.lookup.LookupServiceException;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.reservation.Reservation;
import net.geant2.jra3.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

public class InterdomainClient implements Interdomain {
	
	private final static Logger log = Logger.getLogger(Interdomain.class);
	
	private Interdomain interdomain;
	private String endPoint;
	
	public InterdomainClient(String endPoint) {
		// Query IDM to Lookup
		String finalEndPoint = endPoint;
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(
                        "etc/idm.properties");
            properties.load(is);
            is.close();
            log.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            log.info("Could not load app.properties: " + e.getMessage());
        }
		String host = properties.getProperty("lookuphost");
        LookupService lookup = new LookupService(host);
        String idmLocation = "";
        try {
        	idmLocation = lookup.QueryIdmLocation(endPoint);
        } catch (LookupServiceException e) {
        	log.info("IDM module was not found, Please check your syntax");
        	log.info(e.getMessage());
        }
        if (idmLocation != "") {
        	finalEndPoint = idmLocation;
        }
        
		InterdomainService service = new InterdomainService(finalEndPoint);
		this.endPoint = finalEndPoint;
		this.interdomain = service.getInterdomainPort();
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#scheduleReservation(net.geant2.jra3.reservation.Reservation)
	 */
	public void scheduleReservation(Reservation reservation) {
		log.info("IDM -> IDM: Sending schedule reservation: " + reservation.getBodID() + " to: " + endPoint);
		interdomain.scheduleReservation(reservation);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Sending cancel reservation: " + resID + " to: " + endPoint);
		interdomain.cancelReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#hello()
	 */
	public boolean hello() {
		return interdomain.hello();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportActive(java.lang.String, java.lang.String, boolean)
	 */
	public void reportActive(String resID, String message, boolean success) throws NoSuchReservationException {
		log.info("IDM -> IDM: Sending report active: " + resID + " to: " + endPoint);
		interdomain.reportActive(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportCancellation(java.lang.String, java.lang.String, boolean)
	 */
	public void reportCancellation(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Sending report cancelled: " + resID + " to: " + endPoint);
		interdomain.reportCancellation(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportFinished(java.lang.String, java.lang.String, boolean)
	 */
	public void reportFinished(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Sending report finished: " + resID + " to: " + endPoint);
		interdomain.reportFinished(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportSchedule(java.lang.String, int, java.lang.String, boolean, net.geant2.jra3.constraints.GlobalConstraints)
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
}
