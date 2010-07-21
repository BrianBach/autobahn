
package org.ogf.schema.network.topology.ctrlplane._20080828;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlanePortContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlanePortContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lifetime" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}Lifetime" minOccurs="0"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maximumReservableCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minimumReservableCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="granularity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unreservedCapacity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}link" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "CtrlPlanePortContent", propOrder = {
    "lifetime",
    "capacity",
    "maximumReservableCapacity",
    "minimumReservableCapacity",
    "granularity",
    "unreservedCapacity",
    "link"
})
public class CtrlPlanePortContent {

    protected Lifetime lifetime;
    protected String capacity;
    protected String maximumReservableCapacity;
    protected String minimumReservableCapacity;
    protected String granularity;
    protected String unreservedCapacity;
    protected List<CtrlPlaneLinkContent> link;
    @XmlAttribute(required = true)
    protected String id;

    /**
     * Gets the value of the lifetime property.
     * 
     * @return
     *     possible object is
     *     {@link Lifetime }
     *     
     */
    public Lifetime getLifetime() {
        return lifetime;
    }

    /**
     * Manually added method
     * @param param org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent
     */
     public void addLink(org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneLinkContent param){
         if (link == null) {
             link = new ArrayList<CtrlPlaneLinkContent>();
         }
    
         //update the setting tracker
        //localLinkTracker = true;
    

        link.add(param);
     }
    /**
     * Sets the value of the lifetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lifetime }
     *     
     */
    public void setLifetime(Lifetime value) {
        this.lifetime = value;
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
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneLinkContent }
     * 
     * 
     */
    public List<CtrlPlaneLinkContent> getLink() {
        if (link == null) {
            link = new ArrayList<CtrlPlaneLinkContent>();
        }
        return this.link;
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
