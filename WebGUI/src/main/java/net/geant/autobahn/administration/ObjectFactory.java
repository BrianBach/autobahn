
package net.geant.autobahn.administration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.administration package. 
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

    private final static QName _SetPropertiesResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "setPropertiesResponse");
    private final static QName _GetServicesResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getServicesResponse");
    private final static QName _SetProperties_QNAME = new QName("http://administration.autobahn.geant.net/", "setProperties");
    private final static QName _GetReservation_QNAME = new QName("http://administration.autobahn.geant.net/", "getReservation");
    private final static QName _GetServices_QNAME = new QName("http://administration.autobahn.geant.net/", "getServices");
    private final static QName _GetPropertiesResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getPropertiesResponse");
    private final static QName _GetLogResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getLogResponse");
    private final static QName _GetService_QNAME = new QName("http://administration.autobahn.geant.net/", "getService");
    private final static QName _GetStatusResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getStatusResponse");
    private final static QName _GetTopologyResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getTopologyResponse");
    private final static QName _GetStatus_QNAME = new QName("http://administration.autobahn.geant.net/", "getStatus");
    private final static QName _GetLog_QNAME = new QName("http://administration.autobahn.geant.net/", "getLog");
    private final static QName _GetServiceResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getServiceResponse");
    private final static QName _GetReservationResponse_QNAME = new QName("http://administration.autobahn.geant.net/", "getReservationResponse");
    private final static QName _GetTopology_QNAME = new QName("http://administration.autobahn.geant.net/", "getTopology");
    private final static QName _GetProperties_QNAME = new QName("http://administration.autobahn.geant.net/", "getProperties");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.administration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Neighbor }
     * 
     */
    public Neighbor createNeighbor() {
        return new Neighbor();
    }

    /**
     * Create an instance of {@link SetPropertiesResponse }
     * 
     */
    public SetPropertiesResponse createSetPropertiesResponse() {
        return new SetPropertiesResponse();
    }

    /**
     * Create an instance of {@link GetTopologyResponse }
     * 
     */
    public GetTopologyResponse createGetTopologyResponse() {
        return new GetTopologyResponse();
    }

    /**
     * Create an instance of {@link GetProperties }
     * 
     */
    public GetProperties createGetProperties() {
        return new GetProperties();
    }

    /**
     * Create an instance of {@link GetTopology }
     * 
     */
    public GetTopology createGetTopology() {
        return new GetTopology();
    }

    /**
     * Create an instance of {@link GetLog }
     * 
     */
    public GetLog createGetLog() {
        return new GetLog();
    }

    /**
     * Create an instance of {@link GetStatus }
     * 
     */
    public GetStatus createGetStatus() {
        return new GetStatus();
    }

    /**
     * Create an instance of {@link GetStatusResponse }
     * 
     */
    public GetStatusResponse createGetStatusResponse() {
        return new GetStatusResponse();
    }

    /**
     * Create an instance of {@link GetReservationResponse }
     * 
     */
    public GetReservationResponse createGetReservationResponse() {
        return new GetReservationResponse();
    }

    /**
     * Create an instance of {@link GetServices }
     * 
     */
    public GetServices createGetServices() {
        return new GetServices();
    }

    /**
     * Create an instance of {@link GetLogResponse }
     * 
     */
    public GetLogResponse createGetLogResponse() {
        return new GetLogResponse();
    }

    /**
     * Create an instance of {@link GetService }
     * 
     */
    public GetService createGetService() {
        return new GetService();
    }

    /**
     * Create an instance of {@link SetProperties }
     * 
     */
    public SetProperties createSetProperties() {
        return new SetProperties();
    }

    /**
     * Create an instance of {@link GetReservation }
     * 
     */
    public GetReservation createGetReservation() {
        return new GetReservation();
    }

    /**
     * Create an instance of {@link GetPropertiesResponse }
     * 
     */
    public GetPropertiesResponse createGetPropertiesResponse() {
        return new GetPropertiesResponse();
    }

    /**
     * Create an instance of {@link GetServicesResponse }
     * 
     */
    public GetServicesResponse createGetServicesResponse() {
        return new GetServicesResponse();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link KeyValue }
     * 
     */
    public KeyValue createKeyValue() {
        return new KeyValue();
    }

    /**
     * Create an instance of {@link GetServiceResponse }
     * 
     */
    public GetServiceResponse createGetServiceResponse() {
        return new GetServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "setPropertiesResponse")
    public JAXBElement<SetPropertiesResponse> createSetPropertiesResponse(SetPropertiesResponse value) {
        return new JAXBElement<SetPropertiesResponse>(_SetPropertiesResponse_QNAME, SetPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getServicesResponse")
    public JAXBElement<GetServicesResponse> createGetServicesResponse(GetServicesResponse value) {
        return new JAXBElement<GetServicesResponse>(_GetServicesResponse_QNAME, GetServicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "setProperties")
    public JAXBElement<SetProperties> createSetProperties(SetProperties value) {
        return new JAXBElement<SetProperties>(_SetProperties_QNAME, SetProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getReservation")
    public JAXBElement<GetReservation> createGetReservation(GetReservation value) {
        return new JAXBElement<GetReservation>(_GetReservation_QNAME, GetReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getServices")
    public JAXBElement<GetServices> createGetServices(GetServices value) {
        return new JAXBElement<GetServices>(_GetServices_QNAME, GetServices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getPropertiesResponse")
    public JAXBElement<GetPropertiesResponse> createGetPropertiesResponse(GetPropertiesResponse value) {
        return new JAXBElement<GetPropertiesResponse>(_GetPropertiesResponse_QNAME, GetPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getLogResponse")
    public JAXBElement<GetLogResponse> createGetLogResponse(GetLogResponse value) {
        return new JAXBElement<GetLogResponse>(_GetLogResponse_QNAME, GetLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getService")
    public JAXBElement<GetService> createGetService(GetService value) {
        return new JAXBElement<GetService>(_GetService_QNAME, GetService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getStatusResponse")
    public JAXBElement<GetStatusResponse> createGetStatusResponse(GetStatusResponse value) {
        return new JAXBElement<GetStatusResponse>(_GetStatusResponse_QNAME, GetStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getTopologyResponse")
    public JAXBElement<GetTopologyResponse> createGetTopologyResponse(GetTopologyResponse value) {
        return new JAXBElement<GetTopologyResponse>(_GetTopologyResponse_QNAME, GetTopologyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getStatus")
    public JAXBElement<GetStatus> createGetStatus(GetStatus value) {
        return new JAXBElement<GetStatus>(_GetStatus_QNAME, GetStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getLog")
    public JAXBElement<GetLog> createGetLog(GetLog value) {
        return new JAXBElement<GetLog>(_GetLog_QNAME, GetLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getServiceResponse")
    public JAXBElement<GetServiceResponse> createGetServiceResponse(GetServiceResponse value) {
        return new JAXBElement<GetServiceResponse>(_GetServiceResponse_QNAME, GetServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getReservationResponse")
    public JAXBElement<GetReservationResponse> createGetReservationResponse(GetReservationResponse value) {
        return new JAXBElement<GetReservationResponse>(_GetReservationResponse_QNAME, GetReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getTopology")
    public JAXBElement<GetTopology> createGetTopology(GetTopology value) {
        return new JAXBElement<GetTopology>(_GetTopology_QNAME, GetTopology.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://administration.autobahn.geant.net/", name = "getProperties")
    public JAXBElement<GetProperties> createGetProperties(GetProperties value) {
        return new JAXBElement<GetProperties>(_GetProperties_QNAME, GetProperties.class, null, value);
    }

}
