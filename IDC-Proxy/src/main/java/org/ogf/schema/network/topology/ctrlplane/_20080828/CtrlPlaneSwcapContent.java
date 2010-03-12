
package org.ogf.schema.network.topology.ctrlplane._20080828;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneSwcapContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneSwcapContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="switchingcapType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encodingType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="switchingCapabilitySpecificInfo" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneSwitchingCapabilitySpecificInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtrlPlaneSwcapContent", propOrder = {
    "switchingcapType",
    "encodingType",
    "switchingCapabilitySpecificInfo"
})
public class CtrlPlaneSwcapContent {

    @XmlElement(required = true)
    protected String switchingcapType;
    @XmlElement(required = true)
    protected String encodingType;
    @XmlElement(required = true)
    protected CtrlPlaneSwitchingCapabilitySpecificInfo switchingCapabilitySpecificInfo;

    /**
     * Gets the value of the switchingcapType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchingcapType() {
        return switchingcapType;
    }

    /**
     * Sets the value of the switchingcapType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchingcapType(String value) {
        this.switchingcapType = value;
    }

    /**
     * Gets the value of the encodingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodingType() {
        return encodingType;
    }

    /**
     * Sets the value of the encodingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodingType(String value) {
        this.encodingType = value;
    }

    /**
     * Gets the value of the switchingCapabilitySpecificInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneSwitchingCapabilitySpecificInfo }
     *     
     */
    public CtrlPlaneSwitchingCapabilitySpecificInfo getSwitchingCapabilitySpecificInfo() {
        return switchingCapabilitySpecificInfo;
    }

    /**
     * Sets the value of the switchingCapabilitySpecificInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneSwitchingCapabilitySpecificInfo }
     *     
     */
    public void setSwitchingCapabilitySpecificInfo(CtrlPlaneSwitchingCapabilitySpecificInfo value) {
        this.switchingCapabilitySpecificInfo = value;
    }

}
