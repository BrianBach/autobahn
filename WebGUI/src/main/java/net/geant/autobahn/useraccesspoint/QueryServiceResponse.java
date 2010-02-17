
package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceResponse" type="{useraccesspoint.autobahn.geant.net}ServiceResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryServiceResponse", propOrder = {
    "serviceResponse"
})
public class QueryServiceResponse {

    @XmlElement(name = "ServiceResponse")
    protected ServiceResponse serviceResponse;

    /**
     * Gets the value of the serviceResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceResponse }
     *     
     */
    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    /**
     * Sets the value of the serviceResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceResponse }
     *     
     */
    public void setServiceResponse(ServiceResponse value) {
        this.serviceResponse = value;
    }

}
