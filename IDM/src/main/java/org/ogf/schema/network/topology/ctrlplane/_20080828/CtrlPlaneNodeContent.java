
package org.ogf.schema.network.topology.ctrlplane._20080828;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneNodeContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneNodeContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lifetime" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}Lifetime" minOccurs="0"/>
 *         &lt;element name="address" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneAddressContent" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}port" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtrlPlaneNodeContent", propOrder = {
    "lifetime",
    "address",
    "port"
})
public class CtrlPlaneNodeContent {

    protected Lifetime lifetime;
    protected CtrlPlaneAddressContent address;
    protected List<CtrlPlanePortContent> port;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the lifetime property.
     * 
     * @return
     *     possible object is
     *     {@link Lifetime }
     *     
     */
    public Lifetime getLifetime() {
        return lifetime;
    }

    /**
     * Sets the value of the lifetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lifetime }
     *     
     */
    public void setLifetime(Lifetime value) {
        this.lifetime = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneAddressContent }
     *     
     */
    public CtrlPlaneAddressContent getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneAddressContent }
     *     
     */
    public void setAddress(CtrlPlaneAddressContent value) {
        this.address = value;
    }

    /**
     * Gets the value of the port property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the port property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPort().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlanePortContent }
     * 
     * 
     */
    public List<CtrlPlanePortContent> getPort() {
        if (port == null) {
            port = new ArrayList<CtrlPlanePortContent>();
        }
        return this.port;
    }

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

}
