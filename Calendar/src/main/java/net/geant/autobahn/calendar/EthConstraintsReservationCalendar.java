package net.geant.autobahn.calendar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;

import org.apache.log4j.Logger;

/**
 * Implementation of resources reservation for ethernet domains. Resource that
 * is being reserved is vlan identifier. Vlan identifier is reserved for network
 * nodes in time periods.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class EthConstraintsReservationCalendar implements
		ConstraintsReservationCalendar {
	
	private static final Logger log = Logger.getLogger(EthConstraintsReservationCalendar.class);
	
	// VLANS
    private Map<Node, Set<CalendarEntry>> vlanCalendars = new HashMap<Node, Set<CalendarEntry>>(); 
    
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#reserveResources(java.util.List, net.geant.autobahn.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
     */
    public void reserveResources(IntradomainPath path, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {

		// Checking phase - First we are checking everything
    	for(GenericLink gl : path.getLinks()) {
    		CalendarEntry newEntry = createVlanCalendarEntry(path, gl, start, end);
    		
    		if(newEntry == null)
    			continue;
    		
    		checkResourcesOnNodes(newEntry, gl.getStartInterface().getNode(), gl.getEndInterface().getNode());
    	}
    	
    	// Reservation phase
    	for(GenericLink gl : path.getLinks()) {
    		CalendarEntry newEntry = createVlanCalendarEntry(path, gl, start, end);
    		
    		if(newEntry == null)
    			continue;
    		
    		reserveResourceOnNodes(newEntry, gl.getStartInterface().getNode(), gl.getEndInterface().getNode());
    	}
    }
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#releaseResources(java.util.List, net.geant.autobahn.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
     */
    public void releaseResources(IntradomainPath path, Calendar start, Calendar end) {
    	
    	for(GenericLink gl : path.getLinks()) {
    		PathConstraints pcon = path.getConstraints(gl);
    		RangeConstraint vlansToBeReserved = pcon.getRangeConstraint(ConstraintsNames.VLANS);
    		
    		// vlan number to reserve
    		int vlanNumber = vlansToBeReserved.getFirstValue();

    		// Check it in calendar
    		CalendarEntry entry = new CalendarEntry(start, end, vlanNumber);
    		
    		// remove entry from calendar
    		for(Node n : new Node[] {gl.getStartInterface().getNode(), gl.getEndInterface().getNode()}) {
    			Set<CalendarEntry> vlanCalendar = vlanCalendars.get(n);
    			
    			vlanCalendar.remove(entry);
    		}
    	}
    }
    
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.calendar.ConstraintsReservationCalendar#getConstraints(net.geant.autobahn.intradomain.IntradomainPath, java.util.Calendar, java.util.Calendar)
	 */
	public IntradomainPath getConstraints(IntradomainPath ipath,
			Calendar start, Calendar end) {

		for(GenericLink gl : ipath.getLinks()) {
			PathConstraints pcon = ipath.getConstraints(gl);
			RangeConstraint vlans = pcon.getRangeConstraint(ConstraintsNames.VLANS);
			
			removeAlreadyReservedResources(gl.getStartInterface().getNode(), vlans, start, end);
			
			if(vlans == null || vlans.isEmpty()) {
				return null;
			}
			
			removeAlreadyReservedResources(gl.getEndInterface().getNode(), vlans, start, end);
			
			if(vlans == null || vlans.isEmpty()) {
				return null;
			}
		}
        
		return ipath;
	}

    private void reserveResourceOnNodes(CalendarEntry newEntry, Node... nodes) {
		for(Node node : nodes) {
			Set<CalendarEntry> vlanCalendar = vlanCalendars.get(node);
			
			if(vlanCalendar == null) {
				vlanCalendar = new HashSet<CalendarEntry>();
				vlanCalendars.put(node, vlanCalendar);
			}
			
			vlanCalendar.add(newEntry);
		}
    }
    
    private void checkResourcesOnNodes(CalendarEntry newEntry, Node... nodes) throws ConstraintsAlreadyUsedException {
    	final int vlanNumber = newEntry.getVlan();
    	
		for(Node node : nodes) {
			Set<CalendarEntry> vlanCalendar = vlanCalendars.get(node);

			if(vlanCalendar == null)
				continue;
			
			for(CalendarEntry entry : vlanCalendar) {
				if (entry.overlaps(newEntry) && vlanNumber == entry.getVlan()) {
					log.warn("Attempt to reserve already reserved identifier: " + vlanNumber);
					throw new ConstraintsAlreadyUsedException(
							"Attempt to reserve already reserved identifier: " + vlanNumber);
				}
			}
		}
    }
	
    private CalendarEntry createVlanCalendarEntry(IntradomainPath path, GenericLink gl, Calendar start, Calendar end) {
		PathConstraints pcon = path.getConstraints(gl);
		
		if(pcon == null)
			return null;
		
		RangeConstraint vlansToBeReserved = pcon.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(vlansToBeReserved == null)
			return null;
		
		// vlan number to reserve
		int vlanNumber = vlansToBeReserved.getFirstValue();

		return new CalendarEntry(start, end, vlanNumber);
    }
    
	private void removeAlreadyReservedResources(Node n, RangeConstraint rcon, Calendar start, Calendar end) {
		Set<CalendarEntry> vlanCalendar = vlanCalendars.get(n);
		
		if(vlanCalendar == null)
			return;
		
        for(CalendarEntry entry : vlanCalendar) {
            if(entry.overlaps(start, end)) {
                int vlan = entry.getVlan();
                rcon.removeRange(vlan, vlan);
            }
        }
        
        return;
	}
	
	@Override
    public String toString() {
        
        String res = "";

        for(Node n : vlanCalendars.keySet()) {
        	res += n.getName() + ": \n";
        	
            for (CalendarEntry entry : vlanCalendars.get(n)) {
                res += entry + "\n";
            }
        }

        return res;
    }
}
