package net.geant.autobahn.idm2dm;

import javax.jws.WebService;

import org.mortbay.log.Log;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.Idm2Dm;
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

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String arg0, Link[] arg1, ReservationParams arg2)
			throws ConstraintsAlreadyUsedException {

		AccessPoint.getInstance().addReservation(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkResources(net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public DomainConstraints[] checkResources(Link[] arg0, ReservationParams arg1)
			throws OversubscribedException, AAIException {

		return AccessPoint.getInstance().checkResources(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String arg0) {

		try {
			AccessPoint.getInstance().removeReservation(arg0);
		} catch (Exception e) {
			System.out.println(" ----- >>>>>>> Error when removing !!!");
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkModification(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public boolean checkModification(String resId, TimeRange time) {
		return AccessPoint.getInstance().checkModification(resId, time);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#modifyReservation(java.lang.String, net.geant.autobahn.reservation.TimeRange)
	 */
	public void modifyReservation(String resId, TimeRange time) {
		AccessPoint.getInstance().modifyReservation(resId, time);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#prepareTopology(java.lang.String)
	 */
	public void prepareTopology(String idmAddress) {
		AccessPoint.getInstance().prepareTopology(idmAddress);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
		return AccessPoint.getInstance().getIdentifiers(portName, linkBodId);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#restart()
	 */
	public void restart() {
		AccessPoint.getInstance().restart();
	}
}
