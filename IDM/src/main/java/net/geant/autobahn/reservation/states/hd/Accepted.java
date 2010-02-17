/*
 * Accepted.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation.states.hd;

import net.geant.autobahn.reservation.HomeDomainReservation;

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
