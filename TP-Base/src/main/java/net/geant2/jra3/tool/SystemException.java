/**
 * 
 */
package net.geant2.jra3.tool;

import javax.xml.ws.WebFault;

/**
 * @author jacek
 *
 */
@WebFault(name = "SystemException", targetNamespace = "http://tool.jra3.geant2.net/")
public class SystemException extends Exception {

	private static final long serialVersionUID = -3405644786822178970L;

	/**
	 * 
	 */
	public SystemException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public SystemException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SystemException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
