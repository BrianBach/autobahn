package net.geant2.jra3.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.geant2.jra3.constraints.ConstraintsNames;
import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.constraints.RangeConstraint;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.intradomain.common.Node;

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
    private Map<Node, List<CalendarEntry>> vlanCalendars = new HashMap<Node, List<CalendarEntry>>(); 
    
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.intradomain.calendar.ConstraintsReservationCalendar#reserveResources(java.util.List, net.geant2.jra3.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
     */
    public void reserveResources(List<GenericLink> glinks,
			PathConstraints pcon, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
    	
		RangeConstraint vlansToBeReserved = pcon.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(vlansToBeReserved == null) {
			log.warn("Ethernet domain without VLAN constraint!");
			return;
		}

		// vlan number to reserve
		int vlanNumber = vlansToBeReserved.getRanges().get(0).getMin();

		// Check it in calendar
		CalendarEntry newEntry = new CalendarEntry(start, end, vlanNumber);

		Set<Node> nodes = getNodes(glinks);
		
		// Checking phase
		for(Node node : nodes) {
			List<CalendarEntry> vlanCalendar = vlanCalendars.get(node);

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
		
		// Reservation phase
		for(Node node : nodes) {
			List<CalendarEntry> vlanCalendar = vlanCalendars.get(node);
			
			if(vlanCalendar == null) {
				vlanCalendar = new ArrayList<CalendarEntry>();
				vlanCalendars.put(node, vlanCalendar);
			}
			
			vlanCalendar.add(newEntry);
		}
    }
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.intradomain.calendar.ConstraintsReservationCalendar#releaseResources(java.util.List, net.geant2.jra3.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
     */
    public void releaseResources(List<GenericLink> glinks,
			PathConstraints pcon, Calendar start, Calendar end) {
    	
		RangeConstraint vlansToBeReleased= pcon.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(vlansToBeReleased == null) {
			log.warn("Ethernet domain without VLAN constraint!");
			return;
		}

		int vlanNumber = vlansToBeReleased.getRanges().get(0).getMin();

		CalendarEntry entry = new CalendarEntry(start, end, vlanNumber);
		
		// remove entry from calendar
		for(Node n : getNodes(glinks)) {
			List<CalendarEntry> vlanCalendar = vlanCalendars.get(n);
			
			vlanCalendar.remove(entry);
		}
    }
    
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.calendar.ConstraintsReservationCalendar#getConstraints(net.geant2.jra3.intradomain.IntradomainPath, java.util.Calendar, java.util.Calendar)
	 */
	public PathConstraints getConstraints(IntradomainPath ipath,
			Calendar start, Calendar end) {

		PathConstraints pcon = ipath.getMergedConstraints();
		
		// Filter VLANS
		RangeConstraint rcon = pcon.getRangeConstraint(ConstraintsNames.VLANS);
		
		if(rcon == null) {
			log.warn("Ethernet domain without VLAN constraint!");
			return pcon;
		}
		
        // Exclude vlans already reserved
		for(Node n : getNodes(ipath.getLinks())) {
			List<CalendarEntry> vlanCalendar = vlanCalendars.get(n);
			
			if(vlanCalendar == null)
				continue;
			
	        for(CalendarEntry entry : vlanCalendar) {
	            if(entry.overlaps(start, end)) {
	                int vlan = entry.getVlan();
	                rcon.removeRange(vlan, vlan);
	                
	                if(rcon.isEmpty())
	                	return null;
	            }
	        }
		}
        
		return pcon;
	}
    
	private Set<Node> getNodes(List<GenericLink> glinks) {
		Set<Node> result = new HashSet<Node>();
		
		for(GenericLink glink : glinks) {
			result.add(glink.getStartInterface().getNode());
			result.add(glink.getEndInterface().getNode());
		}
		
		return result;
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
