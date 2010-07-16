
package net.geant.autobahn.idcp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mplsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mplsInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="burstLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lspClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mplsInfo", propOrder = {
    "burstLimit",
    "lspClass"
})
public class MplsInfo {

    protected int burstLimit;
    protected String lspClass;

    /**
     * Gets the value of the burstLimit property.
     * 
     */
    public int getBurstLimit() {
        return burstLimit;
    }

    /**
     * Sets the value of the burstLimit property.
     * 
     */
    public void setBurstLimit(int value) {
        this.burstLimit = value;
    }

    /**
     * Gets the value of the lspClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLspClass() {
        return lspClass;
    }

    /**
     * Sets the value of the lspClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLspClass(String value) {
        this.lspClass = value;
    }

}
