/**
 * 
 */
package net.geant.autobahn.proxy;

import java.io.IOException;

/**
 * Used to forward requests from OSCARSNotify to IDM
 * @author Micha³
 *
 */
public class ProxyNotifyClient implements ProxyNotify {

	
	private ProxyNotify rc = null;
    ProxyNotifyService service;
    
    public ProxyNotifyClient() {

        //TODO: Instead of hard-coded IDM read from Properties
        String endPoint = "http://150.140.8.14:8080/autobahn/proxy";//ProxyServlet.getProperties().getProperty("idm.address");
        if("none".equals(endPoint))
            return;
        service = new ProxyNotifyService(endPoint);
        rc = service.getProxyPort();
    }
    
    /**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of a ProxyNotify
     */
    public ProxyNotifyClient(String endPoint) {
        if("none".equals(endPoint))
            return;
        
        ProxyNotifyService service = new ProxyNotifyService(endPoint);
        rc = service.getProxyPort();
        
    }

	public void notification(String reservationId, String reservationState)
			throws IOException {

		rc.notification(reservationId, reservationState);
	}
}
