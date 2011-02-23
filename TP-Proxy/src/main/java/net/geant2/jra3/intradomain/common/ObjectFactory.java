
package net.geant2.jra3.intradomain.common;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant2.jra3.intradomain.common package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant2.jra3.intradomain.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link GenericInterface }
     * 
     */
    public GenericInterface createGenericInterface() {
        return new GenericInterface();
    }

    /**
     * Create an instance of {@link GenericLink }
     * 
     */
    public GenericLink createGenericLink() {
        return new GenericLink();
    }

    /**
     * Create an instance of {@link InterfaceType }
     * 
     */
    public InterfaceType createInterfaceType() {
        return new InterfaceType();
    }

    /**
     * Create an instance of {@link VersionInfo }
     * 
     */
    public VersionInfo createVersionInfo() {
        return new VersionInfo();
    }

}
