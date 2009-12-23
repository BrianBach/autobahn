package net.geant2.jra3.constraints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents numeric constraints that intersection is equal to the lowest
 * values of all constraints.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MinValueConstraint", namespace="constraints.jra3.geant2.net", propOrder={
		"value"
})
public class MinValueConstraint extends Constraint implements Serializable {

	private Double value;
	
	public MinValueConstraint() {
		
	}
	
	public MinValueConstraint(Double value) {
		this.value = value;
	}

	/**
	 * Returns values
	 * 
	 * @return
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * Intersects with another constraint. The result is equal to the lowest
	 * value of the pair of objects.
	 * 
	 * @param con2 Another constraint object
	 * @return lowest value
	 */
	public MinValueConstraint intersect(MinValueConstraint mval2) {
		
		if(this.getValue() == null)
			return new MinValueConstraint(mval2.getValue());

		if(mval2.getValue() == null)
			return new MinValueConstraint(this.getValue());
		
		return new MinValueConstraint(
				Math.min(this.getValue(), mval2.getValue()));
	}

	@Override
	public ConstraintsTypes getType() {
		return ConstraintsTypes.MINVALUE;
	}

	/**
	 * 
	 * @return copy of the object with the same value.
	 */
	public MinValueConstraint copy() {
		return new MinValueConstraint(value);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + value;
	}
}
