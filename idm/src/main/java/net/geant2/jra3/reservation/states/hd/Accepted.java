/*
 * Accepted.java
 *
 * 2006-11-14
 */
package net.geant2.jra3.reservation.states.hd;

import net.geant2.jra3.reservation.HomeDomainReservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class Accepted extends HomeDomainState {

    public Accepted(int code, String label) {
        super(code, label);
    }

    @Override
    public void run(HomeDomainReservation res) {
        super.run(res);
        
        res.switchState(HomeDomainState.PATHFINDING);
    }
}
