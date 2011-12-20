package net.geant.autobahn.lookup;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandler {
    protected Document doc;
    private Logger log = Logger.getLogger(this.getClass());

    public XmlHandler(File file) throws LookupServiceException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
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
    }

    public XmlHandler(String xml) throws LookupServiceException {
        doc = xmlFromString(xml);
    }

    static public Document xmlFromString(String xml)
            throws LookupServiceException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Logger.getLogger(XmlHandler.class).error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }
        InputSource is = new InputSource(new StringReader(xml));
        try {
            return builder.parse(is);
        } catch (SAXException e) {
            Logger.getLogger(XmlHandler.class).error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(XmlHandler.class).error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }
    }

    static public String getXmlFromNode(Node n) throws LookupServiceException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer;
        try {
            transformer = transFactory.newTransformer();
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(n), new StreamResult(buffer));
            String str = buffer.toString();
            return str;
        } catch (TransformerException e) {
            Logger.getLogger(XmlHandler.class).error("LS exception: ", e);
            throw new LookupServiceException(e.getMessage());
        }
    }

    public String getXml() throws LookupServiceException {
        return getXmlFromNode(doc);
    }
}
