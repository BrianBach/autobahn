/*
 * PathConstraints.java
 *
 * 2006-03-03
 */
package net.geant2.jra3.constraints;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Class represents intradomain path constraints that should be agreed along all
 * domains at interdomain level.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PathConstraints", namespace="constraints.jra3.geant2.net", propOrder={
		"addNames", "addConstraints", "boolNames", "boolConstraints",
		"rangeNames", "rangeConstraints", "minValNames", "minValConstraints"
})
public class PathConstraints implements Serializable {

	@XmlTransient
    private long constraintID;
	
	@XmlElement(required = true, nillable = true)
	private List<ConstraintsNames> addNames = new ArrayList<ConstraintsNames>();
	@XmlElement(required = true, nillable = true)
	private List<AdditiveConstraint> addConstraints = new ArrayList<AdditiveConstraint>();
	@XmlElement(required = true, nillable = true)
	private List<ConstraintsNames> boolNames = new ArrayList<ConstraintsNames>();
	@XmlElement(required = true, nillable = true)
	private List<BooleanConstraint> boolConstraints = new ArrayList<BooleanConstraint>();
	@XmlElement(required = true, nillable = true)
	private List<ConstraintsNames> rangeNames = new ArrayList<ConstraintsNames>();
	@XmlElement(required = true, nillable = true)
	private List<RangeConstraint> rangeConstraints = new ArrayList<RangeConstraint>();
	@XmlElement(required = true, nillable = true)
	private List<ConstraintsNames> minValNames = new ArrayList<ConstraintsNames>();
	@XmlElement(required = true, nillable = true)
	private List<MinValueConstraint> minValConstraints = new ArrayList<MinValueConstraint>();

	
    public void addAdditiveConstraint(ConstraintsNames name, AdditiveConstraint con) {
    	
    	int index = addNames.indexOf(name); 
    	if(index > -1) {
    		//replace existing constraint
    		addNames.remove(index);
    		addConstraints.remove(index);
    		
    		addNames.add(index, name);
    		addConstraints.add(index, con);
    		return;
    	}
    	
    	addNames.add(name);
        addConstraints.add(con);
    }

    public void addBooleanConstraint(ConstraintsNames name, BooleanConstraint con) {
    	
    	int index = boolNames.indexOf(name); 
    	if(index > -1) {
    		//replace existing constraint
    		boolNames.remove(index);
    		boolConstraints.remove(index);
    		
    		boolNames.add(index, name);
    		boolConstraints.add(index, con);
    		return;
    	}
    	
    	boolNames.add(name);
        boolConstraints.add(con);
    }

    public void addRangeConstraint(ConstraintsNames name, RangeConstraint con) {
    	
    	int index = rangeNames.indexOf(name); 
    	if(index > -1) {
    		//replace existing constraint
    		rangeNames.remove(index);
    		rangeConstraints.remove(index);
    		
    		rangeNames.add(index, name);
    		rangeConstraints.add(index, con);
    		return;
    	}
    	
    	rangeNames.add(name);
    	rangeConstraints.add(con);
    }

