
/**
 * OSCARSNotifyMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
        package net.geant.autobahn.oscars.notify;

        /**
        *  OSCARSNotifyMessageReceiverInOut message receiver
        */

        public class OSCARSNotifyMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        OSCARSNotifySkeleton skel = (OSCARSNotifySkeleton)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)){

        

            if("Subscribe".equals(methodName)){
                
                org.oasis_open.docs.wsn.b_2.SubscribeResponse subscribeResponse1 = null;
	                        org.oasis_open.docs.wsn.b_2.Subscribe wrappedParam =
                                                             (org.oasis_open.docs.wsn.b_2.Subscribe)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.b_2.Subscribe.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               subscribeResponse1 =
                                                   
                                                   
                                                         skel.Subscribe(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), subscribeResponse1, false);
                                    } else 

            if("ResumeSubscription".equals(methodName)){
                
                org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse resumeSubscriptionResponse3 = null;
	                        org.oasis_open.docs.wsn.b_2.ResumeSubscription wrappedParam =
                                                             (org.oasis_open.docs.wsn.b_2.ResumeSubscription)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.b_2.ResumeSubscription.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resumeSubscriptionResponse3 =
                                                   
                                                   
                                                         skel.ResumeSubscription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resumeSubscriptionResponse3, false);
                                    } else 

            if("RegisterPublisher".equals(methodName)){
                
                org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisherResponse5 = null;
	                        org.oasis_open.docs.wsn.br_2.RegisterPublisher wrappedParam =
                                                             (org.oasis_open.docs.wsn.br_2.RegisterPublisher)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.br_2.RegisterPublisher.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               registerPublisherResponse5 =
                                                   
                                                   
                                                         skel.RegisterPublisher(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), registerPublisherResponse5, false);
                                    } else 

            if("Unsubscribe".equals(methodName)){
                
                org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribeResponse7 = null;
	                        org.oasis_open.docs.wsn.b_2.Unsubscribe wrappedParam =
                                                             (org.oasis_open.docs.wsn.b_2.Unsubscribe)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.b_2.Unsubscribe.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unsubscribeResponse7 =
                                                   
                                                   
                                                         skel.Unsubscribe(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unsubscribeResponse7, false);
                                    } else 

            if("PauseSubscription".equals(methodName)){
                
                org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse pauseSubscriptionResponse9 = null;
	                        org.oasis_open.docs.wsn.b_2.PauseSubscription wrappedParam =
                                                             (org.oasis_open.docs.wsn.b_2.PauseSubscription)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.b_2.PauseSubscription.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               pauseSubscriptionResponse9 =
                                                   
                                                   
                                                         skel.PauseSubscription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), pauseSubscriptionResponse9, false);
                                    } else 

            if("Renew".equals(methodName)){
                
                org.oasis_open.docs.wsn.b_2.RenewResponse renewResponse11 = null;
	                        org.oasis_open.docs.wsn.b_2.Renew wrappedParam =
                                                             (org.oasis_open.docs.wsn.b_2.Renew)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.b_2.Renew.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               renewResponse11 =
                                                   
                                                   
                                                         skel.Renew(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), renewResponse11, false);
                                    } else 

            if("DestroyRegistration".equals(methodName)){
                
                org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse destroyRegistrationResponse13 = null;
	                        org.oasis_open.docs.wsn.br_2.DestroyRegistration wrappedParam =
                                                             (org.oasis_open.docs.wsn.br_2.DestroyRegistration)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    org.oasis_open.docs.wsn.br_2.DestroyRegistration.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               destroyRegistrationResponse13 =
                                                   
                                                   
                                                         skel.DestroyRegistration(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), destroyRegistrationResponse13, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (TopicExpressionDialectUnknownFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"TopicExpressionDialectUnknownFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (NotifyMessageNotSupportedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"NotifyMessageNotSupportedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ResourceNotDestroyedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ResourceNotDestroyedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (PublisherRegistrationRejectedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PublisherRegistrationRejectedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ResumeFailedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ResumeFailedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidProducerPropertiesExpressionFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidProducerPropertiesExpressionFault");
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
         catch (PauseFailedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PauseFailedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (PublisherRegistrationFailedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"PublisherRegistrationFailedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidFilterFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidFilterFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (TopicNotSupportedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"TopicNotSupportedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UnacceptableInitialTerminationTimeFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UnacceptableInitialTerminationTimeFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (SubscribeCreationFailedFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"SubscribeCreationFailedFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidMessageContentExpressionFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidMessageContentExpressionFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (InvalidTopicExpressionFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"InvalidTopicExpressionFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UnableToDestroySubscriptionFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UnableToDestroySubscriptionFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (ResourceUnknownFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"ResourceUnknownFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UnrecognizedPolicyRequestFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UnrecognizedPolicyRequestFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UnsupportedPolicyRequestFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UnsupportedPolicyRequestFault");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
         catch (UnacceptableTerminationTimeFault e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"UnacceptableTerminationTimeFault");
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
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Subscribe param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Subscribe.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.SubscribeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.SubscribeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidFilterFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidFilterFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.MY_QNAME,
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
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.ResumeFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.RegisterPublisher param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisher.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Unsubscribe param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Unsubscribe.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnsubscribeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.PauseFailedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseFailedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.Renew param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.Renew.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.RenewResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.RenewResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.MY_QNAME,
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
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.DestroyRegistration param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistration.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.SubscribeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.SubscribeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.b_2.SubscribeResponse wrapSubscribe(){
                                org.oasis_open.docs.wsn.b_2.SubscribeResponse wrappedElement = new org.oasis_open.docs.wsn.b_2.SubscribeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse wrapResumeSubscription(){
                                org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse wrappedElement = new org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse wrapRegisterPublisher(){
                                org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse wrappedElement = new org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.UnsubscribeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.b_2.UnsubscribeResponse wrapUnsubscribe(){
                                org.oasis_open.docs.wsn.b_2.UnsubscribeResponse wrappedElement = new org.oasis_open.docs.wsn.b_2.UnsubscribeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse wrapPauseSubscription(){
                                org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse wrappedElement = new org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.b_2.RenewResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.b_2.RenewResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.b_2.RenewResponse wrapRenew(){
                                org.oasis_open.docs.wsn.b_2.RenewResponse wrappedElement = new org.oasis_open.docs.wsn.b_2.RenewResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse wrapDestroyRegistration(){
                                org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse wrappedElement = new org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse();
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
        
                if (org.oasis_open.docs.wsn.b_2.Subscribe.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Subscribe.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.SubscribeResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.SubscribeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidFilterFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidFilterFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeSubscription.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.ResumeFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.ResumeFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.RegisterPublisher.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.RegisterPublisher.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.TopicNotSupportedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Unsubscribe.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Unsubscribe.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnsubscribeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseSubscription.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.PauseFailedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.PauseFailedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Renew.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Renew.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.RenewResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.RenewResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (net.es.oscars.oscars.AAAFault.class.equals(type)){
                
                           return net.es.oscars.oscars.AAAFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.b_2.Notify.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.b_2.Notify.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.DestroyRegistration.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.DestroyRegistration.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsrf.r_2.ResourceUnknownFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.class.equals(type)){
                
                           return org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    