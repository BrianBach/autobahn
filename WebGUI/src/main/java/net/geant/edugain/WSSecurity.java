package net.geant.edugain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.geant.edugain.EdugainSupport;
import net.geant.edugain.Edugain;
import net.geant.edugain.SecurityTokenNotFoundException;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.apache.log4j.Logger;

/**
 * Enables safe ws communication
 * @author Michal
 */
public class WSSecurity {
	
	static final String USER = "autobahn";
	static public String security = null;
	private final static Logger log = Logger.getLogger(WSSecurity.class);
	static public String EDUGAIN_PROPERTIES = "webapps\\autobahn-gui\\WEB-INF\\etc\\edugain\\edugain.properties";
	static public String activatedStr, timestampStr, encryptStr, encrypt, timestamp;
	static public final String PROPERTY_ACTIVATED = "net.geant2.jra3.edugain.activated";
	static public final String PROPERTY_ENCRYPT = "net.geant2.jra3.edugain.encrypt";
	static public final String PROPERTY_TIMESTAMP = "net.geant2.jra3.edugain.timestamp";
	static public final String WSS_X509_TOKENPROFILE = "http://docs.oasis-open"
		+ ".org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
	
	
	XPathExpression xpath;
	
	public WSSecurity() throws XPathException {

		xpath = compileXpathExpression();
	}

	
	public static void setClientTimeout(Object clientInterface) {
		
		final int TIMEOUT = 36000 * 1000;
		
		Client client = ClientProxy.getClient(clientInterface);
		HTTPConduit http = (HTTPConduit)client.getConduit();
		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(TIMEOUT);
		policy.setReceiveTimeout(TIMEOUT);
		policy.setAllowChunking(false);
		http.setClient(policy);

	}
	
	public static String readProperties () throws IOException {
		
		Properties properties = new Properties();
				
		try {
			properties.load(new FileInputStream(EDUGAIN_PROPERTIES));
			activatedStr = properties.getProperty(PROPERTY_ACTIVATED);
			timestampStr = properties.getProperty(PROPERTY_TIMESTAMP);
			encryptStr = properties.getProperty(PROPERTY_ENCRYPT);
						
		} catch (FileNotFoundException e) {
			log.error("Edugain Properties not found: " + e.getMessage());
		}
		
		if (activatedStr != null && "true".equalsIgnoreCase(activatedStr)) {
			
			security = "Signature";
			
			if (timestampStr != null && "true".equalsIgnoreCase(timestampStr)) {
				
				security += " Timestamp";
			
			}			
			
			if (encryptStr != null && "true".equalsIgnoreCase(encryptStr)) {
				
				security += " Encrypt";
			
			} 
			
		} else security = "NoSecurity";
		
		return security;
	}
	
	public static void configureEndpoint(Object clientInterface) 
		throws FileNotFoundException, IOException, Exception, Throwable {
		
		Client client = ClientProxy.getClient(clientInterface);
		Endpoint endpoint = client.getEndpoint();

		configureSecurity(endpoint);
	}

	public static void configureSecurity(Endpoint cxfEndpoint) 
		throws FileNotFoundException, IOException, XPathException, Exception {

		Map<String, Object> in = new HashMap<String, Object>();
		Map<String, Object> out = new HashMap<String, Object>();
		
		out.put(WSHandlerConstants.ACTION, readProperties());
		out.put(WSHandlerConstants.ENC_PROP_FILE,"../etc/edugain/client-sec.properties");
		out.put(WSHandlerConstants.SIG_PROP_FILE,"../etc/edugain/server-sec.properties");
		out.put(WSHandlerConstants.ENCRYPTION_USER, "autobahn");
		out.put(WSHandlerConstants.USER, "autobahn");
		out.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
		out.put(WSHandlerConstants.ENC_SYM_ALGO, WSConstants.TRIPLE_DES);
		out.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
		
		//Encrypt the SOAP body
		String bodyPart = "{Content}{}Body";
		out.put(WSHandlerConstants.ENCRYPTION_PARTS, bodyPart);
		
		in.put(WSHandlerConstants.ACTION, readProperties());
		in.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
		in.put(WSHandlerConstants.DEC_PROP_FILE, "../etc/edugain/server-sec.properties");
		in.put(WSHandlerConstants.SIG_PROP_FILE, "../etc/edugain/client-sec.properties");
				
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(out);
		cxfEndpoint.getOutInterceptors().add(wssOut);
		
		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(in);
		cxfEndpoint.getInInterceptors().add(wssIn);
		
		if (security != "NoSecurity") {
			EdugainSupport edugainInInterceptor = new EdugainSupport(Edugain.loadProperties(EDUGAIN_PROPERTIES));
			EdugainSupport edugainOutInterceptor = new EdugainSupport(Edugain.loadProperties(EDUGAIN_PROPERTIES));
			cxfEndpoint.getInInterceptors().add(edugainInInterceptor);
			cxfEndpoint.getOutInterceptors().add(edugainOutInterceptor);
		}
	}

	
	private XPathExpression compileXpathExpression()
		throws XPathExpressionException {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(new WSSENamespaceContext());
		XPathExpression expr = xpath.compile("//wsse:BinarySecurityToken");
		return expr;
	}
	
	
	public String extractBstFromSoapEnvelope(Document doc) 
		throws XPathException, SecurityTokenNotFoundException {

		NodeList nl = (NodeList) this.xpath.evaluate(doc,XPathConstants.NODESET);

		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			String valueType = e.getAttribute("ValueType");
			if (valueType != null && valueType.equals(WSS_X509_TOKENPROFILE)) {
			// token found
			return e.getTextContent();
			}
		}
		throw new SecurityTokenNotFoundException();
	}


	
	/**
	 * This class comes from the PerfSONAR sources.
	 */
	static public class WSSENamespaceContext implements NamespaceContext {

		public String getNamespaceURI(String prefix) {
			return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
		}

		// This method isn't necessary for XPath processing either.
		public String getPrefix(String uri) {
			throw new UnsupportedOperationException();
		}

		// This method isn't necessary for XPath processing either.
		public Iterator<?> getPrefixes(String uri) {
			throw new UnsupportedOperationException();
		}
	}
	
}

