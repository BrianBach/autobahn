
/**
 * PublisherRegistrationFailedFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant.autobahn.oscars.notify;

public class PublisherRegistrationFailedFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault faultMessage;
    
    public PublisherRegistrationFailedFault() {
        super("PublisherRegistrationFailedFault");
    }
           
    public PublisherRegistrationFailedFault(java.lang.String s) {
       super(s);
    }
    
    public PublisherRegistrationFailedFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault getFaultMessage(){
       return faultMessage;
    }
}
    