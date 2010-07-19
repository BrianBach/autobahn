package net.geant.autobahn.resourcesreservationcalendar;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Communicates with a Resources Reservation Calendar instance through web services.
 * 
 * @author Kostas
 */
public class ResourcesReservationCalendarClient implements ResourcesReservationCalendar {
	
    private final static Logger log = Logger.getLogger(ResourcesReservationCalendarClient.class);
    
	private ResourcesReservationCalendar rc = null; 
	
	/**
     * Creates an instance sending requests to a given endpoint.
     * 
     * @param endPoint URL address of a DM
     */
	public ResourcesReservationCalendarClient(String endPoint) {
        if("none".equals(endPoint)) {
            log.info("RRCalendar location was specified as none, DM->Cal communication impossible");
            return;
        }
        
        try {
            new URL(endPoint);
            log.debug("RRCalendar location seems a valid URL, trying to connect to it");
        } catch (MalformedURLException e) {
            log.error("No valid RRCalendar location ("+ endPoint +") could be found, DM->Cal communication impossible");
            return;
        }
        
        ResourcesReservationCalendarService service = new ResourcesReservationCalendarService(endPoint);
        rc = service.getResourcesReservationCalendarPort();
    }
	
    /* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#checkCapacity()
     */
	public List<GenericLink> checkCapacity(List<GenericLink> glinks, long capacity, 
    		Calendar start, Calendar end){
		if(rc != null) {
            // If the list is empty, XML will return null.
            // So we need to make sure that in that case an empty list is returned.
            List<GenericLink> res = rc.checkCapacity(glinks, capacity, start,  end);
            if (res==null) {
                res = new ArrayList<GenericLink>();
            }
            return res;
        }
        return null;
	}
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#getConstraints()
     */
	public PathConstraints getConstraints(IntradomainPath path,
			Calendar start, Calendar end){
		if(rc != null)
            return rc.getConstraints( path,
			 start,  end);
		
		 return null;
	}
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
     */
	public void addReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end)throws ConstraintsAlreadyUsedException{
		if(rc != null)
             rc.addReservation( glinks, capacity,
        			 pcon,  start,  end);
	}
	
	/* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#removeReservation()
     */
	public void removeReservation(List<GenericLink> glinks, long capacity,
			PathConstraints pcon, Calendar start, Calendar end){
		if(rc != null)
             rc.removeReservation( glinks, capacity,
        			 pcon,  start,  end);;
	}

}
