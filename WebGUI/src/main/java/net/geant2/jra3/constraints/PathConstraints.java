
package net.geant2.jra3.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.geant2.jra3.administration.ConstraintsNames;


/**
 * <p>Java class for PathConstraints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PathConstraints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addNames" type="{http://administration.jra3.geant2.net/}constraintsNames" maxOccurs="unbounded"/>
 *         &lt;element name="addConstraints" type="{constraints.jra3.geant2.net}AdditiveConstraint" maxOccurs="unbounded"/>
 *         &lt;element name="boolNames" type="{http://administration.jra3.geant2.net/}constraintsNames" maxOccurs="unbounded"/>
 *         &lt;element name="boolConstraints" type="{constraints.jra3.geant2.net}BooleanConstraint" maxOccurs="unbounded"/>
 *         &lt;element name="rangeNames" type="{http://administration.jra3.geant2.net/}constraintsNames" maxOccurs="unbounded"/>
 *         &lt;element name="rangeConstraints" type="{constraints.jra3.geant2.net}RangeConstraint" maxOccurs="unbounded"/>
 *         &lt;element name="minValNames" type="{http://administration.jra3.geant2.net/}constraintsNames" maxOccurs="unbounded"/>
 *         &lt;element name="minValConstraints" type="{constraints.jra3.geant2.net}MinValueConstraint" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PathConstraints", propOrder = {
    "addNames",
    "addConstraints",
    "boolNames",
    "boolConstraints",
    "rangeNames",
    "rangeConstraints",
    "minValNames",
    "minValConstraints"
})
public class PathConstraints implements Serializable {

    @XmlElement(required = true, nillable = true)
    protected List<ConstraintsNames> addNames;
    @XmlElement(required = true, nillable = true)
    protected List<AdditiveConstraint> addConstraints;
    @XmlElement(required = true, nillable = true)
    protected List<ConstraintsNames> boolNames;
    @XmlElement(required = true, nillable = true)
    protected List<BooleanConstraint> boolConstraints;
    @XmlElement(required = true, nillable = true)
    protected List<ConstraintsNames> rangeNames;
    @XmlElement(required = true, nillable = true)
    protected List<RangeConstraint> rangeConstraints;
    @XmlElement(required = true, nillable = true)
    protected List<ConstraintsNames> minValNames;
    @XmlElement(required = true, nillable = true)
    protected List<MinValueConstraint> minValConstraints;

    /**
     * Gets the value of the addNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsNames }
     * 
     * 
     */
    public List<ConstraintsNames> getAddNames() {
        if (addNames == null) {
            addNames = new ArrayList<ConstraintsNames>();
        }
        return this.addNames;
    }

    /**
     * Gets the value of the addConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditiveConstraint }
     * 
     * 
     */
    public List<AdditiveConstraint> getAddConstraints() {
        if (addConstraints == null) {
            addConstraints = new ArrayList<AdditiveConstraint>();
        }
        return this.addConstraints;
    }

    /**
     * Gets the value of the boolNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boolNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoolNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsNames }
     * 
     * 
     */
    public List<ConstraintsNames> getBoolNames() {
        if (boolNames == null) {
            boolNames = new ArrayList<ConstraintsNames>();
        }
        return this.boolNames;
    }

    /**
     * Gets the value of the boolConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boolConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoolConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BooleanConstraint }
     * 
     * 
     */
    public List<BooleanConstraint> getBoolConstraints() {
        if (boolConstraints == null) {
            boolConstraints = new ArrayList<BooleanConstraint>();
        }
        return this.boolConstraints;
    }

    /**
     * Gets the value of the rangeNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rangeNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRangeNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsNames }
     * 
     * 
     */
    public List<ConstraintsNames> getRangeNames() {
        if (rangeNames == null) {
            rangeNames = new ArrayList<ConstraintsNames>();
        }
        return this.rangeNames;
    }

    /**
     * Gets the value of the rangeConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rangeConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRangeConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RangeConstraint }
     * 
     * 
     */
    public List<RangeConstraint> getRangeConstraints() {
        if (rangeConstraints == null) {
            rangeConstraints = new ArrayList<RangeConstraint>();
        }
        return this.rangeConstraints;
    }

    /**
     * Gets the value of the minValNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the minValNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMinValNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConstraintsNames }
     * 
     * 
     */
    public List<ConstraintsNames> getMinValNames() {
        if (minValNames == null) {
            minValNames = new ArrayList<ConstraintsNames>();
        }
        return this.minValNames;
    }

    /**
     * Gets the value of the minValConstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the minValConstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMinValConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MinValueConstraint }
     * 
     * 
     */
    public List<MinValueConstraint> getMinValConstraints() {
        if (minValConstraints == null) {
            minValConstraints = new ArrayList<MinValueConstraint>();
        }
        return this.minValConstraints;
    }

}
