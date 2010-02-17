/*
 * DomainConstraints.java
 *
 * 2006-03-03
 */
package net.geant.autobahn.constraints;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Constraints for a domain. It is set of constraints for possible paths across
 * a domain.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DomainConstraints", namespace="constraints.autobahn.geant.net", propOrder={
		"pathConstraints"
})
public class DomainConstraints implements Serializable {

	@XmlTransient
    private long constraintID;
	
	@XmlElement(required = true, nillable = true)
    private List<PathConstraints> pathConstraints = new ArrayList<PathConstraints>();

    /**
     * @return Returns the constraintID.
     */
    public long getConstraintID() {
        return constraintID;
    }

    /**
     * @param constraintID The constraintID to set.
     */
    public void setConstraintID(long constraintID) {
        this.constraintID = constraintID;
    }
	
    /**
     * Add new constraints for single potential intradomain reservation path.
     * 
     * @param pathId
     *            Identifier of the path
     * @param cons
     *            PathConstraints object to be added
     */
    public void addPathConstraints(PathConstraints cons) {
        pathConstraints.add(cons);
    }

    public List<PathConstraints> getPathConstraints() {
        return new ArrayList<PathConstraints>(pathConstraints);
    }

    public void setPathConstraints(List<PathConstraints> constraints) {
        this.pathConstraints = constraints;
    }

    /**
     * Limits constraints to intersection between this domain constraints and
     * specified one. The result set of strict reservation parameters is
     * returned.
     * 
     * @param cons2
     *            domain constraints to search for common part
     * @return DomainConstraints with intersection of PathConstraints
     */
    public DomainConstraints intersect(DomainConstraints cons2) {
        DomainConstraints result = new DomainConstraints();

        for (PathConstraints pcons1 : pathConstraints) {
            for (PathConstraints pcons2 : cons2.pathConstraints) {
                PathConstraints common = pcons1.intersect(pcons2);

                if (common != null) {
                    result.addPathConstraints(common);
                }
            }
        }

        return result;
    }

    /**
     * Performs deep copy of the object
     * 
     * @return
     */
    public DomainConstraints copy() {

        DomainConstraints result = new DomainConstraints();
        
        result.pathConstraints = new ArrayList<PathConstraints>();
        for(PathConstraints pcon : pathConstraints) {
            result.pathConstraints.add(pcon.copy());
        }

        return result;
    }

    /**
     * Indicates whether DomainConstraints object is valid - that means - there
     * is at least one path with all elementary constraints agreeded (i.e. vlan
     * numbers) for all links of the path.
     * 
     * @return true - if constraints object is valid, false - otherwise
     */
    public boolean isValid() {

        for (PathConstraints pcon : pathConstraints) {
            if (pcon != null)
                return true;
        }

        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof Constraint)) {
            return false;
        }
        
        Constraint c2 = (Constraint) obj;
        
        return getConstraintID() == c2.getConstraintID();
    }

    @Override
    public int hashCode() {
        return (int) getConstraintID();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (PathConstraints path : pathConstraints) {
            sb.append(path);
            sb.append("\nOR\n");
        }

        if (sb.length() > 0) {
            sb.delete(sb.lastIndexOf("\nOR\n"), sb.lastIndexOf("\nOR\n") + 3);
        }

        return sb.toString();
    }
}
