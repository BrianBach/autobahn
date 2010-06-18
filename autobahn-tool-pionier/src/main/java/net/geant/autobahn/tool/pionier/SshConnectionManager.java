/**
 * 
 */
package net.geant.autobahn.tool.pionier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ch.ethz.ssh2.Connection;


/**
 * @author Michal
 *
 */
public class SshConnectionManager {

	public Connection GetSshConnection(SshConnectionParameters sshConnectionParameters) {
		
	    try
		{
			/* Create a connection*/

			Connection conn = new Connection(sshConnectionParameters.sshHostAddress);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(sshConnectionParameters.sshUsername, sshConnectionParameters.sshPassword);
			if (isAuthenticated == false) {
				System.out.println("Authentication failed.");
				return null;
			}
			else
				return conn;
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			return null;
		}
	}
}
