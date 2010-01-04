
/**
 * ResourceNotDestroyedFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant2.jra3.oscars.notify;

public class ResourceNotDestroyedFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault faultMessage;
    
    public ResourceNotDestroyedFault() {
        super("ResourceNotDestroyedFault");
    }
           
    public ResourceNotDestroyedFault(java.lang.String s) {
       super(s);
    }
    
    public ResourceNotDestroyedFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault getFaultMessage(){
       return faultMessage;
    }
}
    