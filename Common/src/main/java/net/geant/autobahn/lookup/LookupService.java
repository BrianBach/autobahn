package net.geant.autobahn.lookup;

import java.io.IOException;
import java.io.StringReader;

import java.util.Date;
import java.util.ArrayList;
import java.sql.Timestamp;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.CharacterData;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LookupService {
    
    private static int TYPE1 = 1;
    private static int TYPE2 = 2;
    private static int TYPE3 = 3;
    private static int TYPE4 = 4;
    
    private final String host;
    private Logger log = Logger.getLogger(this.getClass());
    private String xmlToBeSent = "";
    private final String xmlPrefixRegisterIdm = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://ggf.org/ns/nmwg/base/2.0/\">" +
    											"<soapenv:Header/><soapenv:Body><nmwg:message type=\"LSRegisterRequest\" id=\"msg1\" xmlns:lookup=\"ru6.cti.gr\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\" " +
    											"xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\" xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\" " +
    											"xmlns:netutil=\"http://ggf.org/ns/nmwg/characteristic/utilisation/2.0/\">" +
    											"<nmwg:metadata id=\"serviceLookupInfo\"><perfsonar:subject id=\"commonParameters\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
    											"<psservice:service id=\"serviceParameters\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\">" +
    											"<psservice:serviceName>TYPE 2</psservice:serviceName><psservice:accessPoint>";
    private final String xmlSecfixRegisterIdm = "</psservice:accessPoint><psservice:serviceType>IDM</psservice:serviceType><psservice:serviceDescription>" +
    											"Domain name, IDM instance URL, Unix time timestamp, IDM</psservice:serviceDescription></psservice:service></perfsonar:subject>" +
    											"<nmwg:eventType>http://ogf.org/ns/nmwg/tools/org/perfsonar/service/lookup/registration/service/2.0</nmwg:eventType>" +
    											"</nmwg:metadata><nmwg:data id=\"data0\" metadataIdRef=\"serviceLookupInfo\">" +
    											"<nmwg:metadata id=\"meta1\"><perfsonar:subject id=\"subj1\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
    											"<nmwgt:interface xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"><lookup:domain>";
    private final String xmlSuffixRegisterIdm = "</lookup:module></nmwgt:interface></perfsonar:subject></nmwg:metadata></nmwg:data>" +
    											"</nmwg:message></soapenv:Body></soapenv:Envelope>";
    private final String xmlPrefixRegisterPort = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://ggf.org/ns/nmwg/base/2.0/\"><soapenv:Header/><soapenv:Body>" +
     											 " <nmwg:message type=\"LSRegisterRequest\" id=\"msg1\" xmlns:lookup=\"ru6.cti.gr\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\" xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\" xmlns:netutil=\"http://ggf.org/ns/nmwg/characteristic/utilisation/2.0/\"> " +
     											 "<nmwg:metadata id=\"serviceLookupInfo\"><perfsonar:subject id=\"commonParameters\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
     											 "<psservice:service id=\"serviceParameters\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"><psservice:serviceName>TYPE 1</psservice:serviceName>" +
     											 "<psservice:accessPoint>";
    private final String xmlSecfixRegisterPort = "</psservice:accessPoint><psservice:serviceType>PORT</psservice:serviceType><psservice:serviceDescription>End port identifier, User-friendly name, domain name</psservice:serviceDescription>" +
     											 "</psservice:service></perfsonar:subject><nmwg:eventType>http://ogf.org/ns/nmwg/tools/org/perfsonar/service/lookup/registration/service/2.0</nmwg:eventType></nmwg:metadata>" +
     											 "<nmwg:data id=\"data0\" metadataIdRef=\"serviceLookupInfo\"><nmwg:metadata id=\"meta1\"><perfsonar:subject id=\"subj1\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
     											 "<nmwgt:interface xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"><lookup:identifier>";
    private final String xmlSuffixRegisterPort = "</lookup:domain></nmwgt:interface></perfsonar:subject></nmwg:metadata>" +
     											 "</nmwg:data></nmwg:message></soapenv:Body></soapenv:Envelope>";
    private final String xmlPrefixRegisterEdgePort = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://ggf.org/ns/nmwg/base/2.0/\"><soapenv:Header/><soapenv:Body>" +
     												 " <nmwg:message type=\"LSRegisterRequest\" id=\"msg1\" xmlns:lookup=\"ru6.cti.gr\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\" xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\" xmlns:netutil=\"http://ggf.org/ns/nmwg/characteristic/utilisation/2.0/\"> " +
     												 "<nmwg:metadata id=\"serviceLookupInfo\"><perfsonar:subject id=\"commonParameters\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
     												 "<psservice:service id=\"serviceParameters\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"><psservice:serviceName>TYPE 3</psservice:serviceName>" +
     												 "<psservice:accessPoint>";
    private final String xmlSecfixRegisterEdgePort = "</psservice:accessPoint><psservice:serviceType>EdgePORT</psservice:serviceType><psservice:serviceDescription>Start domain, end domain, public edge port identifier</psservice:serviceDescription>" +
     												 "</psservice:service></perfsonar:subject><nmwg:eventType>http://ogf.org/ns/nmwg/tools/org/perfsonar/service/lookup/registration/service/2.0</nmwg:eventType></nmwg:metadata>" +
     												 "<nmwg:data id=\"data0\" metadataIdRef=\"serviceLookupInfo\"><nmwg:metadata id=\"meta1\"><perfsonar:subject id=\"subj1\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
     												 "<nmwgt:interface xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"><lookup:startDomain>";
    private final String xmlSuffixRegisterEdgePort = "</lookup:port></nmwgt:interface></perfsonar:subject></nmwg:metadata>" +
     												 "</nmwg:data></nmwg:message></soapenv:Body></soapenv:Envelope>";
    private final String xmlPrefixRegisterLS = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://ggf.org/ns/nmwg/base/2.0/\"><soapenv:Header/><soapenv:Body>" +
       										   " <nmwg:message type=\"LSRegisterRequest\" id=\"msg1\" xmlns:lookup=\"ru6.cti.gr\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\" xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\" xmlns:netutil=\"http://ggf.org/ns/nmwg/characteristic/utilisation/2.0/\"> " +
       										   "<nmwg:metadata id=\"serviceLookupInfo\"><perfsonar:subject id=\"commonParameters\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
       										   "<psservice:service id=\"serviceParameters\" xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"><psservice:serviceName>TYPE 4</psservice:serviceName>" +
       										   "<psservice:accessPoint>";
    private final String xmlSecfixRegisterLS = "</psservice:accessPoint><psservice:serviceType>LS</psservice:serviceType><psservice:serviceDescription>Lookup server location (URL)</psservice:serviceDescription>" +
       										   "</psservice:service></perfsonar:subject><nmwg:eventType>http://ogf.org/ns/nmwg/tools/org/perfsonar/service/lookup/registration/service/2.0</nmwg:eventType></nmwg:metadata>" +
       										   "<nmwg:data id=\"data0\" metadataIdRef=\"serviceLookupInfo\"><nmwg:metadata id=\"meta1\"><perfsonar:subject id=\"subj1\" xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\">" +
       										   "<nmwgt:interface xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"><lookup:eventType>";
    private final String xmlSuffixRegisterLS = "</lookup:newLS></nmwgt:interface></perfsonar:subject></nmwg:metadata>" +
       										   "</nmwg:data></nmwg:message></soapenv:Body></soapenv:Envelope>";
    private final String xmlPrefixRemoveIdm = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://ggf.org/ns/nmwg/base/2.0/\">" +
      										  "<soapenv:Header/><soapenv:Body><nmwg:message  id = \"blah1\" messageIdRef=\"blah2\" type=\"LSDeregisterRequest\"" +
      										  " xmlns:perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"" +
      										  " xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"" +
      										  " xmlns:psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"" +
      										  " xmlns:nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"><!--xmlns:netutil=\"http://ggf.org/ns/nmwg/characteristic/utilisation/2.0/\"-->" +
      										  "<!--You have a CHOICE of the next 2 items at this level--><nmwg:metadata id=\"meta1\">" +
      										  "<nmwg:key id=\"localhost.localdomain.-6236687d:116c5a2ab6a:-7fc4\">" +
      										  "<nmwg:parameters id=\"localhost.localdomain.-6236687d:116c5a2ab6a:-7fc3\"><nmwg:parameter name=\"lsKey\">";
    private final String xmlSuffixRemoveIdm = "</nmwg:parameter></nmwg:parameters></nmwg:key></nmwg:metadata>" +
      										  "<nmwg:data id=\"data1\" metadataIdRef=\"meta1\"/>" +
      										  "</nmwg:message></soapenv:Body></soapenv:Envelope>";
    private final String xmlPrefixQuery = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"><soapenv:Header/><soapenv:Body><nmwg:message" +
      									  " type=\"LSQueryRequest\" id=\"msg1\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\">" +
      									  " <nmwg:metadata id=\"meta1\"><xquery:subject id=\"sub1\">";
    private final String xmlSuffixQuery = "</xquery:subject><nmwg:eventType>service.lookup.xquery</nmwg:eventType></nmwg:metadata>" +
      									  "<nmwg:data id=\"data1\" metadataIdRef=\"meta1\" /></nmwg:message></soapenv:Body></soapenv:Envelope>";

    /**
     * 
     * @param host - address of LS
     */
    public LookupService(String host) {
        this.host=host;
    }
    
    /**
     * 
     * sends request to LookupService and returns the response from the service
     * 
     * @param xmlToSent - xmlRequest with the appropriate request
     * @return responseContent - information returned by queries
     * @throws LookupServiceException
     */
    public String invokeLS(String xmlToSent)
    	    throws LookupServiceException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(host);
        
        // define required HTTP headers
        httpPost.setHeader("Content-type", "text/xml; charset=UTF-8;");
        httpPost.setHeader("SOAPAction", "");
        
        // read request content for Http(POST) request from file
        String xmlContent = xmlToSent;
        String responseContent;
        try {
        	StringEntity entity = new StringEntity(xmlContent);
        
        	httpPost.setEntity(entity);
        
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
        
        	// send request to server
        	responseContent = httpclient.execute(httpPost,
        			responseHandler);
        
        	responseContent = responseContent.replace(">", ">\n");			
        
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        }
        
        httpclient.getConnectionManager().shutdown();
        return responseContent;
    }
    
    /**
     * 
     * Registration of IDM location to domain name association
     * If the same domain has already registered an IDM, the record is
     * updated.
     * 
     * @param eventType - type of record
     * @param domain - domain name
     * @param URL - idmLocation
     * @param module - type of module
     * @throws LookupServiceException
     */
    public void RegisterIdm(String domain, String URL)
            throws LookupServiceException {
    	
    	if (domain == null || URL == null) {
    		throw new LookupServiceException("Registration of null IDM record not allowed");
    	}
    	
    	if (QueryIdmLocation(domain) != null) {
    		// The IDM for the same domain has already been registered
    		this.RemoveIdm(domain);
    	}
    	
        Date today = new Date();
        Timestamp ts = new Timestamp(today.getTime());
        
        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/IDM ";
        // Generate key with timestamp
        String seckey = GenerateKey(firstkey);
        
        String xmlMetadataURL = "</lookup:domain><lookup:url>";
        String xmlMetadataEventType = "</lookup:url><lookup:eventType>";
        String xmlMetadataTimestamp = "</lookup:eventType><lookup:timestamp>";
        String xmlMetadataModule = "</lookup:timestamp><lookup:module>";

        //XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRegisterIdm + seckey + this.xmlSecfixRegisterIdm + domain 
                + xmlMetadataURL + URL + xmlMetadataEventType + TYPE2 + xmlMetadataTimestamp 
                + ts + xmlMetadataModule + "IDM" + this.xmlSuffixRegisterIdm;
        invokeLS(xmlToBeSent);
    }
    
    /**
     * 
     * Registration of end port to friendly name association
     * 
     * @param eventType - type of record
     * @param friendlyName - friendly name for convenience of user
     * @param portIdentifier - topology abstracted identifiers
     * @param domain - Domain name
     * @throws LookupServiceException
     */
    
    public void RegisterEndPort(String friendlyName, String portIdentifier, String domain)
            throws LookupServiceException {
        
    	if (this.QueryFriendlyName(portIdentifier) !=null) {
    		// The same port has already been registered
    		// TODO: Implement and call remove
    		this.RemoveEndPort(portIdentifier);
    	}

        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/PORT ";
        // Generate key with timestamp
        String seckey = GenerateKey(firstkey);
        
        String xmlMetadataFriendlyName = "</lookup:identifier><lookup:friendlyName>";
        String xmlMetadataEventType = "</lookup:friendlyName><lookup:eventType>";
        String xmlMetadataDomain = "</lookup:eventType><lookup:domain>"; 
        
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRegisterPort + seckey + this.xmlSecfixRegisterPort + portIdentifier 
        	+ xmlMetadataFriendlyName + friendlyName + xmlMetadataEventType + TYPE1 + xmlMetadataDomain
        	+ domain + this.xmlSuffixRegisterPort;
        invokeLS(xmlToBeSent);
    }
    
    /**
     * 
     * Registration of edge port identifier of interdomain link
     * 
     * @param startDomain - starting domain of the port
     * @param endDomain - end domain of the interdomain link
     * @param edgeport - public edge port identifier
     * @throws LookupServiceException
     */
    public void RegisterEdgePort(String startDomain, String endDomain,String edgeport)
            throws LookupServiceException {
        
    	if (QueryEdgePortForDuplication(startDomain, endDomain, edgeport) != null) {
    		// The same port has already been registered
    		RemoveEdgePort(startDomain, endDomain, edgeport);
    	}

    	// This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/EdgePORT ";
        // Generate key with timestamp
        String seckey = GenerateKey(firstkey);
        
        String xmlMetadataEndDomain = "</lookup:startDomain><lookup:endDomain>";
        String xmlMetadataEventType = "</lookup:endDomain><lookup:eventType>";
        String xmlMetadataIdentifier = "</lookup:eventType><lookup:port>";
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRegisterEdgePort + seckey + this.xmlSecfixRegisterEdgePort + startDomain + xmlMetadataEndDomain + endDomain + xmlMetadataEventType + TYPE3 + xmlMetadataIdentifier + edgeport + this.xmlSuffixRegisterEdgePort;
        invokeLS(xmlToBeSent);
    }
    
    /**
     * 
     * New LS instance initialization
     * 
     * @param fileName - file which contains content of HTTP Post
     * @throws LookupServiceException
     */
    public void newLS(String URL)
            throws LookupServiceException {
        
        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/LS ";
        // Generate key with timestamp
        String seckey = GenerateKey(firstkey);
        		
        String xmlMetadataDomain = "</lookup:eventType><lookup:newLS>"; 
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRegisterLS + seckey + this.xmlSecfixRegisterLS + TYPE4 + xmlMetadataDomain + URL +this.xmlSuffixRegisterLS;
        invokeLS(xmlToBeSent);
    }
    
    /**
     * 
     * Removal of IDM location to domain name association
     * 
     * @param URL - IDM location
     * @throws LookupServiceException
     */
    public void RemoveIdm(String domain) throws LookupServiceException {
        String key = "";
        try {
            // use domain name to get the primary key according to the Exist database
            key = this.findDomainkey(domain);
        } catch (NullPointerException e){
            log.info("There is no domain with the particular name to delete");
        }
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRemoveIdm + key + this.xmlSuffixRemoveIdm;
        invokeLS(xmlToBeSent);
    }
    
    /**
     * 
     * Removal of edge port
     * 
     * @param startDomain - starting domain of the port
     * @param endDomain - end domain of the interdomain link
     * @param edgeport - public edge port identifier
     * @throws LookupServiceException
     */
    public void RemoveEdgePort(String startDomain, String endDomain, String edgeport) 
    		throws LookupServiceException {
        String key = "";
        try {
            // use domain name to get the primary key according to the Exist database
            key = this.findPortKey(startDomain, endDomain, edgeport);
        } catch (NullPointerException e){
            log.info("There is no port with the particular name to delete");
        }
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRemoveIdm + key + this.xmlSuffixRemoveIdm;
        invokeLS(xmlToBeSent);
    }
    
    public void RemoveEndPort(String portIdentifier) throws LookupServiceException {
        String key = "";
        try {
            // use domain name to get the primary key according to the Exist database
            key = this.findEndPortkey(portIdentifier);
        } catch (NullPointerException e){
            log.info("There is no domain with the particular name to delete");
        }
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixRemoveIdm + key + this.xmlSuffixRemoveIdm;
        invokeLS(xmlToBeSent);
    }

    /**
     * 
     * Query for location of IDM module
     * 
     * @param domain - domain name
     * @throws LookupServiceException
     * @return URL of IDM location, null if not found
     */
    public String QueryIdmLocation(String domain)
    		throws LookupServiceException{
        String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
           							   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
           							   " for $m in /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject/nmwgt:interface let $accessPoint := $m/lookup:url  where  $m/lookup:domain = \"";
        String xmlMetaSuffixQuery = "\" return $accessPoint";
        String xmlMetaDataQuery = xmlMetaFirstDataQuery + domain + xmlMetaSuffixQuery;
        
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
        String testing = "";
        testing = invokeLS(xmlToBeSent);

        String fname = "";
        
        try {
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	InputSource is = new InputSource();
        	is.setCharacterStream(new StringReader(testing));
        	
        	// Parsing the response of the lookup service			
        	Document doc = db.parse(is);
        	// Getting data from child nodes of "psservice:datum"
        	NodeList nodes = doc.getElementsByTagName("psservice:datum");
        	for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
        		
        		NodeList name = element.getElementsByTagName("lookup:url");
        		Element line = (Element) name.item(0);
        		
        		int nodesLength = nodes.getLength();
                if (nodesLength != 0){
                	fname = getCharacterDataFromElement(line);
                	return fname;
                }	
        	}	
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
        return null;		
    }
    
    /**
     * 
     * Query friendly name of end port
     * 
     * @param endPoint - The identifier of the port
     * @throws LookupServiceException
     */
    public String QueryFriendlyName(String endPoint)
    		throws LookupServiceException {
        String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
        						  	   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
        						       " for $m in  /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject  let $accessPoint := $m/nmwgt:interface/lookup:friendlyName where  $m/nmwgt:interface/lookup:identifier = \"";
        String xmlMetaSuffixQuery = "\" return $accessPoint";
        String xmlMetaDataQuery = xmlMetaFirstDataQuery + endPoint + xmlMetaSuffixQuery;
        
        // XML query to sent to the lookup service
        this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
        String testing = "";
        testing = invokeLS(xmlToBeSent);
        
        String fname = "";
        try {
            DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testing));
        
            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
        
                NodeList name = element.getElementsByTagName("lookup:friendlyName");
                Element line = (Element) name.item(0);
                
                int nodesLength = nodes.getLength();
                if(nodesLength != 0){
                    fname = getCharacterDataFromElement(line);
                	return fname;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
        return null;	
    }
    
    /**
     * 
     * Query for all end ports and their friendly names
     * 
     * @throws UnsupportedEncodingException
     */
    public ArrayList<String> QueryAllFriendlyName() 
    		throws LookupServiceException {
    	String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
    	  							   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
    	  							   " for $m in  /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject  let $accessPoint := $m/nmwgt:interface/lookup:friendlyName ";//2   return $accessPoint";
    	String xmlMetaSuffixQuery = " return $accessPoint";
    	String xmlMetaDataQuery = xmlMetaFirstDataQuery + xmlMetaSuffixQuery;
    	// XML query to sent to the lookup service
    	this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
    	String testing = "";
   		testing = invokeLS(xmlToBeSent);
    	
    	String fname = "";
    	ArrayList<String> friendlynames = new ArrayList<String>();
    	try {
            DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testing));
    
            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
    
                NodeList name = element.getElementsByTagName("lookup:friendlyName");
                Element line = (Element) name.item(0);
                
                int nodesLength = nodes.getLength();
                if(nodesLength != 0) {
                	fname = getCharacterDataFromElement(line);
                }
                if (fname != null) {
	                friendlynames.add(fname); 
	            }
            }
            if (nodes.getLength() != 0) {
            	return friendlynames;
            }
    	} catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
    	return null;
    }
    
    /**
     * 
     * Query for edge port identifier of interdomain link
     * 
     * @param startDomain - this is the external domain that registered this link
     * @param endDomain - this is our local domain
     * @throws LookupServiceException
     */
    public ArrayList<String> QueryEdgePort(String startDomain, String endDomain)
    		throws LookupServiceException {
    	String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
    	  							   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
    	  							   " for $m in  /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject  let $accessPoint := $m/nmwgt:interface/lookup:port where  $m/nmwgt:interface/lookup:startDomain = \"";
    	String xmlMiddleQuery = "\" and $m/nmwgt:interface/lookup:endDomain = \"";
    	String xmlMetaSuffixQuery = "\" return $accessPoint";
    	String xmlMetaDataQuery = xmlMetaFirstDataQuery + startDomain + xmlMiddleQuery + endDomain + xmlMetaSuffixQuery;
    	// XML query to sent to the lookup service
    	this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
    	String testing = "";
   		testing = invokeLS(xmlToBeSent);

   		String fname = "";
    	ArrayList<String> edgeports = new ArrayList<String>();
    	
    	try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		InputSource is = new InputSource();
    		is.setCharacterStream(new StringReader(testing));
    		
    		//Parsing the response of the lookup service
    		Document doc = db.parse(is);
    		//Getting data from child nodes of "psservice:datum"
    		NodeList nodes = doc.getElementsByTagName("psservice:datum");
    		for (int i = 0; i < nodes.getLength(); i++) {
    			Element element = (Element) nodes.item(i);
    			
    			NodeList name = element.getElementsByTagName("lookup:port");
    			Element line = (Element) name.item(0);
    			
    			int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                	fname = getCharacterDataFromElement(line);
                }
                if (fname != null) {
                	edgeports.add(fname);
                }
    		}
    		if (nodes.getLength() != 0) {
            	return edgeports;
            }
    	} catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
    	return null;
    }
    
    private String QueryEdgePortForDuplication(String startDomain, String endDomain,String edgeport)
			throws LookupServiceException {
    	
    	String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
		   							   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
		   							   " for $m in  /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject  let $accessPoint := $m/nmwgt:interface/lookup:port where  $m/nmwgt:interface/lookup:startDomain = \"";
    	String xmlMiddleQuery = "\" and $m/nmwgt:interface/lookup:endDomain = \"";
    	String xmlMiddleSecondQuery = "\" and $m/nmwgt:interface/lookup:port = \"";
    	String xmlMetaSuffixQuery = "\" return $accessPoint";
    	String xmlMetaDataQuery = xmlMetaFirstDataQuery + startDomain + xmlMiddleQuery + endDomain + xmlMiddleSecondQuery + edgeport + xmlMetaSuffixQuery;
    	// XML query to sent to the lookup service
    	this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
    	String testing = "";
   		testing = invokeLS(xmlToBeSent);
   		
   		String fname = "";
   		
   		try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		InputSource is = new InputSource();
    		is.setCharacterStream(new StringReader(testing));
    		
    		//Parsing the response of the lookup service
    		Document doc = db.parse(is);
    		//Getting data from child nodes of "psservice:datum"
    		NodeList nodes = doc.getElementsByTagName("psservice:datum");
    		for (int i = 0; i < nodes.getLength(); i++) {
    			Element element = (Element) nodes.item(i);
    			
    			NodeList name = element.getElementsByTagName("lookup:port");
    			Element line = (Element) name.item(0);
    			
    			int nodesLength = nodes.getLength();
                if(nodesLength != 0) {
                	fname = getCharacterDataFromElement(line);
                }
                if (fname != null) {
                	return fname;
                } 
    		}
	 	} catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
    	return null;
    }

    /**
     * 
     * Query for edge port identifier of interdomain link
     * 
     * @param startDomain - domain connected to another domain
     * @throws LookupServiceException
     */
    public ArrayList<String> QueryAllEdgePort(String startDomain)
    		throws LookupServiceException {
    	String xmlMetaFirstDataQuery = " declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; " +
    	  							   " declare namespace  xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
    	  							   " for $m in  /nmwg:store/nmwg:data/nmwg:metadata/perfsonar:subject  let $accessPoint := $m/nmwgt:interface/lookup:port where  $m/nmwgt:interface/lookup:startDomain = \"";
    	String xmlMetaSuffixQuery = "\" return $accessPoint";
    	String xmlMetaDataQuery = xmlMetaFirstDataQuery + startDomain + xmlMetaSuffixQuery;
    	// XML query to sent to the lookup service
    	this.xmlToBeSent = this.xmlPrefixQuery + xmlMetaDataQuery + this.xmlSuffixQuery;
    	String testing = "";
    	testing = invokeLS(xmlToBeSent);

    	String fname = "";
    	ArrayList<String> edgeports = new ArrayList<String>();
    	
    	try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		InputSource is = new InputSource();
    		is.setCharacterStream(new StringReader(testing));
    		
    		//Parsing the response of the lookup service
    		Document doc = db.parse(is);
    		//Getting data from child nodes of "psservice:datum"
    		NodeList nodes = doc.getElementsByTagName("psservice:datum");
    		for (int i = 0; i < nodes.getLength(); i++) {
    			Element element = (Element) nodes.item(i);
    			
    			NodeList name = element.getElementsByTagName("lookup:port");
    			Element line = (Element) name.item(0);
    			
    			int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                	fname = getCharacterDataFromElement(line);
                }
                if (fname != null) {
                    edgeports.add(fname); 
    			}
    		}
    		if (nodes.getLength() != 0) {
            	return edgeports;
            }
    	} catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
    	return null;
    }
    
    /**
     * Update timestamp of an already registered IDM.
     * If IDM is not found it is now newly registered
     * 
     * @param domainName
     * 
     */
    public void updateIDMTimestamp(String domain, String URL) 
            throws LookupServiceException {
        this.RemoveIdm(domain);
        this.RegisterIdm(domain, URL);
    }
    
    /**
     * 
     * @param key - key for each record
     * @return key with a Unix Timestamp
     */
    private String GenerateKey(String key){
    	Date today = new Date();
    	Timestamp ts = new Timestamp(today.getTime());
    	String finalkey = key + ts.toString();
    
    	return finalkey;
    }
    
    /**
     * 
     * @param Element - node elements
     * @return parsed info or null if element is null
     */
    public static String getCharacterDataFromElement(Element e) {
        if (e == null) {
            return null;
        }
        
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
           CharacterData cd = (CharacterData) child;
           return cd.getData();
        }
        return "?";
    }
    
    /**
     * 
     * @param domain
     * @return domain key record in the database, or null if not found
     */
    public String findDomainkey(String domain) 
    		throws LookupServiceException {
    	String xmlStart = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"><soapenv:Header/>" +
    					"<soapenv:Body><nmwg:message type=\"LSQueryRequest\" id=\"msg1\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\">" +
    					"<nmwg:metadata id=\"meta1\"><xquery:subject id=\"sub1\"> declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\";" +
    					" declare namespace xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
    					" for $m in /nmwg:store/nmwg:metadata let $a := for $j in /nmwg:store/nmwg:data let $n := $j/nmwg:metadata/perfsonar:subject/nmwgt:interface where  $n/lookup:domain = \"";
    	String xmlEnd = "\" return  data($j/@metadataIdRef) where $m/@id = $a return $m/perfsonar:subject/psservice:service/psservice:accessPoint " +
    					" </xquery:subject><nmwg:eventType>service.lookup.xquery</nmwg:eventType></nmwg:metadata><nmwg:data id=\"data1\" metadataIdRef=\"meta1\" /></nmwg:message></soapenv:Body></soapenv:Envelope>";
    	String response = "";
    	// XML query to sent to the lookup service
    	String toSent = xmlStart + domain + xmlEnd;
    	String testing = "";
   		testing = invokeLS(toSent);
    	
    	try {
            DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testing));
    
            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");
          
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
    
                NodeList name = element.getElementsByTagName("psservice:accessPoint");
                
                Element line = (Element) name.item(0);
                int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                	response = getCharacterDataFromElement(line);
                	return response;
                }
            }
            
    	} catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new LookupServiceException(e.getMessage());
        }
    
    	return null;
    }
    
    /**
     * 
     * @param domain
     * @return domain key record in the database, or null if not found
     */
    public String findEndPortkey(String domain) 
            throws LookupServiceException {
        String xmlStart = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"><soapenv:Header/>" +
                        "<soapenv:Body><nmwg:message type=\"LSQueryRequest\" id=\"msg1\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\">" +
                        "<nmwg:metadata id=\"meta1\"><xquery:subject id=\"sub1\"> declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\";" +
                        " declare namespace xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\"; declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\";" +
                        " for $m in /nmwg:store/nmwg:metadata let $a := for $j in /nmwg:store/nmwg:data let $n := $j/nmwg:metadata/perfsonar:subject/nmwgt:interface where  $n/lookup:identifier = \"";
        String xmlEnd = "\" return  data($j/@metadataIdRef) where $m/@id = $a return $m/perfsonar:subject/psservice:service/psservice:accessPoint " +
                        " </xquery:subject><nmwg:eventType>service.lookup.xquery</nmwg:eventType></nmwg:metadata><nmwg:data id=\"data1\" metadataIdRef=\"meta1\" /></nmwg:message></soapenv:Body></soapenv:Envelope>";
        String response = "";
        // XML query to sent to the lookup service
        String toSent = xmlStart + domain + xmlEnd;
        String testing = "";
        testing = invokeLS(toSent);
        
        try {
            DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testing));
    
            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");
          
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
    
                NodeList name = element.getElementsByTagName("psservice:accessPoint");
                
                Element line = (Element) name.item(0);
                int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                    response = getCharacterDataFromElement(line);
                    return response;
                }
            }
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        }
    
        return null;
    }
    
    public String findPortKey(String startDomain, String endDomain, String edgeport) 
            throws LookupServiceException {
        String xmlStart = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"><soapenv:Header/><soapenv:Body><nmwg:message type=\"LSQueryRequest\" id=\"msg1\" xmlns:nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\" xmlns:xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\">" +
                    	  "<nmwg:metadata id=\"meta1\"><xquery:subject id=\"sub1\">declare namespace nmwg=\"http://ggf.org/ns/nmwg/base/2.0/\"; declare namespace perfsonar=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/1.0/\"; declare namespace psservice=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/1.0/\"; declare namespace xquery=\"http://ggf.org/ns/nmwg/tools/org/perfsonar/service/lookup/xquery/1.0/\";" +
                    	  " declare namespace nmwgt=\"http://ggf.org/ns/nmwg/topology/2.0/\"; declare namespace lookup=\"ru6.cti.gr\"; for $m in /nmwg:store/nmwg:metadata let $a := for $j in /nmwg:store/nmwg:data let $n := $j/nmwg:metadata/perfsonar:subject where $n/nmwgt:interface/lookup:port = \"";
        String xmlEnd = "\" return  data($j/@metadataIdRef) where $m/@id = $a return $m/perfsonar:subject/psservice:service/psservice:accessPoint " +
                        " </xquery:subject><nmwg:eventType>service.lookup.xquery</nmwg:eventType></nmwg:metadata><nmwg:data id=\"data1\" metadataIdRef=\"meta1\" /></nmwg:message></soapenv:Body></soapenv:Envelope>";
        String response = "";
        // XML query to sent to the lookup service
        String toSent = xmlStart + edgeport + xmlEnd;
        //String toSent = xmlStart + startDomain + xmlMiddleQuery + endDomain + xmlMiddleSecondQuery + edgeport + xmlEnd;
        String testing = "";

        testing = invokeLS(toSent);
        
        try {
            DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testing));
            
            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");
              
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                
                NodeList name = element.getElementsByTagName("psservice:accessPoint");
                
                Element line = (Element) name.item(0);
                int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                    response = getCharacterDataFromElement(line);
                    return response;
                }
    	    }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new LookupServiceException(e.getMessage());
        }
        
        return null;
    }
    
}
