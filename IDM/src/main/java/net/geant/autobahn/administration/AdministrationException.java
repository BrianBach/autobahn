package net.geant.autobahn.administration;

public class AdministrationException extends Exception {

    private static final long serialVersionUID = -1428420631734498743L;

    public AdministrationException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public AdministrationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public AdministrationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public AdministrationException(Throwable cause) {
        super(cause);
    }
}
