package net.geant.autobahn.edugain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;

/**
 * Holds a password for secured communication.
 * 
 * @author Michal
 *
 */
public class SecurityPasswordCallback implements CallbackHandler {

	private final static Logger log = Logger.getLogger(SecurityPasswordCallback.class);
	private String PASSWORD;
	private String PASSWORD_PROPERTY = "org.apache.ws.security.crypto.merlin.keystore.password";
	private URL security;
	
	public SecurityPasswordCallback (URL url) {
		
		this.security = url;
	}
	
	public SecurityPasswordCallback (String path) {
		
		ClassLoader securityLoader = getClass().getClassLoader();
		this.security= securityLoader.getResource(path);
	}
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@SuppressWarnings("deprecation")
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		
		Properties properties = new Properties();
		
		try {
			properties.load(security.openStream());
			PASSWORD = properties.getProperty(PASSWORD_PROPERTY);

		} catch (FileNotFoundException e) {
			log.error("Security properties not found: " + e.getMessage());
		}
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(PASSWORD);

	}
}
