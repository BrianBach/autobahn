package net.geant2.jra3.idm2dm;

import javax.jws.WebService;

import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.intradomain.AccessPoint;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.reservation.ReservationParams;
import net.geant2.jra3.reservation.TimeRange;

/**
 * Implementation of the Idm2Dm web service. Delegates the messages the an
 * AccessPoint instance.
 * 
 * @author Michal
 */
@WebService(name="Idm2Dm", serviceName="Idm2DmService",
		portName="Idm2DmPort", 
		targetNamespace="http://idm2dm.jra3.geant2.net/",
		endpointInterface="net.geant2.jra3.idm2dm.Idm2Dm")
public class Idm2DmImpl implements Idm2Dm {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant2.jra3.network.Link[], net.geant2.jra3.reservation.ReservationParams)
	 */
	public void addReservation(String arg0, Link[] arg1, ReservationParams arg2)
			throws ConstraintsAlreadyUsedException {

		AccessPoint.getInstance().addReservation(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#checkResources(net.geant2.jra3.network.Link[], net.geant2.jra3.reservation.ReservationParams)
	 */
	public DomainConstraints checkResources(Link[] arg0, ReservationParams arg1)
			throws OversubscribedException {

		return AccessPoint.getInstance().checkResources(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String arg0) {

		AccessPoint.getInstance().removeReservation(arg0);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#checkModification(java.lang.String, net.geant2.jra3.reservation.TimeRange)
	 */
	public boolean checkModification(String resId, TimeRange time) {
		return AccessPoint.getInstance().checkModification(resId, time);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#modifyReservation(java.lang.String, net.geant2.jra3.reservation.TimeRange)
	 */
	public void modifyReservation(String resId, TimeRange time) {
		AccessPoint.getInstance().modifyReservation(resId, time);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#prepareTopology(java.lang.String)
	 */
	public void prepareTopology(String idmAddress) {
		AccessPoint.getInstance().prepareTopology(idmAddress);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#getIdentifiers(java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String portName, String linkBodId) {
		return AccessPoint.getInstance().getIdentifiers(portName, linkBodId);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#restart()
	 */
	public void restart() {
		AccessPoint.getInstance().restart();
	}
}
