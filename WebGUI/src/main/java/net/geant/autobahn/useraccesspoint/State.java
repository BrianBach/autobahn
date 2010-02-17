
package net.geant.autobahn.useraccesspoint;
import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for state.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="state">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FAILED"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="FINISHED"/>
 *     &lt;enumeration value="FINISHING"/>
 *     &lt;enumeration value="ACTIVE"/>
 *     &lt;enumeration value="ACTIVATING"/>
 *     &lt;enumeration value="DEFERRED_CANCEL"/>
 *     &lt;enumeration value="WITHDRAWING"/>
 *     &lt;enumeration value="CANCELLING"/>
 *     &lt;enumeration value="SCHEDULED"/>
 *     &lt;enumeration value="SCHEDULING"/>
 *     &lt;enumeration value="LOCAL_CHECK"/>
 *     &lt;enumeration value="PATHFINDING"/>
 *     &lt;enumeration value="ACCEPTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum State {

    ACCEPTED,
    ACTIVATING,
    ACTIVE,
    CANCELLED,
    CANCELLING,
    DEFERRED_CANCEL,
    FAILED,
    FINISHED,
    FINISHING,
    LOCAL_CHECK,
    PATHFINDING,
    SCHEDULED,
    SCHEDULING,
    WITHDRAWING;

    public String value() {
        return name();
    }

    public static State fromValue(String v) {
        return valueOf(v);
    }

}
