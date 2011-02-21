
package net.geant2.jra3.reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import net.geant2.jra3.constraints.PathConstraints;


/**
 * <p>Java class for ReservationParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReservationParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxDelay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resiliency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bidirectional" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="pathConstraints" type="{constraints.jra3.geant2.net}PathConstraints" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReservationParams", propOrder = {
    "capacity",
    "maxDelay",
    "resiliency",
    "bidirectional",
    "startTime",
    "endTime",
    "pathConstraints"
})
public class ReservationParams {

    protected long capacity;
    protected int maxDelay;
    protected String resiliency;
    protected boolean bidirectional;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    protected PathConstraints pathConstraints;

    /**
     * Gets the value of the capacity property.
     * 
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(long value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the maxDelay property.
     * 
     */
    public int getMaxDelay() {
        return maxDelay;
    }

    /**
     * Sets the value of the maxDelay property.
     * 
     */
    public void setMaxDelay(int value) {
        this.maxDelay = value;
    }

    /**
     * Gets the value of the resiliency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResiliency() {
        return resiliency;
    }

    /**
     * Sets the value of the resiliency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResiliency(String value) {
        this.resiliency = value;
    }

    /**
     * Gets the value of the bidirectional property.
     * 
     */
    public boolean isBidirectional() {
        return bidirectional;
    }

    /**
     * Sets the value of the bidirectional property.
     * 
     */
    public void setBidirectional(boolean value) {
        this.bidirectional = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the pathConstraints property.
     * 
     * @return
     *     possible object is
     *     {@link PathConstraints }
     *     
     */
    public PathConstraints getPathConstraints() {
        return pathConstraints;
    }

    /**
     * Sets the value of the pathConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link PathConstraints }
     *     
     */
    public void setPathConstraints(PathConstraints value) {
        this.pathConstraints = value;
    }

}
