package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Michal
 */

@XmlAccessorType(XmlAccessType.FIELD)
public enum State {
	
    ACCEPTED, PATHFINDING, LOCAL_CHECK, SCHEDULING, SCHEDULED,
	CANCELLING, WITHDRAWING, DEFERRED_CANCEL, ACTIVATING, ACTIVE,
	FINISHING, FINISHED, CANCELLED, FAILED

}
