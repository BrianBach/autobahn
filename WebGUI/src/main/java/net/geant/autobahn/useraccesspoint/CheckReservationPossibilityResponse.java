
package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for checkReservationPossibilityResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkReservationPossibilityResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Possibility" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkReservationPossibilityResponse", propOrder = {
    "possibility"
})
public class CheckReservationPossibilityResponse {

    @XmlElement(name = "Possibility")
    protected boolean possibility;

    /**
     * Gets the value of the possibility property.
     * 
     */
    public boolean isPossibility() {
        return possibility;
    }

    /**
     * Sets the value of the possibility property.
     * 
     */
    public void setPossibility(boolean value) {
        this.possibility = value;
    }

}
