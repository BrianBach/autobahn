
package net.geant2.jra3.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="bodID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="technology" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bundled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="node" type="{network.jra3.geant2.net}Node" minOccurs="0"/>
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
    "bodID",
    "address",
    "technology",
    "bundled",
    "node"
})
public class Port implements Serializable{

    protected String bodID;
    protected String address;
    protected String technology;
    protected boolean bundled;
    protected Node node;

    /**
     * Gets the value of the bodID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets the value of the bodID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodID(String value) {
        this.bodID = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the technology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnology() {
        return technology;
    }

    /**
     * Sets the value of the technology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnology(String value) {
        this.technology = value;
    }

    /**
     * Gets the value of the bundled property.
     * 
     */
    public boolean isBundled() {
        return bundled;
    }

    /**
     * Sets the value of the bundled property.
     * 
     */
    public void setBundled(boolean value) {
        this.bundled = value;
    }

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setNode(Node value) {
        this.node = value;
    }

}
