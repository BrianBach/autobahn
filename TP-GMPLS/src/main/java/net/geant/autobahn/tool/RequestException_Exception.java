
package net.geant.autobahn.tool;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.5
 * Fri Jun 18 15:05:42 CEST 2010
 * Generated source version: 2.2.5
 * 
 */

@WebFault(name = "RequestException", targetNamespace = "http://tool.autobahn.geant.net/")
public class RequestException_Exception extends Exception {
    public static final long serialVersionUID = 20100618150542L;
    
    private net.geant.autobahn.tool.RequestException requestException;

    public RequestException_Exception() {
        super();
    }
    
    public RequestException_Exception(String message) {
        super(message);
    }
    
    public RequestException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException_Exception(String message, net.geant.autobahn.tool.RequestException requestException) {
        super(message);
        this.requestException = requestException;
    }

    public RequestException_Exception(String message, net.geant.autobahn.tool.RequestException requestException, Throwable cause) {
        super(message, cause);
        this.requestException = requestException;
    }

    public net.geant.autobahn.tool.RequestException getFaultInfo() {
        return this.requestException;
    }
}