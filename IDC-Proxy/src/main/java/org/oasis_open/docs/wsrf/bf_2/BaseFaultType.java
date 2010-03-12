
package org.oasis_open.docs.wsrf.bf_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;
import org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType;
import org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType;
import org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;
import org.oasis_open.docs.wsrf.r_2.ResourceUnavailableFaultType;
import org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType;


/**
 * <p>Java class for BaseFaultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseFaultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Originator" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType" minOccurs="0"/>
 *         &lt;element name="ErrorCode" type="{http://docs.oasis-open.org/wsrf/bf-2}ErrorCodeType" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FaultCause" type="{http://docs.oasis-open.org/wsrf/bf-2}FaultCauseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseFaultType", propOrder = {
    "timestamp",
    "originator",
    "errorCode",
    "description",
    "faultCause"
})
@XmlSeeAlso({
    TopicExpressionDialectUnknownFaultType.class,
    UnableToGetMessagesFaultType.class,
    ResumeFailedFaultType.class,
    InvalidProducerPropertiesExpressionFaultType.class,
    SubscribeCreationFailedFaultType.class,
    UnableToDestroySubscriptionFaultType.class,
    UnrecognizedPolicyRequestFaultType.class,
    NotifyMessageNotSupportedFaultType.class,
    UnableToCreatePullPointFaultType.class,
    UnacceptableInitialTerminationTimeFaultType.class,
    InvalidTopicExpressionFaultType.class,
    UnsupportedPolicyRequestFaultType.class,
    PauseFailedFaultType.class,
    InvalidMessageContentExpressionFaultType.class,
    UnableToDestroyPullPointFaultType.class,
    MultipleTopicsSpecifiedFaultType.class,
    NoCurrentMessageOnTopicFaultType.class,
    InvalidFilterFaultType.class,
    TopicNotSupportedFaultType.class,
    UnacceptableTerminationTimeFaultType.class,
    ResourceUnknownFaultType.class,
    ResourceUnavailableFaultType.class,
    PublisherRegistrationFailedFaultType.class,
    ResourceNotDestroyedFaultType.class,
    PublisherRegistrationRejectedFaultType.class
})
public class BaseFaultType {

    @XmlElement(name = "Timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "Originator")
    protected EndpointReferenceType originator;
    @XmlElement(name = "ErrorCode")
    protected ErrorCodeType errorCode;
    @XmlElement(name = "Description")
    protected List<String> description;
    @XmlElement(name = "FaultCause")
    protected FaultCauseType faultCause;

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the originator property.
     * 
     * @return
     *     possible object is
     *     {@link EndpointReferenceType }
     *     
     */
    public EndpointReferenceType getOriginator() {
        return originator;
    }

    /**
     * Sets the value of the originator property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointReferenceType }
     *     
     */
    public void setOriginator(EndpointReferenceType value) {
        this.originator = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorCodeType }
     *     
     */
    public ErrorCodeType getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorCodeType }
     *     
     */
    public void setErrorCode(ErrorCodeType value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDescription() {
        if (description == null) {
            description = new ArrayList<String>();
        }
        return this.description;
    }

    /**
     * Gets the value of the faultCause property.
     * 
     * @return
     *     possible object is
     *     {@link FaultCauseType }
     *     
     */
    public FaultCauseType getFaultCause() {
        return faultCause;
    }

    /**
     * Sets the value of the faultCause property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultCauseType }
     *     
     */
    public void setFaultCause(FaultCauseType value) {
        this.faultCause = value;
    }

}
