package net.geant.autobahn.testplatform.observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Endpoint;

import net.geant.autobahn.testplatform.helpers.AutobahnManagement;
import net.geant.autobahn.useraccesspoint.callback.UapCallback;

public class StatusObserver implements UapCallback {

	public enum ReservationState {FAILED, SCHEDULED, FINISHED, ACTIVE, CANCELLED};
	
	private String url;
	private Map<String, List<ReservationState>> capturedEvents = new HashMap<String, List<ReservationState>>();
	private List<String> domains;
	private List<String> domainsUp = new ArrayList<String>();
	
	private boolean running = false;
	private Endpoint endpoint = null;
	
	public StatusObserver(String url) {
		this.url = url;
	}

	public void start() {
		System.out.println("Creating (ob)server...");
		
        endpoint = Endpoint.publish(url, this);
        this.running = true;
	}
	
	public void stop() {
        endpoint.stop();
        this.running = false;
	}
	
	public void registerService(String sid) {
		
		capturedEvents.put(sid, new ArrayList<ReservationState>());
	}
	
	public void waitForEvent(String sid) {
		synchronized(sid) {
			try {
				sid.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ReservationState waitAndGetEvent(String sid) {
        waitForEvent(sid);
        return getLastEvent(sid);
	}
	
	/**
	 * 
	 * @param sid
	 * @return
	 */
	public List<ReservationState> getStates(String sid) {
		return capturedEvents.get(sid);
	}
	
	/**
	 * 
	 * @param sid
	 * @return
	 */
	public ReservationState getLastEvent(String sid) {
		List<ReservationState> events = capturedEvents.get(sid);
		
		if(events == null || events.size() == 0)
			return null;
		
		//Return the last one
		return events.get(events.size() - 1);
	}
	
	public void reservationActive(String resId) {
		String sid = getServiceId(resId);
		capturedEvents.get(sid).add(ReservationState.ACTIVE);
		
		synchronized(sid) {
			sid.notify();
		}
	}

	public void reservationCancelled(String resId) {
		String sid = getServiceId(resId);
		capturedEvents.get(sid).add(ReservationState.CANCELLED);
		
		synchronized(sid) {
			sid.notify();
		}
	}

	public void reservationFinished(String resId) {
		String sid = getServiceId(resId);
		capturedEvents.get(sid).add(ReservationState.FINISHED);
		
		synchronized(sid) {
			sid.notify();
		}
	}

	public void reservationModified(String resId, boolean success) {
		// TODO Auto-generated method stub
		
	}

	public void reservationProcessingFailed(String resId, String cause) {
		String sid = getServiceId(resId);
		capturedEvents.get(sid).add(ReservationState.FAILED);
		
		synchronized(sid) {
			sid.notify();
		}
	}

	public void reservationScheduled(String resId) {
		
		String sid = getServiceId(resId);
		capturedEvents.get(sid).add(ReservationState.SCHEDULED);
		
		synchronized(sid) {
			sid.notify();
		}
	}
	
	public String getUrl() {
		return url;
	}
	
	private String getServiceId(String resId) {
		
		for(String sid : capturedEvents.keySet()) {
			if(resId.contains(sid)) {
				return sid;
			}
		}

		return null;
	}

	public void domainUp(String address) {
		address = address.replace("/interdomain", "");
		
		System.out.println("Domain up: " + address);

		domainsUp.add(address);
		if(domains != null && domains.size() > 0) {
			synchronized (domains) {
				domains.removeAll(domainsUp);
				domainsUp.clear();
	
				if(domains.size() == 0)
					domains.notify();
			}
		}
	}
	
	public void waitForDomains(String... addresses) {
		if(!running)
			throw new IllegalStateException("Status observer not running...");
		
		domains = new ArrayList<String>();
		domains.addAll(Arrays.asList(addresses));
		
		if(domainsUp.size() > 0) {
			System.out.println("Domains UP: " + domainsUp.size());
			domains.removeAll(domainsUp);
			domainsUp.clear();
			
			if(domains.size() == 0)
				return;
		}
		
		synchronized (domains) {
			try {
				System.out.println("Waiting...");
				domains.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void waitForDomains(AutobahnManagement... domains) {
		String[] addresses = new String[domains.length];
		for(int i = 0; i < domains.length; i++)
			addresses[i] = domains[i].getDomainAddress();
		
		waitForDomains(addresses);
	}
}
