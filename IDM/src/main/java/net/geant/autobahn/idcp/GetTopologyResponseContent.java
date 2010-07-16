
package net.geant.autobahn.idcp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneTopologyContent;


/**
 * <p>Java class for getTopologyResponseContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTopologyResponseContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}topology"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTopologyResponseContent", propOrder = {
    "topology"
})
public class GetTopologyResponseContent {

    @XmlElement(namespace = "http://ogf.org/schema/network/topology/ctrlPlane/20080828/", required = true)
    protected CtrlPlaneTopologyContent topology;

    /**
     * Gets the value of the topology property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlaneTopologyContent }
     *     
     */
    public CtrlPlaneTopologyContent getTopology() {
        return topology;
    }

    /**
     * Sets the value of the topology property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlaneTopologyContent }
     *     
     */
    public void setTopology(CtrlPlaneTopologyContent value) {
        this.topology = value;
    }

}
