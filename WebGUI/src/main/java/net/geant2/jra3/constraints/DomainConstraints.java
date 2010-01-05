
package net.geant2.jra3.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DomainConstraints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DomainConstraints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pathConstraints" type="{constraints.jra3.geant2.net}PathConstraints" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DomainConstraints", propOrder = {
    "pathConstraints"
})
public class DomainConstraints implements Serializable{

    @XmlElement(required = true, nillable = true)
    protected List<PathConstraints> pathConstraints;

    /**
     * Gets the value of the pathConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pathConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPathConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PathConstraints }
     * 
     * 
     */
    public List<PathConstraints> getPathConstraints() {
        if (pathConstraints == null) {
            pathConstraints = new ArrayList<PathConstraints>();
        }
        return this.pathConstraints;
    }

}
