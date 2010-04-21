/**
 * 
 */
package net.geant.autobahn.tool;

/**
 * @author jacek
 *
 */
public class SystemException extends Exception {

	private static final long serialVersionUID = -3405644786822178970L;

	/**
	 * 
	 */
	public SystemException() {
	}

	/**
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

}
