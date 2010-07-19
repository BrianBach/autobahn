
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for globalReservationId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="globalReservationId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "globalReservationId", propOrder = {
    "gri"
})
public class GlobalReservationId {

    @XmlElement(required = true)
    protected String gri;

    /**
     * Gets the value of the gri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGri() {
        return gri;
    }

    /**
     * Sets the value of the gri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGri(String value) {
        this.gri = value;
    }

}
