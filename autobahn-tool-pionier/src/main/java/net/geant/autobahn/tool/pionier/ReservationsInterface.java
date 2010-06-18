/**
 * 
 */
package net.geant.autobahn.tool.pionier;

/**
 * @author Michal
 *
 */
public interface ReservationsInterface {
	
	boolean addReservation(ReservationParameters parameters);

    boolean remReservation(ReservationParameters parameters);

}
