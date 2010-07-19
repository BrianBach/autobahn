
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for layer3Info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="layer3Info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srcHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="protocol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srcIpPort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="destIpPort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dscp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layer3Info", propOrder = {
    "srcHost",
    "destHost",
    "protocol",
    "srcIpPort",
    "destIpPort",
    "dscp"
})
public class Layer3Info {

    @XmlElement(required = true)
    protected String srcHost;
    @XmlElement(required = true)
    protected String destHost;
    protected String protocol;
    protected Integer srcIpPort;
    protected Integer destIpPort;
    protected String dscp;

    /**
     * Gets the value of the srcHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcHost() {
        return srcHost;
    }

    /**
     * Sets the value of the srcHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcHost(String value) {
        this.srcHost = value;
    }

    /**
     * Gets the value of the destHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestHost() {
        return destHost;
    }

    /**
     * Sets the value of the destHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestHost(String value) {
        this.destHost = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets the value of the protocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocol(String value) {
        this.protocol = value;
    }

    /**
     * Gets the value of the srcIpPort property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSrcIpPort() {
        return srcIpPort;
    }

    /**
     * Sets the value of the srcIpPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSrcIpPort(Integer value) {
        this.srcIpPort = value;
    }

    /**
     * Gets the value of the destIpPort property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDestIpPort() {
        return destIpPort;
    }

    /**
     * Sets the value of the destIpPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDestIpPort(Integer value) {
        this.destIpPort = value;
    }

    /**
     * Gets the value of the dscp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDscp() {
        return dscp;
    }

    /**
     * Sets the value of the dscp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDscp(String value) {
        this.dscp = value;
    }

}