    public void addMinValueConstraint(ConstraintsNames name, MinValueConstraint con) {
    	
    	int index = minValNames.indexOf(name); 
    	if(index > -1) {
    		//replace existing constraint
    		minValNames.remove(index);
    		minValConstraints.remove(index);
    		
    		minValNames.add(index, name);
    		minValConstraints.add(index, con);
    		return;
    	}
    	
    	minValNames.add(name);
    	minValConstraints.add(con);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public RangeConstraint getRangeConstraint(ConstraintsNames name) {
    	int index = rangeNames.indexOf(name);
    	
    	if(index < 0)
    		return null;
    	
    	return rangeConstraints.get(index);
    }

    public AdditiveConstraint getAdditiveConstraint(ConstraintsNames name) {
    	int index = addNames.indexOf(name);
    	
    	if(index < 0)
    		return null;
    	
    	return addConstraints.get(index);
    }

    public BooleanConstraint getBooleanConstraint(ConstraintsNames name) {
    	int index = boolNames.indexOf(name);
    	
    	if(index < 0)
    		return null;
    	
    	return boolConstraints.get(index);
    }

    public MinValueConstraint getMinValueConstraint(ConstraintsNames name) {
    	int index = minValNames.indexOf(name);
    	
    	if(index < 0)
    		return null;
    	
    	return minValConstraints.get(index);
    }
    
    
    /**
     * Performs intersect operation on all corresponding constraints of both <code>PathConstraints</code> objects. 
     * 
     * @param constraints2 constraints to interesect with
     * @return PathConstraint object with intersected constraints 
     */
    public PathConstraints intersect(PathConstraints constraints2) {
        PathConstraints result = this.copy();

        result = intersectAddConstraints(constraints2, result);
        result = intersectBoolConstraints(constraints2, result);
        result = intersectRangeConstraints(constraints2, result);
        result = intersectMinValConstraints(constraints2, result);
        
        return result;
    }

    private PathConstraints intersectAddConstraints(PathConstraints pcon2,
			PathConstraints result) {
    	
    	if(pcon2.addNames == null)
    		return result;
    	
        // Additive
        for(ConstraintsNames name : pcon2.addNames) {
            AdditiveConstraint con1 = result.getAdditiveConstraint(name);
            AdditiveConstraint con2 = pcon2.getAdditiveConstraint(name);
            
            if(con1 != null && con2 != null) {
            	AdditiveConstraint intersection = con1.intersect(con2);
            	
            	if(intersection == null)
            		return null;
            	
            	result.addAdditiveConstraint(name, intersection);
            } else if (con1 != null) {
                result.addAdditiveConstraint(name, con1);
            } else if (con2 != null) {
                result.addAdditiveConstraint(name, con2);
            }
        }
        
        return result;
    }
    
    private PathConstraints intersectBoolConstraints(PathConstraints pcon2,
			PathConstraints result) {
    	
    	if(pcon2.boolNames == null)
    		return result;
    	
        // Boolean
        for(ConstraintsNames name : pcon2.boolNames) {
            BooleanConstraint con1 = result.getBooleanConstraint(name);
            BooleanConstraint con2 = pcon2.getBooleanConstraint(name);
            
            if(con1 != null && con2 != null) {
            	BooleanConstraint intersection = con1.intersect(con2);
            	
            	if(intersection == null)
            		return null;
            	
            	result.addBooleanConstraint(name, intersection);
            } else if (con1 != null) {
                result.addBooleanConstraint(name, con1);
            } else if (con2 != null) {
                result.addBooleanConstraint(name, con2);
            }
        }
        
        return result;
    }
    
    private PathConstraints intersectRangeConstraints(PathConstraints pcon2,
			PathConstraints result) {
    	
    	if(pcon2.rangeNames == null)
    		return result;
    	
        // Range
        for(ConstraintsNames name : pcon2.rangeNames) {
            RangeConstraint con1 = result.getRangeConstraint(name);
            RangeConstraint con2 = pcon2.getRangeConstraint(name);
            
            if(con1 != null && con2 != null) {
            	RangeConstraint intersection = con1.intersect(con2);
            	
            	if(intersection == null)
            		return null;
            	
            	result.addRangeConstraint(name, intersection);
            } else if (con1 != null) {
                result.addRangeConstraint(name, con1);
            } else if (con2 != null) {
                result.addRangeConstraint(name, con2);
            }
        }
        
        return result;
    }
    
    private PathConstraints intersectMinValConstraints(PathConstraints pcon2,
			PathConstraints result) {
    	
    	if(pcon2.minValNames == null)
    		return result;
    	
        // MinVal
        for(ConstraintsNames name : pcon2.minValNames) {
            MinValueConstraint con1 = result.getMinValueConstraint(name);
            MinValueConstraint con2 = pcon2.getMinValueConstraint(name);
            
            if(con1 != null && con2 != null) {
            	MinValueConstraint intersection = con1.intersect(con2);
            	
            	if(intersection == null)
            		return null;
            	
            	result.addMinValueConstraint(name, intersection);
            } else if (con1 != null) {
                result.addMinValueConstraint(name, con1);
            } else if (con2 != null) {
                result.addMinValueConstraint(name, con2);
            }
        }

        return result;
    }
    
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
	 * @return the addNames
	 */
	public List<ConstraintsNames> getAddNames() {
		return addNames;
	}

	/**
	 * @param addNames the addNames to set
	 */
	public void setAddNames(List<ConstraintsNames> addNames) {
		this.addNames = addNames;
	}

	/**
	 * @return the addConstraints
	 */
	public List<AdditiveConstraint> getAddConstraints() {
		return addConstraints;
	}

	/**
	 * @param addConstraints the addConstraints to set
	 */
	public void setAddConstraints(List<AdditiveConstraint> addConstraints) {
		this.addConstraints = addConstraints;
	}

	/**
	 * @return the boolNames
	 */
	public List<ConstraintsNames> getBoolNames() {
		return boolNames;
	}

	/**
	 * @param boolNames the boolNames to set
	 */
	public void setBoolNames(List<ConstraintsNames> boolNames) {
		this.boolNames = boolNames;
	}

	/**
	 * @return the boolConstraints
	 */
	public List<BooleanConstraint> getBoolConstraints() {
		return boolConstraints;
	}

	/**
	 * @param boolConstraints the boolConstraints to set
	 */
	public void setBoolConstraints(List<BooleanConstraint> boolConstraints) {
		this.boolConstraints = boolConstraints;
	}

	/**
	 * @return the rangeNames
	 */
	public List<ConstraintsNames> getRangeNames() {
		return rangeNames;
	}

	/**
	 * @param rangeNames the rangeNames to set
	 */
	public void setRangeNames(List<ConstraintsNames> rangeNames) {
		this.rangeNames = rangeNames;
	}

	/**
	 * @return the rangeConstraints
	 */
	public List<RangeConstraint> getRangeConstraints() {
		return rangeConstraints;
	}

	/**
	 * @param rangeConstraints the rangeConstraints to set
	 */
	public void setRangeConstraints(List<RangeConstraint> rangeConstraints) {
		this.rangeConstraints = rangeConstraints;
	}

	/**
	 * @return the minValNames
	 */
	public List<ConstraintsNames> getMinValNames() {
		return minValNames;
	}

	/**
	 * @param minValNames the minValNames to set
	 */
	public void setMinValNames(List<ConstraintsNames> minValNames) {
		this.minValNames = minValNames;
	}

	/**
	 * @return the minValConstraints
	 */
	public List<MinValueConstraint> getMinValConstraints() {
		return minValConstraints;
	}

	/**
	 * @param minValConstraints the minValConstraints to set
	 */
	public void setMinValConstraints(List<MinValueConstraint> minValConstraints) {
		this.minValConstraints = minValConstraints;
	}

	/**
     * Creates exact copy of the object in the deep copy meaning.
     * 
     * @return <code>PathConstraints</code> object - copy of the source object
     */
    public PathConstraints copy() {
        
        PathConstraints result = new PathConstraints();

        for(ConstraintsNames key : addNames) {
            AdditiveConstraint con = getAdditiveConstraint(key);
            result.addAdditiveConstraint(key, con.copy());
        }

        for(ConstraintsNames key : boolNames) {
            BooleanConstraint con = getBooleanConstraint(key);
            result.addBooleanConstraint(key, con.copy());
        }

        for(ConstraintsNames key : rangeNames) {
            RangeConstraint con = getRangeConstraint(key);
            result.addRangeConstraint(key, con.copy());
        }

        for(ConstraintsNames key : minValNames) {
            MinValueConstraint con = getMinValueConstraint(key);
            result.addMinValueConstraint(key, con.copy());
        }

        
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        if(addConstraints.size() > 0) {
	        sb.append("Additive:");
	        for(int i = 0; i < addNames.size(); i++) {
	        	ConstraintsNames key = addNames.get(i);
	        	Constraint con = addConstraints.get(i);
	        	
	        	sb.append("[" + key + " " + con + "] AND ");
	        }
	        sb.delete(sb.lastIndexOf(" AND "), sb.length());
	        sb.append("\n");
        }

        if(boolConstraints.size() > 0) {
	        sb.append("Boolean:");
	        for(int i = 0; i < boolNames.size(); i++) {
	        	ConstraintsNames key = boolNames.get(i);
	        	Constraint con = boolConstraints.get(i);
	        	
	        	sb.append("[" + key + " " + con + "] AND ");
	        }
	        sb.delete(sb.lastIndexOf(" AND "), sb.length());
	        sb.append("\n");
        }

        if(rangeConstraints.size() > 0) {
	        sb.append("Range:");
	        for(int i = 0; i < rangeNames.size(); i++) {
	        	ConstraintsNames key = rangeNames.get(i);
	        	Constraint con = rangeConstraints.get(i);
	        	
	        	sb.append("[" + key + " " + con + "] AND ");
	        }
	        sb.delete(sb.lastIndexOf(" AND "), sb.length());
        }

        if(minValConstraints.size() > 0) {
	        sb.append("MinValue:");
	        for(int i = 0; i < minValNames.size(); i++) {
	        	ConstraintsNames key = minValNames.get(i);
	        	Constraint con = minValConstraints.get(i);
	        	
	        	sb.append("[" + key + " " + con + "] AND ");
	        }
	        sb.delete(sb.lastIndexOf(" AND "), sb.length());
        }
        
        return sb.toString();
    }
}
