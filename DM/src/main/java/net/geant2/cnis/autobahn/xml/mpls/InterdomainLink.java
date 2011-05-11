
package net.geant2.cnis.autobahn.xml.mpls;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.Domain;


/**
 * <p>Java class for InterdomainLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterdomainLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startNode" type="{http://cnis.geant2.net/autobahn/xml/mpls}Node" minOccurs="0"/>
 *         &lt;element name="startPort" type="{http://cnis.geant2.net/autobahn/xml/mpls}Port" minOccurs="0"/>
 *         &lt;element name="externalDomain" type="{http://cnis.geant2.net/autobahn/xml/common}Domain" minOccurs="0"/>
 *         &lt;element name="externalPortId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterdomainLink", propOrder = {
    "id",
    "startNode",
    "startPort",
    "externalDomain",
    "externalPortId",
    "bandwidth"
})
public class InterdomainLink {

    protected String id;
    protected Node startNode;
    protected Port startPort;
    protected Domain externalDomain;
    protected String externalPortId;
    protected BigInteger bandwidth;

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
     * Gets the value of the externalPortId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalPortId() {
        return externalPortId;
    }

    /**
     * Sets the value of the externalPortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalPortId(String value) {
        this.externalPortId = value;
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

}
