
/**
 * MsgDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package net.es.oscars.oscars;
            

            /**
            *  MsgDetails bean class
            */
        
        public  class MsgDetails
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = msgDetails
                Namespace URI = http://oscars.es.net/OSCARS
                Namespace Prefix = ns2
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://oscars.es.net/OSCARS")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for ContentType
                        */

                        
                                    protected java.lang.String localContentType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getContentType(){
                               return localContentType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContentType
                               */
                               public void setContentType(java.lang.String param){
                            
                                            this.localContentType=param;
                                    

                               }
                            

                        /**
                        * field for Forward
                        */

                        
                                    protected net.es.oscars.oscars.ForwardPayload localForward ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localForwardTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ForwardPayload
                           */
                           public  net.es.oscars.oscars.ForwardPayload getForward(){
                               return localForward;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Forward
                               */
                               public void setForward(net.es.oscars.oscars.ForwardPayload param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localForwardTracker = true;
                                       } else {
                                          localForwardTracker = false;
                                              
                                       }
                                   
                                            this.localForward=param;
                                    

                               }
                            

                        /**
                        * field for CreateReservation
                        */

                        
                                    protected net.es.oscars.oscars.ResCreateContent localCreateReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreateReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ResCreateContent
                           */
                           public  net.es.oscars.oscars.ResCreateContent getCreateReservation(){
                               return localCreateReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreateReservation
                               */
                               public void setCreateReservation(net.es.oscars.oscars.ResCreateContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCreateReservationTracker = true;
                                       } else {
                                          localCreateReservationTracker = false;
                                              
                                       }
                                   
                                            this.localCreateReservation=param;
                                    

                               }
                            

                        /**
                        * field for ModifyReservation
                        */

                        
                                    protected net.es.oscars.oscars.ModifyResContent localModifyReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ModifyResContent
                           */
                           public  net.es.oscars.oscars.ModifyResContent getModifyReservation(){
                               return localModifyReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyReservation
                               */
                               public void setModifyReservation(net.es.oscars.oscars.ModifyResContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localModifyReservationTracker = true;
                                       } else {
                                          localModifyReservationTracker = false;
                                              
                                       }
                                   
                                            this.localModifyReservation=param;
                                    

                               }
                            

                        /**
                        * field for CancelReservation
                        */

                        
                                    protected net.es.oscars.oscars.GlobalReservationId localCancelReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCancelReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.GlobalReservationId
                           */
                           public  net.es.oscars.oscars.GlobalReservationId getCancelReservation(){
                               return localCancelReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CancelReservation
                               */
                               public void setCancelReservation(net.es.oscars.oscars.GlobalReservationId param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCancelReservationTracker = true;
                                       } else {
                                          localCancelReservationTracker = false;
                                              
                                       }
                                   
                                            this.localCancelReservation=param;
                                    

                               }
                            

                        /**
                        * field for QueryReservation
                        */

                        
                                    protected net.es.oscars.oscars.GlobalReservationId localQueryReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localQueryReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.GlobalReservationId
                           */
                           public  net.es.oscars.oscars.GlobalReservationId getQueryReservation(){
                               return localQueryReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param QueryReservation
                               */
                               public void setQueryReservation(net.es.oscars.oscars.GlobalReservationId param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localQueryReservationTracker = true;
                                       } else {
                                          localQueryReservationTracker = false;
                                              
                                       }
                                   
                                            this.localQueryReservation=param;
                                    

                               }
                            

                        /**
                        * field for ListReservations
                        */

                        
                                    protected net.es.oscars.oscars.ListRequest localListReservations ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListReservationsTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ListRequest
                           */
                           public  net.es.oscars.oscars.ListRequest getListReservations(){
                               return localListReservations;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListReservations
                               */
                               public void setListReservations(net.es.oscars.oscars.ListRequest param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localListReservationsTracker = true;
                                       } else {
                                          localListReservationsTracker = false;
                                              
                                       }
                                   
                                            this.localListReservations=param;
                                    

                               }
                            

                        /**
                        * field for CreatePath
                        */

                        
                                    protected net.es.oscars.oscars.CreatePathContent localCreatePath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatePathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.CreatePathContent
                           */
                           public  net.es.oscars.oscars.CreatePathContent getCreatePath(){
                               return localCreatePath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreatePath
                               */
                               public void setCreatePath(net.es.oscars.oscars.CreatePathContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCreatePathTracker = true;
                                       } else {
                                          localCreatePathTracker = false;
                                              
                                       }
                                   
                                            this.localCreatePath=param;
                                    

                               }
                            

                        /**
                        * field for RefreshPath
                        */

                        
                                    protected net.es.oscars.oscars.RefreshPathContent localRefreshPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRefreshPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.RefreshPathContent
                           */
                           public  net.es.oscars.oscars.RefreshPathContent getRefreshPath(){
                               return localRefreshPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RefreshPath
                               */
                               public void setRefreshPath(net.es.oscars.oscars.RefreshPathContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localRefreshPathTracker = true;
                                       } else {
                                          localRefreshPathTracker = false;
                                              
                                       }
                                   
                                            this.localRefreshPath=param;
                                    

                               }
                            

                        /**
                        * field for TeardownPath
                        */

                        
                                    protected net.es.oscars.oscars.TeardownPathContent localTeardownPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTeardownPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.TeardownPathContent
                           */
                           public  net.es.oscars.oscars.TeardownPathContent getTeardownPath(){
                               return localTeardownPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TeardownPath
                               */
                               public void setTeardownPath(net.es.oscars.oscars.TeardownPathContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTeardownPathTracker = true;
                                       } else {
                                          localTeardownPathTracker = false;
                                              
                                       }
                                   
                                            this.localTeardownPath=param;
                                    

                               }
                            

                        /**
                        * field for ForwardResponse
                        */

                        
                                    protected net.es.oscars.oscars.ForwardReply localForwardResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localForwardResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ForwardReply
                           */
                           public  net.es.oscars.oscars.ForwardReply getForwardResponse(){
                               return localForwardResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ForwardResponse
                               */
                               public void setForwardResponse(net.es.oscars.oscars.ForwardReply param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localForwardResponseTracker = true;
                                       } else {
                                          localForwardResponseTracker = false;
                                              
                                       }
                                   
                                            this.localForwardResponse=param;
                                    

                               }
                            

                        /**
                        * field for CreateReservationResponse
                        */

                        
                                    protected net.es.oscars.oscars.CreateReply localCreateReservationResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreateReservationResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.CreateReply
                           */
                           public  net.es.oscars.oscars.CreateReply getCreateReservationResponse(){
                               return localCreateReservationResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreateReservationResponse
                               */
                               public void setCreateReservationResponse(net.es.oscars.oscars.CreateReply param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCreateReservationResponseTracker = true;
                                       } else {
                                          localCreateReservationResponseTracker = false;
                                              
                                       }
                                   
                                            this.localCreateReservationResponse=param;
                                    

                               }
                            

                        /**
                        * field for ModifyReservationResponse
                        */

                        
                                    protected net.es.oscars.oscars.ModifyResReply localModifyReservationResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyReservationResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ModifyResReply
                           */
                           public  net.es.oscars.oscars.ModifyResReply getModifyReservationResponse(){
                               return localModifyReservationResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyReservationResponse
                               */
                               public void setModifyReservationResponse(net.es.oscars.oscars.ModifyResReply param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localModifyReservationResponseTracker = true;
                                       } else {
                                          localModifyReservationResponseTracker = false;
                                              
                                       }
                                   
                                            this.localModifyReservationResponse=param;
                                    

                               }
                            

                        /**
                        * field for CancelReservationResponse
                        */

                        
                                    protected java.lang.String localCancelReservationResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCancelReservationResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCancelReservationResponse(){
                               return localCancelReservationResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CancelReservationResponse
                               */
                               public void setCancelReservationResponse(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCancelReservationResponseTracker = true;
                                       } else {
                                          localCancelReservationResponseTracker = false;
                                              
                                       }
                                   
                                            this.localCancelReservationResponse=param;
                                    

                               }
                            

                        /**
                        * field for QueryReservationResponse
                        */

                        
                                    protected net.es.oscars.oscars.ResDetails localQueryReservationResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localQueryReservationResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ResDetails
                           */
                           public  net.es.oscars.oscars.ResDetails getQueryReservationResponse(){
                               return localQueryReservationResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param QueryReservationResponse
                               */
                               public void setQueryReservationResponse(net.es.oscars.oscars.ResDetails param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localQueryReservationResponseTracker = true;
                                       } else {
                                          localQueryReservationResponseTracker = false;
                                              
                                       }
                                   
                                            this.localQueryReservationResponse=param;
                                    

                               }
                            

                        /**
                        * field for ListReservationsResponse
                        */

                        
                                    protected net.es.oscars.oscars.ListReply localListReservationsResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListReservationsResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ListReply
                           */
                           public  net.es.oscars.oscars.ListReply getListReservationsResponse(){
                               return localListReservationsResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListReservationsResponse
                               */
                               public void setListReservationsResponse(net.es.oscars.oscars.ListReply param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localListReservationsResponseTracker = true;
                                       } else {
                                          localListReservationsResponseTracker = false;
                                              
                                       }
                                   
                                            this.localListReservationsResponse=param;
                                    

                               }
                            

                        /**
                        * field for CreatePathResponse
                        */

                        
                                    protected net.es.oscars.oscars.CreatePathResponseContent localCreatePathResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatePathResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.CreatePathResponseContent
                           */
                           public  net.es.oscars.oscars.CreatePathResponseContent getCreatePathResponse(){
                               return localCreatePathResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreatePathResponse
                               */
                               public void setCreatePathResponse(net.es.oscars.oscars.CreatePathResponseContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCreatePathResponseTracker = true;
                                       } else {
                                          localCreatePathResponseTracker = false;
                                              
                                       }
                                   
                                            this.localCreatePathResponse=param;
                                    

                               }
                            

                        /**
                        * field for RefreshPathResponse
                        */

                        
                                    protected net.es.oscars.oscars.RefreshPathResponseContent localRefreshPathResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRefreshPathResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.RefreshPathResponseContent
                           */
                           public  net.es.oscars.oscars.RefreshPathResponseContent getRefreshPathResponse(){
                               return localRefreshPathResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RefreshPathResponse
                               */
                               public void setRefreshPathResponse(net.es.oscars.oscars.RefreshPathResponseContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localRefreshPathResponseTracker = true;
                                       } else {
                                          localRefreshPathResponseTracker = false;
                                              
                                       }
                                   
                                            this.localRefreshPathResponse=param;
                                    

                               }
                            

                        /**
                        * field for TeardownPathResponse
                        */

                        
                                    protected net.es.oscars.oscars.TeardownPathResponseContent localTeardownPathResponse ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTeardownPathResponseTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.TeardownPathResponseContent
                           */
                           public  net.es.oscars.oscars.TeardownPathResponseContent getTeardownPathResponse(){
                               return localTeardownPathResponse;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TeardownPathResponse
                               */
                               public void setTeardownPathResponse(net.es.oscars.oscars.TeardownPathResponseContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTeardownPathResponseTracker = true;
                                       } else {
                                          localTeardownPathResponseTracker = false;
                                              
                                       }
                                   
                                            this.localTeardownPathResponse=param;
                                    

                               }
                            

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
        
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       MsgDetails.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);
            
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://oscars.es.net/OSCARS");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":msgDetails",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "msgDetails",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://oscars.es.net/OSCARS";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"contentType", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"contentType");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("contentType");
                                    }
                                

                                          if (localContentType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("contentType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localContentType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localForwardTracker){
                                            if (localForward==null){
                                                 throw new org.apache.axis2.databinding.ADBException("forward cannot be null!!");
                                            }
                                           localForward.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","forward"),
                                               factory,xmlWriter);
                                        } if (localCreateReservationTracker){
                                            if (localCreateReservation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("createReservation cannot be null!!");
                                            }
                                           localCreateReservation.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createReservation"),
                                               factory,xmlWriter);
                                        } if (localModifyReservationTracker){
                                            if (localModifyReservation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("modifyReservation cannot be null!!");
                                            }
                                           localModifyReservation.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","modifyReservation"),
                                               factory,xmlWriter);
                                        } if (localCancelReservationTracker){
                                            if (localCancelReservation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("cancelReservation cannot be null!!");
                                            }
                                           localCancelReservation.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","cancelReservation"),
                                               factory,xmlWriter);
                                        } if (localQueryReservationTracker){
                                            if (localQueryReservation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("queryReservation cannot be null!!");
                                            }
                                           localQueryReservation.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","queryReservation"),
                                               factory,xmlWriter);
                                        } if (localListReservationsTracker){
                                            if (localListReservations==null){
                                                 throw new org.apache.axis2.databinding.ADBException("listReservations cannot be null!!");
                                            }
                                           localListReservations.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","listReservations"),
                                               factory,xmlWriter);
                                        } if (localCreatePathTracker){
                                            if (localCreatePath==null){
                                                 throw new org.apache.axis2.databinding.ADBException("createPath cannot be null!!");
                                            }
                                           localCreatePath.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createPath"),
                                               factory,xmlWriter);
                                        } if (localRefreshPathTracker){
                                            if (localRefreshPath==null){
                                                 throw new org.apache.axis2.databinding.ADBException("refreshPath cannot be null!!");
                                            }
                                           localRefreshPath.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","refreshPath"),
                                               factory,xmlWriter);
                                        } if (localTeardownPathTracker){
                                            if (localTeardownPath==null){
                                                 throw new org.apache.axis2.databinding.ADBException("teardownPath cannot be null!!");
                                            }
                                           localTeardownPath.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","teardownPath"),
                                               factory,xmlWriter);
                                        } if (localForwardResponseTracker){
                                            if (localForwardResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("forwardResponse cannot be null!!");
                                            }
                                           localForwardResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","forwardResponse"),
                                               factory,xmlWriter);
                                        } if (localCreateReservationResponseTracker){
                                            if (localCreateReservationResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("createReservationResponse cannot be null!!");
                                            }
                                           localCreateReservationResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createReservationResponse"),
                                               factory,xmlWriter);
                                        } if (localModifyReservationResponseTracker){
                                            if (localModifyReservationResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("modifyReservationResponse cannot be null!!");
                                            }
                                           localModifyReservationResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","modifyReservationResponse"),
                                               factory,xmlWriter);
                                        } if (localCancelReservationResponseTracker){
                                    namespace = "http://oscars.es.net/OSCARS";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"cancelReservationResponse", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"cancelReservationResponse");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("cancelReservationResponse");
                                    }
                                

                                          if (localCancelReservationResponse==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cancelReservationResponse cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCancelReservationResponse);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localQueryReservationResponseTracker){
                                            if (localQueryReservationResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("queryReservationResponse cannot be null!!");
                                            }
                                           localQueryReservationResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","queryReservationResponse"),
                                               factory,xmlWriter);
                                        } if (localListReservationsResponseTracker){
                                            if (localListReservationsResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("listReservationsResponse cannot be null!!");
                                            }
                                           localListReservationsResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","listReservationsResponse"),
                                               factory,xmlWriter);
                                        } if (localCreatePathResponseTracker){
                                            if (localCreatePathResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("createPathResponse cannot be null!!");
                                            }
                                           localCreatePathResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createPathResponse"),
                                               factory,xmlWriter);
                                        } if (localRefreshPathResponseTracker){
                                            if (localRefreshPathResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("refreshPathResponse cannot be null!!");
                                            }
                                           localRefreshPathResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","refreshPathResponse"),
                                               factory,xmlWriter);
                                        } if (localTeardownPathResponseTracker){
                                            if (localTeardownPathResponse==null){
                                                 throw new org.apache.axis2.databinding.ADBException("teardownPathResponse cannot be null!!");
                                            }
                                           localTeardownPathResponse.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","teardownPathResponse"),
                                               factory,xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "contentType"));
                                 
                                        if (localContentType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContentType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("contentType cannot be null!!");
                                        }
                                     if (localForwardTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "forward"));
                            
                            
                                    if (localForward==null){
                                         throw new org.apache.axis2.databinding.ADBException("forward cannot be null!!");
                                    }
                                    elementList.add(localForward);
                                } if (localCreateReservationTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "createReservation"));
                            
                            
                                    if (localCreateReservation==null){
                                         throw new org.apache.axis2.databinding.ADBException("createReservation cannot be null!!");
                                    }
                                    elementList.add(localCreateReservation);
                                } if (localModifyReservationTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "modifyReservation"));
                            
                            
                                    if (localModifyReservation==null){
                                         throw new org.apache.axis2.databinding.ADBException("modifyReservation cannot be null!!");
                                    }
                                    elementList.add(localModifyReservation);
                                } if (localCancelReservationTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "cancelReservation"));
                            
                            
                                    if (localCancelReservation==null){
                                         throw new org.apache.axis2.databinding.ADBException("cancelReservation cannot be null!!");
                                    }
                                    elementList.add(localCancelReservation);
                                } if (localQueryReservationTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "queryReservation"));
                            
                            
                                    if (localQueryReservation==null){
                                         throw new org.apache.axis2.databinding.ADBException("queryReservation cannot be null!!");
                                    }
                                    elementList.add(localQueryReservation);
                                } if (localListReservationsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "listReservations"));
                            
                            
                                    if (localListReservations==null){
                                         throw new org.apache.axis2.databinding.ADBException("listReservations cannot be null!!");
                                    }
                                    elementList.add(localListReservations);
                                } if (localCreatePathTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "createPath"));
                            
                            
                                    if (localCreatePath==null){
                                         throw new org.apache.axis2.databinding.ADBException("createPath cannot be null!!");
                                    }
                                    elementList.add(localCreatePath);
                                } if (localRefreshPathTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "refreshPath"));
                            
                            
                                    if (localRefreshPath==null){
                                         throw new org.apache.axis2.databinding.ADBException("refreshPath cannot be null!!");
                                    }
                                    elementList.add(localRefreshPath);
                                } if (localTeardownPathTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "teardownPath"));
                            
                            
                                    if (localTeardownPath==null){
                                         throw new org.apache.axis2.databinding.ADBException("teardownPath cannot be null!!");
                                    }
                                    elementList.add(localTeardownPath);
                                } if (localForwardResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "forwardResponse"));
                            
                            
                                    if (localForwardResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("forwardResponse cannot be null!!");
                                    }
                                    elementList.add(localForwardResponse);
                                } if (localCreateReservationResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "createReservationResponse"));
                            
                            
                                    if (localCreateReservationResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("createReservationResponse cannot be null!!");
                                    }
                                    elementList.add(localCreateReservationResponse);
                                } if (localModifyReservationResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "modifyReservationResponse"));
                            
                            
                                    if (localModifyReservationResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("modifyReservationResponse cannot be null!!");
                                    }
                                    elementList.add(localModifyReservationResponse);
                                } if (localCancelReservationResponseTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "cancelReservationResponse"));
                                 
                                        if (localCancelReservationResponse != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCancelReservationResponse));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cancelReservationResponse cannot be null!!");
                                        }
                                    } if (localQueryReservationResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "queryReservationResponse"));
                            
                            
                                    if (localQueryReservationResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("queryReservationResponse cannot be null!!");
                                    }
                                    elementList.add(localQueryReservationResponse);
                                } if (localListReservationsResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "listReservationsResponse"));
                            
                            
                                    if (localListReservationsResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("listReservationsResponse cannot be null!!");
                                    }
                                    elementList.add(localListReservationsResponse);
                                } if (localCreatePathResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "createPathResponse"));
                            
                            
                                    if (localCreatePathResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("createPathResponse cannot be null!!");
                                    }
                                    elementList.add(localCreatePathResponse);
                                } if (localRefreshPathResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "refreshPathResponse"));
                            
                            
                                    if (localRefreshPathResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("refreshPathResponse cannot be null!!");
                                    }
                                    elementList.add(localRefreshPathResponse);
                                } if (localTeardownPathResponseTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "teardownPathResponse"));
                            
                            
                                    if (localTeardownPathResponse==null){
                                         throw new org.apache.axis2.databinding.ADBException("teardownPathResponse cannot be null!!");
                                    }
                                    elementList.add(localTeardownPathResponse);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MsgDetails parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            MsgDetails object =
                new MsgDetails();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"msgDetails".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MsgDetails)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","contentType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setContentType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","forward").equals(reader.getName())){
                                
                                                object.setForward(net.es.oscars.oscars.ForwardPayload.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createReservation").equals(reader.getName())){
                                
                                                object.setCreateReservation(net.es.oscars.oscars.ResCreateContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","modifyReservation").equals(reader.getName())){
                                
                                                object.setModifyReservation(net.es.oscars.oscars.ModifyResContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","cancelReservation").equals(reader.getName())){
                                
                                                object.setCancelReservation(net.es.oscars.oscars.GlobalReservationId.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","queryReservation").equals(reader.getName())){
                                
                                                object.setQueryReservation(net.es.oscars.oscars.GlobalReservationId.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","listReservations").equals(reader.getName())){
                                
                                                object.setListReservations(net.es.oscars.oscars.ListRequest.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createPath").equals(reader.getName())){
                                
                                                object.setCreatePath(net.es.oscars.oscars.CreatePathContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","refreshPath").equals(reader.getName())){
                                
                                                object.setRefreshPath(net.es.oscars.oscars.RefreshPathContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","teardownPath").equals(reader.getName())){
                                
                                                object.setTeardownPath(net.es.oscars.oscars.TeardownPathContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","forwardResponse").equals(reader.getName())){
                                
                                                object.setForwardResponse(net.es.oscars.oscars.ForwardReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createReservationResponse").equals(reader.getName())){
                                
                                                object.setCreateReservationResponse(net.es.oscars.oscars.CreateReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","modifyReservationResponse").equals(reader.getName())){
                                
                                                object.setModifyReservationResponse(net.es.oscars.oscars.ModifyResReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","cancelReservationResponse").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCancelReservationResponse(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","queryReservationResponse").equals(reader.getName())){
                                
                                                object.setQueryReservationResponse(net.es.oscars.oscars.ResDetails.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","listReservationsResponse").equals(reader.getName())){
                                
                                                object.setListReservationsResponse(net.es.oscars.oscars.ListReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createPathResponse").equals(reader.getName())){
                                
                                                object.setCreatePathResponse(net.es.oscars.oscars.CreatePathResponseContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","refreshPathResponse").equals(reader.getName())){
                                
                                                object.setRefreshPathResponse(net.es.oscars.oscars.RefreshPathResponseContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","teardownPathResponse").equals(reader.getName())){
                                
                                                object.setTeardownPathResponse(net.es.oscars.oscars.TeardownPathResponseContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
          