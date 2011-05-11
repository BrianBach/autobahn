
package net.geant2.cnis.autobahn.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.cnis.autobahn.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Request_QNAME = new QName("http://cnis.geant2.net/autobahn/xml", "request");
    private final static QName _Response_QNAME = new QName("http://cnis.geant2.net/autobahn/xml", "response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.cnis.autobahn.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CnisToAutobahnResponse }
     * 
     */
    public CnisToAutobahnResponse createCnisToAutobahnResponse() {
        return new CnisToAutobahnResponse();
    }

    /**
     * Create an instance of {@link AutobahnToCnisRequest }
     * 
     */
    public AutobahnToCnisRequest createAutobahnToCnisRequest() {
        return new AutobahnToCnisRequest();
    }

    /**
     * Create an instance of {@link IntegerValue }
     * 
     */
    public IntegerValue createIntegerValue() {
        return new IntegerValue();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link InterfaceType }
     * 
     */
    public InterfaceType createInterfaceType() {
        return new InterfaceType();
    }

    /**
     * Create an instance of {@link PruningConditions }
     * 
     */
    public PruningConditions createPruningConditions() {
        return new PruningConditions();
    }

    /**
     * Create an instance of {@link DecimalValue }
     * 
     */
    public DecimalValue createDecimalValue() {
        return new DecimalValue();
    }

    /**
     * Create an instance of {@link VlanRange }
     * 
     */
    public VlanRange createVlanRange() {
        return new VlanRange();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutobahnToCnisRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cnis.geant2.net/autobahn/xml", name = "request")
    public JAXBElement<AutobahnToCnisRequest> createRequest(AutobahnToCnisRequest value) {
        return new JAXBElement<AutobahnToCnisRequest>(_Request_QNAME, AutobahnToCnisRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CnisToAutobahnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cnis.geant2.net/autobahn/xml", name = "response")
    public JAXBElement<CnisToAutobahnResponse> createResponse(CnisToAutobahnResponse value) {
        return new JAXBElement<CnisToAutobahnResponse>(_Response_QNAME, CnisToAutobahnResponse.class, null, value);
    }

}
