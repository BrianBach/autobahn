/**
 * 
 */
package net.geant.autobahn.constraints;

import java.io.Serializable;

/**
 * Represents type of constraints available in the AutoBAHN system.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public enum ConstraintsTypes implements Serializable {
	RANGE, ADDITIVE, BOOLEAN, MINVALUE
}
