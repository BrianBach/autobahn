package net.geant.autobahn.administration;

import java.util.List;

import javax.jws.WebService;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.network.Link;

/**
 * @author Michal
 */

@WebService(name = "Administration", serviceName = "AdministrationService",
        portName = "AdministrationPort",
        targetNamespace = "http://administration.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.administration.Administration")
public class AdministrationImpl implements Administration {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getLog(boolean)
	 */
	public String getLog(boolean all) {

		return AccessPoint.getInstance().getLog(all);
	}

    /* (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getStatistics(boolean)
     */
    public StatisticsType getStatistics(boolean all) {

        return AccessPoint.getInstance().getStatistics(all);
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		return AccessPoint.getInstance().getProperties();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
	 */
	public ReservationType getReservation(String resID) {
	
		return AccessPoint.getInstance().getReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getTopology()
	 */
	public List<Link> getTopology() {

		return AccessPoint.getInstance().getTopology();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setTopology(java.util.List)
	 */
	public void setTopology(List<Link> links) {
		
		AccessPoint.getInstance().setTopology(links);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {

		AccessPoint.getInstance().setProperties(properties);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getServices()
	 */
	public List<ServiceType> getServices() {
		return AccessPoint.getInstance().getServices();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getService(java.lang.String)
	 */
	public ServiceType getService(String serviceId) {
		return AccessPoint.getInstance().getService(serviceId); 
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getStatus()
	 */
	public Status getStatus() {

		return AccessPoint.getInstance().getStatus();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#cancelAllServices()
	 */
	public void cancelAllServices() {
		
		AccessPoint.getInstance().cancelAllServices();
	}

    @Override
    public void restart() {

        AccessPoint.getInstance().restart();
    }

    @Override
    public void handleTopologyChange(boolean deleteReservations)
            throws AdministrationException {
        AccessPoint.getInstance().handleTopologyChange(deleteReservations);
    }
}
