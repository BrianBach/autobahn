package net.geant.autobahn.calendar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar;

import org.apache.log4j.Logger;
import org.hibernate.exception.ExceptionUtils;

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
    
    private StringBuffer initChecks;
    
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
            log.error("Error while init: " + e.getMessage());
            log.debug("Error info: ", e);
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
        
        state = State.RESTARTING;
        
        runBeforeInitChecks();
        
        log.info("===== ResourcesReservationCalendar module Initialization =====");
        long stime = System.currentTimeMillis();
        
        try {
            String type = properties.getProperty("db.type");
            if (type != null) {
                type = type.toLowerCase();
            }
            else {
                throw new Exception("db.type property can not be empty!");
            }
            
        	if(type.equalsIgnoreCase("eth") || type.equalsIgnoreCase("ethernet")) {
        		this.constraintsCalendar = new EthConstraintsReservationCalendar();	
        	} else if(type.equalsIgnoreCase("sdh")) {
        		this.constraintsCalendar = new SdhConstraintsReservationCalendar();
        	}
        	
        	this.setupTime = Integer.valueOf(properties.getProperty("tool.time.setup"));
        	this.teardownTime = Integer.valueOf(properties.getProperty("tool.time.teardown"));

        	state = State.READY;
            
        } catch (Exception e) {
            state = State.ERROR;
            Throwable thr = ExceptionUtils.getRootCause(e);
            if (thr instanceof java.net.BindException) {
                log.error("Error while Calendar init: " + thr.getMessage() +
                        "\nPlease check whether another server is running using" +
                        " the same ports as Autobahn. You can check and edit the" +
                        " ports used by Autobahn in etc/services.properties.");                
            }
            else {
                log.error("Error while Calendar init: " + ((thr == null)?"":thr.getMessage()));
            }
            log.debug("Error info: ", e);
        }
    	
        float total = (System.currentTimeMillis() - stime) / 1000.0f;
        log.info("===== End of ResourcesReservationCalendar module initialization - " + total + " secs =====");
        
    	runAfterInitChecks();
	}
    
    /* (non-Javadoc)
     * @see net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendar#addReservation()
     */
    public void addReservation(IntradomainPath path, long capacity, Calendar start, Calendar end)
			throws ConstraintsAlreadyUsedException {
	
		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
		// Reserve constraints
		constraintsCalendar.reserveResources(path, start, end);

        // Add capacity to links calendars
		for(GenericLink glink : path.getLinks()) {
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
    public IntradomainPath getConstraints(IntradomainPath ipath,
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
    public void removeReservation(IntradomainPath path, long capacity, Calendar start, Calendar end) {

		// Extend time window
		start = (Calendar) start.clone();
		start.add(Calendar.SECOND, -1 * setupTime);
		end = (Calendar) end.clone();
		end.add(Calendar.SECOND, teardownTime);
		
        // Remove capacity from Calendar
		for(GenericLink glink : path.getLinks()) {
			AdditiveCalendar calendar = capacityCalendars.get(glink);
			if(calendar != null) {
				calendar.removeReservation(capacity, start, end);
			}
		}
		
        // Remove constraints reservation
        constraintsCalendar.releaseResources(path, start, end);
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
    
    /**
     * Performs checks before initialization has taken place
     */
    public void runBeforeInitChecks() {
        initChecks = new StringBuffer("");
        
        // Check properties
        
        String db_type = properties.getProperty("db.type");
        if (db_type == null || db_type.equalsIgnoreCase("none") || db_type.equals("")) {
            initChecks.append("db.type property is empty, please check " +
            		"calendar.properties file.\n");
        }
        else if (!db_type.equalsIgnoreCase("ethernet") && !db_type.equalsIgnoreCase("eth")
                && !db_type.equalsIgnoreCase("sdh")) {
            initChecks.append("db.type property currently supports either " +
            		"ethernet (or eth) or sdh value\n");
        }
    }
    
    /**
     * Performs checks after initialization has taken place
     */
    public void runAfterInitChecks() {
        if (state == State.ERROR || initChecks==null) {
            log.error("Calendar module was not initialized successfully. Please check debug.log for" +
                    " more information.");
            return;
        }
        
        // Add any checks here
        
        if (initChecks.toString().equals("")) {
            log.info("ResourcesReservationCalendar module was initialized successfully.");
        } else {
            log.info("\nResourcesReservationCalendar module initialization reported " +
            		"the following potential problems:\n"+initChecks.toString());
        }
    }
    
    public ConstraintsReservationCalendar getConstraintsCalendar() {
    	return constraintsCalendar;
    }
}