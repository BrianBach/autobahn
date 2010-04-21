/**
 * 
 */
package net.geant.autobahn.tool;

/**
 * @author jacek
 *
 */
public class ReservationNotFoundException extends Exception {

	private static final long serialVersionUID = -2092304051574708713L;

	/**
	 * 
	 */
	public ReservationNotFoundException() {
	}

	/**
	 * @param message
	 */
	public ReservationNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ReservationNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ReservationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
