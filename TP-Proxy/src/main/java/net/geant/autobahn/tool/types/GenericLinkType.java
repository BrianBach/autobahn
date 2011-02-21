
package net.geant.autobahn.tool.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenericLinkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericLinkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startInterface" type="{types.tool.autobahn.geant.net}GenericInterfaceType" minOccurs="0"/>
 *         &lt;element name="endInterface" type="{types.tool.autobahn.geant.net}GenericInterfaceType" minOccurs="0"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericLinkType", propOrder = {
    "startInterface",
    "endInterface",
    "direction"
})
public class GenericLinkType {

    protected GenericInterfaceType startInterface;
    protected GenericInterfaceType endInterface;
    protected String direction;

    /**
     * Gets the value of the startInterface property.
     * 
     * @return
     *     possible object is
     *     {@link GenericInterfaceType }
     *     
     */
    public GenericInterfaceType getStartInterface() {
        return startInterface;
    }

    /**
     * Sets the value of the startInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericInterfaceType }
     *     
     */
    public void setStartInterface(GenericInterfaceType value) {
        this.startInterface = value;
    }

    /**
     * Gets the value of the endInterface property.
     * 
     * @return
     *     possible object is
     *     {@link GenericInterfaceType }
     *     
     */
    public GenericInterfaceType getEndInterface() {
        return endInterface;
    }

    /**
     * Sets the value of the endInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericInterfaceType }
     *     
     */
    public void setEndInterface(GenericInterfaceType value) {
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

}
