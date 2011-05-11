
package net.geant2.cnis.autobahn.xml.ethernet;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Reference;


/**
 * <p>Java class for Vlan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vlan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="refToDomain" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vlan", propOrder = {
    "refToDomain"
})
public class Vlan {

    @XmlElement(required = true)
    protected Reference refToDomain;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "number")
    protected BigInteger number;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the refToDomain property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getRefToDomain() {
        return refToDomain;
    }

    /**
     * Sets the value of the refToDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setRefToDomain(Reference value) {
        this.refToDomain = value;
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
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumber(BigInteger value) {
        this.number = value;
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

}
