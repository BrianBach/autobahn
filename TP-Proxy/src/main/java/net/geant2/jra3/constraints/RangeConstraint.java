
package net.geant2.jra3.constraints;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RangeConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RangeConstraint">
 *   &lt;complexContent>
 *     &lt;extension base="{constraints.jra3.geant2.net}Constraint">
 *       &lt;sequence>
 *         &lt;element name="ranges" type="{constraints.jra3.geant2.net}Range" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RangeConstraint", propOrder = {
    "ranges"
})
public class RangeConstraint
    extends Constraint
{

    @XmlElement(required = true, nillable = true)
    protected List<Range> ranges;

    /**
     * Gets the value of the ranges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ranges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRanges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Range }
     * 
     * 
     */
    public List<Range> getRanges() {
        if (ranges == null) {
            ranges = new ArrayList<Range>();
        }
        return this.ranges;
    }

}
