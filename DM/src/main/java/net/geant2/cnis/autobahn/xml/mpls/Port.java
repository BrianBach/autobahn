
package net.geant2.cnis.autobahn.xml.mpls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.AdministrativeStatus;
import net.geant2.cnis.autobahn.xml.common.Tags;


/**
 * <p>Java class for Port complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Port">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagged" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="status" type="{http://cnis.geant2.net/autobahn/xml/common}AdministrativeStatus" minOccurs="0"/>
 *         &lt;element ref="{http://cnis.geant2.net/autobahn/xml/common}tags"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Port", propOrder = {
    "id",
    "name",
    "description",
    "tagged",
    "status",
    "tags"
})
public class Port {

    protected String id;
    protected String name;
    protected String description;
    protected Boolean tagged;
    protected AdministrativeStatus status;
    @XmlElement(namespace = "http://cnis.geant2.net/autobahn/xml/common", required = true)
    protected Tags tags;

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
     * Gets the value of the tagged property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTagged() {
        return tagged;
    }

    /**
     * Sets the value of the tagged property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTagged(Boolean value) {
        this.tagged = value;
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

}
