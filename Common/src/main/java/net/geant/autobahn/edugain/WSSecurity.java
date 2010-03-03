package net.geant.autobahn.edugain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

import net.geant.autobahn.edugain.EdugainSupport;
import net.geant.autobahn.edugain.SecurityTokenNotFoundException;

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
 * and also edugain validation.
 * 
 * @author Akis Kalligeros
 */
public class WSSecurity {
	
	public URL edugain, client, server;
	public String security;
	private final static Logger log = Logger.getLogger(WSSecurity.class);
	private String WSS4J_CLIENT_PATH, WSS4J_SERVER_PATH;
	private String activatedStr, timestampStr, encryptStr, clientUser, serverUser;
	public final String PROPERTY_ACTIVATED = "net.geant.autobahn.edugain.activated";
	public final String PROPERTY_ENCRYPT = "net.geant.autobahn.edugain.encrypt";
	public final String PROPERTY_TIMESTAMP = "net.geant.autobahn.edugain.timestamp";
	public final String PROPERTY_USER = "org.apache.ws.security.crypto.merlin.keystore.alias";
	public final String WSS_X509_TOKENPROFILE = "http://docs.oasis-open"
		+ ".org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";

	
	XPathExpression xpath;
	
	public WSSecurity() throws XPathException {

		xpath = compileXpathExpression();
	}
	
	
	public WSSecurity(String commonPath) throws XPathException {

		xpath = compileXpathExpression();
		ClassLoader securityLoader = getClass().getClassLoader();
		
		this.edugain = securityLoader.getResource(commonPath + "/edugain.properties");
		this.client = securityLoader.getResource(commonPath + "/client-sec.properties");
		this.server = securityLoader.getResource(commonPath + "/server-sec.properties");
		this.WSS4J_SERVER_PATH = commonPath + "/server-sec.properties";
		this.WSS4J_CLIENT_PATH = commonPath + "/client-sec.properties";

	}
	
	
	public void setClientTimeout(Object clientInterface) {
		
		final int TIMEOUT = 36000 * 1000;
		
		Client client = ClientProxy.getClient(clientInterface);
		HTTPConduit http = (HTTPConduit)client.getConduit();
		HTTPClientPolicy policy = new HTTPClientPolicy();
		policy.setConnectionTimeout(TIMEOUT);
		policy.setReceiveTimeout(TIMEOUT);
		policy.setAllowChunking(false);
		http.setClient(policy);

	}
	
	/**
	 * Reads the edugain property file and creates a String that represents
	 * the security methods that will be used (Signature, Timestamp, Encryption)
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readProperties () throws IOException {

		Properties edugainProps = new Properties();
		Properties clientProps = new Properties();
		Properties serverProps = new Properties();

		try {
			edugainProps.load(edugain.openStream());
			activatedStr = edugainProps.getProperty(PROPERTY_ACTIVATED);
			timestampStr = edugainProps.getProperty(PROPERTY_TIMESTAMP);
			encryptStr = edugainProps.getProperty(PROPERTY_ENCRYPT);
		} catch (IOException e) {
			log.error("Couldn't load edugain properties: " + e.getMessage());
		}

		try {
			clientProps.load(client.openStream());
			clientUser = clientProps.getProperty(PROPERTY_USER);
		} catch (IOException e) {
			log.error("Couldn't load client properties: " + e.getMessage());
		}
		
		try {
			serverProps.load(server.openStream());
			serverUser = serverProps.getProperty(PROPERTY_USER);
		} catch (IOException e) {
			log.error("Couldn't load server properties: " + e.getMessage());
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
	
	/**
	 * Retrieves the CXF endpoint from the client's interface object
	 * 
	 * @param clientInterface
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 * @throws Throwable
	 */
	public void configureEndpoint(Object clientInterface) 
		throws FileNotFoundException, IOException, Exception, Throwable {
		
		Client client = ClientProxy.getClient(clientInterface);
		Endpoint endpoint = client.getEndpoint();
		configureSecurity(endpoint);
	}
	
	/**
	 * Configures the interceptors for enabling security. Also creates 
	 * and adds a custom edugain validator
	 * 
	 * @param cxfEndpoint
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws XPathException
	 * @throws Exception
	 */
	public void configureSecurity(Endpoint cxfEndpoint) 
		throws FileNotFoundException, IOException, XPathException, Exception {

		Map<String, Object> in = new HashMap<String, Object>();
		Map<String, Object> out = new HashMap<String, Object>();
		
		ClientPasswordCallback clientPassword = new ClientPasswordCallback(client);
		ServerPasswordCallback serverPassword = new ServerPasswordCallback(server);
		
		//Encrypt the SOAP body
		String bodyPart = "{Content}{}Body";
		
		out.put(WSHandlerConstants.ENC_PROP_FILE, WSS4J_CLIENT_PATH);
		out.put(WSHandlerConstants.SIG_PROP_FILE, WSS4J_SERVER_PATH);
		out.put(WSHandlerConstants.ACTION, readProperties());
		out.put(WSHandlerConstants.ENCRYPTION_USER, clientUser);
		out.put(WSHandlerConstants.USER, serverUser);
		out.put(WSHandlerConstants.PW_CALLBACK_REF, serverPassword);
		out.put(WSHandlerConstants.ENC_SYM_ALGO, WSConstants.TRIPLE_DES);
		out.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
		out.put(WSHandlerConstants.ENCRYPTION_PARTS, bodyPart);
		
		in.put(WSHandlerConstants.ACTION, readProperties());
		in.put(WSHandlerConstants.PW_CALLBACK_REF, clientPassword);
		in.put(WSHandlerConstants.DEC_PROP_FILE, WSS4J_SERVER_PATH);
		in.put(WSHandlerConstants.SIG_PROP_FILE, WSS4J_CLIENT_PATH);
				
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(out);
		cxfEndpoint.getOutInterceptors().add(wssOut);
		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(in);
		cxfEndpoint.getInInterceptors().add(wssIn);

		if (security != "NoSecurity") {
			
			Properties properties = new Properties();
			properties.load(edugain.openStream());
			
			EdugainSupport edugainInInterceptor = new EdugainSupport(edugain);
			EdugainSupport edugainOutInterceptor = new EdugainSupport(edugain);
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
