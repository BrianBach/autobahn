
package net.geant2.cnis.autobahn.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CoreTechnology.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CoreTechnology">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="native Ethernet"/>
 *     &lt;enumeration value="SDH"/>
 *     &lt;enumeration value="ATM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CoreTechnology")
@XmlEnum
public enum CoreTechnology {

    @XmlEnumValue("native Ethernet")
    NATIVE_ETHERNET("native Ethernet"),
    SDH("SDH"),
    ATM("ATM");
    private final String value;

    CoreTechnology(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CoreTechnology fromValue(String v) {
        for (CoreTechnology c: CoreTechnology.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
