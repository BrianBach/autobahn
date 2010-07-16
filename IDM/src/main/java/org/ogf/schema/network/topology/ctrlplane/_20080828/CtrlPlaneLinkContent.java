
package org.ogf.schema.network.topology.ctrlplane._20080828;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneLinkContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneLinkContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="remoteLinkId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trafficEngineeringMetric" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maximumReservableCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minimumReservableCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="granularity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unreservedCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkProtectionTypes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="administrativeGroups" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneAdministrativeGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SwitchingCapabilityDescriptors" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneSwcapContent"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtrlPlaneLinkContent", propOrder = {
    "remoteLinkId",
    "trafficEngineeringMetric",
    "capacity",
    "maximumReservableCapacity",
    "minimumReservableCapacity",
    "granularity",
    "unreservedCapacity",
    "linkProtectionTypes",
    "administrativeGroups",
    "switchingCapabilityDescriptors"
})
public class CtrlPlaneLinkContent {

    protected String remoteLinkId;
    @XmlElement(required = true)
    protected String trafficEngineeringMetric;
    protected String capacity;
    protected String maximumReservableCapacity;
    protected String minimumReservableCapacity;
    protected String granularity;
    protected String unreservedCapacity;
    protected List<String> linkProtectionTypes;
    protected List<CtrlPlaneAdministrativeGroup> administrativeGroups;
    @XmlElement(name = "SwitchingCapabilityDescriptors", required = true)
    protected CtrlPlaneSwcapContent switchingCapabilityDescriptors;
    @XmlAttribute(required = true)
    protected String id;

    /**
     * Gets the value of the remoteLinkId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteLinkId() {
        return remoteLinkId;
    }

    /**
     * Sets the value of the remoteLinkId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteLinkId(String value) {
        this.remoteLinkId = value;
    }

    /**
     * Gets the value of the trafficEngineeringMetric property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrafficEngineeringMetric() {
        return trafficEngineeringMetric;
    }

    /**
     * Sets the value of the trafficEngineeringMetric property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrafficEngineeringMetric(String value) {
        this.trafficEngineeringMetric = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacity(String value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the maximumReservableCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximumReservableCapacity() {
        return maximumReservableCapacity;
    }

    /**
     * Sets the value of the maximumReservableCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximumReservableCapacity(String value) {
        this.maximumReservableCapacity = value;
    }

    /**
     * Gets the value of the minimumReservableCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinimumReservableCapacity() {
        return minimumReservableCapacity;
    }

    /**
     * Sets the value of the minimumReservableCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinimumReservableCapacity(String value) {
        this.minimumReservableCapacity = value;
    }

    /**
     * Gets the value of the granularity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGranularity() {
        return granularity;
    }

    /**
     * Sets the value of the granularity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGranularity(String value) {
        this.granularity = value;
    }

    /**
     * Gets the value of the unreservedCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnreservedCapacity() {
        return unreservedCapacity;
    }

    /**
     * Sets the value of the unreservedCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnreservedCapacity(String value) {
        this.unreservedCapacity = value;
    }

    /**
     * Gets the value of the linkProtectionTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the linkProtectionTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinkProtectionTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLinkProtectionTypes() {
        if (linkProtectionTypes == null) {
            linkProtectionTypes = new ArrayList<String>();
        }
        return this.linkProtectionTypes;
    }

    /**
     * Gets the value of the administrativeGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the administrativeGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdministrativeGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneAdministrativeGroup }
     * 
     * 
     */
    public List<CtrlPlaneAdministrativeGroup> getAdministrativeGroups() {
        if (administrativeGroups == null) {
            administrativeGroups = new ArrayList<CtrlPlaneAdministrativeGroup>();
        }
        return this.administrativeGroups;
    }

    /**
     * Gets the value of the switchingCapabilityDescriptors property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneSwcapContent }
     *     
     */
    public CtrlPlaneSwcapContent getSwitchingCapabilityDescriptors() {
        return switchingCapabilityDescriptors;
    }

    /**
     * Sets the value of the switchingCapabilityDescriptors property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneSwcapContent }
     *     
     */
    public void setSwitchingCapabilityDescriptors(CtrlPlaneSwcapContent value) {
        this.switchingCapabilityDescriptors = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
