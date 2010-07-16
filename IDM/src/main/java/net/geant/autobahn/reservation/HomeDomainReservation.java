/*
 * HomeDomainReservation3.java
 *
 * 2006-11-13
 */
package net.geant.autobahn.reservation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.interdomain.pathfinder.InterdomainPathfinder;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.reservation.states.State;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

/**
 * Subclass of <code>Reservation</code>. Instances of the class are executed
 * in a home domain of each reservation.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HomeDomainReservation extends AutobahnReservation {

    /**
     * <code>InterdomainPathfinder</code> instance used by the reservations to
     * search for interdomain paths.
     */
    protected InterdomainPathfinder pathFinder = null;
    
    // The paths that the reservation has checked in the past and were not suitable
    private List<Path> failedPaths = null;

    private Iterator<Path> paths = null;
    private List<Link> excludedLinks = new ArrayList<Link>();
    private List<String> failures = new ArrayList<String>();
    private DomainConstraints userConstraints = null; 
    
    private HomeDomainState stateObject;
    
    private Timer timer = new Timer(false);
    
    private int domainsActivated = 0;
    private int domainsFinished = 0;
    private boolean processNow;
    
    /**
     * Default constructor
     */
    public HomeDomainReservation() {
        super();
    }

    /**
     * Creates a <code>HomeDomainReservation</code> object and puts it into
     * its initial state.
     * 
     * @param startPort <code>Port</code> start port
     * @param endPort <code>Port</code> end port
     * @param startTime <code>Calendar</code> start time
     * @param endTime <code>Calendar</code> end time
     * @param priority <code>int</code> priority of the reservation
     */
    public HomeDomainReservation(Port startPort, Port endPort,
            Calendar startTime, Calendar endTime, int priority) {
        super(startPort, endPort, startTime, endTime, priority);
    }
    
    public void run() {
    	stateObject.run(this);
    }
    
    public void cancel() {
    	stateObject.cancel(this);
    }
    
	public void withdraw() {
    	stateObject.withdraw(this);
	}

	public void modify(Calendar startTime, Calendar endTime) {
		stateObject.modify(this, startTime, endTime);
	}

	public void recover() {
		stateObject.recover(this);
    }
    
    public void reservationScheduled(int msgCode, String arguments,
			boolean success, GlobalConstraints global) {
        stateObject.reservationScheduled(this, msgCode, arguments, success, global);
    }

    public void reservationCancelled(String message, boolean success) {
    	stateObject.reservationCancelled(this, message, success);
    }

	public void reservationWithdrawn(String message, boolean success) {
		stateObject.reservationWithdrawn(this, message, success);
	}

    public void reservationActivated(String message, boolean success) {
    	stateObject.reservationActivated(this, message, success);
    }

    public void reservationModified(Calendar startTime, Calendar endTime,
			String message, boolean success) {
    	stateObject.reservationModified(this, startTime, endTime, message, success);
	}

	public void reservationFinished(String message, boolean success) {
    	stateObject.reservationFinished(this, message, success);
    }

    /**
     * Performs operations after the reservation has been scheduled. Mail is
     * being sent to the user and all status listeners are being notified.
     * 
     * @param msg <code>String</code> success message
     */
    public void success(String msg) {
        
        log.info(this + " scheduled");
        
        switchState(HomeDomainState.SCHEDULED);
        
        for(ReservationStatusListener listener : statusListeners) {
            listener.reservationScheduled(getBodID());
        }
    }
    
    /**
     * Performs operations that has to be done when one of paths fails.
     * 
     * @param msg <code>String</code> paths failure message
     * @throws RemoteException 
     */
    public void pathFailed(int code, String args) {
        this.addToFailedPaths(this.getPath());    // Keep a list of all paths that were tested and failed

        String msg = ReservationErrors.getInfo(code, args);
    	
        log.info(this + " PATH FAILURE: " + msg);
        
        failures.add("Path#" + (failures.size() + 1) + ": " + msg);
    }
    
    /**
     * Performs operations that has to be done when all paths fail. Mail is
     * being sent to the user and all the status listeners are being notified.
     * 
     * @param msg <code>String</code> reservation failure message
     */
    public void fail() {
    	String msg = getFailureCause();  
    	
        log.info(this + ", FAILURE: " + msg);
        
        switchState(HomeDomainState.FAILED);
        
        for(ReservationStatusListener listener : statusListeners) {
            listener.reservationProcessingFailed(getBodID(), msg);
        }
    }

    public void fail(String msg) {
    	String message = getFailureCause() + msg;
    	
    	log.info(this + ", FAILURE: " + msg);
    	
        switchState(HomeDomainState.FAILED);
        
        for(ReservationStatusListener listener : statusListeners) {
            listener.reservationProcessingFailed(getBodID(), message);
        }
    }
    
    /**
     * 
     * @return
     */
    public String getFailureCause() {
    	String temp = "";
    	
    	if(failures.size() > 0) {
    		for(String failure : failures) {
    			temp += failure + "\n";
    		}
    	}

    	return temp;
    }
    
    /**
     * @return the failedPaths
     */
    public List<Path> getFailedPaths() {
        return failedPaths;
    }

    /**
     * @param failedPath a Path to add to the failedPaths list
     */
    public void addToFailedPaths(Path failedPath) {
        if (failedPaths == null) {
            failedPaths = new ArrayList<Path>();
        }
        failedPaths.add(failedPath);
    }

    /**
     * Returns the <code>Iterator</code> on paths found by pathfinder.
     * 
     * @return <code>Iterator</code> on <code>Path</code> objects
     */
    public Iterator<Path> getPaths() {
        return paths;
    }

    /**
     * Sets the <code>Iterator</code> on paths found by pathfinder.
     * 
     * @param paths <code>Iterator</code> on <code>Path</code> objects
     */
    public void setPaths(Iterator<Path> paths) {
        this.paths = paths;
    }

    /**
     * Returns an <code>InterdomainPathfinder</code> object used by the
     * reservation
     * 
     * @return <code>InterdomainPathfinder</code> object
     */
    public InterdomainPathfinder getPathFinder() {
        return pathFinder;
    }

    /**
     * Sets the pathfinder.
     * 
     * @param pathFinder <code>InterdomainPathfinder</code> object to set
     */
    public void setPathFinder(InterdomainPathfinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public DomainConstraints getUserConstraints() {
		return userConstraints;
	}

	public void setUserConstraints(DomainConstraints userConstraints) {
		this.userConstraints = userConstraints;
	}

	public boolean isProcessNow() {
		return processNow;
	}

	public void setProcessNow(boolean processNow) {
		this.processNow = processNow;
	}

	/**
     * Adds specified <code>Link</code> object to the list of links to exclude
     * in the next pathfinding process.
     * 
     * @param link <code>Link</code> object to exclude
     */
    public void excludeLink(Link link) {
        excludedLinks.add(link);
    }

    /**
     * Clears the list of excluded links.
     *
     */
    public void clearExcluded() {
        excludedLinks.clear();
    }
    
    /**
     * Gets list of links that should not be present in the paths calculated by
     * an interdomain pathfinder in the next pathfinding process.
     * 
     * @return <code>List</code> of <code>Link</code> objects that are excluded
     */
    public List<Link> getExcludedLinks() {
        return excludedLinks;
    }

    /**
     * Sets <code>List</code> of <code>Link</code> objects that should be
     * excluded.
     * 
     * @param excludedLinks
     *            <code>List</code> of <code>Link</code> objects to be
     *            excluded
     */
    public void setExcludedLinks(List<Link> excludedLinks) {
        this.excludedLinks = excludedLinks;
    }

    @Override
    public State getStateObject() {
        return stateObject;
    }

    @Override
    public void setState(int state) {
        this.intState = state;
        this.stateObject = HomeDomainState.getState(state);
        this.stateObject.stateEnter(this);
    }
    
    /**
     * Reservation's state is changed to a new state. New state is not being
     * executed.
     * 
     * @param newState <code>HomeDomainState</code> state to set
     */
    public void setState(HomeDomainState newState) {
        if(stateObject != null) {
            stateObject.stateExit(this);
        }
        
        this.stateObject = newState;
        this.intState = newState.getCode();
        
        stateObject.stateEnter(this);
    }

    /**
     * Reservation switches to a new state and executes it.
     * 
     * @param newState <code>HomeDomainState</code> state to switch to
     */
    public void switchState(HomeDomainState newState) {
        setState(newState);
        
        // execute the state
        newState.run(this);
    }
    
    @Override
    public void gotoInitialState() {
        setState(HomeDomainState.ACCEPTED);
    }

	public void activate(boolean success) {
		reservationActivated("Activation", success);
	}

	public void finish(boolean success) {
		reservationFinished("Finished", success);
	}

	public void nextDomainActivated() {
		domainsActivated++;
	}
	
	public boolean areAllDomainsActivated() {
		return path.getDomainCount() == domainsActivated;
	}
    
	public void nextDomainFinished() {
		domainsFinished++;
	}
	
	public boolean areAllDomainsFinished() {
		return path.getDomainCount() == domainsFinished;
	}
	
	public void addTimeout(ReservationTimeout task, int time) {
		timer.schedule(task, time);
	}
	
	public void cancelTimeouts() {
		timer.cancel();
		timer = new Timer();
	}
}
