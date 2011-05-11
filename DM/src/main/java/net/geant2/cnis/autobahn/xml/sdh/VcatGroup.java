
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
 * <p>Java class for vcatGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vcatGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ingressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="egressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="trails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="trailRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference" maxOccurs="unbounded" minOccurs="2"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vcatGroup", propOrder = {
    "ingressRef",
    "egressRef",
    "trails"
})
public class VcatGroup {

    @XmlElement(required = true)
    protected Reference ingressRef;
    @XmlElement(required = true)
    protected Reference egressRef;
    @XmlElement(required = true)
    protected VcatGroup.Trails trails;
    @XmlAttribute(name = "name", required = true)
    protected String name;

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
     * Gets the value of the trails property.
     * 
     * @return
     *     possible object is
     *     {@link VcatGroup.Trails }
     *     
     */
    public VcatGroup.Trails getTrails() {
        return trails;
    }

    /**
     * Sets the value of the trails property.
     * 
     * @param value
     *     allowed object is
     *     {@link VcatGroup.Trails }
     *     
     */
    public void setTrails(VcatGroup.Trails value) {
        this.trails = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="trailRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference" maxOccurs="unbounded" minOccurs="2"/>
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
        "trailRef"
    })
    public static class Trails {

        @XmlElement(required = true)
        protected List<Reference> trailRef;

        /**
         * Gets the value of the trailRef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the trailRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTrailRef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Reference }
         * 
         * 
         */
        public List<Reference> getTrailRef() {
            if (trailRef == null) {
                trailRef = new ArrayList<Reference>();
            }
            return this.trailRef;
        }

    }

}
