
package net.geant2.cnis.autobahn.xml.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.cnis.autobahn.xml.common package. 
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

    private final static QName _Tags_QNAME = new QName("http://cnis.geant2.net/autobahn/xml/common", "tags");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.cnis.autobahn.xml.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Tags }
     * 
     */
    public Tags createTags() {
        return new Tags();
    }

    /**
     * Create an instance of {@link Domain }
     * 
     */
    public Domain createDomain() {
        return new Domain();
    }

    /**
     * Create an instance of {@link Tag }
     * 
     */
    public Tag createTag() {
        return new Tag();
    }

    /**
     * Create an instance of {@link GeoLocation }
     * 
     */
    public GeoLocation createGeoLocation() {
        return new GeoLocation();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Tags }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cnis.geant2.net/autobahn/xml/common", name = "tags")
    public JAXBElement<Tags> createTags(Tags value) {
        return new JAXBElement<Tags>(_Tags_QNAME, Tags.class, null, value);
    }

}
