package net.geant2.jra3.administration;

import java.util.List;
import javax.jws.WebService;
import net.geant2.jra3.idm.AccessPoint;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.reservation.Reservation;
import net.geant2.jra3.reservation.Service;

/**
 * @author Michal
 */

@WebService(name = "Administration", serviceName = "AdministrationService",
        portName = "AdministrationPort",
        targetNamespace = "http://administration.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.administration.Administration")
public class AdministrationImpl implements Administration {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getLog(boolean)
	 */
	public String getLog(boolean all) {

		return AccessPoint.getInstance().getLog(all);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		return AccessPoint.getInstance().getProperties();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getReservation(java.lang.String)
	 */
	public Reservation getReservation(String resID) {
	
		return AccessPoint.getInstance().getReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getTopology()
	 */
	public List<Link> getTopology() {

		return AccessPoint.getInstance().getTopology();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#setTopology(java.util.List)
	 */
	public void setTopology(List<Link> links) {
		
		AccessPoint.getInstance().setTopology(links);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {

		AccessPoint.getInstance().setProperties(properties);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getServices()
	 */
	public List<Service> getServices() {
		
		return AccessPoint.getInstance().getServices();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getService(java.lang.String)
	 */
	public Service getService(String serviceId) {

		return AccessPoint.getInstance().getService(serviceId);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getStatus()
	 */
	public Status getStatus() {

		return AccessPoint.getInstance().getStatus();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#cancelAllServices()
	 */
	public void cancelAllServices() {
		
		AccessPoint.getInstance().cancelAllServices();
	}
}
