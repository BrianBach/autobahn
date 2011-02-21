
package net.geant2.jra3.tool;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import net.geant2.jra3.intradomain.common.GenericLink;
import net.geant2.jra3.reservation.ReservationParams;


/**
 * <p>Java class for addReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addReservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="links" type="{common.intradomain.jra3.geant2.net}GenericLink" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="params" type="{reservation.jra3.geant2.net}ReservationParams" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addReservation", propOrder = {
    "resID",
    "links",
    "params"
})
public class AddReservation {

    protected String resID;
    protected List<GenericLink> links;
    protected ReservationParams params;

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
     * Gets the value of the links property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the links property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLinks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericLink }
     * 
     * 
     */
    public List<GenericLink> getLinks() {
        if (links == null) {
            links = new ArrayList<GenericLink>();
        }
        return this.links;
    }

    /**
     * Gets the value of the params property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationParams }
     *     
     */
    public ReservationParams getParams() {
        return params;
    }

    /**
     * Sets the value of the params property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationParams }
     *     
     */
    public void setParams(ReservationParams value) {
        this.params = value;
    }

}
