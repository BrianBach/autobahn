/**
 * 
 */
package net.geant.autobahn.tool;

import javax.xml.ws.WebFault;

/**
 * @author jacek
 *
 */
@WebFault(name = "RequestException", targetNamespace = "http://tool.autobahn.geant.net/")
public class RequestException extends Exception {

	private static final long serialVersionUID = -4284923200916533879L;

	/**
	 * 
	 */
	public RequestException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public RequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public RequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
