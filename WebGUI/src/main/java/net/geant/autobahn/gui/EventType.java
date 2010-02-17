
package net.geant.autobahn.gui;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for eventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="eventType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TOPOLOGY_CHANGED"/>
 *     &lt;enumeration value="STATUS"/>
 *     &lt;enumeration value="RESERVATION_CHANGED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum EventType {

    RESERVATION_CHANGED,
    STATUS,
    TOPOLOGY_CHANGED;

    public String value() {
        return name();
    }

    public static EventType fromValue(String v) {
        return valueOf(v);
    }

}
