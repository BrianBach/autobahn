package net.geant.autobahn.network;

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
@XmlType(name="StateOper", namespace="network.autobahn.geant.net", propOrder={
		"id", "desc"
})
public class StateOper implements Serializable {

	private int id = 0;
	private String desc = null;

	public final static StateOper UNKNOWN = new StateOper(1, "UNKNOWN");
	public final static StateOper UP = new StateOper(2, "UP");
	public final static StateOper DEGRADED = new StateOper(3, "DEGRADED");
	public final static StateOper DOWN = new StateOper(4, "DOWN");
	
	public static StateOper[] states = {UNKNOWN, UP, DEGRADED, DOWN};
	
	public StateOper() {
		
	}

	public StateOper(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the desc
	 */
	public String getDescription() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}

	public static StateOper getState(int i) {
		for(StateOper state : states) {
			if(state.getId() == i)
				return state;
		}
		
		return UNKNOWN;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getId();
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
		if (this.getId() != other.getId())
			return false;
		return true;
	}
}
