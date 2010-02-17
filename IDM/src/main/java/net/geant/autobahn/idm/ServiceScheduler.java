package net.geant.autobahn.idm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.Service;
import net.geant.autobahn.reservation.ServiceStatusListener;

import org.apache.log4j.Logger;

/**
 * Class executes services in the proper way. One after another. Prioritizes
 * execution and cancelling of services.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
final public class ServiceScheduler implements Runnable,  ServiceStatusListener {

	static final private Logger log = Logger.getLogger(ServiceScheduler.class);
	
	private Thread thread;
	private Queue<String> exec = new LinkedBlockingQueue<String>();
	private Queue<String> cancel = new LinkedBlockingQueue<String>();
	private Queue<String> priorityCancel = new LinkedBlockingQueue<String>();
	private boolean quit, waiting;

	private Map<String, Service> services = new HashMap<String, Service>();
	private Map<String, Service> resToService = new HashMap<String, Service>(); 
	
	/**
	 * Default constructor.
	 */
	public ServiceScheduler() {
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Puts service on execution queue
	 * @param service
	 */
	public synchronized void submitService(Service service) {
		addService(service);
		exec.add(service.getBodID());
		
		// if there is nothing on queue, run is waiting
		// wake up run and process new service
		if (waiting)
			this.notify();
	}
	
	/**
	 * Just adds a service to the scheduler. Does not execute it.
	 * @param service
	 */
	public void addService(Service service) {
		service.setListener(this);
		services.put(service.getBodID(), service);
		for(Reservation res : service.getReservations()) {
			resToService.put(res.getBodID(), service);
		}
	}
	
	public synchronized void stop() {
		quit = true;
		this.notify();
	}
	
	/**
	 * Puts service on cancel queue
	 * @param serviceID
	 * @return false if service with given serviceID does not exists, otherwise true
	 */
	public synchronized boolean cancelService(String serviceID) {
		
		if (!services.containsKey(serviceID)) {
			log.error("cancel service - " + serviceID);
			return false;
		}
		
		cancel.add(serviceID);
		
		// if there is nothing on queue, run is waiting
		// wake up run and process new service
		if (waiting) {
			this.notify();
		}
		
		return true;
	}
	
	/**
	 * Returns service with given serviceID
	 * @param serviceID
	 * @return null if service with given serviceID does not exists
	 */
	public Service queryService(String serviceID) {
		
		return services.get(serviceID);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ServiceStatusListener#failed(java.lang.String)
	 */
	public void failed(String serviceID) {
		
		if (!services.containsKey(serviceID)) {
			log.error("failed - " + serviceID);
			return;
		}
		// one of the reservations failed
		// put service on priority cancel queue
		// do not allow run to wait
		priorityCancel.add(serviceID);
		if (waiting) {
			synchronized (this) {
				this.notify();
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.reservation.ServiceStatusListener#scheduled(java.lang.String)
	 */
	public void scheduled(String serviceID) {
		
		// ignore this event
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		while (!quit) {
			
			Service service = null;
			
			// priority cancel check first
			service = services.get(priorityCancel.poll());
			if (service != null) {
				log.debug("priority cancel service - " + service.getBodID());
				service.cancel();
			} else {
				service = services.get(cancel.poll());
				if (service != null) {
					log.debug("cancel service - " + service.getBodID());
					service.cancel();
				} else {
					service = services.get(exec.poll());
					if (service != null) {
						log.debug("exec service - " + service.getBodID());
						service.execute();
					}
				}
			}
			if (quit)
				break;
			
			// if nothing on queues, wait
			if (priorityCancel.isEmpty() && cancel.isEmpty() && exec.isEmpty()) {
				
				synchronized (this) {
					waiting = true;
					try {
						this.wait();
					} catch (InterruptedException e) { }
				}
				waiting = false;
			}
		}
	}

	/**
	 * Returns List of services sent to this IDM.
	 * @return List of <code>Service</code> objects
	 */
	public List<Service> getServices() {
		
		return new ArrayList<Service>(services.values());
	}

	/**
	 * Returns a <code>Service</code> object identified by the identifier.
	 * @param srvId String identifier of service.
	 * @return <code>Service</code> object, null if not found
	 */
	public Service getService(String srvId) {
		return services.get(srvId);
	}
	
	/**
	 * Returns a <code>Service</code> that contains reservation identified by the identifier.
	 * @param resId String identifier of reservation
	 * @return <code>Service</code> object, null if not found
	 */
	public Service getServiceForReservation(String resId) {
		return resToService.get(resId);
	}
}
