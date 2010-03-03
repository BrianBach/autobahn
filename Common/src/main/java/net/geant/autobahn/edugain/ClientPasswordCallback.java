package net.geant.autobahn.edugain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;
import java.util.Properties;

/**
 * Holds a password for secured communication.
 * 
 * @author Michal
 *
 */
public class ClientPasswordCallback implements CallbackHandler {

	private final static Logger log = Logger.getLogger(ClientPasswordCallback.class);
	private String PASSWORD;
	private String PASSWORD_PROPERTY = "org.apache.ws.security.crypto.merlin.keystore.password";
	public URL client;

	
	public ClientPasswordCallback (URL url) {
		
		this.client = url;
	}
	
	public ClientPasswordCallback (String path) {
		
		ClassLoader client = getClass().getClassLoader();
		this.client = client.getResource(path);
	}
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@SuppressWarnings("deprecation")
	public void handle(Callback[] arg0) throws IOException,	UnsupportedCallbackException {
		
		Properties properties = new Properties();
		
		try {
			properties.load(client.openStream());
			PASSWORD = properties.getProperty(PASSWORD_PROPERTY);

		} catch (FileNotFoundException e) {
			log.error("Client side security properties not found: " + e.getMessage());
		}
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(PASSWORD);		
		
	}
}
