/**
 * 
 */
package net.geant.autobahn.tool.pionier;

/**
 * @author Michal
 *
 */

public class SshConnectionParameters {
	
	protected String sshHostAddress;
	protected String sshUsername;
	protected String sshPassword;
	
	SshConnectionParameters(String sshtHostAddress,
							String sshtUsername,
							String sshPassword) {
		
		this.sshHostAddress = sshtHostAddress;
		this.sshUsername = sshtUsername;
		this.sshPassword = sshPassword;
	
	}
}
