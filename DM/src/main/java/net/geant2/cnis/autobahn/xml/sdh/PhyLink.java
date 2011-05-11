
package net.geant2.cnis.autobahn.xml.sdh;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.AdministrativeStatus;


/**
 * <p>Java class for phyLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="phyLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startNode" type="{http://cnis.geant2.net/autobahn/xml/sdh}node"/>
 *         &lt;element name="startInterface" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyInterface"/>
 *         &lt;element name="endNode" type="{http://cnis.geant2.net/autobahn/xml/sdh}node"/>
 *         &lt;element name="endInterface" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyInterface"/>
 *       &lt;/sequence>
 *       &lt;attribute name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="status" type="{http://cnis.geant2.net/autobahn/xml/common}AdministrativeStatus" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "phyLink", propOrder = {
    "startNode",
    "startInterface",
    "endNode",
    "endInterface"
})
public class PhyLink {

    @XmlElement(required = true)
    protected Node startNode;
    @XmlElement(required = true)
    protected PhyInterface startInterface;
    @XmlElement(required = true)
    protected Node endNode;
    @XmlElement(required = true)
    protected PhyInterface endInterface;
    @XmlAttribute(name = "bandwidth")
    protected BigInteger bandwidth;
    @XmlAttribute(name = "status")
    protected AdministrativeStatus status;

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
     * Gets the value of the startInterface property.
     * 
     * @return
     *     possible object is
     *     {@link PhyInterface }
     *     
     */
    public PhyInterface getStartInterface() {
        return startInterface;
    }

    /**
     * Sets the value of the startInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhyInterface }
     *     
     */
    public void setStartInterface(PhyInterface value) {
        this.startInterface = value;
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
     * Gets the value of the endInterface property.
     * 
     * @return
     *     possible object is
     *     {@link PhyInterface }
     *     
     */
    public PhyInterface getEndInterface() {
        return endInterface;
    }

    /**
     * Sets the value of the endInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhyInterface }
     *     
     */
    public void setEndInterface(PhyInterface value) {
        this.endInterface = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeStatus }
     *     
     */
    public AdministrativeStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeStatus }
     *     
     */
    public void setStatus(AdministrativeStatus value) {
        this.status = value;
    }

}
