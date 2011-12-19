package net.geant.autobahn.lookup;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandler {
    protected Document doc;

    public XmlHandler(File file) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
    }

    public XmlHandler(String xml) throws ParserConfigurationException,
            SAXException, IOException {
        doc = xmlFromString(xml);
    }

    static public Document xmlFromString(String xml)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    static public String getXmlFromNode(Node n) throws Exception {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transFactory
                .newTransformer();
        StringWriter buffer = new StringWriter();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(n), new StreamResult(buffer));
        String str = buffer.toString();
        return str;
    }

    public String getXml() throws Exception {
        return getXmlFromNode(doc);
    }
}
