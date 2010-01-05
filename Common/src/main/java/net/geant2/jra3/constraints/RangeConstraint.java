/*
 * RangeConstraint.java
 *
 * 2006-03-03
 */
package net.geant2.jra3.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Stores constraints that have range nature, which means that values needs to
 * be intersected in order to find common part. It contains logic to intersect
 * with other objects of the same type.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RangeConstraint", namespace="constraints.jra3.geant2.net", propOrder={
		"ranges"
})
public class RangeConstraint extends Constraint implements Serializable {

	@XmlElement(required = true, nillable = true)
	private List<Range> ranges = new ArrayList<Range>();

    /**
     * Default constructor - creates empty range constraint.
     */
    public RangeConstraint() {
        super();
    }

    public RangeConstraint(RangeConstraint source) {
        super();
        this.ranges = new ArrayList<Range>(source.ranges);
    }

    /**
     * 
     * @param vlans
     */
    public RangeConstraint(String vlans) {
        String[] ranges = vlans.split(",");
        
        if(ranges.length == 1) {
            this.addRange(createRange(vlans));
        } else {
            for(String range : ranges) {
                this.addRange(createRange(range));
            }
        }
    }
    
    /**
     * Creates a range constraint object containing single range <min, max>.
     * 
     * @param min
     *            Integer minimum value of the range
     * @param max
     *            Integer maximum value of the range
     */
    public RangeConstraint(Integer min, Integer max) {
        addRange(min, max);
    }

    /**
     * Returns iterator with ranges of this constraint.
     * 
     * @return Iterator on <code>Range</code> objects
     */
    public List<Range> getRanges() {
        return new ArrayList<Range>(ranges);
    }

