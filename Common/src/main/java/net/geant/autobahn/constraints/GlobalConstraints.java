/*
 * GlobalConstraints.java
 *
 * 2006-03-03
 */
package net.geant.autobahn.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.reservation.ReservationParams;

import org.apache.log4j.Logger;


/**
 * This class represents global constraints for single reservation. It includes
 * all domains constraints that needs to be agreed for reservation to succeed.
 * This object is forwarded between domain in order to collect all constraints,
 * and also, since agreement at last domain, to make reservation with specified
 * parameters. The object contains a logic that provides resolving collected
 * constraints into strict parameters for all reservations at local domain
 * level.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GlobalConstraints", namespace="constraints.autobahn.geant.net", propOrder={
		"domainsIds", "domainConstraints"
})
public class GlobalConstraints implements Serializable {

	@XmlTransient
    private Logger log = Logger.getLogger(GlobalConstraints.class);
    
	@XmlTransient
    private long constraintID;
	
    @XmlElement(required = true, nillable = true)
    private List<String> domainsIds = new ArrayList<String>();
    @XmlElement(required = true, nillable = true)
    private List<DomainConstraints> domainConstraints = new ArrayList<DomainConstraints>();
    
    private static final double ONE_TIMESLOT_CAPACITY = 150336000.0;
    
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
     * Adds new local domain constraints to global constraints container.
     * 
     * @param constraints
     *            DomainConstraints to be added
     */
    public void addDomainConstraints(String domainId,
			DomainConstraints constraints) {
        domainsIds.add(domainId);
        domainConstraints.add(constraints);
    }

    /**
     * Return <code>DomainConstraints</code> object for specified domain.
     * 
     * @param domainId identifier of the domain
     * @return <code>DomainConstraints</code> object, null if constraints don't exist for specified identifier
     */
    public DomainConstraints getDomainConstraints(String domainId) {
        
        int index = domainsIds.indexOf(domainId);
        
        if(index < 0)
            return null;
        
        return domainConstraints.get(index);
    }
    
    /**
     * Resolves collected constraints into set of strict constraints that should
     * be used for reservations in local domains.
     * 
     * @return selected GlobalConstraints
     */
    public GlobalConstraints calculateConstraints(ReservationParams par) {
        
    	List<PathConstraints> selected = findBest(findPossibilities(), par);

    	if(selected == null)
    		return null;
    	
        // select only one VLAN number
        RangeConstraint vlans = selected.get(0).getRangeConstraint(ConstraintsNames.VLANS);
        
        int selectedVlanNumber = vlans != null ? vlans.getFirstValue() : -1;

        GlobalConstraints result = new GlobalConstraints();
        
        // set it for all pathconstraints
        for(int i = 0; i < domainsIds.size(); i++) {
            
    		DomainConstraints resDcon = new DomainConstraints();
    		result.addDomainConstraints(domainsIds.get(i), resDcon);
    		
    		PathConstraints resPcon = new PathConstraints();
    		resDcon.addPathConstraints(resPcon);

    		// Determine which constraints are applicable
    		DomainConstraints dcon = domainConstraints.get(i);
        	PathConstraints first = dcon.getPathConstraints().get(0);
        	
        	Constraint rcon = first.getRangeConstraint(ConstraintsNames.VLANS);
        	Constraint mvcon = first.getMinValueConstraint(ConstraintsNames.TIMESLOTS);
        	
            // Vlans applicable
        	if(rcon != null) {
        		RangeConstraint vlans1 = (RangeConstraint) rcon;
        		
                //RangeConstraint vlanCons = new RangeConstraint(vlans1.getFirstValue(), vlans1.getFirstValue());
                RangeConstraint vlanCons = new RangeConstraint(selectedVlanNumber, selectedVlanNumber);
                resPcon.addRangeConstraint(ConstraintsNames.VLANS, vlanCons);
        	}
        	
        	// Timeslots
        	if(mvcon != null) {
        		double ts_num = Math.ceil((double)par.getCapacity() / ONE_TIMESLOT_CAPACITY);
        		MinValueConstraint timeslots = new MinValueConstraint(ts_num);
        		resPcon.addMinValueConstraint(ConstraintsNames.TIMESLOTS, timeslots);
        	}
        }
        
        return result;
    }

    /**
     * 
     * @param par
     * @return
     */
    public GlobalConstraints mergeConstraints(ReservationParams par) {
        GlobalConstraints result = null;

        List<PathConstraints> selected = findBest(findPossibilities(), par);
        
        if(selected == null)
            return result;
        
        result = new GlobalConstraints();
        
        for(int i = 0; i < domainsIds.size(); i++) {
            
            DomainConstraints domcon = new DomainConstraints();
            domcon.addPathConstraints(selected.get(i));
            
            result.addDomainConstraints(domainsIds.get(i), domcon);
        }
        
        return result;
    }
    
    private List<PathConstraints> findBest(
            List<List<PathConstraints>> possible, ReservationParams par) {

        List<PathConstraints> selected = null;
        PathConstraints merged = null;
        
        if(possible == null || possible.size() == 0)
            return null;

        
        for(List<PathConstraints> paths : possible) {
            
            // merge
            PathConstraints first = paths.get(0).copy();
            
            List<PathConstraints> pathsLeft = paths.subList(1, paths.size());

            for(PathConstraints path : pathsLeft) {
            	
                first = first.intersect(path);
                
                if(first == null)
                    break;
            }

            // Find first that suits
            if(first != null && checkConstraintsAgainst(first, par)) {
	            merged = first;
	            selected = paths;
	            break;
            }
        }

        if(merged == null) {
            log.info("Constraints cannot be agreeded!");
            return null;
        }
        
        List<PathConstraints> result = new ArrayList<PathConstraints>();
        for(int i = 0; i < selected.size(); i++) {
        	result.add(merged);
        }
        
        return result;
    }
    
    private boolean checkConstraintsAgainst(PathConstraints pcon, ReservationParams par) {

        if(par == null) {
            return true;
        }

        // Checking Timeslots
        MinValueConstraint slots = pcon.getMinValueConstraint(ConstraintsNames.TIMESLOTS); 
        if(slots != null) {
        	double required = (double) par.getCapacity() / ONE_TIMESLOT_CAPACITY;
        	
        	if(slots.getValue() < required)
        		return false;
        }
        
        //TODO: Impl checking delay, cost, resiliency etc.
        
        return true;
    }
    
    private List<List<PathConstraints>> findPossibilities() {
        final int size = domainsIds.size();
        
        if(size < 1)
            return null;

        Map<String, List<PathConstraints>> paths = new HashMap<String, List<PathConstraints>>();
        
        for(String id : domainsIds) {
            paths.put(id, getDomainConstraints(id).getPathConstraints());
        }
        
        Stack<PathConstraints> stack = new Stack<PathConstraints>();
        List<List<PathConstraints>> result = new ArrayList<List<PathConstraints>>(); 
        
        findPathsFrom(0, paths, stack, result);
        
        return result;
    }

    private void findPathsFrom(int num, Map<String, List<PathConstraints>> allPaths, Stack<PathConstraints> current, List<List<PathConstraints>> result) {
        String domainId = domainsIds.get(num);
        log.debug("Trying [" + num + "] " + domainId);
        
        Iterator<PathConstraints> paths = allPaths.get(domainId).iterator();
        
        while(paths.hasNext()) {
            current.push(paths.next());

            if(num == domainsIds.size() - 1) {
                result.add(new ArrayList<PathConstraints>(current));
                current.pop();
                continue;
            }
            
            findPathsFrom(num + 1, allPaths, current, result);
            
            current.pop();
        }
        
        if(!paths.hasNext()) {
            // clear iterator
            allPaths.put(domainId, getDomainConstraints(domainId).getPathConstraints());
        }
    }
    
    /**
     * @return Returns the domainConstraints.
     */
    public List<DomainConstraints> getDomainConstraints() {
        return new ArrayList<DomainConstraints>(domainConstraints);
    }

    /**
     * @param domainConstraints The domainConstraints to set.
     */
    public void setDomainConstraints(List<DomainConstraints> domainConstraints) {
        this.domainConstraints = domainConstraints;
    }

    /**
     * @return Returns the domainsIds.
     */
    public List<String> getDomainsIds() {
        return new ArrayList<String>(domainsIds);
    }

    /**
     * @param domainsIds The domainsIds to set.
     */
    public void setDomainsIds(List<String> domainsIds) {
        this.domainsIds = domainsIds;
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        for(String id : domainsIds) {
            sb.append("Domain: " + id + "\n");
            sb.append(getDomainConstraints(id));
        }
        
        return sb.toString();
    }
}
