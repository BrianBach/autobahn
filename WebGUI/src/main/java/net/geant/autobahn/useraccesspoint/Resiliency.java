
package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for resiliency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="resiliency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ONE_PLUS_ONE"/>
 *     &lt;enumeration value="ONE_TO_ONE"/>
 *     &lt;enumeration value="NONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum Resiliency {

    NONE,
    ONE_PLUS_ONE,
    ONE_TO_ONE;

    public String value() {
        return name();
    }

    public static Resiliency fromValue(String v) {
        return valueOf(v);
    }

}
