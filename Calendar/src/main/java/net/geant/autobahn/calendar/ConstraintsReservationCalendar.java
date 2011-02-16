package net.geant.autobahn.calendar;

import java.util.Calendar;

import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;

/**
 * Interface describes methods useful for resources (vlans, sdh timeslots)
 * reserving.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public interface ConstraintsReservationCalendar {

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
   	public IntradomainPath getConstraints(IntradomainPath ipath, Calendar start, Calendar end);
	
   	/**
   	 * Reserves network resources for given time period.
   	 * 
	 * @param glinks
	 *            List of generic links that a reservation uses
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
    public void reserveResources(IntradomainPath ipath, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException;

    /**
     * Releases a reservation of network resources.
     * 
	 * @param glinks
	 *            List of generic links that a reservation uses
	 * @param pcon
	 *            Network constraints selected for this reservation
	 * @param start
	 *            Start time
	 * @param end
	 *            End time
     */
    public void releaseResources(IntradomainPath ipath, Calendar start, Calendar end);

}
