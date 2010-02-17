
package net.geant.autobahn.administration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.reservation.Service;


/**
 * <p>Java class for getServicesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getServicesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serivces" type="{reservation.autobahn.geant.net}Service" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getServicesResponse", propOrder = {
    "serivces"
})
public class GetServicesResponse {

    @XmlElement(required = true)
    protected List<Service> serivces;

    /**
     * Gets the value of the serivces property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serivces property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSerivces().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Service }
     * 
     * 
     */
    public List<Service> getSerivces() {
        if (serivces == null) {
            serivces = new ArrayList<Service>();
        }
        return this.serivces;
    }

}
