
/**
 * PathInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package net.es.oscars.oscars;
            

            /**
            *  PathInfo bean class
            */
        
        public  class PathInfo
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = pathInfo
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
                        * field for PathSetupMode
                        */

                        
                                    protected java.lang.String localPathSetupMode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPathSetupMode(){
                               return localPathSetupMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PathSetupMode
                               */
                               public void setPathSetupMode(java.lang.String param){
                            
                                            this.localPathSetupMode=param;
                                    

                               }
                            

                        /**
                        * field for PathType
                        */

                        
                                    protected java.lang.String localPathType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPathTypeTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPathType(){
                               return localPathType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PathType
                               */
                               public void setPathType(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localPathTypeTracker = true;
                                       } else {
                                          localPathTypeTracker = false;
                                              
                                       }
                                   
                                            this.localPathType=param;
                                    

                               }
                            

                        /**
                        * field for Path
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent localPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPathTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent getPath(){
                               return localPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Path
                               */
                               public void setPath(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localPathTracker = true;
                                       } else {
                                          localPathTracker = false;
                                              
                                       }
                                   
                                            this.localPath=param;
                                    

                               }
                            

                        /**
                        * field for Layer2Info
                        */

                        
                                    protected net.es.oscars.oscars.Layer2Info localLayer2Info ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLayer2InfoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.Layer2Info
                           */
                           public  net.es.oscars.oscars.Layer2Info getLayer2Info(){
                               return localLayer2Info;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Layer2Info
                               */
                               public void setLayer2Info(net.es.oscars.oscars.Layer2Info param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localLayer2InfoTracker = true;
                                       } else {
                                          localLayer2InfoTracker = false;
                                              
                                       }
                                   
                                            this.localLayer2Info=param;
                                    

                               }
                            

                        /**
                        * field for Layer3Info
                        */

                        
                                    protected net.es.oscars.oscars.Layer3Info localLayer3Info ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLayer3InfoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.Layer3Info
                           */
                           public  net.es.oscars.oscars.Layer3Info getLayer3Info(){
                               return localLayer3Info;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Layer3Info
                               */
                               public void setLayer3Info(net.es.oscars.oscars.Layer3Info param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localLayer3InfoTracker = true;
                                       } else {
                                          localLayer3InfoTracker = false;
                                              
                                       }
                                   
                                            this.localLayer3Info=param;
                                    

                               }
                            

                        /**
                        * field for MplsInfo
                        */

                        
                                    protected net.es.oscars.oscars.MplsInfo localMplsInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMplsInfoTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return net.es.oscars.oscars.MplsInfo
                           */
                           public  net.es.oscars.oscars.MplsInfo getMplsInfo(){
                               return localMplsInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MplsInfo
                               */
                               public void setMplsInfo(net.es.oscars.oscars.MplsInfo param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMplsInfoTracker = true;
                                       } else {
                                          localMplsInfoTracker = false;
                                              
                                       }
                                   
                                            this.localMplsInfo=param;
                                    

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
                       PathInfo.this.serialize(parentQName,factory,xmlWriter);
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
                           namespacePrefix+":pathInfo",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "pathInfo",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://oscars.es.net/OSCARS";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"pathSetupMode", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"pathSetupMode");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("pathSetupMode");
                                    }
                                

                                          if (localPathSetupMode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pathSetupMode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPathSetupMode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localPathTypeTracker){
                                    namespace = "http://oscars.es.net/OSCARS";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"pathType", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"pathType");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("pathType");
                                    }
                                

                                          if (localPathType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pathType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPathType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPathTracker){
                                            if (localPath==null){
                                                 throw new org.apache.axis2.databinding.ADBException("path cannot be null!!");
                                            }
                                           localPath.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","path"),
                                               factory,xmlWriter);
                                        } if (localLayer2InfoTracker){
                                            if (localLayer2Info==null){
                                                 throw new org.apache.axis2.databinding.ADBException("layer2Info cannot be null!!");
                                            }
                                           localLayer2Info.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","layer2Info"),
                                               factory,xmlWriter);
                                        } if (localLayer3InfoTracker){
                                            if (localLayer3Info==null){
                                                 throw new org.apache.axis2.databinding.ADBException("layer3Info cannot be null!!");
                                            }
                                           localLayer3Info.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","layer3Info"),
                                               factory,xmlWriter);
                                        } if (localMplsInfoTracker){
                                            if (localMplsInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("mplsInfo cannot be null!!");
                                            }
                                           localMplsInfo.serialize(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","mplsInfo"),
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
                                                                      "pathSetupMode"));
                                 
                                        if (localPathSetupMode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPathSetupMode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pathSetupMode cannot be null!!");
                                        }
                                     if (localPathTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "pathType"));
                                 
                                        if (localPathType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPathType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pathType cannot be null!!");
                                        }
                                    } if (localPathTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "path"));
                            
                            
                                    if (localPath==null){
                                         throw new org.apache.axis2.databinding.ADBException("path cannot be null!!");
                                    }
                                    elementList.add(localPath);
                                } if (localLayer2InfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "layer2Info"));
                            
                            
                                    if (localLayer2Info==null){
                                         throw new org.apache.axis2.databinding.ADBException("layer2Info cannot be null!!");
                                    }
                                    elementList.add(localLayer2Info);
                                } if (localLayer3InfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "layer3Info"));
                            
                            
                                    if (localLayer3Info==null){
                                         throw new org.apache.axis2.databinding.ADBException("layer3Info cannot be null!!");
                                    }
                                    elementList.add(localLayer3Info);
                                } if (localMplsInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://oscars.es.net/OSCARS",
                                                                      "mplsInfo"));
                            
                            
                                    if (localMplsInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("mplsInfo cannot be null!!");
                                    }
                                    elementList.add(localMplsInfo);
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
        public static PathInfo parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            PathInfo object =
                new PathInfo();

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
                    
                            if (!"pathInfo".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (PathInfo)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","pathSetupMode").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPathSetupMode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","pathType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPathType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","path").equals(reader.getName())){
                                
                                                object.setPath(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","layer2Info").equals(reader.getName())){
                                
                                                object.setLayer2Info(net.es.oscars.oscars.Layer2Info.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","layer3Info").equals(reader.getName())){
                                
                                                object.setLayer3Info(net.es.oscars.oscars.Layer3Info.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oscars.es.net/OSCARS","mplsInfo").equals(reader.getName())){
                                
                                                object.setMplsInfo(net.es.oscars.oscars.MplsInfo.Factory.parse(reader));
                                              
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
           
          