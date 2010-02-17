
package net.geant.autobahn.administration;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for constraintsNames.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="constraintsNames">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VLANS"/>
 *     &lt;enumeration value="TIMESLOTS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ConstraintsNames implements Serializable{

    TIMESLOTS,
    VLANS;

    public String value() {
        return name();
    }

    public static ConstraintsNames fromValue(String v) {
        return valueOf(v);
    }

}
