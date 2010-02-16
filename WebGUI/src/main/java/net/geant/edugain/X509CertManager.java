/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant.edugain;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @version $Id$
 * @author Maciej Labedzki <labedzki@man.poznan.pl>
 * 
 */
public class X509CertManager {

	private CertificateFactory certFactory;

	public X509CertManager() {

		try {
			this.certFactory = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}

	public X509Certificate loadCertificate(InputStream in)
			throws CertificateException {

		return (X509Certificate) certFactory.generateCertificate(in);
	}

}
