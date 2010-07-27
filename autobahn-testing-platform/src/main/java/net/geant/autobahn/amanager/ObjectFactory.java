
package net.geant.autobahn.amanager;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.jra3.amanager package. 
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

    private final static QName _UnregisterServiceResponse_QNAME = new QName("http://amanager.jra3.geant2.net/", "unregisterServiceResponse");
    private final static QName _Halt_QNAME = new QName("http://amanager.jra3.geant2.net/", "halt");
    private final static QName _HaltResponse_QNAME = new QName("http://amanager.jra3.geant2.net/", "haltResponse");
    private final static QName _GetServices_QNAME = new QName("http://amanager.jra3.geant2.net/", "getServices");
    private final static QName _UnregisterService_QNAME = new QName("http://amanager.jra3.geant2.net/", "unregisterService");
    private final static QName _GetServicesResponse_QNAME = new QName("http://amanager.jra3.geant2.net/", "getServicesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.jra3.amanager
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnregisterService }
     * 
     */
    public UnregisterService createUnregisterService() {
        return new UnregisterService();
    }

    /**
     * Create an instance of {@link GetServicesResponse }
     * 
     */
    public GetServicesResponse createGetServicesResponse() {
        return new GetServicesResponse();
    }

    /**
     * Create an instance of {@link HaltResponse }
     * 
     */
    public HaltResponse createHaltResponse() {
        return new HaltResponse();
    }

    /**
     * Create an instance of {@link UnregisterServiceResponse }
     * 
     */
    public UnregisterServiceResponse createUnregisterServiceResponse() {
        return new UnregisterServiceResponse();
    }

    /**
     * Create an instance of {@link GetServices }
     * 
     */
    public GetServices createGetServices() {
        return new GetServices();
    }

    /**
     * Create an instance of {@link Halt }
     * 
     */
    public Halt createHalt() {
        return new Halt();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "unregisterServiceResponse")
    public JAXBElement<UnregisterServiceResponse> createUnregisterServiceResponse(UnregisterServiceResponse value) {
        return new JAXBElement<UnregisterServiceResponse>(_UnregisterServiceResponse_QNAME, UnregisterServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Halt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "halt")
    public JAXBElement<Halt> createHalt(Halt value) {
        return new JAXBElement<Halt>(_Halt_QNAME, Halt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HaltResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "haltResponse")
    public JAXBElement<HaltResponse> createHaltResponse(HaltResponse value) {
        return new JAXBElement<HaltResponse>(_HaltResponse_QNAME, HaltResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "getServices")
    public JAXBElement<GetServices> createGetServices(GetServices value) {
        return new JAXBElement<GetServices>(_GetServices_QNAME, GetServices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnregisterService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "unregisterService")
    public JAXBElement<UnregisterService> createUnregisterService(UnregisterService value) {
        return new JAXBElement<UnregisterService>(_UnregisterService_QNAME, UnregisterService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amanager.jra3.geant2.net/", name = "getServicesResponse")
    public JAXBElement<GetServicesResponse> createGetServicesResponse(GetServicesResponse value) {
        return new JAXBElement<GetServicesResponse>(_GetServicesResponse_QNAME, GetServicesResponse.class, null, value);
    }

}
