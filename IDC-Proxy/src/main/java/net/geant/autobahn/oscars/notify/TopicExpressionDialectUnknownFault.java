
/**
 * TopicExpressionDialectUnknownFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant.autobahn.oscars.notify;

public class TopicExpressionDialectUnknownFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault faultMessage;
    
    public TopicExpressionDialectUnknownFault() {
        super("TopicExpressionDialectUnknownFault");
    }
           
    public TopicExpressionDialectUnknownFault(java.lang.String s) {
       super(s);
    }
    
    public TopicExpressionDialectUnknownFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault getFaultMessage(){
       return faultMessage;
    }
}
    