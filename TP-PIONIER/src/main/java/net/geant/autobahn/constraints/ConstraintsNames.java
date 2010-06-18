
package net.geant.autobahn.constraints;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConstraintsNames.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConstraintsNames">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VLANS"/>
 *     &lt;enumeration value="TIMESLOTS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConstraintsNames")
@XmlEnum
public enum ConstraintsNames {

    VLANS,
    TIMESLOTS;

    public String value() {
        return name();
    }

    public static ConstraintsNames fromValue(String v) {
        return valueOf(v);
    }

}
