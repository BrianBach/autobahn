
package net.geant2.cnis.autobahn.xml.mpls;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.cnis.autobahn.xml.mpls package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.cnis.autobahn.xml.mpls
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Topology }
     * 
     */
    public Topology createTopology() {
        return new Topology();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link IntradomainLink }
     * 
     */
    public IntradomainLink createIntradomainLink() {
        return new IntradomainLink();
    }

    /**
     * Create an instance of {@link InterdomainLink }
     * 
     */
    public InterdomainLink createInterdomainLink() {
        return new InterdomainLink();
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
     * Create an instance of {@link Node.Ports }
     * 
     */
    public Node.Ports createNodePorts() {
        return new Node.Ports();
    }

}
