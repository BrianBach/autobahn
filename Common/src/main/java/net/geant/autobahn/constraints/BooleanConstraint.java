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
		"value", "logic"
})
public class BooleanConstraint extends Constraint {

    private Boolean value;
    private String logic = "AND";
    
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
    	this.value = value;
    }
    
    /**
     * Creates object with specified value.
     * 
     * @param value
     *            Value of constraint to be set
     */
    public BooleanConstraint(Boolean value, String logic) {
        super();
        this.value = value;
        this.logic = logic;
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
    	
    	boolean res = false;
    	if("OR".equals(logic)) {
    		res = this.value || bool2.value;
    	} else {
    		// "AND" is default
    		res = this.value && bool2.value;
    	}
    	
        return new BooleanConstraint(res, this.getLogic());
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
    
	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	@Override
	public ConstraintsTypes getType() {
		return ConstraintsTypes.BOOLEAN;
	}

	public BooleanConstraint copy() {
		return new BooleanConstraint(value, logic);
	}
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
