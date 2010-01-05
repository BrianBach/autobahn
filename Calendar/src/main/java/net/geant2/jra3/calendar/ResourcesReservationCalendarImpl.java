/*
 * ResourcesReservationCalendarImpl.java
 *
 * 2007-07-05
 */
package net.geant2.jra3.calendar;

import java.util.Calendar;
import java.util.List;

import javax.jws.WebService;

import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar;
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
@WebService(name="ResourcesReservationCalendar", serviceName="ResourcesReservationCalendarService",
        portName="ResourcesReservationCalendarPort", 
        targetNamespace="http://resourcesreservationcalendar.jra3.geant2.net/",
        endpointInterface="net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar")
public class ResourcesReservationCalendarImpl implements ResourcesReservationCalendar {

	/* (non-Javadoc)
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#checkCapacity()
     */
	public List<GenericLink> checkCapacity(List<GenericLink> glinks, long capacity, 
    		Calendar start, Calendar end) {
        return AccessPoint.getInstance().checkCapacity(glinks, capacity, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#getConstraints()
     */
	public PathConstraints getConstraints(IntradomainPath path,
			Calendar start, Calendar end) {
        return AccessPoint.getInstance().getConstraints(path, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
     */
	public void addReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
         AccessPoint.getInstance().addReservation(glinks, capacity, pcon, start, end);
    }
	
	/* (non-Javadoc)
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#removeReservation()
     */
	public void removeReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end) {
         AccessPoint.getInstance().removeReservation(glinks, capacity, pcon, start, end);
    }
   
}
