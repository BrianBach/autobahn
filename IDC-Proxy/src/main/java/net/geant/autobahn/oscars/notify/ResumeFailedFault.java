
/**
 * ResumeFailedFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant.autobahn.oscars.notify;

public class ResumeFailedFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.b_2.ResumeFailedFault faultMessage;
    
    public ResumeFailedFault() {
        super("ResumeFailedFault");
    }
           
    public ResumeFailedFault(java.lang.String s) {
       super(s);
    }
    
    public ResumeFailedFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.b_2.ResumeFailedFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.b_2.ResumeFailedFault getFaultMessage(){
       return faultMessage;
    }
}
    