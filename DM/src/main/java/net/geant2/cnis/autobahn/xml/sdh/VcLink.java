
package net.geant2.cnis.autobahn.xml.sdh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Reference;


/**
 * <p>Java class for vcLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vcLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="ingressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="egressRef" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timeslots" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vcLink", propOrder = {
    "typeRef",
    "ingressRef",
    "egressRef"
})
public class VcLink {

    @XmlElement(required = true)
    protected Reference typeRef;
    @XmlElement(required = true)
    protected Reference ingressRef;
    @XmlElement(required = true)
    protected Reference egressRef;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "timeslots", required = true)
    protected int timeslots;

    /**
     * Gets the value of the typeRef property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getTypeRef() {
        return typeRef;
    }

    /**
     * Sets the value of the typeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setTypeRef(Reference value) {
        this.typeRef = value;
    }

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
     * Gets the value of the timeslots property.
     * 
     */
    public int getTimeslots() {
        return timeslots;
    }

    /**
     * Sets the value of the timeslots property.
     * 
     */
    public void setTimeslots(int value) {
        this.timeslots = value;
    }

}
