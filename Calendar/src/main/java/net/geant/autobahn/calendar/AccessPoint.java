package net.geant.autobahn.calendar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendarClient;

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
     * reads properties from app.properties 
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
     * Entry point for application:<br/>
     * reads properties from app.properties 
     * @param props
     * @throws Exception when could not be initialized or properties parameter is null
     */
    public AccessPoint(Properties props) throws Exception {

        if (props != null) {
            properties = props;
        }
        else {
            throw new Exception("No properties provided.");
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
                instance.log.error("Error while creating Calendar module AccessPoint", e);
            }
        }
        return instance;
    }
    
    /**
     * Returns an instance of AccessPoint. Singleton.
     * The AP instance will be initialized with the provided properties.
     * @param props
     * @return
     */
    public synchronized static AccessPoint getInstance(Properties props) {
        
        if (instance == null) {
            try {
                instance = new AccessPoint(props);
            } catch (Exception e) {
                instance.log.error("Error while creating Calendar module AccessPoint", e);
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
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
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
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#getConstraints()
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
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#removeReservation()
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
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#checkCapacity()
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