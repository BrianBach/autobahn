
package net.geant2.cnis.autobahn;

import net.geant2.cnis.autobahn.xml.AutobahnToCnisRequest;
import net.geant2.cnis.autobahn.xml.CnisToAutobahnResponse;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Mon Mar 02 16:34:30 CET 2009
 * Generated source version: 2.0.3-incubator
 * 
 */

public final class CnisClient {

	private CnisService cnis;
	
	public CnisClient(String endPoint) {
		if(endPoint.equalsIgnoreCase("none"))
			return;

		cnis = new CnisService(endPoint);
	}
	
	public CnisToAutobahnResponse getIntradomainTopology() {
		AutobahnToCnisRequest req = new AutobahnToCnisRequest();
		
		return cnis.getCnisHttpPort().getTopology(req);
	}

}
