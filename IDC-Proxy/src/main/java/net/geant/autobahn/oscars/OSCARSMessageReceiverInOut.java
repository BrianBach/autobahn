
/**
 * OSCARSMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
        package net.geant.autobahn.oscars;

        /**
        *  OSCARSMessageReceiverInOut message receiver
        */

        public class OSCARSMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        OSCARSSkeleton skel = (OSCARSSkeleton)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)){

        

            if("cancelReservation".equals(methodName)){
                
                net.es.oscars.oscars.CancelReservationResponse cancelReservationResponse1 = null;
	                        net.es.oscars.oscars.CancelReservation wrappedParam =
                                                             (net.es.oscars.oscars.CancelReservation)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.CancelReservation.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               cancelReservationResponse1 =
                                                   
                                                   
                                                         skel.cancelReservation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), cancelReservationResponse1, false);
                                    } else 

            if("createReservation".equals(methodName)){
                
                net.es.oscars.oscars.CreateReservationResponse createReservationResponse3 = null;
	                        net.es.oscars.oscars.CreateReservation wrappedParam =
                                                             (net.es.oscars.oscars.CreateReservation)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.CreateReservation.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createReservationResponse3 =
                                                   
                                                   
                                                         skel.createReservation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createReservationResponse3, false);
                                    } else 

            if("queryReservation".equals(methodName)){
                
                net.es.oscars.oscars.QueryReservationResponse queryReservationResponse5 = null;
	                        net.es.oscars.oscars.QueryReservation wrappedParam =
                                                             (net.es.oscars.oscars.QueryReservation)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.QueryReservation.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               queryReservationResponse5 =
                                                   
                                                   
                                                         skel.queryReservation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), queryReservationResponse5, false);
                                    } else 

            if("refreshPath".equals(methodName)){
                
                net.es.oscars.oscars.RefreshPathResponse refreshPathResponse7 = null;
	                        net.es.oscars.oscars.RefreshPath wrappedParam =
                                                             (net.es.oscars.oscars.RefreshPath)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.RefreshPath.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               refreshPathResponse7 =
                                                   
                                                   
                                                         skel.refreshPath(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), refreshPathResponse7, false);
                                    } else 

            if("teardownPath".equals(methodName)){
                
                net.es.oscars.oscars.TeardownPathResponse teardownPathResponse9 = null;
	                        net.es.oscars.oscars.TeardownPath wrappedParam =
                                                             (net.es.oscars.oscars.TeardownPath)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.TeardownPath.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               teardownPathResponse9 =
                                                   
                                                   
                                                         skel.teardownPath(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), teardownPathResponse9, false);
                                    } else 

            if("createPath".equals(methodName)){
                
                net.es.oscars.oscars.CreatePathResponse createPathResponse11 = null;
	                        net.es.oscars.oscars.CreatePath wrappedParam =
                                                             (net.es.oscars.oscars.CreatePath)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.CreatePath.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createPathResponse11 =
                                                   
                                                   
                                                         skel.createPath(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createPathResponse11, false);
                                    } else 

            if("getNetworkTopology".equals(methodName)){
                
                net.es.oscars.oscars.GetNetworkTopologyResponse getNetworkTopologyResponse13 = null;
	                        net.es.oscars.oscars.GetNetworkTopology wrappedParam =
                                                             (net.es.oscars.oscars.GetNetworkTopology)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.GetNetworkTopology.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getNetworkTopologyResponse13 =
                                                   
                                                   
                                                         skel.getNetworkTopology(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getNetworkTopologyResponse13, false);
                                    } else 

            if("modifyReservation".equals(methodName)){
                
                net.es.oscars.oscars.ModifyReservationResponse modifyReservationResponse15 = null;
	                        net.es.oscars.oscars.ModifyReservation wrappedParam =
                                                             (net.es.oscars.oscars.ModifyReservation)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.ModifyReservation.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               modifyReservationResponse15 =
                                                   
                                                   
                                                         skel.modifyReservation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), modifyReservationResponse15, false);
                                    } else 

            if("forward".equals(methodName)){
                
                net.es.oscars.oscars.ForwardResponse forwardResponse17 = null;
	                        net.es.oscars.oscars.Forward wrappedParam =
                                                             (net.es.oscars.oscars.Forward)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.Forward.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               forwardResponse17 =
                                                   
                                                   
                                                         skel.forward(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), forwardResponse17, false);
                                    } else 

            if("listReservations".equals(methodName)){
                
                net.es.oscars.oscars.ListReservationsResponse listReservationsResponse19 = null;
	                        net.es.oscars.oscars.ListReservations wrappedParam =
                                                             (net.es.oscars.oscars.ListReservations)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.ListReservations.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               listReservationsResponse19 =
                                                   
                                                   
                                                         skel.listReservations(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), listReservationsResponse19, false);
                                    } else 

            if("initiateTopologyPull".equals(methodName)){
                
                net.es.oscars.oscars.InitiateTopologyPullResponse initiateTopologyPullResponse21 = null;
	                        net.es.oscars.oscars.InitiateTopologyPull wrappedParam =
                                                             (net.es.oscars.oscars.InitiateTopologyPull)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    net.es.oscars.oscars.InitiateTopologyPull.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               initiateTopologyPullResponse21 =
                                                   
                                                   
                                                         skel.initiateTopologyPull(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), initiateTopologyPullResponse21, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (BSSFaultMessage e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"BSSFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (AAAFaultMessage e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"AAAFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
        
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CancelReservation param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CancelReservation.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CancelReservationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CancelReservationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.BSSFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.BSSFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.AAAFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.AAAFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CreateReservation param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CreateReservation.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CreateReservationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CreateReservationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.QueryReservation param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.QueryReservation.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.QueryReservationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.QueryReservationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.RefreshPath param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.RefreshPath.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.RefreshPathResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.RefreshPathResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.TeardownPath param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.TeardownPath.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.TeardownPathResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.TeardownPathResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CreatePath param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CreatePath.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.CreatePathResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.CreatePathResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.GetNetworkTopology param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.GetNetworkTopology.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.GetNetworkTopologyResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.GetNetworkTopologyResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.ModifyReservation param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.ModifyReservation.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.ModifyReservationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.ModifyReservationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Notify param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Notify.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.Forward param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.Forward.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.ForwardResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.ForwardResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.ListReservations param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.ListReservations.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.ListReservationsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.ListReservationsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.InitiateTopologyPull param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.InitiateTopologyPull.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(net.es.oscars.oscars.InitiateTopologyPullResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(net.es.oscars.oscars.InitiateTopologyPullResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.CancelReservationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.CancelReservationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.CancelReservationResponse wrapcancelReservation(){
                                net.es.oscars.oscars.CancelReservationResponse wrappedElement = new net.es.oscars.oscars.CancelReservationResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.CreateReservationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.CreateReservationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.CreateReservationResponse wrapcreateReservation(){
                                net.es.oscars.oscars.CreateReservationResponse wrappedElement = new net.es.oscars.oscars.CreateReservationResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.QueryReservationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.QueryReservationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.QueryReservationResponse wrapqueryReservation(){
                                net.es.oscars.oscars.QueryReservationResponse wrappedElement = new net.es.oscars.oscars.QueryReservationResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.RefreshPathResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.RefreshPathResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.RefreshPathResponse wraprefreshPath(){
                                net.es.oscars.oscars.RefreshPathResponse wrappedElement = new net.es.oscars.oscars.RefreshPathResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.TeardownPathResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.TeardownPathResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.TeardownPathResponse wrapteardownPath(){
                                net.es.oscars.oscars.TeardownPathResponse wrappedElement = new net.es.oscars.oscars.TeardownPathResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.CreatePathResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.CreatePathResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.CreatePathResponse wrapcreatePath(){
                                net.es.oscars.oscars.CreatePathResponse wrappedElement = new net.es.oscars.oscars.CreatePathResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.GetNetworkTopologyResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.GetNetworkTopologyResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.GetNetworkTopologyResponse wrapgetNetworkTopology(){
                                net.es.oscars.oscars.GetNetworkTopologyResponse wrappedElement = new net.es.oscars.oscars.GetNetworkTopologyResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.ModifyReservationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.ModifyReservationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.ModifyReservationResponse wrapmodifyReservation(){
                                net.es.oscars.oscars.ModifyReservationResponse wrappedElement = new net.es.oscars.oscars.ModifyReservationResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.ForwardResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.ForwardResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.ForwardResponse wrapforward(){
                                net.es.oscars.oscars.ForwardResponse wrappedElement = new net.es.oscars.oscars.ForwardResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.ListReservationsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.ListReservationsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.ListReservationsResponse wraplistReservations(){
                                net.es.oscars.oscars.ListReservationsResponse wrappedElement = new net.es.oscars.oscars.ListReservationsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, net.es.oscars.oscars.InitiateTopologyPullResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(net.es.oscars.oscars.InitiateTopologyPullResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private net.es.oscars.oscars.InitiateTopologyPullResponse wrapinitiateTopologyPull(){
                                net.es.oscars.oscars.InitiateTopologyPullResponse wrappedElement = new net.es.oscars.oscars.InitiateTopologyPullResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (net.es.oscars.oscars.CancelReservation.class.equals(type)){
                
                           return net.es.oscars.oscars.CancelReservation.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.CancelReservationResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.CancelReservationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.CreateReservation.class.equals(type)){
                
                           return net.es.oscars.oscars.CreateReservation.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.CreateReservationResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.CreateReservationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.QueryReservation.class.equals(type)){
                
                           return net.es.oscars.oscars.QueryReservation.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.QueryReservationResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.QueryReservationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.RefreshPath.class.equals(type)){
                
                           return net.es.oscars.oscars.RefreshPath.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.RefreshPathResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.RefreshPathResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.TeardownPath.class.equals(type)){
                
                           return net.es.oscars.oscars.TeardownPath.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.TeardownPathResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.TeardownPathResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.CreatePath.class.equals(type)){
                
                           return net.es.oscars.oscars.CreatePath.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.CreatePathResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.CreatePathResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.GetNetworkTopology.class.equals(type)){
                
                           return net.es.oscars.oscars.GetNetworkTopology.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.GetNetworkTopologyResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.GetNetworkTopologyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.ModifyReservation.class.equals(type)){
                
                           return net.es.oscars.oscars.ModifyReservation.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.ModifyReservationResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.ModifyReservationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Notify.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Notify.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.Forward.class.equals(type)){
                
                           return net.es.oscars.oscars.Forward.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.ForwardResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.ForwardResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.ListReservations.class.equals(type)){
                
                           return net.es.oscars.oscars.ListReservations.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.ListReservationsResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.ListReservationsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.InitiateTopologyPull.class.equals(type)){
                
                           return net.es.oscars.oscars.InitiateTopologyPull.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.InitiateTopologyPullResponse.class.equals(type)){
                
                           return net.es.oscars.oscars.InitiateTopologyPullResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.BSSFault.class.equals(type)){
                
                           return net.es.oscars.oscars.BSSFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    