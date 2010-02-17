
package net.geant.autobahn.network;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.network package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.network
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StateOper }
     * 
     */
    public StateOper createStateOper() {
        return new StateOper();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Path }
     * 
     */
    public Path createPath() {
        return new Path();
    }

    /**
     * Create an instance of {@link AdminDomain }
     * 
     */
    public AdminDomain createAdminDomain() {
        return new AdminDomain();
    }

    /**
     * Create an instance of {@link ProvisioningDomain }
     * 
     */
    public ProvisioningDomain createProvisioningDomain() {
        return new ProvisioningDomain();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link StateAdmin }
     * 
     */
    public StateAdmin createStateAdmin() {
        return new StateAdmin();
    }

}
