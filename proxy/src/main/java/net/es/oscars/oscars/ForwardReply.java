
/**
 * ForwardReply.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package net.es.oscars.oscars;
            

            /**
            *  ForwardReply bean class
            */
        
        public  class ForwardReply
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = forwardReply
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
                        * field for CreateReservation
                        */

                        
                                    protected net.es.oscars.oscars.CreateReply localCreateReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreateReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.CreateReply
                           */
                           public  net.es.oscars.oscars.CreateReply getCreateReservation(){
                               return localCreateReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreateReservation
                               */
                               public void setCreateReservation(net.es.oscars.oscars.CreateReply param){
                            
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

                        
                                    protected net.es.oscars.oscars.ModifyResReply localModifyReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ModifyResReply
                           */
                           public  net.es.oscars.oscars.ModifyResReply getModifyReservation(){
                               return localModifyReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyReservation
                               */
                               public void setModifyReservation(net.es.oscars.oscars.ModifyResReply param){
                            
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

                        
                                    protected java.lang.String localCancelReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCancelReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCancelReservation(){
                               return localCancelReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CancelReservation
                               */
                               public void setCancelReservation(java.lang.String param){
                            
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

                        
                                    protected net.es.oscars.oscars.ResDetails localQueryReservation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localQueryReservationTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ResDetails
                           */
                           public  net.es.oscars.oscars.ResDetails getQueryReservation(){
                               return localQueryReservation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param QueryReservation
                               */
                               public void setQueryReservation(net.es.oscars.oscars.ResDetails param){
                            
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

                        
                                    protected net.es.oscars.oscars.ListReply localListReservations ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localListReservationsTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.ListReply
                           */
                           public  net.es.oscars.oscars.ListReply getListReservations(){
                               return localListReservations;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ListReservations
                               */
                               public void setListReservations(net.es.oscars.oscars.ListReply param){
                            
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

                        
                                    protected net.es.oscars.oscars.CreatePathResponseContent localCreatePath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatePathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.CreatePathResponseContent
                           */
                           public  net.es.oscars.oscars.CreatePathResponseContent getCreatePath(){
                               return localCreatePath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreatePath
                               */
                               public void setCreatePath(net.es.oscars.oscars.CreatePathResponseContent param){
                            
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

                        
                                    protected net.es.oscars.oscars.RefreshPathResponseContent localRefreshPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRefreshPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.RefreshPathResponseContent
                           */
                           public  net.es.oscars.oscars.RefreshPathResponseContent getRefreshPath(){
                               return localRefreshPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RefreshPath
                               */
                               public void setRefreshPath(net.es.oscars.oscars.RefreshPathResponseContent param){
                            
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

                        
                                    protected net.es.oscars.oscars.TeardownPathResponseContent localTeardownPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTeardownPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.TeardownPathResponseContent
                           */
                           public  net.es.oscars.oscars.TeardownPathResponseContent getTeardownPath(){
                               return localTeardownPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TeardownPath
                               */
                               public void setTeardownPath(net.es.oscars.oscars.TeardownPathResponseContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTeardownPathTracker = true;
                                       } else {
                                          localTeardownPathTracker = false;
                                              
                                       }
                                   
                                            this.localTeardownPath=param;
                                    

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
                       ForwardReply.this.serialize(parentQName,factory,xmlWriter);
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
                           namespacePrefix+":forwardReply",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "forwardReply",
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
                              if (localCreateReservationTracker){
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
                                    namespace = "http://oscars.es.net/OSCARS";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"cancelReservation", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"cancelReservation");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("cancelReservation");
                                    }
                                

                                          if (localCancelReservation==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cancelReservation cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCancelReservation);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
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
                                     if (localCreateReservationTracker){
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
                                 
                                        if (localCancelReservation != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCancelReservation));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cancelReservation cannot be null!!");
                                        }
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
        public static ForwardReply parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ForwardReply object =
                new ForwardReply();

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
                    
                            if (!"forwardReply".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ForwardReply)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createReservation").equals(reader.getName())){
                                
                                                object.setCreateReservation(net.es.oscars.oscars.CreateReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","modifyReservation").equals(reader.getName())){
                                
                                                object.setModifyReservation(net.es.oscars.oscars.ModifyResReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","cancelReservation").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCancelReservation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","queryReservation").equals(reader.getName())){
                                
                                                object.setQueryReservation(net.es.oscars.oscars.ResDetails.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","listReservations").equals(reader.getName())){
                                
                                                object.setListReservations(net.es.oscars.oscars.ListReply.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","createPath").equals(reader.getName())){
                                
                                                object.setCreatePath(net.es.oscars.oscars.CreatePathResponseContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","refreshPath").equals(reader.getName())){
                                
                                                object.setRefreshPath(net.es.oscars.oscars.RefreshPathResponseContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","teardownPath").equals(reader.getName())){
                                
                                                object.setTeardownPath(net.es.oscars.oscars.TeardownPathResponseContent.Factory.parse(reader));
                                              
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
           
          