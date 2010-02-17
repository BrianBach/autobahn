/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant.autobahn.edugain;

import org.apache.cxf.interceptor.Fault;

/**
 * @version $Id: CnisXFireFault.java 2919 2009-02-04 09:30:02Z psnc.labedzki $
 *
 * @author Maciej Labedzki <labedzki@man.poznan.pl>
 *
 */
public class EdugainCxfFault extends Fault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	EdugainCxfFault(Throwable cause, String message) {
		super(cause);
		setMessage(message);
	}
}
