
package net.geant.autobahn.idcp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservationResourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reservationResourceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://oscars.es.net/OSCARS}resCreateContent">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="localDetails" type="{http://oscars.es.net/OSCARS}localDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationResourceType", propOrder = {
    "localDetails"
})
public class ReservationResourceType
    extends ResCreateContent
{

    protected LocalDetails localDetails;

    /**
     * Gets the value of the localDetails property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDetails }
     *     
     */
    public LocalDetails getLocalDetails() {
        return localDetails;
    }

    /**
     * Sets the value of the localDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDetails }
     *     
     */
    public void setLocalDetails(LocalDetails value) {
        this.localDetails = value;
    }

}
