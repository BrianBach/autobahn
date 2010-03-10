
package net.geant.autobahn.reservation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.network.Path;
import net.geant.autobahn.network.Port;
import net.geant.autobahn.useraccesspoint.PathInfo;


/**
 * <p>Java class for Reservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bodID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startPort" type="{network.autobahn.geant.net}Port" minOccurs="0"/>
 *         &lt;element name="endPort" type="{network.autobahn.geant.net}Port" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="userVlanId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userInclude" type="{http://www.w3.org/2001/XMLSchema}PathInfo"/>
 *         &lt;element name="userExclude" type="{http://www.w3.org/2001/XMLSchema}PathInfo"/>
 *         &lt;element name="maxDelay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resiliency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bidirectional" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="globalConstraints" type="{constraints.autobahn.geant.net}GlobalConstraints" minOccurs="0"/>
 *         &lt;element name="path" type="{network.autobahn.geant.net}Path" minOccurs="0"/>
 *         &lt;element name="intState" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fake" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reservation", propOrder = {
    "bodID",
    "startPort",
    "endPort",
    "startTime",
    "endTime",
    "priority",
    "description",
    "capacity",
    "userInclude",
    "userExclude",
    "userVlanId",
    "maxDelay",
    "resiliency",
    "bidirectional",
    "globalConstraints",
    "path",
    "intState",
    "fake"
})
public class Reservation implements Serializable {

    protected String bodID;
    protected Port startPort;
    protected Port endPort;
    protected Object startTime;
    protected Object endTime;
    protected int priority;
    protected String description;
    protected long capacity;
    protected int userVlanId;
    protected PathInfo userInclude;
    protected PathInfo userExclude;
    protected int maxDelay;
    protected String resiliency;
    protected boolean bidirectional;
    protected GlobalConstraints globalConstraints;
    protected Path path;
    protected int intState;
    protected boolean fake;

    /**
     * Gets the value of the bodID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodID() {
        return bodID;
    }

    /**
     * Sets the value of the bodID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodID(String value) {
        this.bodID = value;
    }

    /**
     * Gets the value of the startPort property.
     * 
     * @return
     *     possible object is
     *     {@link Port }
     *     
     */
    public Port getStartPort() {
        return startPort;
    }

    /**
     * Sets the value of the startPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Port }
     *     
     */
    public void setStartPort(Port value) {
        this.startPort = value;
    }

    /**
     * Gets the value of the endPort property.
     * 
     * @return
     *     possible object is
     *     {@link Port }
     *     
     */
    public Port getEndPort() {
        return endPort;
    }

    /**
     * Sets the value of the endPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Port }
     *     
     */
    public void setEndPort(Port value) {
        this.endPort = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setStartTime(Object value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setEndTime(Object value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
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
     * @return the userVlanId
     */
    public int getUserVlanId() {
        return userVlanId;
    }

    /**
     * @param userVlanId the userVlanId to set
     */
    public void setUserVlanId(int userVlanId) {
        this.userVlanId = userVlanId;
    }

    /**
     * @return the userInclude
     */
    public PathInfo getUserInclude() {
        return userInclude;
    }

    /**
     * @param userInclude the userInclude to set
     */
    public void setUserInclude(PathInfo userInclude) {
        this.userInclude = userInclude;
    }

    /**
     * @return the userExclude
     */
    public PathInfo getUserExclude() {
        return userExclude;
    }

    /**
     * @param userExclude the userExclude to set
     */
    public void setUserExclude(PathInfo userExclude) {
        this.userExclude = userExclude;
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
     * Gets the value of the globalConstraints property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalConstraints }
     *     
     */
    public GlobalConstraints getGlobalConstraints() {
        return globalConstraints;
    }

    /**
     * Sets the value of the globalConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalConstraints }
     *     
     */
    public void setGlobalConstraints(GlobalConstraints value) {
        this.globalConstraints = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link Path }
     *     
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link Path }
     *     
     */
    public void setPath(Path value) {
        this.path = value;
    }

    /**
     * Gets the value of the intState property.
     * 
     */
    public int getIntState() {
        return intState;
    }

    /**
     * Sets the value of the intState property.
     * 
     */
    public void setIntState(int value) {
        this.intState = value;
    }

    /**
     * Gets the value of the intState property.
     * 
     */
    public boolean isFake() {
        return fake;
    }

    /**
     * Sets the value of the fake property.
     * 
     */
    public void setFake(boolean fake) {
        this.fake = fake;
    }
}
