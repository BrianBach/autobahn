/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant2.jra3.edugain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Properties;

import net.geant.edugain.validation.ComponentID;
import net.geant.edugain.validation.ValidationException;
import net.geant.edugain.validation.Validator;

import org.apache.commons.logging.Log;
import org.opensaml.common.SignableSAMLObject;


/**
 * This the EduGAIN module provided with the cNIS Web Services Framework.
 * 
 * @version $Id: Edugain.java 3054 2009-02-24 14:44:46Z psnc.labedzki $
 * @author Maciej Labedzki <labedzki@man.poznan.pl>
 * 
 */
public class Edugain {

	static private Log log = org.apache.commons.logging.LogFactory
			.getLog(Edugain.class);

	Validator validator;

	public Edugain(String configFile) throws ValidationException, IOException {

		this(loadProperties(configFile));
	}
	
	public Edugain(Properties properties) throws ValidationException {
		
		if (log.isDebugEnabled()) {
			log.debug(properties.toString());
		}
		
		this.validator = new Validator(properties);
	}
	
	public static Properties loadProperties(String file) throws IOException {

		Properties result = new Properties();
		InputStream in = new FileInputStream(file);

		if (in == null) {
			throw new FileNotFoundException(file);
		}
		try {
			result.load(in);
		} catch (IOException e) {
			throw e;
		} finally {
			in.close();
		}
		
		return result;
	}

	public ComponentID validateCert(X509Certificate cert)
			throws ValidationException {

		log.debug("Validation (" + cert.getType() + "|"
				+ cert.getIssuerDN().getName() + "|"
				+ cert.getSubjectDN().getName() + ")");

		ComponentID result = validator.validate(cert);

		log.debug("Certificate successfully validated (CID: " + result.getURN()
				+ ")");

		return result;
	}
}
