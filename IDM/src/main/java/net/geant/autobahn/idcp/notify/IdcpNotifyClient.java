/**
 * 
 */
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.JAXBElement;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;

import net.geant.autobahn.idcp.IdcpException;
import net.geant.autobahn.idcp.OSCARS_Service;

/**
 * 
 * @author PCSS
 */
public class IdcpNotifyClient {
	
	private final String url;
	private final OSCARSNotify idcpNotify;
	
	public IdcpNotifyClient(String url) {
		
		this.url = url;
		this.idcpNotify = new OSCARSNotify_Service(url).getOSCARSNotify();
		
	}
	
	private EndpointReferenceType createEndpointReference() {
		
		EndpointReferenceType endpoint = new EndpointReferenceType();
		endpoint.setMetadata(null);
		AttributedURIType address = new AttributedURIType();
		address.setValue("");
		endpoint.setAddress(address);
		ReferenceParametersType ref = new ReferenceParametersType();
		ref.setPublisherRegistrationId("");
		ref.setSubscriptionId("");
		endpoint.setReferenceParameters(ref);
		return endpoint;
	}
	
	public void subscribe() throws IdcpException {
		
		Subscribe request = new Subscribe();
		request.setConsumerReference(createEndpointReference());
		FilterType filter = new FilterType();
		
		
		request.setFilter(filter);
		request.setInitialTerminationTime(null);
		request.setSubscriptionPolicy(null);
		
		try {
			SubscribeResponse response = idcpNotify.subscribe(request);
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
	
	public void unsubscribe() throws IdcpException {

		Unsubscribe request = new Unsubscribe();
		request.setSubscriptionReference(createEndpointReference());
		
		try {
			UnsubscribeResponse response = idcpNotify.unsubscribe(request);
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
	
	public void renew() throws IdcpException {
		
		Renew request = new Renew();
		request.setSubscriptionReference(createEndpointReference());
		
		try {
			RenewResponse response = idcpNotify.renew(request);
		} catch (Exception e) {
			throw new IdcpException(e.getMessage());
		}
	}
}
