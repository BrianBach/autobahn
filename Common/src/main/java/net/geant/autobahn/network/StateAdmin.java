package net.geant.autobahn.network;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Describes administrative status values of a link.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StateAdmin", namespace="network.autobahn.geant.net", propOrder={
		"state"
})
public class StateAdmin implements Serializable {
	
	private int state = 0;

	public static final StateAdmin UNKNOWN = new StateAdmin(0);
	public static final StateAdmin NORMAL_OPERATION = new StateAdmin(1);
	public static final StateAdmin MAINTENANCE = new StateAdmin(2);
	public static final StateAdmin TROUBLESHOOTING = new StateAdmin(3);
	public static final StateAdmin UNDER_REPAIR = new StateAdmin(4);
	
	private static StateAdmin[] states = { UNKNOWN, NORMAL_OPERATION,
			MAINTENANCE, TROUBLESHOOTING, UNDER_REPAIR };
	
	public StateAdmin() {
	}
	
	public StateAdmin(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static StateAdmin getState(int i) {
		for(StateAdmin state : states) {
			if(state.getState() == i)
				return state;
		}
		
		return UNKNOWN;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StateAdmin other = (StateAdmin) obj;
		if (getState() != other.getState())
			return false;
		return true;
	}
}
