/**
 * 
 */
package net.geant.autobahn.idcp;

/**
 * Any idcp-related method throws this exception
 * @author PCSS
 */
public class IdcpException extends Exception {

	/**
	 * 
	 */
	public IdcpException() {
	}

	/**
	 * @param arg0
	 */
	public IdcpException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IdcpException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IdcpException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
