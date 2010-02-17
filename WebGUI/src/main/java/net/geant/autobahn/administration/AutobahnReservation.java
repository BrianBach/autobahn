
package net.geant.autobahn.administration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.reservation.Reservation;


/**
 * <p>Java class for autobahnReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="autobahnReservation">
 *   &lt;complexContent>
 *     &lt;extension base="{reservation.autobahn.geant.net}Reservation">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autobahnReservation")
public abstract class AutobahnReservation
    extends Reservation
{


}
