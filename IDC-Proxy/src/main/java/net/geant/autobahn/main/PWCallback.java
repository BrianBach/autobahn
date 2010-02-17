package net.geant.autobahn.main;

import org.apache.ws.security.WSPasswordCallback;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * @author Michal
 *
 */
public class PWCallback implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];

                if (pc.getUsage() == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN) {
                    System.out.println("usage: unknown");
                    throw new UnsupportedCallbackException(callbacks[i],
                            "check failed");
                }
                pc.setPassword("bahn2auto");
            } else {
                throw new UnsupportedCallbackException(callbacks[i],
                        "Unrecognized Callback");
            }
        }
    }
}
