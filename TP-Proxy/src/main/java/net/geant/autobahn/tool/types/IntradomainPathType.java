
package net.geant.autobahn.tool.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntradomainPathType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntradomainPathType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="links" type="{types.tool.autobahn.geant.net}GenericLinkType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="constraints" type="{types.tool.autobahn.geant.net}ConstraintsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntradomainPathType", propOrder = {
    "links",
    "constraints"
})
public class IntradomainPathType {

    @XmlElement(nillable = true)
    protected List<GenericLinkType> links;
    @XmlElement(nillable = true)
    protected List<ConstraintsType> constraints;

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
     * {@link GenericLinkType }
     * 
     * 
     */
    public List<GenericLinkType> getLinks() {
        if (links == null) {
            links = new ArrayList<GenericLinkType>();
        }
        return this.links;
    }

    /**
     * Gets the value of the constraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsType }
     * 
     * 
     */
    public List<ConstraintsType> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<ConstraintsType>();
        }
        return this.constraints;
    }

}
