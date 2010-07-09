/**
 * 
 */
package net.geant.autobahn.proxy;

import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.WebParam;

import java.io.IOException;
import java.util.List;

/**
 * Coresponds to the OSCARSNotify interface, however only methods
 * relevant to Autobahn are implemented
 * @author Micha³
 */
@WebService(targetNamespace = "http://proxy.autobahn.geant.net/", name = "ProxyNotify")
public interface ProxyNotify {
	
	void notification(@WebParam(name="reservationId")String reservationId, 
			@WebParam(name="reservationState")String reservationState) throws IOException;	
	
}
