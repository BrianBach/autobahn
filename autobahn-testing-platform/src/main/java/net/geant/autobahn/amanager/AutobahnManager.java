package net.geant.autobahn.amanager;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://amanager.autobahn.geant.net/", name = "AutobahnManager")
public interface AutobahnManager {

	@WebResult(name="services")
	public String[] getServices();
	
	public void unregisterService(@WebParam(name="name")String name);
	
	public void halt();
}
