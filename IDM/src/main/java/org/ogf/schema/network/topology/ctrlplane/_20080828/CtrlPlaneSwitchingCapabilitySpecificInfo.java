
package org.ogf.schema.network.topology.ctrlplane._20080828;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneSwitchingCapabilitySpecificInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneSwitchingCapabilitySpecificInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="capability" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interfaceMTU" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vlanRangeAvailability" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suggestedVLANRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vlanTranslation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtrlPlaneSwitchingCapabilitySpecificInfo", propOrder = {
    "capability",
    "interfaceMTU",
    "vlanRangeAvailability",
    "suggestedVLANRange",
    "vlanTranslation"
})
public class CtrlPlaneSwitchingCapabilitySpecificInfo {

    protected String capability;
    protected Integer interfaceMTU;
    protected String vlanRangeAvailability;
    protected String suggestedVLANRange;
    protected Boolean vlanTranslation;

    /**
     * Gets the value of the capability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapability() {
        return capability;
    }

    /**
     * Sets the value of the capability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapability(String value) {
        this.capability = value;
    }

    /**
     * Gets the value of the interfaceMTU property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInterfaceMTU() {
        return interfaceMTU;
    }

    /**
     * Sets the value of the interfaceMTU property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInterfaceMTU(Integer value) {
        this.interfaceMTU = value;
    }

    /**
     * Gets the value of the vlanRangeAvailability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVlanRangeAvailability() {
        return vlanRangeAvailability;
    }

    /**
     * Sets the value of the vlanRangeAvailability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVlanRangeAvailability(String value) {
        this.vlanRangeAvailability = value;
    }

    /**
     * Gets the value of the suggestedVLANRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuggestedVLANRange() {
        return suggestedVLANRange;
    }

    /**
     * Sets the value of the suggestedVLANRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuggestedVLANRange(String value) {
        this.suggestedVLANRange = value;
    }

    /**
     * Gets the value of the vlanTranslation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVlanTranslation() {
        return vlanTranslation;
    }

    /**
     * Sets the value of the vlanTranslation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVlanTranslation(Boolean value) {
        this.vlanTranslation = value;
    }

}
