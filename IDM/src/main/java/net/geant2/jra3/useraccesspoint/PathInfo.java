
package net.geant2.jra3.useraccesspoint;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PathInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PathInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domains" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="links" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PathInfo", namespace = "useraccesspoint.jra3.geant2.net", propOrder = {
    "domains",
    "links"
})
public class PathInfo {

    @XmlElement(nillable = true)
    protected List<String> domains;
    @XmlElement(nillable = true)
    protected List<String> links;

    public PathInfo() {
        domains = new ArrayList<String>();
        links = new ArrayList<String>();
    }
    
    public PathInfo(List<String> domains, List<String> links) {
        this.domains = domains;
        this.links = links;
    }
    
    /**
     * Gets the value of the domains property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domains property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomains().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDomains() {
        if (domains == null) {
            domains = new ArrayList<String>();
        }
        return this.domains;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getLinks() {
        if (links == null) {
            links = new ArrayList<String>();
        }
        return this.links;
    }

    public void addDomain(String d) {
        domains.add(d);
    }

    public void addLink(String l) {
        links.add(l);
    }
    
}
