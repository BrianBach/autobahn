
package net.es.oscars.oscars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for forwardReply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="forwardReply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createReservation" type="{http://oscars.es.net/OSCARS}createReply" minOccurs="0"/>
 *         &lt;element name="modifyReservation" type="{http://oscars.es.net/OSCARS}modifyResReply" minOccurs="0"/>
 *         &lt;element name="cancelReservation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryReservation" type="{http://oscars.es.net/OSCARS}resDetails" minOccurs="0"/>
 *         &lt;element name="listReservations" type="{http://oscars.es.net/OSCARS}listReply" minOccurs="0"/>
 *         &lt;element name="createPath" type="{http://oscars.es.net/OSCARS}createPathResponseContent" minOccurs="0"/>
 *         &lt;element name="refreshPath" type="{http://oscars.es.net/OSCARS}refreshPathResponseContent" minOccurs="0"/>
 *         &lt;element name="teardownPath" type="{http://oscars.es.net/OSCARS}teardownPathResponseContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forwardReply", propOrder = {
    "contentType",
    "createReservation",
    "modifyReservation",
    "cancelReservation",
    "queryReservation",
    "listReservations",
    "createPath",
    "refreshPath",
    "teardownPath"
})
public class ForwardReply {

    @XmlElement(required = true)
    protected String contentType;
    protected CreateReply createReservation;
    protected ModifyResReply modifyReservation;
    protected String cancelReservation;
    protected ResDetails queryReservation;
    protected ListReply listReservations;
    protected CreatePathResponseContent createPath;
    protected RefreshPathResponseContent refreshPath;
    protected TeardownPathResponseContent teardownPath;

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the createReservation property.
     * 
     * @return
     *     possible object is
     *     {@link CreateReply }
     *     
     */
    public CreateReply getCreateReservation() {
        return createReservation;
    }

    /**
     * Sets the value of the createReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateReply }
     *     
     */
    public void setCreateReservation(CreateReply value) {
        this.createReservation = value;
    }

    /**
     * Gets the value of the modifyReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ModifyResReply }
     *     
     */
    public ModifyResReply getModifyReservation() {
        return modifyReservation;
    }

    /**
     * Sets the value of the modifyReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyResReply }
     *     
     */
    public void setModifyReservation(ModifyResReply value) {
        this.modifyReservation = value;
    }

    /**
     * Gets the value of the cancelReservation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelReservation() {
        return cancelReservation;
    }

    /**
     * Sets the value of the cancelReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelReservation(String value) {
        this.cancelReservation = value;
    }

    /**
     * Gets the value of the queryReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ResDetails }
     *     
     */
    public ResDetails getQueryReservation() {
        return queryReservation;
    }

    /**
     * Sets the value of the queryReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResDetails }
     *     
     */
    public void setQueryReservation(ResDetails value) {
        this.queryReservation = value;
    }

    /**
     * Gets the value of the listReservations property.
     * 
     * @return
     *     possible object is
     *     {@link ListReply }
     *     
     */
    public ListReply getListReservations() {
        return listReservations;
    }

    /**
     * Sets the value of the listReservations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListReply }
     *     
     */
    public void setListReservations(ListReply value) {
        this.listReservations = value;
    }

    /**
     * Gets the value of the createPath property.
     * 
     * @return
     *     possible object is
     *     {@link CreatePathResponseContent }
     *     
     */
    public CreatePathResponseContent getCreatePath() {
        return createPath;
    }

    /**
     * Sets the value of the createPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreatePathResponseContent }
     *     
     */
    public void setCreatePath(CreatePathResponseContent value) {
        this.createPath = value;
    }

    /**
     * Gets the value of the refreshPath property.
     * 
     * @return
     *     possible object is
     *     {@link RefreshPathResponseContent }
     *     
     */
    public RefreshPathResponseContent getRefreshPath() {
        return refreshPath;
    }

    /**
     * Sets the value of the refreshPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefreshPathResponseContent }
     *     
     */
    public void setRefreshPath(RefreshPathResponseContent value) {
        this.refreshPath = value;
    }

    /**
     * Gets the value of the teardownPath property.
     * 
     * @return
     *     possible object is
     *     {@link TeardownPathResponseContent }
     *     
     */
    public TeardownPathResponseContent getTeardownPath() {
        return teardownPath;
    }

    /**
     * Sets the value of the teardownPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link TeardownPathResponseContent }
     *     
     */
    public void setTeardownPath(TeardownPathResponseContent value) {
        this.teardownPath = value;
    }

}
