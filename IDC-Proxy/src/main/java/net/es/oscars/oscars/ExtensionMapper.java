
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:41 LKT)
 */

            package net.es.oscars.oscars;
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://docs.oasis-open.org/wsrf/r-2".equals(namespaceURI) &&
                  "ResourceUnknownFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "MetadataType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.MetadataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "NotifyMessageNotSupportedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlanePortContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePortContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "NoCurrentMessageOnTopicFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "globalReservationId".equals(typeName)){
                   
                            return  net.es.oscars.oscars.GlobalReservationId.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneDomainContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnableToDestroySubscriptionFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "resDetails".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ResDetails.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "modifyResContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ModifyResContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "MessageType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.MessageType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneSwitchingCapabilitySpecificInfo".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneSwitchingCapabilitySpecificInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnsupportedPolicyRequestFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "TopicNotSupportedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "initiateTopologyPullResponseContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.InitiateTopologyPullResponseContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "EndpointReferenceType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.EndpointReferenceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "Duration".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.Duration.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "initiateTopologyPullContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.InitiateTopologyPullContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsrf/bf-2".equals(namespaceURI) &&
                  "FaultCauseType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsrf.bf_2.FaultCauseType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "TopicExpressionType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.TopicExpressionType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "SubscriptionPolicy_type0".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.SubscriptionPolicy_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "SubscribeCreationFailedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "NotificationMessageHolderType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "ResumeFailedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnableToGetMessagesFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "listReply".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ListReply.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsrf/bf-2".equals(namespaceURI) &&
                  "BaseFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsrf.bf_2.BaseFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "FaultCodesOpenEnumType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.FaultCodesOpenEnumType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "layer3Info".equals(typeName)){
                   
                            return  net.es.oscars.oscars.Layer3Info.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "AbsoluteOrRelativeTimeType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.AbsoluteOrRelativeTimeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnacceptableInitialTerminationTimeFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "eventContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.EventContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "vlanTag".equals(typeName)){
                   
                            return  net.es.oscars.oscars.VlanTag.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "ProblemActionType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.ProblemActionType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "RelationshipType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.RelationshipType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneSwcapContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneSwcapContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsrf/r-2".equals(namespaceURI) &&
                  "ResourceUnavailableFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsrf.r_2.ResourceUnavailableFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "AttributedURIType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.AttributedURIType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "modifyResReply".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ModifyResReply.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "PauseFailedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.PauseFailedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneHopContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "AttributedUnsignedLongType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.AttributedUnsignedLongType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/br-2".equals(namespaceURI) &&
                  "PublisherRegistrationFailedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "Lifetime".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.Lifetime.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "InvalidTopicExpressionFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlanePathContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsrf/bf-2".equals(namespaceURI) &&
                  "ErrorCodeType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsrf.bf_2.ErrorCodeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "emptyArg".equals(typeName)){
                   
                            return  net.es.oscars.oscars.EmptyArg.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "RelationshipTypeOpenEnum".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.RelationshipTypeOpenEnum.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "ReferenceParametersType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.ReferenceParametersType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "AttributedQNameType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.AttributedQNameType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnrecognizedPolicyRequestFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "localDetails".equals(typeName)){
                   
                            return  net.es.oscars.oscars.LocalDetails.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "layer2Info".equals(typeName)){
                   
                            return  net.es.oscars.oscars.Layer2Info.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/br-2".equals(namespaceURI) &&
                  "PublisherRegistrationRejectedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneNodeContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNodeContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "RelatesToType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.RelatesToType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "QueryExpressionType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.QueryExpressionType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "FaultCodesType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.FaultCodesType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnableToDestroyPullPointFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "MultipleTopicsSpecifiedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "mplsInfo".equals(typeName)){
                   
                            return  net.es.oscars.oscars.MplsInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "pathInfo".equals(typeName)){
                   
                            return  net.es.oscars.oscars.PathInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneAddressContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneAddressContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneNextHopContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneNextHopContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "SubscriptionPolicyType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.SubscriptionPolicyType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "FilterType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.FilterType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "createPathResponseContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.CreatePathResponseContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "refreshPathResponseContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.RefreshPathResponseContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2005/08/addressing".equals(namespaceURI) &&
                  "AttributedAnyType".equals(typeName)){
                   
                            return  org.w3.www._2005._08.addressing.AttributedAnyType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "getTopologyResponseContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.GetTopologyResponseContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneLinkContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/br-2".equals(namespaceURI) &&
                  "ResourceNotDestroyedFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneAdministrativeGroup".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneAdministrativeGroup.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "teardownPathContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.TeardownPathContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneTopologyContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneTopologyContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "CtrlPlaneDomainSignatureContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "createPathContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.CreatePathContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "InvalidMessageContentExpressionFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "createReply".equals(typeName)){
                   
                            return  net.es.oscars.oscars.CreateReply.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "refreshPathContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.RefreshPathContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "forwardReply".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ForwardReply.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "InvalidProducerPropertiesExpressionFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "forwardPayload".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ForwardPayload.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "getTopologyContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.GetTopologyContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "resCreateContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ResCreateContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "TopicExpressionDialectUnknownFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "teardownPathResponseContent".equals(typeName)){
                   
                            return  net.es.oscars.oscars.TeardownPathResponseContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnacceptableTerminationTimeFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "listRequest".equals(typeName)){
                   
                            return  net.es.oscars.oscars.ListRequest.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oscars.es.net/OSCARS".equals(namespaceURI) &&
                  "msgDetails".equals(typeName)){
                   
                            return  net.es.oscars.oscars.MsgDetails.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ogf.org/schema/network/topology/ctrlPlane/20080828/".equals(namespaceURI) &&
                  "TimeContent".equals(typeName)){
                   
                            return  org.ogf.schema.network.topology.ctrlplane._20080828.TimeContent.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "InvalidFilterFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://docs.oasis-open.org/wsn/b-2".equals(namespaceURI) &&
                  "UnableToCreatePullPointFaultType".equals(typeName)){
                   
                            return  org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    