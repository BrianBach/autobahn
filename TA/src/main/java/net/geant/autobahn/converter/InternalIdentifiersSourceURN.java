package net.geant.autobahn.converter;

public class InternalIdentifiersSourceURN implements InternalIdentifiersSource {

    String delimiter = ".";
    
    String domain;
    long count = 0;
    
    /**
     * Creates an instance of the identifiers source.
     * 
     * @param domain
     */
    public InternalIdentifiersSourceURN(String domain) {
        this.domain = domain;
        this.count = 0;
    }

    @Override
    public String generateNodeID() {
        return domain + delimiter + "Node" + delimiter + ++count;
    }

    @Override
    public String generatePortID() {
        return domain + delimiter + "Port" + delimiter + ++count;
    }

    @Override
    public String generateLinkID() {
        return domain + delimiter + "Link" + delimiter + ++count;
    }

}
