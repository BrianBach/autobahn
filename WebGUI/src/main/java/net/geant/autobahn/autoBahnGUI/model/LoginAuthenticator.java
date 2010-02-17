package net.geant.autobahn.autoBahnGUI.model;

/**
 * Models the authenticator information
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class LoginAuthenticator {
	/**
	 * Identifier of authenticator
	 */
	String authenticator="";
	
	/**
	 * Gets authenticator identifier
	 * @return authenticator identifier
	 */
	public String getAuthenticator() {
		return authenticator;
	}
	/**
	 * Gets authenticator identifier
	 * @return authenticator identifier
	 */
	public void setAuthenticator(String authenticator) {
		this.authenticator = authenticator;
	}
}
