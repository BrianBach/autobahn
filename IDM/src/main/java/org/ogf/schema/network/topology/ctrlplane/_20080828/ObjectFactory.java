
package org.ogf.schema.network.topology.ctrlplane._20080828;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ogf.schema.network.topology.ctrlplane._20080828 package. 
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

    private final static QName _Link_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "link");
    private final static QName _Port_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "port");
    private final static QName _Topology_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "topology");
    private final static QName _Path_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "path");
    private final static QName _Domain_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "domain");
    private final static QName _Node_QNAME = new QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/", "node");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ogf.schema.network.topology.ctrlplane._20080828
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CtrlPlanePortContent }
     * 
     */
    public CtrlPlanePortContent createCtrlPlanePortContent() {
        return new CtrlPlanePortContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneNodeContent }
     * 
     */
    public CtrlPlaneNodeContent createCtrlPlaneNodeContent() {
        return new CtrlPlaneNodeContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneLinkContent }
     * 
     */
    public CtrlPlaneLinkContent createCtrlPlaneLinkContent() {
        return new CtrlPlaneLinkContent();
    }

    /**
     * Create an instance of {@link CtrlPlanePathContent }
     * 
     */
    public CtrlPlanePathContent createCtrlPlanePathContent() {
        return new CtrlPlanePathContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneDomainContent }
     * 
     */
    public CtrlPlaneDomainContent createCtrlPlaneDomainContent() {
        return new CtrlPlaneDomainContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneTopologyContent }
     * 
     */
    public CtrlPlaneTopologyContent createCtrlPlaneTopologyContent() {
        return new CtrlPlaneTopologyContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneDomainSignatureContent }
     * 
     */
    public CtrlPlaneDomainSignatureContent createCtrlPlaneDomainSignatureContent() {
        return new CtrlPlaneDomainSignatureContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneAdministrativeGroup }
     * 
     */
    public CtrlPlaneAdministrativeGroup createCtrlPlaneAdministrativeGroup() {
        return new CtrlPlaneAdministrativeGroup();
    }

    /**
     * Create an instance of {@link CtrlPlaneHopContent }
     * 
     */
    public CtrlPlaneHopContent createCtrlPlaneHopContent() {
        return new CtrlPlaneHopContent();
    }

    /**
     * Create an instance of {@link TimeContent }
     * 
     */
    public TimeContent createTimeContent() {
        return new TimeContent();
    }

    /**
     * Create an instance of {@link Lifetime }
     * 
     */
    public Lifetime createLifetime() {
        return new Lifetime();
    }

    /**
     * Create an instance of {@link Duration }
     * 
     */
    public Duration createDuration() {
        return new Duration();
    }

    /**
     * Create an instance of {@link CtrlPlaneNextHopContent }
     * 
     */
    public CtrlPlaneNextHopContent createCtrlPlaneNextHopContent() {
        return new CtrlPlaneNextHopContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneAddressContent }
     * 
     */
    public CtrlPlaneAddressContent createCtrlPlaneAddressContent() {
        return new CtrlPlaneAddressContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneSwcapContent }
     * 
     */
    public CtrlPlaneSwcapContent createCtrlPlaneSwcapContent() {
        return new CtrlPlaneSwcapContent();
    }

    /**
     * Create an instance of {@link CtrlPlaneSwitchingCapabilitySpecificInfo }
     * 
     */
    public CtrlPlaneSwitchingCapabilitySpecificInfo createCtrlPlaneSwitchingCapabilitySpecificInfo() {
        return new CtrlPlaneSwitchingCapabilitySpecificInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlaneLinkContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "link")
    public JAXBElement<CtrlPlaneLinkContent> createLink(CtrlPlaneLinkContent value) {
        return new JAXBElement<CtrlPlaneLinkContent>(_Link_QNAME, CtrlPlaneLinkContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlanePortContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "port")
    public JAXBElement<CtrlPlanePortContent> createPort(CtrlPlanePortContent value) {
        return new JAXBElement<CtrlPlanePortContent>(_Port_QNAME, CtrlPlanePortContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlaneTopologyContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "topology")
    public JAXBElement<CtrlPlaneTopologyContent> createTopology(CtrlPlaneTopologyContent value) {
        return new JAXBElement<CtrlPlaneTopologyContent>(_Topology_QNAME, CtrlPlaneTopologyContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlanePathContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "path")
    public JAXBElement<CtrlPlanePathContent> createPath(CtrlPlanePathContent value) {
        return new JAXBElement<CtrlPlanePathContent>(_Path_QNAME, CtrlPlanePathContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlaneDomainContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "domain")
    public JAXBElement<CtrlPlaneDomainContent> createDomain(CtrlPlaneDomainContent value) {
        return new JAXBElement<CtrlPlaneDomainContent>(_Domain_QNAME, CtrlPlaneDomainContent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtrlPlaneNodeContent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", name = "node")
    public JAXBElement<CtrlPlaneNodeContent> createNode(CtrlPlaneNodeContent value) {
        return new JAXBElement<CtrlPlaneNodeContent>(_Node_QNAME, CtrlPlaneNodeContent.class, null, value);
    }

}
