package net.geant.autobahn.useraccesspoint;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Web service methods for placing new service, 
 * query reservations and cancelling them
 * @author Michal
 */

@WebService(targetNamespace = "http://useraccesspoint.autobahn.geant.net/", name = "UserAccessPoint")
public interface UserAccessPoint {
	
    /**
     * Returns all domains in the global topology (not including IDCP clouds)
     * @return domains
     */
    @WebResult(name = "Domains")
    String[] getAllDomains();

    /**
     * Returns all non-client domains in the global topology (not including IDCP clouds)
     * @return domains
     */
    @WebResult(name = "Domains")
    String[] getAllDomains_NonClient();

    /**
     * Returns all links in the global topology (not including links in IDCP clouds)
     * @return links
     */
    @WebResult(name = "Links")
    String[] getAllLinks();

    /**
     * Returns all links in the global topology that do not attach to 
     * a client domain (not including links in IDCP clouds)
     * @return links
     */
    @WebResult(name = "Links")
    String[] getAllLinks_NonClient();

	/**
     * Returns all client ports in the global topology
     * Does not include IDCP ports
     * @return client ports
	 * @throws UserAccessPointException 
     */
	@WebResult(name="PortTypes")
	List<PortType> getAllClientPorts() throws UserAccessPointException;
	
	/**
     * Returns client ports the are connected to the called domain
     * @return client ports
     */
	@WebResult(name="PortTypes")
	List<PortType> getDomainClientPorts();
	
    /**
     * Returns actual IDCP ports in the global topology
     * @return IDCP ports
     */
    @WebResult(name="PortTypes")
    List<PortType> getIdcpPorts();
    
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
