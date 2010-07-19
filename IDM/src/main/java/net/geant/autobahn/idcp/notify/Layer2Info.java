
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for layer2Info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="layer2Info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srcVtag" type="{http://oscars.es.net/OSCARS}vlanTag" minOccurs="0"/>
 *         &lt;element name="destVtag" type="{http://oscars.es.net/OSCARS}vlanTag" minOccurs="0"/>
 *         &lt;element name="srcEndpoint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destEndpoint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layer2Info", propOrder = {
    "srcVtag",
    "destVtag",
    "srcEndpoint",
    "destEndpoint"
})
public class Layer2Info {

    protected VlanTag srcVtag;
    protected VlanTag destVtag;
    @XmlElement(required = true)
    protected String srcEndpoint;
    @XmlElement(required = true)
    protected String destEndpoint;

    /**
     * Gets the value of the srcVtag property.
     * 
     * @return
     *     possible object is
     *     {@link VlanTag }
     *     
     */
    public VlanTag getSrcVtag() {
        return srcVtag;
    }

    /**
     * Sets the value of the srcVtag property.
     * 
     * @param value
     *     allowed object is
     *     {@link VlanTag }
     *     
     */
    public void setSrcVtag(VlanTag value) {
        this.srcVtag = value;
    }

    /**
     * Gets the value of the destVtag property.
     * 
     * @return
     *     possible object is
     *     {@link VlanTag }
     *     
     */
    public VlanTag getDestVtag() {
        return destVtag;
    }

    /**
     * Sets the value of the destVtag property.
     * 
     * @param value
     *     allowed object is
     *     {@link VlanTag }
     *     
     */
    public void setDestVtag(VlanTag value) {
        this.destVtag = value;
    }

    /**
     * Gets the value of the srcEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcEndpoint() {
        return srcEndpoint;
    }

    /**
     * Sets the value of the srcEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcEndpoint(String value) {
        this.srcEndpoint = value;
    }

    /**
     * Gets the value of the destEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestEndpoint() {
        return destEndpoint;
    }

    /**
     * Sets the value of the destEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestEndpoint(String value) {
        this.destEndpoint = value;
    }

}
