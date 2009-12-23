
package net.geant2.cnis.autobahn.xml.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdministrativeStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdministrativeStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="up"/>
 *     &lt;enumeration value="down"/>
 *     &lt;enumeration value="maintenance"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdministrativeStatus")
@XmlEnum
public enum AdministrativeStatus {

    @XmlEnumValue("up")
    UP("up"),
    @XmlEnumValue("down")
    DOWN("down"),
    @XmlEnumValue("maintenance")
    MAINTENANCE("maintenance");
    private final String value;

    AdministrativeStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdministrativeStatus fromValue(String v) {
        for (AdministrativeStatus c: AdministrativeStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
