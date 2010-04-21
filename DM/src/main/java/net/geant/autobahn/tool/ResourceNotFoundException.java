/**
 * 
 */
package net.geant.autobahn.tool;

/**
 * @author jacek
 *
 */
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 7873252770998783322L;

	/**
	 * 
	 */
	public ResourceNotFoundException() {
	}

	/**
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
