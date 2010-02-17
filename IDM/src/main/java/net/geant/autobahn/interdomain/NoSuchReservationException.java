/**
 * 
 */
package net.geant.autobahn.interdomain;

/**
 * @author jacek
 *
 */
public class NoSuchReservationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567003724450615897L;

	public NoSuchReservationException() {
		super();
	}

	public NoSuchReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchReservationException(String message) {
		super(message);
	}

	public NoSuchReservationException(Throwable cause) {
		super(cause);
	}

}
