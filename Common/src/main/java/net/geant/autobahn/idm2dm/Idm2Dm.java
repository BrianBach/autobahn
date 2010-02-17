/**
 * 
 */
package net.geant.autobahn.idm2dm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;

/**
 * Interface describes messages that are being sent by IDM to the corresponding
 * DM.
 * 
 * @author Michal
 */
@WebService(targetNamespace = "http://idm2dm.autobahn.geant.net/", name = "Idm2Dm")
public interface Idm2Dm {

	/**
	 * Checks for local domain paths that can be configured regarding provided
	 * interdomain path and defines constraints for potential paths. Updates
	 * collected constraints passed via <code>constraints</code> parameter.
	 * 
	 * @param links
	 *            Array of links that were chosen for the reservation
	 * @param params
	 *            Parameters of the reservation
	 * @return constraints available for the reservation in the domain
	 * @throws OversubscribedException
	 *             When reservation cannot be done due to exceeding capacity in
	 *             the given time period
	 */
	@WebMethod
	@WebResult(name="DomainConstraints")
	DomainConstraints checkResources(Link[] links,
			ReservationParams params) throws OversubscribedException;

	/**
	 * Reserves capacity of link identified by linkId. Reservation of required
	 * capacity is made for period: reservation startTime inclusive -
	 * reservation endTime exclusive. Values are given in the params parameter.
	 * 
	 * @param resID
	 *            String identifier of the reservation
	 * @param links
	 *            Array of links that were chosen for the reservation
	 * @param params
	 *            Reservation parameters containing reservation details (start
	 *            time, end time, capacity, network constraints)
	 * @throws OversubscribedException
	 *             When reservation cannot be done due to exceeding capacity in
	 *             the given time period
	 * @throws ConstraintsAlreadyUsedException
	 *             When selected network constraints are already reserved for
	 *             another reservation/
	 */
	@WebMethod
    void addReservation(@WebParam(name="resID")String resID, 
    		@WebParam(name="links")Link[] links,
            @WebParam(name="params")ReservationParams params) throws OversubscribedException,
            ConstraintsAlreadyUsedException;

	/**
	 * Checks if it is possible to change the time period of an existing
	 * reservation.
	 * 
	 * @param resId
	 *            String identifier of a reservation
	 * @param time
	 *            New time period to be checked
	 * @return boolean whether the modification is possible
	 */
	@WebMethod
	@WebResult(name="modificationPossible")
	boolean checkModification(@WebParam(name="resID")String resId, 
			@WebParam(name="time")TimeRange time);

	/**
	 * Changes the time period of an existing reservation.
	 * 
	 * @param resId
	 *            String identifier of a reservation
	 * @param time
	 *            New time period to be checked
	 */
	@WebMethod
	void modifyReservation(@WebParam(name="resID")String resId, 
			@WebParam(name="time")TimeRange time);
	
    /**
     * Removes a reservation identified by resId.
     * 
     * @param resID
     *            Identifier of the reservation to be removed
     */
	@WebMethod
    void removeReservation(@WebParam(name="resID")String resID);

	/**
	 * Informs DM that the IDM is connected to other IDM instances and is ready
	 * to mediate in the abstraction process. DM is also informed by the IDM
	 * that it's ready to fetch the abstract link from the domain.
	 * 
	 * @param idmAddress
	 */
	@WebMethod
    void prepareTopology(String idmAddress);
	
	/**
	 * Restarts DM and clears its state.
	 */
	@WebMethod
	void restart();

	/**
	 * IDM asks for identifiers of link, port and node connected to a specified
	 * port. Part of topology abstraction process.
	 * 
	 * @param portName
	 *            String name of the port
	 * @param linkBodId
	 *            String abstract identifier assigned to the link in the other
	 *            domain
	 * @return Object containing requested identifiers
	 */
	@WebMethod
	LinkIdentifiers getIdentifiers(@WebParam(name="portName")String portName, 
			@WebParam(name="linkBodId")String linkBodId);
}
