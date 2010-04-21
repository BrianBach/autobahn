package net.geant.autobahn.useraccesspoint;

/**
 * @author Michal
 */

public class UserAccessPointException extends Exception {

	private static final long serialVersionUID = -5264766983623752833L;

	/**
	 * 
	 */
	public UserAccessPointException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserAccessPointException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UserAccessPointException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UserAccessPointException(Throwable cause) {
		super(cause);
	}
}
