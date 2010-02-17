
package net.geant.autobahn.administration;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for state.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="state">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SCHEDULING"/>
 *     &lt;enumeration value="SCHEDULED"/>
 *     &lt;enumeration value="FAILED"/>
 *     &lt;enumeration value="CANCELLING"/>
 *     &lt;enumeration value="CANCELLED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum State implements Serializable{

    CANCELLED,
    CANCELLING,
    FAILED,
    SCHEDULED,
    SCHEDULING;

    public String value() {
        return name();
    }

    public static State fromValue(String v) {
        return valueOf(v);
    }

}
