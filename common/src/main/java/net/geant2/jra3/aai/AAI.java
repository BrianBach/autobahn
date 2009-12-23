package net.geant2.jra3.aai;


/**
 * @author Michal
 */

public interface AAI {
	
    /**
     * Confirms that user identity is valid
     * @param user <code>User</code> to be authenticated
     * @throws AAIException when authentication fails
     */
    public void authenticate(String login, String password) throws AAIException;
    
    /**
     * Confirms that user is allowed to do given action
     * @param user <code>User</code> to be authorized
     * @throws AAIException when authorization fails
     */
    public void authorize(String login, String password) throws AAIException;

}
