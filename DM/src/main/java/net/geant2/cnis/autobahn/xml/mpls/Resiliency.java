
package net.geant2.cnis.autobahn.xml.mpls;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resiliency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="resiliency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Protection"/>
 *     &lt;enumeration value="Resotration"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "resiliency")
@XmlEnum
public enum Resiliency {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Protection")
    PROTECTION("Protection"),
    @XmlEnumValue("Resotration")
    RESOTRATION("Resotration");
    private final String value;

    Resiliency(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Resiliency fromValue(String v) {
        for (Resiliency c: Resiliency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
