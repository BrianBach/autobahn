package net.geant.autobahn.lookup;

import java.io.File;

import net.geant.autobahn.resources.ResourcePath;

import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

public class RemoveXml extends XmlHandler {
    static final String xmlFileName = "etc/xml/basicRemove.xml";
    static private Logger log = Logger.getLogger(RemoveXml.class);

    public RemoveXml(String key) throws Exception {
        super(new File(new ResourcePath().getFullPath(xmlFileName)));
        NodeList l = doc.getElementsByTagName("nmwg:parameter");
        // should exist exactly 1 node
        if (l == null || l.getLength() != 1) {
            log.error("Error on xml sample file");
            throw new Exception("Error on xml sample file");
        }
        l.item(0).setTextContent(key);
    }

    public String getXml() throws Exception {
        String s = super.getXml();
        
        return s;
    }

}
