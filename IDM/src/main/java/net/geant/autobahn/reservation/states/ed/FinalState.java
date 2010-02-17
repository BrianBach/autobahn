/*
 * FinalState.java
 *
 * 2006-12-08
 */
package net.geant.autobahn.reservation.states.ed;

import net.geant.autobahn.reservation.ExternalReservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class FinalState extends ExternalDomainState {

    /**
     * @param code
     * @param label
     */
    public FinalState(int code, String label) {
        super(code, label);
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.reservation.states.State#recover()
     */
    @Override
    public void recover(ExternalReservation res) {
        // TODO Auto-generated method stub

    }

}
