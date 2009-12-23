
package net.geant2.cnis.autobahn.xml.sdh;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Topology complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Topology">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/sdh}node" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="intradomainLinks" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyLink" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interdomainLinks" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/sdh}IDLink" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vcLinkTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="type" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcLinkType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vcLinks" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vcLink" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcLink" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vcTrails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vcTrail" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcTrail" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vcatGroups" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vcatGroup" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcatGroup" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Topology", propOrder = {
    "nodes",
    "intradomainLinks",
    "interdomainLinks",
    "vcLinkTypes",
    "vcLinks",
    "vcTrails",
    "vcatGroups"
})
public class Topology {

    @XmlElement(required = true)
    protected Topology.Nodes nodes;
    protected Topology.IntradomainLinks intradomainLinks;
    protected Topology.InterdomainLinks interdomainLinks;
    protected Topology.VcLinkTypes vcLinkTypes;
    protected Topology.VcLinks vcLinks;
    protected Topology.VcTrails vcTrails;
    protected Topology.VcatGroups vcatGroups;

    /**
     * Gets the value of the nodes property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.Nodes }
     *     
     */
    public Topology.Nodes getNodes() {
        return nodes;
    }

    /**
     * Sets the value of the nodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.Nodes }
     *     
     */
    public void setNodes(Topology.Nodes value) {
        this.nodes = value;
    }

    /**
     * Gets the value of the intradomainLinks property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.IntradomainLinks }
     *     
     */
    public Topology.IntradomainLinks getIntradomainLinks() {
        return intradomainLinks;
    }

    /**
     * Sets the value of the intradomainLinks property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.IntradomainLinks }
     *     
     */
    public void setIntradomainLinks(Topology.IntradomainLinks value) {
        this.intradomainLinks = value;
    }

    /**
     * Gets the value of the interdomainLinks property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.InterdomainLinks }
     *     
     */
    public Topology.InterdomainLinks getInterdomainLinks() {
        return interdomainLinks;
    }

    /**
     * Sets the value of the interdomainLinks property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.InterdomainLinks }
     *     
     */
    public void setInterdomainLinks(Topology.InterdomainLinks value) {
        this.interdomainLinks = value;
    }

    /**
     * Gets the value of the vcLinkTypes property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.VcLinkTypes }
     *     
     */
    public Topology.VcLinkTypes getVcLinkTypes() {
        return vcLinkTypes;
    }

    /**
     * Sets the value of the vcLinkTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.VcLinkTypes }
     *     
     */
    public void setVcLinkTypes(Topology.VcLinkTypes value) {
        this.vcLinkTypes = value;
    }

    /**
     * Gets the value of the vcLinks property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.VcLinks }
     *     
     */
    public Topology.VcLinks getVcLinks() {
        return vcLinks;
    }

    /**
     * Sets the value of the vcLinks property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.VcLinks }
     *     
     */
    public void setVcLinks(Topology.VcLinks value) {
        this.vcLinks = value;
    }

    /**
     * Gets the value of the vcTrails property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.VcTrails }
     *     
     */
    public Topology.VcTrails getVcTrails() {
        return vcTrails;
    }

    /**
     * Sets the value of the vcTrails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.VcTrails }
     *     
     */
    public void setVcTrails(Topology.VcTrails value) {
        this.vcTrails = value;
    }

    /**
     * Gets the value of the vcatGroups property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.VcatGroups }
     *     
     */
    public Topology.VcatGroups getVcatGroups() {
        return vcatGroups;
    }

    /**
     * Sets the value of the vcatGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.VcatGroups }
     *     
     */
    public void setVcatGroups(Topology.VcatGroups value) {
        this.vcatGroups = value;
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
     *         &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/sdh}IDLink" maxOccurs="unbounded" minOccurs="0"/>
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
        "link"
    })
    public static class InterdomainLinks {

        protected List<IDLink> link;

        /**
         * Gets the value of the link property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the link property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IDLink }
         * 
         * 
         */
        public List<IDLink> getLink() {
            if (link == null) {
                link = new ArrayList<IDLink>();
            }
            return this.link;
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
     *         &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/sdh}phyLink" maxOccurs="unbounded" minOccurs="0"/>
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
        "link"
    })
    public static class IntradomainLinks {

        protected List<PhyLink> link;

        /**
         * Gets the value of the link property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the link property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhyLink }
         * 
         * 
         */
        public List<PhyLink> getLink() {
            if (link == null) {
                link = new ArrayList<PhyLink>();
            }
            return this.link;
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
     *         &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/sdh}node" maxOccurs="unbounded"/>
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
        "node"
    })
    public static class Nodes {

        @XmlElement(required = true)
        protected List<Node> node;

        /**
         * Gets the value of the node property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the node property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Node }
         * 
         * 
         */
        public List<Node> getNode() {
            if (node == null) {
                node = new ArrayList<Node>();
            }
            return this.node;
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
     *         &lt;element name="type" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcLinkType" maxOccurs="unbounded" minOccurs="0"/>
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
        "type"
    })
    public static class VcLinkTypes {

        protected List<VcLinkType> type;

        /**
         * Gets the value of the type property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the type property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VcLinkType }
         * 
         * 
         */
        public List<VcLinkType> getType() {
            if (type == null) {
                type = new ArrayList<VcLinkType>();
            }
            return this.type;
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
     *         &lt;element name="vcLink" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcLink" maxOccurs="unbounded" minOccurs="0"/>
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
        "vcLink"
    })
    public static class VcLinks {

        protected List<VcLink> vcLink;

        /**
         * Gets the value of the vcLink property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vcLink property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVcLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VcLink }
         * 
         * 
         */
        public List<VcLink> getVcLink() {
            if (vcLink == null) {
                vcLink = new ArrayList<VcLink>();
            }
            return this.vcLink;
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
     *         &lt;element name="vcTrail" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcTrail" maxOccurs="unbounded" minOccurs="0"/>
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
        "vcTrail"
    })
    public static class VcTrails {

        protected List<VcTrail> vcTrail;

        /**
         * Gets the value of the vcTrail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vcTrail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVcTrail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VcTrail }
         * 
         * 
         */
        public List<VcTrail> getVcTrail() {
            if (vcTrail == null) {
                vcTrail = new ArrayList<VcTrail>();
            }
            return this.vcTrail;
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
     *         &lt;element name="vcatGroup" type="{http://cnis.geant2.net/autobahn/xml/sdh}vcatGroup" maxOccurs="unbounded" minOccurs="0"/>
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
        "vcatGroup"
    })
    public static class VcatGroups {

        protected List<VcatGroup> vcatGroup;

        /**
         * Gets the value of the vcatGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vcatGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVcatGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VcatGroup }
         * 
         * 
         */
        public List<VcatGroup> getVcatGroup() {
            if (vcatGroup == null) {
                vcatGroup = new ArrayList<VcatGroup>();
            }
            return this.vcatGroup;
        }

    }

}
