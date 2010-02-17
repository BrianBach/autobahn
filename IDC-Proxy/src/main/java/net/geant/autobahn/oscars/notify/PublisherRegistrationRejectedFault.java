
/**
 * PublisherRegistrationRejectedFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant.autobahn.oscars.notify;

public class PublisherRegistrationRejectedFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault faultMessage;
    
    public PublisherRegistrationRejectedFault() {
        super("PublisherRegistrationRejectedFault");
    }
           
    public PublisherRegistrationRejectedFault(java.lang.String s) {
       super(s);
    }
    
    public PublisherRegistrationRejectedFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault getFaultMessage(){
       return faultMessage;
    }
}
    