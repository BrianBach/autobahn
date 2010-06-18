
package net.geant.autobahn.tool;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.tool package. 
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

    private final static QName _AddReservationResponse_QNAME = new QName("http://tool.autobahn.geant.net/", "addReservationResponse");
    private final static QName _AAIException_QNAME = new QName("http://tool.autobahn.geant.net/", "AAIException");
    private final static QName _SystemException_QNAME = new QName("http://tool.autobahn.geant.net/", "SystemException");
    private final static QName _AddReservation_QNAME = new QName("http://tool.autobahn.geant.net/", "addReservation");
    private final static QName _RemoveReservationResponse_QNAME = new QName("http://tool.autobahn.geant.net/", "removeReservationResponse");
    private final static QName _RemoveReservation_QNAME = new QName("http://tool.autobahn.geant.net/", "removeReservation");
    private final static QName _RequestException_QNAME = new QName("http://tool.autobahn.geant.net/", "RequestException");
    private final static QName _ReservationNotFoundException_QNAME = new QName("http://tool.autobahn.geant.net/", "ReservationNotFoundException");
    private final static QName _ResourceNotFoundException_QNAME = new QName("http://tool.autobahn.geant.net/", "ResourceNotFoundException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.tool
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddReservation }
     * 
     */
    public AddReservation createAddReservation() {
        return new AddReservation();
    }

    /**
     * Create an instance of {@link AAIException }
     * 
     */
    public AAIException createAAIException() {
        return new AAIException();
    }

    /**
     * Create an instance of {@link AddReservationResponse }
     * 
     */
    public AddReservationResponse createAddReservationResponse() {
        return new AddReservationResponse();
    }

    /**
     * Create an instance of {@link RemoveReservationResponse }
     * 
     */
    public RemoveReservationResponse createRemoveReservationResponse() {
        return new RemoveReservationResponse();
    }

    /**
     * Create an instance of {@link RemoveReservation }
     * 
     */
    public RemoveReservation createRemoveReservation() {
        return new RemoveReservation();
    }

    /**
     * Create an instance of {@link ReservationNotFoundException }
     * 
     */
    public ReservationNotFoundException createReservationNotFoundException() {
        return new ReservationNotFoundException();
    }

    /**
     * Create an instance of {@link RequestException }
     * 
     */
    public RequestException createRequestException() {
        return new RequestException();
    }

    /**
     * Create an instance of {@link ResourceNotFoundException }
     * 
     */
    public ResourceNotFoundException createResourceNotFoundException() {
        return new ResourceNotFoundException();
    }

    /**
     * Create an instance of {@link SystemException }
     * 
     */
    public SystemException createSystemException() {
        return new SystemException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "addReservationResponse")
    public JAXBElement<AddReservationResponse> createAddReservationResponse(AddReservationResponse value) {
        return new JAXBElement<AddReservationResponse>(_AddReservationResponse_QNAME, AddReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AAIException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "AAIException")
    public JAXBElement<AAIException> createAAIException(AAIException value) {
        return new JAXBElement<AAIException>(_AAIException_QNAME, AAIException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "SystemException")
    public JAXBElement<SystemException> createSystemException(SystemException value) {
        return new JAXBElement<SystemException>(_SystemException_QNAME, SystemException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "addReservation")
    public JAXBElement<AddReservation> createAddReservation(AddReservation value) {
        return new JAXBElement<AddReservation>(_AddReservation_QNAME, AddReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "removeReservationResponse")
    public JAXBElement<RemoveReservationResponse> createRemoveReservationResponse(RemoveReservationResponse value) {
        return new JAXBElement<RemoveReservationResponse>(_RemoveReservationResponse_QNAME, RemoveReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "removeReservation")
    public JAXBElement<RemoveReservation> createRemoveReservation(RemoveReservation value) {
        return new JAXBElement<RemoveReservation>(_RemoveReservation_QNAME, RemoveReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "RequestException")
    public JAXBElement<RequestException> createRequestException(RequestException value) {
        return new JAXBElement<RequestException>(_RequestException_QNAME, RequestException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "ReservationNotFoundException")
    public JAXBElement<ReservationNotFoundException> createReservationNotFoundException(ReservationNotFoundException value) {
        return new JAXBElement<ReservationNotFoundException>(_ReservationNotFoundException_QNAME, ReservationNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tool.autobahn.geant.net/", name = "ResourceNotFoundException")
    public JAXBElement<ResourceNotFoundException> createResourceNotFoundException(ResourceNotFoundException value) {
        return new JAXBElement<ResourceNotFoundException>(_ResourceNotFoundException_QNAME, ResourceNotFoundException.class, null, value);
    }

}
