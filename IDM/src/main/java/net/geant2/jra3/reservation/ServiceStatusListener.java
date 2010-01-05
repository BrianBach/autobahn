/**
 * 
 */
package net.geant2.jra3.reservation;

/**
 * @author Michal
 *
 */
public interface ServiceStatusListener {
	
	void scheduled(String serviceID);
	
	void failed(String serviceID);

}
