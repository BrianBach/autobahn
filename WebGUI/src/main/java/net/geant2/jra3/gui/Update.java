
package net.geant2.jra3.gui;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.jra3.administration.KeyValue;


/**
 * <p>Java class for update complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="update">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="event" type="{http://gui.jra3.geant2.net/}eventType" minOccurs="0"/>
 *         &lt;element name="properties" type="{administration.jra3.geant2.net}KeyValue" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "update", propOrder = {
    "idm",
    "event",
    "properties"
})
public class Update {

    protected String idm;
    protected EventType event;
    @XmlElement(required = true)
    protected List<KeyValue> properties;

    /**
     * Gets the value of the idm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdm() {
        return idm;
    }

    /**
     * Sets the value of the idm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdm(String value) {
        this.idm = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setEvent(EventType value) {
        this.event = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValue }
     * 
     * 
     */
    public List<KeyValue> getProperties() {
        if (properties == null) {
            properties = new ArrayList<KeyValue>();
        }
        return this.properties;
    }

}
