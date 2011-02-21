
package net.geant.autobahn.tool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.ReservationParamsType;


/**
 * <p>Java class for removeReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeReservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipath" type="{types.tool.autobahn.geant.net}IntradomainPathType" minOccurs="0"/>
 *         &lt;element name="params" type="{types.tool.autobahn.geant.net}ReservationParamsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeReservation", propOrder = {
    "resID",
    "ipath",
    "params"
})
public class RemoveReservation {

    protected String resID;
    protected IntradomainPathType ipath;
    protected ReservationParamsType params;

    /**
     * Gets the value of the resID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResID() {
        return resID;
    }

    /**
     * Sets the value of the resID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResID(String value) {
        this.resID = value;
    }

    /**
     * Gets the value of the ipath property.
     * 
     * @return
     *     possible object is
     *     {@link IntradomainPathType }
     *     
     */
    public IntradomainPathType getIpath() {
        return ipath;
    }

    /**
     * Sets the value of the ipath property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntradomainPathType }
     *     
     */
    public void setIpath(IntradomainPathType value) {
        this.ipath = value;
    }

    /**
     * Gets the value of the params property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationParamsType }
     *     
     */
    public ReservationParamsType getParams() {
        return params;
    }

    /**
     * Sets the value of the params property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationParamsType }
     *     
     */
    public void setParams(ReservationParamsType value) {
        this.params = value;
    }

}
