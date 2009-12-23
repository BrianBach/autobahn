
package net.geant2.cnis.autobahn.xml.ethernet;

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
 *                   &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Node" maxOccurs="unbounded"/>
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
 *                   &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Link" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interdomainLinks">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/ethernet}IDLink" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="vlans" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="vlan" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Vlan" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="spanningTrees" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="st" type="{http://cnis.geant2.net/autobahn/xml/ethernet}SpanningTree" maxOccurs="unbounded" minOccurs="0"/>
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
    "vlans",
    "spanningTrees"
})
public class Topology {

    @XmlElement(required = true)
    protected Topology.Nodes nodes;
    protected Topology.IntradomainLinks intradomainLinks;
    @XmlElement(required = true)
    protected Topology.InterdomainLinks interdomainLinks;
    protected Topology.Vlans vlans;
    protected Topology.SpanningTrees spanningTrees;

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
     * Gets the value of the vlans property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.Vlans }
     *     
     */
    public Topology.Vlans getVlans() {
        return vlans;
    }

    /**
     * Sets the value of the vlans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.Vlans }
     *     
     */
    public void setVlans(Topology.Vlans value) {
        this.vlans = value;
    }

    /**
     * Gets the value of the spanningTrees property.
     * 
     * @return
     *     possible object is
     *     {@link Topology.SpanningTrees }
     *     
     */
    public Topology.SpanningTrees getSpanningTrees() {
        return spanningTrees;
    }

    /**
     * Sets the value of the spanningTrees property.
     * 
     * @param value
     *     allowed object is
     *     {@link Topology.SpanningTrees }
     *     
     */
    public void setSpanningTrees(Topology.SpanningTrees value) {
        this.spanningTrees = value;
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
     *         &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/ethernet}IDLink" maxOccurs="unbounded" minOccurs="0"/>
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
     *         &lt;element name="link" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Link" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<Link> link;

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
         * {@link Link }
         * 
         * 
         */
        public List<Link> getLink() {
            if (link == null) {
                link = new ArrayList<Link>();
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
     *         &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Node" maxOccurs="unbounded"/>
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
     *         &lt;element name="st" type="{http://cnis.geant2.net/autobahn/xml/ethernet}SpanningTree" maxOccurs="unbounded" minOccurs="0"/>
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
        "st"
    })
    public static class SpanningTrees {

        protected List<SpanningTree> st;

        /**
         * Gets the value of the st property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the st property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SpanningTree }
         * 
         * 
         */
        public List<SpanningTree> getSt() {
            if (st == null) {
                st = new ArrayList<SpanningTree>();
            }
            return this.st;
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
     *         &lt;element name="vlan" type="{http://cnis.geant2.net/autobahn/xml/ethernet}Vlan" maxOccurs="unbounded" minOccurs="0"/>
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
        "vlan"
    })
    public static class Vlans {

        protected List<Vlan> vlan;

        /**
         * Gets the value of the vlan property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vlan property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVlan().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Vlan }
         * 
         * 
         */
        public List<Vlan> getVlan() {
            if (vlan == null) {
                vlan = new ArrayList<Vlan>();
            }
            return this.vlan;
        }

    }

}
