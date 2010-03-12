
package org.apache.cxf.ws.addressing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferenceParametersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReferenceParametersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://oscars.es.net/OSCARS}subscriptionId" minOccurs="0"/>
 *         &lt;element ref="{http://oscars.es.net/OSCARS}publisherRegistrationId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceParametersType", propOrder = {
    "subscriptionId",
    "publisherRegistrationId"
})
public class ReferenceParametersType {

    @XmlElement(namespace = "http://oscars.es.net/OSCARS")
    protected String subscriptionId;
    @XmlElement(namespace = "http://oscars.es.net/OSCARS")
    protected String publisherRegistrationId;

    /**
     * Gets the value of the subscriptionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets the value of the subscriptionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionId(String value) {
        this.subscriptionId = value;
    }

    /**
     * Gets the value of the publisherRegistrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisherRegistrationId() {
        return publisherRegistrationId;
    }

    /**
     * Sets the value of the publisherRegistrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisherRegistrationId(String value) {
        this.publisherRegistrationId = value;
    }

}
