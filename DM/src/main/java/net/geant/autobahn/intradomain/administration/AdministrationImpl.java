package net.geant.autobahn.intradomain.administration;

import java.util.List;

import javax.jws.WebService;

import net.geant.autobahn.intradomain.AccessPoint;
import net.geant.autobahn.intradomain.IntradomainTopology;

/**
 * @author Michal
 */

@WebService(name = "DmAdministration", serviceName = "DmAdministrationService",
        portName = "DmAdministrationPort",
        targetNamespace = "http://administration.intradomain.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.intradomain.administration.Administration")
public class AdministrationImpl implements Administration {

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		return AccessPoint.getInstance().getProperties();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {

		AccessPoint.getInstance().setProperties(properties);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.administration.Administration#setTopology(net.geant.autobahn.intradomain.IntradomainTopology)
	 */
	public void setTopology(IntradomainTopology topology) {
		AccessPoint.getInstance().setTopology(topology);
	}
	
}
