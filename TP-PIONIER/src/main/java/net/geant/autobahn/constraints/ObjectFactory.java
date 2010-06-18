
package net.geant.autobahn.constraints;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.geant.autobahn.constraints package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.geant.autobahn.constraints
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BooleanConstraint }
     * 
     */
    public BooleanConstraint createBooleanConstraint() {
        return new BooleanConstraint();
    }

    /**
     * Create an instance of {@link Range }
     * 
     */
    public Range createRange() {
        return new Range();
    }

    /**
     * Create an instance of {@link RangeConstraint }
     * 
     */
    public RangeConstraint createRangeConstraint() {
        return new RangeConstraint();
    }

    /**
     * Create an instance of {@link AdditiveConstraint }
     * 
     */
    public AdditiveConstraint createAdditiveConstraint() {
        return new AdditiveConstraint();
    }

    /**
     * Create an instance of {@link MinValueConstraint }
     * 
     */
    public MinValueConstraint createMinValueConstraint() {
        return new MinValueConstraint();
    }

    /**
     * Create an instance of {@link PathConstraints }
     * 
     */
    public PathConstraints createPathConstraints() {
        return new PathConstraints();
    }

}
