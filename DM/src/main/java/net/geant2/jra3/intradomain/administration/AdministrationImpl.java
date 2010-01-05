package net.geant2.jra3.intradomain.administration;

import java.util.List;

import javax.jws.WebService;

import net.geant2.jra3.intradomain.AccessPoint;
import net.geant2.jra3.intradomain.IntradomainTopology;

/**
 * @author Michal
 */

@WebService(name = "DmAdministration", serviceName = "DmAdministrationService",
        portName = "DmAdministrationPort",
        targetNamespace = "http://administration.intradomain.jra3.geant2.net/", 
        endpointInterface = "net.geant2.jra3.intradomain.administration.Administration")
public class AdministrationImpl implements Administration {

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		return AccessPoint.getInstance().getProperties();
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {

		AccessPoint.getInstance().setProperties(properties);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.administration.Administration#setTopology(net.geant2.jra3.intradomain.IntradomainTopology)
	 */
	public void setTopology(IntradomainTopology topology) {
		AccessPoint.getInstance().setTopology(topology);
	}
	
}
