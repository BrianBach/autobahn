/*
 * FinalState.java
 *
 * 2006-12-08
 */
package net.geant2.jra3.reservation.states.ld;

import net.geant2.jra3.reservation.LastDomainReservation;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class FinalState extends LastDomainState {

    /**
     * @param code
     * @param label
     */
    public FinalState(int code, String label) {
        super(code, label);
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.reservation.states.State#recover()
     */
    @Override
    public void recover(LastDomainReservation res) {
        // we don't need to recover anything as it's a final state
    }
}
