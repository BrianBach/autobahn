
package org.oasis_open.docs.wsn.br_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.EndpointReferenceType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.oasis_open.docs.wsn.br_2 package. 
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

    private final static QName _Demand_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "Demand");
    private final static QName _RequiresRegistration_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "RequiresRegistration");
    private final static QName _ResourceNotDestroyedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "ResourceNotDestroyedFault");
    private final static QName _PublisherRegistrationRejectedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherRegistrationRejectedFault");
    private final static QName _PublisherRegistrationFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherRegistrationFailedFault");
    private final static QName _PublisherReference_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherReference");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.oasis_open.docs.wsn.br_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PublisherRegistrationRejectedFaultType }
     * 
     */
    public PublisherRegistrationRejectedFaultType createPublisherRegistrationRejectedFaultType() {
        return new PublisherRegistrationRejectedFaultType();
    }

    /**
     * Create an instance of {@link DestroyRegistrationResponse }
     * 
     */
    public DestroyRegistrationResponse createDestroyRegistrationResponse() {
        return new DestroyRegistrationResponse();
    }

    /**
     * Create an instance of {@link PublisherRegistrationFailedFaultType }
     * 
     */
    public PublisherRegistrationFailedFaultType createPublisherRegistrationFailedFaultType() {
        return new PublisherRegistrationFailedFaultType();
    }

    /**
     * Create an instance of {@link DestroyRegistration }
     * 
     */
    public DestroyRegistration createDestroyRegistration() {
        return new DestroyRegistration();
    }

    /**
     * Create an instance of {@link RegisterPublisher }
     * 
     */
    public RegisterPublisher createRegisterPublisher() {
        return new RegisterPublisher();
    }

    /**
     * Create an instance of {@link ResourceNotDestroyedFaultType }
     * 
     */
    public ResourceNotDestroyedFaultType createResourceNotDestroyedFaultType() {
        return new ResourceNotDestroyedFaultType();
    }

    /**
     * Create an instance of {@link RegisterPublisherResponse }
     * 
     */
    public RegisterPublisherResponse createRegisterPublisherResponse() {
        return new RegisterPublisherResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "Demand")
    public JAXBElement<Boolean> createDemand(Boolean value) {
        return new JAXBElement<Boolean>(_Demand_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "RequiresRegistration")
    public JAXBElement<Boolean> createRequiresRegistration(Boolean value) {
        return new JAXBElement<Boolean>(_RequiresRegistration_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceNotDestroyedFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "ResourceNotDestroyedFault")
    public JAXBElement<ResourceNotDestroyedFaultType> createResourceNotDestroyedFault(ResourceNotDestroyedFaultType value) {
        return new JAXBElement<ResourceNotDestroyedFaultType>(_ResourceNotDestroyedFault_QNAME, ResourceNotDestroyedFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PublisherRegistrationRejectedFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "PublisherRegistrationRejectedFault")
    public JAXBElement<PublisherRegistrationRejectedFaultType> createPublisherRegistrationRejectedFault(PublisherRegistrationRejectedFaultType value) {
        return new JAXBElement<PublisherRegistrationRejectedFaultType>(_PublisherRegistrationRejectedFault_QNAME, PublisherRegistrationRejectedFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PublisherRegistrationFailedFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "PublisherRegistrationFailedFault")
    public JAXBElement<PublisherRegistrationFailedFaultType> createPublisherRegistrationFailedFault(PublisherRegistrationFailedFaultType value) {
        return new JAXBElement<PublisherRegistrationFailedFaultType>(_PublisherRegistrationFailedFault_QNAME, PublisherRegistrationFailedFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndpointReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wsn/br-2", name = "PublisherReference")
    public JAXBElement<EndpointReferenceType> createPublisherReference(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_PublisherReference_QNAME, EndpointReferenceType.class, null, value);
    }

}
