
package net.es.oscars.oscars;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebFault(name = "PublisherRegistrationRejectedFault", targetNamespace = "http://docs.oasis-open.org/wsn/br-2")

public class PublisherRegistrationRejectedFault extends Exception {
    public static final long serialVersionUID = 20100212154406L;
    
    private org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType publisherRegistrationRejectedFault;

    public PublisherRegistrationRejectedFault() {
        super();
    }
    
    public PublisherRegistrationRejectedFault(String message) {
        super(message);
    }
    
    public PublisherRegistrationRejectedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PublisherRegistrationRejectedFault(String message, org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType publisherRegistrationRejectedFault) {
        super(message);
        this.publisherRegistrationRejectedFault = publisherRegistrationRejectedFault;
    }

    public PublisherRegistrationRejectedFault(String message, org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType publisherRegistrationRejectedFault, Throwable cause) {
        super(message, cause);
        this.publisherRegistrationRejectedFault = publisherRegistrationRejectedFault;
    }

    public org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType getFaultInfo() {
        return this.publisherRegistrationRejectedFault;
    }
}