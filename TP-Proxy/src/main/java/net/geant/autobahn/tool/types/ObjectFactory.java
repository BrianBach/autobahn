
package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.tool.types package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.tool.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Constraint }
     * 
     */
    public Constraint createConstraint() {
        return new Constraint();
    }

    /**
     * Create an instance of {@link IntradomainPathType }
     * 
     */
    public IntradomainPathType createIntradomainPathType() {
        return new IntradomainPathType();
    }

    /**
     * Create an instance of {@link GenericInterfaceType }
     * 
     */
    public GenericInterfaceType createGenericInterfaceType() {
        return new GenericInterfaceType();
    }

    /**
     * Create an instance of {@link ReservationParamsType }
     * 
     */
    public ReservationParamsType createReservationParamsType() {
        return new ReservationParamsType();
    }

    /**
     * Create an instance of {@link GenericLinkType }
     * 
     */
    public GenericLinkType createGenericLinkType() {
        return new GenericLinkType();
    }

    /**
     * Create an instance of {@link ConstraintsType }
     * 
     */
    public ConstraintsType createConstraintsType() {
        return new ConstraintsType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

}
