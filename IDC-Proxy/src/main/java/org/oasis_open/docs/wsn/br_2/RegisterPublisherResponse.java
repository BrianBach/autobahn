
package org.oasis_open.docs.wsn.br_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PublisherRegistrationReference" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType"/>
 *         &lt;element name="ConsumerReference" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "publisherRegistrationReference",
    "consumerReference"
})
@XmlRootElement(name = "RegisterPublisherResponse")
public class RegisterPublisherResponse {

    @XmlElement(name = "PublisherRegistrationReference", required = true)
    protected EndpointReferenceType publisherRegistrationReference;
    @XmlElement(name = "ConsumerReference")
    protected EndpointReferenceType consumerReference;

    /**
     * Gets the value of the publisherRegistrationReference property.
     * 
     * @return
     *     possible object is
     *     {@link EndpointReferenceType }
     *     
     */
    public EndpointReferenceType getPublisherRegistrationReference() {
        return publisherRegistrationReference;
    }

    /**
     * Sets the value of the publisherRegistrationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointReferenceType }
     *     
     */
    public void setPublisherRegistrationReference(EndpointReferenceType value) {
        this.publisherRegistrationReference = value;
    }

    /**
     * Gets the value of the consumerReference property.
     * 
     * @return
     *     possible object is
     *     {@link EndpointReferenceType }
     *     
     */
    public EndpointReferenceType getConsumerReference() {
        return consumerReference;
    }

    /**
     * Sets the value of the consumerReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointReferenceType }
     *     
     */
    public void setConsumerReference(EndpointReferenceType value) {
        this.consumerReference = value;
    }

}
