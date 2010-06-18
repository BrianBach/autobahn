
package net.geant.autobahn.intradomain.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InterfaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterfaceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="switchingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataEncodingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceType", propOrder = {
    "switchingType",
    "dataEncodingType"
})
public class InterfaceType {

    protected String switchingType;
    protected String dataEncodingType;

    /**
     * Gets the value of the switchingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchingType() {
        return switchingType;
    }

    /**
     * Sets the value of the switchingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchingType(String value) {
        this.switchingType = value;
    }

    /**
     * Gets the value of the dataEncodingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEncodingType() {
        return dataEncodingType;
    }

    /**
     * Sets the value of the dataEncodingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEncodingType(String value) {
        this.dataEncodingType = value;
    }

}
