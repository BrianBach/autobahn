/**
 * 
 */
package net.geant.autobahn.idcp;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Sets password for a key
 * 
 * @author PCSS
 */
public final class KeyPasswordCallback implements CallbackHandler {

	private String keyPassword; 
	public KeyPasswordCallback(String keyPassword) {
		
		this.keyPassword = keyPassword;
	}
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@Override
	public void handle(Callback[] arg0) throws IOException,	UnsupportedCallbackException {
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		pc.setPassword(keyPassword);
	}
}
