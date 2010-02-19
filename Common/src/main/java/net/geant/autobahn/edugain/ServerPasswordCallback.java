package net.geant.autobahn.edugain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class ServerPasswordCallback implements CallbackHandler {

	private final static Logger log = Logger.getLogger(ClientPasswordCallback.class);
	static public String SERVER_PROPERTIES = "etc/edugain/client-sec.properties";
	static public String PASSWORD_PROPERTY = "org.apache.ws.security.crypto.merlin.keystore.password";
	static public String PASSWORD = null;
	
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@SuppressWarnings("deprecation")
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(SERVER_PROPERTIES));
			PASSWORD = properties.getProperty(PASSWORD_PROPERTY);
						
		} catch (FileNotFoundException e) {
			log.error("Server side security properties not found: " + e.getMessage());
		}
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(PASSWORD);

	}
}
