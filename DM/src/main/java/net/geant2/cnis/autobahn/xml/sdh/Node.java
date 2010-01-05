
package net.geant2.cnis.autobahn.xml.sdh;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.cnis.autobahn.xml.common.AdministrativeStatus;
import net.geant2.cnis.autobahn.xml.common.GeoLocation;


/**
 * <p>Java class for node complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="node">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://cnis.geant2.net/autobahn/xml/common}GeoLocation" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phyInterfaces">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="interface" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyInterface" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ctpSet">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ctp" type="{http://cnis.geant2.net/autobahn/xml/sdh}ctp" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vtpSet">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vtp" type="{http://cnis.geant2.net/autobahn/xml/sdh}vtp" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nsap" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="status" type="{http://cnis.geant2.net/autobahn/xml/common}AdministrativeStatus" />
 *       &lt;attribute name="vendor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="model" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "node", propOrder = {
    "location",
    "description",
    "phyInterfaces",
    "ctpSet",
    "vtpSet"
})
public class Node {

    protected GeoLocation location;
    protected String description;
    @XmlElement(required = true)
    protected Node.PhyInterfaces phyInterfaces;
    @XmlElement(required = true)
    protected Node.CtpSet ctpSet;
    @XmlElement(required = true)
    protected Node.VtpSet vtpSet;
    @XmlAttribute(required = true)
    protected String id;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected BigInteger nsap;
    @XmlAttribute
    protected AdministrativeStatus status;
    @XmlAttribute
    protected String vendor;
    @XmlAttribute
    protected String model;

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link GeoLocation }
     *     
     */
    public GeoLocation getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoLocation }
     *     
     */
    public void setLocation(GeoLocation value) {
        this.location = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the phyInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link Node.PhyInterfaces }
     *     
     */
    public Node.PhyInterfaces getPhyInterfaces() {
        return phyInterfaces;
    }

    /**
     * Sets the value of the phyInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node.PhyInterfaces }
     *     
     */
    public void setPhyInterfaces(Node.PhyInterfaces value) {
        this.phyInterfaces = value;
    }

    /**
     * Gets the value of the ctpSet property.
     * 
     * @return
     *     possible object is
     *     {@link Node.CtpSet }
     *     
     */
    public Node.CtpSet getCtpSet() {
        return ctpSet;
    }

    /**
     * Sets the value of the ctpSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node.CtpSet }
     *     
     */
    public void setCtpSet(Node.CtpSet value) {
        this.ctpSet = value;
    }

    /**
     * Gets the value of the vtpSet property.
     * 
     * @return
     *     possible object is
     *     {@link Node.VtpSet }
     *     
     */
    public Node.VtpSet getVtpSet() {
        return vtpSet;
    }

    /**
     * Sets the value of the vtpSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Node.VtpSet }
     *     
     */
    public void setVtpSet(Node.VtpSet value) {
        this.vtpSet = value;
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

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the nsap property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNsap() {
        return nsap;
    }

    /**
     * Sets the value of the nsap property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNsap(BigInteger value) {
        this.nsap = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeStatus }
     *     
     */
    public AdministrativeStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeStatus }
     *     
     */
    public void setStatus(AdministrativeStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ctp" type="{http://cnis.geant2.net/autobahn/xml/sdh}ctp" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ctp"
    })
    public static class CtpSet {

        protected List<Ctp> ctp;

        /**
         * Gets the value of the ctp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ctp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCtp().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ctp }
         * 
         * 
         */
        public List<Ctp> getCtp() {
            if (ctp == null) {
                ctp = new ArrayList<Ctp>();
            }
            return this.ctp;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="interface" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyInterface" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "_interface"
    })
    public static class PhyInterfaces {

        @XmlElement(name = "interface")
        protected List<PhyInterface> _interface;

        /**
         * Gets the value of the interface property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the interface property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInterface().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhyInterface }
         * 
         * 
         */
        public List<PhyInterface> getInterface() {
            if (_interface == null) {
                _interface = new ArrayList<PhyInterface>();
            }
            return this._interface;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="vtp" type="{http://cnis.geant2.net/autobahn/xml/sdh}vtp" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "vtp"
    })
    public static class VtpSet {

        protected List<Vtp> vtp;

        /**
         * Gets the value of the vtp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vtp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVtp().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Vtp }
         * 
         * 
         */
        public List<Vtp> getVtp() {
            if (vtp == null) {
                vtp = new ArrayList<Vtp>();
            }
            return this.vtp;
        }

    }

}
