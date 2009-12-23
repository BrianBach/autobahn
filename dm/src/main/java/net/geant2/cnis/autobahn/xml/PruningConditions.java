
package net.geant2.cnis.autobahn.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PruningConditions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PruningConditions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coreTechnology" type="{http://cnis.geant2.net/autobahn/xml}CoreTechnology" minOccurs="0"/>
 *         &lt;element name="taggedVlan" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vlanRange" type="{http://cnis.geant2.net/autobahn/xml}VlanRange" minOccurs="0"/>
 *         &lt;element name="linkDelay" type="{http://cnis.geant2.net/autobahn/xml}DecimalValue" minOccurs="0"/>
 *         &lt;element name="linkBandwidth" type="{http://cnis.geant2.net/autobahn/xml}IntegerValue" minOccurs="0"/>
 *         &lt;element name="interfaceMds" type="{http://cnis.geant2.net/autobahn/xml}DecimalValue" minOccurs="0"/>
 *         &lt;element name="interfaceRate" type="{http://cnis.geant2.net/autobahn/xml}DecimalValue" minOccurs="0"/>
 *         &lt;element name="InterfaceType" type="{http://cnis.geant2.net/autobahn/xml}InterfaceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PruningConditions", propOrder = {
    "domainName",
    "coreTechnology",
    "taggedVlan",
    "serviceName",
    "vlanRange",
    "linkDelay",
    "linkBandwidth",
    "interfaceMds",
    "interfaceRate",
    "interfaceType"
})
public class PruningConditions {

    protected String domainName;
    protected CoreTechnology coreTechnology;
    @XmlElement(defaultValue = "false")
    protected Boolean taggedVlan;
    protected String serviceName;
    protected VlanRange vlanRange;
    protected DecimalValue linkDelay;
    protected IntegerValue linkBandwidth;
    protected DecimalValue interfaceMds;
    protected DecimalValue interfaceRate;
    @XmlElement(name = "InterfaceType")
    protected InterfaceType interfaceType;

    /**
     * Gets the value of the domainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * Sets the value of the domainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainName(String value) {
        this.domainName = value;
    }

    /**
     * Gets the value of the coreTechnology property.
     * 
     * @return
     *     possible object is
     *     {@link CoreTechnology }
     *     
     */
    public CoreTechnology getCoreTechnology() {
        return coreTechnology;
    }

    /**
     * Sets the value of the coreTechnology property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoreTechnology }
     *     
     */
    public void setCoreTechnology(CoreTechnology value) {
        this.coreTechnology = value;
    }

    /**
     * Gets the value of the taggedVlan property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTaggedVlan() {
        return taggedVlan;
    }

    /**
     * Sets the value of the taggedVlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTaggedVlan(Boolean value) {
        this.taggedVlan = value;
    }

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the vlanRange property.
     * 
     * @return
     *     possible object is
     *     {@link VlanRange }
     *     
     */
    public VlanRange getVlanRange() {
        return vlanRange;
    }

    /**
     * Sets the value of the vlanRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link VlanRange }
     *     
     */
    public void setVlanRange(VlanRange value) {
        this.vlanRange = value;
    }

    /**
     * Gets the value of the linkDelay property.
     * 
     * @return
     *     possible object is
     *     {@link DecimalValue }
     *     
     */
    public DecimalValue getLinkDelay() {
        return linkDelay;
    }

    /**
     * Sets the value of the linkDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecimalValue }
     *     
     */
    public void setLinkDelay(DecimalValue value) {
        this.linkDelay = value;
    }

    /**
     * Gets the value of the linkBandwidth property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerValue }
     *     
     */
    public IntegerValue getLinkBandwidth() {
        return linkBandwidth;
    }

    /**
     * Sets the value of the linkBandwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerValue }
     *     
     */
    public void setLinkBandwidth(IntegerValue value) {
        this.linkBandwidth = value;
    }

    /**
     * Gets the value of the interfaceMds property.
     * 
     * @return
     *     possible object is
     *     {@link DecimalValue }
     *     
     */
    public DecimalValue getInterfaceMds() {
        return interfaceMds;
    }

    /**
     * Sets the value of the interfaceMds property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecimalValue }
     *     
     */
    public void setInterfaceMds(DecimalValue value) {
        this.interfaceMds = value;
    }

    /**
     * Gets the value of the interfaceRate property.
     * 
     * @return
     *     possible object is
     *     {@link DecimalValue }
     *     
     */
    public DecimalValue getInterfaceRate() {
        return interfaceRate;
    }

    /**
     * Sets the value of the interfaceRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DecimalValue }
     *     
     */
    public void setInterfaceRate(DecimalValue value) {
        this.interfaceRate = value;
    }

    /**
     * Gets the value of the interfaceType property.
     * 
     * @return
     *     possible object is
     *     {@link InterfaceType }
     *     
     */
    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    /**
     * Sets the value of the interfaceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterfaceType }
     *     
     */
    public void setInterfaceType(InterfaceType value) {
        this.interfaceType = value;
    }

}
