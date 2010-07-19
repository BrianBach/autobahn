
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.6
 * Fri Jul 16 16:33:14 CEST 2010
 * Generated source version: 2.2.6
 * 
 */

@WebFault(name = "AAAFault", targetNamespace = "http://oscars.es.net/OSCARS")
public class AAAFaultMessage extends Exception {
    public static final long serialVersionUID = 20100716163314L;
    
    private net.geant.autobahn.idcp.AAAFault aaaFault;

    public AAAFaultMessage() {
        super();
    }
    
    public AAAFaultMessage(String message) {
        super(message);
    }
    
    public AAAFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public AAAFaultMessage(String message, net.geant.autobahn.idcp.AAAFault aaaFault) {
        super(message);
        this.aaaFault = aaaFault;
    }

    public AAAFaultMessage(String message, net.geant.autobahn.idcp.AAAFault aaaFault, Throwable cause) {
        super(message, cause);
        this.aaaFault = aaaFault;
    }

    public net.geant.autobahn.idcp.AAAFault getFaultInfo() {
        return this.aaaFault;
    }
}
