
package org.ogf.schema.network.topology.ctrlplane._20080828;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneTopologyContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneTopologyContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idcId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}path" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}domain" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="domainSignature" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneDomainSignatureContent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtrlPlaneTopologyContent", propOrder = {
    "idcId",
    "path",
    "domain",
    "domainSignature"
})
public class CtrlPlaneTopologyContent {

    @XmlElement(required = true)
    protected String idcId;
    protected List<CtrlPlanePathContent> path;
    protected List<CtrlPlaneDomainContent> domain;
    protected List<CtrlPlaneDomainSignatureContent> domainSignature;
    @XmlAttribute(required = true)
    protected String id;

    /**
     * Gets the value of the idcId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdcId() {
        return idcId;
    }

    /**
     * Sets the value of the idcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdcId(String value) {
        this.idcId = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the path property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlanePathContent }
     * 
     * 
     */
    public List<CtrlPlanePathContent> getPath() {
        if (path == null) {
            path = new ArrayList<CtrlPlanePathContent>();
        }
        return this.path;
    }

    /**
     * Gets the value of the domain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneDomainContent }
     * 
     * 
     */
    public List<CtrlPlaneDomainContent> getDomain() {
        if (domain == null) {
            domain = new ArrayList<CtrlPlaneDomainContent>();
        }
        return this.domain;
    }
    
    public void setDomain(CtrlPlaneDomainContent[] ctrlSigns) {
        this.domain = new ArrayList<CtrlPlaneDomainContent>();
        for (CtrlPlaneDomainContent ct : ctrlSigns) {
            this.domain.add(ct);
        }
    }

    /**
     * Gets the value of the domainSignature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domainSignature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomainSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneDomainSignatureContent }
     * 
     * 
     */
    public List<CtrlPlaneDomainSignatureContent> getDomainSignature() {
        if (domainSignature == null) {
            domainSignature = new ArrayList<CtrlPlaneDomainSignatureContent>();
        }
        return this.domainSignature;
    }
    
    public void setDomainSignature(CtrlPlaneDomainSignatureContent[] ctrlSigns) {
        this.domainSignature = new ArrayList<CtrlPlaneDomainSignatureContent>();
        for (CtrlPlaneDomainSignatureContent ct : ctrlSigns) {
            this.domainSignature.add(ct);
        }
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
