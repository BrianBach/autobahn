package net.geant.autobahn.intradomain.administration;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant.autobahn.intradomain.IntradomainTopology;


/**
 * @author Michal
 */

@WebService(targetNamespace = "http://administration.intradomain.autobahn.geant.net/", name = "DmAdministration")
public interface DmAdministration {
	
	@WebResult(name="Properties")
	public List<KeyValue> getProperties();
	
	public void setProperties(@WebParam(name="properties")List<KeyValue> properties);
	
	public void setTopology(IntradomainTopology topology);
}
