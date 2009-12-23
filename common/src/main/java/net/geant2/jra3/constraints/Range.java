/*
 * Range.java
 *
 * 2006-03-03
 */
package net.geant2.jra3.constraints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents range of some values. Consists of minimum and maximum values that
 * are set during creation of an instance. Object state is immutable during
 * lifetime.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Range", namespace="constraints.jra3.geant2.net", propOrder={
		"min", "max"
})
public class Range implements Comparable<Range>, Serializable {

	@XmlTransient
    private long rangeID;
    private Integer min;
    private Integer max;

    public Range() {
        
    }
    
    /**
     * Creates Range object with specified boundary values.
     * 
     * @param min
     *            Int minimum value of the range
     * @param max
     *            Int maximum value of the range
     */
    public Range(Integer min, Integer max) throws IllegalArgumentException {
        super();

        if(min > max)
            throw new IllegalArgumentException("Min should not be greater than Max");

        this.min = min;
        this.max = max;
    }

    /**
     * @return Returns the max.
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @return Returns the min.
     */
    public Integer getMin() {
        return min;
    }
    
    /**
     * @param max The max to set.
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @param min The min to set.
     */
    public void setMin(Integer min) {
        this.min = min;
    }
    
    /**
     * @return Returns the rangeID.
     */
    public long getRangeID() {
        return rangeID;
    }

    /**
     * @param rangeID The rangeID to set.
     */
    public void setRangeID(long rangeID) {
        this.rangeID = rangeID;
    }

    /**
     * Indicates whether this object's range overlaps with the given one.
     * Overlapping means that there is at least one common value that belongs to
     * both of objects ranges.
     * 
     * @param range2
     *            Range object to check if it is overlapping
     * @return true when ranges overlaps, false - otherwise
     */
    public boolean overLaps(Range range2) {
        return (range2.min >= this.min && range2.min <= this.max)
                || (range2.max >= this.min && range2.min <= this.min);
    }
    
    public boolean contains(Range range2) {
        return (range2.min >= this.min && range2.max <= this.max);        
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Range))
            return false;
        
        Range range2 = (Range) obj;
        
        return (min.equals(range2.getMin()) && max.equals(range2.getMax()));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return min.hashCode() ^ max.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return min + "-" + max;
    }

    public int compareTo(Range range2) {
        if(this.max > range2.max || this.min > range2.min)
            return 1;
        
        if(this.min < range2.min || this.max < range2.max)
            return -1;
        
        return 0;
    }
}
