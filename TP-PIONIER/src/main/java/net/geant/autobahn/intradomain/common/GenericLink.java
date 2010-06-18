
package net.geant.autobahn.intradomain.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenericLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="version" type="{common.intradomain.autobahn.geant.net}VersionInfo" minOccurs="0"/>
 *         &lt;element name="startInterface" type="{common.intradomain.autobahn.geant.net}GenericInterface" minOccurs="0"/>
 *         &lt;element name="endInterface" type="{common.intradomain.autobahn.geant.net}GenericInterface" minOccurs="0"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protection" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="propDelay" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericLink", propOrder = {
    "version",
    "startInterface",
    "endInterface",
    "direction",
    "protection",
    "propDelay"
})
public class GenericLink {

    protected VersionInfo version;
    protected GenericInterface startInterface;
    protected GenericInterface endInterface;
    protected String direction;
    protected boolean protection;
    protected double propDelay;

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link VersionInfo }
     *     
     */
    public VersionInfo getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link VersionInfo }
     *     
     */
    public void setVersion(VersionInfo value) {
        this.version = value;
    }

    /**
     * Gets the value of the startInterface property.
     * 
     * @return
     *     possible object is
     *     {@link GenericInterface }
     *     
     */
    public GenericInterface getStartInterface() {
        return startInterface;
    }

    /**
     * Sets the value of the startInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericInterface }
     *     
     */
    public void setStartInterface(GenericInterface value) {
        this.startInterface = value;
    }

    /**
     * Gets the value of the endInterface property.
     * 
     * @return
     *     possible object is
     *     {@link GenericInterface }
     *     
     */
    public GenericInterface getEndInterface() {
        return endInterface;
    }

    /**
     * Sets the value of the endInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericInterface }
     *     
     */
    public void setEndInterface(GenericInterface value) {
        this.endInterface = value;
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
     * Gets the value of the protection property.
     * 
     */
    public boolean isProtection() {
        return protection;
    }

    /**
     * Sets the value of the protection property.
     * 
     */
    public void setProtection(boolean value) {
        this.protection = value;
    }

    /**
     * Gets the value of the propDelay property.
     * 
     */
    public double getPropDelay() {
        return propDelay;
    }

    /**
     * Sets the value of the propDelay property.
     * 
     */
    public void setPropDelay(double value) {
        this.propDelay = value;
    }

}
