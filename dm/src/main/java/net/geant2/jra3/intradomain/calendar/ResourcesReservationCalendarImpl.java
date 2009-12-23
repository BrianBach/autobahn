/*
 * ResourcesReservationCalendarImpl.java
 *
 * 2007-07-05
 */
package net.geant2.jra3.intradomain.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.intradomain.common.GenericLink;

/**
 * Class implements calendar based reservation of resources. For reserving
 * resources it uses extended time window than requested, due to the need of
 * the setup and release time.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class ResourcesReservationCalendarImpl implements ResourcesReservationCalendar {
	
    private Map<GenericLink, AdditiveCalendar> capacityCalendars = new HashMap<GenericLink, AdditiveCalendar>();
    private ConstraintsReservationCalendar constraintsCalendar = null; 
    private int setupTime;
    private int teardownTime;

	/**
	 * Creates an instance, extendind the reservations periods by setup and
	 * teardown time.
	 * 
	 * @param type Type of the calendar ("eth" or "sdh")
	 * @param setupTime Additional time to setup a reservation in the TP
	 * @param teardownTime Additional time to remove a reservation
	 */
    public ResourcesReservationCalendarImpl(String type, int setupTime,
			int teardownTime) {
    	type = type.toLowerCase();
    	if(type.equals("eth") || type.equals("ethernet")) {
    		this.constraintsCalendar = new EthConstraintsReservationCalendar();	
    	} else if(type.equals("sdh")) {
    		this.constraintsCalendar = new SdhConstraintsReservationCalendar();
    	}
    	
    	this.setupTime = setupTime;
    	this.teardownTime = teardownTime;
    }
    
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.calendar.ResourcesReservationCalendar#addReservation(java.util.List, long, net.geant2.jra3.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
	 */
	public void addReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
	
		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
		// Reserve constraints
		constraintsCalendar.reserveResources(glinks, pcon, start, end);

        // Add capacity to links calendars
		for(GenericLink glink : glinks) {
			AdditiveCalendar calendar = capacityCalendars.get(glink);
			if(calendar == null) {
				calendar = new AdditiveCalendar();
				capacityCalendars.put(glink, calendar);
			}
			
			calendar.addReservation(capacity, start, end);	
		}
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.calendar.ResourcesReservationCalendar#getConstraints(net.geant2.jra3.intradomain.IntradomainPath, java.util.Calendar, java.util.Calendar)
	 */
	public PathConstraints getConstraints(IntradomainPath ipath,
			Calendar start, Calendar end) {

		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
		return constraintsCalendar.getConstraints(ipath, start, end);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.calendar.ResourcesReservationCalendar#removeReservation(java.util.List, long, net.geant2.jra3.constraints.PathConstraints, java.util.Calendar, java.util.Calendar)
	 */
	public void removeReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end) {

		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
        // Remove capacity from Calendar
		for(GenericLink glink : glinks) {
			AdditiveCalendar calendar = capacityCalendars.get(glink);
			if(calendar != null) {
				calendar.removeReservation(capacity, start, end);
			}
		}
		
        // Remove constraints reservation
        constraintsCalendar.releaseResources(glinks, pcon, start, end);
	}

	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.calendar.ResourcesReservationCalendar#checkCapacity(java.util.List, long, java.util.Calendar, java.util.Calendar)
	 */
	public List<GenericLink> checkCapacity(List<GenericLink> glinks, long capacity,
			Calendar start, Calendar end) {
		
		List<GenericLink> result = new ArrayList<GenericLink>();
		
		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
        for(GenericLink glink : glinks) {
			AdditiveCalendar calendar = capacityCalendars.get(glink);
			
			long maxUsage = 0;
			if(calendar != null) {
	            maxUsage = calendar.getMaxUsage(start, end);
			}
            
            long remaining = glink.getCapacity() - maxUsage;
            
            if(remaining < capacity)
            	result.add(glink);
        }

		return result;
	}
}
