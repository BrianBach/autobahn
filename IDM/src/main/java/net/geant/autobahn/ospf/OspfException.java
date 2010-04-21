package net.geant.autobahn.ospf;

/**
 * Exception that is thrown when some problems with OSPF API occurs 
 *
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

public class OspfException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public OspfException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public OspfException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public OspfException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public OspfException(Throwable cause) {
        super(cause);
    }
}
