
package net.es.oscars.oscars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="payload" type="{http://oscars.es.net/OSCARS}forwardPayload"/>
 *         &lt;element name="payloadSender" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "payload",
    "payloadSender"
})
@XmlRootElement(name = "forward")
public class Forward {

    @XmlElement(required = true)
    protected ForwardPayload payload;
    @XmlElement(required = true)
    protected String payloadSender;

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link ForwardPayload }
     *     
     */
    public ForwardPayload getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForwardPayload }
     *     
     */
    public void setPayload(ForwardPayload value) {
        this.payload = value;
    }

    /**
     * Gets the value of the payloadSender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayloadSender() {
        return payloadSender;
    }

    /**
     * Sets the value of the payloadSender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayloadSender(String value) {
        this.payloadSender = value;
    }

}
