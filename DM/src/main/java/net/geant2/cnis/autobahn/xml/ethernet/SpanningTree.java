
package net.geant2.cnis.autobahn.xml.ethernet;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Reference;


/**
 * <p>Java class for SpanningTree complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpanningTree">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="refToLink" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *         &lt;element name="refToVlan" type="{http://cnis.geant2.net/autobahn/xml/common}Reference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cost" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpanningTree", propOrder = {
    "refToLink",
    "refToVlan"
})
public class SpanningTree {

    @XmlElement(required = true)
    protected Reference refToLink;
    @XmlElement(required = true)
    protected Reference refToVlan;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "cost")
    protected BigInteger cost;

    /**
     * Gets the value of the refToLink property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getRefToLink() {
        return refToLink;
    }

    /**
     * Sets the value of the refToLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setRefToLink(Reference value) {
        this.refToLink = value;
    }

    /**
     * Gets the value of the refToVlan property.
     * 
     * @return
     *     possible object is
     *     {@link Reference }
     *     
     */
    public Reference getRefToVlan() {
        return refToVlan;
    }

    /**
     * Sets the value of the refToVlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reference }
     *     
     */
    public void setRefToVlan(Reference value) {
        this.refToVlan = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCost(BigInteger value) {
        this.cost = value;
    }

}
