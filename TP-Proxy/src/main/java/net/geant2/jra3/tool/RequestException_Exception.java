
package net.geant2.jra3.tool;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.9
 * Mon Feb 21 17:26:54 EET 2011
 * Generated source version: 2.2.9
 * 
 */

@WebFault(name = "RequestException", targetNamespace = "http://tool.jra3.geant2.net/")
public class RequestException_Exception extends Exception {
    public static final long serialVersionUID = 20110221172654L;
    
    private net.geant2.jra3.tool.RequestException requestException;

    public RequestException_Exception() {
        super();
    }
    
    public RequestException_Exception(String message) {
        super(message);
    }
    
    public RequestException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException_Exception(String message, net.geant2.jra3.tool.RequestException requestException) {
        super(message);
        this.requestException = requestException;
    }

    public RequestException_Exception(String message, net.geant2.jra3.tool.RequestException requestException, Throwable cause) {
        super(message, cause);
        this.requestException = requestException;
    }

    public net.geant2.jra3.tool.RequestException getFaultInfo() {
        return this.requestException;
    }
}
