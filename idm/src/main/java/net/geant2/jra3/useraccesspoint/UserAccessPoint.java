package net.geant2.jra3.useraccesspoint;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Web service methods for placing new service, 
 * query reservations and cancelling them
 * @author Michal
 */

@WebService(targetNamespace = "http://useraccesspoint.jra3.geant2.net/", name = "UserAccessPoint")
public interface UserAccessPoint {
	
	/**
     * Returns all client ports in the global topology
     * @return client ports
     */
	@WebResult(name="Ports")
	String[] getAllClientPorts();
	
	/**
     * Returns client ports the are connected to the called domain
     * @return client ports
     */
	@WebResult(name="Ports")
	String[] getDomainClientPorts();
	
	/**
	 * Submits service
	 * @param request
	 * @return
	 * @throws UserAccessPointException
	 */
	@WebResult(name="serviceID")
	String submitService(@WebParam(name="request")ServiceRequest request) throws UserAccessPointException;

	/**
	 * 
	 * @param res
	 * @return
	 */
	@WebResult(name="Possibility")
	boolean checkReservationPossibility(@WebParam(name="request")ReservationRequest res) throws UserAccessPointException;
	
	/**
	 * 
	 * @param request
	 * @param url
	 * @return
	 * @throws UserAccessPointException
	 */
	@WebResult(name="serviceID")
	String submitServiceAndRegister(@WebParam(name="request")ServiceRequest request, 
			@WebParam(name="url")String url) throws UserAccessPointException;
	
	/**
	 * Removes service
	 * @param serviceID
	 * @throws UserAccessPointException
	 */
	void cancelService(@WebParam(name="serviceID")String serviceID) throws UserAccessPointException;

	/**
	 * 
	 * @param resId
	 * @param sTime
	 * @param eTime
	 */
	void modifyReservation(@WebParam(name="request")ModifyRequest request);
	
	/**
	 * Retrieves information about service and its reservations
	 * @param serviceID
	 * @return
	 * @throws UserAccessPointException
	 */
	@WebResult(name="ServiceResponse")
	ServiceResponse queryService(@WebParam(name="serviceID")String serviceID) throws UserAccessPointException;
	
	/**
	 * 
	 * @param serviceID
	 * @param url
	 */
	public void registerCallback(@WebParam(name="serviceID")String serviceID, 
			@WebParam(name="url")String url);
}
