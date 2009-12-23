
package net.geant2.cnis.autobahn.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CnisToAutobahnResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CnisToAutobahnResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ethTopology" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Topology" minOccurs="0"/>
 *         &lt;element name="sdhTopology" type="{http://cnis.geant2.net/autobahn/xml/sdh}Topology" minOccurs="0"/>
 *         &lt;element name="status" type="{http://cnis.geant2.net/autobahn/xml}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CnisToAutobahnResponse", propOrder = {
    "ethTopology",
    "sdhTopology",
    "status"
})
public class CnisToAutobahnResponse {

    protected net.geant2.cnis.autobahn.xml.ethernet.Topology ethTopology;
    protected net.geant2.cnis.autobahn.xml.sdh.Topology sdhTopology;
    @XmlElement(required = true)
    protected Status status;

    /**
     * Gets the value of the ethTopology property.
     * 
     * @return
     *     possible object is
     *     {@link net.geant2.cnis.autobahn.xml.ethernet.Topology }
     *     
     */
    public net.geant2.cnis.autobahn.xml.ethernet.Topology getEthTopology() {
        return ethTopology;
    }

    /**
     * Sets the value of the ethTopology property.
     * 
     * @param value
     *     allowed object is
     *     {@link net.geant2.cnis.autobahn.xml.ethernet.Topology }
     *     
     */
    public void setEthTopology(net.geant2.cnis.autobahn.xml.ethernet.Topology value) {
        this.ethTopology = value;
    }

    /**
     * Gets the value of the sdhTopology property.
     * 
     * @return
     *     possible object is
     *     {@link net.geant2.cnis.autobahn.xml.sdh.Topology }
     *     
     */
    public net.geant2.cnis.autobahn.xml.sdh.Topology getSdhTopology() {
        return sdhTopology;
    }

    /**
     * Sets the value of the sdhTopology property.
     * 
     * @param value
     *     allowed object is
     *     {@link net.geant2.cnis.autobahn.xml.sdh.Topology }
     *     
     */
    public void setSdhTopology(net.geant2.cnis.autobahn.xml.sdh.Topology value) {
        this.sdhTopology = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

}
