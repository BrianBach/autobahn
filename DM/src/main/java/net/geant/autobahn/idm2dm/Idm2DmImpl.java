package net.geant.autobahn.idm2dm;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.AccessPoint;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;

/**
 * Implementation of the Idm2Dm web service. Delegates the messages the an
 * AccessPoint instance.
 * 
 * @author Michal
 */
@WebService(name="Idm2Dm", serviceName="Idm2DmService",
		portName="Idm2DmPort", 
		targetNamespace="http://idm2dm.autobahn.geant.net/",
		endpointInterface="net.geant.autobahn.idm2dm.Idm2Dm")
public class Idm2DmImpl implements Idm2Dm {

    private final Logger log = Logger.getLogger(Idm2DmImpl.class);
    
	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String arg0, Link[] arg1, ReservationParams arg2)
			throws ConstraintsAlreadyUsedException {
	    try {
	        AccessPoint.getInstance().addReservation(arg0, arg1, arg2);
        } catch (Exception e) {
            if (e instanceof ConstraintsAlreadyUsedException) {
                throw (ConstraintsAlreadyUsedException) e;
            } else {
                log.error("DM addReservation failed: " + e.getMessage());
                log.debug("Exception info: ", e);
            }
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkResources(net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public DomainConstraints[] checkResources(Link[] arg0, ReservationParams arg1)
			throws OversubscribedException, AAIException {
	    try {
	        return AccessPoint.getInstance().checkResources(arg0, arg1);
        } catch (Exception e) {
            if (e instanceof OversubscribedException) {
                throw (OversubscribedException) e;
            } else if (e instanceof AAIException) {
                throw (AAIException) e;
            } else {
                log.error("DM checkResources failed: " + e.getMessage());
                log.debug("Exception info: ", e);
                return null;
            }
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String arg0) {

		try {
			AccessPoint.getInstance().removeReservation(arg0);
        } catch (Exception e) {
            log.error("DM removeReservation failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkModification(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public boolean checkModification(String resId, TimeRange time) {
	    try {
	        return AccessPoint.getInstance().checkModification(resId, time);
        } catch (Exception e) {
            log.error("DM checkModification failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return false;
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#modifyReservation(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public void modifyReservation(String resId, TimeRange time) {
	    try {
	        AccessPoint.getInstance().modifyReservation(resId, time);
        } catch (Exception e) {
            log.error("DM modifyReservation failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#prepareTopology(java.lang.String)
	 */
	public void prepareTopology(String idmAddress) {
	    try {
	        AccessPoint.getInstance().prepareTopology(idmAddress);
        } catch (Exception e) {
            log.error("DM prepareTopology failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
	    try {
	        return AccessPoint.getInstance().getIdentifiers(portName, linkBodId);
        } catch (Exception e) {
            log.error("DM getIdentifiers failed: " + e.getMessage());
            log.debug("Exception info: ", e);
            return null;
        }
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#restart()
	 */
	public void restart() {
	    try {
	        AccessPoint.getInstance().restart();
        } catch (Exception e) {
            log.error("DM restart failed: " + e.getMessage());
            log.debug("Exception info: ", e);
        }
	}
}
