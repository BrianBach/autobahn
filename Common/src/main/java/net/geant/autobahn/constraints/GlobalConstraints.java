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
    private static final Logger log = Logger.getLogger(GlobalConstraints.class);
    
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
        
        int index;
        try {
            index = domainsIds.indexOf(domainId);
        } catch (NullPointerException e) {
            log.error("domainId supplied was null, no domain constraints can be found");
            return null;
        }
        
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

    	removeUserConstraints();
    	
    	List<PathConstraints> selected = findBest(findPossibilities(), par);

    	if(selected == null)
    		return null;
    	
        // select only one VLAN number
        MinValueConstraint mtu = selected.get(0).getMinValueConstraint(ConstraintsNames.MTU);
        
        GlobalConstraints result = new GlobalConstraints();
        
        // set it for all pathconstraints
        for(int i = 0; i < domainsIds.size(); i++) {
            
    		DomainConstraints resDcon = new DomainConstraints();
    		result.addDomainConstraints(domainsIds.get(i), resDcon);
    		
    		PathConstraints resPcon = new PathConstraints();
    		resDcon.addPathConstraints(resPcon);

    		// Determine which constraints are applicable
        	PathConstraints first = domainConstraints.get(i).getFirstPathConstraints();
        	
        	Constraint rcon = first.getRangeConstraint(ConstraintsNames.VLANS);
        	Constraint mvcon = first.getMinValueConstraint(ConstraintsNames.TIMESLOTS);
        	Constraint mcon = first.getMinValueConstraint(ConstraintsNames.MTU);
        	
            // Vlans applicable
        	if(rcon != null) {
        		RangeConstraint vlans = selected.get(i).getRangeConstraint(ConstraintsNames.VLANS);
        		
                RangeConstraint vlanCons = new RangeConstraint(vlans.getFirstValue(), vlans.getFirstValue());
                resPcon.addRangeConstraint(ConstraintsNames.VLANS, vlanCons);
        	}
        	
            //mtu info added
            if(mcon != null) {
                //MinValueConstraint mtu1 = (MinValueConstraint) mcon;
                MinValueConstraint mtuCons = new MinValueConstraint(mtu.getValue());
                resPcon.addMinValueConstraint(ConstraintsNames.MTU, mtuCons);
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
    
    private List<PathConstraints> findBestOld(
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
    
    private List<PathConstraints> findBest(
            List<List<PathConstraints>> possible, ReservationParams par) {

        List<PathConstraints> selected = null;
        Map<Integer, PathConstraints> merged = new HashMap<Integer, PathConstraints>();
        
        if(possible == null || possible.size() == 0)
            return null;

        
        for(List<PathConstraints> path : possible) {
        	merged.clear();
        	
        	// check whether single Vlan is possible for whole e2e path
        	PathConstraints res = stitchSegment(path);
        	
        	if(res != null) {
        		selected = path;
        		merged.put(path.size(), res);
        		break;
        	}
        	
        	// break the e2e to the segments through the vlan translation
        	List<List<PathConstraints>> segments = getSegmentsWithSingleVlan(path);
        	if(segments == null)
        		return null;
        	
        	int count = -1;
        	
        	for(List<PathConstraints> seg : segments) {
                // merging the segment constraints
        		PathConstraints seg_res = stitchSegment(seg);
        		
        		if(seg_res == null) {
        			break;
        		}
        		
       			count += seg.size();
        		
                if(seg_res != null && checkConstraintsAgainst(seg_res, par)) {
    	            merged.put(count, seg_res);
                }
        	}
        	
        	// get first that suits
        	if(merged.size() == segments.size()) {
        		selected = path;
        		break;
        	}
        }

        if(selected == null) {
            log.info("Constraints cannot be agreeded!");
            return null;
        }
        
        List<PathConstraints> result = new ArrayList<PathConstraints>();
        int old = 0;
    	for(int i : merged.keySet()) {
    		for(int j = old; j <= i; j++) {
       			result.add(merged.get(i));
        	}
    		old = i + 1;
        }
    	
        return result;
    }
    
    private PathConstraints stitchSegment(List<PathConstraints> seg) {
        // merging the segment constraints
        PathConstraints first = seg.get(0).copy();
        List<PathConstraints> pathsLeft = seg.subList(1, seg.size());
        
        for(PathConstraints path : pathsLeft) {
            first = first.intersect(path);
            
            if(first == null)
                return null;
        }
        
        return first;
    }
    
    public List<List<PathConstraints>> getSegmentsWithSingleVlan(List<PathConstraints> pcons) {

        List<List<PathConstraints>> res = new ArrayList<List<PathConstraints>>();
        List<PathConstraints> curList = new ArrayList<PathConstraints>();
        
        PathConstraints merged = null;
        
        for(int i = 0; i < pcons.size(); i++) {
        	PathConstraints pcon = pcons.get(i);
        	
        	if(merged != null) {
        		merged = merged.intersect(pcon);
        	} else {
        		merged = pcon;
        	}

        	if(merged != null) {
        		if(!curList.contains(pcon)) {
        			curList.add(pcon);
        		}
        		continue;
        	}

        	// Otherwise - need to go back and check where is the VLAN translation supported
        	for(int j = i - 1; j >= 0; j--) {
            	PathConstraints pcon2 = pcons.get(j);
            	
            	if(pcon2 == null) {
            		log.debug("PCON2 is null");
            	} else if(pcon2.getBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION) == null) {
            		log.debug("PCON2 vlan trans is null");
            	}
            	
           		// need to check whether the VLAN translation is supported
            	boolean vlanTranslationEnabled = pcon2.getBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION).getValue();
            	
            	if(vlanTranslationEnabled) {
            		res.add(curList);
            		
            		curList = new ArrayList<PathConstraints>();
            		curList.add(pcon);
            		
            		merged = pcon;
            		i = j + 1;
            		
            		break;
            	} else {
            		curList.remove(pcon2);
            	}
        	}
        	
        	if(curList.isEmpty()) {
        		// No translation possible - path impossible
        		return null;
        	}
        }
        
        if(!res.contains(curList))
        	res.add(curList);
        
        return res;
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
    
    public List<List<PathConstraints>> findPossibilities() {
        final int size = domainsIds.size();
        
        if(size < 1)
            return null;

        Map<String, List<PathConstraints>> paths = new HashMap<String, List<PathConstraints>>();
        
        for(String id : domainsIds) {
        	if(!id.contains("user-"))
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
        //return new ArrayList<DomainConstraints>(domainConstraints);
        return domainConstraints;
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
        //return new ArrayList<String>(domainsIds);
        return domainsIds;
    }

    /**
     * @param domainsIds The domainsIds to set.
     */
    public void setDomainsIds(List<String> domainsIds) {
        this.domainsIds = domainsIds;
    }

    private void removeUserConstraints() {
    	
    	for(String name : new String[] {"user-ingress", "user-egress"}) {
        	int index = domainsIds.indexOf(name);
        	
        	if(index > 0) {
        		domainsIds.remove(index);
        		domainConstraints.remove(index);
        	}
    	}
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
            sb.append("Domain <" + id + ">: ");
            sb.append(getDomainConstraints(id) + " | ");
        }
        
        return sb.toString();
    }
}
