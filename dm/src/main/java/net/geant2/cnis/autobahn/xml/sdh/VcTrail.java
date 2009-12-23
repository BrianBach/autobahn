
package net.geant2.cnis.autobahn.xml.sdh;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Reference;


/**
 * <p>Java class for vcTrail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vcTrail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ingressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="egressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="links">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="linkRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference" maxOccurs="unbounded" minOccurs="2"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vcTrail", propOrder = {
    "ingressRef",
    "egressRef",
    "links",
    "description"
})
public class VcTrail {

    @XmlElement(required = true)
    protected Reference ingressRef;
    @XmlElement(required = true)
    protected Reference egressRef;
    @XmlElement(required = true)
    protected VcTrail.Links links;
    protected String description;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String id;

    /**
     * Gets the value of the ingressRef property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getIngressRef() {
        return ingressRef;
    }

    /**
     * Sets the value of the ingressRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setIngressRef(Reference value) {
        this.ingressRef = value;
    }

    /**
     * Gets the value of the egressRef property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getEgressRef() {
        return egressRef;
    }

    /**
     * Sets the value of the egressRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setEgressRef(Reference value) {
        this.egressRef = value;
    }

    /**
     * Gets the value of the links property.
     * 
     * @return
     *     possible object is
     *     {@link VcTrail.Links }
     *     
     */
    public VcTrail.Links getLinks() {
        return links;
    }

    /**
     * Sets the value of the links property.
     * 
     * @param value
     *     allowed object is
     *     {@link VcTrail.Links }
     *     
     */
    public void setLinks(VcTrail.Links value) {
        this.links = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="linkRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference" maxOccurs="unbounded" minOccurs="2"/>
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
        "linkRef"
    })
    public static class Links {

        @XmlElement(required = true)
        protected List<Reference> linkRef;

        /**
         * Gets the value of the linkRef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the linkRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLinkRef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Reference }
         * 
         * 
         */
        public List<Reference> getLinkRef() {
            if (linkRef == null) {
                linkRef = new ArrayList<Reference>();
            }
            return this.linkRef;
        }

    }

}
