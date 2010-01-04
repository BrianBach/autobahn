/**
 * 
 */
package net.geant2.jra3.tool;

import javax.xml.ws.WebFault;

/**
 * @author jacek
 *
 */
@WebFault(name = "ReservationNotFoundException", targetNamespace = "http://tool.jra3.geant2.net/")
public class ReservationNotFoundException extends Exception {

	private static final long serialVersionUID = -2092304051574708713L;

	/**
	 * 
	 */
	public ReservationNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ReservationNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ReservationNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ReservationNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
