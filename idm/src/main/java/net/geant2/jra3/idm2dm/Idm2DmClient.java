package net.geant2.jra3.idm2dm;

import net.geant2.jra3.constraints.DomainConstraints;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;
import net.geant2.jra3.reservation.ReservationParams;
import net.geant2.jra3.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * Client for sending messages to DM
 * @author Michal
 */

public class Idm2DmClient implements Idm2Dm {
	
	static private Logger log = Logger.getLogger(Idm2DmClient.class);
	
	private Idm2Dm idm2dm;
	
	public Idm2DmClient(String endPoint) {
		Idm2DmService service = new Idm2DmService(endPoint);
		idm2dm = service.getIdm2DmPort();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant2.jra3.network.Link[], net.geant2.jra3.reservation.ReservationParams)
	 */
	public void addReservation(String resId, Link[] links, ReservationParams arg2)
			throws ConstraintsAlreadyUsedException, OversubscribedException {
		log.info("IDM -> DM: add reservation " + resId + " start");
		idm2dm.addReservation(resId, links, arg2);
		log.info("IDM -> DM: add reservation " + resId + "end");
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#checkResources(net.geant2.jra3.network.Link[], net.geant2.jra3.reservation.ReservationParams)
	 */
	public DomainConstraints checkResources(Link[] links, ReservationParams arg1)
			throws OversubscribedException {
		log.info("IDM -> DM: checking resources");
		return idm2dm.checkResources(links, arg1);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resId) {
		log.info("IDM -> DM: removing reservation " + resId + " start");
		idm2dm.removeReservation(resId);
		log.info("IDM -> DM: removing reservation " + resId + " end");
	}

	public boolean checkModification(String resId, TimeRange time) {
		return idm2dm.checkModification(resId, time);
	}

	public void modifyReservation(String resId, TimeRange time) {
		idm2dm.modifyReservation(resId, time);
	}
	
	public void prepareTopology(String idmAddress) {
		idm2dm.prepareTopology(idmAddress);
	}

	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
		return idm2dm.getIdentifiers(portName, bodId);
	}

	public void restart() {
		idm2dm.restart();
	}
}
