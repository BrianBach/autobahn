/**
 * 
 */
package net.geant2.jra3.dm2idm;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.LinkIdentifiers;

/**
 * Interface desribes communication between DM and IDM instances.
 * 
 * @author Michal
 */
@WebService(targetNamespace = "http://dm2idm.jra3.geant2.net/", name = "Dm2Idm")
public interface Dm2Idm {

	/**
	 * Notifies IDM about the start of a reservation.
	 * 
	 * @param resID String Identifier of a reservation
	 * @param success boolean whether activation has succeeded in the domain
	 */
	@WebMethod
	public void activate(@WebParam(name="resID")String resID, 
			@WebParam(name="success")boolean success);
	
	/**
	 * Notifies IDM about the finish of a reservation.
	 * 
	 * @param resID String Identifier of a reservation
	 * @param success boolean whether deactivation has succeeded in the domain
	 */
	@WebMethod
	public void finish(@WebParam(name="resID")String resID, 
			@WebParam(name="success")boolean success);
	
	/**
	 * Injects to the IDM abstract links created during the conversion process.
	 * 
	 * @param links List of abstract links
	 */
	@WebMethod 
	public void injectAbstractLinks(@WebParam(name="links")List<Link> links);

	/**
	 * Asks IDM about identifiers of link, port and node connected to a
	 * specified port. IDM forwards the request to the specified IDM.
	 * 
	 * @param idmAddress String URL address of IDM from another domain
	 * @param portName String name of the port
	 * @param linkBodId String abstract identifier assigned to the link in this domain
	 * @return Object containing requested identifiers
	 */
	@WebMethod
	@WebResult(name="identifiers")
	public LinkIdentifiers getIdentifiers(@WebParam(name="idmAddress")String idmAddress, 
			@WebParam(name = "portName")String portName, @WebParam(name = "linkBodId")String linkBodId);

	/**
	 * Saves status of the reservation into database. Status is calculated by
	 * the Monitoring module.
	 * 
	 * @param res String reservation idenitifier
	 * @param st int code of the status
	 * @return boolean whether the operation succeded
	 */
    @WebMethod
    @WebResult(name="result")
    public boolean saveReservationStatusDB(@WebParam(name="res")String res,
            @WebParam(name="st")int st);

}
