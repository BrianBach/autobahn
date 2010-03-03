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
public class ServerPasswordCallback implements CallbackHandler {

	private final static Logger log = Logger.getLogger(ServerPasswordCallback.class);
	private String PASSWORD;
	private String PASSWORD_PROPERTY = "org.apache.ws.security.crypto.merlin.keystore.password";
	private URL server;
	
	public ServerPasswordCallback (URL url) {
		
		this.server = url;
	}
	
	public ServerPasswordCallback (String path) {
		
		ClassLoader server = getClass().getClassLoader();
		this.server = server.getResource(path);
	}
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@SuppressWarnings("deprecation")
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		
		Properties properties = new Properties();
		
		try {
			properties.load(server.openStream());
			PASSWORD = properties.getProperty(PASSWORD_PROPERTY);

		} catch (FileNotFoundException e) {
			log.error("Server side security properties not found: " + e.getMessage());
		}
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(PASSWORD);

	}
}
