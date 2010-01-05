package net.geant2.jra3.network;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes status of links.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StateOper", namespace="network.jra3.geant2.net", propOrder={
		"state"
})
public class StateOper implements Serializable {

	private int state = 0;

	public final static StateOper UNKNOWN = new StateOper(0);
	public final static StateOper UP = new StateOper(1);
	public final static StateOper DEGRADED = new StateOper(2);
	public final static StateOper DOWN = new StateOper(3);
	
	private static StateOper[] states = {UNKNOWN, UP, DEGRADED, DOWN};
	
	public StateOper() {
		
	}

	public StateOper(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static StateOper getState(int i) {
		for(StateOper state : states) {
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
		final StateOper other = (StateOper) obj;
		if (getState() != other.getState())
			return false;
		return true;
	}
}
