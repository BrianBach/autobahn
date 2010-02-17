/*
 * ResourcesReservationCalendarImpl.java
 *
 * 2007-07-05
 */
package net.geant.autobahn.calendar;

import java.util.Calendar;
import java.util.List;

import javax.jws.WebService;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar;

/**
 * Class implements calendar based reservation of resources. For reserving
 * resources it uses extended time window than requested, due to the need of
 * the setup and release time.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@WebService(name="ResourcesReservationCalendar", serviceName="ResourcesReservationCalendarService",
        portName="ResourcesReservationCalendarPort", 
        targetNamespace="http://resourcesreservationcalendar.autobahn.geant.net/",
        endpointInterface="net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar")
public class ResourcesReservationCalendarImpl implements ResourcesReservationCalendar {

	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#checkCapacity()
     */
	public List<GenericLink> checkCapacity(List<GenericLink> glinks, long capacity, 
    		Calendar start, Calendar end) {
        return AccessPoint.getInstance().checkCapacity(glinks, capacity, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#getConstraints()
     */
	public PathConstraints getConstraints(IntradomainPath path,
			Calendar start, Calendar end) {
        return AccessPoint.getInstance().getConstraints(path, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
     */
	public void addReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
         AccessPoint.getInstance().addReservation(glinks, capacity, pcon, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#removeReservation()
     */
	public void removeReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end) {
         AccessPoint.getInstance().removeReservation(glinks, capacity, pcon, start, end);
    }
   
}
