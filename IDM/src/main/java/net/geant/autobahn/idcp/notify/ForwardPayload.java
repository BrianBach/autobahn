
package net.geant.autobahn.idcp.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for forwardPayload complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="forwardPayload">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createReservation" type="{http://oscars.es.net/OSCARS}resCreateContent" minOccurs="0"/>
 *         &lt;element name="modifyReservation" type="{http://oscars.es.net/OSCARS}modifyResContent" minOccurs="0"/>
 *         &lt;element name="cancelReservation" type="{http://oscars.es.net/OSCARS}globalReservationId" minOccurs="0"/>
 *         &lt;element name="queryReservation" type="{http://oscars.es.net/OSCARS}globalReservationId" minOccurs="0"/>
 *         &lt;element name="listReservations" type="{http://oscars.es.net/OSCARS}listRequest" minOccurs="0"/>
 *         &lt;element name="createPath" type="{http://oscars.es.net/OSCARS}createPathContent" minOccurs="0"/>
 *         &lt;element name="refreshPath" type="{http://oscars.es.net/OSCARS}refreshPathContent" minOccurs="0"/>
 *         &lt;element name="teardownPath" type="{http://oscars.es.net/OSCARS}teardownPathContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forwardPayload", propOrder = {
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
public class ForwardPayload {

    @XmlElement(required = true)
    protected String contentType;
    protected ResCreateContent createReservation;
    protected ModifyResContent modifyReservation;
    protected GlobalReservationId cancelReservation;
    protected GlobalReservationId queryReservation;
    protected ListRequest listReservations;
    protected CreatePathContent createPath;
    protected RefreshPathContent refreshPath;
    protected TeardownPathContent teardownPath;

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
     *     {@link ResCreateContent }
     *     
     */
    public ResCreateContent getCreateReservation() {
        return createReservation;
    }

    /**
     * Sets the value of the createReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResCreateContent }
     *     
     */
    public void setCreateReservation(ResCreateContent value) {
        this.createReservation = value;
    }

    /**
     * Gets the value of the modifyReservation property.
     * 
     * @return
     *     possible object is
     *     {@link ModifyResContent }
     *     
     */
    public ModifyResContent getModifyReservation() {
        return modifyReservation;
    }

    /**
     * Sets the value of the modifyReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyResContent }
     *     
     */
    public void setModifyReservation(ModifyResContent value) {
        this.modifyReservation = value;
    }

    /**
     * Gets the value of the cancelReservation property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReservationId }
     *     
     */
    public GlobalReservationId getCancelReservation() {
        return cancelReservation;
    }

    /**
     * Sets the value of the cancelReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReservationId }
     *     
     */
    public void setCancelReservation(GlobalReservationId value) {
        this.cancelReservation = value;
    }

    /**
     * Gets the value of the queryReservation property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReservationId }
     *     
     */
    public GlobalReservationId getQueryReservation() {
        return queryReservation;
    }

    /**
     * Sets the value of the queryReservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReservationId }
     *     
     */
    public void setQueryReservation(GlobalReservationId value) {
        this.queryReservation = value;
    }

    /**
     * Gets the value of the listReservations property.
     * 
     * @return
     *     possible object is
     *     {@link ListRequest }
     *     
     */
    public ListRequest getListReservations() {
        return listReservations;
    }

    /**
     * Sets the value of the listReservations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRequest }
     *     
     */
    public void setListReservations(ListRequest value) {
        this.listReservations = value;
    }

    /**
     * Gets the value of the createPath property.
     * 
     * @return
     *     possible object is
     *     {@link CreatePathContent }
     *     
     */
    public CreatePathContent getCreatePath() {
        return createPath;
    }

    /**
     * Sets the value of the createPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreatePathContent }
     *     
     */
    public void setCreatePath(CreatePathContent value) {
        this.createPath = value;
    }

    /**
     * Gets the value of the refreshPath property.
     * 
     * @return
     *     possible object is
     *     {@link RefreshPathContent }
     *     
     */
    public RefreshPathContent getRefreshPath() {
        return refreshPath;
    }

    /**
     * Sets the value of the refreshPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefreshPathContent }
     *     
     */
    public void setRefreshPath(RefreshPathContent value) {
        this.refreshPath = value;
    }

    /**
     * Gets the value of the teardownPath property.
     * 
     * @return
     *     possible object is
     *     {@link TeardownPathContent }
     *     
     */
    public TeardownPathContent getTeardownPath() {
        return teardownPath;
    }

    /**
     * Sets the value of the teardownPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link TeardownPathContent }
     *     
     */
    public void setTeardownPath(TeardownPathContent value) {
        this.teardownPath = value;
    }

}
