/**
 * Common Network Information Service
 * Geant2 Project http://www.geant2.net/, http://wiki.geant2.net/
 */
package net.geant.autobahn.edugain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.xpath.XPathException;

import net.geant.autobahn.edugain.Edugain;
import net.geant.autobahn.edugain.EdugainCxfFault;
import net.geant.autobahn.edugain.SecurityTokenNotFoundException;
import net.geant.autobahn.edugain.WSSecurity;
import net.geant.autobahn.edugain.X509CertManager;
import net.geant.edugain.validation.ValidationException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.util.Base64;

import org.w3c.dom.Document;

/**
 * Works in cooperation with {@link DOMInHandler}.
 * 
 * This handler looks for a security token inside the SOAP message. In
 * perfSONAR, there are two kind of security tokens: (1) X.509 certificate, (2)
 * SAML Assertion. This version of the handler implements only the (1) case.
 * 
 * TODO: Implement the SAML assertion support
 * 
 * @version $Id: EdugainWssHandler.java 2912 2009-02-02 17:38:22Z psnc.labedzki
 *          $
 * @author Maciej Labedzki <labedzki@man.poznan.pl>
 * 
 */

public class EdugainSupport extends AbstractSoapInterceptor {

	static private final String valErrPrefix = "SOAP message validation failed: ";

	private Logger log = Logger.getLogger(EdugainSupport.class);
	private Edugain edugain; 
	private WSSecurity wss;
	private X509CertManager x509;
	private String active = "false";
	
	/*
	public EdugainSupport(Properties properties) throws XPathException, ValidationException {

		this();
		this.wss = new WSSecurity();
		this.x509 = new X509CertManager();
		this.edugain = new Edugain(properties); 
		this.active = "true";
	}
	*/
	
	public EdugainSupport(Properties properties, String active) throws XPathException, ValidationException {

        this();
        this.wss = new WSSecurity();
        this.x509 = new X509CertManager();
        this.active = active;
        this.edugain = new Edugain(properties, active);
    }
	
	/*
	public EdugainSupport(URL url) throws XPathException, ValidationException, IOException {
		
		this();
		this.wss = new WSSecurity();
		this.x509 = new X509CertManager();
		
		Properties properties = new Properties();
		properties.load(url.openStream());

		this.edugain = new Edugain(properties); 
	}*/
	
	/**
	 * This contructor performs basic handler initialization
	 * 
	 * @throws XPathException
	 */
	
	 private EdugainSupport() {

		super(Phase.SEND);        		
	 }

    public void handleMessage(SoapMessage message) {
        
        log.debug("+++Inside handleMessage() - 1 ");
        
        if (this.active.equals("true")) {
            
            String handleError;
            Document doc = null;
            
            log.debug("+++Inside handleMessage() - 2 ");
            try {
                doc = toDocument(message);
            } catch (TransformerConfigurationException e1) {
                handleError = "Could not transform Soap Message to Document: "
                        + e1.getMessage();
                log.error(handleError);
            } catch (TransformerException e1) {
                handleError = "Could not transform Soap Message to Document: "
                        + e1.getMessage();
                log.error(handleError);
            } catch (SOAPException e1) {
                handleError = "Could not transform Soap Message to Document: "
                        + e1.getMessage();
                log.error(handleError);
            } catch (IOException e1) {
                handleError = "Could not transform Soap Message to Document: "
                        + e1.getMessage();
                log.error(handleError);
            }
            
            assert this.edugain != null;
            String errorMsg;
            
            try {
                String bst = this.wss.extractBstFromSoapEnvelope(doc);
                byte[] bytes = Base64.decode(bst);
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                X509Certificate cert = x509.loadCertificate(bais);
                log.debug(cert.toString());
                this.edugain.validateCert(cert);
            } catch (SecurityTokenNotFoundException e) {
                errorMsg = valErrPrefix + "the Binary Security Token "
                        + "could not be found in the SOAP envelope";
                log.error(errorMsg);
                throw new EdugainCxfFault(e, errorMsg);
            } catch (XPathException e) {
                errorMsg = valErrPrefix + "error appeared when searching for "
                        + "the Binary Security Token in the SOAP envelope: "
                        + e.getMessage();
                log.error(errorMsg);
                throw new EdugainCxfFault(e, errorMsg);
            } catch (WSSecurityException e) {
                errorMsg = valErrPrefix + "the Binary Security Token "
                        + "could not be decoded (Base64): " + e.getMessage();
                log.error(errorMsg);
                throw new EdugainCxfFault(e, errorMsg);
            } catch (CertificateException e) {
                errorMsg = valErrPrefix + "the Binary Security Token "
                        + "is not an X509 certificate: " + e.getMessage();
                log.error(errorMsg);
                throw new EdugainCxfFault(e, errorMsg);
            } catch (ValidationException e) {
                errorMsg = valErrPrefix + "certificate validation failed: "
                        + e.getMessage();
                log.error(errorMsg);
                throw new EdugainCxfFault(e, errorMsg);
            }
            
            log.debug("Edugain Validation finished succesfully!");
        }
	}
    
    /**
     * Extracts the DOM representation of the SOAP message from the
     * MessageContext object. XFire internally keeps the message as an
     * XMLStreamReader entity. To get the DOM document the conversion is needed.
     * 
     * @param soapMsg
     * @return
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws SOAPException
     * @throws IOException
     */
    public Document toDocument(SoapMessage soapMsg)throws TransformerConfigurationException,
  	  	TransformerException, SOAPException, IOException {

		 	SOAPMessage SOAPMsg = soapMsg.getContent(SOAPMessage.class);
		 	SOAPPart soapPart = SOAPMsg.getSOAPPart();
		 	Source source = soapPart.getContent();
		 	
		 	TransformerFactory tf = TransformerFactory.newInstance();
		  	Transformer transformer = tf.newTransformer();
		  	DOMResult result = new DOMResult();
		  	transformer.transform(source, result);
			  		
	  		return (Document)result.getNode();
	} 
}
