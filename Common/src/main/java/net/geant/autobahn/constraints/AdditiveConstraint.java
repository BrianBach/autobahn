/*
 * AdditiveConstraint.java
 *
 * 2006-03-03
 */
package net.geant.autobahn.constraints;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Stores constraints that have additive nature, which means that values are
 * summarized along all domains. It contains logic to summarize with other
 * constraint of the same type.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AdditiveConstraint", namespace="constraints.autobahn.geant.net", propOrder={
		"value"
})
public class AdditiveConstraint extends Constraint {

    private Double value;

    /**
     * Default constructor
     */
    public AdditiveConstraint() {
        super();
    }

    /**
     * Creates constraint with specified value
     * 
     * @param value
     *            Double value of the constraint
     */
    public AdditiveConstraint(Double value) {
        super();
        this.value = value;
    }

    /**
     * Calculates sum of itself value and value of specified constraint object.
     * New value is returned as new AdditiveConstraint object.
     * 
     * @param constraint2
     *            constraints to summarize with
     * @return AdditiveConstraint object containing sum of constraints
     */
    public AdditiveConstraint intersect(AdditiveConstraint add2) {
    	
        return new AdditiveConstraint(this.value + add2.value);
    }

    /**
     * @return Returns the value.
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(Double value) {
        this.value = value;
    }

	@Override
	public ConstraintsTypes getType() {
		return ConstraintsTypes.ADDITIVE;
	}

	public AdditiveConstraint copy() {
		return new AdditiveConstraint(value);
	}
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
