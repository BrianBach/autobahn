/*
 * StateObject.java
 * 2006-02-15
 */
package net.geant2.jra3.reservation;

/**
 * 
 * Describes reservation or service object's state
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 *
 */
public interface StateObject {
        
    /**
     * Returns value describing object state
     * @return AbstractState value with current object state
     */
    int getState();
}
