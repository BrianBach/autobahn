package net.geant2.jra3.intradomain.administration;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import net.geant2.jra3.intradomain.IntradomainTopology;


/**
 * @author Michal
 */

@WebService(targetNamespace = "http://administration.intradomain.jra3.geant2.net/", name = "DmAdministration")
public interface Administration {
	
	@WebResult(name="Properties")
	public List<KeyValue> getProperties();
	
	public void setProperties(@WebParam(name="properties")List<KeyValue> properties);
	
	public void setTopology(IntradomainTopology topology);
}
