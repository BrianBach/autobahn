
package net.geant.autobahn.gui;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.gui package. 
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

    private final static QName _Update_QNAME = new QName("http://gui.autobahn.geant.net/", "update");
    private final static QName _ReservationChangedResponse_QNAME = new QName("http://gui.autobahn.geant.net/", "reservationChangedResponse");
    private final static QName _UpdateResponse_QNAME = new QName("http://gui.autobahn.geant.net/", "updateResponse");
    private final static QName _StatusUpdated_QNAME = new QName("http://gui.autobahn.geant.net/", "statusUpdated");
    private final static QName _ReservationChanged_QNAME = new QName("http://gui.autobahn.geant.net/", "reservationChanged");
    private final static QName _StatusUpdatedResponse_QNAME = new QName("http://gui.autobahn.geant.net/", "statusUpdatedResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.gui
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReservationChangedResponse }
     * 
     */
    public ReservationChangedResponse createReservationChangedResponse() {
        return new ReservationChangedResponse();
    }

    /**
     * Create an instance of {@link StatusUpdatedResponse }
     * 
     */
    public StatusUpdatedResponse createStatusUpdatedResponse() {
        return new StatusUpdatedResponse();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     * 
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link StatusUpdated }
     * 
     */
    public StatusUpdated createStatusUpdated() {
        return new StatusUpdated();
    }

    /**
     * Create an instance of {@link ReservationChanged }
     * 
     */
    public ReservationChanged createReservationChanged() {
        return new ReservationChanged();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationChangedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "reservationChangedResponse")
    public JAXBElement<ReservationChangedResponse> createReservationChangedResponse(ReservationChangedResponse value) {
        return new JAXBElement<ReservationChangedResponse>(_ReservationChangedResponse_QNAME, ReservationChangedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "updateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusUpdated }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "statusUpdated")
    public JAXBElement<StatusUpdated> createStatusUpdated(StatusUpdated value) {
        return new JAXBElement<StatusUpdated>(_StatusUpdated_QNAME, StatusUpdated.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationChanged }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "reservationChanged")
    public JAXBElement<ReservationChanged> createReservationChanged(ReservationChanged value) {
        return new JAXBElement<ReservationChanged>(_ReservationChanged_QNAME, ReservationChanged.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusUpdatedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gui.autobahn.geant.net/", name = "statusUpdatedResponse")
    public JAXBElement<StatusUpdatedResponse> createStatusUpdatedResponse(StatusUpdatedResponse value) {
        return new JAXBElement<StatusUpdatedResponse>(_StatusUpdatedResponse_QNAME, StatusUpdatedResponse.class, null, value);
    }

}
