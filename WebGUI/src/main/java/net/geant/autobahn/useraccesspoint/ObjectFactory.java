
package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.useraccesspoint package. 
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

    private final static QName _QueryServiceResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "queryServiceResponse");
    private final static QName _CancelService_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "cancelService");
    private final static QName _ModifyReservation_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "modifyReservation");
    private final static QName _GetAllClientPortsResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "getAllClientPortsResponse");
    private final static QName _CheckReservationPossibility_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "checkReservationPossibility");
    private final static QName _SubmitService_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "submitService");
    private final static QName _UserAccessPointException_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "UserAccessPointException");
    private final static QName _GetAllClientPorts_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "getAllClientPorts");
    private final static QName _SubmitServiceAndRegister_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "submitServiceAndRegister");
    private final static QName _QueryService_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "queryService");
    private final static QName _CancelServiceResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "cancelServiceResponse");
    private final static QName _GetDomainClientPortsResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "getDomainClientPortsResponse");
    private final static QName _SubmitServiceAndRegisterResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "submitServiceAndRegisterResponse");
    private final static QName _CheckReservationPossibilityResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "checkReservationPossibilityResponse");
    private final static QName _RegisterCallbackResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "registerCallbackResponse");
    private final static QName _RegisterCallback_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "registerCallback");
    private final static QName _GetDomainClientPorts_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "getDomainClientPorts");
    private final static QName _SubmitServiceResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "submitServiceResponse");
    private final static QName _ModifyReservationResponse_QNAME = new QName("http://useraccesspoint.autobahn.geant.net/", "modifyReservationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.useraccesspoint
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDomainClientPortsResponse }
     * 
     */
    public GetDomainClientPortsResponse createGetDomainClientPortsResponse() {
        return new GetDomainClientPortsResponse();
    }

    /**
     * Create an instance of {@link GetAllClientPorts }
     * 
     */
    public GetAllClientPorts createGetAllClientPorts() {
        return new GetAllClientPorts();
    }

    /**
     * Create an instance of {@link UserAccessPointException }
     * 
     */
    public UserAccessPointException createUserAccessPointException() {
        return new UserAccessPointException();
    }

    /**
     * Create an instance of {@link ReservationRequest }
     * 
     */
    public ReservationRequest createReservationRequest() {
        return new ReservationRequest();
    }

    /**
     * Create an instance of {@link GetAllClientPortsResponse }
     * 
     */
    public GetAllClientPortsResponse createGetAllClientPortsResponse() {
        return new GetAllClientPortsResponse();
    }

    /**
     * Create an instance of {@link CancelService }
     * 
     */
    public CancelService createCancelService() {
        return new CancelService();
    }

    /**
     * Create an instance of {@link CheckReservationPossibilityResponse }
     * 
     */
    public CheckReservationPossibilityResponse createCheckReservationPossibilityResponse() {
        return new CheckReservationPossibilityResponse();
    }

    /**
     * Create an instance of {@link CheckReservationPossibility }
     * 
     */
    public CheckReservationPossibility createCheckReservationPossibility() {
        return new CheckReservationPossibility();
    }

    /**
     * Create an instance of {@link ModifyReservation }
     * 
     */
    public ModifyReservation createModifyReservation() {
        return new ModifyReservation();
    }

    /**
     * Create an instance of {@link GetDomainClientPorts }
     * 
     */
    public GetDomainClientPorts createGetDomainClientPorts() {
        return new GetDomainClientPorts();
    }

    /**
     * Create an instance of {@link ModifyReservationResponse }
     * 
     */
    public ModifyReservationResponse createModifyReservationResponse() {
        return new ModifyReservationResponse();
    }

    /**
     * Create an instance of {@link RegisterCallback }
     * 
     */
    public RegisterCallback createRegisterCallback() {
        return new RegisterCallback();
    }

    /**
     * Create an instance of {@link SubmitServiceAndRegister }
     * 
     */
    public SubmitServiceAndRegister createSubmitServiceAndRegister() {
        return new SubmitServiceAndRegister();
    }

    /**
     * Create an instance of {@link ModifyRequest }
     * 
     */
    public ModifyRequest createModifyRequest() {
        return new ModifyRequest();
    }

    /**
     * Create an instance of {@link CancelServiceResponse }
     * 
     */
    public CancelServiceResponse createCancelServiceResponse() {
        return new CancelServiceResponse();
    }

    /**
     * Create an instance of {@link QueryServiceResponse }
     * 
     */
    public QueryServiceResponse createQueryServiceResponse() {
        return new QueryServiceResponse();
    }

    /**
     * Create an instance of {@link QueryService }
     * 
     */
    public QueryService createQueryService() {
        return new QueryService();
    }

    /**
     * Create an instance of {@link RegisterCallbackResponse }
     * 
     */
    public RegisterCallbackResponse createRegisterCallbackResponse() {
        return new RegisterCallbackResponse();
    }

    /**
     * Create an instance of {@link ServiceResponse }
     * 
     */
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    /**
     * Create an instance of {@link ServiceRequest }
     * 
     */
    public ServiceRequest createServiceRequest() {
        return new ServiceRequest();
    }

    /**
     * Create an instance of {@link SubmitService }
     * 
     */
    public SubmitService createSubmitService() {
        return new SubmitService();
    }

    /**
     * Create an instance of {@link ReservationResponse }
     * 
     */
    public ReservationResponse createReservationResponse() {
        return new ReservationResponse();
    }

    /**
     * Create an instance of {@link SubmitServiceAndRegisterResponse }
     * 
     */
    public SubmitServiceAndRegisterResponse createSubmitServiceAndRegisterResponse() {
        return new SubmitServiceAndRegisterResponse();
    }

    /**
     * Create an instance of {@link SubmitServiceResponse }
     * 
     */
    public SubmitServiceResponse createSubmitServiceResponse() {
        return new SubmitServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "queryServiceResponse")
    public JAXBElement<QueryServiceResponse> createQueryServiceResponse(QueryServiceResponse value) {
        return new JAXBElement<QueryServiceResponse>(_QueryServiceResponse_QNAME, QueryServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "cancelService")
    public JAXBElement<CancelService> createCancelService(CancelService value) {
        return new JAXBElement<CancelService>(_CancelService_QNAME, CancelService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "modifyReservation")
    public JAXBElement<ModifyReservation> createModifyReservation(ModifyReservation value) {
        return new JAXBElement<ModifyReservation>(_ModifyReservation_QNAME, ModifyReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClientPortsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "getAllClientPortsResponse")
    public JAXBElement<GetAllClientPortsResponse> createGetAllClientPortsResponse(GetAllClientPortsResponse value) {
        return new JAXBElement<GetAllClientPortsResponse>(_GetAllClientPortsResponse_QNAME, GetAllClientPortsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckReservationPossibility }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "checkReservationPossibility")
    public JAXBElement<CheckReservationPossibility> createCheckReservationPossibility(CheckReservationPossibility value) {
        return new JAXBElement<CheckReservationPossibility>(_CheckReservationPossibility_QNAME, CheckReservationPossibility.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "submitService")
    public JAXBElement<SubmitService> createSubmitService(SubmitService value) {
        return new JAXBElement<SubmitService>(_SubmitService_QNAME, SubmitService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAccessPointException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "UserAccessPointException")
    public JAXBElement<UserAccessPointException> createUserAccessPointException(UserAccessPointException value) {
        return new JAXBElement<UserAccessPointException>(_UserAccessPointException_QNAME, UserAccessPointException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClientPorts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "getAllClientPorts")
    public JAXBElement<GetAllClientPorts> createGetAllClientPorts(GetAllClientPorts value) {
        return new JAXBElement<GetAllClientPorts>(_GetAllClientPorts_QNAME, GetAllClientPorts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitServiceAndRegister }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "submitServiceAndRegister")
    public JAXBElement<SubmitServiceAndRegister> createSubmitServiceAndRegister(SubmitServiceAndRegister value) {
        return new JAXBElement<SubmitServiceAndRegister>(_SubmitServiceAndRegister_QNAME, SubmitServiceAndRegister.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "queryService")
    public JAXBElement<QueryService> createQueryService(QueryService value) {
        return new JAXBElement<QueryService>(_QueryService_QNAME, QueryService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "cancelServiceResponse")
    public JAXBElement<CancelServiceResponse> createCancelServiceResponse(CancelServiceResponse value) {
        return new JAXBElement<CancelServiceResponse>(_CancelServiceResponse_QNAME, CancelServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainClientPortsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "getDomainClientPortsResponse")
    public JAXBElement<GetDomainClientPortsResponse> createGetDomainClientPortsResponse(GetDomainClientPortsResponse value) {
        return new JAXBElement<GetDomainClientPortsResponse>(_GetDomainClientPortsResponse_QNAME, GetDomainClientPortsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitServiceAndRegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "submitServiceAndRegisterResponse")
    public JAXBElement<SubmitServiceAndRegisterResponse> createSubmitServiceAndRegisterResponse(SubmitServiceAndRegisterResponse value) {
        return new JAXBElement<SubmitServiceAndRegisterResponse>(_SubmitServiceAndRegisterResponse_QNAME, SubmitServiceAndRegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckReservationPossibilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "checkReservationPossibilityResponse")
    public JAXBElement<CheckReservationPossibilityResponse> createCheckReservationPossibilityResponse(CheckReservationPossibilityResponse value) {
        return new JAXBElement<CheckReservationPossibilityResponse>(_CheckReservationPossibilityResponse_QNAME, CheckReservationPossibilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterCallbackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "registerCallbackResponse")
    public JAXBElement<RegisterCallbackResponse> createRegisterCallbackResponse(RegisterCallbackResponse value) {
        return new JAXBElement<RegisterCallbackResponse>(_RegisterCallbackResponse_QNAME, RegisterCallbackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterCallback }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "registerCallback")
    public JAXBElement<RegisterCallback> createRegisterCallback(RegisterCallback value) {
        return new JAXBElement<RegisterCallback>(_RegisterCallback_QNAME, RegisterCallback.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDomainClientPorts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "getDomainClientPorts")
    public JAXBElement<GetDomainClientPorts> createGetDomainClientPorts(GetDomainClientPorts value) {
        return new JAXBElement<GetDomainClientPorts>(_GetDomainClientPorts_QNAME, GetDomainClientPorts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "submitServiceResponse")
    public JAXBElement<SubmitServiceResponse> createSubmitServiceResponse(SubmitServiceResponse value) {
        return new JAXBElement<SubmitServiceResponse>(_SubmitServiceResponse_QNAME, SubmitServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://useraccesspoint.autobahn.geant.net/", name = "modifyReservationResponse")
    public JAXBElement<ModifyReservationResponse> createModifyReservationResponse(ModifyReservationResponse value) {
        return new JAXBElement<ModifyReservationResponse>(_ModifyReservationResponse_QNAME, ModifyReservationResponse.class, null, value);
    }

}
