
package net.geant2.cnis.autobahn.xml.sdh;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.cnis.autobahn.xml.sdh package. 
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

    private final static QName _Topology_QNAME = new QName("http://cnis.geant2.net/autobahn/xml/sdh", "topology");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.cnis.autobahn.xml.sdh
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link InterfaceType }
     * 
     */
    public InterfaceType createInterfaceType() {
        return new InterfaceType();
    }

    /**
     * Create an instance of {@link Topology.Nodes }
     * 
     */
    public Topology.Nodes createTopologyNodes() {
        return new Topology.Nodes();
    }

    /**
     * Create an instance of {@link VcTrail.Links }
     * 
     */
    public VcTrail.Links createVcTrailLinks() {
        return new VcTrail.Links();
    }

    /**
     * Create an instance of {@link Topology.VcLinkTypes }
     * 
     */
    public Topology.VcLinkTypes createTopologyVcLinkTypes() {
        return new Topology.VcLinkTypes();
    }

    /**
     * Create an instance of {@link Topology.VcTrails }
     * 
     */
    public Topology.VcTrails createTopologyVcTrails() {
        return new Topology.VcTrails();
    }

    /**
     * Create an instance of {@link VcatGroup }
     * 
     */
    public VcatGroup createVcatGroup() {
        return new VcatGroup();
    }

    /**
     * Create an instance of {@link Vtp }
     * 
     */
    public Vtp createVtp() {
        return new Vtp();
    }

    /**
     * Create an instance of {@link PhyInterface }
     * 
     */
    public PhyInterface createPhyInterface() {
        return new PhyInterface();
    }

    /**
     * Create an instance of {@link Node.CtpSet }
     * 
     */
    public Node.CtpSet createNodeCtpSet() {
        return new Node.CtpSet();
    }

    /**
     * Create an instance of {@link Topology.IntradomainLinks }
     * 
     */
    public Topology.IntradomainLinks createTopologyIntradomainLinks() {
        return new Topology.IntradomainLinks();
    }

    /**
     * Create an instance of {@link Topology.InterdomainLinks }
     * 
     */
    public Topology.InterdomainLinks createTopologyInterdomainLinks() {
        return new Topology.InterdomainLinks();
    }

    /**
     * Create an instance of {@link IDLink }
     * 
     */
    public IDLink createIDLink() {
        return new IDLink();
    }

    /**
     * Create an instance of {@link VcLinkType }
     * 
     */
    public VcLinkType createVcLinkType() {
        return new VcLinkType();
    }

    /**
     * Create an instance of {@link VcLink }
     * 
     */
    public VcLink createVcLink() {
        return new VcLink();
    }

    /**
     * Create an instance of {@link Ctp }
     * 
     */
    public Ctp createCtp() {
        return new Ctp();
    }

    /**
     * Create an instance of {@link Node.PhyInterfaces }
     * 
     */
    public Node.PhyInterfaces createNodePhyInterfaces() {
        return new Node.PhyInterfaces();
    }

    /**
     * Create an instance of {@link Topology.VcatGroups }
     * 
     */
    public Topology.VcatGroups createTopologyVcatGroups() {
        return new Topology.VcatGroups();
    }

    /**
     * Create an instance of {@link Node.VtpSet }
     * 
     */
    public Node.VtpSet createNodeVtpSet() {
        return new Node.VtpSet();
    }

    /**
     * Create an instance of {@link VcTrail }
     * 
     */
    public VcTrail createVcTrail() {
        return new VcTrail();
    }

    /**
     * Create an instance of {@link PhyLink }
     * 
     */
    public PhyLink createPhyLink() {
        return new PhyLink();
    }

    /**
     * Create an instance of {@link Topology }
     * 
     */
    public Topology createTopology() {
        return new Topology();
    }

    /**
     * Create an instance of {@link Topology.VcLinks }
     * 
     */
    public Topology.VcLinks createTopologyVcLinks() {
        return new Topology.VcLinks();
    }

    /**
     * Create an instance of {@link VcatGroup.Trails }
     * 
     */
    public VcatGroup.Trails createVcatGroupTrails() {
        return new VcatGroup.Trails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Topology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cnis.geant2.net/autobahn/xml/sdh", name = "topology")
    public JAXBElement<Topology> createTopology(Topology value) {
        return new JAXBElement<Topology>(_Topology_QNAME, Topology.class, null, value);
    }

}
