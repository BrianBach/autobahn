
/**
 * NotificationMessageHolderType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package org.oasis_open.docs.wsn.b_2;
            

            /**
            *  NotificationMessageHolderType bean class
            */
        
        public  class NotificationMessageHolderType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = NotificationMessageHolderType
                Namespace URI = http://docs.oasis-open.org/wsn/b-2
                Namespace Prefix = ns5
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://docs.oasis-open.org/wsn/b-2")){
                return "ns5";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for SubscriptionReference
                        */

                        
                                    protected org.w3.www._2005._08.addressing.EndpointReferenceType localSubscriptionReference ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSubscriptionReferenceTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2005._08.addressing.EndpointReferenceType
                           */
                           public  org.w3.www._2005._08.addressing.EndpointReferenceType getSubscriptionReference(){
                               return localSubscriptionReference;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SubscriptionReference
                               */
                               public void setSubscriptionReference(org.w3.www._2005._08.addressing.EndpointReferenceType param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localSubscriptionReferenceTracker = true;
                                       } else {
                                          localSubscriptionReferenceTracker = false;
                                              
                                       }
                                   
                                            this.localSubscriptionReference=param;
                                    

                               }
                            

                        /**
                        * field for Topic
                        */

                        
                                    protected org.oasis_open.docs.wsn.b_2.TopicExpressionType localTopic ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTopicTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.oasis_open.docs.wsn.b_2.TopicExpressionType
                           */
                           public  org.oasis_open.docs.wsn.b_2.TopicExpressionType getTopic(){
                               return localTopic;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Topic
                               */
                               public void setTopic(org.oasis_open.docs.wsn.b_2.TopicExpressionType param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTopicTracker = true;
                                       } else {
                                          localTopicTracker = false;
                                              
                                       }
                                   
                                            this.localTopic=param;
                                    

                               }
                            

                        /**
                        * field for ProducerReference
                        */

                        
                                    protected org.w3.www._2005._08.addressing.EndpointReferenceType localProducerReference ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProducerReferenceTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2005._08.addressing.EndpointReferenceType
                           */
                           public  org.w3.www._2005._08.addressing.EndpointReferenceType getProducerReference(){
                               return localProducerReference;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProducerReference
                               */
                               public void setProducerReference(org.w3.www._2005._08.addressing.EndpointReferenceType param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localProducerReferenceTracker = true;
                                       } else {
                                          localProducerReferenceTracker = false;
                                              
                                       }
                                   
                                            this.localProducerReference=param;
                                    

                               }
                            

                        /**
                        * field for Message
                        */

                        
                                    protected org.oasis_open.docs.wsn.b_2.MessageType localMessage ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.oasis_open.docs.wsn.b_2.MessageType
                           */
                           public  org.oasis_open.docs.wsn.b_2.MessageType getMessage(){
                               return localMessage;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Message
                               */
                               public void setMessage(org.oasis_open.docs.wsn.b_2.MessageType param){
                            
                                            this.localMessage=param;
                                    

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
                       NotificationMessageHolderType.this.serialize(parentQName,factory,xmlWriter);
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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://docs.oasis-open.org/wsn/b-2");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":NotificationMessageHolderType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "NotificationMessageHolderType",
                           xmlWriter);
                   }

               
                   }
                if (localSubscriptionReferenceTracker){
                                            if (localSubscriptionReference==null){
                                                 throw new org.apache.axis2.databinding.ADBException("SubscriptionReference cannot be null!!");
                                            }
                                           localSubscriptionReference.serialize(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","SubscriptionReference"),
                                               factory,xmlWriter);
                                        } if (localTopicTracker){
                                            if (localTopic==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Topic cannot be null!!");
                                            }
                                           localTopic.serialize(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","Topic"),
                                               factory,xmlWriter);
                                        } if (localProducerReferenceTracker){
                                            if (localProducerReference==null){
                                                 throw new org.apache.axis2.databinding.ADBException("ProducerReference cannot be null!!");
                                            }
                                           localProducerReference.serialize(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","ProducerReference"),
                                               factory,xmlWriter);
                                        }
                                            if (localMessage==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Message cannot be null!!");
                                            }
                                           localMessage.serialize(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","Message"),
                                               factory,xmlWriter);
                                        
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

                 if (localSubscriptionReferenceTracker){
                            elementList.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2",
                                                                      "SubscriptionReference"));
                            
                            
                                    if (localSubscriptionReference==null){
                                         throw new org.apache.axis2.databinding.ADBException("SubscriptionReference cannot be null!!");
                                    }
                                    elementList.add(localSubscriptionReference);
                                } if (localTopicTracker){
                            elementList.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2",
                                                                      "Topic"));
                            
                            
                                    if (localTopic==null){
                                         throw new org.apache.axis2.databinding.ADBException("Topic cannot be null!!");
                                    }
                                    elementList.add(localTopic);
                                } if (localProducerReferenceTracker){
                            elementList.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2",
                                                                      "ProducerReference"));
                            
                            
                                    if (localProducerReference==null){
                                         throw new org.apache.axis2.databinding.ADBException("ProducerReference cannot be null!!");
                                    }
                                    elementList.add(localProducerReference);
                                }
                            elementList.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2",
                                                                      "Message"));
                            
                            
                                    if (localMessage==null){
                                         throw new org.apache.axis2.databinding.ADBException("Message cannot be null!!");
                                    }
                                    elementList.add(localMessage);
                                

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
        public static NotificationMessageHolderType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            NotificationMessageHolderType object =
                new NotificationMessageHolderType();

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
                    
                            if (!"NotificationMessageHolderType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (NotificationMessageHolderType)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","SubscriptionReference").equals(reader.getName())){
                                
                                                object.setSubscriptionReference(org.w3.www._2005._08.addressing.EndpointReferenceType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","Topic").equals(reader.getName())){
                                
                                                object.setTopic(org.oasis_open.docs.wsn.b_2.TopicExpressionType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","ProducerReference").equals(reader.getName())){
                                
                                                object.setProducerReference(org.w3.www._2005._08.addressing.EndpointReferenceType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/b-2","Message").equals(reader.getName())){
                                
                                                object.setMessage(org.oasis_open.docs.wsn.b_2.MessageType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
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
           
          