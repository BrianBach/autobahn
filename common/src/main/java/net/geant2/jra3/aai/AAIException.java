package net.geant2.jra3.aai;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AAIException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public AAIException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public AAIException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
