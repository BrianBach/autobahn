
package net.geant2.cnis.autobahn.xml.mpls;

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
 *                   &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/mpls}Node" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="intradomainLinks">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="intradomainLink" type="{http://cnis.geant2.net/autobahn/xml/mpls}IntradomainLink" maxOccurs="unbounded"/>
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
 *                   &lt;element name="interdomainLink" type="{http://cnis.geant2.net/autobahn/xml/mpls}InterdomainLink" maxOccurs="unbounded"/>
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
    "interdomainLinks"
})
public class Topology {

    @XmlElement(required = true)
    protected Topology.Nodes nodes;
    @XmlElement(required = true)
    protected Topology.IntradomainLinks intradomainLinks;
    @XmlElement(required = true)
    protected Topology.InterdomainLinks interdomainLinks;

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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="interdomainLink" type="{http://cnis.geant2.net/autobahn/xml/mpls}InterdomainLink" maxOccurs="unbounded"/>
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
        "interdomainLink"
    })
    public static class InterdomainLinks {

        @XmlElement(required = true)
        protected List<InterdomainLink> interdomainLink;

        /**
         * Gets the value of the interdomainLink property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the interdomainLink property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInterdomainLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InterdomainLink }
         * 
         * 
         */
        public List<InterdomainLink> getInterdomainLink() {
            if (interdomainLink == null) {
                interdomainLink = new ArrayList<InterdomainLink>();
            }
            return this.interdomainLink;
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
     *         &lt;element name="intradomainLink" type="{http://cnis.geant2.net/autobahn/xml/mpls}IntradomainLink" maxOccurs="unbounded"/>
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
        "intradomainLink"
    })
    public static class IntradomainLinks {

        @XmlElement(required = true)
        protected List<IntradomainLink> intradomainLink;

        /**
         * Gets the value of the intradomainLink property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the intradomainLink property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIntradomainLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IntradomainLink }
         * 
         * 
         */
        public List<IntradomainLink> getIntradomainLink() {
            if (intradomainLink == null) {
                intradomainLink = new ArrayList<IntradomainLink>();
            }
            return this.intradomainLink;
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
     *         &lt;element name="node" type="{http://cnis.geant2.net/autobahn/xml/mpls}Node" maxOccurs="unbounded"/>
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

}
