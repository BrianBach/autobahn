/**
 * 
 */
package net.geant.autobahn.idm2dm;

/**
 * @author Michal
 */

public class ConstraintsAlreadyUsedException extends Exception {

	private static final long serialVersionUID = 6442911787786191272L;

	public ConstraintsAlreadyUsedException() {
		super();
	}

	public ConstraintsAlreadyUsedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConstraintsAlreadyUsedException(String message) {
		super(message);
	}

	public ConstraintsAlreadyUsedException(Throwable cause) {
		super(cause);
	}
}
