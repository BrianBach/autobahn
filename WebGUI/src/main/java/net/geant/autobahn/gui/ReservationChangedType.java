
package net.geant.autobahn.gui;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for reservationChangedType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reservationChangedType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SCHEDULED"/>
 *     &lt;enumeration value="FINISHED"/>
 *     &lt;enumeration value="FAILED"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="ACTIVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ReservationChangedType {

    ACTIVE,
    CANCELLED,
    FAILED,
    FINISHED,
    SCHEDULED;

    public String value() {
        return name();
    }

    public static ReservationChangedType fromValue(String v) {
        return valueOf(v);
    }

}
