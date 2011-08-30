
package org.ogf.schema.network.topology.ctrlplane._20080828;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtrlPlaneHopContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtrlPlaneHopContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainIdRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nodeIdRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="portIdRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkIdRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}domain" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}node" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}port" minOccurs="0"/>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}link" minOccurs="0"/>
 *         &lt;element name="nextHop" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlaneNextHopContent" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "CtrlPlaneHopContent", propOrder = {
    "domainIdRef",
    "nodeIdRef",
    "portIdRef",
    "linkIdRef",
    "domain",
    "node",
    "port",
    "link",
    "nextHop"
})
public class CtrlPlaneHopContent {

    protected String domainIdRef;
    protected String nodeIdRef;
    protected String portIdRef;
    protected String linkIdRef;
    protected CtrlPlaneDomainContent domain;
    protected CtrlPlaneNodeContent node;
    protected CtrlPlanePortContent port;
    protected CtrlPlaneLinkContent link;
    protected List<CtrlPlaneNextHopContent> nextHop;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the domainIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainIdRef() {
        return domainIdRef;
    }

    /**
     * Sets the value of the domainIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainIdRef(String value) {
        this.domainIdRef = value;
    }

    /**
     * Gets the value of the nodeIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeIdRef() {
        return nodeIdRef;
    }

    /**
     * Sets the value of the nodeIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeIdRef(String value) {
        this.nodeIdRef = value;
    }

    /**
     * Gets the value of the portIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortIdRef() {
        return portIdRef;
    }

    /**
     * Sets the value of the portIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortIdRef(String value) {
        this.portIdRef = value;
    }

    /**
     * Gets the value of the linkIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkIdRef() {
        return linkIdRef;
    }

    /**
     * Sets the value of the linkIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkIdRef(String value) {
        this.linkIdRef = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneDomainContent }
     *     
     */
    public CtrlPlaneDomainContent getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneDomainContent }
     *     
     */
    public void setDomain(CtrlPlaneDomainContent value) {
        this.domain = value;
    }

    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneNodeContent }
     *     
     */
    public CtrlPlaneNodeContent getNode() {
        return node;
    }

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneNodeContent }
     *     
     */
    public void setNode(CtrlPlaneNodeContent value) {
        this.node = value;
    }

    /**
     * Gets the value of the port property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlanePortContent }
     *     
     */
    public CtrlPlanePortContent getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlanePortContent }
     *     
     */
    public void setPort(CtrlPlanePortContent value) {
        this.port = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneLinkContent }
     *     
     */
    public CtrlPlaneLinkContent getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneLinkContent }
     *     
     */
    public void setLink(CtrlPlaneLinkContent value) {
        this.link = value;
    }

    /**
     * Gets the value of the nextHop property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nextHop property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNextHop().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtrlPlaneNextHopContent }
     * 
     * 
     */
    public List<CtrlPlaneNextHopContent> getNextHop() {
        if (nextHop == null) {
            nextHop = new ArrayList<CtrlPlaneNextHopContent>();
        }
        return this.nextHop;
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
