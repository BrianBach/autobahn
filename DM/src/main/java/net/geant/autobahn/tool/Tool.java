/**
 * 
 */
package net.geant.autobahn.tool;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebParam;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.reservation.ReservationParams;

/**
 * Interface between DomainManager and Network managment proxy to create and
 * manage network circuits.
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

@WebService(targetNamespace = "http://tool.autobahn.geant.net/", name = "Tool")
public interface Tool {

	/**
	 * Request to create the circuit consisting of specified links. Circuit is
	 * identified by reservation identifier (resID parameter). Circuit
	 * parameters (such as capacity) are passed in <code>params</code>
	 * parameter.
	 * 
	 * @param resID
	 *            String Identifier of the circuit
	 * @param links
	 *            List of <code>GenericLink</code> objects that were chosen to
	 *            be reserved in the domain.
	 * @param params
	 *            Reservation parameters (such as capacity in bps)
	 * @throws AAIException
	 *             when Authorization fails
	 * @throws RequestException
	 *             When the request is in wrong format or some important
	 *             information is missing.
	 * @throws SystemException
	 *             When network proxy is unable to bring the circuit up
	 * @throws ResourceNotFoundException
	 *             When request concerns a device that doesn't exist
	 */
	public void addReservation(@WebParam(name="resID")String resID, 
			@WebParam(name="links")List<GenericLink> links,
			@WebParam(name="params")ReservationParams params) throws AAIException, RequestException,
			SystemException, ResourceNotFoundException;

	/**
	 * Removes the reservation with given identifier.
	 * 
	 * @param resID
	 *            String identifier of the reservation to be removed.
	 * @param links
	 *            List of <code>GenericLink</code> objects that were chosen to
	 *            be reserved in the domain.
	 * @param params
	 *            Reservation parameters (such as capacity in bps)
	 * @throws AAIException
	 *             when Authorization fails
	 * @throws RequestException
	 *             When the request is in wrong format or some important
	 *             information is missing.
	 * @throws SystemException
	 *             When network proxy is unable to bring the circuit up
	 * @throws ReservationNotFoundException
	 *             When reservation with given identifier doesn't exist in the
	 *             proxy module.
	 */
	public void removeReservation(@WebParam(name="resID")String resID, 
			@WebParam(name="links")List<GenericLink> links,
			@WebParam(name="params")ReservationParams params) throws AAIException, RequestException,
			SystemException, ReservationNotFoundException;
}
