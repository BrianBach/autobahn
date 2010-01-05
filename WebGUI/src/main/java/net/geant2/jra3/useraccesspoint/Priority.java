
package net.geant2.jra3.useraccesspoint;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for priority.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="priority">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HIGHEST"/>
 *     &lt;enumeration value="HIGH"/>
 *     &lt;enumeration value="NORMAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum Priority {

    HIGH,
    HIGHEST,
    NORMAL;

    public String value() {
        return name();
    }

    public static Priority fromValue(String v) {
        return valueOf(v);
    }

}
