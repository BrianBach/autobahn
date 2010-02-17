package net.geant.autobahn.idm;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * @author Michal
 *
 */
public class ServerPasswordCallback implements CallbackHandler {

	final String PASSWORD = "jra3prototype";
	
	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	public void handle(Callback[] arg0) throws IOException,
			UnsupportedCallbackException {
		
		WSPasswordCallback pc = (WSPasswordCallback)arg0[0];
		if (pc.getIdentifer().equals("autobahn"))
			pc.setPassword(PASSWORD);
	}
}