    /**
     * 
     * @param ranges
     */
    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }
    
    /**
     * Adds new range to list of ranges value that <code>RangeConstraint</code>
     * object contains. Some merging can occur if it is necessery.
     * 
     * @param min
     *            Integer minimum value of the range
     * @param max
     *            Integer maximum value of the range
     */
    public void addRange(Integer min, Integer max) {

        addRange(new Range(min, max));
    }
    
    /**
     * 
     * @param newRange
     */
    public void addRange(Range newRange) {
        for (Range range : ranges) {
            if (!range.overLaps(newRange))
                continue;

            if (range.contains(newRange) || range.equals(newRange))
                return;

            // remove the old one
            ranges.remove(range);

            if (newRange.contains(range)) {
                ranges.add(newRange);
            } else if (newRange.compareTo(range) > 0) {
                ranges.add(new Range(range.getMin(), newRange.getMax()));
            } else {
                ranges.add(new Range(newRange.getMin(), range.getMax()));
            }

            mergeRanges();
            return;
        }

        ranges.add(newRange);
        Collections.sort(ranges);
    }

    /**
     * Removes a range from the constraint. Removing range [x, y] causes that
     * 
     * @param min
     * @param max
     */
    public void removeRange(Integer min, Integer max) {
        Range range2Remove = new Range(min, max);

        List<Range> copy = new ArrayList<Range>(ranges);

        for (Range range : ranges) {
            if (!range.overLaps(range2Remove))
                continue;

            if (range.equals(range2Remove)) {
                copy.remove(range);
                break;
            }

            if (range2Remove.contains(range)) {
                copy.remove(range);
                continue;
            }

            if (range.contains(range2Remove)) {
                copy.remove(range);

                if (range2Remove.getMin() > range.getMin())
                    copy.add(new Range(range.getMin(),
                            range2Remove.getMin() - 1));
                if (range2Remove.getMax() < range.getMax())
                    copy.add(new Range(range2Remove.getMax() + 1, range
                            .getMax()));

                break;
            }

            if (range.compareTo(range2Remove) < 0) {
                copy.remove(range);
                copy.add(new Range(range.getMin(), range2Remove.getMin() - 1));
            } else {
                copy.remove(range);
                copy.add(new Range(range2Remove.getMax() + 1, range.getMax()));
            }
        }

        ranges.clear();
        ranges.addAll(copy);

        Collections.sort(ranges);
    }

    /**
     * Return number of ranges object in this constraint.
     *  
     * @return int number of ranges
     */
    public int getCount() {
        return ranges.size();
    }

    /**
     * Intersects this <code>RangeConstraint</code> object with another.
     * Returns a new <code>RangeConstraint</code> object that contains only
     * these ranges that exists in both of <code>RangeConstraint</code>
     * objects.
     * 
     * @param constraint2
     *            <code>RangeConstraint</code> object to make intersection
     *            with
     * @return <code>RangeConstraint</code> object containing ranges that are
     *         common for both of source objects
     */
    public RangeConstraint intersect(RangeConstraint rcon2) {
    	
        RangeConstraint result = new RangeConstraint(this);

        // Check for empty constraints - everything is applicable
        if(rcon2.ranges.size() == 0) {
            return result;
        }
        
        if(ranges.size() == 0) {
            return new RangeConstraint(rcon2);
        }
        
        // Prepare difference
        for (Range range2 : difference(rcon2).ranges) {
            result.removeRange(range2.getMin(), range2.getMax());
        }
        
        if(result.getCount() == 0) {
            return null;
        }
        
        return result;
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
        return ranges.size() < 1;
    }
    
    /**
     * 
     * @return
     */
    public int getFirstValue() {
    	if(ranges.size() < 1)
    		throw new IllegalStateException("Empty ranges");
    	
    	return ranges.get(0).getMin();
    }
    
    /**
     * Performes difference operation on object with another. Resulting
     * Constraint contains only these ranges that exist in this object and do
     * not exist in <code>RangeConstraint</code> passed as a parameter.
     * 
     * @param constraint2
     *            <code>RangeConstraint</code> object to make difference with
     * @return <code>RangeConstraint</code> object containing ranges that
     *         exist in source object and do not exist in constraint2 object
     */
    public RangeConstraint difference(RangeConstraint constraint2) {
        RangeConstraint diff = new RangeConstraint(this);

        for (Range range2 : constraint2.ranges) {
            diff.removeRange(range2.getMin(), range2.getMax());
        }

        Collections.sort(diff.ranges);

        return diff;
    }

    /**
     * Indicates whteher this RangeConstraint object contains another.
     * 
     * @param constraint2
     * @return
     */
    public boolean contains(RangeConstraint constraint2) {
        
        // No constraints - satisfies all
        if(this.getRanges().size() == 0)
            return true;
        
        // all of constraint2 ranges should be present in the ranges
        for(Range range2 : constraint2.getRanges()) {
            boolean tmp = false;
            
            for(Range range1 : this.getRanges()) {
                if(range1.contains(range2))
                    tmp = true;
            }
            
            if(tmp == false)
                return false;
        }
        
        return true;
    }
    
    private void mergeRanges() {
        List<Range> tempList = new ArrayList<Range>(ranges);

        // remove all the ranges
        ranges.clear();

        for (Range range : tempList) {
            this.addRange(range.getMin(), range.getMax());
        }

        Collections.sort(ranges);
    }

    public RangeConstraint copy() {
        RangeConstraint copy = new RangeConstraint();
        
        copy.ranges = new ArrayList<Range>();
        
        for(Range range : ranges) {
            copy.ranges.add(new Range(range.getMin(), range.getMax()));
        }
        
        return copy;
    }
    
    private Range createRange(String desc) {
        Range r = null;
        
        if(desc.contains("-")) {
            String[] ns = desc.split("-");
            r = new Range(Integer.parseInt(ns[0]), Integer.parseInt(ns[1]));
        } else {
            r = new Range(Integer.parseInt(desc), Integer.parseInt(desc));
        }
        
        return r;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RangeConstraint))
            return false;

        RangeConstraint range2 = (RangeConstraint) obj;

        return ranges.equals(range2.getRanges());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return ranges.hashCode();
    }

	@Override
	public ConstraintsTypes getType() {
		return ConstraintsTypes.RANGE;
	}
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (Range range : ranges) {
            sb.append(range + ", ");
        }
        
        if(sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }
}
