/*
 * State.java
 *
 * 2006-11-14
 */
package net.geant.autobahn.reservation.states;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public abstract class State {

    protected Logger log = Logger.getLogger(State.class);
    
    private int code;
    private String label;
    
    /**
     * 
     */
    public State(int code, String label) {
        super();
        this.label = label;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        
        if(obj == this)
            return true;
        
        if (!(obj instanceof State)) {
            return false;
        }
        
        State state2 = (State) obj;
        
        return this.getCode() == state2.getCode();
    }

    @Override
    public int hashCode() {
        return getCode();
    }

    @Override
    public String toString() {
        return label;
    }
}
