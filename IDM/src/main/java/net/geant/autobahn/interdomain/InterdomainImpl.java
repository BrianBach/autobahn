package net.geant.autobahn.interdomain;

import javax.jws.WebService;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * @author Michal
 */

@WebService(name = "Interdomain", serviceName = "InterdomainService",
        portName = "InterdomainPort",
        targetNamespace = "http://interdomain.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.interdomain.Interdomain")
public class InterdomainImpl implements Interdomain {

	private final static Logger log = Logger.getLogger(Interdomain.class);
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#cancelReservation(java.lang.String)
	 */
	public void cancelReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Cancel " + resID + " received");
		try {
		    AccessPoint.getInstance().cancelReservation(resID);
        } catch (Exception e) {
            if (e instanceof NoSuchReservationException) {
                throw (NoSuchReservationException) e;
            } else {
                log.error("IDM cancelReservation failed: " + e.getMessage());
                log.debug("Exception info: ", e);
            }
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#hello()
	 */
	public boolean hello() {

		return true;
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportActive(java.lang.String, java.lang.String, boolean)
	 */
	public void reportActive(String resID, String message, boolean success) throws NoSuchReservationException {
		log.info("IDM -> IDM: Report Active: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportActive(resID, message, success);
        } catch (Exception e) {
            if (e instanceof NoSuchReservationException) {
                throw (NoSuchReservationException) e;
            } else {
                log.error("IDM reportActive failed: " + e.getMessage());
                log.debug("Exception info: ", e);
            }
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportCancellation(java.lang.String, java.lang.String, boolean)
	 */
	public void reportCancellation(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Cancel: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportCancellation(resID, message, success);
        } catch (Exception e) {
            log.error("IDM reportCancellation failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportFinished(java.lang.String, java.lang.String, boolean)
	 */
	public void reportFinished(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Finished: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportFinished(resID, message, success);
        } catch (Exception e) {
            log.error("IDM reportFinished failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#reportSchedule(java.lang.String, int, java.lang.String, boolean, net.geant.autobahn.constraints.GlobalConstraints)
	 */
	public void reportSchedule(String resID, int code, String message,
			boolean success, GlobalConstraints global) {
		log.info("IDM -> IDM: Report Schedule: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportSchedule(resID, code, message, success, global);
        } catch (Exception e) {
            log.error("IDM reportSchedule failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.interdomain.Interdomain#scheduleReservation(net.geant.autobahn.reservation.Reservation)
	 */
	public void scheduleReservation(Reservation reservation) {
		log.info("IDM -> IDM: Schedule reservation: " + reservation.getBodID() + " received");
		try {
		    AccessPoint.getInstance().scheduleReservation(reservation);
        } catch (Exception e) {
            log.error("IDM scheduleReservation failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public void withdrawReservation(String resID) throws NoSuchReservationException {
		log.info("IDM -> IDM: Withdraw: " + resID + " received");
		try {
		    AccessPoint.getInstance().withdrawReservation(resID);
        } catch (Exception e) {
            if (e instanceof NoSuchReservationException) {
                throw (NoSuchReservationException) e;
            } else {
                log.error("IDM withdrawReservation failed: " + e.getMessage());
                log.debug("Exception info: ", e);
            }
        }
	}
	
	public void reportWithdraw(String resID, String message, boolean success) {
		log.info("IDM -> IDM: Report Withdraw: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportWithdraw(resID, message, success);
        } catch (Exception e) {
            log.error("IDM reportWithdraw failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public void modifyReservation(String resID, TimeRange time) {
		log.info("IDM -> IDM: Modify: " + resID + " received");
		try {
		    AccessPoint.getInstance().modifyReservation(resID, time);
        } catch (Exception e) {
            log.error("IDM modifyReservation failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	public void reportModify(String resID, TimeRange time,
			String message, boolean success) {
		log.info("IDM -> IDM: Report Modify: " + resID + " received");
		try {
		    AccessPoint.getInstance().reportModify(resID, time, message, success);
        } catch (Exception e) {
            log.error("IDM reportModify failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}
	
	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
        try {
            return AccessPoint.getInstance().getIdentifiers(portName, bodId);
        } catch (Exception e) {
            log.error("IDM getIdentifiers failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
	}
}
