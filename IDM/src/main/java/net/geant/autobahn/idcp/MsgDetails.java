
package net.geant.autobahn.idcp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for msgDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="msgDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="forward" type="{http://oscars.es.net/OSCARS}forwardPayload" minOccurs="0"/>
 *         &lt;element name="createReservation" type="{http://oscars.es.net/OSCARS}resCreateContent" minOccurs="0"/>
 *         &lt;element name="modifyReservation" type="{http://oscars.es.net/OSCARS}modifyResContent" minOccurs="0"/>
 *         &lt;element name="cancelReservation" type="{http://oscars.es.net/OSCARS}globalReservationId" minOccurs="0"/>
 *         &lt;element name="queryReservation" type="{http://oscars.es.net/OSCARS}globalReservationId" minOccurs="0"/>
 *         &lt;element name="listReservations" type="{http://oscars.es.net/OSCARS}listRequest" minOccurs="0"/>
 *         &lt;element name="createPath" type="{http://oscars.es.net/OSCARS}createPathContent" minOccurs="0"/>
 *         &lt;element name="refreshPath" type="{http://oscars.es.net/OSCARS}refreshPathContent" minOccurs="0"/>
 *         &lt;element name="teardownPath" type="{http://oscars.es.net/OSCARS}teardownPathContent" minOccurs="0"/>
 *         &lt;element name="forwardResponse" type="{http://oscars.es.net/OSCARS}forwardReply" minOccurs="0"/>
 *         &lt;element name="createReservationResponse" type="{http://oscars.es.net/OSCARS}createReply" minOccurs="0"/>
 *         &lt;element name="modifyReservationResponse" type="{http://oscars.es.net/OSCARS}modifyResReply" minOccurs="0"/>
 *         &lt;element name="cancelReservationResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryReservationResponse" type="{http://oscars.es.net/OSCARS}resDetails" minOccurs="0"/>
 *         &lt;element name="listReservationsResponse" type="{http://oscars.es.net/OSCARS}listReply" minOccurs="0"/>
 *         &lt;element name="createPathResponse" type="{http://oscars.es.net/OSCARS}createPathResponseContent" minOccurs="0"/>
 *         &lt;element name="refreshPathResponse" type="{http://oscars.es.net/OSCARS}refreshPathResponseContent" minOccurs="0"/>
 *         &lt;element name="teardownPathResponse" type="{http://oscars.es.net/OSCARS}teardownPathResponseContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "msgDetails", propOrder = {
    "contentType",
    "forward",
    "createReservation",
    "modifyReservation",
    "cancelReservation",
    "queryReservation",
    "listReservations",
    "createPath",
    "refreshPath",
    "teardownPath",
    "forwardResponse",
    "createReservationResponse",
    "modifyReservationResponse",
    "cancelReservationResponse",
    "queryReservationResponse",
    "listReservationsResponse",
    "createPathResponse",
    "refreshPathResponse",
    "teardownPathResponse"
})
public class MsgDetails {

    @XmlElement(required = true)
    protected String contentType;
    protected ForwardPayload forward;
    protected ResCreateContent createReservation;
    protected ModifyResContent modifyReservation;
    protected GlobalReservationId cancelReservation;
    protected GlobalReservationId queryReservation;
    protected ListRequest listReservations;
    protected CreatePathContent createPath;
    protected RefreshPathContent refreshPath;
    protected TeardownPathContent teardownPath;
    protected ForwardReply forwardResponse;
    protected CreateReply createReservationResponse;
    protected ModifyResReply modifyReservationResponse;
    protected String cancelReservationResponse;
    protected ResDetails queryReservationResponse;
    protected ListReply listReservationsResponse;
    protected CreatePathResponseContent createPathResponse;
    protected RefreshPathResponseContent refreshPathResponse;
    protected TeardownPathResponseContent teardownPathResponse;

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
     * Gets the value of the forward property.
     * 
     * @return
     *     possible object is
     *     {@link ForwardPayload }
     *     
     */
    public ForwardPayload getForward() {
        return forward;
    }

    /**
     * Sets the value of the forward property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForwardPayload }
     *     
     */
    public void setForward(ForwardPayload value) {
        this.forward = value;
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

    /**
     * Gets the value of the forwardResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ForwardReply }
     *     
     */
    public ForwardReply getForwardResponse() {
        return forwardResponse;
    }

    /**
     * Sets the value of the forwardResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForwardReply }
     *     
     */
    public void setForwardResponse(ForwardReply value) {
        this.forwardResponse = value;
    }

    /**
     * Gets the value of the createReservationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreateReply }
     *     
     */
    public CreateReply getCreateReservationResponse() {
        return createReservationResponse;
    }

    /**
     * Sets the value of the createReservationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateReply }
     *     
     */
    public void setCreateReservationResponse(CreateReply value) {
        this.createReservationResponse = value;
    }

    /**
     * Gets the value of the modifyReservationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ModifyResReply }
     *     
     */
    public ModifyResReply getModifyReservationResponse() {
        return modifyReservationResponse;
    }

    /**
     * Sets the value of the modifyReservationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyResReply }
     *     
     */
    public void setModifyReservationResponse(ModifyResReply value) {
        this.modifyReservationResponse = value;
    }

    /**
     * Gets the value of the cancelReservationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelReservationResponse() {
        return cancelReservationResponse;
    }

    /**
     * Sets the value of the cancelReservationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelReservationResponse(String value) {
        this.cancelReservationResponse = value;
    }

    /**
     * Gets the value of the queryReservationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ResDetails }
     *     
     */
    public ResDetails getQueryReservationResponse() {
        return queryReservationResponse;
    }

    /**
     * Sets the value of the queryReservationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResDetails }
     *     
     */
    public void setQueryReservationResponse(ResDetails value) {
        this.queryReservationResponse = value;
    }

    /**
     * Gets the value of the listReservationsResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ListReply }
     *     
     */
    public ListReply getListReservationsResponse() {
        return listReservationsResponse;
    }

    /**
     * Sets the value of the listReservationsResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListReply }
     *     
     */
    public void setListReservationsResponse(ListReply value) {
        this.listReservationsResponse = value;
    }

    /**
     * Gets the value of the createPathResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreatePathResponseContent }
     *     
     */
    public CreatePathResponseContent getCreatePathResponse() {
        return createPathResponse;
    }

    /**
     * Sets the value of the createPathResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreatePathResponseContent }
     *     
     */
    public void setCreatePathResponse(CreatePathResponseContent value) {
        this.createPathResponse = value;
    }

    /**
     * Gets the value of the refreshPathResponse property.
     * 
     * @return
     *     possible object is
     *     {@link RefreshPathResponseContent }
     *     
     */
    public RefreshPathResponseContent getRefreshPathResponse() {
        return refreshPathResponse;
    }

    /**
     * Sets the value of the refreshPathResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefreshPathResponseContent }
     *     
     */
    public void setRefreshPathResponse(RefreshPathResponseContent value) {
        this.refreshPathResponse = value;
    }

    /**
     * Gets the value of the teardownPathResponse property.
     * 
     * @return
     *     possible object is
     *     {@link TeardownPathResponseContent }
     *     
     */
    public TeardownPathResponseContent getTeardownPathResponse() {
        return teardownPathResponse;
    }

    /**
     * Sets the value of the teardownPathResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TeardownPathResponseContent }
     *     
     */
    public void setTeardownPathResponse(TeardownPathResponseContent value) {
        this.teardownPathResponse = value;
    }

}
