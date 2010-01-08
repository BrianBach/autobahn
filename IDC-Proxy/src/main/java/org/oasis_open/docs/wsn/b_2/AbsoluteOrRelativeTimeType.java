
/**
 * AbsoluteOrRelativeTimeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package org.oasis_open.docs.wsn.b_2;
            

            /**
            *  AbsoluteOrRelativeTimeType bean class
            */
        
        public  class AbsoluteOrRelativeTimeType extends  org.apache.axis2.databinding.types.Union 
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://docs.oasis-open.org/wsn/b-2",
                "AbsoluteOrRelativeTimeType",
                "ns5");

            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://docs.oasis-open.org/wsn/b-2")){
                return "ns5";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        
            

              public void setObject(java.lang.Object object){
                  
                      if (object instanceof java.util.Calendar){
                            this.localObject = object;
                  } else 
                      if (object instanceof org.apache.axis2.databinding.types.Duration){
                            this.localObject = object;
                  
                      } else {
                          throw new java.lang.RuntimeException("Invalid object type");
                      }
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
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       AbsoluteOrRelativeTimeType.this.serialize(MY_QNAME,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               MY_QNAME,factory,dataSource);
            
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
            
                // fist write the start element
                java.lang.String namespace = parentQName.getNamespaceURI();
                java.lang.String localName = parentQName.getLocalPart();

                if (! namespace.equals("")) {
                    java.lang.String prefix = xmlWriter.getPrefix(namespace);

                    if (prefix == null) {
                        prefix = generatePrefix(namespace);

                        xmlWriter.writeStartElement(prefix, localName, namespace);
                        xmlWriter.writeNamespace(prefix, namespace);
                        xmlWriter.setPrefix(prefix, namespace);

                    } else {
                        xmlWriter.writeStartElement(namespace, localName);
                    }

                } else {
                    xmlWriter.writeStartElement(localName);
                }

                
                      if (localObject instanceof java.util.Calendar){
                           java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.w3.org/2001/XMLSchema");
                           if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                   namespacePrefix+":dateTime",
                                   xmlWriter);
                           } else {
                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                   "dateTime",
                                   xmlWriter);
                           }

                       
                               xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString((java.util.Calendar)localObject));
                           } else 
                      if (localObject instanceof org.apache.axis2.databinding.types.Duration){
                           java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.w3.org/2001/XMLSchema");
                           if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                   namespacePrefix+":duration",
                                   xmlWriter);
                           } else {
                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                   "duration",
                                   xmlWriter);
                           }

                       
                               xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString((org.apache.axis2.databinding.types.Duration)localObject));
                           
                      } else {
                          throw new org.apache.axis2.databinding.ADBException("Invalid object type");
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


        
                  return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME,
                    new java.lang.Object[]{
                    org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
                    localObject.toString()
                    },
                    null);
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        
            
              public static AbsoluteOrRelativeTimeType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader,
                                                     java.lang.String namespaceURI,
                                                     java.lang.String type) throws org.apache.axis2.databinding.ADBException {

                    AbsoluteOrRelativeTimeType object = null;
                    try {
                        if ("http://www.w3.org/2001/XMLSchema".equals(namespaceURI)) {
                            object = new AbsoluteOrRelativeTimeType();
                            object.setObject(xmlStreamReader, namespaceURI, type);
                        } else {
                            object = new AbsoluteOrRelativeTimeType();
                            object.setObject(net.es.oscars.oscars.ExtensionMapper.getTypeObject(namespaceURI, type, xmlStreamReader));
                        }
                        return object;
                    } catch (java.lang.Exception e) {
                        throw new org.apache.axis2.databinding.ADBException("Error in parsing value");
                    }
               }

               public static AbsoluteOrRelativeTimeType fromString(java.lang.String value,
                                                        java.lang.String namespaceURI){
                    AbsoluteOrRelativeTimeType object = new AbsoluteOrRelativeTimeType();
                    boolean isValueSet = false;
                    
                      // we have to set the object with the first matching type.
                      if (!isValueSet) {
                        
                                try {
                                    java.lang.reflect.Method converterMethod =
                                            org.apache.axis2.databinding.utils.ConverterUtil.class.getMethod(
                                                    "convertToCalendar",
                                                    new java.lang.Class[]{java.lang.String.class});
                                    object.setObject(converterMethod.invoke(null, new java.lang.Object[]{value}));
                                    isValueSet = true;
                                } catch (java.lang.Exception e) {
                                }
                            
                      }
                    
                      // we have to set the object with the first matching type.
                      if (!isValueSet) {
                        
                                try {
                                    java.lang.reflect.Method converterMethod =
                                            org.apache.axis2.databinding.utils.ConverterUtil.class.getMethod(
                                                    "convertToDuration",
                                                    new java.lang.Class[]{java.lang.String.class});
                                    object.setObject(converterMethod.invoke(null, new java.lang.Object[]{value}));
                                    isValueSet = true;
                                } catch (java.lang.Exception e) {
                                }
                            
                      }
                    
                    return object;
                }

                public static AbsoluteOrRelativeTimeType fromString(javax.xml.stream.XMLStreamReader xmlStreamReader,
                                                                    java.lang.String content) {
                    if (content.indexOf(":") > -1){
                        java.lang.String prefix = content.substring(0,content.indexOf(":"));
                        java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
                        return AbsoluteOrRelativeTimeType.Factory.fromString(content,namespaceUri);
                    } else {
                       return AbsoluteOrRelativeTimeType.Factory.fromString(content,"");
                    }
                }

        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static AbsoluteOrRelativeTimeType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            AbsoluteOrRelativeTimeType object =
                new AbsoluteOrRelativeTimeType();

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
                    
                            java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                            object = AbsoluteOrRelativeTimeType.Factory.fromString(reader,nsUri,type);
                        

                  }
                
                  } else {
                    // i.e this is an union type with out specific xsi:type
                    java.lang.String content = reader.getElementText();
                    if (content.indexOf(":") > -1){
                        // i.e. this could be a qname
                        prefix = content.substring(0,content.indexOf(":"));
                        namespaceuri = reader.getNamespaceContext().getNamespaceURI(prefix);
                        object = AbsoluteOrRelativeTimeType.Factory.fromString(content,namespaceuri);
                    } else {
                        object = AbsoluteOrRelativeTimeType.Factory.fromString(content,"");
                    }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
          