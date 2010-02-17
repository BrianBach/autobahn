/**
 * 
 */
package net.geant.autobahn.idm2dm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michal
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="OversubsribedException", propOrder={
		"failedLink"
})
public class OversubscribedException extends Exception {

	private static final long serialVersionUID = 5738299375565692660L;
	private String failedLink = null;
	
	public OversubscribedException() {
		super();
	}

	public OversubscribedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public OversubscribedException(String arg0, String linkId) {
		super(arg0);
		failedLink = linkId;
	}

	public OversubscribedException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @return the failedLink
	 */
	public String getFailedLink() {
		return failedLink;
	}

	/**
	 * @param failedLink the failedLink to set
	 */
	public void setFailedLink(String failedLink) {
		this.failedLink = failedLink;
	}
}
