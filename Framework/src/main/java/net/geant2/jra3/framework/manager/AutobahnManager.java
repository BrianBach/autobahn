package net.geant2.jra3.framework.manager;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://amanager.jra3.geant2.net/", name = "AutobahnManager")
public interface AutobahnManager {

	@WebResult(name="services")
	public String[] getServices();
	
	public void unregisterService(@WebParam(name="name")String name);
	
	public void halt();
}
