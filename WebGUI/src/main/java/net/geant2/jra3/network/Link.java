
package net.geant2.jra3.network;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Link complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Link">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bodID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kind" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startPort" type="{network.jra3.geant2.net}Port" minOccurs="0"/>
 *         &lt;element name="endPort" type="{network.jra3.geant2.net}Port" minOccurs="0"/>
 *         &lt;element name="bidirectional" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="delay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="manualCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="monetaryCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="granularity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="minResCapacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maxResCapacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="resilience" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" minOccurs="0"/>
 *         &lt;element name="localName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{network.jra3.geant2.net}LinkType" minOccurs="0"/>
 *         &lt;element name="operationalState" type="{network.jra3.geant2.net}StateOper" minOccurs="0"/>
 *         &lt;element name="administrativeState" type="{network.jra3.geant2.net}StateAdmin" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Link", propOrder = {
    "bodID",
    "kind",
    "startPort",
    "endPort",
    "bidirectional",
    "delay",
    "manualCost",
    "monetaryCost",
    "granularity",
    "minResCapacity",
    "maxResCapacity",
    "capacity",
    "resilience",
    "timestamp",
    "localName",
    "type",
    "operationalState",
    "administrativeState"
})
public class Link implements Serializable{

    protected String bodID;
    protected int kind;
    protected Port startPort;
    protected Port endPort;
    protected boolean bidirectional;
    protected int delay;
    protected double manualCost;
    protected double monetaryCost;
    protected long granularity;
    protected long minResCapacity;
    protected long maxResCapacity;
    protected long capacity;
    protected String resilience;
    protected Object timestamp;
    protected String localName;
    protected LinkType type;
    protected StateOper operationalState;
    protected StateAdmin administrativeState;

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
     * Gets the value of the kind property.
     * 
     */
    public int getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     */
    public void setKind(int value) {
        this.kind = value;
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
     * Gets the value of the delay property.
     * 
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Sets the value of the delay property.
     * 
     */
    public void setDelay(int value) {
        this.delay = value;
    }

    /**
     * Gets the value of the manualCost property.
     * 
     */
    public double getManualCost() {
        return manualCost;
    }

    /**
     * Sets the value of the manualCost property.
     * 
     */
    public void setManualCost(double value) {
        this.manualCost = value;
    }

    /**
     * Gets the value of the monetaryCost property.
     * 
     */
    public double getMonetaryCost() {
        return monetaryCost;
    }

    /**
     * Sets the value of the monetaryCost property.
     * 
     */
    public void setMonetaryCost(double value) {
        this.monetaryCost = value;
    }

    /**
     * Gets the value of the granularity property.
     * 
     */
    public long getGranularity() {
        return granularity;
    }

    /**
     * Sets the value of the granularity property.
     * 
     */
    public void setGranularity(long value) {
        this.granularity = value;
    }

    /**
     * Gets the value of the minResCapacity property.
     * 
     */
    public long getMinResCapacity() {
        return minResCapacity;
    }

    /**
     * Sets the value of the minResCapacity property.
     * 
     */
    public void setMinResCapacity(long value) {
        this.minResCapacity = value;
    }

    /**
     * Gets the value of the maxResCapacity property.
     * 
     */
    public long getMaxResCapacity() {
        return maxResCapacity;
    }

    /**
     * Sets the value of the maxResCapacity property.
     * 
     */
    public void setMaxResCapacity(long value) {
        this.maxResCapacity = value;
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
     * Gets the value of the resilience property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResilience() {
        return resilience;
    }

    /**
     * Sets the value of the resilience property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResilience(String value) {
        this.resilience = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTimestamp(Object value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the localName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Sets the value of the localName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalName(String value) {
        this.localName = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setType(LinkType value) {
        this.type = value;
    }

    /**
     * Gets the value of the operationalState property.
     * 
     * @return
     *     possible object is
     *     {@link StateOper }
     *     
     */
    public StateOper getOperationalState() {
        return operationalState;
    }

    /**
     * Sets the value of the operationalState property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateOper }
     *     
     */
    public void setOperationalState(StateOper value) {
        this.operationalState = value;
    }

    /**
     * Gets the value of the administrativeState property.
     * 
     * @return
     *     possible object is
     *     {@link StateAdmin }
     *     
     */
    public StateAdmin getAdministrativeState() {
        return administrativeState;
    }

    /**
     * Sets the value of the administrativeState property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateAdmin }
     *     
     */
    public void setAdministrativeState(StateAdmin value) {
        this.administrativeState = value;
    }

}
