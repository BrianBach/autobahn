package net.geant.autobahn.lookup;

import net.geant.autobahn.resources.ResourcePath;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class EchoXml extends XmlHandler  
{
    private final static String xmlFileName = "etc/xml/EchoRequest.xml";

    public EchoXml() throws ParserConfigurationException,SAXException, IOException 
    {
        super( new File( new ResourcePath().getFullPath(xmlFileName) ) ) ;   
    }
}
