
package net.es.oscars.oscars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlanePathContent;


/**
 * <p>Java class for pathInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pathInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pathSetupMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pathType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="path" type="{http://ogf.org/schema/network/topology/ctrlPlane/20080828/}CtrlPlanePathContent" minOccurs="0"/>
 *         &lt;element name="layer2Info" type="{http://oscars.es.net/OSCARS}layer2Info" minOccurs="0"/>
 *         &lt;element name="layer3Info" type="{http://oscars.es.net/OSCARS}layer3Info" minOccurs="0"/>
 *         &lt;element name="mplsInfo" type="{http://oscars.es.net/OSCARS}mplsInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pathInfo", propOrder = {
    "pathSetupMode",
    "pathType",
    "path",
    "layer2Info",
    "layer3Info",
    "mplsInfo"
})
public class PathInfo {

    @XmlElement(required = true)
    protected String pathSetupMode;
    protected String pathType;
    protected CtrlPlanePathContent path;
    protected Layer2Info layer2Info;
    protected Layer3Info layer3Info;
    protected MplsInfo mplsInfo;

    /**
     * Gets the value of the pathSetupMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathSetupMode() {
        return pathSetupMode;
    }

    /**
     * Sets the value of the pathSetupMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathSetupMode(String value) {
        this.pathSetupMode = value;
    }

    /**
     * Gets the value of the pathType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathType() {
        return pathType;
    }

    /**
     * Sets the value of the pathType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathType(String value) {
        this.pathType = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link CtrlPlanePathContent }
     *     
     */
    public CtrlPlanePathContent getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtrlPlanePathContent }
     *     
     */
    public void setPath(CtrlPlanePathContent value) {
        this.path = value;
    }

    /**
     * Gets the value of the layer2Info property.
     * 
     * @return
     *     possible object is
     *     {@link Layer2Info }
     *     
     */
    public Layer2Info getLayer2Info() {
        return layer2Info;
    }

    /**
     * Sets the value of the layer2Info property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layer2Info }
     *     
     */
    public void setLayer2Info(Layer2Info value) {
        this.layer2Info = value;
    }

    /**
     * Gets the value of the layer3Info property.
     * 
     * @return
     *     possible object is
     *     {@link Layer3Info }
     *     
     */
    public Layer3Info getLayer3Info() {
        return layer3Info;
    }

    /**
     * Sets the value of the layer3Info property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layer3Info }
     *     
     */
    public void setLayer3Info(Layer3Info value) {
        this.layer3Info = value;
    }

    /**
     * Gets the value of the mplsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MplsInfo }
     *     
     */
    public MplsInfo getMplsInfo() {
        return mplsInfo;
    }

    /**
     * Sets the value of the mplsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MplsInfo }
     *     
     */
    public void setMplsInfo(MplsInfo value) {
        this.mplsInfo = value;
    }

}
