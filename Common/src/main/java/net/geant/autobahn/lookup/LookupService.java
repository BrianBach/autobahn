package net.geant.autobahn.lookup;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.geant.autobahn.network.Link;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class LookupService {

    private static int TYPE1 = 1;
    private static int TYPE2 = 2;
    private static int TYPE3 = 3;
    private static int TYPE4 = 4;
    private static int TYPE5 = 5;

    private final String host;
    private Logger log = Logger.getLogger(this.getClass());
    public static Long timestamp = new Long(0);

    /**
     * 
     * @param host
     *            - address of LS
     */
    public LookupService(String host) {
        this.host = host;
    }

    /**
     * 
     * sends request to LookupService and returns the response from the service
     * 
     * @param xmlToSent
     *            - xmlRequest with the appropriate request
     * @return responseContent - information returned by queries
     * @throws LookupServiceException
     */
    private String invokeLS(String xmlToSent) throws LookupServiceException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(host);

        // define required HTTP headers
        httpPost.setHeader("Content-type", "text/xml; charset=UTF-8;");
        httpPost.setHeader("SOAPAction", "");

        // read request content for Http(POST) request from file
        String xmlContent = xmlToSent;
        String responseContent = null;

        try {
            StringEntity entity = new StringEntity(xmlContent);
            httpPost.setEntity(entity);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            // send request to server
            responseContent = httpclient.execute(httpPost, responseHandler);
            responseContent = responseContent.replace(">", ">\n");
        } catch (ClientProtocolException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }

        httpclient.getConnectionManager().shutdown();
        return responseContent;
    }

    /**
     * Gets the first matching key from the supplied String
     * 
     * @param input
     * @return null if none found
     * @throws LookupServiceException
     */
    private String getFirstMatch(String input, String elementName)
            throws LookupServiceException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));

            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                NodeList name = element.getElementsByTagName(elementName);

                Element line = (Element) name.item(0);
                int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                    String fname = getCharacterDataFromElement(line);
                    if (fname != null) {
                        return fname;
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }

        return null;
    }

    /**
     * Gets all matching keys from the supplied String
     * 
     * @param input
     * @return null if none found
     * @throws LookupServiceException
     */
    private ArrayList<String> getMatches(String input, String elementName)
            throws LookupServiceException {
        ArrayList<String> results = new ArrayList<String>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(input));

            // Parsing the response of the lookup service
            Document doc = db.parse(is);
            // Getting data from child nodes of "psservice:datum"
            NodeList nodes = doc.getElementsByTagName("psservice:datum");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                NodeList name = element.getElementsByTagName(elementName);

                Element line = (Element) name.item(0);
                int nodesLength = nodes.getLength();
                if (nodesLength != 0) {
                    String response = getCharacterDataFromElement(line);
                    if (response != null) {
                        results.add(response);
                    }
                }
            }
            if (nodes.getLength() != 0) {
                return results;
            }

        } catch (ParserConfigurationException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (SAXException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            log.error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }

        return null;
    }

    /**
     * 
     * Registration of IDM location to domain name association If the same
     * domain has already registered an IDM, the record is updated.
     * 
     * @param eventType
     *            - type of record
     * @param domain
     *            - domain name
     * @param URL
     *            - idmLocation
     * @param module
     *            - type of module
     * @throws LookupServiceException
     */
    public void registerIdm(String domain, String URL)
            throws LookupServiceException {
        if (domain == null || URL == null) {
            throw new LookupServiceException(
                    "Registration of null IDM record not allowed");
        }

        if (queryIdmLocation(domain) != null) {
            // The IDM for the same domain has already been registered
            this.removeIdm(domain);
        }

        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/IDM ";
        // Generate key with timestamp
        String seckey = generateKey(firstkey);
        RegisterXml xml;
        xml = new RegisterXml(seckey, "TYPE 2", "IDM");
        xml.addDescription("Domain name, IDM instance URL, Unix time timestamp, IDM");
        xml.addField("domain", domain);
        xml.addField("url", URL);
        xml.addField("eventType", String.valueOf(TYPE2));
        xml.addTimestamp();
        xml.addField("module", "IDM");
        invokeLS(xml.getXml());
    }

    /**
     * 
     * Registration of end port to friendly name association
     * 
     * @param eventType
     *            - type of record
     * @param friendlyName
     *            - friendly name for convenience of user
     * @param portIdentifier
     *            - topology abstracted identifiers
     * @param domain
     *            - Domain name
     * @throws LookupServiceException
     */

    public void registerEndPort(String friendlyName, String portIdentifier,
            String domain) throws LookupServiceException {

        if (this.queryFriendlyName(portIdentifier) != null) {
            // The same port has already been registered
            this.removeEndPort(portIdentifier);
        }

        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/PORT ";
        // Generate key with timestamp
        String seckey = generateKey(firstkey);

        RegisterXml xml;
        xml = new RegisterXml(seckey, "TYPE 1", "PORT");
        xml.addDescription("End port identifier, User-friendly name, domain name");
        xml.addField("identifier", portIdentifier);
        xml.addField("friendlyName", friendlyName);
        xml.addEventType(TYPE1);
        xml.addField("domain", domain);
        invokeLS(xml.getXml());
    }

    public void removeAbstractLinks() throws LookupServiceException {
        //first remove the existing links
        RemoveXml rem = new RemoveXml("AbstractLinkKey");
        invokeLS(rem.getXml());            
    }
    
    public void registerAbstractLinks(List<Link> links)
            throws LookupServiceException {
        
        //remove previous abstract entries
        removeAbstractLinks();
        
        XStream xstream = new XStream();
        xstream.alias("AbstractLink", Link.class);
        RegisterXml xml = null;
        xml = new RegisterXml("AbstractLinkKey", "TYPE 5", "abstract topology");
        timestamp = xml.addTimestamp();
        xml.addEventType(TYPE5);

        for (Link link : links) {
            String xmlString = xstream.toXML(link);
            xml.addXml("AbstractLink", xmlString);
        }

        invokeLS(xml.getXml());
    }

    /**
     * 
     * Registration of edge port identifier of interdomain link
     * 
     * @param startDomain
     *            - starting domain of the port
     * @param endDomain
     *            - end domain of the interdomain link
     * @param edgeport
     *            - public edge port identifier
     * @throws LookupServiceException
     */
    public void registerEdgePort(String startDomain, String endDomain,
            String edgeport) throws LookupServiceException {

        if (queryEdgePortForDuplication(startDomain, endDomain, edgeport) != null) {
            // The same port has already been registered
            removeEdgePort(startDomain, endDomain, edgeport);
        }

        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/EdgePORT ";
        // Generate key with timestamp
        String seckey = generateKey(firstkey);

        RegisterXml xml;
        xml = new RegisterXml(seckey, "TYPE 3", "PORT");
        xml.addDescription("Start domain, end domain, public edge port identifier");
        xml.addField("startDomain", startDomain);
        xml.addField("endDomain", endDomain);
        xml.addEventType(TYPE3);
        xml.addField("port", edgeport);
        invokeLS(xml.getXml());
    }

    /**
     * 
     * New LS instance initialization
     * 
     * @param fileName
     *            - file which contains content of HTTP Post
     * @throws LookupServiceException
     */
    public void newLS(String URL) throws LookupServiceException {

        // This is an auto generated primary key for the LS
        String firstkey = "http://reed.man.poznan.pl:8085/axis/services/LS ";
        // Generate key with timestamp
        String seckey = generateKey(firstkey);

        RegisterXml xml;
        xml = new RegisterXml(seckey, "TYPE 4", "LS");
        xml.addField("newLS", URL);
        xml.addEventType(TYPE4);
        invokeLS(xml.getXml());

    }

    /**
     * 
     * Removal of IDM location to domain name association
     * 
     * @param URL
     *            - IDM location
     * @throws LookupServiceException
     */
    public void removeIdm(String domain) throws LookupServiceException {
        String key = "";
        try {
            // use domain name to get the primary key according to the Exist
            // database
            key = this.findDomainkey(domain);
        } catch (NullPointerException e) {
            log.info("There is no domain " + domain + " to delete");
        }

        // XML query to sent to the lookup service
        RemoveXml rem = new RemoveXml(key);
        invokeLS(rem.getXml());
    }

    /**
     * 
     * Removal of edge port
     * 
     * @param startDomain
     *            - starting domain of the port
     * @param endDomain
     *            - end domain of the interdomain link
     * @param edgeport
     *            - public edge port identifier
     * @throws LookupServiceException
     */
    public void removeEdgePort(String startDomain, String endDomain,
            String edgeport) throws LookupServiceException {
        removeEdgePort(edgeport);
    }

    /**
     * 
     * Removal of edge port
     * 
     * @param edgeport
     *            - public edge port identifier
     * @throws LookupServiceException
     */
    public void removeEdgePort(String edgeport) throws LookupServiceException {
        String key = "";
        try {
            // use edge port to get the primary key according to the Exist
            // database
            key = this.findPortKey(edgeport);
        } catch (NullPointerException e) {
            log.info("There is no edge port " + edgeport + " to delete");
        }

        // XML query to sent to the lookup service
        RemoveXml rem = new RemoveXml(key);
        invokeLS(rem.getXml());
    }

    /**
     * Remove all edge ports with the specified starting domain
     * 
     * @param startDomain
     * @throws LookupServiceException
     */
    public void removeAllEdgePorts(String startDomain)
            throws LookupServiceException {
        List<String> edgePorts = findAllEdgePortKeys(startDomain);
        if (edgePorts != null) {
            for (String port : edgePorts) {
                this.removeEdgePort(port);
            }
        }
    }

    public void removeEndPort(String portIdentifier)
            throws LookupServiceException {
        String key = "";
        try {
            // use end port to get the primary key according to the Exist
            // database
            key = this.findEndPortkey(portIdentifier);
        } catch (NullPointerException e) {
            log.info("There is no end port " + portIdentifier + " to delete");
        }

        RemoveXml rem = new RemoveXml(key);
        invokeLS(rem.getXml());
    }

    /**
     * 
     * Query for location of IDM module
     * 
     * @param domain
     *            - domain name
     * @throws LookupServiceException
     * @return URL of IDM location, null if not found
     */
    public String queryIdmLocation(String domain) throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("url", "domain", domain);
        String s = xml.getXml();
        response = invokeLS(s);

        return getFirstMatch(response, "lookup:url");
    }

    public Long getTimeStamp() throws LookupServiceException {
        QueryXml xml;
        String responseString = "";
        String newline = System.getProperty("line.separator");
        ResponseXml response = null;
        xml = new QueryXml();
        xml.setWhere("eventType", String.valueOf(TYPE5));
        xml.setReturnType("timestamp");
        responseString = invokeLS(xml.getXml());
        response = new ResponseXml(responseString);
        responseString = response.getText("timestamp", 0);
        responseString = responseString.replace(newline, "");
        
        return new Long(responseString);        
    }

    
    public boolean topoIsUptodate() {      
        try {
            Long remoteTimestamp = getTimeStamp();
            if (remoteTimestamp > timestamp) {
                timestamp = remoteTimestamp;
                return false;
            }
        } catch (LookupServiceException e) {
            log.debug("Error checking if abstract topology is up to date: "
                    + e.getMessage());
        }

        return true;
    }

    public List<Link> getAbstractLink() throws LookupServiceException {
        List<Link> list = new ArrayList<Link>();
        QueryXml xml;
        XStream xstream = new XStream();
        xstream.alias("AbstractLink", Link.class);
        String newline = System.getProperty("line.separator");
        String responseString = "";

        xml = new QueryXml("AbstractLink");
        xml.setReturnType("AbstractLink");
        responseString = invokeLS(xml.getXml());

        ResponseXml response = new ResponseXml(responseString);

        String xmlString = response.getElement("AbstractLink", 0);
        int i = 0;
        while (xmlString != null) {
            xmlString = xmlString.replaceAll(newline, "");
            Link l = (Link) xstream.fromXML(xmlString);
            list.add(l);
            i++;
            xmlString = response.getElement("AbstractLink", i);
        }

        return list;
    }

    /**
     * 
     * Query friendly name of end port
     * 
     * @param endPoint
     *            - The identifier of the port
     * @throws LookupServiceException
     */
    public String queryFriendlyName(String endPoint)
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("friendlyName", "identifier", endPoint);
        String s = xml.getXml();
        response = invokeLS(s);

        return getFirstMatch(response, "lookup:friendlyName");
    }

    /**
     * 
     * Query for all end ports and their friendly names
     * 
     * @throws UnsupportedEncodingException
     */
    public ArrayList<String> queryAllFriendlyName()
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("friendlyName");
        String s = xml.getXml();
        response = invokeLS(s);

        return getMatches(response, "lookup:friendlyName");
    }

    /**
     * 
     * Query for edge port identifier of interdomain link
     * 
     * @param startDomain
     *            - this is the external domain that registered this link
     * @param endDomain
     *            - this is our local domain
     * @throws LookupServiceException
     */
    public ArrayList<String> queryEdgePort(String startDomain, String endDomain)
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("port", "startDomain", startDomain);
        xml.connectAnd("endDomain", endDomain);
        String s = xml.getXml();
        response = invokeLS(s);

        return getMatches(response, "lookup:port");
    }

    private String queryEdgePortForDuplication(String startDomain,
            String endDomain, String edgeport) throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("port", "startDomain", startDomain);
        xml.connectAnd("endDomain", endDomain);
        xml.connectAnd("port", edgeport);
        String s = xml.getXml();
        response = invokeLS(s);

        return getFirstMatch(response, "lookup:port");
    }

    /**
     * 
     * Query for edge port identifier of interdomain link
     * 
     * @param startDomain
     *            - domain connected to another domain
     * @throws LookupServiceException
     */
    public ArrayList<String> queryAllEdgePort(String startDomain)
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml("port", "startDomain", startDomain);
        String s = xml.getXml();
        response = invokeLS(s);

        return getMatches(response, "lookup:port");
    }

    /**
     * Update timestamp of an already registered IDM. If IDM is not found it is
     * now newly registered
     * 
     * @param domainName
     * 
     */
    public void updateIDMTimestamp(String domain, String URL)
            throws LookupServiceException {
        this.removeIdm(domain);
        this.registerIdm(domain, URL);
    }

    /**
     * 
     * @param key
     *            - key for each record
     * @return key with a Unix Timestamp
     */
    private String generateKey(String key) {
        return (new Long(System.currentTimeMillis())).toString();
    }

    /**
     * 
     * @param Element
     *            - node elements
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
     * @throws LookupServiceException
     */
    private String findDomainkey(String domain) throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml();
        xml.setWhere("domain", domain);
        xml.searchKeys();
        String s = xml.getXml();
        response = invokeLS(s);

        return getFirstMatch(response, "psservice:accessPoint");
    }

    /**
     * 
     * @param portIdentifier
     * @return EndPort key record in the database, or null if not found
     * @throws LookupServiceException
     */
    private String findEndPortkey(String portIdentifier)
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml();
        xml.setWhere("identifier", portIdentifier);
        xml.searchKeys();
        String s = xml.getXml();
        response = invokeLS(s);
        return getFirstMatch(response, "psservice:accessPoint");
    }

    /**
     * Find Edge Port key
     * 
     * @param edgeport
     * @return EdgePort key record in the database, or null if not found
     * @throws LookupServiceException
     */
    private String findPortKey(String edgeport) throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml();
        xml.setWhere("port", edgeport);
        xml.searchKeys();
        String s = xml.getXml();
        response = invokeLS(s);

        return getFirstMatch(response, "psservice:accessPoint");
    }

    /**
     * Find all Edge Port keys starting from specified domain
     * 
     * @param domain
     * @return All EdgePort key records in the database for the specified start
     *         domain, null if none found
     * @throws LookupServiceException
     */
    private List<String> findAllEdgePortKeys(String startDomain)
            throws LookupServiceException {
        QueryXml xml;
        String response = "";
        xml = new QueryXml();
        xml.setWhere("startDomain", startDomain);
        xml.searchKeys();
        String s = xml.getXml();
        response = invokeLS(s);

        return getMatches(response, "psservice:accessPoint");

    }
    
    public static boolean isLSavailable(String ls) {
        if ((ls == null) || ls.equalsIgnoreCase("none") || ls.equals("")) {
            return false;
        }
        // Check if it is a proper URL
        try {
            new URL(ls);
        } catch (MalformedURLException e) {
            Logger.getLogger(LookupService.class).debug(ls + " is not a proper URL for LS");
            return false;
        }
        return true;
    }
}
