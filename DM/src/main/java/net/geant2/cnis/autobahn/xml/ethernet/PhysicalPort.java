
package net.geant2.cnis.autobahn.xml.ethernet;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.AdministrativeStatus;
import net.geant2.cnis.autobahn.xml.common.Tags;


/**
 * <p>Java class for PhysicalPort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhysicalPort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://cnis.geant2.net/autobahn/xml/ethernet}PortType" minOccurs="0"/>
 *         &lt;element ref="{http://cnis.geant2.net/autobahn/xml/common}tags"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macAddress" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="status" type="{http://cnis.geant2.net/autobahn/xml/common}AdministrativeStatus" />
 *       &lt;attribute name="duplex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mdi" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="group" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhysicalPort", propOrder = {
    "description",
    "type",
    "tags"
})
public class PhysicalPort {

    protected String description;
    protected PortType type;
    @XmlElement(namespace = "http://cnis.geant2.net/autobahn/xml/common", required = true)
    protected Tags tags;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "macAddress", required = true)
    protected String macAddress;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "bandwidth")
    protected BigInteger bandwidth;
    @XmlAttribute(name = "status")
    protected AdministrativeStatus status;
    @XmlAttribute(name = "duplex")
    protected String duplex;
    @XmlAttribute(name = "mdi")
    protected Boolean mdi;
    @XmlAttribute(name = "group")
    protected String group;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PortType }
     *     
     */
    public PortType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortType }
     *     
     */
    public void setType(PortType value) {
        this.type = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link Tags }
     *     
     */
    public Tags getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tags }
     *     
     */
    public void setTags(Tags value) {
        this.tags = value;
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

    /**
     * Gets the value of the macAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets the value of the macAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMacAddress(String value) {
        this.macAddress = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the bandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the value of the bandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBandwidth(BigInteger value) {
        this.bandwidth = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeStatus }
     *     
     */
    public AdministrativeStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeStatus }
     *     
     */
    public void setStatus(AdministrativeStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the duplex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplex() {
        return duplex;
    }

    /**
     * Sets the value of the duplex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplex(String value) {
        this.duplex = value;
    }

    /**
     * Gets the value of the mdi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMdi() {
        return mdi;
    }

    /**
     * Sets the value of the mdi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMdi(Boolean value) {
        this.mdi = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup(String value) {
        this.group = value;
    }

}
