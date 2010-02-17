/**
 * 
 */
package net.geant.autobahn.reservation;

/**
 * @author Michal
 *
 */
public interface ServiceStatusListener {
	
	void scheduled(String serviceID);
	
	void failed(String serviceID);

}
