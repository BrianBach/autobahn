
package net.geant2.jra3.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProvisioningDomain complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProvisioningDomain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bodID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adminDomain" type="{network.jra3.geant2.net}AdminDomain" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvisioningDomain", propOrder = {
    "bodID",
    "provType",
    "adminDomain"
})
public class ProvisioningDomain implements Serializable {

    protected String bodID;
    protected String provType;
    protected AdminDomain adminDomain;

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
     * Gets the value of the provType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvType() {
        return provType;
    }

    /**
     * Sets the value of the provType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvType(String value) {
        this.provType = value;
    }

    /**
     * Gets the value of the adminDomain property.
     * 
     * @return
     *     possible object is
     *     {@link AdminDomain }
     *     
     */
    public AdminDomain getAdminDomain() {
        return adminDomain;
    }

    /**
     * Sets the value of the adminDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdminDomain }
     *     
     */
    public void setAdminDomain(AdminDomain value) {
        this.adminDomain = value;
    }

}
