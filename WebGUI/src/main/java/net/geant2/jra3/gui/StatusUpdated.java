
package net.geant2.jra3.gui;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import net.geant2.jra3.administration.Status;


/**
 * <p>Java class for statusUpdated complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="statusUpdated">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{administration.jra3.geant2.net}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statusUpdated", propOrder = {
    "idm",
    "status"
})
public class StatusUpdated {

    protected String idm;
    protected Status status;

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
