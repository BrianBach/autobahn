
/**
 * CtrlPlaneHopContent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */
            
                package org.ogf.schema.network.topology.ctrlplane._20080828;
            

            /**
            *  CtrlPlaneHopContent bean class
            */
        
        public  class CtrlPlaneHopContent
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = CtrlPlaneHopContent
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
                        * field for DomainIdRef
                        */

                        
                                    protected java.lang.String localDomainIdRef ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomainIdRefTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDomainIdRef(){
                               return localDomainIdRef;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DomainIdRef
                               */
                               public void setDomainIdRef(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localDomainIdRefTracker = true;
                                       } else {
                                          localDomainIdRefTracker = false;
                                              
                                       }
                                   
                                            this.localDomainIdRef=param;
                                    

                               }
                            

                        /**
                        * field for NodeIdRef
                        */

                        
                                    protected java.lang.String localNodeIdRef ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNodeIdRefTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNodeIdRef(){
                               return localNodeIdRef;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NodeIdRef
                               */
                               public void setNodeIdRef(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localNodeIdRefTracker = true;
                                       } else {
                                          localNodeIdRefTracker = false;
                                              
                                       }
                                   
                                            this.localNodeIdRef=param;
                                    

                               }
                            

                        /**
                        * field for PortIdRef
                        */

                        
                                    protected java.lang.String localPortIdRef ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPortIdRefTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPortIdRef(){
                               return localPortIdRef;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PortIdRef
                               */
                               public void setPortIdRef(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localPortIdRefTracker = true;
                                       } else {
                                          localPortIdRefTracker = false;
                                              
                                       }
                                   
                                            this.localPortIdRef=param;
                                    

                               }
                            

                        /**
                        * field for LinkIdRef
                        */

                        
                                    protected java.lang.String localLinkIdRef ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkIdRefTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkIdRef(){
                               return localLinkIdRef;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkIdRef
                               */
                               public void setLinkIdRef(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localLinkIdRefTracker = true;
                                       } else {
                                          localLinkIdRefTracker = false;
                                              
                                       }
                                   
                                            this.localLinkIdRef=param;
                                    

                               }
                            

                        /**
                        * field for Domain
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent localDomain ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomainTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent getDomain(){
                               return localDomain;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Domain
                               */
                               public void setDomain(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localDomainTracker = true;
                                       } else {
                                          localDomainTracker = false;
                                              
                                       }
                                   
                                            this.localDomain=param;
                                    

                               }
                            

                        /**
                        * field for Node
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent localNode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNodeTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent getNode(){
                               return localNode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Node
                               */
                               public void setNode(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localNodeTracker = true;
                                       } else {
                                          localNodeTracker = false;
                                              
                                       }
                                   
                                            this.localNode=param;
                                    

                               }
                            

                        /**
                        * field for Port
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent localPort ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPortTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent getPort(){
                               return localPort;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Port
                               */
                               public void setPort(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localPortTracker = true;
                                       } else {
                                          localPortTracker = false;
                                              
                                       }
                                   
                                            this.localPort=param;
                                    

                               }
                            

                        /**
                        * field for Link
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent localLink ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent getLink(){
                               return localLink;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Link
                               */
                               public void setLink(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localLinkTracker = true;
                                       } else {
                                          localLinkTracker = false;
                                              
                                       }
                                   
                                            this.localLink=param;
                                    

                               }
                            

                        /**
                        * field for NextHop
                        * This was an Array!
                        */

                        
                                    protected org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[] localNextHop ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNextHopTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[]
                           */
                           public  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[] getNextHop(){
                               return localNextHop;
                           }

                           
                        


                               
                              /**
                               * validate the array for NextHop
                               */
                              protected void validateNextHop(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param NextHop
                              */
                              public void setNextHop(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[] param){
                              
                                   validateNextHop(param);

                               
                                          if (param != null){
                                             //update the setting tracker
                                             localNextHopTracker = true;
                                          } else {
                                             localNextHopTracker = false;
                                                 
                                          }
                                      
                                      this.localNextHop=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent
                             */
                             public void addNextHop(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent param){
                                   if (localNextHop == null){
                                   localNextHop = new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[]{};
                                   }

                            
                                 //update the setting tracker
                                localNextHopTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localNextHop);
                               list.add(param);
                               this.localNextHop =
                             (org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[])list.toArray(
                            new org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[list.size()]);

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
                       CtrlPlaneHopContent.this.serialize(parentQName,factory,xmlWriter);
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
                           namespacePrefix+":CtrlPlaneHopContent",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "CtrlPlaneHopContent",
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
                                     if (localDomainIdRefTracker){
                                    namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"domainIdRef", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"domainIdRef");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("domainIdRef");
                                    }
                                

                                          if (localDomainIdRef==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("domainIdRef cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDomainIdRef);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNodeIdRefTracker){
                                    namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"nodeIdRef", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"nodeIdRef");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("nodeIdRef");
                                    }
                                

                                          if (localNodeIdRef==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nodeIdRef cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNodeIdRef);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPortIdRefTracker){
                                    namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"portIdRef", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"portIdRef");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("portIdRef");
                                    }
                                

                                          if (localPortIdRef==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("portIdRef cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPortIdRef);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkIdRefTracker){
                                    namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"linkIdRef", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"linkIdRef");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("linkIdRef");
                                    }
                                

                                          if (localLinkIdRef==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("linkIdRef cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkIdRef);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDomainTracker){
                                            if (localDomain==null){
                                                 throw new org.apache.axis2.databinding.ADBException("domain cannot be null!!");
                                            }
                                           localDomain.serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domain"),
                                               factory,xmlWriter);
                                        } if (localNodeTracker){
                                            if (localNode==null){
                                                 throw new org.apache.axis2.databinding.ADBException("node cannot be null!!");
                                            }
                                           localNode.serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","node"),
                                               factory,xmlWriter);
                                        } if (localPortTracker){
                                            if (localPort==null){
                                                 throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
                                            }
                                           localPort.serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","port"),
                                               factory,xmlWriter);
                                        } if (localLinkTracker){
                                            if (localLink==null){
                                                 throw new org.apache.axis2.databinding.ADBException("link cannot be null!!");
                                            }
                                           localLink.serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","link"),
                                               factory,xmlWriter);
                                        } if (localNextHopTracker){
                                       if (localNextHop!=null){
                                            for (int i = 0;i < localNextHop.length;i++){
                                                if (localNextHop[i] != null){
                                                 localNextHop[i].serialize(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","nextHop"),
                                                           factory,xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("nextHop cannot be null!!");
                                        
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

                 if (localDomainIdRefTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "domainIdRef"));
                                 
                                        if (localDomainIdRef != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDomainIdRef));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("domainIdRef cannot be null!!");
                                        }
                                    } if (localNodeIdRefTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "nodeIdRef"));
                                 
                                        if (localNodeIdRef != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNodeIdRef));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nodeIdRef cannot be null!!");
                                        }
                                    } if (localPortIdRefTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "portIdRef"));
                                 
                                        if (localPortIdRef != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPortIdRef));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("portIdRef cannot be null!!");
                                        }
                                    } if (localLinkIdRefTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "linkIdRef"));
                                 
                                        if (localLinkIdRef != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkIdRef));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("linkIdRef cannot be null!!");
                                        }
                                    } if (localDomainTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "domain"));
                            
                            
                                    if (localDomain==null){
                                         throw new org.apache.axis2.databinding.ADBException("domain cannot be null!!");
                                    }
                                    elementList.add(localDomain);
                                } if (localNodeTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "node"));
                            
                            
                                    if (localNode==null){
                                         throw new org.apache.axis2.databinding.ADBException("node cannot be null!!");
                                    }
                                    elementList.add(localNode);
                                } if (localPortTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "port"));
                            
                            
                                    if (localPort==null){
                                         throw new org.apache.axis2.databinding.ADBException("port cannot be null!!");
                                    }
                                    elementList.add(localPort);
                                } if (localLinkTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                      "link"));
                            
                            
                                    if (localLink==null){
                                         throw new org.apache.axis2.databinding.ADBException("link cannot be null!!");
                                    }
                                    elementList.add(localLink);
                                } if (localNextHopTracker){
                             if (localNextHop!=null) {
                                 for (int i = 0;i < localNextHop.length;i++){

                                    if (localNextHop[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/",
                                                                          "nextHop"));
                                         elementList.add(localNextHop[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("nextHop cannot be null!!");
                                    
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
        public static CtrlPlaneHopContent parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            CtrlPlaneHopContent object =
                new CtrlPlaneHopContent();

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
                    
                            if (!"CtrlPlaneHopContent".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CtrlPlaneHopContent)net.es.oscars.oscars.ExtensionMapper.getTypeObject(
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
                
                        java.util.ArrayList list9 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domainIdRef").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDomainIdRef(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","nodeIdRef").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNodeIdRef(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","portIdRef").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPortIdRef(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","linkIdRef").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkIdRef(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","domain").equals(reader.getName())){
                                
                                                object.setDomain(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","node").equals(reader.getName())){
                                
                                                object.setNode(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","port").equals(reader.getName())){
                                
                                                object.setPort(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","link").equals(reader.getName())){
                                
                                                object.setLink(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","nextHop").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list9.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone9 = false;
                                                        while(!loopDone9){
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
                                                                loopDone9 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://ogf.org/schema/network/topology/ctrlPlane/20080828/","nextHop").equals(reader.getName())){
                                                                    list9.add(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone9 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setNextHop((org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent.class,
                                                                list9));
                                                            
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
           
          