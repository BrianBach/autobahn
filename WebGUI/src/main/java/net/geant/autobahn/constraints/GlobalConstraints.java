
package net.geant.autobahn.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GlobalConstraints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GlobalConstraints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainsIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="domainConstraints" type="{constraints.autobahn.geant.net}DomainConstraints" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalConstraints", propOrder = {
    "domainsIds",
    "domainConstraints"
})
public class GlobalConstraints implements Serializable{

    @XmlElement(required = true, nillable = true)
    protected List<String> domainsIds;
    @XmlElement(required = true, nillable = true)
    protected List<DomainConstraints> domainConstraints;

    /**
     * Gets the value of the domainsIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainsIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainsIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDomainsIds() {
        if (domainsIds == null) {
            domainsIds = new ArrayList<String>();
        }
        return this.domainsIds;
    }

    /**
     * Gets the value of the domainConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DomainConstraints }
     * 
     * 
     */
    public List<DomainConstraints> getDomainConstraints() {
        if (domainConstraints == null) {
            domainConstraints = new ArrayList<DomainConstraints>();
        }
        return this.domainConstraints;
    }

}
