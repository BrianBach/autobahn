/*
 * FailureRecovery.java
 *
 * 2006-12-08
 */
package net.geant2.jra3.reservation;

import java.util.List;

import net.geant2.jra3.dao.IdmDAOFactory;
import net.geant2.jra3.dao.hibernate.HibernateIdmDAOFactory;
import net.geant2.jra3.reservation.dao.ReservationDAO;

/**
 * Class responsible for recovering running reservations after an application
 * failure.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class FailureRecovery {

    private IdmDAOFactory daos = HibernateIdmDAOFactory.getInstance();
    
    /**
     * Retrieves running reservations from database, performs recovering
     * operations then a list containing ready to execute recovered reservations
     * is returned.
     * 
     * @return <code>List<Reservation></code> list of reservations that are
     *         still running
     */
    public List<Reservation> recoverRunningReservations() {
        ReservationDAO rdao = daos.getReservationDAO();
        
        List<Reservation> reservations = rdao.getRunningReservations();
        
        for(Reservation res : reservations) {
        	AutobahnReservation tmp = (AutobahnReservation) res; 
            tmp.recover();
        }
        
        return reservations;
    }
}
