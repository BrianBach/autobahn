
package net.geant.autobahn.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Path complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Path">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="links" type="{network.autobahn.geant.net}Link" maxOccurs="unbounded"/>
 *         &lt;element name="monetary_cost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="manual_cost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Path", propOrder = {
    "links",
    "monetaryCost",
    "manualCost",
    "capacity"
})
public class Path implements Serializable {

    @XmlElement(required = true, nillable = true)
    protected List<Link> links;
    @XmlElement(name = "monetary_cost")
    protected double monetaryCost;
    @XmlElement(name = "manual_cost")
    protected double manualCost;
    protected long capacity;

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
     * {@link Link }
     * 
     * 
     */
    public List<Link> getLinks() {
        if (links == null) {
            links = new ArrayList<Link>();
        }
        return this.links;
    }

    /**
     * Gets the value of the monetaryCost property.
     * 
     */
    public double getMonetaryCost() {
        return monetaryCost;
    }

    /**
     * Sets the value of the monetaryCost property.
     * 
     */
    public void setMonetaryCost(double value) {
        this.monetaryCost = value;
    }

    /**
     * Gets the value of the manualCost property.
     * 
     */
    public double getManualCost() {
        return manualCost;
    }

    /**
     * Sets the value of the manualCost property.
     * 
     */
    public void setManualCost(double value) {
        this.manualCost = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(long value) {
        this.capacity = value;
    }

}
