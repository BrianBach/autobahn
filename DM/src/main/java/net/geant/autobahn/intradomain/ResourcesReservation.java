package net.geant.autobahn.intradomain;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.dao.hibernate.DmHibernateUtil;
import net.geant.autobahn.dm2idm.Dm2Idm;
import net.geant.autobahn.dm2idm.Dm2IdmClient;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.timer.EventsTimer;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.resourcesreservationcalendar.ResourcesReservationCalendarClient;
import net.geant.autobahn.tool.Tool;
import net.geant.autobahn.tool.ToolClient;
import net.geant.autobahn.topologyabstraction.TopologyAbstraction;
import net.geant.autobahn.topologyabstraction.TopologyAbstractionClient;

import org.apache.log4j.Logger;

/**
 * Main class of the Domain Manager.<br/>
 * Allows resources handling: adding and removing reservation, checking resources.<br/>
 * Manages also time driven events such as activation or finishing the reservation.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class ResourcesReservation {
	
	private final static Logger log = Logger.getLogger(ResourcesReservation.class);
	    
    private static final int PATH_LIMIT = 3; 
    
//    private TopologyConverter converter = null;
    private String taAddress = null;
    private String calendarAddress = null;
    private IntradomainPathfinder pathfinder = null;
    private ToolClient tool = null;
    private String idmAddress = null;
    private PersistentReservationsManager prManager = null;
    
    private ResourcesReservationCalendarClient calendar = null;
    private int setupTime;
    private int tearDownTime;
    
    // Timers for reservations
    private EventsTimer timer = new EventsTimer();

    // Reservation history
    private Map<String, IntradomainReservation> reservations = new HashMap<String, IntradomainReservation>();
    
    /**
     * Creates an instance.
     * 
     * @param converter Topology converter
     * @param pathfinder Intradomain pathfinder
     * @param props Properties for the system
     */
    public ResourcesReservation(IntradomainPathfinder pathfinder,
			PersistentReservationsManager prManager, Properties props) {
    	
    	// this.converter = converter;
    	this.pathfinder = pathfinder;
    	this.prManager = prManager;
    	
        this.taAddress = props.getProperty("topologyabstraction.address");
            
    	tool = new ToolClient(props.getProperty("tool.address"));
    	this.idmAddress = props.getProperty("idm.address");
    	
    	setupTime = Integer.valueOf(props.getProperty("tool.time.setup"));
		tearDownTime = Integer.valueOf(props.getProperty("tool.time.teardown"));
		
		this.calendarAddress = props.getProperty("resourcesreservationcalendar.address");
		calendar = new ResourcesReservationCalendarClient(calendarAddress);
    }

	/**
	 * Restores the internal DM memory. Retrieves reservation from the database
	 * and performs certain action again. Time driven events are properly
	 * restored for all not ended reservation.
	 */
    public void restoreReservations() {
		//Restore active reservations from DB
    	DmHibernateUtil hbm = DmHibernateUtil.getInstance();
		reservations = prManager.loadReservations();
		log.info("DM Recovery - Found: " + reservations.size() + " active reservations");
		
		for(IntradomainReservation res : reservations.values()) {
			Calendar now = Calendar.getInstance();
			
			ReservationParams par = res.getParams();

			Calendar endTime = par.getEndTime();
			Calendar startTime = par.getStartTime();
			String resID = res.getReservationId();
			
			//Add it to Calendar again if it's not expired
			try {
				calendar.addReservation(res.getReservedPath().getLinks(), 
					par.getCapacity(), par.getPathConstraints(), 
					par.getStartTime(), endTime);
			} catch (ConstraintsAlreadyUsedException e) {
				log.error("Error while restoring DM state", e);
			}

			log.info("Reservation: " + resID + " tasks:");
			
			if(res.isPathCreated()) {
				// Prepare tool
				tool.setReservationAdded(resID);
				
				if(now.compareTo(startTime) < 0) {
					log.info("  ActivatedTask scheduled at: " + startTime.getTime());
					timer.schedule(resID, startTime, new ActivatedTask(resID));
				} else if(!res.isActive()) {
					log.info("  Start time missed... Other domains may be already cancelled");
					
	    			Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
	    			dm2Idm.activate(resID, false);
	    			
	    			continue;
				}
			} else {
				Calendar nStartTime = (Calendar) startTime.clone();
				nStartTime.add(Calendar.SECOND, -1 * setupTime);

				// Missed activation
				if(now.compareTo(nStartTime) > 0) {
					log.info("  Activation missed...");
					
	    			Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
	    			dm2Idm.activate(resID, false);
	    			
					continue;
				}
				
				log.info("  ActiveTask scheduled at: " + nStartTime.getTime());
				timer.schedule(resID, nStartTime, new ActiveTask(resID));
			}
			
			//Schedule the finish event at finish time
			if(now.compareTo(endTime) < 0) {
				log.info("  FinishTask scheduled at: " + endTime.getTime());
				timer.schedule(resID, endTime, new FinishTask(resID));
			} else {
				log.info("  Finish missed...");
				
				releaseResources(resID);
				
    			Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
    			dm2Idm.finish(resID, false);
			}
		}
		
		hbm.closeSession();
    }

	/**
	 * Checks availability of capacity and other network resources for a
	 * reservation with given params.
	 * 
	 * @param links
	 *            Abstract links that are to be used by a reservation
	 * @param par
	 *            Reservation parameters: time period, capacity, vlan etc.
	 * @return DomainConstraints object containing possible network constraints
	 *         of possible paths, null if the given links are not correct.
	 * @throws OversubscribedException
	 *             When domain do not have enough resources in the given period
	 */
	public DomainConstraints checkResources(Link[] links,
			ReservationParams par) throws OversubscribedException {
		
		final Link ingress = links[0];
		final Link egress = links[links.length - 1];
		
        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
		GenericLink src = ta.getEdgeLink(ingress);
		GenericLink dest = ta.getEdgeLink(egress);

		if(src == null || dest == null) {
			log.debug("Edge link not found!");
			return null;
		}

		Calendar sTime = par.getStartTime();
		Calendar eTime = par.getEndTime();
		
		// Check capacity of ingress and egress in calendar
		checkEdgeLinkCapacity(ingress, par.getCapacity(), sTime, eTime);
		checkEdgeLinkCapacity(egress, par.getCapacity(), sTime, eTime);
		
		// Get paths from PF
		Set<GenericLink> excluded = new HashSet<GenericLink>();
		
		List<IntradomainPath> paths = pathfinder.findPaths(src, dest, 
				par.getCapacity(), excluded, PATH_LIMIT, par.getUserVlanId());

		if (paths==null) {
		    //TODO: Perhaps use a more fine-grained exception type
		    throw new OversubscribedException("No suitable paths found", links[1].getBodID());
		}
		
		log.debug(paths.size() + " possible paths found (before calendar checking)");
		
		// Filter paths in calendar
		Set<IntradomainPath> results = new HashSet<IntradomainPath>();
		int i = 0;
		
		while(i < paths.size()) {
			IntradomainPath path = paths.get(i++); 
			
			if(path == null)
				continue;

			// Filter out links using capacity in calendar 
			List<GenericLink> toExclude = calendar.checkCapacity(path.getLinks(),
					par.getCapacity(), sTime, eTime);
			
			if(toExclude.isEmpty()) {
				// Path contains enough capacity.
				results.add(path);
			} else {
				// Link is excluded. Then new path should be found and checked
				excluded.addAll(toExclude);

				int newPathsNeeded = 1;
				
				// Filter out already found paths
				for(int j = i; j < paths.size(); j++) {
					IntradomainPath p2 = paths.get(j);
					if(p2.containsAny(excluded)) {
						newPathsNeeded++;
						paths.set(j, null);
					}
				}
				
				List<IntradomainPath> npaths = pathfinder.findPaths(src, dest, 
						par.getCapacity(), excluded, newPathsNeeded, par.getUserVlanId());

				if(npaths != null)
					npaths.removeAll(paths);
				
				if(npaths != null && npaths.size() > 0) {
					int j = --i;
					for(IntradomainPath npath : npaths) {
						paths.set(j++, npath);
					}
				}
			}
		}
		
		if(results.size() > 0) {
			DomainConstraints dcon = new DomainConstraints();
			
			// Filtering Constraints
			for(IntradomainPath path : results) {
				PathConstraints res = calendar.getConstraints(path, 
						sTime, eTime);
				dcon.addPathConstraints(res);
			}
			
			return dcon;
		} else
			throw new OversubscribedException("Not enough available capacity",
					links[1].getBodID());
	}

	/**
	 * Adds a reservation to the underlying network managment system.
	 * 
	 * @param resID
	 *            String reservation identifier
	 * @param links
	 *            Links selected for the reservation
	 * @param par
	 *            Reservation parameters agreed among all reservation domains
	 * @throws OversubscribedException
	 *             When for some reasons domain can not quarantee requested
	 *             capacity
	 * @throws ConstraintsAlreadyUsedException
	 *             When selected resources are already reserved for another
	 *             reservation in the same time.
	 */
	public void addReservation(String resID, Link[] links,
			ReservationParams par) throws OversubscribedException, 
			ConstraintsAlreadyUsedException {

        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
		GenericLink src = ta.getEdgeLink(links[0]);
		GenericLink dest = ta.getEdgeLink(links[links.length - 1]);
		
		PathConstraints pcon = par.getPathConstraints();
		Calendar sTime = par.getStartTime();
		Calendar eTime = par.getEndTime();
		
		List<GenericLink> excluded = new ArrayList<GenericLink>();
		
		// Get a path from PF and check in calendar
		IntradomainPath path = null;
		boolean valid = false;
		
		while(!valid) {
			path = pathfinder.findPath(src, dest, par.getCapacity(), pcon,
					excluded, par.getUserVlanId());
		
			if(path == null)
				break;
			
			List<GenericLink> toExclude = calendar.checkCapacity(path.getLinks(),
					par.getCapacity(), sTime, eTime);
			
			if(toExclude.isEmpty()) {
				valid = true;
			} else {
				// Exclude link and try another path
				excluded.addAll(toExclude);
			}
		}
		
		if(valid) {
	        // Try to reserve vlan
			calendar.addReservation(path.getLinks(), par.getCapacity(), pcon,
					sTime, eTime);

			log.info("Reserving: " + par.getCapacity() + " bps on: \n" + path + "\n" +
					"Constraints: " + pcon);
			
	        // Store reservation information (path, parameters etc.)
			IntradomainReservation reservation = new IntradomainReservation(resID, path, par);
			reservations.put(resID, reservation);
			prManager.save(reservation);
			
	        // Schedule activation task
	        if(!sTime.after(Calendar.getInstance())) {
	        	// process now
		        timer.scheduleNow(resID, new ActiveTask(resID));
	        } else {
	    		sTime = (Calendar) sTime.clone();
	    		sTime.add(Calendar.SECOND, -1 * setupTime);
	        	
	        	timer.schedule(resID, sTime, new ActiveTask(resID));	
	        }
		} else {
			log.error("AddReservation: No paths found");
			throw new OversubscribedException("Not enough available capacity",
					links[1].getBodID());
		}
	}

	/**
	 * Removes the reservation from the Domain Manager components and underlying
	 * network managment system. All reserved resources are released.
	 * 
	 * @param resID String reservation identifier
	 */
	public void removeReservation(String resID) {
		timer.cancel(resID);
		
		releaseResources(resID);
	}

	/**
	 * Checks whether it's possible to change the time period of an existing
	 * reservation. No rerouting is possible.
	 * 
	 * @param resID
	 *            String reservation ideintifier
	 * @param nStartTime
	 *            Calendar new start time
	 * @param nEndTime
	 *            Calendar new end time
	 * @return True if the changing of the time is possible. false - otherwise
	 */
	public boolean checkModification(String resID, Calendar nStartTime, Calendar nEndTime) {
		
		IntradomainReservation reservation = reservations.get(resID);
        if(reservation == null) {
        	log.warn("CheckModification: Reservation " + resID + " not found!");
        	return false;
        }
        
        ReservationParams par = reservation.getParams();
        IntradomainPath path = reservation.getReservedPath();
        
        Calendar now = Calendar.getInstance();
        
		Calendar sTime = par.getStartTime();
		Calendar eTime = par.getEndTime();
		
        if(now.after(sTime)) {
        	// Reservation already running
        	// Ignore start time
        	nStartTime = sTime;
        } else if(now.after(nStartTime)){
        	// Attempt to create reservation in the past
        	return false;
        }
        
        //Temporarily remove the reservation
        calendar.removeReservation(path.getLinks(), par.getCapacity(), 
        		par.getPathConstraints(), sTime, eTime);
        
        
        boolean result = false;
        
        List<GenericLink> failed = calendar.checkCapacity(path.getLinks(), 
        		par.getCapacity(), nStartTime, nEndTime);
        
        if(failed.isEmpty()) {
        	PathConstraints pcon = calendar.getConstraints(path, nStartTime, nEndTime);
        	if(pcon != null)
        		result = true;
        }
        
        // Restore original reservation
        try {
			calendar.addReservation(path.getLinks(), par.getCapacity(), 
					par.getPathConstraints(), sTime, eTime);
		} catch (ConstraintsAlreadyUsedException e) {
			log.warn("Check modification restore: ALREADY IN USE !");
		}
        
		return result;
	}

	/**
	 * Changes the time period of an existing reservation.
	 * 
	 * @param resID String reservation idenitfier 
	 * @param nStartTime
	 *            Calendar new start time
	 * @param nEndTime
	 *            Calendar new end time
	 */
	public void modifyReservation(String resID, Calendar nStartTime, Calendar nEndTime) {
		IntradomainReservation reservation = reservations.get(resID);
        if(reservation == null) {
        	log.warn("ModifyReservation: Reservation " + resID + " not found!");
        	return;
        }
        
        if(!checkModification(resID, nStartTime, nEndTime)) {
        	log.warn("Modification failed");
        	return;
        }

        // Old info
        ReservationParams par = reservation.getParams();
        IntradomainPath path = reservation.getReservedPath();
		Calendar sTime = par.getStartTime();
		Calendar eTime = par.getEndTime();
        
        // Remove the old reservation
        calendar.removeReservation(path.getLinks(), par.getCapacity(), 
        		par.getPathConstraints(), sTime, eTime);

        // Add modified reservation
        try {
			calendar.addReservation(path.getLinks(), par.getCapacity(), 
					par.getPathConstraints(), nStartTime, nEndTime);
		} catch (ConstraintsAlreadyUsedException e) {
        	log.warn("Modification failed: Constraints in use");
        	return;
		}
		
		// Update time in the registry
		par.setStartTime(nStartTime);
		par.setEndTime(nEndTime);
		
		// Modify time driven events
		timer.cancel(resID);

		Calendar now = Calendar.getInstance();
		
		if(now.after(sTime)) {
			// Reservation is Active - Reschedule FinishTask
			timer.schedule(resID, nEndTime, new FinishTask(resID));
		} else {
			// Reschedule ActivateTask
    		nStartTime = (Calendar) nStartTime.clone();
    		nStartTime.add(Calendar.SECOND, -1 * setupTime);

			timer.schedule(resID, nStartTime, new ActiveTask(resID));
		}
	}

	private boolean releaseResources(String resID) {
		
        IntradomainReservation reservation = reservations.remove(resID);
        if(reservation == null) {
        	log.warn("Attempt to release already released resources!!!");
        	return false;
        }

        // Attach IntraReservation to the current hibernate session
        prManager.attach(reservation);
        
        ReservationParams par = reservation.getParams();
        IntradomainPath path = reservation.getReservedPath();
        
		log.info("Releasing " + par.getCapacity() + " bps on: \n" + path + "\n" +
				"Constraints: " + par.getPathConstraints());
        
		// Remove from Calendar
		calendar.removeReservation(path.getLinks(), par.getCapacity(), 
				par.getPathConstraints(), par.getStartTime(), par.getEndTime());
		
		boolean removeInTP = reservation.isPathCreated();
		log.debug("Res: " + resID + ", removing in TP: " + removeInTP);
		
        // Release in Tool
		if(removeInTP) {
	        try {
				tool.removeReservation(resID, path.getLinks(), par);
			} catch (Exception e) {
				log.error("Problem while removing reservation from a Tool", e);
				return false;
			}
		}
        
		// Delete from DB
		prManager.delete(reservation);
		
        return true;
	}
	
	private void checkEdgeLinkCapacity(Link link, long capacity,
			Calendar sTime, Calendar eTime) throws OversubscribedException {
		
        TopologyAbstraction ta = new TopologyAbstractionClient(taAddress);
		GenericLink glink = ta.getEdgeLink(link);
		
		List<GenericLink> links = new ArrayList<GenericLink>();
		links.add(glink);
		
		List<GenericLink> failedLinks = calendar.checkCapacity(links, capacity,
				sTime, eTime);
		log.debug("failedLinks:"+failedLinks);
        
		if(!failedLinks.isEmpty()) {
			throw new OversubscribedException("Not enough available capacity", link.getBodID());
		}
	}

	/**
	 * Task performs resources reservation operation when reservation is to be
	 * activated. Task is executed some amount of time before the exact start time
	 * (configurable). TP is requested to build specified curcuit.
	 * 
	 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
	 */
    private class ActiveTask extends TimerTask {
    	
    	private String resID;
    	
    	ActiveTask(String resID) {
    		super();
    		this.resID = resID;
		}
    	
        @Override
        public void run() {
        	IntradomainReservation reservation = reservations.get(resID);
        	
    		ReservationParams par = reservation.getParams();
    		List<GenericLink> glinks = reservation.getReservedPath().getLinks();

    		boolean success = true;
    		
    		reservation.setPathCreated(true);
   			prManager.save(reservation);
   			
            try {
    	        tool.addReservation(resID, glinks, par);
    		} catch (Exception e) {
    			success = false;
    			log.error("Problem while adding reservation to a Tool", e);
    		}

    		if(success) {
    			reservation = reservations.get(resID);
    			
    			if(reservation == null) {
    				log.error("Res: " + resID + ", activation completed, but already cancelled...");
    				return;
    			}
    			
    			// Schedule Activated & Finish task
    			if(!par.getStartTime().after(Calendar.getInstance())) {
    				// process now - activate immediately
    				timer.scheduleNow(resID, new ActivatedTask(resID));
    			} else {
    				log.info("Resources ready, activation at: " + par.getStartTime().getTime());
    				timer.schedule(resID, par.getStartTime(), new ActivatedTask(resID));
    			}
    			timer.schedule(resID, par.getEndTime(), new FinishTask(resID));
    		} else {
    			reservation.setPathCreated(false);
    			//PersistentReservations.delete(reservation);
    			
    			Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
    			dm2Idm.activate(resID, false);
    		}
        }
    }
    
    /**
     * Task performs operations when reservation is to be finish.
     * 
     * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
     *
     */
    private class FinishTask extends TimerTask {
    	private String resID;
    	
    	FinishTask(String resID) {
    		super();
    		this.resID = resID;
		}
    	
        @Override
        public void run() {
        	boolean success = releaseResources(resID);
        	
        	// Notify IDM of finishing result
        	Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
        	dm2Idm.finish(resID, success);
        }
    }

	/**
	 * Task executed when the start time is reached. Notifies IDM about the
	 * result of activation in underlying TP.
	 * 
	 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
	 * 
	 */
    private class ActivatedTask extends TimerTask {
    	private String resID;
    	
    	public ActivatedTask(String resID) {
    		this.resID = resID;
		}
    	
		@Override
		public void run() {
			Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
			dm2Idm.activate(resID, true);
			
			IntradomainReservation reservation = reservations.get(resID);
			
    		reservation.setActive(true);
   			prManager.save(reservation);
		}
    }
    
    /**
     * Returns the TP endpoint. Used for debugging purposes
     * @return
     */
    public Tool getTool() {
    	return tool;
    }
    
    /**
     * Resets the modules of the class.
     */
    public void dispose() {
    	timer.cancelAll();
    }
    
    /**
     * Used by EthMonitoring class.
     * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
     */       
    public String getTopologyAbstractionAddress() {
    	return taAddress;
    }
    
    /**
     * Used by EthMonitoring class.
     * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
     */      
    public IntradomainPathfinder getPathfinder() {
    	return pathfinder;
    }
    
    /**
     * Used by EthMonitoring class.
     * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
     */      
    public List<GenericLink> getResvMapping(String resID) {
    	return reservations.get(resID).getReservedPath().getLinks(); 
    }
    
    /**
     * Used by EthMonitoring class.
     * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
     */      
    public Map<String, IntradomainPath> getAllResvMapping() {
    	Map<String, IntradomainPath> result = new HashMap<String, IntradomainPath>();
    	for(String resID : reservations.keySet()) {
    		result.put(resID, reservations.get(resID).getReservedPath());
    	}
    	
    	return result; 
    }
    
    /**
     * Used by EthMonitoring class.
     * @author <a href="mailto:gkamas@cti.gr">Gkamas Apostolos</a>
     */      
    public ReservationParams getResvParams(String resID) {
    	return reservations.get(resID).getParams();
    }
}
