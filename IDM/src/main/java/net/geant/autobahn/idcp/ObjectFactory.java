
package net.geant.autobahn.idcp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.idcp package. 
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

    private final static QName _ListReservations_QNAME = new QName("http://oscars.es.net/OSCARS", "listReservations");
    private final static QName _SubscriptionId_QNAME = new QName("http://oscars.es.net/OSCARS", "subscriptionId");
    private final static QName _CreateReservationResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "createReservationResponse");
    private final static QName _ModifyReservation_QNAME = new QName("http://oscars.es.net/OSCARS", "modifyReservation");
    private final static QName _RefreshPathResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "refreshPathResponse");
    private final static QName _TeardownPath_QNAME = new QName("http://oscars.es.net/OSCARS", "teardownPath");
    private final static QName _ModifyReservationResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "modifyReservationResponse");
    private final static QName _ReservationResource_QNAME = new QName("http://oscars.es.net/OSCARS", "reservationResource");
    private final static QName _TeardownPathResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "teardownPathResponse");
    private final static QName _GetNetworkTopologyResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "getNetworkTopologyResponse");
    private final static QName _CancelReservation_QNAME = new QName("http://oscars.es.net/OSCARS", "cancelReservation");
    private final static QName _ForwardResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "forwardResponse");
    private final static QName _CreatePath_QNAME = new QName("http://oscars.es.net/OSCARS", "createPath");
    private final static QName _PublisherRegistrationId_QNAME = new QName("http://oscars.es.net/OSCARS", "publisherRegistrationId");
    private final static QName _CreatePathResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "createPathResponse");
    private final static QName _QueryReservation_QNAME = new QName("http://oscars.es.net/OSCARS", "queryReservation");
    private final static QName _ListReservationsResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "listReservationsResponse");
    private final static QName _Event_QNAME = new QName("http://oscars.es.net/OSCARS", "event");
    private final static QName _CreateReservation_QNAME = new QName("http://oscars.es.net/OSCARS", "createReservation");
    private final static QName _RefreshPath_QNAME = new QName("http://oscars.es.net/OSCARS", "refreshPath");
    private final static QName _CancelReservationResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "cancelReservationResponse");
    private final static QName _GetNetworkTopology_QNAME = new QName("http://oscars.es.net/OSCARS", "getNetworkTopology");
    private final static QName _QueryReservationResponse_QNAME = new QName("http://oscars.es.net/OSCARS", "queryReservationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.idcp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListRequest }
     * 
     */
    public ListRequest createListRequest() {
        return new ListRequest();
    }

    /**
     * Create an instance of {@link EventContent }
     * 
     */
    public EventContent createEventContent() {
        return new EventContent();
    }

    /**
     * Create an instance of {@link CreatePathResponseContent }
     * 
     */
    public CreatePathResponseContent createCreatePathResponseContent() {
        return new CreatePathResponseContent();
    }

    /**
     * Create an instance of {@link RefreshPathContent }
     * 
     */
    public RefreshPathContent createRefreshPathContent() {
        return new RefreshPathContent();
    }

    /**
     * Create an instance of {@link BSSFault }
     * 
     */
    public BSSFault createBSSFault() {
        return new BSSFault();
    }

    /**
     * Create an instance of {@link ForwardPayload }
     * 
     */
    public ForwardPayload createForwardPayload() {
        return new ForwardPayload();
    }

    /**
     * Create an instance of {@link ResCreateContent }
     * 
     */
    public ResCreateContent createResCreateContent() {
        return new ResCreateContent();
    }

    /**
     * Create an instance of {@link TopologyFault }
     * 
     */
    public TopologyFault createTopologyFault() {
        return new TopologyFault();
    }

    /**
     * Create an instance of {@link ModifyResReply }
     * 
     */
    public ModifyResReply createModifyResReply() {
        return new ModifyResReply();
    }

    /**
     * Create an instance of {@link EmptyArg }
     * 
     */
    public EmptyArg createEmptyArg() {
        return new EmptyArg();
    }

    /**
     * Create an instance of {@link TeardownPathContent }
     * 
     */
    public TeardownPathContent createTeardownPathContent() {
        return new TeardownPathContent();
    }

    /**
     * Create an instance of {@link PathInfo }
     * 
     */
    public PathInfo createPathInfo() {
        return new PathInfo();
    }

    /**
     * Create an instance of {@link MplsInfo }
     * 
     */
    public MplsInfo createMplsInfo() {
        return new MplsInfo();
    }

    /**
     * Create an instance of {@link AAAFault }
     * 
     */
    public AAAFault createAAAFault() {
        return new AAAFault();
    }

    /**
     * Create an instance of {@link Layer2Info }
     * 
     */
    public Layer2Info createLayer2Info() {
        return new Layer2Info();
    }

    /**
     * Create an instance of {@link ReservationResourceType }
     * 
     */
    public ReservationResourceType createReservationResourceType() {
        return new ReservationResourceType();
    }

    /**
     * Create an instance of {@link LocalDetails }
     * 
     */
    public LocalDetails createLocalDetails() {
        return new LocalDetails();
    }

    /**
     * Create an instance of {@link VlanTag }
     * 
     */
    public VlanTag createVlanTag() {
        return new VlanTag();
    }

    /**
     * Create an instance of {@link GetTopologyResponseContent }
     * 
     */
    public GetTopologyResponseContent createGetTopologyResponseContent() {
        return new GetTopologyResponseContent();
    }

    /**
     * Create an instance of {@link Forward }
     * 
     */
    public Forward createForward() {
        return new Forward();
    }

    /**
     * Create an instance of {@link CreatePathContent }
     * 
     */
    public CreatePathContent createCreatePathContent() {
        return new CreatePathContent();
    }

    /**
     * Create an instance of {@link ResDetails }
     * 
     */
    public ResDetails createResDetails() {
        return new ResDetails();
    }

    /**
     * Create an instance of {@link TeardownPathResponseContent }
     * 
     */
    public TeardownPathResponseContent createTeardownPathResponseContent() {
        return new TeardownPathResponseContent();
    }

    /**
     * Create an instance of {@link GetTopologyContent }
     * 
     */
    public GetTopologyContent createGetTopologyContent() {
        return new GetTopologyContent();
    }

    /**
     * Create an instance of {@link ListReply }
     * 
     */
    public ListReply createListReply() {
        return new ListReply();
    }

    /**
     * Create an instance of {@link MsgDetails }
     * 
     */
    public MsgDetails createMsgDetails() {
        return new MsgDetails();
    }

    /**
     * Create an instance of {@link SignalFault }
     * 
     */
    public SignalFault createSignalFault() {
        return new SignalFault();
    }

    /**
     * Create an instance of {@link ForwardReply }
     * 
     */
    public ForwardReply createForwardReply() {
        return new ForwardReply();
    }

    /**
     * Create an instance of {@link GlobalReservationId }
     * 
     */
    public GlobalReservationId createGlobalReservationId() {
        return new GlobalReservationId();
    }

    /**
     * Create an instance of {@link Layer3Info }
     * 
     */
    public Layer3Info createLayer3Info() {
        return new Layer3Info();
    }

    /**
     * Create an instance of {@link CreateReply }
     * 
     */
    public CreateReply createCreateReply() {
        return new CreateReply();
    }

    /**
     * Create an instance of {@link ModifyResContent }
     * 
     */
    public ModifyResContent createModifyResContent() {
        return new ModifyResContent();
    }

    /**
     * Create an instance of {@link RefreshPathResponseContent }
     * 
     */
    public RefreshPathResponseContent createRefreshPathResponseContent() {
        return new RefreshPathResponseContent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "listReservations")
    public JAXBElement<ListRequest> createListReservations(ListRequest value) {
        return new JAXBElement<ListRequest>(_ListReservations_QNAME, ListRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "subscriptionId")
    public JAXBElement<String> createSubscriptionId(String value) {
        return new JAXBElement<String>(_SubscriptionId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "createReservationResponse")
    public JAXBElement<CreateReply> createCreateReservationResponse(CreateReply value) {
        return new JAXBElement<CreateReply>(_CreateReservationResponse_QNAME, CreateReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyResContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "modifyReservation")
    public JAXBElement<ModifyResContent> createModifyReservation(ModifyResContent value) {
        return new JAXBElement<ModifyResContent>(_ModifyReservation_QNAME, ModifyResContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshPathResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "refreshPathResponse")
    public JAXBElement<RefreshPathResponseContent> createRefreshPathResponse(RefreshPathResponseContent value) {
        return new JAXBElement<RefreshPathResponseContent>(_RefreshPathResponse_QNAME, RefreshPathResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TeardownPathContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "teardownPath")
    public JAXBElement<TeardownPathContent> createTeardownPath(TeardownPathContent value) {
        return new JAXBElement<TeardownPathContent>(_TeardownPath_QNAME, TeardownPathContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyResReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "modifyReservationResponse")
    public JAXBElement<ModifyResReply> createModifyReservationResponse(ModifyResReply value) {
        return new JAXBElement<ModifyResReply>(_ModifyReservationResponse_QNAME, ModifyResReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationResourceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "reservationResource")
    public JAXBElement<ReservationResourceType> createReservationResource(ReservationResourceType value) {
        return new JAXBElement<ReservationResourceType>(_ReservationResource_QNAME, ReservationResourceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TeardownPathResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "teardownPathResponse")
    public JAXBElement<TeardownPathResponseContent> createTeardownPathResponse(TeardownPathResponseContent value) {
        return new JAXBElement<TeardownPathResponseContent>(_TeardownPathResponse_QNAME, TeardownPathResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopologyResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "getNetworkTopologyResponse")
    public JAXBElement<GetTopologyResponseContent> createGetNetworkTopologyResponse(GetTopologyResponseContent value) {
        return new JAXBElement<GetTopologyResponseContent>(_GetNetworkTopologyResponse_QNAME, GetTopologyResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GlobalReservationId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "cancelReservation")
    public JAXBElement<GlobalReservationId> createCancelReservation(GlobalReservationId value) {
        return new JAXBElement<GlobalReservationId>(_CancelReservation_QNAME, GlobalReservationId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForwardReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "forwardResponse")
    public JAXBElement<ForwardReply> createForwardResponse(ForwardReply value) {
        return new JAXBElement<ForwardReply>(_ForwardResponse_QNAME, ForwardReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePathContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "createPath")
    public JAXBElement<CreatePathContent> createCreatePath(CreatePathContent value) {
        return new JAXBElement<CreatePathContent>(_CreatePath_QNAME, CreatePathContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "publisherRegistrationId")
    public JAXBElement<String> createPublisherRegistrationId(String value) {
        return new JAXBElement<String>(_PublisherRegistrationId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePathResponseContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "createPathResponse")
    public JAXBElement<CreatePathResponseContent> createCreatePathResponse(CreatePathResponseContent value) {
        return new JAXBElement<CreatePathResponseContent>(_CreatePathResponse_QNAME, CreatePathResponseContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GlobalReservationId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "queryReservation")
    public JAXBElement<GlobalReservationId> createQueryReservation(GlobalReservationId value) {
        return new JAXBElement<GlobalReservationId>(_QueryReservation_QNAME, GlobalReservationId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "listReservationsResponse")
    public JAXBElement<ListReply> createListReservationsResponse(ListReply value) {
        return new JAXBElement<ListReply>(_ListReservationsResponse_QNAME, ListReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "event")
    public JAXBElement<EventContent> createEvent(EventContent value) {
        return new JAXBElement<EventContent>(_Event_QNAME, EventContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResCreateContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "createReservation")
    public JAXBElement<ResCreateContent> createCreateReservation(ResCreateContent value) {
        return new JAXBElement<ResCreateContent>(_CreateReservation_QNAME, ResCreateContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshPathContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "refreshPath")
    public JAXBElement<RefreshPathContent> createRefreshPath(RefreshPathContent value) {
        return new JAXBElement<RefreshPathContent>(_RefreshPath_QNAME, RefreshPathContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "cancelReservationResponse")
    public JAXBElement<String> createCancelReservationResponse(String value) {
        return new JAXBElement<String>(_CancelReservationResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopologyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "getNetworkTopology")
    public JAXBElement<GetTopologyContent> createGetNetworkTopology(GetTopologyContent value) {
        return new JAXBElement<GetTopologyContent>(_GetNetworkTopology_QNAME, GetTopologyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://oscars.es.net/OSCARS", name = "queryReservationResponse")
    public JAXBElement<ResDetails> createQueryReservationResponse(ResDetails value) {
        return new JAXBElement<ResDetails>(_QueryReservationResponse_QNAME, ResDetails.class, null, value);
    }

}
