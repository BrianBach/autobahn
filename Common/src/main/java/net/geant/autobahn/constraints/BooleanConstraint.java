/*
 * BooleanConstraint.java
 *
 * 2006-03-03
 */
package net.geant.autobahn.constraints;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Stores constraints that have true/false nature, which means that values are
 * restricted to logical AND/OR operations. It contains logic to summarize with
 * other constraints of the same type.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BooleanConstraint", namespace="constraints.autobahn.geant.net", propOrder={
		"value"
})
public class BooleanConstraint extends Constraint {

    private Boolean value;

    /**
     * Default constructor
     */
    public BooleanConstraint() {
        super();
    }

    /**
     * Creates object with specified value.
     * 
     * @param value
     *            Value of constraint to be set
     */
    public BooleanConstraint(Boolean value) {
        super();
        this.value = value;
    }

    /**
     * Calculates boolean AND of itself value and value of specified constraint
     * object. New value is returned as new BooleanConstraint object
     * 
     * @param constraint2
     *            constraint to perform logical AND with
     * @return BooleanConstraint object with result value
     */
    public BooleanConstraint intersect(BooleanConstraint bool2) {
    	
        return new BooleanConstraint(this.value && bool2.value);
    }

    /**
     * @return Returns the value.
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

	@Override
	public ConstraintsTypes getType() {
		return ConstraintsTypes.BOOLEAN;
	}

	public BooleanConstraint copy() {
		return new BooleanConstraint(value);
	}
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
