package net.geant.autobahn.constraints;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Constraint", namespace="constraints.autobahn.geant.net", propOrder={
		
})
public abstract class Constraint implements Serializable {

	@XmlTransient
    private long constraintID;

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
     * 
     * @return
     */
    public abstract ConstraintsTypes getType();

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
}
