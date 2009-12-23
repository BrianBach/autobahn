
package net.geant2.cnis.autobahn.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AutobahnToCnisRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutobahnToCnisRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partitionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pruningConditions" type="{http://cnis.geant2.net/autobahn/xml}PruningConditions" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutobahnToCnisRequest", propOrder = {
    "partitionName",
    "pruningConditions"
})
public class AutobahnToCnisRequest {

    protected String partitionName;
    protected PruningConditions pruningConditions;

    /**
     * Gets the value of the partitionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitionName() {
        return partitionName;
    }

    /**
     * Sets the value of the partitionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitionName(String value) {
        this.partitionName = value;
    }

    /**
     * Gets the value of the pruningConditions property.
     * 
     * @return
     *     possible object is
     *     {@link PruningConditions }
     *     
     */
    public PruningConditions getPruningConditions() {
        return pruningConditions;
    }

    /**
     * Sets the value of the pruningConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PruningConditions }
     *     
     */
    public void setPruningConditions(PruningConditions value) {
        this.pruningConditions = value;
    }

}
