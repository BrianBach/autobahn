package net.geant2.jra3.resourcesreservationcalendar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Calendar;
import java.util.List;

import net.geant2.jra3.constraints.PathConstraints;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.intradomain.common.GenericLink;

/**
 * Interface describes messages that are being sent by other modules
 * to the Calendar module.
 * 
 * @author Kostas
 */

/**
 * Defines operations for an calendar driven resources reservation module.
 * Calendar allows checking availability, reserving and releasing network
 * resources in the specified time period.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
@WebService(targetNamespace = "http://resourcesreservationcalendar.jra3.geant2.net/", name = "ResourcesReservationCalendar")
public interface ResourcesReservationCalendar {


    /**
     * Checks if there is enough capacity on specified links in specified time
     * frame. Returns the GenericLink object which does not provide sufficient
     * capacity or null if every link provide.
     * 
     * @param glinks
     *            List of GenericLink objects to check capacity on
     * @param capacity
     *            long required capacity
     * @param start
     *            Calendar start of time frame
     * @param end
     *            Calendar end of time frame
     * @return List<GenericLink> links which can't quarantee requested capacity
     *         in the time period
     */
    @WebMethod()
    @WebResult(name="genericLink")
    public List<GenericLink> checkCapacity(@WebParam(name = "glinks")List<GenericLink> glinks,@WebParam(name = "capacity")long capacity, 
    		@WebParam(name = "start")Calendar start, @WebParam(name = "end")Calendar end);

    /**
     * Returns constraints that are available for the given path in the
     * specified time period.
     * 
     * @param path
     *            Intradomain path
     * @param start
     *            Start time
     * @param end
     *            End time
     * @return Available network constraints for the path, null if not available
     */
    @WebMethod()
    @WebResult(name="PathConstraints")
    public PathConstraints getConstraints(@WebParam(name = "path")IntradomainPath path,
    		@WebParam(name = "start")Calendar start, @WebParam(name = "end")Calendar end);

    /**
     * Reserves capacity and other resources in the calendar.
     * 
     * @param glinks
     *            List of generic links that a reservation uses
     * @param capacity
     *            Capacity in bps
     * @param pcon
     *            Network constraints selected for this reservation
     * @param start
     *            Start time
     * @param end
     *            End time
     * @throws ConstraintsAlreadyUsedException
     *             When for some reasons given resources has been already
     *             reserved for anotheer reservation
     */
    @WebMethod()
    public void addReservation(@WebParam(name = "glink")List<GenericLink> glinks, long capacity,
    		@WebParam(name = "pcon")PathConstraints pcon, @WebParam(name = "start")Calendar start, @WebParam(name = "end")Calendar end)
            throws ConstraintsAlreadyUsedException;

    /**
     * Removes reservation of capacity and other network resources from the
     * calendar in the specified period.
     * 
     * @param glinks
     *            List of generic links that a reservation uses
     * @param capacity
     *            Capacity in bps
     * @param pcon
     *            Network constraints to be released
     * @param start
     *            Start time
     * @param end
     *            End time
     */
    @WebMethod()
    public void removeReservation(@WebParam(name = "glink")List<GenericLink> glinks, @WebParam(name = "capacity")long capacity,
    		@WebParam(name = "pcon")PathConstraints pcon, @WebParam(name = "start")Calendar start, @WebParam(name = "end")Calendar end);
}
