package net.geant2.jra3.useraccesspoint;

import javax.jws.WebResult;

import net.geant2.jra3.idm.AccessPoint;

/**
 * @author Michal
 */

@javax.jws.WebService(name = "UserAccessPoint", serviceName = "UserAccessPointService",
        portName = "UserAccessPointPort",
        targetNamespace = "http://useraccesspoint.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.useraccesspoint.UserAccessPoint")
public class UserAccessPointImpl implements UserAccessPoint {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#cancelService(java.lang.String)
	 */
	public void cancelService(String serviceID) throws UserAccessPointException {

		AccessPoint.getInstance().cancelService(serviceID);
	}

    /* (non-Javadoc)
     * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#getAllDomains()
     */
    public String[] getAllDomains() {
        return AccessPoint.getInstance().getAllDomains();
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#getAllLinks()
     */
    public String[] getAllLinks() {
        return AccessPoint.getInstance().getAllLinks();
    }

    /* (non-Javadoc)
	 * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#getAllClientPorts()
	 */
	public String[] getAllClientPorts() {
		return AccessPoint.getInstance().getAllClientPorts();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#getDomainClientPorts()
	 */
	public String[] getDomainClientPorts() {
		return AccessPoint.getInstance().getDomainClientPorts();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#queryService(java.lang.String)
	 */
	public ServiceResponse queryService(String serviceID)
			throws UserAccessPointException {
		
		return AccessPoint.getInstance().queryService(serviceID);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.useraccesspoint.UserAccessPoint#submitService(net.geant2.jra3.useraccesspoint.ServiceRequest)
	 */
	public String submitService(ServiceRequest request)
			throws UserAccessPointException {
		
		return AccessPoint.getInstance().submitService(request);
	}

	public boolean checkReservationPossibility(ReservationRequest req) throws UserAccessPointException {
		return AccessPoint.getInstance().checkReservationPossibility(req);
	}
	
	public String submitServiceAndRegister(ServiceRequest request, String url)
			throws UserAccessPointException {
		return AccessPoint.getInstance().submitServiceAndRegister(request, url);
	}

	public void modifyReservation(ModifyRequest request) {
		
		AccessPoint.getInstance().modifyReservation(request);
	}

	public void registerCallback(String serviceID, String url) {
		AccessPoint.getInstance().registerCallback(serviceID, url);
	}
}
