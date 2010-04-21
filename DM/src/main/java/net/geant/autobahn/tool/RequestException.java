/**
 * 
 */
package net.geant.autobahn.tool;

/**
 * @author jacek
 *
 */
public class RequestException extends Exception {

	private static final long serialVersionUID = -4284923200916533879L;

	/**
	 * 
	 */
	public RequestException() {
	}

	/**
	 * @param message
	 */
	public RequestException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RequestException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
