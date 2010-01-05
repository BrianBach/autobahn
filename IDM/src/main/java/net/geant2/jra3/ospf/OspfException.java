package net.geant2.jra3.ospf;

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
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public OspfException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public OspfException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public OspfException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
