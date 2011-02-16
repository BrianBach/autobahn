/*
 * IntraTopologySaver.java
 *
 * 2007-04-03
 */
package net.geant.autobahn.testplatform.configuration;

import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.utils.IntraTopologyBuilder;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class IntraTopologyBuilder2 extends IntraTopologyBuilder {

    private Domain domain = null;
    
    public IntraTopologyBuilder2(boolean useDb) {
    	super(useDb);
    }
    
    public GenericInterface createRouterIf(String nodeID, String interfaceID, String publicId, long bandwidth) {
    	registerPublicId(interfaceID, publicId);
    	return createRouterIf(nodeID, interfaceID, domain.getDomainId(), false, bandwidth, null);
    }
    
    public void setDomain(Domain domain) {
    	this.domain = domain;
    }

	public Domain getDomain() {
		return domain;
	}
	
}
