package net.geant.autobahn.lookup;

import java.io.File;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.geant.autobahn.resources.ResourcePath;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class RegisterXml extends XmlHandler {
    private final static String xmlFileName = "etc/xml/basicRegister.xml";
    static private Logger log = Logger.getLogger(RegisterXml.class);

    public RegisterXml(String key, String name, String servType)
            throws Exception {
        super(new File(new ResourcePath().getFullPath(xmlFileName)));
        NodeList nodes = doc.getElementsByTagName("psservice:accessPoint");
        // should exist exactly 1 node
        if (nodes.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new LookupServiceException("Error on xml sample file");
        }

        nodes.item(0).setTextContent(key);

        nodes = doc.getElementsByTagName("psservice:serviceName");
        if (nodes.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new LookupServiceException("Error on xml sample file");
        }
        nodes.item(0).setTextContent(name);

        nodes = doc.getElementsByTagName("psservice:serviceType");
        if (nodes.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new LookupServiceException("error on xml sample file");
        }
        nodes.item(0).setTextContent(servType);
    }

    void addField(String name, String value) throws Exception {
        NodeList l = doc.getElementsByTagName("nmwgt:interface");

        // should exist exactly 1 node
        if (l == null || l.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new Exception("Error on xml sample file");
        }
        
        Node nod = l.item(0);
        Node newNode = doc.createElement("lookup:" + name);
        newNode.setTextContent(value);
        nod.appendChild(newNode);
    }

    /*
     * constracts a new node using the given name and sets the xml contents of
     * the node using the given xml string
     */

    void addXml(String name, String xml) throws Exception {
        NodeList l = doc.getElementsByTagName("nmwgt:interface");

        // should exist exactly 1 node
        if (l == null || l.getLength() != 1) {
            log.error("error on xml sample file");
            throw new Exception("error on xml sample file");
        }

        Node nod = l.item(0);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));

        Document d = builder.parse(is);
        Node n = d.getFirstChild();
        if (n == null) {
            return;
        }
        
        n = doc.importNode(n, true);
        Node newNode = doc.createElement("lookup:" + name);
        newNode.appendChild(n);
        nod.appendChild(newNode);
    }

    public Long addTimestamp() throws Exception {
        Long timestamp = new Long(System.currentTimeMillis());
        this.addField("timestamp", timestamp.toString());

        return timestamp;
    }

    void addEventType(int type) throws Exception {
        this.addField("eventType", String.valueOf(type));
    }

    void addDescription(String descr) throws Exception {

        NodeList l = doc.getElementsByTagName("psservice:serviceDescription");
        if (l.getLength() != 1) {
            log.error("error on xml sample file");
            throw new Exception("error on xml sample file");
        }
        Node nod = l.item(0);
        nod.setTextContent(descr);
    }

};
