
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eventContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="userLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resDetails" type="{http://oscars.es.net/OSCARS}resDetails" minOccurs="0"/>
 *         &lt;element name="msgDetails" type="{http://oscars.es.net/OSCARS}msgDetails" minOccurs="0"/>
 *         &lt;element name="localDetails" type="{http://oscars.es.net/OSCARS}localDetails" minOccurs="0"/>
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
@XmlType(name = "eventContent", propOrder = {
    "type",
    "timestamp",
    "userLogin",
    "errorSource",
    "errorCode",
    "errorMessage",
    "resDetails",
    "msgDetails",
    "localDetails"
})
public class EventContent {

    @XmlElement(required = true)
    protected String type;
    protected long timestamp;
    protected String userLogin;
    protected String errorSource;
    protected String errorCode;
    protected String errorMessage;
    protected ResDetails resDetails;
    protected MsgDetails msgDetails;
    protected LocalDetails localDetails;
    @XmlAttribute(required = true)
    protected String id;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the userLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the value of the userLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLogin(String value) {
        this.userLogin = value;
    }

    /**
     * Gets the value of the errorSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSource() {
        return errorSource;
    }

    /**
     * Sets the value of the errorSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSource(String value) {
        this.errorSource = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the resDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ResDetails }
     *     
     */
    public ResDetails getResDetails() {
        return resDetails;
    }

    /**
     * Sets the value of the resDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResDetails }
     *     
     */
    public void setResDetails(ResDetails value) {
        this.resDetails = value;
    }

    /**
     * Gets the value of the msgDetails property.
     * 
     * @return
     *     possible object is
     *     {@link MsgDetails }
     *     
     */
    public MsgDetails getMsgDetails() {
        return msgDetails;
    }

    /**
     * Sets the value of the msgDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgDetails }
     *     
     */
    public void setMsgDetails(MsgDetails value) {
        this.msgDetails = value;
    }

    /**
     * Gets the value of the localDetails property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDetails }
     *     
     */
    public LocalDetails getLocalDetails() {
        return localDetails;
    }

    /**
     * Sets the value of the localDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDetails }
     *     
     */
    public void setLocalDetails(LocalDetails value) {
        this.localDetails = value;
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
