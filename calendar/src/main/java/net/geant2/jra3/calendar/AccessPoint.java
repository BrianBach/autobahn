package net.geant2.jra3.calendar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import net.geant2.jra3.intradomain.IntradomainPath;
import net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendarClient;
import net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar;
import net.geant2.jra3.idm2dm.ConstraintsAlreadyUsedException;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.constraints.PathConstraints;

/**
 * Implementation of web services. Singleton design pattern.
 * 
 * @author Kostas
 */
public final class AccessPoint implements ResourcesReservationCalendar {
	
	private static AccessPoint instance;
    private enum State { READY, PROCESSING, INACTIVE, RESTARTING, ERROR };
    private State state;
    private final Logger log = Logger.getLogger(AccessPoint.class);
    private Properties properties;
    
    private Map<GenericLink, AdditiveCalendar> capacityCalendars = new HashMap<GenericLink, AdditiveCalendar>();
    private ConstraintsReservationCalendar constraintsCalendar = null; 
    private int setupTime;
    private int teardownTime;
    
    /**
     * Entry point for application:<br/>
     * reads properties from app.properties (some properties can be changed later
     * via monitoring interface), sets ssl properties, calls <code>init</code> method
     * @throws Exception when one of the submodules could not be initialized
     */
    public AccessPoint() throws Exception {

        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("etc/calendar.properties");
            properties.load(fis);
            fis.close();
            log.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            log.info("Could not load app.properties: " + e.getMessage());
            throw new Exception("Could not load app.properties: " + e.getMessage());
        }

        state = State.RESTARTING;
        try {
            init();
            state = State.READY;
        } catch (Exception e) {
            state = State.ERROR;
            log.error("Error while init", e);
        }
    }
    
    /**
     * Returns an instance of AccessPoint. Singleton.
     * @return
     */
    public synchronized static AccessPoint getInstance() {
        
        if (instance == null) {
            try {
                instance = new AccessPoint();
            } catch (Exception e) {
                instance.log.error("Error while creating Topology Abstraction module AccessPoint", e);
            }
        }
        return instance;
    }
    
    /**
     * Initializes the module:<br/>
     * - retrieves type eth or sdh<br/>
     * - retrieves properties from DM<br/>
     */
    public void init() throws Exception {
        
        log.info("===== Resources Reservation module Initialization =====");
        long stime = System.currentTimeMillis();
        
        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        
        log.info("===== End of initialization - " + total + " secs =====");
    
        
        String type = properties.getProperty("db.type").toLowerCase();
    	if(type.equals("eth") || type.equals("ethernet")) {
    		this.constraintsCalendar = new EthConstraintsReservationCalendar();	
    	} else if(type.equals("sdh")) {
    		this.constraintsCalendar = new SdhConstraintsReservationCalendar();
    	}
    	
    	this.setupTime = Integer.valueOf(properties.getProperty("tool.time.setup"));
    	this.teardownTime = Integer.valueOf(properties.getProperty("tool.time.teardown"));
	}
    
    /* (non-Javadoc)
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
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
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#getConstraints()
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
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#removeReservation()
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
     * @see net.geant2.jra3.resourcesreservationcalendar.ResourcesReservationCalendar#checkCapacity()
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

        log.debug("List of links that do not have capacity:"+result);
		return result;
	}
}