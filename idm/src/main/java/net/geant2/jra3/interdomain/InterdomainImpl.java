package net.geant2.jra3.interdomain;

import javax.jws.WebService;

import net.geant2.jra3.constraints.GlobalConstraints;
import net.geant2.jra3.idm.AccessPoint;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.reservation.Reservation;
import net.geant2.jra3.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

@WebService(name = "Interdomain", serviceName = "InterdomainService",
        portName = "InterdomainPort",
        targetNamespace = "http://interdomain.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.interdomain.Interdomain")
public class InterdomainImpl implements Interdomain {

	private final static Logger log = Logger.getLogger(Interdomain.class);
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Cancel " + resID + " received");
		AccessPoint.getInstance().cancelReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#hello()
	 */
	public boolean hello() {

		return true;
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportActive(java.lang.String, java.lang.String, boolean)
	 */
	public void reportActive(String resID, String message, boolean success) throws NoSuchReservationException {
		log.info("IDM -> IDM: Report Active: " + resID + " received");
		AccessPoint.getInstance().reportActive(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportCancellation(java.lang.String, java.lang.String, boolean)
	 */
	public void reportCancellation(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Cancel: " + resID + " received");
		AccessPoint.getInstance().reportCancellation(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportFinished(java.lang.String, java.lang.String, boolean)
	 */
	public void reportFinished(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Finished: " + resID + " received");
		AccessPoint.getInstance().reportFinished(resID, message, success);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#reportSchedule(java.lang.String, int, java.lang.String, boolean, net.geant2.jra3.constraints.GlobalConstraints)
	 */
	public void reportSchedule(String resID, int code, String message,
			boolean success, GlobalConstraints global) {
		log.info("IDM -> IDM: Report Schedule: " + resID + " received");
		AccessPoint.getInstance().reportSchedule(resID, code, message, success, global);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.interdomain.Interdomain#scheduleReservation(net.geant2.jra3.reservation.Reservation)
	 */
	public void scheduleReservation(Reservation reservation) {
		log.info("IDM -> IDM: Schedule reservation: " + reservation.getBodID() + " received");
		AccessPoint.getInstance().scheduleReservation(reservation);
	}

	public void withdrawReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Withdraw: " + resID + " received");
		AccessPoint.getInstance().withdrawReservation(resID);
	}
	
	public void reportWithdraw(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Withdraw: " + resID + " received");
		AccessPoint.getInstance().reportWithdraw(resID, message, success);
	}

	public void modifyReservation(String resID, TimeRange time) {
		log.info("IDM -> IDM: Modify: " + resID + " received");
		AccessPoint.getInstance().modifyReservation(resID, time);
	}

	public void reportModify(String resID, TimeRange time,
			String message, boolean success) {
		log.info("IDM -> IDM: Report Modify: " + resID + " received");
		AccessPoint.getInstance().reportModify(resID, time, message, success);
	}
	
	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
		return AccessPoint.getInstance().getIdentifiers(portName, bodId);
	}
}
