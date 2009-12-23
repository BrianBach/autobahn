package net.geant2.jra3.constraints;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents constraints available in the AutoBAHN system.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ConstraintsNames", namespace="constraints.jra3.geant2.net")
public enum ConstraintsNames implements Serializable {
	VLANS, TIMESLOTS
}
