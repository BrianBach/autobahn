package net.geant.autobahn.intradomain;

import net.geant.autobahn.reservation.ReservationParams;

/**
 * Class representing intradomain reservation. Stores the intradomain path to be
 * used by the reservation, as well as reservation parameters. Stores also
 * information whether the reservation has been submitted to the domain's TP.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class IntradomainReservation {

	private String reservationId;
	
	private IntradomainPath reservedPath;
	private ReservationParams params;
	private boolean pathCreated;
	private boolean active;
	
	/**
	 * Default constructor
	 */
	public IntradomainReservation() {
		
	}
	
	/**
	 * Creates intradomain reservation.
	 * 
	 * @param reservationId String identifier of the reservation
	 * @param reservedPath Path to be used
	 * @param params Reservation params
	 */
	public IntradomainReservation(String reservationId, IntradomainPath reservedPath,
			ReservationParams params) {
		
		this.reservationId = reservationId;
		this.reservedPath = reservedPath;
		this.params = params;
		this.pathCreated = false;
		this.active = false;
	}

	/**
	 * @return the reservation identifier
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * Sets reservation identifiers
	 * 
	 * @param reservationId the reservationId to set
	 */
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	/**
	 * @return the reservedPath
	 */
	public IntradomainPath getReservedPath() {
		return reservedPath;
	}

	/**
	 * Sets the intradomain path that has been chosen for this reservation
	 * 
	 * @param reservedPath the reservedPath to set
	 */
	public void setReservedPath(IntradomainPath reservedPath) {
		this.reservedPath = reservedPath;
	}

	/**
	 * @return Reservation parameters
	 */
	public ReservationParams getParams() {
		return params;
	}

	/**
	 * Sets reservation parameters
	 * 
	 * @param Paramaters to set 
	 */
	public void setParams(ReservationParams params) {
		this.params = params;
	}

	/**
	 * @return boolean Whether the path has been created in the TP
	 */
	public boolean isPathCreated() {
		return pathCreated;
	}

	/**
	 * Sets whether the path has been created in the TP
	 * 
	 * @param pathCreated 
	 */
	public void setPathCreated(boolean pathCreated) {
		this.pathCreated = pathCreated;
	}

	/**
	 * @return Whether the reservation is active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active Sets the active flag
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
