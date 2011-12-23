package net.geant.autobahn.lookup;

import net.geant.autobahn.resources.ResourcePath;

import java.io.File;

public class EchoXml extends XmlHandler  
{
    public static String xmlFileName = "etc/xml/EchoRequest.xml";

    public EchoXml() throws LookupServiceException 
    {
        super( new File( new ResourcePath().getFullPath(xmlFileName) ) ) ;   
    }
}
