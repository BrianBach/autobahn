
package net.geant2.cnis.autobahn.xml.mpls;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntradomainLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntradomainLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startNode" type="{http://cnis.geant2.net/autobahn/xml/mpls}Node" minOccurs="0"/>
 *         &lt;element name="startPort" type="{http://cnis.geant2.net/autobahn/xml/mpls}Port" minOccurs="0"/>
 *         &lt;element name="endNode" type="{http://cnis.geant2.net/autobahn/xml/mpls}Node" minOccurs="0"/>
 *         &lt;element name="endPort" type="{http://cnis.geant2.net/autobahn/xml/mpls}Port" minOccurs="0"/>
 *         &lt;element name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="resiliency" type="{http://cnis.geant2.net/autobahn/xml/mpls}resiliency" minOccurs="0"/>
 *         &lt;element name="vlan" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntradomainLink", propOrder = {
    "id",
    "startNode",
    "startPort",
    "endNode",
    "endPort",
    "bandwidth",
    "resiliency",
    "vlan"
})
public class IntradomainLink {

    protected String id;
    protected Node startNode;
    protected Port startPort;
    protected Node endNode;
    protected Port endPort;
    protected BigInteger bandwidth;
    protected Resiliency resiliency;
    protected BigInteger vlan;

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
     *     {@link Port }
     *     
     */
    public Port getStartPort() {
        return startPort;
    }

    /**
     * Sets the value of the startPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Port }
     *     
     */
    public void setStartPort(Port value) {
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
     *     {@link Port }
     *     
     */
    public Port getEndPort() {
        return endPort;
    }

    /**
     * Sets the value of the endPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Port }
     *     
     */
    public void setEndPort(Port value) {
        this.endPort = value;
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
     * Gets the value of the resiliency property.
     * 
     * @return
     *     possible object is
     *     {@link Resiliency }
     *     
     */
    public Resiliency getResiliency() {
        return resiliency;
    }

    /**
     * Sets the value of the resiliency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resiliency }
     *     
     */
    public void setResiliency(Resiliency value) {
        this.resiliency = value;
    }

    /**
     * Gets the value of the vlan property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVlan() {
        return vlan;
    }

    /**
     * Sets the value of the vlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVlan(BigInteger value) {
        this.vlan = value;
    }

}
