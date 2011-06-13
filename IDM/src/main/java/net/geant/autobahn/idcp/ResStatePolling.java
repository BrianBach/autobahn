/**
 * 
 */
package net.geant.autobahn.idcp;

import java.rmi.RemoteException;

import net.geant.autobahn.idm.AccessPoint;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;

/**
 * This is for supporting scenarios without notifications
 *  Periodically asks for status of a reservation
 * @author PCSS
 */
public class ResStatePolling extends Thread {
	
	private String endpoint;
	private String resId;
	
	public ResStatePolling(String endpoint, String resId) {
		
		this.endpoint = endpoint;
		this.resId = resId;
		this.start();
	}
	
	@Override
	public void run() {
		
		OscarsClient idcp = new OscarsClient(endpoint);
		String state = "UNKNOWN";
		
		while (true) {
			
			try {
				ResDetails res = idcp.queryReservation(resId);
				state = res.getStatus();
				Reservation r = AccessPoint.getInstance().getAutobahnReservation(resId);
				
				if (res.getStatus().equals("ACCEPTED") || res.getStatus().equals("INCREATE")) {
					// do nothing
				} else if (res.getStatus().equals("PENDING")) {
					// do nothing
				} else if (res.getStatus().equals("INCREATE")) {
					r.setState(HomeDomainState.ACTIVATING.getCode());
				} else if (res.getStatus().equals("ACTIVE")) {
					r.setState(HomeDomainState.ACTIVE.getCode());
				} else if (res.getStatus().equals("FINISHED")) {
					r.setState(HomeDomainState.FINISHED.getCode());
					break;
				} else if (res.getStatus().equals("FAILED")) {
					r.setState(HomeDomainState.FAILED.getCode());
					break;
				} else if (res.getStatus().equals("CANCELLED")) {
					r.setState(HomeDomainState.CANCELLED.getCode());
					break;
				}
				Thread.sleep(1000 * 10);
				
			} catch (RemoteException e) {
				System.out.println("ResStatePooling ex - " + e.getMessage());
				break;
			} catch (Exception e1) { 
				System.out.println("ResStatePooling ex - " + e1.getMessage());
				break;
			}
		}
		System.out.println("idcp reservation state - " + resId + ", state: " + state);
	}
}
