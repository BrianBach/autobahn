
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="globalReservationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="bandwidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pathInfo" type="{http://oscars.es.net/OSCARS}pathInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resDetails", propOrder = {
    "globalReservationId",
    "login",
    "status",
    "startTime",
    "endTime",
    "createTime",
    "bandwidth",
    "description",
    "pathInfo"
})
public class ResDetails {

    @XmlElement(required = true)
    protected String globalReservationId;
    @XmlElement(required = true)
    protected String login;
    @XmlElement(required = true)
    protected String status;
    protected long startTime;
    protected long endTime;
    protected long createTime;
    protected int bandwidth;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected PathInfo pathInfo;

    /**
     * Gets the value of the globalReservationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalReservationId() {
        return globalReservationId;
    }

    /**
     * Sets the value of the globalReservationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalReservationId(String value) {
        this.globalReservationId = value;
    }

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     */
    public void setEndTime(long value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     */
    public void setCreateTime(long value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the bandwidth property.
     * 
     */
    public int getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the value of the bandwidth property.
     * 
     */
    public void setBandwidth(int value) {
        this.bandwidth = value;
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
     * Gets the value of the pathInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PathInfo }
     *     
     */
    public PathInfo getPathInfo() {
        return pathInfo;
    }

    /**
     * Sets the value of the pathInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathInfo }
     *     
     */
    public void setPathInfo(PathInfo value) {
        this.pathInfo = value;
    }

}
