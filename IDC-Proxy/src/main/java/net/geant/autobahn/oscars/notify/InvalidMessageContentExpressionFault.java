
/**
 * InvalidMessageContentExpressionFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant.autobahn.oscars.notify;

public class InvalidMessageContentExpressionFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault faultMessage;
    
    public InvalidMessageContentExpressionFault() {
        super("InvalidMessageContentExpressionFault");
    }
           
    public InvalidMessageContentExpressionFault(java.lang.String s) {
       super(s);
    }
    
    public InvalidMessageContentExpressionFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault getFaultMessage(){
       return faultMessage;
    }
}
    