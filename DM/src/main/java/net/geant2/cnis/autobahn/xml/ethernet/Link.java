
package net.geant2.cnis.autobahn.xml.ethernet;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Link complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Link">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startNode" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Node"/>
 *         &lt;element name="startPort" type="{http://cnis.geant2.net/autobahn/xml/ethernet}PhysicalPort"/>
 *         &lt;element name="endNode" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Node"/>
 *         &lt;element name="endPort" type="{http://cnis.geant2.net/autobahn/xml/ethernet}PhysicalPort"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="direction" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="discoveryProtocol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isL2Boundary" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isTrunk" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="nativeVlan" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="propagationDelay" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="protection" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Link", propOrder = {
    "startNode",
    "startPort",
    "endNode",
    "endPort"
})
public class Link {

    @XmlElement(required = true)
    protected Node startNode;
    @XmlElement(required = true)
    protected PhysicalPort startPort;
    @XmlElement(required = true)
    protected Node endNode;
    @XmlElement(required = true)
    protected PhysicalPort endPort;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "bandwidth")
    protected BigInteger bandwidth;
    @XmlAttribute(name = "direction")
    protected String direction;
    @XmlAttribute(name = "discoveryProtocol")
    protected String discoveryProtocol;
    @XmlAttribute(name = "isL2Boundary")
    protected Boolean isL2Boundary;
    @XmlAttribute(name = "isTrunk")
    protected Boolean isTrunk;
    @XmlAttribute(name = "nativeVlan")
    protected BigInteger nativeVlan;
    @XmlAttribute(name = "propagationDelay")
    protected BigDecimal propagationDelay;
    @XmlAttribute(name = "protection")
    protected Boolean protection;

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
     *     {@link PhysicalPort }
     *     
     */
    public PhysicalPort getStartPort() {
        return startPort;
    }

    /**
     * Sets the value of the startPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalPort }
     *     
     */
    public void setStartPort(PhysicalPort value) {
        this.startPort = value;
    }

    /**
     * Gets the value of the endNode property.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getEndNode() {
        return endNode;
    }

    /**
     * Sets the value of the endNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setEndNode(Node value) {
        this.endNode = value;
    }

    /**
     * Gets the value of the endPort property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalPort }
     *     
     */
    public PhysicalPort getEndPort() {
        return endPort;
    }

    /**
     * Sets the value of the endPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalPort }
     *     
     */
    public void setEndPort(PhysicalPort value) {
        this.endPort = value;
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
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

    /**
     * Gets the value of the discoveryProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscoveryProtocol() {
        return discoveryProtocol;
    }

    /**
     * Sets the value of the discoveryProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscoveryProtocol(String value) {
        this.discoveryProtocol = value;
    }

    /**
     * Gets the value of the isL2Boundary property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsL2Boundary() {
        return isL2Boundary;
    }

    /**
     * Sets the value of the isL2Boundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsL2Boundary(Boolean value) {
        this.isL2Boundary = value;
    }

    /**
     * Gets the value of the isTrunk property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTrunk() {
        return isTrunk;
    }

    /**
     * Sets the value of the isTrunk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTrunk(Boolean value) {
        this.isTrunk = value;
    }

    /**
     * Gets the value of the nativeVlan property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNativeVlan() {
        return nativeVlan;
    }

    /**
     * Sets the value of the nativeVlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNativeVlan(BigInteger value) {
        this.nativeVlan = value;
    }

    /**
     * Gets the value of the propagationDelay property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPropagationDelay() {
        return propagationDelay;
    }

    /**
     * Sets the value of the propagationDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPropagationDelay(BigDecimal value) {
        this.propagationDelay = value;
    }

    /**
     * Gets the value of the protection property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProtection() {
        return protection;
    }

    /**
     * Sets the value of the protection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProtection(Boolean value) {
        this.protection = value;
    }

}
