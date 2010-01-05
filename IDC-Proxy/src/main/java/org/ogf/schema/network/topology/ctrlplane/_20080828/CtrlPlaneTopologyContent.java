
/**
 * CtrlPlaneTopologyContent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package org.ogf.schema.network.topology.ctrlplane._20080828;
            

            /**
            *  CtrlPlaneTopologyContent bean class
            */
        
        public  class CtrlPlaneTopologyContent
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = CtrlPlaneTopologyContent
                Namespace URI = http://ogf.org/schema/network/topology/ctrlPlane/20080828/
                Namespace Prefix = ns1
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://ogf.org/schema/network/topology/ctrlPlane/20080828/")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for IdcId
                        */

                        
                                    protected java.lang.String localIdcId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdcId(){
                               return localIdcId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdcId
                               */
                               public void setIdcId(java.lang.String param){
                            
                                            this.localIdcId=param;
                                    

                               }
                            

                        /**
                        * field for Path
                        * This was an Array!
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[] localPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[]
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[] getPath(){
                               return localPath;
                           }

                           
                        


                               
                              /**
                               * validate the array for Path
                               */
                              protected void validatePath(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Path
                              */
                              public void setPath(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[] param){
                              
                                   validatePath(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localPathTracker = true;
                                          } else {
                                             localPathTracker = false;
                                                 
                                          }
                                      
                                      this.localPath=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent
                             */
                             public void addPath(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent param){
                                   if (localPath == null){
                                   localPath = new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[]{};
                                   }

                            
                                 //update the setting tracker
                                localPathTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localPath);
                               list.add(param);
                               this.localPath =
                             (org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[])list.toArray(
                            new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[list.size()]);

                             }
                             

                        /**
                        * field for Domain
                        * This was an Array!
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[] localDomain ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomainTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[]
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[] getDomain(){
                               return localDomain;
                           }

                           
                        


                               
                              /**
                               * validate the array for Domain
                               */
                              protected void validateDomain(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Domain
                              */
                              public void setDomain(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[] param){
                              
                                   validateDomain(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localDomainTracker = true;
                                          } else {
                                             localDomainTracker = false;
                                                 
                                          }
                                      
                                      this.localDomain=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent
                             */
                             public void addDomain(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent param){
                                   if (localDomain == null){
                                   localDomain = new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[]{};
                                   }

                            
                                 //update the setting tracker
                                localDomainTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDomain);
                               list.add(param);
                               this.localDomain =
                             (org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[])list.toArray(
                            new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[list.size()]);

                             }
                             

                        /**
                        * field for DomainSignature
                        * This was an Array!
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[] localDomainSignature ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomainSignatureTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[]
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[] getDomainSignature(){
                               return localDomainSignature;
                           }

                           
                        


                               
                              /**
                               * validate the array for DomainSignature
                               */
                              protected void validateDomainSignature(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param DomainSignature
                              */
                              public void setDomainSignature(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[] param){
                              
                                   validateDomainSignature(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localDomainSignatureTracker = true;
                                          } else {
                                             localDomainSignatureTracker = false;
                                                 
                                          }
                                      
                                      this.localDomainSignature=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent
                             */
                             public void addDomainSignature(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent param){
                                   if (localDomainSignature == null){
                                   localDomainSignature = new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[]{};
                                   }

                            
                                 //update the setting tracker
                                localDomainSignatureTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localDomainSignature);
                               list.add(param);
                               this.localDomainSignature =
                             (org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[])list.toArray(
                            new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[list.size()]);

                             }
                             

                        /**
                        * field for Id
                        * This was an Attribute!
                        */

                        
                                    protected java.lang.String localId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            
                                            this.localId=param;
                                    

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
                       CtrlPlaneTopologyContent.this.serialize(parentQName,factory,xmlWriter);
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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ogf.org/schema/network/topology/ctrlPlane/20080828/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":CtrlPlaneTopologyContent",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "CtrlPlaneTopologyContent",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localId != null){
                                        
                                                writeAttribute("",
                                                         "id",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localId is null");
                                      }
                                    
                                    namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"idcId", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"idcId");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("idcId");
                                    }
                                

                                          if (localIdcId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idcId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdcId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localPathTracker){
                                       if (localPath!=null){
                                            for (int i = 0;i < localPath.length;i++){
                                                if (localPath[i] != null){
                                                 localPath[i].serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","path"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("path cannot be null!!");
                                        
                                    }
                                 } if (localDomainTracker){
                                       if (localDomain!=null){
                                            for (int i = 0;i < localDomain.length;i++){
                                                if (localDomain[i] != null){
                                                 localDomain[i].serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domain"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("domain cannot be null!!");
                                        
                                    }
                                 } if (localDomainSignatureTracker){
                                       if (localDomainSignature!=null){
                                            for (int i = 0;i < localDomainSignature.length;i++){
                                                if (localDomainSignature[i] != null){
                                                 localDomainSignature[i].serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domainSignature"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("domainSignature cannot be null!!");
                                        
                                    }
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "idcId"));
                                 
                                        if (localIdcId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdcId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idcId cannot be null!!");
                                        }
                                     if (localPathTracker){
                             if (localPath!=null) {
                                 for (int i = 0;i < localPath.length;i++){

                                    if (localPath[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                          "path"));
                                         elementList.add(localPath[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("path cannot be null!!");
                                    
                             }

                        } if (localDomainTracker){
                             if (localDomain!=null) {
                                 for (int i = 0;i < localDomain.length;i++){

                                    if (localDomain[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                          "domain"));
                                         elementList.add(localDomain[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("domain cannot be null!!");
                                    
                             }

                        } if (localDomainSignatureTracker){
                             if (localDomainSignature!=null) {
                                 for (int i = 0;i < localDomainSignature.length;i++){

                                    if (localDomainSignature[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                          "domainSignature"));
                                         elementList.add(localDomainSignature[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("domainSignature cannot be null!!");
                                    
                             }

                        }
                            attribList.add(
                            new javax.xml.namespace.QName("","id"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                

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
        public static CtrlPlaneTopologyContent parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            CtrlPlaneTopologyContent object =
                new CtrlPlaneTopologyContent();

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
                    
                            if (!"CtrlPlaneTopologyContent".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CtrlPlaneTopologyContent)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    // handle attribute "id"
                    java.lang.String tempAttribId =
                        
                                reader.getAttributeValue(null,"id");
                            
                   if (tempAttribId!=null){
                         java.lang.String content = tempAttribId;
                        
                                                 object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(tempAttribId));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute id is missing");
                           
                    }
                    handledAttributes.add("id");
                    
                    
                    reader.next();
                
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                        java.util.ArrayList list4 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","idcId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdcId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","path").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list2.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone2 = false;
                                                        while(!loopDone2){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone2 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","path").equals(reader.getName())){
                                                                    list2.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone2 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setPath((org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent.class,
                                                                list2));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domain").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list3.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domain").equals(reader.getName())){
                                                                    list3.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDomain((org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domainSignature").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list4.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone4 = false;
                                                        while(!loopDone4){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone4 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domainSignature").equals(reader.getName())){
                                                                    list4.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone4 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setDomainSignature((org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent.class,
                                                                list4));
                                                            
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
           
          