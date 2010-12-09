/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant.autobahn.edugain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Properties;

import net.geant.edugain.validation.ComponentID;
import net.geant.edugain.validation.ValidationException;
import net.geant.edugain.validation.Validator;

import org.apache.log4j.Logger;

/**
 * This the EduGAIN module provided with the cNIS Web Services Framework.
 * 
 * @version $Id: Edugain.java 3054 2009-02-24 14:44:46Z psnc.labedzki $
 * @author Maciej Labedzki <labedzki@man.poznan.pl>
 * 
 */
public class Edugain {

	public URL edugain = null;
	static private Logger log = Logger.getLogger(Edugain.class);

	Validator validator;

	public Edugain(String configFile) throws ValidationException, IOException {

		ClassLoader edugainLoader = getClass().getClassLoader();
		this.edugain = edugainLoader.getResource(configFile);
	}
	
	public Edugain(Properties properties) throws ValidationException {
		
		this.validator = new Validator(properties);
	}
	
	/**
	 * Static method that is used by Spring framework (WebGUI)
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
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
	
	public static Properties loadProperties(URL url) throws IOException {

		Properties result = new Properties();
		InputStream in = url.openStream();

		if (in == null) {
			throw new IOException();
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
	
	public Properties getPropsLoader() throws IOException {
		
		Properties result = new Properties();
		InputStream in = edugain.openStream();
		
		if (in == null) {
			throw new FileNotFoundException();
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

		/*log.debug("Validation (" + cert.getType() + "|"
				+ cert.getIssuerDN().getName() + "|"
				+ cert.getSubjectDN().getName() + ")");*/

		ComponentID result = validator.validate(cert);

		log.debug("Certificate successfully validated by Edugain (CID: " + result.getURN()
				+ ")");

		return result;
	}
}
