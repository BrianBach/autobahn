package net.geant.autobahn.aai;

/**
 * @author Michal
 */

public class AAIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004427864388061661L;

	/**
	 * 
	 */
	public AAIException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AAIException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public AAIException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AAIException(Throwable cause) {
		super(cause);
	}
}
