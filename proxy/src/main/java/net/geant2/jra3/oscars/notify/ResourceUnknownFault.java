
/**
 * ResourceUnknownFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant2.jra3.oscars.notify;

public class ResourceUnknownFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault faultMessage;
    
    public ResourceUnknownFault() {
        super("ResourceUnknownFault");
    }
           
    public ResourceUnknownFault(java.lang.String s) {
       super(s);
    }
    
    public ResourceUnknownFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault getFaultMessage(){
       return faultMessage;
    }
}
    