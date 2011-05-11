
package net.geant2.cnis.autobahn.xml.ethernet;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.cnis.autobahn.xml.ethernet package. 
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

    private final static QName _Topology_QNAME = new QName("http://cnis.geant2.net/autobahn/xml/ethernet", "topology");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.cnis.autobahn.xml.ethernet
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
     * Create an instance of {@link Topology }
     * 
     */
    public Topology createTopology() {
        return new Topology();
    }

    /**
     * Create an instance of {@link VlanRanges }
     * 
     */
    public VlanRanges createVlanRanges() {
        return new VlanRanges();
    }

    /**
     * Create an instance of {@link Range }
     * 
     */
    public Range createRange() {
        return new Range();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link VlanPort }
     * 
     */
    public VlanPort createVlanPort() {
        return new VlanPort();
    }

    /**
     * Create an instance of {@link LogicalPort }
     * 
     */
    public LogicalPort createLogicalPort() {
        return new LogicalPort();
    }

    /**
     * Create an instance of {@link SpanningTree }
     * 
     */
    public SpanningTree createSpanningTree() {
        return new SpanningTree();
    }

    /**
     * Create an instance of {@link Vlan }
     * 
     */
    public Vlan createVlan() {
        return new Vlan();
    }

    /**
     * Create an instance of {@link PortType }
     * 
     */
    public PortType createPortType() {
        return new PortType();
    }

    /**
     * Create an instance of {@link PhysicalPort }
     * 
     */
    public PhysicalPort createPhysicalPort() {
        return new PhysicalPort();
    }

    /**
     * Create an instance of {@link IDLink }
     * 
     */
    public IDLink createIDLink() {
        return new IDLink();
    }

    /**
     * Create an instance of {@link Node.PhysicalPorts }
     * 
     */
    public Node.PhysicalPorts createNodePhysicalPorts() {
        return new Node.PhysicalPorts();
    }

    /**
     * Create an instance of {@link Topology.Nodes }
     * 
     */
    public Topology.Nodes createTopologyNodes() {
        return new Topology.Nodes();
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
     * Create an instance of {@link Topology.Vlans }
     * 
     */
    public Topology.Vlans createTopologyVlans() {
        return new Topology.Vlans();
    }

    /**
     * Create an instance of {@link Topology.SpanningTrees }
     * 
     */
    public Topology.SpanningTrees createTopologySpanningTrees() {
        return new Topology.SpanningTrees();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Topology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cnis.geant2.net/autobahn/xml/ethernet", name = "topology")
    public JAXBElement<Topology> createTopology(Topology value) {
        return new JAXBElement<Topology>(_Topology_QNAME, Topology.class, null, value);
    }

}
