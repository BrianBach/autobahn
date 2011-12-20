package net.geant.autobahn.lookup;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResponseXml extends XmlHandler {
    
    public ResponseXml(String xml) throws LookupServiceException {
        super(xml);
    }

    public boolean isSuccessful() {
        String s = "";
        s = doc.getElementsByTagName("nmwg:eventTypenmwg:eventType").item(0).getTextContent();
        
        if (s.startsWith("success")) {
            return true;
        }

        return false;
    }

    public String getElement(String name, int item) throws LookupServiceException {
        if (item < 0) {
            return null;
        }
        NodeList nodes = doc.getElementsByTagName(name);
        if (item >= nodes.getLength()) {
            return null;
        }
        Node node = nodes.item(item);
        return getXmlFromNode(node);
    }

    public String getText(String name, int item) {
        if (item < 0) {
            return null;
        }
        NodeList nodes = doc.getElementsByTagName("lookup:" + name);
        if (item >= nodes.getLength()) {
            return null;
        }
        Node node = nodes.item(item);
        return node.getTextContent();
    }

}
