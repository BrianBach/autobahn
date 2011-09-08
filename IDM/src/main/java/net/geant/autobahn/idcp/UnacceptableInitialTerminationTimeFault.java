
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-08-30T10:58:32.394+02:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "UnacceptableInitialTerminationTimeFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class UnacceptableInitialTerminationTimeFault extends Exception {
    
    private org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault;

    public UnacceptableInitialTerminationTimeFault() {
        super();
    }
    
    public UnacceptableInitialTerminationTimeFault(String message) {
        super(message);
    }
    
    public UnacceptableInitialTerminationTimeFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptableInitialTerminationTimeFault(String message, org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault) {
        super(message);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public UnacceptableInitialTerminationTimeFault(String message, org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType unacceptableInitialTerminationTimeFault, Throwable cause) {
        super(message, cause);
        this.unacceptableInitialTerminationTimeFault = unacceptableInitialTerminationTimeFault;
    }

    public org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType getFaultInfo() {
        return this.unacceptableInitialTerminationTimeFault;
    }
}