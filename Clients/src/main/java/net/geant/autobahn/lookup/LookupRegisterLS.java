package net.geant.autobahn.lookup;


import net.geant.autobahn.resources.ResourcePath;

public class LookupRegisterLS {

    public static void main(String args[]) throws Exception {
        //Set paths for Lookup Client
        ResourcePath resource = new ResourcePath(false);
        QueryXml.xmlFileName = "src/main/resources/etc/xml/basicQuery.xml";
        EchoXml.xmlFileName = "src/main/resources/etc/xml/EchoRequest.xml";
        RegisterXml.xmlFileName = "src/main/resources/etc/xml/basicRegister.xml";
        RemoveXml.xmlFileName = "src/main/resources/etc/xml/basicRemove.xml";
        try {
        	LookupService lookup = new LookupService("http://150.140.8.55:8080/perfsonar-java-xml-ls/services/LookupService");
            lookup.updateLookupServices("http://150.140.8.52:8080/perfsonar-java-xml-ls/services/LookupService");
            lookup.updateLookupServices("http://150.140.8.53:8080/perfsonar-java-xml-ls/services/LookupService");
            System.out.println("Finished...");
            
        } catch (Exception lse) {
            lse.printStackTrace();
        }

    }
}
