package net.geant2.jra3.edugain;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Holds a password for secured communication.
 * 
 * @author Michal
 *
 */
public class ClientPasswordCallback implements CallbackHandler {

	final String PASSWORD = "edugain";

	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@SuppressWarnings("deprecation")
	public void handle(Callback[] arg0) throws IOException,	UnsupportedCallbackException {
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(PASSWORD);

		
		
	}
}
