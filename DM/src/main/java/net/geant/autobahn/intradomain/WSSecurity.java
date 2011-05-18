package net.geant.autobahn.intradomain;

import java.util.Map;
import java.util.HashMap;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 * Enables safe ws communication
 * @author Michal
 */
public class WSSecurity {
	
	static final String USER = "autobahn";
	
	public static void setClientTimeout(Object clientInterface) {
		
		final int TIMEOUT = 36000 * 1000;
		
		Client client = ClientProxy.getClient(clientInterface);
		HTTPConduit http = (HTTPConduit)client.getConduit();
		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(TIMEOUT);
		policy.setReceiveTimeout(TIMEOUT);
		policy.setAllowChunking(false);
		http.setClient(policy);
	}
	
	public static void configureClient(Object clientInterface) {
		
		Client client = ClientProxy.getClient(clientInterface);
		Endpoint endpoint = client.getEndpoint();
		
		Map<String, Object> in = new HashMap<String, Object>();

		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(in);
		endpoint.getInInterceptors().add(wssIn);
		endpoint.getInInterceptors().add(new SAAJInInterceptor());

		Map<String, Object> out = new HashMap<String, Object>();
		//out.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		out.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
		out.put(WSHandlerConstants.USER, USER);
		//out.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		out.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
		out.put(WSHandlerConstants.SIG_PROP_FILE, "etc/security/security.properties");
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(out);
		endpoint.getOutInterceptors().add(wssOut);
		endpoint.getOutInterceptors().add(new SAAJOutInterceptor());
	}

	public static void configureServer(Endpoint endpoint) {
		
		Map<String, Object> in = new HashMap<String, Object>();
		//in.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		//in.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		//in.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
		in.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE);
		in.put(WSHandlerConstants.SIG_PROP_FILE, "etc/security/security.properties");
		
		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(in);
		endpoint.getInInterceptors().add(wssIn);
		endpoint.getInInterceptors().add(new SAAJInInterceptor());

		Map<String, Object> out = new HashMap<String, Object>();
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(out);
		endpoint.getOutInterceptors().add(wssOut);
		endpoint.getOutInterceptors().add(new SAAJOutInterceptor());
	}
}
