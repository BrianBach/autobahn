
package net.geant2.cnis.autobahn.xml.sdh;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Domain;


/**
 * <p>Java class for IDLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IDLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startNode" type="{http://cnis.geant2.net/autobahn/xml/sdh}node"/>
 *         &lt;element name="startPort" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyInterface"/>
 *         &lt;element name="externalDomain" type="{http://cnis.geant2.net/autobahn/xml/common}Domain"/>
 *       &lt;/sequence>
 *       &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="endPortId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDLink", propOrder = {
    "startNode",
    "startPort",
    "externalDomain"
})
public class IDLink {

    @XmlElement(required = true)
    protected Node startNode;
    @XmlElement(required = true)
    protected PhyInterface startPort;
    @XmlElement(required = true)
    protected Domain externalDomain;
    @XmlAttribute
    protected BigInteger bandwidth;
    @XmlAttribute
    protected String endPortId;

    /**
     * Gets the value of the startNode property.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * Sets the value of the startNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setStartNode(Node value) {
        this.startNode = value;
    }

    /**
     * Gets the value of the startPort property.
     * 
     * @return
     *     possible object is
     *     {@link PhyInterface }
     *     
     */
    public PhyInterface getStartPort() {
        return startPort;
    }

    /**
     * Sets the value of the startPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhyInterface }
     *     
     */
    public void setStartPort(PhyInterface value) {
        this.startPort = value;
    }

    /**
     * Gets the value of the externalDomain property.
     * 
     * @return
     *     possible object is
     *     {@link Domain }
     *     
     */
    public Domain getExternalDomain() {
        return externalDomain;
    }

    /**
     * Sets the value of the externalDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link Domain }
     *     
     */
    public void setExternalDomain(Domain value) {
        this.externalDomain = value;
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
     * Gets the value of the endPortId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndPortId() {
        return endPortId;
    }

    /**
     * Sets the value of the endPortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndPortId(String value) {
        this.endPortId = value;
    }

}
