/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant.autobahn.edugain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.xml.xpath.XPathException;

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
	
	public Edugain(URL configFile) throws ValidationException, IOException {

        this.edugain = configFile;
    }
	
	public Edugain(Properties properties) throws ValidationException, XPathException {
		
		this.validator = new Validator(properties);	    
	}
	
	public Edugain(Properties properties, String active) throws ValidationException, XPathException {
        
        if (active.equals("true")) {
            this.validator = new Validator(properties);
        }        
    }

	/**
	 * Searches for a key in the provided properties and converts its value to 
	 * absolute path by concatenating it with the path argument
	 * If the key is not found, no action is performed
	 * 
	 * @param properties - the properties that contain the key to be converted
	 * @param key - the key in the properties that will be converted to absolute
	 * @param path - the absolute path
	 */
    public static void convertPropertyToAbsolutePath(Properties properties, String key, String path) {
        
        String s = properties.getProperty(key);
        if (s == null) {
            return;
        }
        
        // If the path is a file, keep only the directory part
        if (s.charAt(0) != '/') {
            
            path=path.substring(0,path.lastIndexOf('/')+1 );
            try {
                path = URLDecoder.decode(path, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.debug("Error in converting relative path to absolute: " + e.getMessage());
            }
            
            s = path + s;
            properties.setProperty(key, s);
        }       
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
		InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.debug("Edugain: Couldn't open stream " + file);
        }

		if (in == null) 
			throw new FileNotFoundException(file);
		
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
	
    
    public Properties getPropsLoaderForWGui() throws IOException {
        
        Properties result = getPropsLoader();
        
        if (edugain != null) {
            convertPropertyToAbsolutePath(result, "org.opensaml.ssl.truststore", edugain.getPath() );        
            convertPropertyToAbsolutePath(result, "net.geant.edugain.validation.valid-components", edugain.getPath() );                      
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
